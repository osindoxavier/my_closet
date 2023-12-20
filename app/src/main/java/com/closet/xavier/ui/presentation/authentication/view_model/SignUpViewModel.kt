package com.closet.xavier.ui.presentation.authentication.view_model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.closet.xavier.data.firebase.model.authentication.UserProfile
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.model.base.ValidationResult
import com.closet.xavier.domain.use_cases.authentication.CheckAuthStateUseCase
import com.closet.xavier.domain.use_cases.authentication.SignUpUseCase
import com.closet.xavier.domain.use_cases.user_profile.CreateProfileUseCase
import com.closet.xavier.domain.validators.AuthValidators
import com.closet.xavier.ui.presentation.authentication.events.SignUpFormEvent
import com.closet.xavier.ui.presentation.authentication.state.AuthenticationStateForm
import com.closet.xavier.ui.presentation.authentication.state.CreateProfileState
import com.closet.xavier.ui.presentation.authentication.state.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val validators: AuthValidators,
    private val getAuthStateUseCase: CheckAuthStateUseCase,
    private val createProfileUseCase: CreateProfileUseCase
) : ViewModel() {


    companion object {
        private const val TAG = "SignUpViewModel"
    }

    var state by mutableStateOf(AuthenticationStateForm())

    private val _stateSignUp = MutableSharedFlow<SignInState>()
    val stateSignUp = _stateSignUp.asSharedFlow()

    private val _stateCreateProfile = MutableSharedFlow<CreateProfileState>()
    val stateCreateProfile = _stateCreateProfile.asSharedFlow()


    private val currentUserUid = mutableStateOf("")


//    init {
//        getAuthState()
//    }
//
//
//    private fun getAuthState() {
//        currentUserUid.value = getAuthStateUseCase(viewScope = viewModelScope).value?.uid.apply {
//            Log.d(TAG, "getAuthState: $this")
//        } ?: ""
//    }


    fun onSignUp(event: SignUpFormEvent) {
        when (event) {
            is SignUpFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is SignUpFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            is SignUpFormEvent.PhoneChanged -> {
                state = state.copy(phone = event.phone)
            }

            is SignUpFormEvent.FirstNameChanged -> {
                state = state.copy(firstName = event.firstName)
            }

            is SignUpFormEvent.MiddleNameChanged -> {
                state = state.copy(middleName = event.middleName)
            }

            is SignUpFormEvent.LastNameChanged -> {
                state = state.copy(lastName = event.lastName)
            }


            SignUpFormEvent.Submit -> {
                validateSignUpData()
            }

        }
    }

    private fun validateSignUpData() {


        val emailResult = validators.executeEmail(state.email)
        val passwordResult =
            if (currentUserUid.value.isEmpty()) validators.executePassword(state.password) else ValidationResult(
                successful = true
            )
        val phoneResult = validators.executePhone(state.phone)
        val firstNameResult = validators.executeFirstName(state.firstName)
        val middleNameResult = validators.executeMiddleName(state.middleName)
        val lastNameResult = validators.executeLastName(state.lastName)


        val hasError = listOf(
            emailResult,
            passwordResult,
            phoneResult,
            firstNameResult,
            middleNameResult,
            lastNameResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                phoneError = phoneResult.errorMessage,
                middleNameError = middleNameResult.errorMessage,
                firstNameError = firstNameResult.errorMessage,
                lastNameError = lastNameResult.errorMessage
            )
            return
        }

        viewModelScope.launch {
            if (currentUserUid.value.isNotEmpty()) {
                val userProfile = UserProfile(
                    uid = currentUserUid.value,
                    email = state.email,
                    firstName = state.firstName,
                    middleName = state.middleName,
                    lastName = state.lastName,
                    phone = state.phone
                )
                Log.d(TAG, "validateSignUpData: $userProfile")
                createUserProfile(userProfile = userProfile)
            } else {
                signUpUser(userState = state)
            }

        }
    }


    private fun signUpUser(userState: AuthenticationStateForm) {
        signUpUseCase(email = userState.email, password = userState.password).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    Log.e(TAG, "signUpUser: ${result.errorResponse}")
                    _stateSignUp.emit(SignInState(error = result.errorResponse))
                }

                is Resource.Loading -> {
                    Log.d(TAG, "signUpUser: isLoading")
                    _stateSignUp.emit(SignInState(isLoading = true))
                }

                is Resource.Success -> {
                    Log.d(TAG, "signUpUser: ${result.data}")
                    val userUid = result.data?.uid
                    val userProfile = userUid?.let {
                        UserProfile(
                            uid = it,
                            email = userState.email,
                            firstName = userState.firstName,
                            middleName = userState.middleName,
                            lastName = userState.lastName,
                            phone = userState.phone
                        )
                    }
                    if (userProfile != null) {
                        createUserProfile(userProfile = userProfile)
                    }
                    _stateSignUp.emit(SignInState(success = result.data))
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun createUserProfile(userProfile: UserProfile) {
        createProfileUseCase(userProfile = userProfile).onEach { result ->

            when (result) {
                is Resource.Error -> {
                    _stateCreateProfile.emit(CreateProfileState(error = result.errorResponse!!))
                }

                is Resource.Loading -> {
                    _stateCreateProfile.emit(CreateProfileState(isLoading = true))
                }

                is Resource.Success -> {
                    _stateCreateProfile.emit(CreateProfileState(success = result.data!!))
                }
            }
        }.launchIn(viewModelScope)
    }
}
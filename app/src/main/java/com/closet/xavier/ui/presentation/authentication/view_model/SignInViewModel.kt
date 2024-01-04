package com.closet.xavier.ui.presentation.authentication.view_model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.model.authentication.AuthenticationRequest
import com.closet.xavier.domain.use_cases.authentication.GoogleSignInUseCase
import com.closet.xavier.domain.use_cases.authentication.SignInWithEmailPasswordUseCase
import com.closet.xavier.domain.use_cases.user_profile.CheckUserProfileUseCase
import com.closet.xavier.domain.validators.AuthValidators
import com.closet.xavier.ui.presentation.authentication.events.AuthenticationFormEvent
import com.closet.xavier.ui.presentation.authentication.state.AuthenticationStateForm
import com.closet.xavier.ui.presentation.authentication.state.CheckUserProfileState
import com.closet.xavier.ui.presentation.authentication.state.SignInState
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInWithEmailPasswordUseCase: SignInWithEmailPasswordUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val validators: AuthValidators,
    private val checkUserProfileUseCase: CheckUserProfileUseCase
) : ViewModel() {

    var state by mutableStateOf(AuthenticationStateForm())

    companion object {
        private const val TAG = "SignInViewModel"
    }

    private val _stateSignIn = MutableSharedFlow<SignInState>()
    val stateSignIn = _stateSignIn.asSharedFlow()


    private val _checkUserProfileState = MutableSharedFlow<CheckUserProfileState>()
    val checkUserProfileState = _checkUserProfileState.asSharedFlow()


    fun onSignIn(event: AuthenticationFormEvent) {
        when (event) {
            is AuthenticationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is AuthenticationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            AuthenticationFormEvent.Submit -> {
                validateSignInData()
            }

            else -> {}
        }
    }

    private fun validateSignInData() {
        val emailResult = validators.executeEmail(state.email)
        val passwordResult = validators.executePassword(state.password)

        val hasError = listOf(
            emailResult, passwordResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage, passwordError = passwordResult.errorMessage
            )
            return
        }

        viewModelScope.launch {
            val request = AuthenticationRequest(
                email = state.email,
                password = state.password
            )
            signInWithEmailAndPassword(request = request)
        }
    }


    private fun signInWithEmailAndPassword(request: AuthenticationRequest) {
        signInWithEmailPasswordUseCase(request = request).onEach { results ->
            when (results) {
                is Resource.Success -> {
                    Log.d(TAG, "signInWithEmailAndPassword: ${results.data}")
                    val uid = results.data?.uid
                    if (uid != null) {
                        checkUserProfile(userId = uid)
                    }
                    _stateSignIn.emit(SignInState(success = results.data))
                }

                is Resource.Error -> {
                    Log.e(TAG, "signInWithEmailAndPassword: ${results.errorResponse}")
                    _stateSignIn.emit(SignInState(error = results.errorResponse))
                }

                is Resource.Loading -> {
                    Log.d(TAG, "signInWithEmailAndPassword: isLoading")
                    _stateSignIn.emit(SignInState(isLoading = true))
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }


    fun signInWithGoogle(credential: AuthCredential) {
        googleSignInUseCase(credential = credential).onEach { results ->
            when (results) {
                is Resource.Success -> {
                    Log.d(TAG, "signInWithGoogle: ${results.data}")
                    val uid = results.data?.uid
                    if (uid != null) {
                        checkUserProfile(userId = uid)
                    }
                    _stateSignIn.emit(SignInState(success = results.data))
                }

                is Resource.Error -> {
                    Log.e(TAG, "signInWithGoogle: ${results.errorResponse}")
                    _stateSignIn.emit(SignInState(error = results.errorResponse))
                }

                is Resource.Loading -> {
                    Log.d(TAG, "signInWithGoogle: isLoading")
                    _stateSignIn.emit(SignInState(isLoading = true))
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun checkUserProfile(userId: String) {
        checkUserProfileUseCase(userId = userId).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _checkUserProfileState.emit(CheckUserProfileState(errorMessage = result.errorResponse!!))
                }

                is Resource.Loading -> {
                    _checkUserProfileState.emit(CheckUserProfileState(isPresent = true))
                }

                is Resource.Success -> {
                    _checkUserProfileState.emit(CheckUserProfileState(isPresent = result.data!!))
                }
            }
        }.launchIn(viewModelScope)
    }
}
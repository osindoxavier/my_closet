package com.closet.xavier.presentation.authentication.view_model

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.closet.xavier.data.firebase.domain.base.Resource
import com.closet.xavier.data.firebase.domain.authentication.AuthenticationRequest
import com.closet.xavier.domain.use_cases.authentication.GetGoogleSignInUseCase
import com.closet.xavier.domain.use_cases.authentication.GetSignInCreateAccountUseCase
import com.closet.xavier.domain.use_cases.authentication.GetSignInWithEmailPasswordUseCase
import com.closet.xavier.domain.validators.AuthValidators
import com.closet.xavier.presentation.authentication.events.AuthenticationFormEvent
import com.closet.xavier.presentation.authentication.state.AuthenticationStateForm
import com.closet.xavier.presentation.authentication.state.SignInState
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val getSignInWithEmailPasswordUseCase: GetSignInWithEmailPasswordUseCase,
    private val getSignInCreateAccountUseCase: GetSignInCreateAccountUseCase,
    private val googleSignInUseCase: GetGoogleSignInUseCase,
    private val validators: AuthValidators
) : ViewModel() {

    var state by mutableStateOf(AuthenticationStateForm())

    companion object {
        private const val TAG = "AuthenticationViewModel"
    }

    private val _stateSignIn = MutableSharedFlow<SignInState>()
    val stateSignIn = _stateSignIn.asSharedFlow()


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
        getSignInWithEmailPasswordUseCase(request = request).onEach { results ->
            when (results) {
                is Resource.Success -> {
                    Log.d(TAG, "signInWithEmailAndPassword: ${results.data}")
                    _stateSignIn.emit(SignInState(success = results.data.toString()))
                }

                is Resource.Error -> {
                    Log.e(TAG, "signInWithEmailAndPassword: ${results.errorResponse}")
                    _stateSignIn.emit(SignInState(error = results.errorResponse))
                }

                is Resource.Loading -> {
                    Log.d(TAG, "signInWithEmailAndPassword: isLoading")
                    _stateSignIn.emit(SignInState(isLoading = true))
                }
            }
        }.launchIn(viewModelScope)
    }


    fun signInWithGoogle(credential: AuthCredential) {
        googleSignInUseCase(credential = credential).onEach { results ->
            when (results) {
                is Resource.Success -> {
                    Log.d(TAG, "signInWithGoogle: ${results.data}")
                    _stateSignIn.emit(SignInState(success = results.data.toString()))
                }

                is Resource.Error -> {
                    Log.e(TAG, "signInWithGoogle: ${results.errorResponse}")
                    _stateSignIn.emit(SignInState(error = results.errorResponse))
                }

                is Resource.Loading -> {
                    Log.d(TAG, "signInWithGoogle: isLoading")
                    _stateSignIn.emit(SignInState(isLoading = true))
                }
            }
        }.launchIn(viewModelScope)
    }
}
package com.closet.xavier.domain.use_cases.authentication

import android.util.Log
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.model.authentication.AuthenticationRequest
import com.closet.xavier.data.firebase.repository.authentication.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInWithEmailPasswordUseCase @Inject constructor(private val repository: AuthenticationRepository) {

    companion object {
        private const val TAG = "SignInWithEmailPassword"
    }

    operator fun invoke(request: AuthenticationRequest): Flow<Resource<FirebaseUser>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.signIn(email = request.email, password = request.password)
            Log.d(TAG, "invoke: ${result.data}")
            emit(Resource.Success(result.data!!))
        } catch (e: Exception) {
            e.stackTrace
            Log.e(TAG, "invoke: ${e.message}", e)
            emit(Resource.Error(errorResponse = e.localizedMessage))
        }
    }
}
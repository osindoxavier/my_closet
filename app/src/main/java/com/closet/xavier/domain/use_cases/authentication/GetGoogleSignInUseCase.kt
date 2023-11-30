package com.closet.xavier.domain.use_cases.authentication

import android.util.Log
import com.closet.xavier.data.firebase.domain.base.Resource
import com.closet.xavier.data.firebase.repository.authentication.AuthenticationRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGoogleSignInUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    companion object {
        private const val TAG = "GetGoogleSignInUseCase"
    }
    operator fun invoke(credential: AuthCredential): Flow<Resource<AuthResult>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.googleSignIn(credential = credential)
            Log.d(TAG, "invoke: $result")
            emit(Resource.Success(result))
        } catch (e: Exception) {
            e.stackTrace
            Log.e(TAG, "invoke: ${e.message}", e)
            emit(Resource.Error(errorResponse = e.message))
        }
    }
}
package com.closet.xavier.domain.use_cases.authentication

import android.util.Log
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.repository.authentication.AuthenticationRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    companion object {
        private const val TAG = "GoogleSignInUseCase"
    }

    operator fun invoke(credential: AuthCredential): Flow<Resource<FirebaseUser>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.googleSignIn(authCredential = credential)
            Log.d(TAG, "invoke: $result")
            emit(Resource.Success(result.data!!))
        } catch (e: Exception) {
            e.stackTrace
            Log.e(TAG, "invoke: ${e.message}", e)
            emit(Resource.Error(errorResponse = e.message))
        }
    }
}
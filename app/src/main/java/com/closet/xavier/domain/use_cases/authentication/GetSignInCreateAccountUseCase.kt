package com.closet.xavier.domain.use_cases.authentication

import android.util.Log
import com.closet.xavier.data.firebase.domain.base.Resource
import com.closet.xavier.data.firebase.domain.authentication.AuthenticationRequest
import com.closet.xavier.data.firebase.repository.authentication.AuthenticationRepository
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSignInCreateAccountUseCase @Inject constructor(private val repository: AuthenticationRepository) {

    companion object {
        private const val TAG = "GetSignInCreateAccountU"
    }

    operator fun invoke(request: AuthenticationRequest): Flow<Resource<AuthResult>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.signUp(request = request)
            Log.d(TAG, "invoke: $result")
            emit(Resource.Success(result))
        } catch (e: Exception) {
            e.stackTrace
            Log.e(TAG, "invoke: ${e.message}", e)
            emit(Resource.Error(errorResponse = e.message))
        }
    }
}
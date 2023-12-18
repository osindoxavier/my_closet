package com.closet.xavier.domain.use_cases.authentication

import android.util.Log
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.repository.authentication.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    companion object {
        private const val TAG = "SignUpUseCase"
    }

    operator fun invoke(email: String, password: String): Flow<Resource<FirebaseUser>> = flow {
        emit(Resource.Loading())
        try {
            val result = repository.signUp(email = email, password = password)
            Log.d(TAG, "invoke: ${result.data}")
            emit(Resource.Success(data = result.data!!))
        } catch (e: Exception) {
            Log.e(TAG, "invoke: ${e.localizedMessage}", e)
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage))
        }
    }
}
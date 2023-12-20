package com.closet.xavier.domain.use_cases.authentication

import android.util.Log
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.model.authentication.UserProfile
import com.closet.xavier.data.firebase.repository.authentication.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateProfileUseCase @Inject constructor(private val repository: AuthenticationRepository) {

    companion object {
        private const val TAG = "CreateProfileUseCase"
    }

    operator fun invoke(userProfile: UserProfile): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.createUserProfile(userProfile = userProfile)
            if (result.data == true) {
                Log.d(TAG, "invoke: ${result.data}")
                emit(Resource.Success(result.data))
            }

        } catch (e: Exception) {
            e.stackTrace
            Log.e(TAG, "invoke: ${e.message}", e)
            emit(Resource.Error(errorResponse = e.message))
        }
    }
}
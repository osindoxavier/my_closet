package com.closet.xavier.domain.use_cases.user_profile

import android.util.Log
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.repository.profile.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckUserProfileUseCase @Inject constructor(private val repository: UserProfileRepository) {
    companion object {
        private const val TAG = "CheckUserProfileUseCase"
    }

    operator fun invoke(userId: String): Flow<Resource<Boolean>> = flow {
        try {
            val result = repository.checkIfUserProfileExists(userId)
            Log.d(TAG, "invoke: ${result.data}")
            emit(Resource.Success(result.data!!))
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "invoke: ${e.localizedMessage}", e)
            emit(Resource.Error(e.localizedMessage))
        }
    }
}
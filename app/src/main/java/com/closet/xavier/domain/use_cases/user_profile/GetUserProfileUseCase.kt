package com.closet.xavier.domain.use_cases.user_profile

import android.util.Log
import com.closet.xavier.data.firebase.model.authentication.UserProfile
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.repository.profile.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: UserProfileRepository) {

    companion object {
        private const val TAG = "GetUserProfileUseCase"
    }



    operator fun invoke(userId: String): Flow<Resource<UserProfile>> = flow {
        emit(Resource.Loading())
        try {
            val result = repository.getUserProfileFromFireStore(userId = userId)
            Log.d(TAG, "invoke: $result")
            emit(Resource.Success(data = result.data!!))
        } catch (e: Exception) {
            emit(Resource.Error(errorResponse = e.toString()))
        }
    }
}
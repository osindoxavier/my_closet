package com.closet.xavier.domain.use_cases.user_profile

import android.util.Log
import com.closet.xavier.data.firebase.model.authentication.UserProfile
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.data.firebase.repository.profile.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateProfileUseCase @Inject constructor(private val repository: UserProfileRepository) {

    companion object {
        private const val TAG = "CreateProfileUseCase"
    }

    operator fun invoke(userProfile: UserProfile): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            val result = repository.createUserProfile(userProfile = userProfile)
            emit(Resource.Success(result.data!!))
        } catch (e: Exception) {
            Log.e(TAG, "invoke: ${e.localizedMessage}", e)
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage))
        }
    }

}
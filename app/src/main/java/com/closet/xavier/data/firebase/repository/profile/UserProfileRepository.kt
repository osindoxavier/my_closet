package com.closet.xavier.data.firebase.repository.profile

import com.closet.xavier.data.firebase.model.authentication.UserProfile
import com.closet.xavier.data.firebase.model.base.Resource

interface UserProfileRepository {
    suspend fun checkIfUserProfileExists(userId: String): Resource<Boolean>
    suspend fun createUserProfile(userProfile: UserProfile): Resource<Boolean>
    suspend fun getUserProfileFromFireStore(userId: String): Resource<UserProfile?>
}
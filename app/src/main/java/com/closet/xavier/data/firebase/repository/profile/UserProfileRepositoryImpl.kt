package com.closet.xavier.data.firebase.repository.profile

import com.closet.xavier.data.firebase.model.authentication.UserProfile
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.utils.Constants
import com.closet.xavier.utils.await
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserProfileRepositoryImpl @Inject constructor(firestore: FirebaseFirestore) :
    UserProfileRepository {
    private val collectionRef = firestore.collection(Constants.FIREBASE_USER_COLLECTION)
    override suspend fun checkIfUserProfileExists(userId: String): Resource<Boolean> {
        return try {
            val result = collectionRef.document(userId).get().await()
            Resource.Success(result.exists())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage)
        }
    }

    override suspend fun createUserProfile(userProfile: UserProfile): Resource<Boolean> = try {
        val id = userProfile.uid
        collectionRef.document(id).set(userProfile).await()
        Resource.Success(true)
    } catch (e: Exception) {
        Resource.Error(e.localizedMessage)
    }

    override suspend fun getUserProfileFromFireStore(userId: String): Resource<UserProfile?> =
        withContext(
            Dispatchers.IO
        ) {
            return@withContext try {
                val documentSnapshot = collectionRef.document(userId).get().await()
                if (documentSnapshot.exists()) {
                    val result = documentSnapshot.toObject(UserProfile::class.java)
                    Resource.Success(result)
                } else {
                    Resource.Error(errorResponse = "User profile doesn't exist!")
                }
            } catch (e: FirebaseException) {
                Resource.Error(errorResponse = e.localizedMessage)
            }
        }

}
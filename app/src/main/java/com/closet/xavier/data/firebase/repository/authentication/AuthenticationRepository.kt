package com.closet.xavier.data.firebase.repository.authentication

import com.closet.xavier.data.firebase.model.authentication.UserProfile
import com.closet.xavier.data.firebase.model.base.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthenticationRepository {

    val currentUser: FirebaseUser?

    suspend fun startAuthStateListener(): Flow<Boolean>
    fun stopAuthStateListener()
    suspend fun signIn(email: String, password: String): Resource<FirebaseUser>


    suspend fun googleSignIn(authCredential: AuthCredential): Resource<FirebaseUser>
    suspend fun signUp(email: String, password: String): Resource<FirebaseUser>

    suspend fun createUserProfile(userProfile: UserProfile): Resource<Boolean>


    fun logOut()
}
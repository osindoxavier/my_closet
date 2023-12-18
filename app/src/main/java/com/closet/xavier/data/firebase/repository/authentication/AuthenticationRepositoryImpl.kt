package com.closet.xavier.data.firebase.repository.authentication

import com.closet.xavier.data.firebase.model.authentication.UserProfile
import com.closet.xavier.data.firebase.model.base.Resource
import com.closet.xavier.utils.Constants
import com.closet.xavier.utils.await
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth, private val firestore: FirebaseFirestore
) : AuthenticationRepository {

    //    override val currentUser: FirebaseUser?
//        get() {
//            return firebaseAuth.currentUser
//        }
//
//    override suspend fun signIn(request: AuthenticationRequest): AuthResult {
//        return firebaseAuth.signInWithEmailAndPassword(request.email, request.password).await()
//    }
//
//    override suspend fun signUp(email: String, password: String): AuthResult {
//        return firebaseAuth.createUserWithEmailAndPassword(email, password).await()
//    }
//
//    override suspend fun googleSignIn(credential: AuthCredential): AuthResult {
//        return firebaseAuth.signInWithCredential(credential).await()
//    }
//
//    override suspend fun reloadFirebaseUser(): Resource<Boolean> {
//        return try {
//            firebaseAuth.currentUser?.reload()?.await()
//            Resource.Success(true)
//        } catch (e: Exception) {
//            Resource.Error(e.localizedMessage)
//        }
//    }
//
//    override fun signOut() {
//        firebaseAuth.signOut()
//    }
//


    override val currentUser: FirebaseUser? get() = firebaseAuth.currentUser

    override fun getAuthState(viewScope: CoroutineScope) = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser)
        }
        firebaseAuth.addAuthStateListener(authStateListener)
        awaitClose {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewScope, SharingStarted.WhileSubscribed(), firebaseAuth.currentUser)

    override suspend fun signIn(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(errorResponse = e.localizedMessage)
        }
    }

    override suspend fun googleSignIn(authCredential: AuthCredential): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithCredential(authCredential).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage)
        }
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): Resource<FirebaseUser> {
        return try {
            val result =
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            return Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage)
        }
    }

    override suspend fun createUserProfile(userProfile: UserProfile): Resource<Boolean> {
        return try {
            firestore.collection(Constants.FIREBASE_USER_COLLECTION).document(userProfile.uid)
                .set(userProfile).await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(errorResponse = e.localizedMessage)
        }
    }


    override fun logOut() {
        firebaseAuth.signOut()
    }


}
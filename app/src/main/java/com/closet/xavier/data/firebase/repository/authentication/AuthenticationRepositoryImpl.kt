package com.closet.xavier.data.firebase.repository.authentication

import com.closet.xavier.data.firebase.domain.authentication.AuthenticationRequest
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthenticationRepository {
    override suspend fun signIn(request: AuthenticationRequest): AuthResult {
        return firebaseAuth.signInWithEmailAndPassword(request.email, request.password).await()
    }

    override suspend fun signUp(request: AuthenticationRequest): AuthResult {
        return firebaseAuth.createUserWithEmailAndPassword(request.email, request.password).await()
    }

    override suspend fun googleSignIn(credential: AuthCredential): AuthResult {
        return firebaseAuth.signInWithCredential(credential).await()
    }


}
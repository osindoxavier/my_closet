package com.closet.xavier.data.firebase.repository.authentication

import com.closet.xavier.data.firebase.domain.authentication.AuthenticationRequest
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult

interface AuthenticationRepository {
    suspend fun signIn(request: AuthenticationRequest): AuthResult
    suspend fun signUp(request: AuthenticationRequest):AuthResult

    suspend fun googleSignIn(credential: AuthCredential):AuthResult
}
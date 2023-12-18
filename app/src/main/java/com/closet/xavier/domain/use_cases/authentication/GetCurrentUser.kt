package com.closet.xavier.domain.use_cases.authentication

import com.closet.xavier.data.firebase.repository.authentication.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class GetCurrentUser @Inject constructor(private val repository: AuthenticationRepository) {
    operator fun invoke(): FirebaseUser? {
        return repository.currentUser
    }
}
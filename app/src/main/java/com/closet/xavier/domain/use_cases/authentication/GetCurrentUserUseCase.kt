package com.closet.xavier.domain.use_cases.authentication

import com.closet.xavier.data.firebase.repository.authentication.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    operator fun invoke(): FirebaseUser? {
        return repository.currentUser
    }
}
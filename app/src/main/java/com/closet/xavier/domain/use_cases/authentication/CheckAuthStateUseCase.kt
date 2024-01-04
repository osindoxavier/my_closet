package com.closet.xavier.domain.use_cases.authentication

import com.closet.xavier.data.firebase.repository.authentication.AuthenticationRepository
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class CheckAuthStateUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(): Flow<Boolean> {
        return repository.startAuthStateListener()
    }
}
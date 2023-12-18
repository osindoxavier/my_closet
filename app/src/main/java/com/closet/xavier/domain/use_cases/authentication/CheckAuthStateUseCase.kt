package com.closet.xavier.domain.use_cases.authentication

import com.closet.xavier.data.firebase.repository.authentication.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CheckAuthStateUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    operator fun invoke(viewScope: CoroutineScope): StateFlow<FirebaseUser?> {
        return repository.getAuthState(viewScope = viewScope)
    }
}
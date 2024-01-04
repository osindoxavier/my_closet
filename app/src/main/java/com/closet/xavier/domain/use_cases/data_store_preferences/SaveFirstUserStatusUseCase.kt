package com.closet.xavier.domain.use_cases.data_store_preferences

import com.closet.xavier.data.data_store_prefence.repository.DataStorePreferencesRepository
import javax.inject.Inject

class SaveFirstUserStatusUseCase @Inject constructor(private val repository: DataStorePreferencesRepository) {
    suspend operator fun invoke() {
        repository.saveFirstTimeUser()
    }
}
package com.closet.xavier.domain.use_cases.data_store_preferences

import com.closet.xavier.data.data_store_prefence.model.DarkTheme
import com.closet.xavier.data.data_store_prefence.repository.DataStorePreferencesRepository
import javax.inject.Inject

class ToggleDarkThemeUseCase @Inject constructor(
    private val repository: DataStorePreferencesRepository
) {
    companion object {
        private const val TAG = "ToggleDarkThemeUseCase"
    }

    suspend operator fun invoke(isDark: Boolean) {
        repository.toggleThemeMode(isDark = isDark)
    }
}
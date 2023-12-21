package com.closet.xavier.data.data_store_prefence.repository

import com.closet.xavier.data.data_store_prefence.model.DarkTheme
import kotlinx.coroutines.flow.Flow

interface DataStorePreferencesRepository {
    suspend fun saveFirstTimeUser()
    fun isUserFirstTimeUser(): Flow<Boolean>
    suspend fun saveUserUid(userId: String)
    fun getUserUid(): Flow<String>

    fun getThemeMode():Flow<DarkTheme>

    suspend fun toggleThemeMode(isDark:Boolean)
}
package com.closet.xavier.data.data_store_prefence.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.closet.xavier.data.data_store_prefence.model.DarkTheme
import com.closet.xavier.utils.Constants.IS_DARK_MODE
import com.closet.xavier.utils.Constants.IS_FIRST_TIME_USER
import com.closet.xavier.utils.Constants.LOGGED_IN_USER_UID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStorePreferencesRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    DataStorePreferencesRepository {
    override suspend fun saveFirstTimeUser() {
        dataStore.edit { preferences ->
            preferences[IS_FIRST_TIME_USER] = false
        }
    }

    override fun isUserFirstTimeUser(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[IS_FIRST_TIME_USER] ?: true
        }
    }


    override suspend fun saveUserUid(userId: String) {
        dataStore.edit { preferences ->
            preferences[LOGGED_IN_USER_UID] = userId

        }
    }

    override fun getUserUid(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[LOGGED_IN_USER_UID] ?: ""
        }
    }

    override fun getThemeMode(): Flow<DarkTheme> = flow {
        val response = dataStore.data.map { preferences ->
            preferences[IS_DARK_MODE] ?: false
        }
        val darkTheme = DarkTheme(isDark = response.first())
        emit(darkTheme)
    }

    override suspend fun toggleThemeMode(isDark: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = isDark
        }
    }
}
package com.closet.xavier.data.firebase.repository.data_store_prefence

import kotlinx.coroutines.flow.Flow

interface DataStorePreferencesRepository {
    suspend fun saveFirstTimeUser()
    fun isUserFirstTimeUser(): Flow<Boolean>
    suspend fun saveUserUid(userId: String)
    fun getUserUid(): Flow<String>
}
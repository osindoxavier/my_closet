package com.closet.xavier.data.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.closet.xavier.data.di.DataStoreSingleton
import com.closet.xavier.data.data_store_prefence.repository.DataStorePreferencesRepository
import com.closet.xavier.data.data_store_prefence.repository.DataStorePreferencesRepositoryImpl
import kotlinx.coroutines.flow.Flow

class DataStorePreferencesRepositoryTest : DataStorePreferencesRepository {

    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val testDataStore = DataStoreSingleton.getInstance(testContext)

    private val repository: DataStorePreferencesRepository =
        DataStorePreferencesRepositoryImpl(testDataStore)

    override suspend fun saveFirstTimeUser() {
        repository.saveFirstTimeUser()
    }

    override fun isUserFirstTimeUser(): Flow<Boolean> {
        return repository.isUserFirstTimeUser()
    }

    override suspend fun saveUserUid(userId: String) {
        repository.saveUserUid(userId)
    }

    override fun getUserUid(): Flow<String> {
        return repository.getUserUid()
    }
}
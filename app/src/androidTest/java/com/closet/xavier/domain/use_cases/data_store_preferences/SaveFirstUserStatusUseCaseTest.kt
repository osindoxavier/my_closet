package com.closet.xavier.domain.use_cases.data_store_preferences

import com.closet.xavier.data.repository.DataStorePreferencesRepositoryTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SaveFirstUserStatusUseCaseTest{

    private lateinit var repository: DataStorePreferencesRepositoryTest
    private lateinit var useCase: SaveFirstUserStatusUseCase


    @Before
    fun setUp() {
        repository = DataStorePreferencesRepositoryTest()
        useCase = SaveFirstUserStatusUseCase(repository=repository)

        runBlocking {
            repository.saveFirstTimeUser()
        }
    }

    @Test
    fun saveFirstTimeUserStatus() = runBlocking {
        useCase()
        assertTrue(
            !repository.isUserFirstTimeUser().first()
        )
    }

}
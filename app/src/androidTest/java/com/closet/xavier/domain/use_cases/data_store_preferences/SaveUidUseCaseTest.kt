package com.closet.xavier.domain.use_cases.data_store_preferences

import com.closet.xavier.data.repository.DataStorePreferencesRepositoryTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SaveUidUseCaseTest {

    private lateinit var repository: DataStorePreferencesRepositoryTest
    private lateinit var useCase: SaveUidUseCase
    private lateinit var uid: String


    @Before
    fun setUp() {
        repository = DataStorePreferencesRepositoryTest()
        useCase = SaveUidUseCase(repository = repository)
        uid = "test_uid"
    }

    @Test
    fun saveFirstTimeUserStatus() = runBlocking {
        useCase(userUid = uid)
        val result = repository.getUserUid().first()
        assertTrue(
            result == uid
        )
    }
}
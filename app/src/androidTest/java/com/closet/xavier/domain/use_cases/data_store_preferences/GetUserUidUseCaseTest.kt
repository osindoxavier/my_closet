package com.closet.xavier.domain.use_cases.data_store_preferences

import com.closet.xavier.data.repository.DataStorePreferencesRepositoryTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetUserUidUseCaseTest {
    private lateinit var repository: DataStorePreferencesRepositoryTest
    private lateinit var useCase: GetUserUidUseCase


    @Before
    fun setUp() {
        repository = DataStorePreferencesRepositoryTest()
        useCase = GetUserUidUseCase(repository = repository)
    }

    @Test
    fun getLoggedInUserUID() = runBlocking {
        val userUid = "test_uid"
        repository.saveUserUid(userUid)
        val result = useCase().first()
        assertTrue(result == userUid)


    }
}
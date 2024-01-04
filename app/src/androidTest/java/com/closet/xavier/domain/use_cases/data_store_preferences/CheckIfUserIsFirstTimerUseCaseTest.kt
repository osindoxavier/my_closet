package com.closet.xavier.domain.use_cases.data_store_preferences

import com.closet.xavier.data.repository.DataStorePreferencesRepositoryTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CheckIfUserIsFirstTimerUseCaseTest {
    private lateinit var repository: DataStorePreferencesRepositoryTest
    private lateinit var useCase: CheckIfUserIsFirstTimerUseCase


    @Before
    fun setUp() {
        repository = DataStorePreferencesRepositoryTest()
        useCase = CheckIfUserIsFirstTimerUseCase(repository = repository)
    }

    @Test
    fun checkIfFirstTimeUser() = runBlocking {
        val result1 = useCase().first()
        println(result1)
        assertTrue(
            result1
        )
        repository.saveFirstTimeUser()
        val result2 = useCase().first()
        assertTrue(
            !result2
        )


    }
}
package com.closet.xavier.domain.use_cases.data_store_preferences

import android.util.Log
import com.closet.xavier.data.firebase.repository.data_store_prefence.DataStorePreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.math.log

class CheckIfUserIsFirstTimerUseCase @Inject constructor(
    private val repository: DataStorePreferencesRepository
) {
    companion object {
        private const val TAG = "CheckIfUserIsFirstTimer"
    }

    operator fun invoke(): Flow<Boolean> = flow {
        val response = repository.isUserFirstTimeUser()
        Log.d(TAG, "invoke: $response")
        emit(response.first())
    }
}
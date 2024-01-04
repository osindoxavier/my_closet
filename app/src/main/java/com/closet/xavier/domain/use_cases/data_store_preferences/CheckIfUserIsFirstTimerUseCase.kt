package com.closet.xavier.domain.use_cases.data_store_preferences

import android.util.Log
import com.closet.xavier.data.data_store_prefence.repository.DataStorePreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

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
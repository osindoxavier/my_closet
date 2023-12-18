package com.closet.xavier.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope

private const val TEST_DATASTORE_NAME: String = "test_datastore"
object DataStoreSingleton {
    private var instance: DataStore<Preferences>? = null

    fun getInstance(context: Context): DataStore<Preferences> {
        return instance ?: testDataStore(context).also { instance = it }
    }

    private val testCoroutineDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope =
        TestCoroutineScope(testCoroutineDispatcher + Job())

    private fun testDataStore(context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = testCoroutineScope,
            produceFile =
            { context.preferencesDataStoreFile(TEST_DATASTORE_NAME) }
        )
}
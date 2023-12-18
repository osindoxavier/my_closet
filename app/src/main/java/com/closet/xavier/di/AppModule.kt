package com.closet.xavier.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.closet.xavier.data.firebase.repository.authentication.AuthenticationRepository
import com.closet.xavier.data.firebase.repository.authentication.AuthenticationRepositoryImpl
import com.closet.xavier.data.firebase.repository.brand.BrandRepository
import com.closet.xavier.data.firebase.repository.brand.BrandRepositoryImpl
import com.closet.xavier.data.firebase.repository.data_store_prefence.DataStorePreferencesRepository
import com.closet.xavier.data.firebase.repository.data_store_prefence.DataStorePreferencesRepositoryImpl
import com.closet.xavier.data.firebase.repository.product.ProductRepository
import com.closet.xavier.data.firebase.repository.product.ProductRepositoryImpl
import com.closet.xavier.data.firebase.repository.profile.UserProfileRepository
import com.closet.xavier.data.firebase.repository.profile.UserProfileRepositoryImpl
import com.closet.xavier.domain.validators.AuthValidatorImpl
import com.closet.xavier.domain.validators.AuthValidators
import com.closet.xavier.utils.Constants.USER_PREFERENCES
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFireStore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }


    @Provides
    @Singleton
    fun provideUserRepository(firestore: FirebaseFirestore): UserProfileRepository {
        return UserProfileRepositoryImpl(firestore = firestore)
    }


    @Provides
    @Singleton
    fun providesAuthenticationRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(firebaseAuth = firebaseAuth, firestore = firestore)
    }

    @Provides
    @Singleton
    fun provideAuthValidator(): AuthValidators {
        return AuthValidatorImpl()
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext, USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }

    @Singleton
    @Provides
    fun provideDataStorePreferencesRepository(dataStore: DataStore<Preferences>): DataStorePreferencesRepository {
        return DataStorePreferencesRepositoryImpl(dataStore = dataStore)
    }

    @Singleton
    @Provides
    fun provideBrandsRepository(firestore: FirebaseFirestore): BrandRepository {
        return BrandRepositoryImpl(firestore = firestore)
    }

    @Singleton
    @Provides
    fun provideProductsRepository(firestore: FirebaseFirestore): ProductRepository {
        return ProductRepositoryImpl(firestore = firestore)
    }
}
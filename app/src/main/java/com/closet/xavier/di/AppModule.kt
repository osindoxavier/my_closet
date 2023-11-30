package com.closet.xavier.di

import android.content.Context
import com.closet.xavier.data.firebase.repository.authentication.AuthenticationRepository
import com.closet.xavier.data.firebase.repository.authentication.AuthenticationRepositoryImpl
import com.closet.xavier.domain.validators.AuthValidatorImpl
import com.closet.xavier.domain.validators.AuthValidators
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun providesAuthenticationRepository(firebaseAuth: FirebaseAuth): AuthenticationRepository {
        return AuthenticationRepositoryImpl(firebaseAuth = firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideAuthValidator(): AuthValidators {
        return AuthValidatorImpl()
    }
}
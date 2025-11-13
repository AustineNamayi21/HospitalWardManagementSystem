package com.austi.hospitalwardmanagementsystem.di

import com.austi.hospitalwardmanagementsystem.data.PatientRepository
import com.austi.hospitalwardmanagementsystem.data.PatientDao
import com.austi.hospitalwardmanagementsystem.data.WardDao
import com.austi.hospitalwardmanagementsystem.data.WardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePatientRepository(
        patientDao: PatientDao,
        wardDao: WardDao
    ): PatientRepository {
        return PatientRepository(patientDao, wardDao)
    }

    @Provides
    @Singleton
    fun provideWardRepository(wardDao: WardDao): WardRepository {
        return WardRepository(wardDao)
    }
}
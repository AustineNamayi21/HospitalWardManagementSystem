package com.austi.hospitalwardmanagementsystem.di

import android.content.Context
import com.austi.hospitalwardmanagementsystem.data.HospitalDatabase
import com.austi.hospitalwardmanagementsystem.data.PatientDao
import com.austi.hospitalwardmanagementsystem.data.WardDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideHospitalDatabase(@ApplicationContext context: Context): HospitalDatabase {
        return HospitalDatabase.getDatabase(context)
    }

    @Provides
    fun providePatientDao(database: HospitalDatabase): PatientDao {
        return database.patientDao()
    }

    @Provides
    fun provideWardDao(database: HospitalDatabase): WardDao {
        return database.wardDao()
    }
}
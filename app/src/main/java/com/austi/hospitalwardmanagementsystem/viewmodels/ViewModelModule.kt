package com.austi.hospitalwardmanagementsystem.di

import com.austi.hospitalwardmanagementsystem.viewmodels.AddPatientViewModel
import com.austi.hospitalwardmanagementsystem.viewmodels.AnalyticsViewModel
import com.austi.hospitalwardmanagementsystem.viewmodels.PatientListViewModel
import com.austi.hospitalwardmanagementsystem.viewmodels.WardViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun providePatientListViewModel(
        patientRepository: com.austi.hospitalwardmanagementsystem.data.PatientRepository
    ): PatientListViewModel {
        return PatientListViewModel(patientRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideAddPatientViewModel(
        patientRepository: com.austi.hospitalwardmanagementsystem.data.PatientRepository
    ): AddPatientViewModel {
        return AddPatientViewModel(patientRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideWardViewModel(
        wardRepository: com.austi.hospitalwardmanagementsystem.data.WardRepository
    ): WardViewModel {
        return WardViewModel(wardRepository)
    }

    // REMOVED the AddWardViewModel provider since we don't have that class

    @Provides
    @ViewModelScoped
    fun provideAnalyticsViewModel(
        patientRepository: com.austi.hospitalwardmanagementsystem.data.PatientRepository,
        wardRepository: com.austi.hospitalwardmanagementsystem.data.WardRepository
    ): AnalyticsViewModel {
        return AnalyticsViewModel(patientRepository, wardRepository)
    }
}
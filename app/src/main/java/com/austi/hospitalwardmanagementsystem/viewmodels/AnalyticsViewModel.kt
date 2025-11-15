package com.austi.hospitalwardmanagementsystem.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.austi.hospitalwardmanagementsystem.data.PatientRepository
import com.austi.hospitalwardmanagementsystem.data.WardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val patientRepository: PatientRepository,
    private val wardRepository: WardRepository
) : ViewModel() {

    private val _analyticsState = MutableStateFlow(AnalyticsState())
    val analyticsState: StateFlow<AnalyticsState> = _analyticsState.asStateFlow()

    init {
        startRealTimeUpdates()
    }

    fun startRealTimeUpdates() {
        viewModelScope.launch {
            // Combine patient and ward data for real-time analytics
            combine(
                patientRepository.getAllPatients(),
                wardRepository.getAllWards()
            ) { patients, wards ->
                calculateAnalytics(patients, wards)
            }.collect { analytics ->
                _analyticsState.value = analytics
            }
        }
    }

    private fun calculateAnalytics(
        patients: List<com.austi.hospitalwardmanagementsystem.data.Patient>,
        wards: List<com.austi.hospitalwardmanagementsystem.data.Ward>
    ): AnalyticsState {
        val admittedPatients = patients.filter { it.status == com.austi.hospitalwardmanagementsystem.data.PatientStatus.ADMITTED }

        val totalPatients = admittedPatients.size
        val availableBeds = wards.sumOf { it.availableBeds }

        val wardStats = wards.map { ward ->
            val occupiedBeds = ward.totalBeds - ward.availableBeds
            val occupancyRate = if (ward.totalBeds > 0) {
                (occupiedBeds.toDouble() / ward.totalBeds.toDouble() * 100).toInt()
            } else {
                0
            }

            WardStat(
                wardNumber = ward.wardNumber,
                wardType = ward.wardType.name,
                totalBeds = ward.totalBeds,
                occupiedBeds = occupiedBeds,
                occupancyRate = occupancyRate
            )
        }

        val averageOccupancy = if (wards.isNotEmpty()) {
            wardStats.map { it.occupancyRate }.average().toInt()
        } else {
            0
        }

        val busiestWard = wardStats.maxByOrNull { it.occupancyRate }?.wardNumber

        return AnalyticsState(
            totalPatients = totalPatients,
            availableBeds = availableBeds,
            averageOccupancy = averageOccupancy,
            busiestWard = busiestWard,
            wardStats = wardStats
        )
    }
}

// Add these data classes INSIDE the same file
data class AnalyticsState(
    val totalPatients: Int = 0,
    val availableBeds: Int = 0,
    val averageOccupancy: Int = 0,
    val busiestWard: String? = null,
    val wardStats: List<WardStat> = emptyList()
)

data class WardStat(
    val wardNumber: String,
    val wardType: String,
    val totalBeds: Int,
    val occupiedBeds: Int,
    val occupancyRate: Int
)
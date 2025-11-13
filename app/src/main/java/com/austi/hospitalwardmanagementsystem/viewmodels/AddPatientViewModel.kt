package com.austi.hospitalwardmanagementsystem.viewmodels

import androidx.lifecycle.ViewModel
import com.austi.hospitalwardmanagementsystem.data.Patient
import com.austi.hospitalwardmanagementsystem.data.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddPatientViewModel @Inject constructor(
    private val patientRepository: PatientRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddPatientUiState())
    val uiState: StateFlow<AddPatientUiState> = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun updateAge(age: String) {
        _uiState.update { it.copy(age = age) }
    }

    fun updateGender(gender: String) {
        _uiState.update { it.copy(gender = gender) }
    }

    fun updateCondition(condition: String) {
        _uiState.update { it.copy(condition = condition) }
    }

    fun updateWard(wardNumber: String) {
        _uiState.update { it.copy(wardNumber = wardNumber) }
    }

    fun updateBed(bedNumber: String) {
        _uiState.update { it.copy(bedNumber = bedNumber) }
    }

    fun updateDoctor(doctorName: String) {
        _uiState.update { it.copy(doctorName = doctorName) }
    }

    fun updateNotes(notes: String) {
        _uiState.update { it.copy(notes = notes) }
    }

    suspend fun addPatient(): Result<Long> {
        return try {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val patient = Patient(
                name = _uiState.value.name,
                age = _uiState.value.age.toIntOrNull() ?: 0,
                gender = _uiState.value.gender,
                condition = _uiState.value.condition,
                wardNumber = _uiState.value.wardNumber,
                bedNumber = _uiState.value.bedNumber.toIntOrNull() ?: 0,
                admissionDate = Date(),
                doctorName = _uiState.value.doctorName,
                notes = _uiState.value.notes
            )

            val result = patientRepository.admitPatient(patient)
            _uiState.update { it.copy(isLoading = false) }
            result
        } catch (e: Exception) {
            _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            Result.failure(e)
        }
    }

    fun resetForm() {
        _uiState.update { AddPatientUiState() }
    }
}

data class AddPatientUiState(
    val name: String = "",
    val age: String = "",
    val gender: String = "Male",
    val condition: String = "",
    val wardNumber: String = "WARD-001",
    val bedNumber: String = "",
    val doctorName: String = "",
    val notes: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
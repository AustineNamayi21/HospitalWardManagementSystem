package com.austi.hospitalwardmanagementsystem.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.austi.hospitalwardmanagementsystem.data.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientListViewModel @Inject constructor(
    private val patientRepository: PatientRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PatientListUiState())
    val uiState: StateFlow<PatientListUiState> = _uiState.asStateFlow()

    init {
        loadPatients()
    }

    fun loadPatients() {
        viewModelScope.launch {
            patientRepository.getAllPatients().collect { patients ->
                _uiState.update { it.copy(patients = patients, isLoading = false) }
            }
        }
    }

    fun deletePatient(patientId: Long) {
        viewModelScope.launch {
            val patient = _uiState.value.patients.find { it.id == patientId }
            patient?.let {
                patientRepository.deletePatient(it)
                // List will automatically update via Flow
            }
        }
    }

    fun toggleFilter() {
        _uiState.update { it.copy(showAdmittedOnly = !it.showAdmittedOnly) }
        loadFilteredPatients()
    }

    private fun loadFilteredPatients() {
        viewModelScope.launch {
            if (_uiState.value.showAdmittedOnly) {
                patientRepository.getAdmittedPatients().collect { patients ->
                    _uiState.update { it.copy(patients = patients) }
                }
            } else {
                loadPatients()
            }
        }
    }
}

data class PatientListUiState(
    val patients: List<com.austi.hospitalwardmanagementsystem.data.Patient> = emptyList(),
    val isLoading: Boolean = false,
    val showAdmittedOnly: Boolean = false,
    val errorMessage: String? = null
)
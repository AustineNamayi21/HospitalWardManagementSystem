package com.austi.hospitalwardmanagementsystem.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.austi.hospitalwardmanagementsystem.data.Ward
import com.austi.hospitalwardmanagementsystem.data.WardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WardViewModel @Inject constructor(
    private val wardRepository: WardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WardUiState())
    val uiState: StateFlow<WardUiState> = _uiState.asStateFlow()

    init {
        loadWards()
    }

    fun loadWards() {
        viewModelScope.launch {
            wardRepository.getAllWards().collect { wards ->
                _uiState.update { it.copy(wards = wards, isLoading = false) }
            }
        }
    }

    suspend fun addWard(ward: Ward): Result<Unit> {
        return try {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = wardRepository.addWard(ward)
            _uiState.update { it.copy(isLoading = false) }
            result
        } catch (e: Exception) {
            _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            Result.failure(e)
        }
    }

    suspend fun updateWard(ward: Ward): Result<Unit> {
        return try {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            wardRepository.updateWard(ward)
            _uiState.update { it.copy(isLoading = false) }
            Result.success(Unit)
        } catch (e: Exception) {
            _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            Result.failure(e)
        }
    }

    suspend fun deleteWard(ward: Ward): Result<Unit> {
        return try {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            wardRepository.deleteWard(ward)
            _uiState.update { it.copy(isLoading = false) }
            Result.success(Unit)
        } catch (e: Exception) {
            _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            Result.failure(e)
        }
    }

    fun getAvailableWards(): List<Ward> {
        return _uiState.value.wards.filter { it.availableBeds > 0 }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

data class WardUiState(
    val wards: List<Ward> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
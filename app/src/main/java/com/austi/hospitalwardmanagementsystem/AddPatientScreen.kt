package com.austi.hospitalwardmanagementsystem

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.austi.hospitalwardmanagementsystem.viewmodels.AddPatientViewModel
import kotlinx.coroutines.launch

@Composable
fun AddPatientScreen(navController: NavHostController) {
    val viewModel: AddPatientViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val scrollState = rememberScrollState()

    // Show success/error messages
    if (uiState.errorMessage != null) {
        LaunchedEffect(uiState.errorMessage) {
            // TODO: Show error snackbar
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Admit New Patient",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Patient Form
        OutlinedTextField(
            value = uiState.name,
            onValueChange = { viewModel.updateName(it) },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.age,
            onValueChange = { viewModel.updateAge(it) },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.gender,
            onValueChange = { viewModel.updateGender(it) },
            label = { Text("Gender") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.condition,
            onValueChange = { viewModel.updateCondition(it) },
            label = { Text("Medical Condition") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.wardNumber,
            onValueChange = { viewModel.updateWard(it) },
            label = { Text("Ward Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.bedNumber,
            onValueChange = { viewModel.updateBed(it) },
            label = { Text("Bed Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.doctorName,
            onValueChange = { viewModel.updateDoctor(it) },
            label = { Text("Doctor Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.notes,
            onValueChange = { viewModel.updateNotes(it) },
            label = { Text("Notes") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = { navController.popBackStack() }
            ) {
                Text("Cancel")
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        val result = viewModel.addPatient()
                        if (result.isSuccess) {
                            navController.popBackStack()
                        }
                        // If there's an error, it will be shown in the UI state
                    }
                },
                enabled = uiState.name.isNotBlank() && uiState.age.isNotBlank() && uiState.condition.isNotBlank()
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Admit Patient")
                }
            }
        }
    }
}
package com.austi.hospitalwardmanagementsystem

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.austi.hospitalwardmanagementsystem.data.Ward
import com.austi.hospitalwardmanagementsystem.data.WardType
import com.austi.hospitalwardmanagementsystem.viewmodels.WardViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWardScreen(navController: NavHostController) {
    val viewModel: WardViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var wardNumber by remember { mutableStateOf("") }
    var wardType by remember { mutableStateOf(WardType.GENERAL) }
    var totalBeds by remember { mutableStateOf("") }
    var availableBeds by remember { mutableStateOf("") }
    var nurseInCharge by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Add New Ward",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = wardNumber,
            onValueChange = { wardNumber = it },
            label = { Text("Ward Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Ward Type Dropdown - SIMPLIFIED VERSION (no experimental APIs)
        Text(
            text = "Ward Type",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        WardType.values().forEach { type ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = wardType == type,
                    onClick = { wardType = type }
                )
                Text(
                    text = type.name,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = totalBeds,
            onValueChange = { totalBeds = it },
            label = { Text("Total Beds") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = availableBeds,
            onValueChange = { availableBeds = it },
            label = { Text("Available Beds") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nurseInCharge,
            onValueChange = { nurseInCharge = it },
            label = { Text("Nurse In Charge") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = contactNumber,
            onValueChange = { contactNumber = it },
            label = { Text("Contact Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(onClick = { navController.popBackStack() }) {
                Text("Cancel")
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        val ward = Ward(
                            wardNumber = wardNumber,
                            wardType = wardType,
                            totalBeds = totalBeds.toIntOrNull() ?: 0,
                            availableBeds = availableBeds.toIntOrNull() ?: 0,
                            nurseInCharge = nurseInCharge,
                            contactNumber = contactNumber
                        )
                        val result = viewModel.addWard(ward)
                        if (result.isSuccess) {
                            navController.popBackStack()
                        }
                    }
                },
                enabled = wardNumber.isNotBlank() && totalBeds.isNotBlank() &&
                        availableBeds.isNotBlank() && nurseInCharge.isNotBlank()
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Add Ward")
                }
            }
        }
    }
}
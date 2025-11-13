package com.austi.hospitalwardmanagementsystem

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.austi.hospitalwardmanagementsystem.data.Patient

@Composable
fun PatientManagementScreen(navController: NavHostController) {
    var patients by remember { mutableStateOf(emptyList<Patient>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Patient Management",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Add Patient Button
        Button(
            onClick = { navController.navigate("addPatient") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Admit New Patient")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (patients.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("No patients admitted yet")
                Text("Click 'Admit New Patient' to add patients")
            }
        } else {
            // Patients List - FIXED: Use items() correctly
            LazyColumn {
                items(patients) { patient ->
                    PatientCard(
                        patient = patient,
                        onDelete = { /* TODO: Implement delete */ }
                    )
                }
            }
        }
    }
}
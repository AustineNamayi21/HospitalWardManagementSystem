package com.austi.hospitalwardmanagementsystem

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.austi.hospitalwardmanagementsystem.data.Patient

@Composable
fun PatientCard(
    patient: Patient,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = patient.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Age: ${patient.age} | ${patient.gender}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Condition: ${patient.condition}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Ward: ${patient.wardNumber} - Bed: ${patient.bedNumber}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Doctor: ${patient.doctorName}",
                    style = MaterialTheme.typography.bodySmall
                )
                if (patient.notes.isNotBlank()) {
                    Text(
                        text = "Notes: ${patient.notes}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Patient",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
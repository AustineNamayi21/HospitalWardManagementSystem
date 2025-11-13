package com.austi.hospitalwardmanagementsystem

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.austi.hospitalwardmanagementsystem.viewmodels.WardViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WardManagementScreen(navController: NavHostController) {
    val viewModel: WardViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ward Management",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("addWard") }
            ) {
                Icon(Icons.Default.Add, "Add Ward")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Overall Statistics
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Hospital Overview", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Total Wards: ${uiState.wards.size}")
                    Text("Total Capacity: ${uiState.wards.sumOf { it.totalBeds }} beds")
                    Text("Current Occupancy: ${uiState.wards.sumOf { it.totalBeds - it.availableBeds }} patients")
                    Text("Available Beds: ${uiState.wards.sumOf { it.availableBeds }}")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Ward Availability",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            when {
                uiState.isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Text("Loading wards...", modifier = Modifier.padding(8.dp))
                    }
                }

                uiState.wards.isEmpty() -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Warning,
                            contentDescription = "No wards",
                            modifier = Modifier.padding(16.dp)
                        )
                        Text("No wards found")
                    }
                }

                else -> {
                    LazyColumn {
                        items(uiState.wards) { ward ->
                            WardCard(
                                ward = ward,
                                onEdit = { /* TODO: Implement edit */ },
                                onDelete = {
                                    coroutineScope.launch {
                                        viewModel.deleteWard(ward)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WardCard(
    ward: com.austi.hospitalwardmanagementsystem.data.Ward,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentOccupancy = ward.totalBeds - ward.availableBeds
    val occupancyRate = (currentOccupancy.toDouble() / ward.totalBeds.toDouble()) * 100
    val cardColor = when {
        occupancyRate >= 90 -> MaterialTheme.colorScheme.errorContainer
        occupancyRate >= 75 -> MaterialTheme.colorScheme.tertiaryContainer
        else -> MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ward ${ward.wardNumber}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Row {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, "Edit Ward", tint = MaterialTheme.colorScheme.primary)
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, "Delete Ward", tint = MaterialTheme.colorScheme.error)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("Type: ${ward.wardType}")
            Text("Capacity: ${ward.totalBeds} beds")
            Text("Occupied: $currentOccupancy beds")
            Text("Available: ${ward.availableBeds} beds")
            Text("Nurse: ${ward.nurseInCharge}")
            Text("Contact: ${ward.contactNumber}")

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = (occupancyRate / 100f).toFloat(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = when {
                    occupancyRate >= 90 -> MaterialTheme.colorScheme.error
                    occupancyRate >= 75 -> MaterialTheme.colorScheme.tertiary
                    else -> MaterialTheme.colorScheme.primary
                }
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Occupancy: ${"%.1f".format(occupancyRate)}%",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
package com.austi.hospitalwardmanagementsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController)
        }
        composable("patients") {
            PatientListScreen(
                onAddPatient = { navController.navigate("addPatient") },
                onViewWards = { navController.navigate("wards") }
            )
        }
        composable("addPatient") {
            AddPatientScreen(navController)
        }
        composable("wards") {
            WardManagementScreen(navController)
        }
        composable("addWard") {
            AddWardScreen(navController)
        }
    }
}

@Preview
@Composable
fun AppNavigationPreview() {
    AppNavigation()
}
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
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.PatientList.route) {
            PatientListScreen(
                onAddPatient = { navController.navigate(Screen.AddPatient.route) },
                onViewWards = { navController.navigate(Screen.WardManagement.route) },
                navController = navController
            )
        }
        composable(Screen.AddPatient.route) {
            AddPatientScreen(navController)
        }
        composable(Screen.WardManagement.route) {
            WardManagementScreen(navController)
        }
        composable(Screen.AddWard.route) {
            AddWardScreen(navController)
        }
        composable(Screen.Analytics.route) { // MOVED THIS OUTSIDE - it was nested!
            AnalyticsScreen(navController)
        }
    }
}

@Preview
@Composable
fun AppNavigationPreview() {
    AppNavigation()
}
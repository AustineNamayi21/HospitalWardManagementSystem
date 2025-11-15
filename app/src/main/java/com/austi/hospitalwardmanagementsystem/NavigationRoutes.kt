package com.austi.hospitalwardmanagementsystem

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object PatientList : Screen("patients")
    object AddPatient : Screen("addPatient")
    object WardManagement : Screen("wards")
    object AddWard : Screen("addWard")
    object Analytics : Screen("analytics") // MAKE SURE THIS LINE EXISTS
}
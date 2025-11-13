package com.austi.hospitalwardmanagementsystem.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "patients")
data class Patient(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val age: Int,
    val gender: String,
    val condition: String,
    val wardNumber: String,
    val bedNumber: Int,
    val admissionDate: Date,
    val dischargeDate: Date? = null,
    val status: PatientStatus = PatientStatus.ADMITTED,
    val doctorName: String,
    val notes: String = ""
)

enum class PatientStatus {
    ADMITTED, DISCHARGED, TRANSFERRED
}

@Entity(tableName = "wards")
data class Ward(
    @PrimaryKey
    val wardNumber: String,
    val wardType: WardType,
    val totalBeds: Int,
    val availableBeds: Int,
    val nurseInCharge: String,
    val contactNumber: String
)

enum class WardType {
    GENERAL, ICU, PEDIATRICS, MATERNITY, SURGICAL, CARDIOLOGY, EMERGENCY
}
package com.austi.hospitalwardmanagementsystem.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface PatientDao {

    @Query("SELECT * FROM patients ORDER BY admissionDate DESC")
    fun getAllPatients(): Flow<List<Patient>>

    @Query("SELECT * FROM patients WHERE id = :patientId")
    fun getPatientById(patientId: Long): Flow<Patient?>

    @Query("SELECT * FROM patients WHERE wardNumber = :wardNumber")
    fun getPatientsByWard(wardNumber: String): Flow<List<Patient>>

    @Query("SELECT * FROM patients WHERE status = 'ADMITTED'")
    fun getAdmittedPatients(): Flow<List<Patient>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient: Patient): Long

    @Update
    suspend fun updatePatient(patient: Patient)

    @Delete
    suspend fun deletePatient(patient: Patient)

    @Query("UPDATE patients SET status = 'DISCHARGED', dischargeDate = :dischargeDate WHERE id = :patientId")
    suspend fun dischargePatient(patientId: Long, dischargeDate: Date)
}
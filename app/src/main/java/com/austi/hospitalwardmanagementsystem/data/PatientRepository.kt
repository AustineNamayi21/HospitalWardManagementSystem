package com.austi.hospitalwardmanagementsystem.data

import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class PatientRepository @Inject constructor(
    private val patientDao: PatientDao,
    private val wardDao: WardDao
) {

    fun getAllPatients(): Flow<List<Patient>> = patientDao.getAllPatients()

    fun getPatientById(patientId: Long): Flow<Patient?> = patientDao.getPatientById(patientId)

    fun getPatientsByWard(wardNumber: String): Flow<List<Patient>> =
        patientDao.getPatientsByWard(wardNumber)

    fun getAdmittedPatients(): Flow<List<Patient>> = patientDao.getAdmittedPatients()

    suspend fun admitPatient(patient: Patient): Result<Long> {
        return try {
            val patientId = patientDao.insertPatient(patient)
            wardDao.occupyBed(patient.wardNumber)
            Result.success(patientId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updatePatient(patient: Patient): Result<Unit> {
        return try {
            patientDao.updatePatient(patient)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun dischargePatient(patientId: Long, dischargeDate: Date): Result<Unit> {
        return try {
            patientDao.dischargePatient(patientId, dischargeDate)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deletePatient(patient: Patient): Result<Unit> {
        return try {
            patientDao.deletePatient(patient)
            if (patient.status == PatientStatus.ADMITTED) {
                wardDao.freeBed(patient.wardNumber)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
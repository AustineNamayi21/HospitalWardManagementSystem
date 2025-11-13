package com.austi.hospitalwardmanagementsystem.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WardRepository @Inject constructor(
    private val wardDao: WardDao
) {
    fun getAllWards(): Flow<List<Ward>> = wardDao.getAllWards()

    fun getWardByNumber(wardNumber: String): Flow<Ward?> = wardDao.getWardByNumber(wardNumber)

    fun getAvailableWards(): Flow<List<Ward>> = wardDao.getAvailableWards()

    suspend fun insertWard(ward: Ward) = wardDao.insertWard(ward)

    suspend fun updateWard(ward: Ward) = wardDao.updateWard(ward)

    suspend fun deleteWard(ward: Ward) = wardDao.deleteWard(ward)

    suspend fun addWard(ward: Ward): Result<Unit> {
        return try {
            wardDao.insertWard(ward)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
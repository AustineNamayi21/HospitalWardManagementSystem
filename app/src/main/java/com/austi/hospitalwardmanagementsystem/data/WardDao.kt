package com.austi.hospitalwardmanagementsystem.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WardDao {

    @Query("SELECT * FROM wards ORDER BY wardNumber")
    fun getAllWards(): Flow<List<Ward>>

    @Query("SELECT * FROM wards WHERE wardNumber = :wardNumber")
    fun getWardByNumber(wardNumber: String): Flow<Ward?>

    @Query("SELECT * FROM wards WHERE availableBeds > 0")
    fun getAvailableWards(): Flow<List<Ward>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWard(ward: Ward)

    @Update
    suspend fun updateWard(ward: Ward)

    @Delete
    suspend fun deleteWard(ward: Ward)

    @Query("UPDATE wards SET availableBeds = availableBeds - 1 WHERE wardNumber = :wardNumber AND availableBeds > 0")
    suspend fun occupyBed(wardNumber: String)

    @Query("UPDATE wards SET availableBeds = availableBeds + 1 WHERE wardNumber = :wardNumber AND availableBeds < totalBeds")
    suspend fun freeBed(wardNumber: String)
}
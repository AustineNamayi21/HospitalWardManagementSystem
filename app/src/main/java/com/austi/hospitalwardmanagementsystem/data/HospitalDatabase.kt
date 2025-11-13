package com.austi.hospitalwardmanagementsystem.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context

@Database(
    entities = [Patient::class, Ward::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)  // ADD THIS LINE
abstract class HospitalDatabase : RoomDatabase() {

    abstract fun patientDao(): PatientDao
    abstract fun wardDao(): WardDao

    companion object {
        @Volatile
        private var Instance: HospitalDatabase? = null

        fun getDatabase(context: Context): HospitalDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    HospitalDatabase::class.java,
                    "hospital_database"
                ).build().also { Instance = it }
            }
        }
    }
}
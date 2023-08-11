package com.krubo.apmlib.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.krubo.apmlib.KApm
import com.krubo.apmlib.db.dao.KLooperPrinter
import com.krubo.apmlib.db.dao.KLooperPrinterDao

@Database(entities = [KLooperPrinter::class], version = 1)
abstract class KDatabase : RoomDatabase() {
    abstract fun looperPrinterDao(): KLooperPrinterDao

    companion object {
        private const val DB_NAME = "k_apm.db"

        @Volatile
        private var INSTANCE: KDatabase? = null

        @Synchronized
        fun getInstance(): KDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    KApm.context, KDatabase::class.java, DB_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
package com.krubo.apmlib.db.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import com.krubo.apmlib.db.KDatabase

@Entity(tableName = "k_looper_printer")
data class KLooperPrinter(
    @PrimaryKey var datetime: Long,
    @ColumnInfo var interval: Long,
    @ColumnInfo(typeAffinity = ColumnInfo.TEXT) var content: String
)

@Dao
interface KLooperPrinterDao {
    @Query("select * from k_looper_printer where datetime=:dateTime")
    fun queryItem(dateTime: Long): List<KLooperPrinter>

    @Insert
    fun insertItem(printer: KLooperPrinter)

    @Update
    fun updateItem(printer: KLooperPrinter)
}

object KLooperPrinterHelper {
    fun insertItem(printer: KLooperPrinter) {
        KDatabase.getInstance().looperPrinterDao().insertItem(printer)
    }

    fun updateItem(dateTime: Long, interval: Long) {
        val dao = KDatabase.getInstance().looperPrinterDao()
        val list = dao.queryItem(dateTime)
        if (list.isEmpty()) {
            return
        }
        val item = list[0]
        item.interval = interval
        dao.updateItem(item)
    }
}
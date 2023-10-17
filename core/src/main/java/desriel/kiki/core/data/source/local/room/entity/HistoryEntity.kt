package desriel.kiki.core.data.source.local.room.entity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "history_table")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val orderTime : String,
    val finishTime  : String,
    val orderType : String,
    val pickLocation : String,
    val destinationLocation : String,
    val description: String,
    val tariff : String
)


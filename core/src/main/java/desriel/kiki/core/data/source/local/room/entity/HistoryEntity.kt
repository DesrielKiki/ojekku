package desriel.kiki.core.data.source.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userEmail: String,
    val orderTime: String,
    val finishTime: String,
    val orderType: String,
    val pickLocation: String,
    val destinationLocation: String,
    val description: String,
    val tariff: String
)


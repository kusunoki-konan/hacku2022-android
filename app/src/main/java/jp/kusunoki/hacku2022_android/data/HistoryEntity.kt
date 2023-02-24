package jp.kusunoki.hacku2022_android.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "history_table")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val videoId: String,
    val title: String,
    val thumbnailPath: String,
    val watchDate: Date
)
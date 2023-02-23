package jp.kusunoki.hacku2022_android

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String = "",
    val created_at: Date = Date()
)

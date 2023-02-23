package jp.kusunoki.hacku2022_android

import androidx.room.*

@Dao
interface TodoDao {
    @Query("select * from todos order by created_at asc")
    fun getAll(): MutableList<Todo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun post(todo: Todo)

    @Delete
    fun delete(todo: Todo)
}
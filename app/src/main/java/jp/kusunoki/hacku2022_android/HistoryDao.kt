package jp.kusunoki.hacku2022_android

import androidx.room.*

@Dao
interface HistoryDao {
    //　参照　すべてのデータをwatchDateの大きいもの（新しい順）に並べる
    @Query("select * from history_table order by watchDate DESC")
    fun getAllHistory(): List<HistoryEntity>

    //挿入　すべてのデータを挿入する
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(history: HistoryEntity)

    //アップデート　データベース テーブルの特定の行を更新する
    @Update
    fun update(history: HistoryEntity)

    //　削除　データベース テーブルの特定の行を削除する
    @Query("DELETE FROM history_table")
    suspend fun deleteAll()
}
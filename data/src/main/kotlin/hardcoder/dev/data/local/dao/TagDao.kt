package hardcoder.dev.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hardcoder.dev.data.local.entities.TagLocal

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tagLocal: TagLocal)

    @Query("DELETE FROM tags")
    suspend fun deleteAllTags()
}
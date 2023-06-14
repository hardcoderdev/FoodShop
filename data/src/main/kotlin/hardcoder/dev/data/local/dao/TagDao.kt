package hardcoder.dev.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hardcoder.dev.data.local.entities.TagLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tagLocal: TagLocal)

    @Query("SELECT * FROM tags")
    fun getAllTags(): Flow<List<TagLocal>>

    @Query("DELETE FROM tags")
    suspend fun deleteAllTags()
}
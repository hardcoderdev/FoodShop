package hardcoder.dev.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CleanerDao {

    @Query("DELETE FROM dishes")
    suspend fun deleteAllDishes()

    @Query("DELETE FROM tags")
    suspend fun deleteAllTags()

    @Query("DELETE FROM dishes_with_tags")
    suspend fun deleteAllDishesWithTags()

    @Transaction
    suspend fun deleteAll() {
        deleteAllDishes()
        deleteAllTags()
        deleteAllDishesWithTags()
    }
}
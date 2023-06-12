package hardcoder.dev.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import hardcoder.dev.data.local.entities.DishTagCrossRefLocal
import hardcoder.dev.data.local.entities.DishWithTagsLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface DishWithTagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dishWithTagCrossRefLocal: DishTagCrossRefLocal)

    @Transaction
    @Query("SELECT * FROM dishes")
    fun getAllDishesWithTags(): Flow<List<DishWithTagsLocal>>
}
package hardcoder.dev.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hardcoder.dev.data.local.entities.DishLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface DishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dishLocal: DishLocal)

    @Query("DELETE FROM dishes WHERE dish_id = :dishId")
    suspend fun delete(dishId: Int)

    @Query("DELETE FROM dishes")
    suspend fun deleteAllDishes()

    @Query("SELECT * FROM dishes")
    fun getAllDishes(): Flow<List<DishLocal>>
}
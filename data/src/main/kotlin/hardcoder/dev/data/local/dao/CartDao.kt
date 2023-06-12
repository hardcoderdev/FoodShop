package hardcoder.dev.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hardcoder.dev.data.local.entities.CartLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartLocal: CartLocal)

    @Query("DELETE FROM cart WHERE dish_id = :itemId")
    suspend fun delete(itemId: Int): Int

    @Query("SELECT * FROM cart")
    fun getAllCartItems(): Flow<List<CartLocal>>
}
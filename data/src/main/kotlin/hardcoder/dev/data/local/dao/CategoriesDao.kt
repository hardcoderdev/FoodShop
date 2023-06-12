package hardcoder.dev.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hardcoder.dev.data.local.entities.CategoryLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<CategoryLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categoryLocalList: List<CategoryLocal>)

    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()
}
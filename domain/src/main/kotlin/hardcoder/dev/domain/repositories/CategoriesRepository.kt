package hardcoder.dev.domain.repositories

import hardcoder.dev.domain.entities.Category
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {

    fun categoriesFlow(): Flow<List<Category>>

    suspend fun refreshCategories()
}
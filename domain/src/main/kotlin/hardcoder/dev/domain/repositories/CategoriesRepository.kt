package hardcoder.dev.domain.repositories

import hardcoder.dev.domain.entities.Category

interface CategoriesRepository {

    suspend fun getAllCategories(): List<Category>
}
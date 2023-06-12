package hardcoder.dev.data.repositories

import hardcoder.dev.data.remote.FoodAPI
import hardcoder.dev.data.remote.entities.CategoryRemote
import hardcoder.dev.domain.entities.Category
import hardcoder.dev.domain.repositories.CategoriesRepository

class CategoriesRepositoryImpl(private val foodAPI: FoodAPI) : CategoriesRepository {

    override suspend fun getAllCategories() =
        foodAPI.getAllCategories().categories.map(CategoryRemote::toDomain)
}

private fun CategoryRemote.toDomain() = Category(
    id = id,
    name = name,
    imageUrl = imageUrl
)
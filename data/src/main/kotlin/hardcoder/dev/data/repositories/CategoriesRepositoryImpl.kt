package hardcoder.dev.data.repositories

import androidx.room.withTransaction
import hardcoder.dev.coroutines.mapItems
import hardcoder.dev.data.local.AppDatabase
import hardcoder.dev.data.local.entities.CategoryLocal
import hardcoder.dev.data.remote.FoodAPI
import hardcoder.dev.data.remote.entities.CategoryRemote
import hardcoder.dev.domain.entities.Category
import hardcoder.dev.domain.repositories.CategoriesRepository

class CategoriesRepositoryImpl(
    private val foodAPI: FoodAPI,
    private val appDatabase: AppDatabase
) : CategoriesRepository {

    override suspend fun categoriesFlow() = appDatabase.categoriesDao()
        .getAllCategories()
        .mapItems(CategoryLocal::toDomain)

    override suspend fun refreshCategories() {
        foodAPI.getAllCategories().categories.let { categoriesList ->
            with(appDatabase) {
                withTransaction {
                    categoriesDao().insert(categoriesList.map(CategoryRemote::toLocal))
                }
            }
        }
    }
}

private fun CategoryRemote.toLocal() = CategoryLocal(
    id = id,
    name = name,
    imageUrl = imageUrl
)

private fun CategoryLocal.toDomain() = Category(
    id = id,
    name = name,
    imageUrl = imageUrl
)
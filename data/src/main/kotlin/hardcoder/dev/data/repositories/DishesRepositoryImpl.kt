package hardcoder.dev.data.repositories

import androidx.room.withTransaction
import hardcoder.dev.data.local.AppDatabase
import hardcoder.dev.data.local.entities.DishLocal
import hardcoder.dev.data.local.entities.DishTagCrossRefLocal
import hardcoder.dev.data.local.entities.TagLocal
import hardcoder.dev.data.remote.FoodAPI
import hardcoder.dev.data.remote.entities.DishRemote
import hardcoder.dev.domain.entities.Dish
import hardcoder.dev.domain.entities.Tag
import hardcoder.dev.domain.repositories.DishesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DishesRepositoryImpl(
    private val foodAPI: FoodAPI,
    private val appDatabase: AppDatabase
) : DishesRepository {

    override suspend fun dishesFlowByTag(tag: Tag): Flow<List<Dish>> {
        return appDatabase.dishWithTagDao().getAllDishesWithTags().map {
            it.filter { dish ->
                tag.toLocal() in dish.tagList
            }.map { dishWithTag ->
                dishWithTag.dishLocal.toDomain()
            }
        }
    }

    override suspend fun refreshDishes() {
        foodAPI.getAllDishes().dishes.let { dishes ->
            dishes.forEach { dishRemote ->
                val dishLocal = dishRemote.toLocal()
                val tagList = dishRemote.tagList.map { TagLocal(name = it) }

                with(appDatabase) {
                    withTransaction {
                        dishDao().insert(dishLocal)

                        tagList.forEach { tag ->
                            tagDao().insert(tag)

                            dishWithTagDao().insert(
                                DishTagCrossRefLocal(
                                    dishId = dishLocal.id,
                                    tagName = tag.name
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun Tag.toLocal() = TagLocal(name = name)

private fun DishLocal.toDomain() = Dish(
    id = id,
    name = name,
    description = description,
    imageUrl = imageUrl,
    weight = weight,
    price = price
)

private fun DishRemote.toLocal() = DishLocal(
    id = id,
    name = name,
    description = description,
    imageUrl = imageUrl,
    weight = weight,
    price = price
)
package hardcoder.dev.data.repositories

import androidx.room.withTransaction
import hardcoder.dev.coroutines.mapItems
import hardcoder.dev.data.local.AppDatabase
import hardcoder.dev.data.local.entities.DishLocal
import hardcoder.dev.data.local.entities.DishTagCrossRefLocal
import hardcoder.dev.data.local.entities.TagLocal
import hardcoder.dev.data.remote.FoodAPI
import hardcoder.dev.data.remote.entities.DishRemote
import hardcoder.dev.domain.entities.Dish
import hardcoder.dev.domain.entities.Tag
import hardcoder.dev.domain.repositories.DishesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DishesRepositoryImpl(
    private val foodAPI: FoodAPI,
    private val appDatabase: AppDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : DishesRepository {

    private val dishDao = appDatabase.dishDao
    private val tagDao = appDatabase.tagDao
    private val dishWithTagDao = appDatabase.dishWithTagDao

    override fun dishesFlowByTag(tag: Tag): Flow<List<Dish>> {
        return dishWithTagDao.getAllDishesWithTags().map {
            it.filter { dish ->
                tag.toLocal() in dish.tagList
            }.map { dishWithTag ->
                dishWithTag.dishLocal.toDomain()
            }
        }.flowOn(ioDispatcher)
    }

    override fun tagsFlow() = tagDao.getAllTags()
        .mapItems(TagLocal::toDomain)
        .flowOn(ioDispatcher)

    override suspend fun refreshDishes() = withContext(ioDispatcher) {
        foodAPI.getAllDishes().dishes.let { dishes ->
            dishes.forEach { dishRemote ->
                val dishLocal = dishRemote.toLocal()
                val tagList = dishRemote.tagList.map { TagLocal(name = it) }

                appDatabase.withTransaction {
                    dishDao.deleteAllDishes()
                    dishDao.insert(dishLocal)

                    tagList.forEach { tag ->
                        tagDao.deleteAllTags()
                        tagDao.insert(tag)

                        dishWithTagDao.deleteAllDishesWithTags()
                        dishWithTagDao.insert(
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

private fun Tag.toLocal() = TagLocal(name = name)
private fun TagLocal.toDomain() = Tag(name = name)

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
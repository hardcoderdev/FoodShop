package hardcoder.dev.domain.repositories

import hardcoder.dev.domain.entities.Dish
import hardcoder.dev.domain.entities.Tag
import kotlinx.coroutines.flow.Flow

interface DishesRepository {

    fun dishesFlowByTag(tag: Tag): Flow<List<Dish>>

    fun tagsFlow(): Flow<List<Tag>>

    suspend fun refreshDishes()
}
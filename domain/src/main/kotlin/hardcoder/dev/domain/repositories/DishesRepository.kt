package hardcoder.dev.domain.repositories

import hardcoder.dev.domain.entities.Dish
import hardcoder.dev.domain.entities.Tag
import kotlinx.coroutines.flow.Flow

interface DishesRepository {

    fun dishesFlowByTag(tag: Tag): Flow<List<Dish>>

    fun getDishById(dishId: Int): Flow<Dish>

    fun tagsFlow(): Flow<List<Tag>>

    suspend fun refreshDishes()
}
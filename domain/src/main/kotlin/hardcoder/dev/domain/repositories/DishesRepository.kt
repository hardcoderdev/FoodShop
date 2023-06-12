package hardcoder.dev.domain.repositories

import hardcoder.dev.domain.entities.Dish
import hardcoder.dev.domain.entities.Tag
import kotlinx.coroutines.flow.Flow

interface DishesRepository {

    suspend fun dishesFlowByTag(tag: Tag): Flow<List<Dish>>

    suspend fun refreshDishes()
}
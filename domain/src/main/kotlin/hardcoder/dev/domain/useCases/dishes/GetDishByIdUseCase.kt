package hardcoder.dev.domain.useCases.dishes

import hardcoder.dev.domain.entities.Dish
import hardcoder.dev.domain.repositories.DishesRepository
import kotlinx.coroutines.flow.Flow

class GetDishByIdUseCase(private val dishesRepository: DishesRepository) {

    operator fun invoke(dishInt: Int): Flow<Dish> {
        return dishesRepository.getDishById(dishInt)
    }
}
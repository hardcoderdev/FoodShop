package hardcoder.dev.domain.useCases.dishes

import hardcoder.dev.domain.entities.Dish
import hardcoder.dev.domain.entities.Tag
import hardcoder.dev.domain.repositories.DishesRepository
import kotlinx.coroutines.flow.Flow

class GetAllDishesByTagUseCase(private val dishesRepository: DishesRepository) {

    operator fun invoke(tag: Tag): Flow<List<Dish>> {
        return dishesRepository.dishesFlowByTag(tag)
    }
}
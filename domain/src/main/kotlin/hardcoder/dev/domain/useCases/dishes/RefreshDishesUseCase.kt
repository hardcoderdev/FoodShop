package hardcoder.dev.domain.useCases.dishes

import hardcoder.dev.domain.repositories.DishesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RefreshDishesUseCase(
    private val dishesRepository: DishesRepository,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke() = withContext(ioDispatcher) {
        dishesRepository.refreshDishes()
    }
}
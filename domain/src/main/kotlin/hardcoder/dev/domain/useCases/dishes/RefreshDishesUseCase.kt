package hardcoder.dev.domain.useCases.dishes

import hardcoder.dev.domain.repositories.DishesRepository

class RefreshDishesUseCase(private val dishesRepository: DishesRepository) {
    suspend operator fun invoke() {
        dishesRepository.refreshDishes()
    }
}
package hardcoder.dev.domain.useCases

import hardcoder.dev.domain.entities.Tag
import hardcoder.dev.domain.repositories.DishesRepository
import kotlinx.coroutines.flow.Flow

class GetAllTagsUseCase(private val dishesRepository: DishesRepository) {

    operator fun invoke(): Flow<List<Tag>> {
        return dishesRepository.tagsFlow()
    }
}
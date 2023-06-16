package hardcoder.dev.domain.useCases.categories

import hardcoder.dev.domain.entities.Category
import hardcoder.dev.domain.repositories.CategoriesRepository
import kotlinx.coroutines.flow.Flow

class GetAllCategoriesUseCase(private val categoriesRepository: CategoriesRepository) {

    operator fun invoke(): Flow<List<Category>> {
        return categoriesRepository.categoriesFlow()
    }
}
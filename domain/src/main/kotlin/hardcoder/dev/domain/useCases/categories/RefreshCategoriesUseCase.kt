package hardcoder.dev.domain.useCases.categories

import hardcoder.dev.domain.repositories.CategoriesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RefreshCategoriesUseCase(
    private val categoriesRepository: CategoriesRepository,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke() = withContext(ioDispatcher) {
        categoriesRepository.refreshCategories()
    }
}
package hardcoder.dev.domain.useCases.categories

import hardcoder.dev.domain.repositories.CategoriesRepository

class RefreshCategoriesUseCase(private val categoriesRepository: CategoriesRepository) {
    suspend operator fun invoke() {
        categoriesRepository.refreshCategories()
    }
}
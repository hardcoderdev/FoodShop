package hardcoder.dev.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hardcoder.dev.coroutines.mapItems
import hardcoder.dev.domain.entities.Category
import hardcoder.dev.domain.useCases.categories.GetAllCategoriesUseCase
import hardcoder.dev.domain.useCases.categories.RefreshCategoriesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoriesViewModel(
    getAllCategoriesUseCase: GetAllCategoriesUseCase,
    refreshCategoriesUseCase: RefreshCategoriesUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            refreshCategoriesUseCase()
        }
    }

    val categories = getAllCategoriesUseCase().mapItems {
        it.toUICategory()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )
}

private fun Category.toUICategory() = ItemCategory(
    title = name,
    imageUrl = imageUrl
)

data class ItemCategory(
    val title: String,
    val imageUrl: String
)

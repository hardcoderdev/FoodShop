package hardcoder.dev.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hardcoder.dev.coroutines.mapItems
import hardcoder.dev.domain.entities.Category
import hardcoder.dev.domain.entities.Dish
import hardcoder.dev.domain.useCases.categories.GetAllCategoriesUseCase
import hardcoder.dev.domain.useCases.categories.RefreshCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.Exception

class CategoriesViewModel(
    getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val refreshCategoriesUseCase: RefreshCategoriesUseCase
) : ViewModel() {

    private val refreshState = MutableStateFlow<RefreshState>(RefreshState.Loading)

    val categories = combine(
        refreshState,
        getAllCategoriesUseCase()
    ) { refreshState, categories ->
        LoadingState.Loaded(
            refreshState = refreshState,
            categories = categories
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = LoadingState.Loading
    )

    fun refreshCategories() {
        viewModelScope.launch {
            try {
                refreshState.value = RefreshState.Loading
                refreshCategoriesUseCase()
                refreshState.value = RefreshState.Loaded
            } catch (e: Exception) {
                refreshState.value = RefreshState.Error(e)
            }
        }
    }

    init {
        refreshCategories()
    }

    sealed class RefreshState {
        object Loading : RefreshState()
        object Loaded : RefreshState()
        data class Error(val exception: Exception) : RefreshState()
    }

    sealed class LoadingState {
        object Loading : LoadingState()
        data class Loaded(
            val refreshState: RefreshState,
            val categories: List<Category>
        ) : LoadingState()
    }
}
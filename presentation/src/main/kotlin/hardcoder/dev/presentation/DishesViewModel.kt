package hardcoder.dev.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hardcoder.dev.domain.entities.Category
import hardcoder.dev.domain.entities.Dish
import hardcoder.dev.domain.entities.Tag
import hardcoder.dev.domain.useCases.dishes.GetAllDishesByTagUseCase
import hardcoder.dev.domain.useCases.dishes.GetAllTagsUseCase
import hardcoder.dev.domain.useCases.dishes.RefreshDishesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.Exception

@OptIn(ExperimentalCoroutinesApi::class)
class DishesViewModel(
    getAllTagsUseCase: GetAllTagsUseCase,
    private val refreshDishesUseCase: RefreshDishesUseCase,
    private val getAllDishesByTagUseCase: GetAllDishesByTagUseCase,
) : ViewModel() {

    private val refreshState = MutableStateFlow<RefreshState>(RefreshState.Loading)

    val sortedTagList = getAllTagsUseCase().map {
        it.sortedBy { tag -> tag.name }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    private var _selectedTag = MutableStateFlow<Tag?>(null)
    val selectedTag = _selectedTag.asStateFlow()

    val dishesList = combine(
        refreshState,
        _selectedTag.flatMapLatest {
            if (it == null) flowOf(emptyList())
            else getAllDishesByTagUseCase(it)
        }
    ) { refreshState, dishes ->
        LoadingState.Loaded(refreshState = refreshState, dishes = dishes)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = LoadingState.Loading
    )

    fun refreshDishes() {
        viewModelScope.launch {
            try {
                refreshState.value = RefreshState.Loading
                refreshDishesUseCase()
                _selectedTag.value = sortedTagList.first().first()
                refreshState.value = RefreshState.Loaded
            } catch (e: Exception) {
                refreshState.value = RefreshState.Error(e)
            }
        }
    }

    fun filterByTag(tag: Tag) {
        _selectedTag.value = tag
    }

    init {
        refreshDishes()
    }

    sealed class RefreshState {
        object Loading : RefreshState()
        object Loaded : RefreshState()
        data class Error(val exception: Exception) : RefreshState()
    }

    sealed class LoadingState {
        object Loading : LoadingState()
        data class Loaded(val refreshState: RefreshState, val dishes: List<Dish>) : LoadingState()
    }
}

package hardcoder.dev.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hardcoder.dev.domain.entities.Tag
import hardcoder.dev.domain.useCases.GetAllDishesByTagUseCase
import hardcoder.dev.domain.useCases.GetAllTagsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class DishesViewModel(
    getAllTagsUseCase: GetAllTagsUseCase,
    private val getAllDishesByTagUseCase: GetAllDishesByTagUseCase,
) : ViewModel() {

    val tagList = getAllTagsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    private var _selectedTag = MutableStateFlow<Tag?>(null)
    val selectedTag = _selectedTag.asStateFlow()

    val dishesList = _selectedTag.flatMapLatest {
        if (it == null) flowOf(emptyList())
        else getAllDishesByTagUseCase(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    init {
        viewModelScope.launch {
            _selectedTag.value = tagList.first().first()
        }
    }

    fun filterByTag(tag: Tag) {
        _selectedTag.value = tag
    }
}
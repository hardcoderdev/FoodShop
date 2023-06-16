package hardcoder.dev.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hardcoder.dev.coroutines.mapItems
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

@OptIn(ExperimentalCoroutinesApi::class)
class DishesViewModel(
    refreshDishesUseCase: RefreshDishesUseCase,
    getAllTagsUseCase: GetAllTagsUseCase,
    private val getAllDishesByTagUseCase: GetAllDishesByTagUseCase,
) : ViewModel() {

    private val sortedTagList = getAllTagsUseCase().map {
        it.sortedBy { tag -> tag.name }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    private var _selectedTag = MutableStateFlow<Tag?>(null)
    val selectedTag = _selectedTag.asStateFlow()

    val resultTagList = combine(
        sortedTagList,
        selectedTag
    ) { tagList, selectedTag ->
        tagList.map { it.toUI(it == selectedTag) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    val dishesList = _selectedTag.flatMapLatest {
        if (it == null) flowOf(emptyList())
        else getAllDishesByTagUseCase(it).mapItems { dish ->
            dish.toUIDish()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    init {
        viewModelScope.launch {
            refreshDishesUseCase()
            _selectedTag.value = sortedTagList.first().first()
        }
    }

    fun filterByTag(itemTag: ItemTag) {
        _selectedTag.value = itemTag.toDomain()
    }
}

private fun Dish.toUIDish() = ItemDish(
    id = id,
    title = name,
    description = description,
    imageUrl = imageUrl,
    weight = weight,
    price = price
)

private fun Tag.toUI(isSelected: Boolean) = ItemTag(
    title = name,
    isSelected = isSelected
)

private fun ItemTag.toDomain() = Tag(
    name = title
)

data class ItemDish(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val weight: Int,
    val price: Int
)

data class ItemTag(
    val title: String,
    var isSelected: Boolean
)

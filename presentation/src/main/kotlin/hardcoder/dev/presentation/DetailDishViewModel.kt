package hardcoder.dev.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hardcoder.dev.domain.entities.CartItem
import hardcoder.dev.domain.entities.Dish
import hardcoder.dev.domain.useCases.cart.PutItemInCartUseCase
import hardcoder.dev.domain.useCases.dishes.GetDishByIdUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailDishViewModel(
    dishId: Int,
    getDishByIdUseCase: GetDishByIdUseCase,
    private val putItemInCartUseCase: PutItemInCartUseCase
) : ViewModel() {

    val dish = getDishByIdUseCase(dishId).map {
        LoadingState.Loaded(dish = it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = LoadingState.Loading
    )

    fun putToCart() {
        viewModelScope.launch {
            val loadedState = dish.value as LoadingState.Loaded
            putItemInCartUseCase(loadedState.dish.toCartItem())
        }
    }

    private fun Dish.toCartItem() = CartItem(
        dishId = id,
        quantity = 1
    )

    sealed class LoadingState {
        object Loading : LoadingState()
        data class Loaded(val dish: Dish) : LoadingState()
    }
}
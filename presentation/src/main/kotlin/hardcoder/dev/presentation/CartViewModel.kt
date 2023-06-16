package hardcoder.dev.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hardcoder.dev.domain.entities.CartItem
import hardcoder.dev.domain.useCases.cart.DecrementCartItemUseCase
import hardcoder.dev.domain.useCases.cart.DishCartItem
import hardcoder.dev.domain.useCases.cart.GetAllCartItemsUseCase
import hardcoder.dev.domain.useCases.cart.IncrementCartItemUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(
    private val incrementCartItemUseCase: IncrementCartItemUseCase,
    private val decrementCartItemUseCase: DecrementCartItemUseCase,
    getAllCartItemsUseCase: GetAllCartItemsUseCase,
) : ViewModel() {

    val cartItems = getAllCartItemsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    val totalCartSum = cartItems.map { cartItemsList ->
        cartItemsList.sumOf { it.dish.price * if (it.quantity == 0) 1 else it.quantity }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = 0
    )

    fun incrementCartItem(dishCartItem: DishCartItem) {
        viewModelScope.launch {
            incrementCartItemUseCase(dishCartItem.toCartItem())
        }
    }

    fun decrementCartItem(dishCartItem: DishCartItem) {
        viewModelScope.launch {
            decrementCartItemUseCase(dishCartItem.toCartItem())
        }
    }
}

private fun DishCartItem.toCartItem() = CartItem(
    dishId = dish.id,
    quantity = quantity
)
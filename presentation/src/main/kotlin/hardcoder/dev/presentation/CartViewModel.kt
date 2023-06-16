package hardcoder.dev.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hardcoder.dev.domain.entities.CartItem
import hardcoder.dev.domain.entities.Dish
import hardcoder.dev.domain.useCases.cart.DecrementCartItemUseCase
import hardcoder.dev.domain.useCases.cart.GetAllCartItemsUseCase
import hardcoder.dev.domain.useCases.dishes.GetDishByIdUseCase
import hardcoder.dev.domain.useCases.cart.IncrementCartItemUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModel(
    private val getDishByIdUseCase: GetDishByIdUseCase,
    private val incrementCartItemUseCase: IncrementCartItemUseCase,
    private val decrementCartItemUseCase: DecrementCartItemUseCase,
    getAllCartItemsUseCase: GetAllCartItemsUseCase,
) : ViewModel() {

    val cartItems = getAllCartItemsUseCase().flatMapLatest { cartItemsList ->
        if (cartItemsList.isEmpty()) flowOf(emptyList())
        else combine(
            cartItemsList.map { cartItem ->
                getDishByIdUseCase(cartItem.dishId).map {
                    it.toDishCart(cartItem.quantity)
                }
            }
        ) {
            it.toList()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    val totalCartSum = cartItems.map { cartItemsList ->
        cartItemsList.sumOf { it.price * if (it.quantity == 0) 1 else it.quantity }
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

data class DishCartItem(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val weight: Int,
    val price: Int,
    val quantity: Int
)

private fun Dish.toDishCart(quantity: Int) = DishCartItem(
    id, name, description, imageUrl, weight, price, quantity
)

private fun DishCartItem.toCartItem() = CartItem(
    dishId = id,
    quantity = quantity
)
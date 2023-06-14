package hardcoder.dev.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hardcoder.dev.domain.entities.CartItem
import hardcoder.dev.domain.useCases.DecrementCartItemUseCase
import hardcoder.dev.domain.useCases.GetAllCartItemsUseCase
import hardcoder.dev.domain.useCases.IncrementCartItemUseCase
import kotlinx.coroutines.flow.SharingStarted
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

    fun putInCart(cartItem: CartItem) {
        viewModelScope.launch {
            incrementCartItemUseCase(cartItem)
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            decrementCartItemUseCase(cartItem)
        }
    }
}
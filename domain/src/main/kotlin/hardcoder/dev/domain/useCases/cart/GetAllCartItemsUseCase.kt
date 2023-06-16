package hardcoder.dev.domain.useCases.cart

import hardcoder.dev.domain.entities.Dish
import hardcoder.dev.domain.repositories.CartRepository
import hardcoder.dev.domain.useCases.dishes.GetDishByIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class GetAllCartItemsUseCase(
    private val getDishByIdUseCase: GetDishByIdUseCase,
    private val cartRepository: CartRepository
) {
    operator fun invoke(): Flow<List<DishCartItem>> {
        return cartRepository.getAllItemsInCart().flatMapLatest { cartItemsList ->
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
        }
    }
}

data class DishCartItem(
    val dish: Dish,
    val quantity: Int
)

private fun Dish.toDishCart(quantity: Int) = DishCartItem(
    this, quantity
)
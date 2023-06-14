package hardcoder.dev.domain.repositories

import hardcoder.dev.domain.entities.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun put(cartItem: CartItem)

    suspend fun remove(itemId: Int)

    suspend fun increaseCount(cartItem: CartItem)

    suspend fun decreaseCount(cartItem: CartItem)

    fun getAllItemsInCart(): Flow<List<CartItem>>
}
package hardcoder.dev.domain.repositories

import hardcoder.dev.domain.entities.Cart
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun put(cart: Cart)

    suspend fun remove(itemId: Int)

    fun getAllItemsInCart(): Flow<List<Cart>>
}
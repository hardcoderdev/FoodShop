package hardcoder.dev.data.repositories

import hardcoder.dev.coroutines.mapItems
import hardcoder.dev.data.local.dao.CartDao
import hardcoder.dev.data.local.entities.CartLocal
import hardcoder.dev.domain.entities.CartItem
import hardcoder.dev.domain.repositories.CartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CartRepositoryImpl(
    private val cartDao: CartDao,
    private val ioDispatcher: CoroutineDispatcher
) : CartRepository {

    override suspend fun put(cartItem: CartItem) = withContext(ioDispatcher) {
        if (cartItem.quantity != 0) {
            increaseCount(cartItem)
        } else {
            cartDao.insert(cartItem.toLocal())
        }
    }

    override suspend fun remove(itemId: Int) = withContext(ioDispatcher) {
        cartDao.delete(itemId)
    }

    override suspend fun increaseCount(cartItem: CartItem) = withContext(ioDispatcher) {
        cartDao.update(cartItem.copy(quantity = cartItem.quantity + 1).toLocal())
    }

    override suspend fun decreaseCount(cartItem: CartItem) = withContext(ioDispatcher) {
        cartDao.update(cartItem.copy(quantity = cartItem.quantity - 1).toLocal())
    }

    override fun getAllItemsInCart(): Flow<List<CartItem>> {
        return cartDao.getAllCartItems().mapItems { cartLocal ->
            cartLocal.toDomain()
        }
    }
}

private fun CartItem.toLocal() = CartLocal(id = dishId, quantity = quantity)
private fun CartLocal.toDomain() = CartItem(dishId = id, quantity = quantity)
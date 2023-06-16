package hardcoder.dev.data.repositories

import hardcoder.dev.coroutines.mapItems
import hardcoder.dev.data.local.AppDatabase
import hardcoder.dev.data.local.entities.CartLocal
import hardcoder.dev.domain.entities.CartItem
import hardcoder.dev.domain.repositories.CartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class CartRepositoryImpl(
    appDatabase: AppDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : CartRepository {

    private val cartDao = appDatabase.cartDao

    override suspend fun put(cartItem: CartItem) = withContext(ioDispatcher) {
        cartDao.insert(cartItem.toLocal())
    }

    override suspend fun remove(itemId: Int) = withContext(ioDispatcher) {
        cartDao.delete(itemId)
    }

    override suspend fun increaseCount(cartItem: CartItem) = withContext(ioDispatcher) {
        cartDao.update(cartItem.copy(quantity = cartItem.quantity + 1).toLocal())
    }

    override suspend fun decreaseCount(cartItem: CartItem) = withContext(ioDispatcher) {
        if (cartItem.quantity > 1) {
            cartDao.update(cartItem.copy(quantity = cartItem.quantity - 1).toLocal())
        } else {
            remove(cartItem.dishId)
        }
    }

    override fun getAllItemsInCart(): Flow<List<CartItem>> {
        return cartDao.getAllCartItems().mapItems { cartLocal ->
            cartLocal.toDomain()
        }.flowOn(ioDispatcher)
    }
}

private fun CartItem.toLocal() = CartLocal(id = dishId, quantity = quantity)
private fun CartLocal.toDomain() = CartItem(dishId = id, quantity = quantity)
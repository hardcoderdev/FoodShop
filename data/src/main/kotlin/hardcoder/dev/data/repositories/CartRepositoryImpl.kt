package hardcoder.dev.data.repositories

import hardcoder.dev.data.local.dao.CartDao
import hardcoder.dev.data.local.entities.CartLocal
import hardcoder.dev.domain.entities.Cart
import hardcoder.dev.domain.repositories.CartRepository
import hardcoder.dev.foundation.mapItems
import kotlinx.coroutines.flow.Flow

class CartRepositoryImpl(private val cartDao: CartDao) : CartRepository {

    override suspend fun put(cart: Cart) {
        cartDao.insert(cart.toLocal())
    }

    override suspend fun remove(itemId: Int) {
        cartDao.delete(itemId)
    }

    override fun getAllItemsInCart(): Flow<List<Cart>> {
        return cartDao.getAllCartItems().mapItems { cartLocal ->
            cartLocal.toDomain()
        }
    }
}

private fun Cart.toLocal() = CartLocal(id = dishId)
private fun CartLocal.toDomain() = Cart(dishId = id)
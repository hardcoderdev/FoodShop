package hardcoder.dev.domain.useCases.cart

import hardcoder.dev.domain.entities.CartItem
import hardcoder.dev.domain.repositories.CartRepository
import kotlinx.coroutines.flow.Flow

class GetAllCartItemsUseCase(private val cartRepository: CartRepository) {

    operator fun invoke(): Flow<List<CartItem>> {
        return cartRepository.getAllItemsInCart()
    }
}
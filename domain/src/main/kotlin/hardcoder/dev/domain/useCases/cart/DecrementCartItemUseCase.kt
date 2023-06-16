package hardcoder.dev.domain.useCases.cart

import hardcoder.dev.domain.entities.CartItem
import hardcoder.dev.domain.repositories.CartRepository

class DecrementCartItemUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(cartItem: CartItem) {
        cartRepository.decreaseCount(cartItem)
    }
}
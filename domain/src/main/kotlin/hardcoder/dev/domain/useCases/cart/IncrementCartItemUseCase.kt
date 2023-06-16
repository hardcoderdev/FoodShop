package hardcoder.dev.domain.useCases.cart

import hardcoder.dev.domain.entities.CartItem
import hardcoder.dev.domain.repositories.CartRepository

class IncrementCartItemUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(cartItem: CartItem) {
        cartRepository.increaseCount(cartItem)
    }
}
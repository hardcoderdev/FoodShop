package hardcoder.dev.domain.useCases

import hardcoder.dev.domain.entities.CartItem
import hardcoder.dev.domain.repositories.CartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class IncrementCartItemUseCase(
    private val cartRepository: CartRepository,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(cartItem: CartItem) = withContext(ioDispatcher) {
        if (cartItem.quantity != 0) {
            cartRepository.increaseCount(cartItem)
        } else {
            cartRepository.put(cartItem)
        }
    }
}
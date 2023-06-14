package hardcoder.dev.domain.useCases

import hardcoder.dev.domain.entities.CartItem
import hardcoder.dev.domain.repositories.CartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DecrementCartItemUseCase(
    private val cartRepository: CartRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(cartItem: CartItem) = withContext(dispatcher) {
        if (cartItem.quantity != 1) {
            cartRepository.decreaseCount(cartItem)
        } else {
            cartRepository.remove(cartItem.dishId)
        }
    }
}
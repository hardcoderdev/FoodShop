package hardcoder.dev.domain.useCases.cart

import hardcoder.dev.domain.entities.CartItem
import hardcoder.dev.domain.repositories.CartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DecrementCartItemUseCase(
    private val cartRepository: CartRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(cartItem: CartItem) = withContext(ioDispatcher) {
        cartRepository.decreaseCount(cartItem)
    }
}
package hardcoder.dev.domain.useCases.dishes

import hardcoder.dev.domain.repositories.CartRepository

class CountCartItemsTotalPrice(private val cartRepository: CartRepository) {

    operator suspend fun invoke() {

    }
}
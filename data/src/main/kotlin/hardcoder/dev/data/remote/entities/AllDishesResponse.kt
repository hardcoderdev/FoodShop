package hardcoder.dev.data.remote.entities

import kotlinx.serialization.Serializable

@Serializable
data class AllDishesResponse(val dishes: List<DishRemote>)
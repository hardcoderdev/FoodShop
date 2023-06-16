package hardcoder.dev.data.remote.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllDishesResponse(
        @SerialName("dishes")
        val dishes: List<DishRemote>
)
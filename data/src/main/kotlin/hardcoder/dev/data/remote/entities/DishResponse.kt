package hardcoder.dev.data.remote.entities

import kotlinx.serialization.Serializable

@Serializable
data class DishResponse(val dishes: List<DishRemote>)
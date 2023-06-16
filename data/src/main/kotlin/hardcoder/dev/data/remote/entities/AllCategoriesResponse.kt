package hardcoder.dev.data.remote.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllCategoriesResponse(
        @SerialName("сategories")
        val categories: List<CategoryRemote>
)
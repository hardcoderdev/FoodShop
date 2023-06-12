package hardcoder.dev.data.remote.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DishRemote(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("price")
    val price: Int,
    @SerialName("tegs")
    val tagList: List<String>,
    @SerialName("weight")
    val weight: Int
)
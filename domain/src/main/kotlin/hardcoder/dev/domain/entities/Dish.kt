package hardcoder.dev.domain.entities

data class Dish(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Int,
    val weight: Int
)

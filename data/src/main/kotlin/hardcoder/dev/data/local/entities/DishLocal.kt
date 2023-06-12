package hardcoder.dev.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dishes")
data class DishLocal(
    @PrimaryKey
    @ColumnInfo(name = "dish_id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "price")
    val price: Int,
    @ColumnInfo(name = "weight")
    val weight: Int
)

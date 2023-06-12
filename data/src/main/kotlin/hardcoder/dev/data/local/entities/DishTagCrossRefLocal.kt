package hardcoder.dev.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "dishes_with_tags",
    primaryKeys = ["dish_id", "tag_name"]
)
data class DishTagCrossRefLocal(
    @ColumnInfo(name = "dish_id")
    val dishId: Int,
    @ColumnInfo(name = "tag_name")
    val tagName: String
)

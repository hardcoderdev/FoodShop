package hardcoder.dev.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartLocal(
    @PrimaryKey
    @ColumnInfo(name = "dish_id")
    val id: Int,
    @ColumnInfo(name = "quantity")
    val quantity: Int
)

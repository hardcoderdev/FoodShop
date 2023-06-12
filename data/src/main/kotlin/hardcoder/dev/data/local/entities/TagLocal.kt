package hardcoder.dev.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags")
data class TagLocal(
    @PrimaryKey
    @ColumnInfo(name = "tag_name")
    val name: String
)

package hardcoder.dev.data.local.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class DishWithTagsLocal(
    @Embedded val dishLocal: DishLocal,
    @Relation(
        parentColumn = "dish_id",
        entityColumn = "tag_name",
        associateBy = Junction(
            value = DishTagCrossRefLocal::class,
            parentColumn = "dish_id",
            entityColumn = "tag_name"
        )
    )
    val tagList: List<TagLocal>
)

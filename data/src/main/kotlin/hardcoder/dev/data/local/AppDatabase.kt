package hardcoder.dev.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import hardcoder.dev.data.local.dao.CategoriesDao
import hardcoder.dev.data.local.dao.DishDao
import hardcoder.dev.data.local.dao.DishWithTagDao
import hardcoder.dev.data.local.dao.TagDao
import hardcoder.dev.data.local.entities.CartLocal
import hardcoder.dev.data.local.entities.CategoryLocal
import hardcoder.dev.data.local.entities.DishLocal
import hardcoder.dev.data.local.entities.DishTagCrossRefLocal
import hardcoder.dev.data.local.entities.TagLocal

@Database(
    version = 1,
    entities = [
        CategoryLocal::class,
        DishLocal::class,
        TagLocal::class,
        DishTagCrossRefLocal::class,
        CartLocal::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun dishDao(): DishDao
    abstract fun tagDao(): TagDao
    abstract fun dishWithTagDao(): DishWithTagDao
}
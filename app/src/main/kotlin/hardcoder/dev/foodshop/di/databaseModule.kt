package hardcoder.dev.foodshop.di

import android.content.Context
import androidx.room.Room
import hardcoder.dev.data.local.AppDatabase
import org.koin.dsl.module

fun databaseModule(context: Context) = module {
    single {
        Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "food_shop.db"
        ).build()
    }
}
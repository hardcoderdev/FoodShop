package hardcoder.dev.foodshop.di

import androidx.room.Room
import hardcoder.dev.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = AppDatabase::class.java,
            name = "food_shop.db"
        ).build()
    }
}
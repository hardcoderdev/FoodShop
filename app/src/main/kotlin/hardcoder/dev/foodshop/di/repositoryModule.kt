package hardcoder.dev.foodshop.di

import hardcoder.dev.data.repositories.CartRepositoryImpl
import hardcoder.dev.data.repositories.CategoriesRepositoryImpl
import hardcoder.dev.data.repositories.DishesRepositoryImpl
import hardcoder.dev.domain.repositories.CartRepository
import hardcoder.dev.domain.repositories.CategoriesRepository
import hardcoder.dev.domain.repositories.DishesRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoriesModule = module {
    single<DishesRepository> {
        DishesRepositoryImpl(
            foodAPI = get(),
            appDatabase = get(),
            ioDispatcher = Dispatchers.IO
        )
    }

    single<CategoriesRepository> {
        CategoriesRepositoryImpl(
            foodAPI = get(),
            appDatabase = get(),
            ioDispatcher = Dispatchers.IO
        )
    }

    single<CartRepository> {
        CartRepositoryImpl(
            appDatabase = get(),
            ioDispatcher = Dispatchers.IO
        )
    }
}
package hardcoder.dev.foodshop.di

import hardcoder.dev.domain.useCases.cart.DecrementCartItemUseCase
import hardcoder.dev.domain.useCases.cart.GetAllCartItemsUseCase
import hardcoder.dev.domain.useCases.categories.GetAllCategoriesUseCase
import hardcoder.dev.domain.useCases.dishes.GetAllDishesByTagUseCase
import hardcoder.dev.domain.useCases.dishes.GetAllTagsUseCase
import hardcoder.dev.domain.useCases.dishes.GetDishByIdUseCase
import hardcoder.dev.domain.useCases.cart.IncrementCartItemUseCase
import hardcoder.dev.domain.useCases.cart.PutItemInCartUseCase
import hardcoder.dev.domain.useCases.categories.RefreshCategoriesUseCase
import hardcoder.dev.domain.useCases.dishes.RefreshDishesUseCase
import org.koin.dsl.module

val useCasesModule = module {
    single {
        RefreshDishesUseCase(
            dishesRepository = get()
        )
    }

    single {
        GetAllTagsUseCase(
            dishesRepository = get()
        )
    }

    single {
        GetAllDishesByTagUseCase(
            dishesRepository = get()
        )
    }

    single {
        GetDishByIdUseCase(
            dishesRepository = get()
        )
    }

    single {
        GetAllCategoriesUseCase(
            categoriesRepository = get()
        )
    }

    single {
        RefreshCategoriesUseCase(
            categoriesRepository = get()
        )
    }

    single {
        GetAllCartItemsUseCase(
            cartRepository = get()
        )
    }

    single {
        PutItemInCartUseCase(
            cartRepository = get()
        )
    }

    single {
        IncrementCartItemUseCase(
            cartRepository = get()
        )
    }

    single {
        DecrementCartItemUseCase(
            cartRepository = get()
        )
    }
}
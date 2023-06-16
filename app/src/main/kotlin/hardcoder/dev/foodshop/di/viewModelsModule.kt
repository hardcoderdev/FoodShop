package hardcoder.dev.foodshop.di

import hardcoder.dev.presentation.CartViewModel
import hardcoder.dev.presentation.CategoriesViewModel
import hardcoder.dev.presentation.DetailDishViewModel
import hardcoder.dev.presentation.DishesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        DishesViewModel(
            getAllTagsUseCase = get(),
            getAllDishesByTagUseCase = get(),
            refreshDishesUseCase = get()
        )
    }

    viewModel { parameters ->
        DetailDishViewModel(
            dishId = parameters.get(),
            getDishByIdUseCase = get(),
            putItemInCartUseCase = get()
        )
    }

    viewModel {
        CategoriesViewModel(
            getAllCategoriesUseCase = get(),
            refreshCategoriesUseCase = get()
        )
    }

    viewModel {
        CartViewModel(
            incrementCartItemUseCase = get(),
            decrementCartItemUseCase = get(),
            getAllCartItemsUseCase = get()
        )
    }
}
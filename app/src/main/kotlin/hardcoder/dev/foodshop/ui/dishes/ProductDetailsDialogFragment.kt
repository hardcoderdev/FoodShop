package hardcoder.dev.foodshop.ui.dishes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.RoundedCornersTransformation
import hardcoder.dev.coroutines.launchWith
import hardcoder.dev.domain.entities.Dish
import hardcoder.dev.presentation.DetailDishViewModel
import hardcoderdev.foodshop.app.R
import hardcoderdev.foodshop.app.databinding.FragmentProductDetailsDialogBinding
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailsDialogFragment : DialogFragment(R.layout.fragment_product_details_dialog) {

    private val binding by viewBinding(FragmentProductDetailsDialogBinding::bind)
    private val navArgs by navArgs<ProductDetailsDialogFragmentArgs>()
    private val dishId by lazy { navArgs.dishId }
    private val detailDishViewModel by viewModel<DetailDishViewModel>(
        parameters = { parametersOf(dishId) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClicks()
        subscribeToObservables()
    }

    private fun subscribeToObservables() = with(detailDishViewModel) {
        dish.onEach { loadedState ->
            when (loadedState) {
                is DetailDishViewModel.LoadingState.Loaded -> {
                    setUpViews(loadedState.dish)
                }
                is DetailDishViewModel.LoadingState.Loading -> {
                    /* no-op */
                }
            }
        }.launchWith(viewLifecycleOwner)
    }

    private fun setUpViews(loadedDish: Dish) = with(binding) {
        dishImageView.load(loadedDish.imageUrl) {
            crossfade(enable = true)
            transformations(RoundedCornersTransformation(16f))
        }

        titleTextView.text = loadedDish.name
        priceTextView.text = getString(R.string.price_format, loadedDish.price)
        weightTextView.text = getString(R.string.weight_format, loadedDish.weight)
        descriptionTextView.text = loadedDish.description
    }

    private fun setUpClicks() = with(binding) {
        favouriteImageView.setOnClickListener {
            /* no-op */
        }

        closeImageView.setOnClickListener {
            dismiss()
        }

        addToCartButton.setOnClickListener {
            detailDishViewModel.putToCart()
            dismiss()
        }
    }
}
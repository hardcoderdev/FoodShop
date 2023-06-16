package hardcoder.dev.foodshop.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.RoundedCornersTransformation
import epicarchitect.recyclerview.EpicAdapter
import epicarchitect.recyclerview.bind
import epicarchitect.recyclerview.requireEpicAdapter
import hardcoder.dev.coroutines.launchWith
import hardcoder.dev.domain.useCases.cart.DishCartItem
import hardcoder.dev.presentation.CartViewModel
import hardcoderdev.foodshop.app.R
import hardcoderdev.foodshop.app.databinding.FragmentCartBinding
import hardcoderdev.foodshop.app.databinding.ItemCartBinding
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment(R.layout.fragment_cart) {

    private val binding by viewBinding(FragmentCartBinding::bind)
    private val viewModel by viewModel<CartViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpClicks()
        subscribeToObservables()
    }

    private fun subscribeToObservables() = with(viewModel) {
        cartItems.onEach { cartItemList ->
            binding.categoriesRecyclerView.requireEpicAdapter().loadItems(cartItemList)
        }.launchWith(viewLifecycleOwner)

        totalCartSum.onEach {
            binding.payButton.text = getString(R.string.pay_format, it)
        }.launchWith(viewLifecycleOwner)
    }

    private fun setUpClicks() = with(binding) {
        payButton.setOnClickListener {
            /* no-op */
        }
    }

    private fun setUpRecyclerView() = with(binding) {
        categoriesRecyclerView.adapter = EpicAdapter {
            setup<DishCartItem, ItemCartBinding>(ItemCartBinding::inflate) {
                diffUtil {
                    areItemsTheSame { oldItem, newItem -> oldItem.dish.id == newItem.dish.id }
                    payload { oldItem, newItem ->
                        if (oldItem.quantity != newItem.quantity) {
                            Unit
                        } else {
                            null
                        }
                    }
                }

                init { item ->
                    decreaseQuantityImageView.setOnClickListener {
                        viewModel.decrementCartItem(item.value)
                    }

                    increaseQuantityImageView.setOnClickListener {
                        viewModel.incrementCartItem(item.value)
                    }
                }

                bind { item ->
                    titleTextView.text = item.dish.name
                    priceTextView.text = getString(R.string.price_format, item.dish.price)
                    weightTextView.text = getString(R.string.weight_format, item.dish.weight)
                    quantityTextView.text = item.quantity.toString()
                    dishImageView.load(item.dish.imageUrl) {
                        crossfade(enable = true)
                        transformations(RoundedCornersTransformation(16f))
                    }
                }
            }
        }
    }
}
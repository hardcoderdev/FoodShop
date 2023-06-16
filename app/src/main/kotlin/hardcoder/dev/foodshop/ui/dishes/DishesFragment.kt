package hardcoder.dev.foodshop.ui.dishes

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.RoundedCornersTransformation
import epicarchitect.recyclerview.EpicAdapter
import epicarchitect.recyclerview.bind
import epicarchitect.recyclerview.requireEpicAdapter
import hardcoder.dev.coroutines.launchWith
import hardcoder.dev.presentation.DishesViewModel
import hardcoder.dev.presentation.ItemDish
import hardcoder.dev.presentation.ItemTag
import hardcoderdev.foodshop.app.R
import hardcoderdev.foodshop.app.databinding.FragmentDishesBinding
import hardcoderdev.foodshop.app.databinding.ItemDishBinding
import hardcoderdev.foodshop.app.databinding.ItemTagBinding
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class DishesFragment : Fragment(R.layout.fragment_dishes) {

    private val binding by viewBinding(FragmentDishesBinding::bind)
    private val navArgs by navArgs<DishesFragmentArgs>()
    private val viewModel by viewModel<DishesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        setUpRecyclerView()
        subscribeToObservables()
    }

    private fun subscribeToObservables() = with(viewModel) {
        resultTagList.onEach { tagsList ->
            binding.tagRecyclerView.requireEpicAdapter().loadItems(tagsList)
        }.launchWith(viewLifecycleOwner)

        dishesList.onEach { dishesList ->
            binding.dishesRecyclerView.requireEpicAdapter().loadItems(dishesList)
        }.launchWith(viewLifecycleOwner)
    }

    private fun setUpToolbar() = with(binding.toolbar) {
        categoryTextView.text = navArgs.categoryName
        goBackImageView.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpRecyclerView() = with(binding) {
        tagRecyclerView.adapter = EpicAdapter {
            setup<ItemTag, ItemTagBinding>(ItemTagBinding::inflate) {
                bind { item ->
                    root.setOnClickListener { viewModel.filterByTag(item) }
                    tagTextView.apply {
                        text = item.title
                        setTextColor(if (item.isSelected) Color.WHITE else Color.BLACK)
                        background = ResourcesCompat.getDrawable(
                            context.resources,
                            if (item.isSelected) {
                                R.drawable.tag_active
                            } else {
                                R.drawable.tag_inactive
                            },
                            context.theme
                        )
                    }
                }
            }
        }

        dishesRecyclerView.adapter = EpicAdapter {
            setup<ItemDish, ItemDishBinding>(ItemDishBinding::inflate) {
                bind { item ->
                    root.setOnClickListener {
                        findNavController().navigate(
                            ProductDetailsDialogFragmentDirections.toProductDetailsDialogFragment(
                                item.id
                            )
                        )
                    }

                    titleTextView.text = item.title
                    dishImageView.load(item.imageUrl) {
                        crossfade(enable = true)
                        transformations(RoundedCornersTransformation(16f))
                    }
                }
            }
        }
    }
}
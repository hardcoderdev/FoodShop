package hardcoder.dev.foodshop.ui.dishes

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
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
import hardcoder.dev.domain.entities.Dish
import hardcoder.dev.domain.entities.Tag
import hardcoder.dev.presentation.CategoriesViewModel
import hardcoder.dev.presentation.DishesViewModel
import hardcoderdev.foodshop.app.R
import hardcoderdev.foodshop.app.databinding.FragmentDishesBinding
import hardcoderdev.foodshop.app.databinding.ItemDishBinding
import hardcoderdev.foodshop.app.databinding.ItemTagBinding
import kotlinx.coroutines.flow.launchIn
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
        dishesList.onEach { loadingState ->
            when (loadingState) {
                is DishesViewModel.LoadingState.Loaded -> {
                    when (loadingState.refreshState) {
                        is DishesViewModel.RefreshState.Error -> {
                            with(binding) {
                                progressBar.isVisible = false
                                dishesRecyclerView.isVisible = false
                                internetDisconnectedTextView.isVisible = true
                                refreshButton.isVisible = true
                                refreshButton.setOnClickListener {
                                    viewModel.refreshDishes()
                                }
                            }
                        }
                        DishesViewModel.RefreshState.Loaded -> {
                            with(binding) {
                                progressBar.isVisible = false
                                dishesRecyclerView.isVisible = true
                                internetDisconnectedTextView.isVisible = false
                                refreshButton.isVisible = false
                                dishesRecyclerView.requireEpicAdapter().loadItems(
                                    loadingState.dishes
                                )
                            }
                        }
                        DishesViewModel.RefreshState.Loading -> {
                            with(binding) {
                                progressBar.isVisible = true
                                dishesRecyclerView.isVisible = false
                                internetDisconnectedTextView.isVisible = false
                                refreshButton.isVisible = false
                            }
                        }
                    }
                }
                is DishesViewModel.LoadingState.Loading -> {
                    with(binding) {
                        progressBar.isVisible = true
                        dishesRecyclerView.isVisible = false
                        internetDisconnectedTextView.isVisible = false
                        refreshButton.isVisible = false
                    }
                }
            }
        }.launchWith(viewLifecycleOwner)

        sortedTagList.onEach { tagsList ->
            binding.tagRecyclerView.requireEpicAdapter().loadItems(tagsList)
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
            setup<Tag, ItemTagBinding>(ItemTagBinding::inflate) {
                init { item ->
                    root.setOnClickListener {
                        viewModel.filterByTag(item.value)
                    }
                }

                bind { scope, _, _, item ->
                    tagTextView.apply {
                        text = item.name

                        viewModel.selectedTag.onEach {
                            val isSelected = item == it
                            setTextColor(if (isSelected) Color.WHITE else Color.BLACK)
                            background = ResourcesCompat.getDrawable(
                                context.resources,
                                if (isSelected) {
                                    R.drawable.tag_active
                                } else {
                                    R.drawable.tag_inactive
                                },
                                context.theme
                            )
                        }.launchIn(scope)
                    }
                }
            }
        }

        dishesRecyclerView.adapter = EpicAdapter {
            setup<Dish, ItemDishBinding>(ItemDishBinding::inflate) {
                bind { item ->
                    root.setOnClickListener {
                        findNavController().navigate(
                            ProductDetailsDialogFragmentDirections.toProductDetailsDialogFragment(
                                item.id
                            )
                        )
                    }

                    titleTextView.text = item.name
                    dishImageView.load(item.imageUrl) {
                        crossfade(enable = true)
                        transformations(RoundedCornersTransformation(16f))
                    }
                }
            }
        }
    }
}
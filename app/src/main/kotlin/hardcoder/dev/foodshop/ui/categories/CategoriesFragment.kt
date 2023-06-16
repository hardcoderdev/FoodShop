package hardcoder.dev.foodshop.ui.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.RoundedCornersTransformation
import epicarchitect.recyclerview.EpicAdapter
import epicarchitect.recyclerview.bind
import epicarchitect.recyclerview.requireEpicAdapter
import hardcoder.dev.coroutines.launchWith
import hardcoder.dev.domain.entities.Category
import hardcoder.dev.presentation.CategoriesViewModel
import hardcoderdev.foodshop.app.R
import hardcoderdev.foodshop.app.databinding.FragmentCategoriesBinding
import hardcoderdev.foodshop.app.databinding.ItemCategoryBinding
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    private val binding by viewBinding(FragmentCategoriesBinding::bind)
    private val viewModel by viewModel<CategoriesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        subscribeToObservables()
    }

    private fun subscribeToObservables() = with(viewModel) {
        categories.onEach { loadedState ->
            when (loadedState) {
                is CategoriesViewModel.LoadingState.Loaded -> {
                    when (loadedState.refreshState) {
                        is CategoriesViewModel.RefreshState.Error -> {
                            with(binding) {
                                progressBar.isVisible = false
                                categoriesRecyclerView.isVisible = false
                                internetDisconnectedTextView.isVisible = true
                                refreshButton.isVisible = true
                                refreshButton.setOnClickListener {
                                    viewModel.refreshCategories()
                                }
                            }
                        }
                        CategoriesViewModel.RefreshState.Loaded -> {
                            with(binding) {
                                progressBar.isVisible = false
                                categoriesRecyclerView.isVisible = true
                                internetDisconnectedTextView.isVisible = false
                                refreshButton.isVisible = false
                                categoriesRecyclerView.requireEpicAdapter().loadItems(
                                    loadedState.categories
                                )
                            }
                        }
                        CategoriesViewModel.RefreshState.Loading -> {
                            with(binding) {
                                progressBar.isVisible = true
                                categoriesRecyclerView.isVisible = false
                                internetDisconnectedTextView.isVisible = false
                                refreshButton.isVisible = false
                            }
                        }
                    }
                }
                is CategoriesViewModel.LoadingState.Loading -> {
                    with(binding) {
                        progressBar.isVisible = true
                        categoriesRecyclerView.isVisible = false
                        internetDisconnectedTextView.isVisible = false
                        refreshButton.isVisible = false
                    }
                }
            }

        }.launchWith(viewLifecycleOwner)
    }

    private fun setUpRecyclerView() = with(binding) {
        categoriesRecyclerView.adapter = EpicAdapter {
            setup<Category, ItemCategoryBinding>(ItemCategoryBinding::inflate) {
                init { item ->
                    root.setOnClickListener {
                        findNavController().navigate(
                            CategoriesFragmentDirections.toDishesFragment(item.value.name)
                        )
                    }
                }

                bind { item ->
                    titleTextView.text = item.name
                    backgroundImageView.load(item.imageUrl) {
                        crossfade(enable = true)
                        transformations(RoundedCornersTransformation(16f))
                    }
                }
            }
        }
    }
}
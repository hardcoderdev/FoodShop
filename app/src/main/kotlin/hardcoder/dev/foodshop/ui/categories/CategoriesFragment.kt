package hardcoder.dev.foodshop.ui.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.RoundedCornersTransformation
import epicarchitect.recyclerview.EpicAdapter
import epicarchitect.recyclerview.bind
import epicarchitect.recyclerview.requireEpicAdapter
import hardcoder.dev.coroutines.launchWith
import hardcoder.dev.presentation.CategoriesViewModel
import hardcoder.dev.presentation.ItemCategory
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
        categories.onEach { categories ->
            binding.categoriesRecyclerView.requireEpicAdapter().loadItems(
                categories.map {
                    ItemCategory(
                        title = it.title,
                        imageUrl = it.imageUrl
                    )
                }
            )
        }.launchWith(viewLifecycleOwner)
    }

    private fun setUpRecyclerView() = with(binding) {
        categoriesRecyclerView.adapter = EpicAdapter {
            setup<ItemCategory, ItemCategoryBinding>(ItemCategoryBinding::inflate) {
                bind { item ->
                    root.setOnClickListener {
                        findNavController().navigate(
                            CategoriesFragmentDirections.toDishesFragment(item.title)
                        )
                    }

                    titleTextView.text = item.title
                    backgroundImageView.load(item.imageUrl) {
                        crossfade(enable = true)
                        transformations(RoundedCornersTransformation(16f))
                    }
                }
            }
        }
    }
}
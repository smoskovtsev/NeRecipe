package ru.netology.nerecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipe.adapter.RecipesAdapter
import ru.netology.nerecipe.databinding.CookBookFragmentBinding
import ru.netology.nerecipe.ui.RecipeDescriptionFragment
import ru.netology.nerecipe.viewModel.RecipeViewModel

class CookBookFragment : Fragment() {

    private val viewModel: RecipeViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navigateToRecipeDescriptionScreenEvent.observe(this) { initialRecipe ->
            val direction = CookBookFragmentDirections.toRecipeDescriptionFragment(initialRecipe)
            findNavController().navigate(direction)
        }

        viewModel.navigateToRecipeCardScreenEvent.observe(this) { initialRecipe ->
            val direction = CookBookFragmentDirections.toRecipeCardFragment(initialRecipe)
            findNavController().navigate(direction)
        }

        viewModel.navigateToFavoritesFragmentScreenEvent.observe(this) { initialRecipe ->
            val direction = CookBookFragmentDirections.toFavoritesFragment(initialRecipe)
            findNavController().navigate(direction)
        }

        viewModel.navigateToSearchFragmentScreenEvent.observe(this) { initialRecipe ->
            val direction = CookBookFragmentDirections.toSearchFragment(initialRecipe)
            findNavController().navigate(direction)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = CookBookFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        val adapter = RecipesAdapter(viewModel)
        binding.recipesRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)
            if (viewModel.data.value.isNullOrEmpty()) {
                binding.imageEmpty.visibility = View.VISIBLE
                binding.textEmpty.visibility = View.VISIBLE
            }
        }

        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
        }

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_favorites -> {
                    viewModel.onFavoritesFiltered()
                    true
                }
                R.id.navigation_home -> {
                    binding.textEmpty.visibility = View.VISIBLE
                    true
                }
                R.id.navigation_search -> {
                    viewModel.onSearchOpened()
                    true
                }
                else -> false
            }
        }
    }.root

//    override fun getItemCount(): Int {
//        return recipesFilterList.size
//    }

    override fun onResume () {
        super.onResume()

        setFragmentResultListener(RecipeDescriptionFragment.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != RecipeDescriptionFragment.REQUEST_KEY) return@setFragmentResultListener
            val newRecipe = bundle.getStringArray(RecipeDescriptionFragment.RESULT_KEY) ?: return@setFragmentResultListener
            viewModel.onSaveButtonClicked(newRecipe)
        }
    }

    companion object {
        const val TAG = "cookBookFragment"
    }
}
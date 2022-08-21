package ru.netology.nerecipe

import android.os.Bundle
import android.view.LayoutInflater
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
        }

        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
        }
    }.root

    override fun onResume () {
        super.onResume()

        setFragmentResultListener(RecipeDescriptionFragment.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != RecipeDescriptionFragment.REQUEST_KEY) return@setFragmentResultListener
            val newRecipeDescription = bundle.getString(RecipeDescriptionFragment.RESULT_KEY) ?: return@setFragmentResultListener
            viewModel.onSaveButtonClicked(newRecipeDescription)
        }
    }

    companion object {
        const val TAG = "cookBookFragment"
    }
}
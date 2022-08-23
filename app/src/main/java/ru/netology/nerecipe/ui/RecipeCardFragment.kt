package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import ru.netology.nerecipe.CookBookFragmentDirections
import ru.netology.nerecipe.adapter.RecipesAdapter
import ru.netology.nerecipe.databinding.RecipeCardFragmentBinding
import ru.netology.nerecipe.viewModel.RecipeViewModel

class RecipeCardFragment : Fragment() {

    private val viewModel: RecipeViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private val initialRecipe by lazy {
        val args by navArgs<RecipeCardFragmentArgs>()
        args.initialRecipe
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeCardFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        val viewHolder = RecipesAdapter.ViewHolder(binding.recipeCard, viewModel)
        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            val recipe = recipes.find { it.id == initialRecipe?.id } ?: run {
                findNavController().navigateUp() // the post was deleted, close the fragment
                return@observe
            }
            viewHolder.bind(recipe)
        }

        viewModel.navigateToRecipeDescriptionScreenEvent.observe(viewLifecycleOwner) { initialContent ->
            val direction = CookBookFragmentDirections.toRecipeDescriptionFragment(initialContent)
            findNavController().navigate(direction)
        }
    }.root

    override fun onResume () {
        super.onResume()

        setFragmentResultListener(RecipeDescriptionFragment.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != RecipeDescriptionFragment.REQUEST_KEY) return@setFragmentResultListener
            val newRecipeContent = bundle.getStringArray(RecipeDescriptionFragment.RESULT_KEY) ?: return@setFragmentResultListener
            viewModel.onSaveButtonClicked(newRecipeContent)
        }
    }
}
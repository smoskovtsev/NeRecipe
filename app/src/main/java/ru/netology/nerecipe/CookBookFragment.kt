package ru.netology.nerecipe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipe.adapter.RecipesAdapter
import ru.netology.nerecipe.databinding.CookBookFragmentBinding
import ru.netology.nerecipe.ui.RecipeFragment
import ru.netology.nerecipe.viewModel.RecipeViewModel

class CookBookFragment : Fragment() {

    private val viewModel by viewModels<RecipeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(
            requestKey = RecipeFragment.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != RecipeFragment.REQUEST_KEY) return@setFragmentResultListener
            val newRecipe = bundle.getString(RecipeFragment.RESULT_KEY) ?: return@setFragmentResultListener
            viewModel.onSaveButtonClicked(newRecipe)
        }

        viewModel.navigateToRecipeScreenEvent.observe(this) { initialRecipe ->
            val direction = CookBookFragmentDirections.toRecipeFragment(initialRecipe)
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

    companion object {
        const val TAG = "cookBookFragment"
    }
}
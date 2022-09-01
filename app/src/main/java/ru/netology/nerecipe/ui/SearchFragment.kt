package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import ru.netology.nerecipe.adapter.RecipesAdapter
import ru.netology.nerecipe.databinding.SearchFragmentBinding
import ru.netology.nerecipe.viewModel.RecipeViewModel

class SearchFragment : Fragment() {

    private val viewModel: RecipeViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = SearchFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        val adapter = RecipesAdapter(viewModel)
        binding.recipesRecyclerView.adapter = adapter
        searchView = binding.recipeSearch
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.data.observe(viewLifecycleOwner) { recipes ->
                    adapter.submitList(recipes.filter {
                        it.description!!.contains(newText)
                    })
                }
                return false
            }
        })
    }.root

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
package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.newFixedThreadPoolContext
import ru.netology.nerecipe.CookBookFragmentDirections
import ru.netology.nerecipe.adapter.RecipesAdapter
import ru.netology.nerecipe.databinding.CookBookFragmentBinding
import ru.netology.nerecipe.databinding.FavoritesFragmentBinding
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.ui.RecipeDescriptionFragment
import ru.netology.nerecipe.util.SingleLiveEvent
import ru.netology.nerecipe.viewModel.RecipeViewModel

class FavoritesFragment : Fragment() {

    private val viewModel: RecipeViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FavoritesFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        val adapter = RecipesAdapter(viewModel)
        binding.recipesRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(null)
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
package ru.netology.nerecipe.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nerecipe.adapter.RecipeInteractionListener
import ru.netology.nerecipe.data.RecipeRepository
import ru.netology.nerecipe.data.impl.FileRecipeRepository
import ru.netology.nerecipe.data.impl.SharedPrefsRecipeRepository
import ru.netology.nerecipe.data.impl.InMemoryRecipeRepository
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.util.SingleLiveEvent

class RecipeViewModel(
    application: Application
) : AndroidViewModel(application), RecipeInteractionListener {

    private val repository: RecipeRepository = FileRecipeRepository(application)

    val data get() = repository.data

    val navigateToRecipeScreenEvent = SingleLiveEvent<String>()
    val currentRecipe = MutableLiveData<Recipe?>(null)

    fun onSaveButtonClicked(description: String) {
        if (description.isBlank()) return

        val recipe = currentRecipe.value?.copy(
            description = description
        ) ?: Recipe(
            id = RecipeRepository.NEW_RECIPE_ID,
            name = "TestDish",
            author = "NewChef",
            category = "Miscellaneous",
            description = description
        )
        repository.addUpdateRecipe(recipe)
        currentRecipe.value = null
    }

    fun onAddClicked() {
        navigateToRecipeScreenEvent.call()
    }

    // region PostInteractionListener

    override fun onFavAdded(recipe: Recipe) = repository.addToFavorite(recipe.id)

    override fun onRecipeDeleted(recipe: Recipe) = repository.deleteRecipe(recipe.id)

    override fun onRecipeEdited(recipe: Recipe) {
        currentRecipe.value = recipe
        navigateToRecipeScreenEvent.value = recipe.description
    }

    // endregion PostInteractionListener

}
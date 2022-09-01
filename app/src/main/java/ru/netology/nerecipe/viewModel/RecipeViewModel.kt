package ru.netology.nerecipe.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipe.adapter.RecipeInteractionListener
import ru.netology.nerecipe.data.RecipeRepository
import ru.netology.nerecipe.data.impl.FileRecipeRepository
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.util.SingleLiveEvent

class RecipeViewModel(
    application: Application
) : AndroidViewModel(application), RecipeInteractionListener {

    private val repository: RecipeRepository = FileRecipeRepository(application)

    val data get() = repository.data

    val navigateToRecipeDescriptionScreenEvent = SingleLiveEvent<Recipe>()
    val navigateToFavoritesFragmentScreenEvent = SingleLiveEvent<Recipe>()
    val navigateToSearchFragmentScreenEvent = SingleLiveEvent<Recipe>()
    val navigateToRecipeCardScreenEvent = SingleLiveEvent<Recipe>()
    val currentRecipe = MutableLiveData<Recipe?>(null)

    fun onSaveButtonClicked(adjustedRecipe: Array<String>) {
        if (adjustedRecipe.isEmpty()) return
        val recipe = currentRecipe.value?.copy(
            name = adjustedRecipe[0],
            author = adjustedRecipe[1],
            category = adjustedRecipe[2],
            description = adjustedRecipe[3]
        ) ?: Recipe(
            id = RecipeRepository.NEW_RECIPE_ID,
            name = adjustedRecipe[0],
            author = adjustedRecipe[1],
            category = adjustedRecipe[2],
            description = adjustedRecipe[3]
        )
        repository.addUpdateRecipe(recipe)
        currentRecipe.value = null
    }

    fun onAddClicked() {
        navigateToRecipeDescriptionScreenEvent.call()
    }

    fun onFavoritesFiltered() {
        navigateToFavoritesFragmentScreenEvent.call()
    }

    fun onSearchOpened() {
        navigateToSearchFragmentScreenEvent.call()
    }

    // region PostInteractionListener

    override fun onFavAdded(recipe: Recipe) = repository.addToFavorite(recipe.id)

    override fun onRecipeDeleted(recipe: Recipe) = repository.deleteRecipe(recipe.id)

    override fun onRecipeEdited(recipe: Recipe) {
        currentRecipe.value = recipe
        navigateToRecipeDescriptionScreenEvent.value = recipe
    }

    override fun onRecipeClicked(recipe: Recipe) {
        currentRecipe.value = recipe
        navigateToRecipeCardScreenEvent.value = recipe
    }

    // endregion PostInteractionListener

}
package ru.netology.nerecipe.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nerecipe.adapter.RecipeInteractionListener
import ru.netology.nerecipe.data.RecipeRepository
import ru.netology.nerecipe.data.impl.InMemoryRecipeRepository
import ru.netology.nerecipe.dto.Recipe

class RecipeViewModel : ViewModel(), RecipeInteractionListener {

    private val repository: RecipeRepository = InMemoryRecipeRepository()

    val data get() = repository.data

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

    fun onCancelButtonClicked() {
        val recipe = currentRecipe.value?.copy()
        if (recipe != null) {
            repository.addUpdateRecipe(recipe)
        }
        currentRecipe.value = null
    }

    // region PostInteractionListener

    override fun onFavAdded(recipe: Recipe) = repository.addToFavorite(recipe.id)

    override fun onRecipeDeleted(recipe: Recipe) = repository.deleteRecipe(recipe.id)

    override fun onRecipeEdited(recipe: Recipe) {
        currentRecipe.value = recipe
    }

    // endregion PostInteractionListener

}
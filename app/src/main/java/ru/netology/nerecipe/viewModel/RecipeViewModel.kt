package ru.netology.nerecipe.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nerecipe.data.RecipeRepository
import ru.netology.nerecipe.data.impl.InMemoryRecipeRepository
import ru.netology.nerecipe.dto.Recipe

class RecipeViewModel : ViewModel() {

    private val repository: RecipeRepository = InMemoryRecipeRepository()

    val data get() = repository.data

    fun onFavAdded(recipe: Recipe) = repository.addToFavorite(recipe.id)

}
package ru.netology.nerecipe.data

import androidx.lifecycle.LiveData
import ru.netology.nerecipe.dto.Recipe

interface RecipeRepository {

    val data: LiveData<List<Recipe>>

    fun addToFavorite(recipeId: Long)

    fun deleteRecipe(recipeId: Long)

    fun addUpdateRecipe(recipe: Recipe)

    companion object {
        const val NEW_RECIPE_ID = 0L
    }

}
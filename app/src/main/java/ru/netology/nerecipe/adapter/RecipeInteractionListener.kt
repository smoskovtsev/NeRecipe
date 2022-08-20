package ru.netology.nerecipe.adapter

import ru.netology.nerecipe.dto.Recipe

interface RecipeInteractionListener {
    fun onFavAdded(recipe: Recipe)
    fun onRecipeDeleted(recipe: Recipe)
    fun onRecipeEdited(recipe: Recipe)
}
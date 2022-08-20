package ru.netology.nerecipe.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipe.data.RecipeRepository
import ru.netology.nerecipe.dto.Recipe

class InMemoryRecipeRepository : RecipeRepository {

    private var nextId = GENERATED_RECIPES_AMOUNT.toLong()

    override val data = MutableLiveData(
        List(GENERATED_RECIPES_AMOUNT) { index ->
            Recipe(
                id = index + 1L,
                name = "Паэлья по андалусийски",
                author = "Хави Алонсо",
                category = "Европейская",
                description = "Вот как готовить это блюдо"
            )
        }
    )

    private val recipes
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    override fun addToFavorite(recipeId: Long) {
        data.value = recipes.map {
            if (it.id != recipeId) it
            else it.copy(favorite = !it.favorite)
        }
    }

    override fun deleteRecipe(recipeId: Long) {
        data.value = recipes.filterNot { it.id == recipeId}
    }

    override fun addUpdateRecipe(recipe: Recipe) {
        if (recipe.id == RecipeRepository.NEW_RECIPE_ID) insert(recipe) else update(recipe)
    }

    private fun insert(recipe: Recipe) {
        data.value = listOf(
            recipe.copy(id = ++nextId)
        ) + recipes
    }

    private fun update(recipe: Recipe) {
        data.value = recipes.map {
            if (it.id == recipe.id) recipe else it
        }
    }

    private companion object {
        const val GENERATED_RECIPES_AMOUNT = 100
    }
}
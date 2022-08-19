package ru.netology.nerecipe.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipe.data.RecipeRepository
import ru.netology.nerecipe.dto.Recipe

class InMemoryRecipeRepository : RecipeRepository {
    override val data = MutableLiveData(
        List(100) { index ->
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
}
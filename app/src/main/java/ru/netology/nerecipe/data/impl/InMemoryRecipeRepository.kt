package ru.netology.nerecipe.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipe.data.RecipeRepository
import ru.netology.nerecipe.dto.Recipe

class InMemoryRecipeRepository : RecipeRepository {
    override val data = MutableLiveData(
        Recipe(
            id = 1L,
            name = "Паэлья по андалусийски",
            author = "Хави Алонсо",
            category = "Европейская",
            description = "Вот как готовить это блюдо"
        )
    )
}
package ru.netology.nerecipe.data

import androidx.lifecycle.LiveData
import ru.netology.nerecipe.dto.Recipe

interface RecipeRepository {

    val data: LiveData<Recipe>

}
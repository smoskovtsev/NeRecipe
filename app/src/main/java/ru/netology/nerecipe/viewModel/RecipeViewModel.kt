package ru.netology.nerecipe.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nerecipe.data.RecipeRepository
import ru.netology.nerecipe.data.impl.InMemoryRecipeRepository

class RecipeViewModel : ViewModel() {

    private val repository: RecipeRepository = InMemoryRecipeRepository()

    val data get() = repository.data

}
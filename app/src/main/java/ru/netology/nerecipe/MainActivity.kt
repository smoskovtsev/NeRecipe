package ru.netology.nerecipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nerecipe.databinding.ActivityMainBinding
import ru.netology.nerecipe.dto.Recipe
import ru.netology.nerecipe.viewModel.RecipeViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<RecipeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { recipe ->
            binding.render(recipe)
        }
    }

    private fun ActivityMainBinding.render(recipe: Recipe) {
        recipeName.text = recipe.name
        recipeAuthor.text = recipe.author
        recipeCategory.text = recipe.category
        recipeDescription.text = recipe.description
    }
}
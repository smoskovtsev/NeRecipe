package ru.netology.nerecipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nerecipe.databinding.ActivityMainBinding
import ru.netology.nerecipe.dto.Recipe

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipe = Recipe(
            id = 1L,
            name = "Паэлья по андалусийски",
            author = "Хави Алонсо",
            category = "Европейская",
            description = "Вот как готовить это блюдо"
        )

        binding.render(recipe)
    }

    private fun ActivityMainBinding.render(recipe: Recipe) {
        recipeName.text = recipe.name
        recipeAuthor.text = recipe.author
        recipeCategory.text = recipe.category
        recipeDescription.text = recipe.description
    }
}
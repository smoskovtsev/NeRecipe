package ru.netology.nerecipe

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nerecipe.data.impl.RecipesAdapter
import ru.netology.nerecipe.databinding.ActivityMainBinding
import ru.netology.nerecipe.viewModel.RecipeViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<RecipeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = RecipesAdapter(viewModel::onFavAdded)
        binding.recipesRecycleView.adapter = adapter
        viewModel.data.observe(this) { recipes ->
            adapter.submitList(recipes)
        }
    }
}
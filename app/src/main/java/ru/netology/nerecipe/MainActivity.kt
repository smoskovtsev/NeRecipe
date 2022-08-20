package ru.netology.nerecipe

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nerecipe.adapter.RecipesAdapter
import ru.netology.nerecipe.databinding.ActivityMainBinding
import ru.netology.nerecipe.util.hideKeyboard
import ru.netology.nerecipe.util.showKeyboard
import ru.netology.nerecipe.viewModel.RecipeViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<RecipeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = RecipesAdapter(viewModel)
        binding.recipesRecyclerView.adapter = adapter
        viewModel.data.observe(this) { recipes ->
            adapter.submitList(recipes)
        }

        binding.saveButton.setOnClickListener {
            val description = binding.descriptionEdit.text.toString()
            viewModel.onSaveButtonClicked(description)
        }

        viewModel.currentRecipe.observe(this) { currentRecipe ->
            with(binding.descriptionEdit) {
                val description = currentRecipe?.description
                setText(description)
                if (description != null) {
                    requestFocus()
                    showKeyboard()
                } else {
                    clearFocus()
                    hideKeyboard()
                }
            }
        }
    }
}
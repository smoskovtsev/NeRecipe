package ru.netology.nerecipe.data.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nerecipe.R
import ru.netology.nerecipe.databinding.RecipeBinding
import ru.netology.nerecipe.dto.Recipe

internal class RecipesAdapter(
    private val onFavAdded: (Recipe) -> Unit
) : ListAdapter<Recipe, RecipesAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, onFavAdded)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: RecipeBinding, onFavAdded: (Recipe) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var recipe: Recipe

        init {
            binding.favorite.setOnClickListener { onFavAdded(recipe) }
        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe

            with(binding) {
                recipeName.text = recipe.name
                recipeAuthor.text = recipe.author
                recipeCategory.text = recipe.category
                recipeDescription.text = recipe.description

                favorite.setImageResource(getFavIconResId(recipe.favorite))
            }
        }

        @DrawableRes
        private fun getFavIconResId(liked: Boolean) =
            if (liked) R.drawable.ic_favorite_added_24 else R.drawable.ic_favorite_border_24
    }

    private object DiffCallback : DiffUtil.ItemCallback<Recipe>() {

        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem == newItem
    }
}
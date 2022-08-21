package ru.netology.nerecipe.data.impl

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nerecipe.data.RecipeRepository
import ru.netology.nerecipe.dto.Recipe
import kotlin.properties.Delegates

class FileRecipeRepository(
    private val application: Application
) : RecipeRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Recipe::class.java).type

    private val prefs = application.getSharedPreferences(
        "repo", Context.MODE_PRIVATE
    )
    private var nextId: Long by Delegates.observable(
        prefs.getLong(NEXT_ID_PREFS_KEY, 0L)
    ) { _, _, newValue ->
        prefs.edit { putLong(NEXT_ID_PREFS_KEY, newValue)}

    }

    private var recipes
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }
        set(value) {
            application.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).bufferedWriter().use {
                it.write(gson.toJson(value))
            }
            data.value = value
        }

    override val data: MutableLiveData<List<Recipe>>

    init {
        val recipesFile = application.filesDir.resolve(FILE_NAME)
        val recipes: List<Recipe> = if (recipesFile.exists()) {
            val inputStream = application.openFileInput(FILE_NAME)
            val reader = inputStream.bufferedReader()
            reader.use { gson.fromJson(it, type) }
        } else emptyList()
        data = MutableLiveData(recipes)
    }

    override fun addToFavorite(recipeId: Long) {
        recipes = recipes.map {
            if (it.id != recipeId) it
            else it.copy(
                favorite = !it.favorite
            )
        }
    }

    override fun deleteRecipe(recipeId: Long) {
        recipes = recipes.filterNot { it.id == recipeId} //or .filter {it.id != postId}
    }

    override fun addUpdateRecipe(recipe: Recipe) {
        if (recipe.id == RecipeRepository.NEW_RECIPE_ID) insert(recipe) else update(recipe)
    }

    private fun insert(recipe: Recipe) {
        recipes = listOf(
            recipe.copy(id = ++nextId)
        ) + recipes
    }

    private fun update(recipe: Recipe) {
        recipes = recipes.map {
            if (it.id == recipe.id) recipe else it
        }
    }

    private companion object {
        const val RECIPES_PREFS_KEY = "recipes"
        const val NEXT_ID_PREFS_KEY = "nextId"
        const val FILE_NAME = "recipes.json"
    }
}
package ru.netology.nerecipe.dto

data class Recipe(
    val id: Long,
    val name: String,
    val author: String,
    val category: String,
    val description: String,
    var favorite: Boolean = false
)
package com.example.myrecipes.recipes.dataModel

sealed class RecipeState<out T> {
    object Loading : RecipeState<Nothing>()
    data class Success<out T>(val data: T) : RecipeState<T>()
    data class Error(val message: String) : RecipeState<Nothing>()
}
package com.example.myrecipes.recipes.network

import com.example.myrecipes.recipes.dataModel.Recipe
import com.example.myrecipes.recipes.dataModel.RecipeState

interface RecipeDataSource {
    suspend fun fetchRecipes(): RecipeState<List<Recipe>>
}

package com.example.myrecipes.recipes.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipes.recipes.dataModel.Recipe
import com.example.myrecipes.recipes.dataModel.RecipeState
import com.example.myrecipes.recipes.network.RecipeApiService
import com.example.myrecipes.recipes.network.RecipeDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * ViewModel responsible for managing the state of recipe data and providing it to the UI.
 *
 * @param apiService An instance of [RecipeApiService] for fetching recipes from the remote API.
 */
@HiltViewModel
class RecipeViewModel @Inject constructor(private val apiService: RecipeDataSource) : ViewModel() {

    // MutableStateFlow to hold the current state of recipe data
    private val _recipeStateFlow = MutableStateFlow<RecipeState<List<Recipe>>>(RecipeState.Loading)

    // Expose an immutable StateFlow to the UI to observe changes in recipe data
    val recipeStateFlow: StateFlow<RecipeState<List<Recipe>>> = _recipeStateFlow

    /**
     * Initializes the ViewModel and triggers the initial fetch of recipes from the API.
     */
    init {
        fetchRecipes()
    }

    /**
     * Fetches recipes from the API using [apiService] and updates the [_recipeStateFlow] accordingly.
     */
    private fun fetchRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = apiService.fetchRecipes()
            _recipeStateFlow.value = result
        }
    }
}
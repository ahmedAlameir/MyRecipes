package com.example.myrecipes.recipes.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myrecipes.recipes.dataModel.Recipe
import com.example.myrecipes.recipes.dataModel.RecipeState
import com.example.myrecipes.recipes.screen.ui.RecipeItem
import com.example.myrecipes.recipes.viewModel.RecipeViewModel

/**
 * Composable function for displaying a screen that shows a list of recipes.
 *
 * @param viewModel The ViewModel responsible for managing recipe data.
 */
@Composable
fun RecipesScreen(
    viewModel: RecipeViewModel = hiltViewModel()
) {
    // Collect the current recipe state as a Composable state

    val recipeState by viewModel.recipeStateFlow.collectAsState()
    Scaffold(
        topBar = {
            Row(
                Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Recipes",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF121212),
                    )
                )
            }

        }

    ) {
        // Display the RecipeList Composable with the collected recipe state

        RecipeList(recipeState, Modifier.padding(it))
    }
}
/**
 * Composable function for displaying a list of recipes based on the provided [RecipeState].
 *
 * @param recipeState The current state of recipe data, including loading, success, or error.
 * @param modifier The modifier for customizing the layout.
 */
@Composable
fun RecipeList(recipeState: RecipeState<List<Recipe>>, modifier: Modifier = Modifier) {
    when (recipeState) {
        is RecipeState.Success -> {
            val recipes = recipeState.data
            LazyColumn(
                modifier = modifier.fillMaxSize()
            ) {
                // Display each recipe item in the list
                items(recipes) { recipe ->
                    RecipeItem(recipe, Modifier.padding(horizontal = 16.dp))
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }

        is RecipeState.Error -> {
            val errorMessage = recipeState.message
            // Display an error message in case of an error state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = errorMessage, style = MaterialTheme.typography.bodyLarge)
            }
        }

        is RecipeState.Loading -> {
            // Display a loading indicator while fetching data
            Box(modifier = modifier.fillMaxSize()){
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        }
    }
}


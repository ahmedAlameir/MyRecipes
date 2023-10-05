package com.example.myrecipes.recipes.screen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myrecipes.R
import com.example.myrecipes.image.ui.ImageFromUrl
import com.example.myrecipes.core.utils.parseDuration
import com.example.myrecipes.recipes.dataModel.Recipe
import com.example.myrecipes.ui.theme.MyRecipesTheme

/**
 * Composable function for displaying a recipe item inside a card.
 *
 * @param recipe The recipe to display.
 * @param modifier The modifier for customizing the layout.
 */
@Composable
fun RecipeItem(recipe: Recipe, modifier: Modifier = Modifier) {

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),

        modifier = modifier
    ) {
        // Display the content of the recipe item
        RecipeItemContent(recipe)
    }
}
/**
 * Composable function for the content of a recipe item inside a card.
 *
 * @param recipe The recipe to display.
 */
@Composable
fun RecipeItemContent(recipe: Recipe) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFCACACA), Color(0xFF000000))
                )
            )
            .clickable {
                // Toggle the expansion state when clicked
                isExpanded = !isExpanded
            }
    ) {
        RecipeImage(recipe)
        Spacer(modifier = Modifier.height(8.dp))
        RecipeTitle(recipe)
        // Display the recipe description if expanded
        if (isExpanded){
            RecipeDescription(recipe)
        }
        RecipeDetails(recipe)

    }
}
/**
 * Composable function for displaying the recipe image.
 *
 * @param recipe The recipe containing image information.
 */
@Composable
fun RecipeImage(recipe: Recipe) {
    ImageFromUrl(
        imageUrl = recipe.image,
        placeholderResId = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
    )
}
/**
 * Composable function for displaying the recipe title.
 *
 * @param recipe The recipe containing title information.
 */
@Composable
fun RecipeTitle(recipe: Recipe) {
    Column {
        Text(
            text = recipe.name,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = Color.White,
            ),
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        )
        Text(
            text = recipe.headline,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFFD3D3D3),
            ),
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .fillMaxWidth()
        )
    }

}
/**
 * Composable function for displaying the recipe description.
 *
 * @param recipe The recipe containing description information.
 */
@Composable
fun RecipeDescription(recipe: Recipe) {
    Spacer(modifier = Modifier.height(4.dp))

    Column{
        Text(
            text = "Description:",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFFD3D3D3),
            ),
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = recipe.description,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFAC0C0C0),
            ),
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .fillMaxWidth()
        )
    }

}
/**
 * Composable function for displaying the recipe description.
 *
 * @param recipe The recipe containing description information.
 */
@Composable
fun RecipeDetails(recipe: Recipe) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            RecipeDetailItem(
                iconResId = R.drawable.ic_timer,
                text = "${recipe.time.parseDuration()} min."
            )
            Spacer(modifier = Modifier.width(8.dp))
            RecipeDetailItem(
                iconResId = R.drawable.ic_calories,
                text = "${recipe.calories}."
            )
        }
        Difficulty(recipe.difficulty)
    }
}

@Composable
fun RecipeDetailItem(iconResId: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                color = Color.Gray,
            )
        )
    }
}
/**
 * Composable function for displaying the recipe difficulty level.
 *
 * @param difficulty The difficulty level (0 to 3) of the recipe.
 * @param modifier The modifier for customizing the layout.
 */
@Composable
fun Difficulty(difficulty: Int, modifier: Modifier = Modifier) {
    val (text, textColor) = when (difficulty) {
        0 -> Pair("Easy", Color.Green)
        1 -> Pair("Medium", Color.Yellow)
        2 -> Pair("Hard", Color(0xFFFF9800))
        3 -> Pair("Extreme", Color.Red)

        else -> Pair("Unknown", Color.Gray)
    }

    Text(
        text = text,
        style = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight(400),
            color = textColor,
        ),
        modifier = modifier
    )
}



@Preview(showBackground = true)
@Composable
fun RecipeItemPreview() {
    MyRecipesTheme {
        RecipeItem(
            Recipe(
                calories = "458 kcal",
                carbos = "29 g",
                description = "World-renowned people generally all have one thing in common: a legacy...",
                difficulty = 1,
                fats = "6 g",
                headline = "with Tomato Concasse and Crispy Potatoes",
                id = "53314276ff604d28828b456b",
                image = "https://img.hellofresh.com/f_auto,q_auto/hellofresh_s3/image/53314276ff604d28828b456b.jpg",
                name = "Simple Sumptuous Sea Bream",
                proteins = "29 g",
                thumb = "https://img.hellofresh.com/f_auto,q_auto,w_300/hellofresh_s3/image/53314276ff604d28828b456b.jpg",
                time = "PT35M"
            ),
        )
    }
}





package com.example.myrecipes.recipes.network

import com.example.myrecipes.recipes.dataModel.Recipe
import com.example.myrecipes.recipes.dataModel.RecipeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Service class for fetching recipes from a remote API.
 *
 */
class RecipeApiService(private val apiUrl: String) : RecipeDataSource{

    /**
     * Fetches recipes from the remote API.
     *
     * @return A [RecipeState] representing the result of the fetch operation.
     */
    override suspend fun fetchRecipes(): RecipeState<List<Recipe>> {
        return try {
            val jsonArray = makeApiRequest()
            val recipes = parseRecipes(jsonArray)
            RecipeState.Success(recipes)
        } catch (e: IOException) {
            RecipeState.Error("An IO error occurred: ${e.message}")
        } catch (e: JSONException) {
            RecipeState.Error("JSON parsing error: ${e.message}")
        } catch (e: Exception) {
            RecipeState.Error("An error occurred: ${e.message}")
        }
    }

    /**
     * Makes a network request to the API and returns the JSON response as a JSONArray.
     *
     * @return The JSON response as a JSONArray.
     */
    private suspend fun makeApiRequest(): JSONArray = withContext(Dispatchers.IO) {
        val url = URL(apiUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = connection.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            val responseStringBuilder = StringBuilder()

            var line: String? = bufferedReader.readLine()
            while (line != null) {
                responseStringBuilder.append(line)
                line = bufferedReader.readLine()
            }

            val jsonString = responseStringBuilder.toString()
            JSONArray(jsonString)
        } else {
            throw Exception("HTTP Request failed with response code $responseCode")
        }
    }

    /**
     * Parses a JSONArray containing recipe data into a list of [Recipe] objects.
     *
     * @param jsonArray The JSONArray to parse, containing recipe data.
     * @return A list of [Recipe] objects parsed from the JSON data.
     */
    private fun parseRecipes(jsonArray: JSONArray): List<Recipe> {
        val recipes = mutableListOf<Recipe>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val calories = jsonObject.getString("calories")
            val carbos = jsonObject.getString("carbos")
            val description = jsonObject.getString("description")
            val difficulty = jsonObject.getInt("difficulty")
            val fats = jsonObject.getString("fats")
            val headline = jsonObject.getString("headline")
            val id = jsonObject.getString("id")
            val image = jsonObject.getString("image")
            val name = jsonObject.getString("name")
            val proteins = jsonObject.getString("proteins")
            val thumb = jsonObject.getString("thumb")
            val time = jsonObject.getString("time")

            val recipe = Recipe(
                calories,
                carbos,
                description,
                difficulty,
                fats,
                headline,
                id,
                image,
                name,
                proteins,
                thumb,
                time
            )
            recipes.add(recipe)
        }

        return recipes
    }
}
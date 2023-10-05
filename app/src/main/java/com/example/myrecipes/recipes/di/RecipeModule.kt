package com.example.myrecipes.recipes.di

import com.example.myrecipes.recipes.network.RecipeApiService
import com.example.myrecipes.recipes.network.RecipeDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RecipeModule{
    @Provides
    fun providesRecipeApi(): RecipeDataSource {
        val apiUrl = "https://hf-android-app.s3-eu-west-1.amazonaws.com/android-test/recipes.json"
        return RecipeApiService(apiUrl)   }
}
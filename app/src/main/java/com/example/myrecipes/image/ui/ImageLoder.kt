package com.example.myrecipes.image.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.myrecipes.image.cache.ImageCache
import com.example.myrecipes.image.download.ImageDownloader

/**
 * A Composable function to load an image from the provided URL with caching support.
 *
 * @param imageUrl The URL of the image to load.
 * @param modifier Modifier for styling the Composable.
 * @param placeholderResId Optional resource ID for a placeholder image while loading.
 */
@Composable
fun ImageFromUrl(
    imageUrl: String,
    modifier: Modifier = Modifier,
    placeholderResId: Int?,
    contentScale : ContentScale = ContentScale.Fit
) {
    val imageDownloader = ImageDownloader()
    val imageCache = ImageCache(LocalContext.current)
    LoadImageWithCaching(
        imageUrl = imageUrl,
        modifier = modifier,
        imageDownloader = imageDownloader ,
        imageCache =imageCache,
        placeholderResId = placeholderResId,
        contentScale = contentScale
    )
}

/**
 * A Composable function to load an image from a URL with caching and placeholder support.
 * This function should be used within LoadImage.
 *
 * @param imageUrl The URL of the image to load.
 * @param modifier Modifier for styling the Composable.
 * @param imageDownloader The image downloader instance.
 * @param imageCache The image cache instance.
 * @param placeholderResId Optional resource ID for a placeholder image while loading.
 */
@Composable
private fun LoadImageWithCaching(
    imageUrl: String,
    modifier: Modifier = Modifier,
    imageDownloader: ImageDownloader,
    imageCache: ImageCache,
    placeholderResId: Int?,
    contentScale : ContentScale = ContentScale.Fit
) {
    var imageBitmap by remember(imageUrl) { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(imageUrl) {
        // Try to load from cache
        imageBitmap = imageCache.loadCachedImage(imageUrl)?.asImageBitmap()

        if (imageBitmap == null) {
            // If not cached, download and cache the image
            val byteArray = imageDownloader.downloadImage(imageUrl)

            if (byteArray != null) {
                imageBitmap = byteArray.asImageBitmap()
                imageCache.cacheImage(imageUrl, byteArray)
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (imageBitmap != null) {
            Image(
                contentScale = contentScale,
                bitmap = imageBitmap!!,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Display a CircularProgressIndicator or a placeholder image if available
            if (placeholderResId != null) {
                Image(
                    painter = painterResource(id = placeholderResId),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}




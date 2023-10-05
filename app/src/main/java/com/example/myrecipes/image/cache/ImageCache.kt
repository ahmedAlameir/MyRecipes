package com.example.myrecipes.image.cache

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


/**
 * A utility class for caching and loading images from the cache.
 *
 * @property context The Android application context.
 */
class ImageCache(private val context: Context) {

    /**
     * Cache the given Bitmap image to a file.
     *
     * @param url The URL associated with the image.
     * @param bitmap The image to be cached.
     */
    fun cacheImage(url: String, bitmap: Bitmap) {
        val cacheFile = File(getCacheDir(), getFileNameFromUrl(url))
        saveImageToCache(bitmap, cacheFile)
    }

    /**
     * Load a cached Bitmap image from the cache directory.
     *
     * @param url The URL associated with the image.
     * @return The cached image as a Bitmap, or null if not found.
     */
    fun loadCachedImage(url: String): Bitmap? {
        val cacheFile = File(getCacheDir(), getFileNameFromUrl(url))
        if (cacheFile.exists()) {
            try {
                val inputStream = FileInputStream(cacheFile)
                return BitmapFactory.decodeStream(inputStream)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    private fun getCacheDir(): File {
        return context.cacheDir
    }

    private fun getFileNameFromUrl(url: String): String {
        val parts = url.split("/")
        return parts.lastOrNull() ?: "default_filename.png"
    }

    private fun saveImageToCache(bitmap: Bitmap, cacheFile: File) {
        val outputStream = FileOutputStream(cacheFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()
    }
}
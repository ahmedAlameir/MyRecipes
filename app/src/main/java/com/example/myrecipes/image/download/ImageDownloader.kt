package com.example.myrecipes.image.download

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
/**
 * A utility class for downloading images from a given URL.
 */
class ImageDownloader {

    /**
     * Download an image from the specified URL and return it as a Bitmap.
     *
     * @param url The URL of the image to download.
     * @return The downloaded image as a Bitmap, or null if an error occurs.
     */
    suspend fun downloadImage(url: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()

                val inputStream: InputStream = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()

                bitmap
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }
}
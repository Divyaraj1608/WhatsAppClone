package com.example.whatsappclone.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import androidx.core.graphics.scale

/**
 * Firebase Storage needs a paid plan, so profile pictures are converted to a Base64 string,
 * saved in the Realtime Database, and decoded back to a Bitmap when shown.
 */
object ImageUtils {

    /** Bitmap -> Base64 string (scaled down so it fits comfortably in the database). */
    fun bitmapToBase64(bitmap: Bitmap, maxSize: Int = 512, quality: Int = 70): String {
        val ratio = minOf(1f, maxSize.toFloat()
                                        / maxOf(bitmap.width, bitmap.height))
        val scaled = if (ratio < 1f) {
            bitmap.scale((bitmap.width * ratio).toInt(),
                        (bitmap.height * ratio).toInt())
        } else bitmap

        val stream = ByteArrayOutputStream()
        scaled.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        return Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP)
    }

    /** Base64 string -> Bitmap (null if it cannot be decoded). */
    fun base64ToBitmap(base64: String): Bitmap? = try {
        val bytes = Base64.decode(base64, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    } catch (e: Exception) {
        null
    }





    /** Gallery Uri -> Bitmap (minSdk is 28, so ImageDecoder is always available). */
    fun uriToBitmap(context: Context, uri: Uri): Bitmap? = try {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
            decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
            decoder.isMutableRequired = true
        }
    } catch (e: Exception) {
        null
    }
}

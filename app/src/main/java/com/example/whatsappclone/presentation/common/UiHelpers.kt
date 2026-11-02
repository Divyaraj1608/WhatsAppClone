package com.example.whatsappclone.presentation.common

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import com.example.whatsappclone.R
import com.example.whatsappclone.ui.theme.LightGreen

/** Text field with a transparent background and a green underline (WhatsApp style). */
@Composable
fun whatsAppTextFieldColors(): TextFieldColors = TextFieldDefaults.colors(
    focusedContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent,
    disabledContainerColor = Color.Transparent,
    focusedIndicatorColor = LightGreen,
    unfocusedIndicatorColor = LightGreen
)

/** Circular profile picture from a Bitmap; falls back to the `profile_placeholder` drawable. */
@Composable
fun Avatar(bitmap: Bitmap?, size: Dp = 60.dp, modifier: Modifier = Modifier) {
    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier.size(size).clip(CircleShape)
        )
    } else {
        ResImage(R.drawable.profile_placeholder, size, modifier)
    }
}

/** Cropped drawable-resource image (circle by default). */
@Composable
fun ResImage(
    @DrawableRes id: Int,
    size: Dp = 60.dp,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape
) {
    Image(
        painter = painterResource(id),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier.size(size).clip(shape)
    )
}

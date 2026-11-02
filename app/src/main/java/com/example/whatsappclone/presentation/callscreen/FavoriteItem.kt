package com.example.whatsappclone.presentation.callscreen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.presentation.common.ResImage

data class Favorite(@DrawableRes val image: Int, val name: String)

@Composable
fun FavoriteItem(favorite: Favorite) {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 4.dp).width(72.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ResImage(favorite.image, size = 60.dp)
        Spacer(Modifier.height(4.dp))
        Text(
            text = favorite.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

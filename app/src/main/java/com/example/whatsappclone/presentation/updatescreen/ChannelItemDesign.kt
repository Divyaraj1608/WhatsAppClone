package com.example.whatsappclone.presentation.updatescreen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.presentation.common.ResImage
import com.example.whatsappclone.ui.theme.LightGreen

data class Channels(@DrawableRes val image: Int, val name: String, val description: String)

/** Channel row with a Follow / Following toggle button. */
@Composable
fun ChannelItemDesign(channel: Channels) {
    var isFollowing by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ResImage(channel.image, size = 60.dp)
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(channel.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(channel.description, fontSize = 14.sp, color = Color.Gray)
        }
        Button(
            onClick = { isFollowing = !isFollowing },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isFollowing) Color.LightGray else LightGreen
            ),
            modifier = Modifier.height(36.dp)
        ) {
            Text(
                text = if (isFollowing) "Following" else "Follow",
                color = if (isFollowing) Color.Black else Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

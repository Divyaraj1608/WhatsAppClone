package com.example.whatsappclone.presentation.updatescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.presentation.common.Avatar
import com.example.whatsappclone.R
import com.example.whatsappclone.presentation.common.ResImage
import com.example.whatsappclone.ui.theme.LightGreen

data class StatusData(@DrawableRes val image: Int, val name: String, val time: String)

/** "My Status" row with the green "+" badge on the avatar. */
@Composable
fun MyStatus() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Avatar(bitmap = null, size = 60.dp)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(LightGreen),
                contentAlignment = Alignment.Center
            ) {
                Icon(painterResource(R.drawable.baseline_add_24), contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
            }
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text("My Status", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text("Tap to add status update", fontSize = 14.sp, color = Color.Gray)
        }
    }
}

/** A friend's status row. */
@Composable
fun StatusItem(status: StatusData) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ResImage(status.image, size = 60.dp)
        Spacer(Modifier.width(12.dp))
        Column {
            Text(status.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(status.time, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

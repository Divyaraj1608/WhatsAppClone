package com.example.whatsappclone.presentation.callscreen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.R
import com.example.whatsappclone.presentation.common.ResImage
import com.example.whatsappclone.ui.theme.LightGreen

data class Call(@DrawableRes val image: Int, val name: String, val time: String, val isMissed: Boolean)

/** Recent-call row: avatar | name + (missed/answered arrow, time) | call button. */
@Composable
fun CallItemDesign(call: Call) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ResImage(call.image, size = 60.dp)
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(call.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                // red arrow for a missed call, green for one that was picked up
                Icon(
                    painter = painterResource(R.drawable.baseline_call_missed_24),
                    contentDescription = null,
                    tint = if (call.isMissed) Color.Red else LightGreen,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(call.time, fontSize = 14.sp, color = Color.Gray)
            }
        }
        IconButton(onClick = { }) {
            Icon(painterResource(R.drawable.outline_phone_24), contentDescription = "Call", modifier = Modifier.size(24.dp))
        }
    }
}

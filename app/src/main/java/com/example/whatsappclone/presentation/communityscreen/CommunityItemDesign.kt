package com.example.whatsappclone.presentation.communityscreen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.presentation.common.ResImage

data class Communities(@DrawableRes val image: Int, val name: String, val memberCount: String)

@Composable
fun CommunityItemDesign(community: Communities) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ResImage(community.image, size = 60.dp, shape = RoundedCornerShape(16.dp))
        Spacer(Modifier.width(12.dp))
        Column {
            Text(community.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(community.memberCount, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

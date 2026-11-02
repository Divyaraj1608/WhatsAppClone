package com.example.whatsappclone.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclone.R



/**
 * Shared top bar for Home / Updates / Communities / Calls:
 * title + optional camera + search (toggles a text field, cross clears it) + 3-dot dropdown menu.
 */


data class MenuAction(val label: String, val onClick: () -> Unit = {})

@Composable
fun AppTopBar(
    title: String,
    titleColor: Color = Color.Black,
    showCamera: Boolean = false,
    menuItems: List<MenuAction> = emptyList(),
    onSearchQueryChange: (String) -> Unit = {}
) {
    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSearching) {
                TextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                        onSearchQueryChange(it)
                    },
                    placeholder = { Text("Search", color = Color.Gray) },
                    singleLine = true,
                    colors = whatsAppTextFieldColors(),
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = {
                    isSearching = false
                    searchText = ""
                    onSearchQueryChange("")
                }) {
                    Icon(painterResource(R.drawable.cross), contentDescription = "Close search", modifier = Modifier.size(15.dp))
                }
            } else {
                Text(
                    text = title,
                    color = titleColor,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(Modifier.weight(1f))
                if (showCamera) {
                    IconButton(onClick = { }) {
                        Icon(painterResource(R.drawable.camera), contentDescription = "Camera", modifier = Modifier.size(24.dp))
                    }
                }
                IconButton(onClick = { isSearching = true }) {
                    Icon(painterResource(R.drawable.search), contentDescription = "Search", modifier = Modifier.size(24.dp))
                }
                if (menuItems.isNotEmpty()) {
                    Box {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(painterResource(R.drawable.more), contentDescription = "More", modifier = Modifier.size(24.dp))
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            menuItems.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(item.label) },
                                    onClick = {
                                        showMenu = false
                                        item.onClick()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
        HorizontalDivider(color = Color.LightGray)
    }
}

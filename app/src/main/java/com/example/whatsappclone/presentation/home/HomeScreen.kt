package com.example.whatsappclone.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.example.whatsappclone.R
import com.example.whatsappclone.presentation.bottomnavigation.BottomNavigation
import com.example.whatsappclone.presentation.navigation.Routes
import com.example.whatsappclone.presentation.viewmodels.BaseViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    baseViewModel: BaseViewModel
) {
    var showPopup by remember { mutableStateOf(false) }
    val chatData by baseViewModel.chatList.collectAsState()
    val userId = FirebaseAuth.getInstance().currentUser?.uid


    if (userId != null) {
        LaunchedEffect(userId) {
            baseViewModel.getChatForUser(userId) { chats ->

            }
        }
    }



    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showPopup = true },
                containerColor = colorResource(id = R.color.light_green),
                contentColor = Color.White,
                modifier = Modifier.size(65.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_chat_icon),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    tint = Color.White
                )
            }
        },
        bottomBar = {
            BottomNavigation(
                navController = navController,
                selectedItem = 0,
                onClick = { index ->
                    when (index) {
                        0 -> navController.navigate(Routes.HomeScreen)
                        1 -> navController.navigate(Routes.UpdateScreen)
                        2 -> navController.navigate(Routes.CommunitiesScreen)
                        3 -> navController.navigate(Routes.CallScreen)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // For top bar
            Box(modifier = Modifier.fillMaxWidth()) {
                if (isSearching) {
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = { Text(text = "Search", color = Color.Gray) },
                        singleLine = true,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 12.dp)
                            .fillMaxWidth(0.8f),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )
                } else {
                    Text(
                        text = "WhatsApp",
                        fontSize = 28.sp,
                        color = colorResource(id = R.color.light_green),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 12.dp)
                    )
                }

                // Right side icons of top Bar
                Row(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    if (isSearching) {
                        IconButton(onClick = {
                            isSearching = false
                            searchText = ""
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.cross),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {
                        IconButton(onClick = { isSearching = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.search),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Box {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(
                                painter = painterResource(id = R.drawable.more),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            DropdownMenuItem(
                                text = { Text("New group") },
                                onClick = { showMenu = false }
                            )
                            DropdownMenuItem(
                                text = { Text("New broadcast") },
                                onClick = { showMenu = false }
                            )
                            DropdownMenuItem(
                                text = { Text("Linked devices") },
                                onClick = { showMenu = false }
                            )
                            DropdownMenuItem(
                                text = { Text("Starred messages") },
                                onClick = { showMenu = false }
                            )
                            DropdownMenuItem(
                                text = { Text("Settings") },
                                onClick = {
                                    showMenu = false
                                    navController.navigate(Routes.SettingsScreen)
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            // Pop up dialog
            if (showPopup) {
                AddUserPopup(
                    onDismiss = { showPopup = false },
                    onUserAdded = { newUser ->
                        baseViewModel.addChat(newUser)
                    },
                    baseViewModel = baseViewModel
                )
            }

            // Chat List
            LazyColumn {
                items(chatData) { chat ->
                    ChatListBox(
                        chatListModel = chat,
                        onClick = {
                            navController.navigate(
                                Routes.ChatScreen.createRoute(
                                    phoneNumber = chat.phoneNumber ?: "ok"
                                )
                            )
                        },
                        baseViewModel = baseViewModel
                    )
                }
            }
        }
    }
}


/*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.whatsappclone.R
import com.example.whatsappclone.presentation.bottomnavigation.BottomNavigation
import com.example.whatsappclone.presentation.common.AppTopBar
import com.example.whatsappclone.presentation.common.MenuAction
import com.example.whatsappclone.presentation.navigation.Routes
import com.example.whatsappclone.presentation.viewmodels.BaseViewModel
import com.example.whatsappclone.ui.theme.LightGreen


@Composable
fun HomeScreen(
    navController: NavHostController,
    baseViewModel: BaseViewModel = hiltViewModel()
) {
    val chatData by baseViewModel.chatList.collectAsState()
    var showPopup by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val visibleChats = if (searchQuery.isBlank()) chatData
    else chatData.filter { it.name?.contains(searchQuery, ignoreCase = true) == true }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            AppTopBar(
                title = "WhatsApp",
                titleColor = LightGreen,
                showCamera = true,
                onSearchQueryChange = { searchQuery = it },
                menuItems = listOf(
                    MenuAction("New group"),
                    MenuAction("New broadcast"),
                    MenuAction("Linked devices"),
                    MenuAction("Starred messages"),
                    MenuAction("Settings") { navController.navigate(Routes.SettingsScreen) }
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showPopup = true },
                containerColor = LightGreen,
                contentColor = Color.White,
                modifier = Modifier.size(65.dp)
            ) {
                Icon(painterResource(R.drawable.add_chat_icon), contentDescription = "New chat", modifier = Modifier.size(28.dp))
            }
        },
        bottomBar = { BottomNavigation(navController, selectedItem = 0) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            LazyColumn {
                items(visibleChats) { chat ->
                    ChatListBox(chat = chat) {
                        navController.navigate(Routes.ChatScreen(chat.phoneNumber ?: ""))
                    }
                }
            }
        }

        if (showPopup) {
            AddUserPopup(
                onDismiss = { showPopup = false },
                onUserAdd = { newUser -> baseViewModel.addChat(newUser) },
                baseViewModel = baseViewModel
            )
        }
    }
}
*/


package com.example.whatsappclone.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.whatsappclone.R
import com.example.whatsappclone.model.Message
import com.example.whatsappclone.presentation.viewmodels.BaseViewModel
import com.example.whatsappclone.ui.theme.DarkGreen
import com.example.whatsappclone.ui.theme.LightGreen
import com.example.whatsappclone.ui.theme.MintGreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** One-to-one chat backed by BaseViewModel's Firebase Realtime Database methods. */
@Composable
fun ChatScreen(
    navController: NavHostController,
    otherPhoneNumber: String,
    baseViewModel: BaseViewModel = hiltViewModel()
) {
    val myPhone = baseViewModel.currentUserPhone()
    val messages = remember(otherPhoneNumber, myPhone) { mutableStateListOf<Message>() }
    var input by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    DisposableEffect(otherPhoneNumber, myPhone) {
        if (myPhone.isNotBlank() && otherPhoneNumber.isNotBlank()) {
            val stopListening = baseViewModel.getMessage(myPhone,
                                otherPhoneNumber) { incoming ->

                if (messages.none { it.senderPhoneNumber == incoming.senderPhoneNumber
                            && it.timeStamp == incoming.timeStamp
                            && it.message == incoming.message }) {
                    messages.add(incoming)
                    messages.sortBy { it.timeStamp }
                }
            }
            onDispose { stopListening() }
        } else {
            onDispose { }
        }
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.lastIndex)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .imePadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkGreen)
                .statusBarsPadding()
                .padding(vertical = 5.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_back_24),
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = otherPhoneNumber,
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(text = "tap here for contact info", color = Color.White.copy(alpha = 0.75f), fontSize = 11.sp)
            }
        }

        if (messages.isEmpty()) {
            Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = if (myPhone.isBlank()) "Sign in to start chatting" else "No messages yet. Say hello!",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(24.dp)
                )
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 10.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                items(messages) { message ->
                    val isMine = message.senderPhoneNumber == myPhone
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
                    ) {
                        Surface(
                            color = if (isMine) MintGreen else Color.White,
                            shape = RoundedCornerShape(
                                topStart = 12.dp, topEnd = 12.dp,
                                bottomStart = if (isMine) 12.dp else 3.dp,
                                bottomEnd = if (isMine) 3.dp else 12.dp
                            ),
                            shadowElevation = 1.dp,
                            modifier = Modifier.widthIn(max = 300.dp)
                        ) {
                            Column(Modifier.padding(horizontal = 11.dp, vertical = 7.dp)) {
                                Text(text = message.message, color = Color(0xFF202124), fontSize = 15.sp)
                                Text(
                                    text = formatMessageTime(message.timeStamp),
                                    color = Color.DarkGray,
                                    fontSize = 10.sp,
                                    modifier = Modifier.align(Alignment.End).padding(top = 3.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        HorizontalDivider(color = Color(0xFFE4E4E4))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF7F7F7))
                .navigationBarsPadding()
                .padding(horizontal = 8.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = input,
                onValueChange = { input = it },
                placeholder = { Text("Message") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(24.dp),
                maxLines = 5,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                )
            )
            IconButton(
                enabled = input.isNotBlank() && myPhone.isNotBlank(),
                onClick = {
                    val text = input.trim()
                    if (text.isNotEmpty()) {
                        baseViewModel.sendMessage(myPhone, otherPhoneNumber, text)
                        input = ""
                    }
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_send_24),
                    contentDescription = "Send message",
                    tint = LightGreen
                )
            }
        }
    }
}

private fun formatMessageTime(timestamp: Long): String =
    if (timestamp <= 0L) "" else SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(timestamp))




















/*
package com.example.whatsappclone.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.whatsappclone.R
import com.example.whatsappclone.model.Message
import com.example.whatsappclone.presentation.common.whatsAppTextFieldColors
import com.example.whatsappclone.presentation.viewmodels.BaseViewModel
import com.example.whatsappclone.ui.theme.DarkGreen
import com.example.whatsappclone.ui.theme.LightGreen
import com.example.whatsappclone.ui.theme.MintGreen


@Composable
fun ChatScreen(
    navController: NavHostController,
    otherPhoneNumber: String,
    baseViewModel: BaseViewModel = hiltViewModel()
) {
    val myPhone = baseViewModel.currentUserPhone()
    val messages = remember { mutableStateListOf<Message>() }
    var input by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    // start / stop listening for messages with this contact
    DisposableEffect(otherPhoneNumber) {
        val stopListening = baseViewModel.getMessages(myPhone, otherPhoneNumber) { messages.add(it) }
        onDispose { stopListening() }
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.lastIndex)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .imePadding()
    ) {
        // top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkGreen)
                .statusBarsPadding()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(painterResource(R.drawable.baseline_arrow_back_24), contentDescription = "Back", tint = Color.White)
            }
            Text(otherPhoneNumber, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        // messages
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(messages) { msg ->
                val mine = msg.senderPhoneNumber == myPhone
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = if (mine) Alignment.CenterEnd else Alignment.CenterStart
                ) {
                    Text(
                        text = msg.message,
                        modifier = Modifier
                            .background(if (mine) MintGreen else Color(0xFFEFEFEF), RoundedCornerShape(10.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }

        HorizontalDivider(color = Color.LightGray)
        Row(
            modifier = Modifier.fillMaxWidth().navigationBarsPadding().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = input,
                onValueChange = { input = it },
                placeholder = { Text("Message") },
                colors = whatsAppTextFieldColors(),
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                if (input.isNotBlank()) {
                    baseViewModel.sendMessage(myPhone, otherPhoneNumber, input.trim())
                    input = ""
                }
            }) {
                Icon(painterResource(R.drawable.baseline_send_24), contentDescription = "Send", tint = LightGreen)
            }
        }
    }
}

 */

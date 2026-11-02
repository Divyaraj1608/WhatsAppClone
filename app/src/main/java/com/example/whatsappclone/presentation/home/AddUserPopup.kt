package com.example.whatsappclone.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.whatsappclone.R
import com.example.whatsappclone.model.ChatListModel
import com.example.whatsappclone.presentation.viewmodels.BaseViewModel

@Composable
fun AddUserPopup(
    onDismiss: () -> Unit,
    onUserAdded: (ChatListModel) -> Unit,
    baseViewModel: BaseViewModel
) {
    var phoneNumber by remember { mutableStateOf("") }
    var userFound by remember { mutableStateOf<ChatListModel?>(null) }
    var isSearching by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Enter Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    isSearching = true
                    baseViewModel.searchUserByPhoneNumber(phoneNumber) { user ->
                        isSearching = false
                        if (user != null) {
                            userFound = user
                        } else {
                            userFound = null
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.light_green)
                )
            ) {
                Text("Search")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { onDismiss() },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.light_green)
                )
            ) {
                Text("Cancel")
            }
        }

        if (isSearching) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Searching...", color = Color.Gray)
        }

        userFound?.let { user ->
            Spacer(modifier = Modifier.height(8.dp))
            Column {
                Text("User Found: ${user.name}")
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = {
                        onUserAdded(user)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.light_green)
                    )
                ) {
                    Text("Add to Chat")
                }
            }
        } ?: run {
            if (!isSearching && phoneNumber.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("No user found with this phone number", color = Color.Gray)
            }
        }
    }
}















/*

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.whatsappclone.model.ChatListModel
import com.example.whatsappclone.presentation.common.whatsAppTextFieldColors
import com.example.whatsappclone.presentation.viewmodels.BaseViewModel
import com.example.whatsappclone.ui.theme.DarkGreen
import com.example.whatsappclone.ui.theme.LightGreen

/**
 * Dialog opened by the floating button: type a full phone number (with country code),
 * press Search, and if the user exists add them to your chat list.
 */
@Composable
fun AddUserPopup(
    onDismiss: () -> Unit,
    onUserAdd: (ChatListModel) -> Unit,
    baseViewModel: BaseViewModel
) {
    var phoneNumber by remember { mutableStateOf("") }
    var userFound by remember { mutableStateOf<ChatListModel?>(null) }
    var isSearching by remember { mutableStateOf(false) }
    var hasSearched by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = { Text("Add a contact", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Enter phone number (+91...)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    colors = whatsAppTextFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        isSearching = true
                        baseViewModel.searchUserByPhoneNumber(phoneNumber.trim()) { user ->
                            userFound = user
                            isSearching = false
                            hasSearched = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = LightGreen),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Search", color = Color.White)
                }
                Spacer(Modifier.height(12.dp))

                if (isSearching) {
                    Text("Searching...", color = Color.Gray)
                }
                userFound?.let { user ->
                    Text("User found: ${user.name}", color = DarkGreen, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = {
                            onUserAdd(user)
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Add to chat")
                    }
                }
                if (hasSearched && !isSearching && userFound == null) {
                    Text("No user found with this phone number yet.", color = Color.Gray)
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel", color = DarkGreen) }
        }
    )
}
 */



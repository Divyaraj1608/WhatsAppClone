package com.example.whatsappclone.presentation.viewmodels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.example.whatsappclone.model.ChatListModel
import com.example.whatsappclone.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.ByteArrayInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BaseViewModel : ViewModel() {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    // state flow to expose chat list
    private val _chatList = MutableStateFlow<List<ChatListModel>>(emptyList())
    val chatList: StateFlow<List<ChatListModel>> = _chatList.asStateFlow()



    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    fun currentUserPhone(): String = auth.currentUser?.phoneNumber.orEmpty()


    init {
        loadChatData()
    }


    // 1. Search User by Phone number
    fun searchUserByPhoneNumber(
        phoneNumber: String,
        callBack: (ChatListModel?) -> Unit
    ) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Log.e("BaseViewModel", "User is not authenticated")
            callBack(null)
            return
        }
        val usersRef = FirebaseDatabase.getInstance().getReference("users")
        usersRef.orderByChild("phoneNumber").equalTo(phoneNumber)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (childSnapshot in snapshot.children) {
                            val user = childSnapshot
                                .getValue(ChatListModel::class.java)
                            callBack(user)
                            return
                        }
                    } else {
                        callBack(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(
                        "BaseViewModel",
                        "Error fetching user: ${error.message}, " +
                                "Details: ${error.details}"
                    )
                    callBack(null)
                }
            })
    }

    // 2. To get chat for user
    fun getChatForUser(
        userId: String,
        callBack: (List<ChatListModel>) -> Unit
    ) {
        val chatRef = FirebaseDatabase
                        .getInstance()
                        .getReference("users/$userId/chats")

        chatRef.orderByChild("userId").equalTo(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val chatList = mutableListOf<ChatListModel>()
                    for (childSnapshot in snapshot.children) {
                        val chat = childSnapshot
                            .getValue(ChatListModel::class.java)
                        if (chat != null) {
                            chatList.add(chat)
                        }
                    }
                    callBack(chatList)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("BaseViewModel", "Error fetching user chat: " +
                            "${error.message}")
                    callBack(emptyList())
                }
            })
    }





    // 3. To load chat data and fetch last message and time for each chat
    fun loadChatData() {
        val currentUserId = auth.currentUser?.uid ?: return
        val myPhone = currentUserPhone()

        val chatReference = FirebaseDatabase.getInstance().getReference("chats")
        chatReference.orderByChild("userId").equalTo(currentUserId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val rawChats = mutableListOf<ChatListModel>()
                    for (childSnapshot in snapshot.children) {
                        val chat = childSnapshot
                            .getValue(ChatListModel::class.java)
                        if (chat != null) {
                            rawChats.add(chat)
                        }
                    }

                    if (rawChats.isEmpty()) {
                        _chatList.value = emptyList()
                        return
                    }

                    // fetch last message and time for each chat
                    rawChats.forEachIndexed { index, chatItem ->
                        val otherPhone = chatItem.phoneNumber.orEmpty()
                        if (myPhone.isNotBlank() && otherPhone.isNotBlank()) {
                            fetchLastMessageForChat(myPhone,
                                 otherPhone)
                                                            { lastMsg, formattedTime ->
                                val currentItems = _chatList.value.toMutableList()
                                val targetIndex = currentItems
                                    .indexOfFirst { it.phoneNumber == otherPhone }

                                val updatedModel = chatItem.copy(
                                    message = lastMsg,
                                    time = formattedTime
                                )

                                if (targetIndex != -1) {
                                    currentItems[targetIndex] = updatedModel
                                } else {
                                    currentItems.add(updatedModel)
                                }
                                _chatList.value = currentItems
                            }
                        }
                    }

                    // To set initial list if not set till now
                    if (_chatList.value.isEmpty()) {
                        _chatList.value = rawChats
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("BaseViewModel", "Error loading chats: " +
                            "${error.message}")
                }
            })
    }






    // 4. To Add new chat
    fun addChat(newChat: ChatListModel) {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        if (currentUserId != null) {
            val newChatReference = FirebaseDatabase.getInstance()
                                        .getReference("chats").push()
            val chatWithUser = newChat.copy(userId = currentUserId)

            newChatReference.setValue(chatWithUser)
                .addOnSuccessListener {
                    Log.d("BaseViewModel", "Chat " +
                            "added successfully to Firebase")
                }
                .addOnFailureListener { exception ->
                    Log.e("BaseViewModel", "Failed to" +
                            " add chat: ${exception.message}")
                }
        } else {
            Log.e("BaseViewModel", "No user is authenticated")
        }
    }

    // 5. To send message
    fun sendMessage(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        messageText: String
    ) {
        val messageId = databaseReference.push().key ?: return

        val message = Message(
            senderPhoneNumber = senderPhoneNumber,
            message = messageText,
            timeStamp = System.currentTimeMillis()
        )

        // Save in sender's node
        databaseReference.child("messages")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)
            .child(messageId)
            .setValue(message)

        // Save in receiver's node
        databaseReference.child("messages")
            .child(receiverPhoneNumber)
            .child(senderPhoneNumber)
            .child(messageId)
            .setValue(message)
    }

    // 6. Get Messages (Real-time Listener)

    /** Adds a real-time child listener and returns a cleanup function for DisposableEffect. */

    fun getMessage(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        onNewMessage: (Message) -> Unit
    ): () -> Unit {
        val ref = databaseReference.child("messages")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)

        val listener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?)
            {
                snapshot.getValue(Message::class.java)?.let(onNewMessage)
            }

            override fun onChildChanged
                        (snapshot: DataSnapshot, previousChildName: String?) = Unit
            override fun onChildRemoved(snapshot: DataSnapshot) = Unit
            override fun onChildMoved
                        (snapshot: DataSnapshot, previousChildName: String?) = Unit
            override fun onCancelled(error: DatabaseError) {
                Log.e("BaseViewModel", "Message listener failed:" +
                        " ${error.message}")
            }
        }
        ref.addChildEventListener(listener)
        return { ref.removeEventListener(listener) }
    }




    // 7. Function to fetch last message and formatted time
    fun fetchLastMessageForChat(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        onLastMessageFetched: (String, String) -> Unit
    ) {
        val chatReference = FirebaseDatabase.getInstance().reference
            .child("messages")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)

        // limitToLast(1) directly fetch the recent message
        chatReference.limitToLast(1)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (child in snapshot.children) {
                            val lastMsg = child.child("message").getValue(String::class.java) ?: "No message"

                            // timeStamp to long
                            val rawTime = child.child("timeStamp").value ?: child.child("timestamp").value
                            val timeLong: Long = when (rawTime) {
                                is Long -> rawTime
                                is Number -> rawTime.toLong()
                                is String -> rawTime.toLongOrNull() ?: 0L
                                else -> 0L
                            }

                            // Millisecond to 12 hr format (Like 10:45 AM)
                            val formattedTime = if (timeLong > 0L) {
                                SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(timeLong))
                            } else {
                                "-- : --"
                            }

                            onLastMessageFetched(lastMsg, formattedTime)
                            return
                        }
                    } else {
                        onLastMessageFetched("No message", "-- : --")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("BaseViewModel", "Error fetching last message: ${error.message}")
                    onLastMessageFetched("No message", "-- : --")
                }
            })
    }




    // 8. Load full chat list
    fun loadChatList(
        currentUserPhoneNumber: String,
        onChatListLoaded: (List<ChatListModel>) -> Unit
    ) {
        val chatList = mutableListOf<ChatListModel>()
        val chatReference =
            FirebaseDatabase.getInstance().reference.child("chats").child(currentUserPhoneNumber)

        chatReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (child in snapshot.children) {
                        val name = child.child("name").getValue(String::class.java) ?: "Unknown"
                        val imageString = child.child("image").getValue(String::class.java)
                        val receiverPhone = child.key ?: ""

                        fetchLastMessageForChat(
                            currentUserPhoneNumber,
                            receiverPhone
                        ) { lastMsg, time ->
                            val chatItem = ChatListModel(
                                name = name,
                                phoneNumber = receiverPhone,
                                profileImage = imageString,
                                message = lastMsg,
                                time = time
                            )
                            chatList.add(chatItem)

                            if (chatList.size == snapshot.childrenCount.toInt()) {
                                onChatListLoaded(chatList)
                            }
                        }
                    }
                } else {
                    onChatListLoaded(emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("BaseViewModel", "Error loading chat list: ${error.message}")
                onChatListLoaded(emptyList())
            }
        })
    }


    // 9. Base64 to Bitmap
    /** SEE ImageUtils **/


    // 10. Base64 to Bitmap
    /** SEE ImageUtils **/


}

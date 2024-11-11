package yerakulmanov.petproject.chatbotapp.data

import androidx.annotation.Keep

@Keep
data class Message(
    val senderFirstName: String = "",
    val senderId: String = "",
    val text: String = "asd",
    val timestamp: Long = System.currentTimeMillis(),
    val isSentByCurrentUser: Boolean = false
)

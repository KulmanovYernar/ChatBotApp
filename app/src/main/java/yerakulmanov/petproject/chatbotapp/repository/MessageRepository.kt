package yerakulmanov.petproject.chatbotapp.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import yerakulmanov.petproject.chatbotapp.data.Message
import yerakulmanov.petproject.chatbotapp.data.ResultEvent

class MessageRepository(private val firestore: FirebaseFirestore) {

    suspend fun sendMessage(roomId: String, message: Message): ResultEvent<Unit> = try {
        firestore.collection("rooms").document(roomId)
            .collection("messages").add(message).await()
        ResultEvent.Success(Unit)
    } catch (e: Exception) {
        ResultEvent.Error(e)
    }

    fun getChatMessages(roomId: String): Flow<List<Message>> = callbackFlow {
        val subscription = firestore.collection("rooms").document(roomId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { querySnapshot, _ ->
                querySnapshot?.let {
                    trySend(it.documents.map { doc ->
                        doc.toObject(Message::class.java)!!.copy()
                    }).isSuccess
                }
            }

        awaitClose { subscription.remove() }
    }
}
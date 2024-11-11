package yerakulmanov.petproject.chatbotapp.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import yerakulmanov.petproject.chatbotapp.data.ResultEvent
import yerakulmanov.petproject.chatbotapp.data.Room

class RoomRepository(private val firestore: FirebaseFirestore) {

    suspend fun createRoom(name: String): ResultEvent<Unit> = try {
        val room = Room(name = name)
        firestore.collection("rooms").add(room).await()
        ResultEvent.Success(Unit)
    } catch (e: Exception) {
        ResultEvent.Error(e)
    }

    suspend fun getRooms(): ResultEvent<List<Room>> = try {
        val querySnapshot = firestore.collection("rooms").get().await()
        val rooms = querySnapshot.documents.map { document ->
            document.toObject(Room::class.java)!!.copy(id = document.id)
        }
        ResultEvent.Success(rooms)
    } catch (e: Exception) {
        ResultEvent.Error(e)
    }
}
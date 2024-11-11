package yerakulmanov.petproject.chatbotapp.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import yerakulmanov.petproject.chatbotapp.data.ResultEvent
import yerakulmanov.petproject.chatbotapp.data.User

class UserRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    suspend fun signUp(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): ResultEvent<Boolean> =
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val user = User(email, firstName, lastName)
            saveUserToFirestore(user = user)
            ResultEvent.Success(true)
        } catch (e: Exception) {
            ResultEvent.Error(e)
        }

    private suspend fun saveUserToFirestore(user: User) {
        firestore.collection("users").document(user.email).set(user).await()
    }

    suspend fun login(email: String, password: String,  onSuccess: () -> Unit): ResultEvent<Boolean> =
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            onSuccess()
            ResultEvent.Success(true)
        } catch (e: Exception) {
            ResultEvent.Error(e)
        }


    suspend fun getCurrentUser(): ResultEvent<User> = try {
        val uid = auth.currentUser?.email
        if (uid != null) {
            val userDocument = firestore.collection("users").document(uid).get().await()
            val user = userDocument.toObject(User::class.java)
            if (user != null) {
                Log.d("user2","$uid")
                ResultEvent.Success(user)
            } else {
                ResultEvent.Error(Exception("User data not found"))
            }
        } else {
            ResultEvent.Error(Exception("User not authenticated"))
        }
    } catch (e: Exception) {
        ResultEvent.Error(e)
    }
}
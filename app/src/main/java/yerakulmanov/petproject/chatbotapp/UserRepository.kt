package yerakulmanov.petproject.chatbotapp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

    suspend fun login(email: String, password: String): ResultEvent<Boolean> =
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            ResultEvent.Success(true)
        } catch (e: Exception) {
            ResultEvent.Error(e)
        }

}
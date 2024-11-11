package yerakulmanov.petproject.chatbotapp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import yerakulmanov.petproject.chatbotapp.data.Result
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
    ): Result<Boolean> =
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val user = User(email, firstName, lastName)
            saveUserToFirestore(user = user)
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }

    private suspend fun saveUserToFirestore(user: User) {
        firestore.collection("users").document(user.email).set(user).await()
    }

    suspend fun login(email: String, password: String): Result<Boolean> =
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }

}
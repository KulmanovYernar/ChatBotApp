package yerakulmanov.petproject.chatbotapp.data

sealed class ResultEvent<out T> {
    data class Success<out T>(val data: T) : ResultEvent<T>()
    data class Error(val exception: Exception) : ResultEvent<Nothing>()
}
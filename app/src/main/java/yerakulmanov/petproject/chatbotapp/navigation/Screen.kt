package yerakulmanov.petproject.chatbotapp.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object SignupScreen : Screen("signup_screen")
    object ChatRoomsScreen : Screen("chatroom_screen")
    object ChatScreen : Screen("chat_screen")
}
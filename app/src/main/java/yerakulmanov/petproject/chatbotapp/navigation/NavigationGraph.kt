package yerakulmanov.petproject.chatbotapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import yerakulmanov.petproject.chatbotapp.screens.ChatRoomListScreen
import yerakulmanov.petproject.chatbotapp.screens.ChatScreen
import yerakulmanov.petproject.chatbotapp.viewmodels.AuthViewModel
import yerakulmanov.petproject.chatbotapp.screens.LoginScreen
import yerakulmanov.petproject.chatbotapp.screens.SignUpScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SignupScreen.route
    ) {
        composable(Screen.SignupScreen.route) {
            SignUpScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = { navController.navigate(Screen.LoginScreen.route) }
            )
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onNavigateToSignUp = { navController.navigate(Screen.SignupScreen.route) },
                onSignInSuccess = {
                    navController.navigate(Screen.ChatRoomsScreen.route)
                }
            )
        }

        composable(Screen.ChatRoomsScreen.route) {
            ChatRoomListScreen(
                onJoinClicked = { room ->
                    navController.navigate(Screen.ChatScreen.route + "/${room.id}")
                }
            )
        }

        composable(Screen.ChatScreen.route + "/{roomId}") {
            val roomId: String = it
                .arguments?.getString("roomId") ?: ""
            ChatScreen(roomId = roomId)
        }
    }
}
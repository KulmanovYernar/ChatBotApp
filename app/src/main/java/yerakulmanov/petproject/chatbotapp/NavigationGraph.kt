package yerakulmanov.petproject.chatbotapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import yerakulmanov.petproject.chatbotapp.navigation.Screen
import yerakulmanov.petproject.chatbotapp.screens.LoginScreen
import yerakulmanov.petproject.chatbotapp.screens.SignUpScreen

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SignupScreen.route
    ) {
        composable(Screen.SignupScreen.route) {
            SignUpScreen(
                onNavigateToLogin = { navController.navigate(Screen.LoginScreen.route) }
            )
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onNavigateToSignUp = { navController.navigate(Screen.SignupScreen.route) }
            )
        }
    }
}
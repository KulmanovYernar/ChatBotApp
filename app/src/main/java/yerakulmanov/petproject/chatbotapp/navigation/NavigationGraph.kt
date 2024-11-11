package yerakulmanov.petproject.chatbotapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import yerakulmanov.petproject.chatbotapp.AuthViewModel
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
                onNavigateToSignUp = { navController.navigate(Screen.SignupScreen.route) }
            )
        }
    }
}
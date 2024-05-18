@file:OptIn(ExperimentalPermissionsApi::class)

package com.example.mentormatch

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mentormatch.ui.LoginScreen
import com.example.mentormatch.ui.theme.MentorMatchTheme
import com.example.mentormatch.ui.ProfileScreen
import com.example.mentormatch.ui.SearchScreen
import com.example.mentormatch.ui.SignupScreen
import com.example.mentormatch.ui.SwipeCards
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint

sealed class DestinationScreen(val route: String) {
    object Signup  : DestinationScreen("sair")
    object Login   : DestinationScreen("login")
    object Profile : DestinationScreen("profile")
    object Swipe   : DestinationScreen("swipe")
    object Search : DestinationScreen("search")

}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MentorMatchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                  SwipeAppNavigation()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SwipeAppNavigation() {
    val navController = rememberNavController()
    val vm = hiltViewModel<TCViewModel>()
    
    NotificationMessage(vm = vm)
    
    NavHost(navController = navController, startDestination = DestinationScreen.Signup.route) {
        composable(DestinationScreen.Swipe.route) {
            SwipeCards(navController, vm)
        }
        composable(DestinationScreen.Profile.route) {
            ProfileScreen(navController, vm)
        }
        composable(DestinationScreen.Login.route) {
            LoginScreen(navController, vm)
        }
        composable(DestinationScreen.Signup.route) {
            SignupScreen(navController, vm)
        }
        composable(DestinationScreen.Search.route) {
            SearchScreen(navController, vm)
        }
    }
}


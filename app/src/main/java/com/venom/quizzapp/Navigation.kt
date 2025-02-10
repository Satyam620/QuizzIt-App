package com.venom.quizzapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.venom.quizzapp.screens.HomeScreen
import com.venom.quizzapp.screens.LeaderBoardScreen
import com.venom.quizzapp.screens.ProfileScreen
import com.venom.quizzapp.screens.QuizScreen
import com.venom.quizzapp.model.QuizViewModel
import com.venom.quizzapp.screens.CategoryScreen
import com.venom.quizzapp.screens.ScoreScreen

sealed class Screen(val route: String) {
    data object Home : Screen("Home")
    data object Profile : Screen("Profile")
    data object Leaderboard : Screen("Leaderboard")
    data object QuizScreen : Screen("QuizScreen")
    data object Categories : Screen("Categories")
    data object Score : Screen("Score")
}

@Composable
fun MainScreen(viewModel: QuizViewModel, navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        //Bottom Navigation Options
        composable(Screen.Home.route) {
            HomeScreen(viewModel, navController)
        }
        composable(Screen.Categories.route) {
            CategoryScreen(viewModel, navController)
        }
        composable(Screen.Leaderboard.route) {
            LeaderBoardScreen(viewModel, navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(viewModel, navController)
        }

        //Hidden Navigation Options
        composable(Screen.QuizScreen.route) {
            QuizScreen(viewModel, navController)
        }
        composable(Screen.Score.route) {
            ScoreScreen(viewModel, navController)
        }

    }
}
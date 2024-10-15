package com.venom.quizzapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.venom.quizzapp.model.QuizViewModel
import com.venom.quizzapp.ui.theme.QuizzappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizzappTheme {
                val viewModel: QuizViewModel = viewModel()
                val navController = rememberNavController()
                MainScreen(viewModel, navController)
            }
        }
    }
}

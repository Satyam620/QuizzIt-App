package com.venom.quizzapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.venom.quizzapp.model.AuthViewModel
import com.venom.quizzapp.model.QuizViewModel
import com.venom.quizzapp.ui.theme.QuizzappTheme

@Composable
fun HomeScreen(
    viewModel: QuizViewModel,
    navHostController: NavHostController,
    authViewModel: AuthViewModel
) {
    if (!viewModel.logged.value) {
        RegisterScreen(viewModel, authViewModel)
        LoginScreen(viewModel, authViewModel)
    }
    QuizzappTheme {
        Scaffold(
            topBar = {
                TopBar("Home")
            },
            bottomBar = {
                BottomBar(viewModel, "Home", navHostController)
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when {
                    viewModel.triviaState.value.loading -> {
                        AnimatedLogo()
                    }

                    viewModel.triviaState.value.error != null -> {
                        Text(
                            text = "ERROR OCCURRED",
                            fontSize = 25.sp,
                            color = MaterialTheme.colorScheme.error
                        )
                        SubmitButton(text = "Reload") { viewModel.fetchTrivia() }
                    }

                    else -> {
                        //Display trivia fact
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Trivia",
                                fontSize = 30.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.secondary,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(bottom = 20.dp)
                            )
                            Text(
                                text = viewModel.triviaState.value.fact!!.text,
                                fontSize = 25.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.ExtraBold,
                                lineHeight = 50.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    HomeScreen(
        viewModel = QuizViewModel(),
        navHostController = NavHostController(LocalContext.current),
        authViewModel = AuthViewModel()
    )
}



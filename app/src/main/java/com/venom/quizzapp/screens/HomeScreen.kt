package com.venom.quizzapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
import com.venom.quizzapp.model.QuizViewModel
import com.venom.quizzapp.ui.theme.QuizzappTheme

@Composable
fun HomeScreen(viewModel: QuizViewModel, navHostController: NavHostController) {
    if (!viewModel.logged.value) {
        RegisterScreen(viewModel)
        LoginScreen(viewModel)
    }
    QuizzappTheme {
        Scaffold(
            topBar = {
                TopBar("Home")
            },
            bottomBar = {
                BottomBar("Home", navHostController)
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
                        CircularProgressIndicator(
                            strokeWidth = 10.dp,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(50.dp, 200.dp),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    viewModel.triviaState.value.error != null -> {
                        Text(
                            text = "ERROR OCCURRED ${viewModel.triviaState.value.error}",
                            fontSize = 25.sp,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    else -> {
                        //Display trivia fact
                        Column(
                            modifier = Modifier.padding(20.dp).fillMaxWidth(),
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
        navHostController = NavHostController(LocalContext.current)
    )
}



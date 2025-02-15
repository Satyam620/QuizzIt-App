package com.venom.quizzapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import com.venom.quizzapp.model.QuizViewModel
import com.venom.quizzapp.ui.theme.QuizzappTheme

@Composable
fun ProfileScreen(viewModel: QuizViewModel, navController: NavHostController) {
    QuizzappTheme {
        Scaffold(
            topBar = {
                TopBar("Profile")
            },
            bottomBar = {
                BottomBar(viewModel, "Profile", navController)
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginScreen(viewModel = viewModel)
                RegisterScreen(viewModel = viewModel)
                if (viewModel.logged.value) {
                    Row {
                        //TODO Write profile page.
                    }
                } else {
                    Text(
                        text = "Please Login First",
                        fontSize = 50.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(bottom = 50.dp),
                        lineHeight = 50.sp
                    )
                    SubmitButton(text = "Login") {
                        viewModel.loginDialogue.value = true
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfilePreview() {
    ProfileScreen(
        viewModel = QuizViewModel(),
        navController = NavHostController(LocalContext.current)
    )
}

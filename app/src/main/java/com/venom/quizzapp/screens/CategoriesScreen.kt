package com.venom.quizzapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.venom.quizzapp.Screen
import com.venom.quizzapp.model.QuizViewModel
import com.venom.quizzapp.ui.theme.QuizzappTheme

//TODO -Load data From Viewmodel
@Composable
fun CategoryScreen(viewModel: QuizViewModel, navController: NavHostController) {
    QuizzappTheme {
        Scaffold(
            topBar = {
                TopBar("Category")
            },
            bottomBar = {
                BottomBar(viewModel, "Category", navController)
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Click on a category to start quiz.",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Center
                )
                when {
                    viewModel.categoriesState.value.loading -> {
                        AnimatedLogo()
                    }

                    viewModel.categoriesState.value.error != null -> {
                        Text(text = "ERROR OCCURRED", fontSize = 25.sp, color = Color.Red)
                        SubmitButton(text = "Reload") { viewModel.fetchCategories() }
                    }

                    else -> {
                        CategoryList(viewModel, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryList(viewModel: QuizViewModel, navController: NavHostController) {
    QuizzappTheme {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(viewModel.categoriesState.value.list) { category ->
                CategoryButton(category.name) {
                    viewModel.fetchQuestions(category.id)
                    navController.navigate(Screen.QuizScreen.route)
                }
            }
        }
    }
}

@Composable
fun CategoryButton(category: String, onClick: () -> Unit) {
    QuizzappTheme {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth(.95f),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                MaterialTheme.colorScheme.background,
                MaterialTheme.colorScheme.primary
            ),
            border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
        ) {
            Text(
                text = category,
                fontSize = 20.sp,
                lineHeight = 30.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
fun CategoryPreview() {
    CategoryScreen(
        viewModel = QuizViewModel(),
        navController = NavHostController(LocalContext.current)
    )
}


@Preview(showBackground = true)
@Composable
fun CategoryButtonPrev() {
    CategoryButton("Animal") {}
}


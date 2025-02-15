package com.venom.quizzapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var query by remember { mutableStateOf("") }

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
                    text = "Click on a category to start quiz or generate one you like.",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.1f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = query,
                        onValueChange = { query = it },
                        label = { Text("Enter Quiz Topic") },
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .fillMaxHeight()
                            .border(
                                3.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp)
                            ),
                        colors = TextFieldDefaults.colors(
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedLabelColor = Color.Gray,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                        ),
                        shape = RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp)
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(.5f)
                            .fillMaxHeight()
                            .border(
                                3.dp,
                                MaterialTheme.colorScheme.secondary,
                                RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp)
                            ),
                        onClick = {
                            viewModel.sendGeminiRequest(query)
                            navController.navigate(Screen.QuizScreen.route)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.secondary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        enabled = query.isNotEmpty()
                    ) {
                        Text(text = "Go", fontSize = 15.sp)
                    }
                }
                when {
                    viewModel.categoriesState.value.loading -> {
                        AnimatedLogo()
                    }

                    viewModel.categoriesState.value.error != null -> {
                        Text(text = "ERROR OCCURRED", fontSize = 25.sp, color = Color.Red)
                        println(viewModel.categoriesState.value.error)
                    }

                    else -> {
                        //Display Categories
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


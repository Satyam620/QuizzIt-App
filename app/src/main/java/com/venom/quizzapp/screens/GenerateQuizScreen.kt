package com.venom.quizzapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateQuizScreen(viewModel: QuizViewModel, navController: NavHostController) {
    var query by remember { mutableStateOf("") }
    var difficultylevel by remember { mutableStateOf("Any") }
    val options = listOf("Easy", "Medium", "Hard", "Any")
    var expanded by remember { mutableStateOf(false) }

    QuizzappTheme {
        Scaffold(
            topBar = {
                TopBar("Generate")
            },
            bottomBar = {
                BottomBar(viewModel, "Generator", navController)
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
                Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Enter a query to generate quiz.",
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(10.dp),
                            textAlign = TextAlign.Center
                        )
                        TextField(
                            value = query,
                            onValueChange = { query = it },
                            label = { Text("Enter Quiz Topic") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    3.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                    RoundedCornerShape(10.dp)
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
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "Select difficulty level of the quiz.",
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(10.dp),
                            textAlign = TextAlign.Center
                        )
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            TextField(
                                value = difficultylevel,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                                    .border(
                                        3.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                        RoundedCornerShape(10.dp)
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
                                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.background)
                                    .border(
                                        border = BorderStroke(
                                            width = 2.dp,
                                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                        ),
                                        shape = RoundedCornerShape(
                                            bottomEnd = 10.dp,
                                            bottomStart = 10.dp
                                        )
                                    ),
                            ) {
                                options.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(text = option) },
                                        onClick = {
                                            difficultylevel = option
                                            expanded = false
                                        },
                                    )
                                }
                            }
                        }
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                3.dp,
                                MaterialTheme.colorScheme.secondary,
                                RoundedCornerShape(10.dp)
                            ),
                        onClick = {
                            viewModel.sendGeminiRequest(query, difficultylevel)
                            navController.navigate(Screen.QuizScreen.route)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.secondary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        enabled = query.isNotEmpty()
                    ) {
                        Text(text = "Generate", fontSize = 15.sp)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun GenerateQuizPreview() {
    GenerateQuizScreen(
        viewModel = QuizViewModel(),
        navController = NavHostController(LocalContext.current)
    )
}
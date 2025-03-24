package com.venom.quizzapp.screens

import android.text.Html
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.venom.quizzapp.Screen
import com.venom.quizzapp.model.QuizViewModel
import com.venom.quizzapp.ui.theme.QuizzappTheme

@Composable
fun QuizScreen(viewModel: QuizViewModel, navController: NavHostController) {
    val optionColor = MaterialTheme.colorScheme.background
    val selectedOpt = MaterialTheme.colorScheme.secondary
    val buttonColors =
        remember { mutableStateListOf(optionColor, optionColor, optionColor, optionColor) }


    fun updateButtonColors(selectedIndex: Int) {
        buttonColors.fill(optionColor)
        buttonColors[selectedIndex] = selectedOpt
    }

    fun decodeHtml(encodedString: String): String {
        return Html.fromHtml(encodedString, Html.FROM_HTML_MODE_LEGACY).toString()
    }

    var selectedOption: String? = null

    Scaffold(
        topBar = { TopBar("Quiz-Time") }
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
                viewModel.questionsState.value.loading -> {
                    AnimatedLogo()
                }

                viewModel.questionsState.value.error != null -> {
                    Text(
                        text = "ERROR OCCURRED",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 25.sp
                    )
                }

                else -> {
                    Text(
                        modifier = Modifier.padding(30.dp, 20.dp, 30.dp, 10.dp),
                        text = "Q${viewModel.currentPosition + 1}. " + decodeHtml(viewModel.question.value),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        lineHeight = 25.sp
                    )
                    TenButtonsInTwoRows(viewModel, viewModel.selectedOptions)
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                            .fillMaxHeight(.8f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemsIndexed(viewModel.options.value) { index, item ->
                            Button(
                                onClick = {
                                    updateButtonColors(index)
                                    selectedOption = item
                                },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = buttonColors[index],
                                    contentColor = MaterialTheme.colorScheme.primary
                                ),
                                border = BorderStroke(
                                    2.dp,
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                                )
                            ) {
                                Text(
                                    text = decodeHtml(item),
                                    fontSize = 18.sp,
                                    lineHeight = 25.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    SubmitButton(text = if (viewModel.currentPosition == 9) "Finish" else "Next") {
                        buttonColors.fill(optionColor)
                        viewModel.updateQuestion(selectedOption)
                        if (viewModel.navigateToScore.value) {
                            navController.navigate(Screen.Answer.route)
                        }
                        selectedOption = null
                    }
                }
            }
        }
    }
}

@Composable
fun TenButtonsInTwoRows(
    viewModel: QuizViewModel,
    items: List<String?>
) { // Replace String with your data type
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // First row with 5 buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (i in 0 until 5) {
                Button(
                    onClick = { viewModel.updatePosition(i) },
                    modifier = Modifier
                        .size(40.dp) // Same size for consistency
                        .padding(5.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonColors(
                        containerColor = if (viewModel.selectedOptions[i] != null) Color.Green else if (i == viewModel.currentPosition) Color.Cyan else Color.Red,
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = Color.Black,
                        disabledContentColor = Color.Black
                    )
                ) {
                    Text(text = (i + 1).toString(), fontSize = 24.sp)
                }
            }
        }
        // Second row with 5 buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (i in 5 until 10) {
                Button(
                    onClick = { viewModel.updatePosition(i) },
                    modifier = Modifier
                        .size(40.dp) // Same size for consistency
                        .padding(5.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonColors(
                        containerColor = if (viewModel.selectedOptions[i] != null) Color.Green else if (i == viewModel.currentPosition) Color.Cyan else Color.Red,
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = Color.Black,
                        disabledContentColor = Color.Black
                    )
                ) {
                    Text(text = (i + 1).toString(), fontSize = 24.sp)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    QuizzappTheme {
        val viewModel = QuizViewModel()
        val context = LocalContext.current
        QuizScreen(viewModel, NavHostController(context))
    }
}
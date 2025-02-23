package com.venom.quizzapp.screens

import android.text.Html
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.venom.quizzapp.Screen
import com.venom.quizzapp.model.Question
import com.venom.quizzapp.model.QuizViewModel
import com.venom.quizzapp.ui.theme.QuizzappTheme

@Composable
fun AnswerScreen(viewModel: QuizViewModel, navController: NavHostController) {
    QuizzappTheme {
        Scaffold(
            topBar = {
                TopBar(name = "Answers")
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
                Box(modifier = Modifier.fillMaxHeight(.9f)) {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 15.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        itemsIndexed(viewModel.questionsState.value.list) { index, question ->
                            QuestionItem(index, question, viewModel)
                        }
                    }
                }
                SubmitButton(text = "Next") {
                    navController.navigate(Screen.Score.route)
                }
            }
        }
    }
}

fun decodeHtml(encodedString: String): String {
    return Html.fromHtml(encodedString, Html.FROM_HTML_MODE_LEGACY).toString()
}

@Composable
fun QuestionItem(index: Int, question: Question, viewModel: QuizViewModel) {
    val selectedOption: String = viewModel.selectedOptions[index]
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = decodeHtml(question.question),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (question.correct_answer != selectedOption) {
                Text(
                    text = "Your Answer: ${decodeHtml(selectedOption)}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.Red
                )
                Text(
                    text = "Correct Answer: ${decodeHtml(question.correct_answer)}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.Green
                )
                if (question.explanation != null) {
                    Text(
                        text = "Explanation: ${decodeHtml(question.explanation)}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                    )
                }
            } else {
                Text(
                    text = "Correct Answer: ${decodeHtml(question.correct_answer)}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.Green
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnswerScreenPreview() {
    AnswerScreen(QuizViewModel(), NavHostController(LocalContext.current))
}
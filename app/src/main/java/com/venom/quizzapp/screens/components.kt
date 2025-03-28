package com.venom.quizzapp.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.venom.quizzapp.R
import com.venom.quizzapp.Screen
import com.venom.quizzapp.model.QuizViewModel
import com.venom.quizzapp.ui.theme.QuizzappTheme


@Composable
fun TopBar(name: String) {
    QuizzappTheme {
        Column(
            modifier = Modifier.padding(top = 40.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 2.dp
            )
            Text(
                textAlign = TextAlign.Center,
                text = name.uppercase(),
                fontSize = 40.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 60.sp
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 2.dp
            )
        }
    }
}

data class NavItem(val label: String, val route: String, val icon: Int, val index: Int)

@Composable
fun BottomBar(viewModel: QuizViewModel, name: String, navController: NavHostController) {
    QuizzappTheme {
        val iconSize = 40.dp
        val iconColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
        val selectedIconColor = MaterialTheme.colorScheme.secondary
        val buttonColors =
            remember { mutableStateListOf(iconColor, iconColor, iconColor, iconColor, iconColor) }

        val navItems = listOf(
            NavItem("Home", Screen.Home.route, R.drawable.round_home_24, 0),
            NavItem("Category", Screen.Categories.route, R.drawable.round_category_24, 1),
            NavItem("Generator", Screen.Generator.route, R.drawable.ai_ml_icon, 2),
            NavItem("Leaderboard", Screen.Leaderboard.route, R.drawable.round_leaderboard_24, 3),
            NavItem("Profile", Screen.Profile.route, R.drawable.round_person_24, 4)
        )

        val enabled = remember { mutableStateListOf(true, true, true, true, true) }

        fun updateButtonColors(selectedIndex: Int) {
            buttonColors.fill(iconColor) // Reset all to white
            buttonColors[selectedIndex] = selectedIconColor// Highlight the selected button
            enabled[selectedIndex] = false
        }

        navItems.firstOrNull { it.label == name }?.let { updateButtonColors(it.index) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 2.dp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                navItems.forEach { item ->
                    IconButton(
                        onClick = {
                            navController.navigate(item.route)
                            updateButtonColors(item.index)
                            if (item.label == "Home") {
                                viewModel.fetchTrivia()
                            } else if (item.label == "Generator") {
                                viewModel.resetGenerateValues()
                            }
                        },
                        enabled = enabled[item.index]
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = "${item.label} Button",
                            modifier = Modifier.size(iconSize),
                            tint = buttonColors[item.index]
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SubmitButton(text: String, width: Float = 0.8f, onClick: () -> Unit) {
    QuizzappTheme {
        Button(
            modifier = Modifier
                .fillMaxWidth(width)
                .border(3.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(25.dp))
                .height(50.dp),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(text = text.uppercase(), fontSize = 25.sp, textAlign = TextAlign.Center)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopPreview() {
    TopBar("Trivia")
}

@Preview(showBackground = true)
@Composable
fun BottomPreview() {
    QuizzappTheme {
        val context = LocalContext.current
        BottomBar(QuizViewModel(), name = "Home", navController = NavHostController(context))
    }
}

@Preview(showBackground = true)
@Composable
fun SubmitPreview() {
    SubmitButton("Submit") {

    }
}


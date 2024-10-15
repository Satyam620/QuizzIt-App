package com.venom.quizzapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.venom.quizzapp.model.LeaderBoardItem
import com.venom.quizzapp.model.QuizViewModel
import com.venom.quizzapp.ui.theme.QuizzappTheme

@Composable
fun LeaderBoardScreen(viewModel: QuizViewModel, navController: NavHostController) {
    Scaffold(
        topBar = { TopBar("Leaderboard") },
        bottomBar = { BottomBar("Leaderboard", navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(top = 20.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //TODO Change the value inside the function.
            Row(
                modifier = Modifier
                    .fillMaxWidth(.9F)
                    .height(50.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.6f), RoundedCornerShape(10.dp))
                    .padding(start = 15.dp, end = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "RANK",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.secondary,
                    maxLines = 1
                )
                Text(
                    text = "NAME",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.secondary,
                    maxLines = 1
                )
                Text(
                    text = "SCORE",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.secondary,
                    maxLines = 1
                )
            }
            LeaderboardList(viewModel.leaderBoardItems)
        }
    }
}

@Composable
fun LeaderboardList(leaderboard: List<LeaderBoardItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(leaderboard) { item ->
            LeaderboardColumn(
                rank = item.rank.toString(),
                name = item.name,
                score = item.score.toString()
            )
        }
    }
}

@Composable
fun LeaderboardColumn(rank: String, name: String, score: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(.9f)
            .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
            .height(50.dp)
            .padding(start = 30.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = rank,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1
        )
        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1
        )
        Text(
            text = score,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun LeaderboardPreview() {
    QuizzappTheme {
        LeaderBoardScreen(QuizViewModel(), NavHostController(LocalContext.current))
    }
}

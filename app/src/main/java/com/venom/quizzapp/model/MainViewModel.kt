package com.venom.quizzapp.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    init {
        fetchTrivia()
        fetchCategories()
    }

    //Question State Management.
    private var _questionsState = mutableStateOf(QuestionState())
    val questionsState: State<QuestionState> = _questionsState

    fun fetchQuestions(category: Int) {
        viewModelScope.launch {
            try {
                val response = questionService.getQuestion(category = category)
                _questionsState.value = _questionsState.value.copy(
                    list = response.results,
                    loading = false,
                    error = null
                )
                initializeQuestion()
            } catch (e: Exception) {
                _questionsState.value = _questionsState.value.copy(
                    loading = false,
                    error = "Error Fetching Questions"
                )
            }
        }
    }
    //Question Data management.

    private var score = 0
    private var currentPosition = 0
    var currentProgress = mutableFloatStateOf(.1f)
    var progress = mutableStateOf("1/10")
    var question = mutableStateOf("")
    var options = mutableStateOf(listOf<String>())
    val navigateToScore = mutableStateOf(false)

    private fun shuffleOptions(): List<String> {
        val allOptions =
            questionsState.value.list[currentPosition].incorrect_answers.toMutableList() // Start with incorrect answers
        allOptions.add(questionsState.value.list[currentPosition].correct_answer) // Add the correct answer to the list
        allOptions.shuffle() // Shuffle the list randomly
        return allOptions
    }

    private fun initializeQuestion() {
        if (questionsState.value.list.isNotEmpty()) {
            question.value = questionsState.value.list[currentPosition].question
            options.value = shuffleOptions()  // Shuffle options after initializing
            currentProgress.floatValue =
                (currentPosition + 1).toFloat() / questionsState.value.list.size
            progress.value = "${currentPosition + 1}/${questionsState.value.list.size}"
        }
    }

    fun updateQuestion(selectedOption: String?) {
        if (selectedOption == questionsState.value.list[currentPosition].correct_answer) {
            score++
        }

        currentPosition++
        navigateToScore.value = (currentPosition == questionsState.value.list.size)
        if (currentPosition < questionsState.value.list.size) {
            question.value = questionsState.value.list[currentPosition].question
            currentProgress.floatValue =
                (currentPosition + 1).toFloat() / questionsState.value.list.size
            progress.value = "${currentPosition + 1}/${questionsState.value.list.size}"
            options.value = shuffleOptions()
        } else {
            totalScore = score
            score = 0
            currentPosition = 0
            currentProgress.floatValue = .1f
            progress.value = "1/10"
            question.value = ""
            options.value = listOf()
            _questionsState.value = _questionsState.value.copy(
                loading = true,
            )
        }
    }

    //Category State Management.
    private val _categoriesState = mutableStateOf(CategoryState())
    val categoriesState: State<CategoryState> = _categoriesState


    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = categoryService.getCategories()
                _categoriesState.value = _categoriesState.value.copy(
                    list = response.trivia_categories,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    error = "Error Fetching Categories ${e.message}"
                )
            }
        }
    }

    //Trivia State Management.
    private val _triviaState = mutableStateOf(TriviaState())
    val triviaState: State<TriviaState> = _triviaState

    private fun fetchTrivia() {
        viewModelScope.launch {
            try {
                val response = TriviaService.getFact()
                _triviaState.value = _triviaState.value.copy(
                    fact = response,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                _triviaState.value = _triviaState.value.copy(
                    loading = false,
                    error = "Error Fetching Trivia Fact ${e.message}"
                )
            }
        }
    }

    //TODO LeaderBoard Data Management.
    val leaderBoardItems = listOf(
        LeaderBoardItem(1, "Satyam", 1000),
        LeaderBoardItem(2, "Satya", 900),
        LeaderBoardItem(3, "Venom", 800),
        LeaderBoardItem(4, "Sanedeepak", 700),
        LeaderBoardItem(5, "Satyam", 600),
        LeaderBoardItem(6, "Satya", 500),
        LeaderBoardItem(7, "Sally", 400),
        LeaderBoardItem(8, "Demon", 300),
        LeaderBoardItem(9, "Lucky", 200),
        LeaderBoardItem(10, "Unnamed", 100),
    )
    var totalScore = 0
    var logged = mutableStateOf(false)
    var loginDialogue = mutableStateOf(false)
    var registerDialogue = mutableStateOf(false)

}

data class LeaderBoardItem(val rank: Int, val name: String, val score: Int)


//FINAL DATA CLASSES. Do not Mess with these data Classes.
data class QuestionState(
    val loading: Boolean = true,
    val list: List<Question> = emptyList(),
    val error: String? = null
)

data class CategoryState(
    val loading: Boolean = true,
    val list: List<Category> = emptyList(),
    val error: String? = null
)

data class TriviaState(
    val loading: Boolean = true,
    val fact: TriviaResponse?=null,
    val error: String? = null
)


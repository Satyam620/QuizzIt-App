package com.venom.quizzapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val retrofit = Retrofit.Builder().baseUrl("https://opentdb.com/")
    .addConverterFactory(GsonConverterFactory.create()).build()

private val TriviaRetrofit = Retrofit.Builder().baseUrl("http://numbersapi.com/")
    .addConverterFactory(GsonConverterFactory.create()).build()

val questionService: QuizApi = retrofit.create(QuizApi::class.java)

val categoryService: CategoryApi = retrofit.create(CategoryApi::class.java)

val TriviaService: FactApi = TriviaRetrofit.create(FactApi::class.java)

interface QuizApi {
    @GET("api.php")  // Endpoint path without query parameters
    suspend fun getQuestion(
        @Query("amount") amount: Int = 10,              // Fixed query parameter
        @Query("type") type: String = "multiple",       // Fixed query parameter
        @Query("category") category: Int                // Dynamic query parameter
    ): QuestionResponse
}

interface CategoryApi {
    @GET("api_category.php")
    suspend fun getCategories(): CategoryResponse
}

interface FactApi {
    @GET("random/trivia?json")
    suspend fun getFact(): TriviaResponse
}

//Data Classes for Api.
@Parcelize
data class Question(
    val type: String,
    val difficulty: String,
    val category: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>,
    val explanation: String?,
) : Parcelable

@Parcelize
data class Category(
    val id: Int,
    val name: String
) : Parcelable

data class QuestionResponse(val response_code: Int, val results: List<Question>)

data class CategoryResponse(val trivia_categories: List<Category>)


@Parcelize
data class TriviaResponse(
    val text: String,
    val number: Int,
    val found: Boolean,
    val type: String
) : Parcelable


//JSON from https://opentdb.com/api.php.
//"type": "multiple",
//"difficulty": "easy",
//"category": "General Knowledge",
//"question": "What is the Spanish word for &quot;donkey&quot;?",
//"correct_answer": "Burro",
//"incorrect_answers": [
//"Caballo",
//"Toro",
//"Perro"
//]

// JSON from https://opentdb.com/api_category.php
//"trivia_categories": [
//{
//    "id": 9,
//    "name": "General Knowledge"
//},

// JSON from http://numbersapi.com/random/trivia?json
//{
//    "text": "729 is the number of times a philosopher's pleasure is greater than a tyrant's pleasure according to Plato in the Republic.",
//    "number": 729,
//    "found": true,
//    "type": "trivia"
//}
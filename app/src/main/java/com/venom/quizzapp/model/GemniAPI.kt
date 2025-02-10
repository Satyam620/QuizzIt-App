package com.venom.quizzapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

private val GeminiRetrofit = Retrofit.Builder()
    .baseUrl("https://generativelanguage.googleapis.com/")
    .addConverterFactory(GsonConverterFactory.create()).build()

val GeminiService: GeminiApi = GeminiRetrofit.create(GeminiApi::class.java)

interface GeminiApi {
    @Headers("Content-Type: application/json")
    @POST("v1beta/models/gemini-2.0-flash:generateContent")
    suspend fun generateTrivia(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}

data class GeminiRequest(
    @SerializedName("contents") val contents: List<Content>,
    @SerializedName("generationConfig") val generationConfig: GenerationConfig
)

@Parcelize
data class GeminiResponse(
    @SerializedName("candidates") val candidates: List<Candidate>
) : Parcelable

@Parcelize
data class Candidate(
    @SerializedName("content") val content: Content
) : Parcelable

@Parcelize
data class Content(
    @SerializedName("parts") val parts: List<Part>
) : Parcelable

@Parcelize
data class Part(
    @SerializedName("text") val text: String // This contains embedded JSON as a string
) : Parcelable

data class GenerationConfig(
    @SerializedName("responseMimeType") val responseMimeType: String
)

//val example = {
//    "candidates": [
//    {
//        "content": {
//        "parts": [
//        {
//            "text": "{\n  \"response_code\": 0,\n  \"results\": [\n    {\n      \"type\": \"multiple\",\n      \"difficulty\": \"medium\",\n      \"category\": \"Entertainment: Video Games\",\n      \"question\": \"Which of these characters was NOT playable in the original release of Super Smash Bros. for the Nintendo 64?\",\n      \"correct_answer\": \"Jigglypuff\",\n      \"incorrect_answers\": [\"Mario\", \"Link\", \"Pikachu\"]\n    },\n    {\n      \"type\": \"multiple\",\n      \"difficulty\": \"medium\",\n      \"category\": \"Entertainment: Video Games\",\n      \"question\": \"In the game Doki Doki Literature Club!, what is the name of the school&#039;s Literature Club?\",\n      \"correct_answer\": \"Doki Doki Literature Club!\",\n      \"incorrect_answers\": [\"The Literature Club\", \"Sunshine Literature Club\", \"High School Literature Club\"]\n    },\n    {\n      \"type\": \"multiple\",\n      \"difficulty\": \"medium\",\n      \"category\": \"Entertainment: Video Games\",\n      \"question\": \"In the video game &quot;Persona 5&quot;, what is the main character&#039;s code name?\",\n      \"correct_answer\": \"Joker\",\n      \"incorrect_answers\": [\"Hero\", \"Skull\", \"Panther\"]\n    },\n    {\n      \"type\": \"multiple\",\n      \"difficulty\": \"medium\",\n      \"category\": \"Entertainment: Video Games\",\n      \"question\": \"In the Fallout series, what is the currency used?\",\n      \"correct_answer\": \"Caps\",\n      \"incorrect_answers\": [\"Bottle Caps\", \"Nuka Caps\", \"Dollars\"]\n    },\n    {\n      \"type\": \"multiple\",\n      \"difficulty\": \"medium\",\n      \"category\": \"Entertainment: Video Games\",\n      \"question\": \"What is the name of the main ship in FTL: Faster Than Light?\",\n      \"correct_answer\": \"Kestrel\",\n      \"incorrect_answers\": [\"USS Enterprise\", \"Normandy\", \"Pillar of Autumn\"]\n    },\n    {\n      \"type\": \"multiple\",\n      \"difficulty\": \"medium\",\n      \"category\": \"Entertainment: Video Games\",\n      \"question\": \"What type of character is Captain Phasma in the Lego Star Wars The Force Awakens game?\",\n      \"correct_answer\": \"Silver Lego\",\n      \"incorrect_answers\": [\"Gold Lego\", \" сюжет Lego\", \"Plastic Lego\"]\n    },\n    {\n      \"type\": \"multiple\",\n      \"difficulty\": \"medium\",\n      \"category\": \"Entertainment: Video Games\",\n      \"question\": \"Which company developed the video game &quot;Rocket League&quot;?\",\n      \"correct_answer\": \"Psyonix\",\n      \"incorrect_answers\": [\"Epic Games\", \"Ubisoft\", \"2K Games\"]\n    },\n    {\n      \"type\": \"multiple\",\n      \"difficulty\": \"medium\",\n      \"category\": \"Entertainment: Video Games\",\n      \"question\": \"Which character in the game Overwatch was revealed to be homosexual in December 2016?\",\n      \"correct_answer\": \"Tracer\",\n      \"incorrect_answers\": [\"Soldier: 76\", \"Widowmaker\", \"Zarya\"]\n    },\n    {\n      \"type\": \"multiple\",\n      \"difficulty\": \"medium\",\n      \"category\": \"Entertainment: Video Games\",\n      \"question\": \"In the &quot;Half-Life&quot; series, what is Dr. Gordon Freeman&#039;s primary weapon?\",\n      \"correct_answer\": \"Crowbar\",\n      \"incorrect_answers\": [\"Pistol\", \"Assault Rifle\", \"Rocket Launcher\"]\n    },\n    {\n      \"type\": \"multiple\",\n      \"difficulty\": \"medium\",\n      \"category\": \"Entertainment: Video Games\",\n      \"question\": \"What is the name of the first level in &quot;Sonic Adventure&quot;?\",\n      \"correct_answer\": \"Emerald Coast\",\n      \"incorrect_answers\": [\"Windy Valley\", \"Green Hill Zone\", \"Station Square\"]\n    }\n  ]\n}"
//        }
//        ],
//        "role": "model"
//    },
//        "finishReason": "STOP",
//        "avgLogprobs":-0.12734142319980638
//    }
//    ],
//    "usageMetadata":
//    {
//        "promptTokenCount": 132,
//        "candidatesTokenCount": 924,
//        "totalTokenCount": 1056,
//        "promptTokensDetails": [
//        {
//            "modality": "TEXT",
//            "tokenCount": 132
//        }
//        ],
//        "candidatesTokensDetails": [
//        {
//            "modality": "TEXT",
//            "tokenCount": 924
//        }
//        ]
//    },
//    "modelVersion": "gemini-2.0-flash"
//}
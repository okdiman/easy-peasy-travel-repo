package okunev.projects.easypeasytravel.recognizer.data.network

import okunev.projects.easypeasytravel.recognizer.data.models.TranslatedTextDto
import retrofit2.http.POST
import retrofit2.http.Query

interface TranslatorApi {
    @POST("api/v3/translate")
    suspend fun translate(
        @Query("fast") fast: Boolean = false,
        @Query("from") from: String = "en",
        @Query("to") to: String = "ru",
        @Query("text") text: String
    ): TranslatedTextDto
}
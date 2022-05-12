package okunev.projects.easypeasytravel.recognizer.domain.usecases.api

interface TranslateTextUseCase {
    suspend operator fun invoke(text: String): String
}
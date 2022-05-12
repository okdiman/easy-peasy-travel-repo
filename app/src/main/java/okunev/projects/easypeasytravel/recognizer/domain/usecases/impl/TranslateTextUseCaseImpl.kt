package okunev.projects.easypeasytravel.recognizer.domain.usecases.impl

import okunev.projects.easypeasytravel.recognizer.domain.repositories.RecognizerRepository
import okunev.projects.easypeasytravel.recognizer.domain.usecases.api.TranslateTextUseCase
import javax.inject.Inject

class TranslateTextUseCaseImpl @Inject constructor(
    private val repository: RecognizerRepository
) : TranslateTextUseCase {
    override suspend fun invoke(text: String) = repository.translateText(text)
}
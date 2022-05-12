package okunev.projects.easypeasytravel.recognizer.domain.usecases.impl

import androidx.camera.core.ImageCapture
import okunev.projects.easypeasytravel.recognizer.domain.repositories.RecognizerRepository
import okunev.projects.easypeasytravel.recognizer.domain.usecases.api.TakePhotoUseCase
import javax.inject.Inject

class TakePhotoUseCaseImpl @Inject constructor(
    private val repository: RecognizerRepository
) : TakePhotoUseCase {
    override fun invoke(image: ImageCapture?, listener: (text: String) -> Unit) {
        repository.takePhoto(image, listener)
    }
}
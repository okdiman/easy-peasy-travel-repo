package okunev.projects.easypeasytravel.recognizer.domain.usecases.api

import androidx.camera.core.ImageCapture

interface TakePhotoUseCase {
    operator fun invoke(image: ImageCapture?, listener: (text: String) -> Unit)
}
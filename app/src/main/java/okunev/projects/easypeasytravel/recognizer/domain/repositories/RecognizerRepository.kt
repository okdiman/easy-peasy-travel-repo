package okunev.projects.easypeasytravel.recognizer.domain.repositories

import androidx.camera.core.ImageCapture

interface RecognizerRepository {
    fun takePhoto(imageCapture: ImageCapture?, listener: (text: String) -> Unit)
}
package okunev.projects.easypeasytravel.recognizer.domain.repositories

import androidx.camera.core.ImageCapture

interface RecognizerRepository {
    fun takePhoto(image: ImageCapture?)
}
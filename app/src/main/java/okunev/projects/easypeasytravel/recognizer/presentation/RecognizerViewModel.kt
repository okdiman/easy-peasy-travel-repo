package okunev.projects.easypeasytravel.recognizer.presentation

import androidx.camera.core.ImageCapture
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okunev.projects.easypeasytravel.recognizer.domain.repositories.RecognizerRepository
import javax.inject.Inject

@HiltViewModel
class RecognizerViewModel @Inject constructor(
    private val repository: RecognizerRepository
) : ViewModel() {

    fun onTranslateClick(image: ImageCapture?, listener: (text: String) -> Unit) {
        repository.takePhoto(image, listener)
    }
}
package okunev.projects.easypeasytravel.recognizer.presentation

import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okunev.projects.easypeasytravel.recognizer.domain.repositories.RecognizerRepository
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RecognizerViewModel @Inject constructor(
    private val repository: RecognizerRepository
) : ViewModel() {

    val translatedText = MutableLiveData<String>()

    fun onTranslateClick(image: ImageCapture?, listener: (text: String) -> Unit) {
        repository.takePhoto(image, listener)
    }

    fun translateText(text: String) {
        try {
            viewModelScope.launch {
                translatedText.value = repository.translateText(text)
            }
        } catch (e: IOException) {
            Log.e("CameraXApp", "${e.message}")
        }
    }
}
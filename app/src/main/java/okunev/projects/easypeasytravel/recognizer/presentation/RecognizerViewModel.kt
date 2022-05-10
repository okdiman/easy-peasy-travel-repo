package okunev.projects.easypeasytravel.recognizer.presentation

import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okunev.projects.easypeasytravel.recognizer.domain.repositories.RecognizerRepository
import javax.inject.Inject

@HiltViewModel
class RecognizerViewModel @Inject constructor(
    private val repository: RecognizerRepository
) : ViewModel() {

    private val _translatedText = MutableLiveData<String>()
    val translatedText: LiveData<String>
        get() = _translatedText

    private var translateJob: Job? = null

    fun onTranslateClick(image: ImageCapture?, listener: (text: String) -> Unit) {
        repository.takePhoto(image, listener)
    }

    fun translateText(text: String) {
        try {
            translateJob?.cancel()
            translateJob = viewModelScope.launch(Dispatchers.IO) {
                _translatedText.postValue(repository.translateText(text))
            }
        } catch (t: Throwable) {
            Log.e("CameraXApp", "${t.message}")
        }
    }
}
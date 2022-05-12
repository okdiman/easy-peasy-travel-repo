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
import okunev.projects.easypeasytravel.recognizer.domain.usecases.api.TakePhotoUseCase
import okunev.projects.easypeasytravel.recognizer.domain.usecases.api.TranslateTextUseCase
import javax.inject.Inject

@HiltViewModel
class RecognizerViewModel @Inject constructor(
    private val translateText: TranslateTextUseCase,
    private val takePhoto: TakePhotoUseCase
) : ViewModel() {

    private val _translatedText = MutableLiveData<String>()
    val translatedText: LiveData<String>
        get() = _translatedText

    private var translateJob: Job? = null

    fun onTranslateClick(image: ImageCapture?, listener: (text: String) -> Unit) {
        takePhoto(image, listener)
    }

    fun translate(text: String) {
        try {
            translateJob?.cancel()
            translateJob = viewModelScope.launch(Dispatchers.IO) {
                _translatedText.postValue(translateText(text))
            }
        } catch (t: Throwable) {
            Log.e("CameraXApp", "${t.message}")
        }
    }
}
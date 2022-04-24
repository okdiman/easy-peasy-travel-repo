package okunev.projects.easypeasytravel.recognizer.data.analyzer

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage

class ImageAnalyzer(private val imageListener: (image: InputImage) -> Unit) :
    ImageAnalysis.Analyzer {
    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {
        val mediaImage = image.image
        if (mediaImage != null) {
            val imageInput = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)
            imageListener(imageInput)
        }
//        image.close()
    }
}
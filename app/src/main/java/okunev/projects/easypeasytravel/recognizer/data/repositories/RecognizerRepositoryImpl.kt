package okunev.projects.easypeasytravel.recognizer.data.repositories

import android.content.ContentValues
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import okunev.projects.easypeasytravel.recognizer.domain.repositories.RecognizerRepository
import java.util.*
import javax.inject.Inject

class RecognizerRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : RecognizerRepository {

    override fun takePhoto(imageCapture: ImageCapture?, listener: (text: String) -> Unit) {
        if (imageCapture == null) return
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, MIME_TYPE)
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, RELATIVE_PATH)
            }
        }
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                context.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val uri = output.savedUri
                    if (uri != null) {
                        val imageSaved = InputImage.fromFilePath(context, uri)
                        createRecognizer(imageSaved, listener)
                        context.contentResolver.delete(uri, null, null)
                    }
                }
            }
        )
    }

    private fun createRecognizer(image: InputImage, listener: (text: String) -> Unit) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(image)
            .addOnSuccessListener { result ->
                listener(result.text)
                val resultText = result.text
                Log.e(TAG, resultText)
//                for (block in result.textBlocks) {
//                    val blockText = block.text
//                    val blockCornerPoints = block.cornerPoints
//                    val blockFrame = block.boundingBox
//                    Log.e(TAG, "block - $blockText / $blockCornerPoints / $blockFrame")
//                    for (line in block.lines) {
//                        val lineText = line.text
//                        val lineCornerPoints = line.cornerPoints
//                        val lineFrame = line.boundingBox
//                        Log.e(TAG, "line - $lineText / $lineCornerPoints / $lineFrame")
//                        for (element in line.elements) {
//                            val elementText = element.text
//                            val elementCornerPoints = element.cornerPoints
//                            val elementFrame = element.boundingBox
//                            Log.e(TAG, "element - $elementText / $elementCornerPoints / $elementFrame")
//                        }
//                    }
//                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "failure - $e")
            }
    }

    companion object {
        private const val MIME_TYPE= "image/jpeg"
        private const val RELATIVE_PATH = "Pictures/CameraX-Image"
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}
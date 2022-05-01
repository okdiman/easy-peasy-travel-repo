package okunev.projects.easypeasytravel.recognizer.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import okunev.projects.easypeasytravel.R
import okunev.projects.easypeasytravel.databinding.RecognizerFragmentBinding
import okunev.projects.easypeasytravel.recognizer.data.analyzer.ImageAnalyzer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class RecognizerFragment : Fragment(R.layout.recognizer_fragment) {
    private val binding: RecognizerFragmentBinding by viewBinding()
    private val viewModel by viewModels<RecognizerViewModel>()

    private var imageCapture: ImageCapture? = null

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vImageCaptureButton.setOnClickListener {
            viewModel.onTranslateClick(imageCapture){ recognizedText ->
                binding.vTranslateTextView.text = recognizedText
            }
        }
        binding.vClearTextButton.setOnClickListener {
            binding.vTranslateTextView.text = ""
        }
        startCamera()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            Log.e(TAG, "req")
            if (allPermissionsGranted()) {
                Log.e(TAG, "granted")
                startCamera()
            } else {
                Log.e(TAG, "else")
                showRationaleForCamera()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.vViewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()

            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, ImageAnalyzer { image ->
//                        createRecognizer(image)
                    })
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture, imageAnalyzer
                )
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun allPermissionsGranted() =
        REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                requireContext(), it
            ) == PackageManager.PERMISSION_GRANTED
        }


    private fun showRationaleForCamera() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.permissions_show_rationale_title)
            .setMessage(R.string.permissions_show_rationale_message)
            .setCancelable(false)
            .setNegativeButton(R.string.permissions_show_rationale_negative_button) { _, _ -> }
            .setPositiveButton(R.string.permissions_show_rationale_positive_button) { _, _ ->
                startCamera()
            }
            .show()
    }

    private fun onCameraDenied() {
        Toast.makeText(
            requireContext(), R.string.permission_denied_notify, Toast.LENGTH_LONG
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }


    companion object {
        private const val TAG = "CameraXApp"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}
package okunev.projects.easypeasytravel.recognizer.presentation

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import okunev.projects.easypeasytravel.R
import okunev.projects.easypeasytravel.databinding.RecognizerFragmentBinding

@AndroidEntryPoint
class RecognizerFragment : Fragment(R.layout.recognizer_fragment) {
    private val binding: RecognizerFragmentBinding by viewBinding()
    private val viewModel by viewModels<RecognizerViewModel>()

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var imageCapture: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerPermissionListener()
        checkPermissions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun registerPermissionListener() {
        permissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                if (permissions.values.contains(false)) {
                    onCameraDenied()
                } else {
                    startCamera()
                }
            }
    }

    private fun checkPermissions() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> startCamera()
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                showRationaleForCamera()
            }
            else -> {
                permissionLauncher.launch(REQUIRED_PERMISSIONS)
            }
        }
    }

    private fun showRationaleForCamera() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.permissions_show_rationale_title)
            .setMessage(R.string.permissions_show_rationale_message)
            .setCancelable(false)
            .setNegativeButton(R.string.permissions_show_rationale_negative_button) { _, _ ->
                onCameraDenied()
            }
            .setPositiveButton(R.string.permissions_show_rationale_positive_button) { _, _ ->
                permissionLauncher.launch(REQUIRED_PERMISSIONS)
            }
            .show()
    }

    private fun onCameraDenied() {
        Snackbar.make(binding.root, R.string.permission_denied_notify, Snackbar.LENGTH_LONG).show()
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

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (exc: Exception) {

            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun setClickListeners() {
        binding.apply {
            vImageCaptureButton.setOnClickListener {
                viewModel.onTranslateClick(imageCapture) { recognizedText ->
                    binding.vTranslateTextView.text = recognizedText
                }
            }
            vClearTextButton.setOnClickListener { binding.vTranslateTextView.text = "" }
        }
    }

    companion object {
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
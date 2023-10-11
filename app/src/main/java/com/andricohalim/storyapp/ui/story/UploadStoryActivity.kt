package com.andricohalim.storyapp.ui.story

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.andricohalim.storyapp.response.Result
import com.andricohalim.storyapp.R
import com.andricohalim.storyapp.ViewModelFactory
import com.andricohalim.storyapp.databinding.ActivityUploadStoryBinding

//class UploadStoryActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityUploadStoryBinding
//
//    private var currentImageUri: Uri? = null
//
//    private val viewModel by viewModels<UploadStoryViewModel> {
//        ViewModelFactory.getInstance()
//    }
//
//    private val requestPermissionLauncher =
//        registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (isGranted) {
//                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
//            }
//        }
//
//    private fun allPermissionsGranted() =
//        ContextCompat.checkSelfPermission(
//            this,
//            REQUIRED_PERMISSION
//        ) == PackageManager.PERMISSION_GRANTED
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityUploadStoryBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        if (!allPermissionsGranted()) {
//            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
//        }
//
//        binding.btnGallery.setOnClickListener { startGallery() }
//        binding.btnCamera.setOnClickListener { startCamera() }
//        binding.btnUpload.setOnClickListener { uploadImage() }
//    }
//
//    private fun startGallery() {
//        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//    }
//
//    private val launcherGallery = registerForActivityResult(
//        ActivityResultContracts.PickVisualMedia()
//    ) { uri: Uri? ->
//        if (uri != null) {
//            currentImageUri = uri
//            showImage()
//        } else {
//            Log.d("Photo Picker", "No media selected")
//        }
//    }
//
//    private fun startCamera() {
//        currentImageUri = getImageUri(this)
//        launcherIntentCamera.launch(currentImageUri)
//    }
//
//    private val launcherIntentCamera = registerForActivityResult(
//        ActivityResultContracts.TakePicture()
//    ) { isSuccess ->
//        if (isSuccess) {
//            showImage()
//        }
//    }
//
//    private fun showImage() {
//        currentImageUri?.let {
//            Log.d("Image URI", "showImage: $it")
//            binding.previewImageView.setImageURI(it)
//        }
//    }
//
//    private fun uploadImage() {
//        currentImageUri?.let { uri ->
//            val imageFile = uriToFile(uri, this).reduceFileImage()
//            Log.d("Image File", "showImage: ${imageFile.path}")
//            val description = "Ini adalah deksripsi gambar"
//
////            showLoading(true)
////            val requestBody = description.toRequestBody("text/plain".toMediaType())
////            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
////            val multipartBody = MultipartBody.Part.createFormData(
////                "photo",
////                imageFile.name,
////                requestImageFile
////            )
////            lifecycleScope.launch {
////                try {
////                    val apiService = ApiConfig.getApiService()
////                    val successResponse = apiService.uploadImage(multipartBody, requestBody)
////                    showToast(successResponse.message)
////                    showLoading(false)
////                } catch (e: HttpException) {
////                    val errorBody = e.response()?.errorBody()?.string()
////                    val errorResponse = Gson().fromJson(errorBody, FileUploadResponse::class.java)
////                    showToast(errorResponse.message)
////                    showLoading(false)
////                }
////            }
//
//            viewModel.uploadImage(imageFile, description).observe(this) { result ->
//                if (result != null) {
//                    when (result) {
//                        is Result.Loading -> {
//                            showLoading(true)
//                        }
//
//                        is Result.Success -> {
//                            showToast(result.data.message)
//                            showLoading(false)
//                        }
//
//                        is Result.Error -> {
//                            showToast(result.error)
//                            showLoading(false)
//                        }
//                    }
//                }
//            }
//        }
//    }
////        } ?: showToast(getString(R.string.empty_image_warning))
////    }
////
//    private fun showLoading(isLoading: Boolean) {
//        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }
//
//    private fun showToast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }
//
//    companion object {
//        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
//    }
//}

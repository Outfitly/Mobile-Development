package com.android.outfitly.ui.dashboard

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.android.outfitly.data.api.ApiClient
import com.android.outfitly.databinding.FragmentScanBinding
import com.android.outfitly.ui.recommendation.RecommendationActivity
import com.android.outfitly.util.DataHelper
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    private var photoUri: Uri? = null
    private val scanViewModel by lazy {
        ViewModelProvider(requireActivity())[ScanViewModel::class.java]
    }

    // Launcher untuk mengambil foto dari kamera
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            photoUri?.let { uri ->
                binding.imageView.setImageURI(uri)
                binding.tvChooseImage.visibility = View.GONE
                binding.btnGenerate.isEnabled = true
            }
        }
    }

    // Launcher untuk memilih gambar dari galeri
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImageUri = result.data?.data
            selectedImageUri?.let { uri ->
                binding.imageView.setImageURI(uri)
                photoUri = uri
                binding.tvChooseImage.visibility = View.GONE
                binding.btnGenerate.isEnabled = true
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set initial state
        binding.btnGenerate.isEnabled = false
        binding.tvChooseImage.visibility = View.VISIBLE

        // Listener untuk tombol Gallery
        binding.btnGallery.setOnClickListener {
            openGallery()
        }

        // Listener untuk tombol Camera
        binding.btnCamera.setOnClickListener {
            dispatchTakePictureIntent()
        }
        binding.btnGenerate.setOnClickListener {
            uploadImage()
        }

        scanViewModel.recommendationResponse.observe(viewLifecycleOwner) {
            DataHelper.recommendationResponse = it
            startActivity(Intent(requireContext(), RecommendationActivity::class.java))
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            try {
                val photoFile: File? = createImageFile()
                photoFile?.also {
                    photoUri = FileProvider.getUriForFile(
                        requireContext(),
                        "${requireContext().packageName}.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    cameraLauncher.launch(takePictureIntent)
                }
            } catch (ex: IOException) {
                Toast.makeText(requireContext(), "Error creating file", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = requireActivity().getExternalFilesDir(null)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    private fun uploadImage() {
        photoUri?.let { uri ->
            val file = uriToFile(uri, requireContext())
            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            val imagePart = MultipartBody.Part.createFormData("image", file.name, requestBody)
            scanViewModel.getRecommendation(imagePart)
        }
    }

    // Tambahkan method untuk mendapatkan path file dari URI
    private fun getRealPathFromURI(uri: Uri): String {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireContext().contentResolver.query(uri, filePathColumn, null, null, null)
        cursor?.use {
            it.moveToFirst()
            val columnIndex = it.getColumnIndex(filePathColumn[0])
            return it.getString(columnIndex)
        }
        return uri.path ?: ""
    }

    private fun uriToFile(imageUri: Uri, context: Context): File {
        val myFile = createImageFile()
        val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
        val outputStream = FileOutputStream(myFile)
        val buffer = ByteArray(1024)
        var length: Int

        while (inputStream.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }
        outputStream.close()
        inputStream.close()

        return myFile
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
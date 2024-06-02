package com.pbm.koetaradjatrip.halaman

import DataViewModel
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pbm.koetaradjatrip.databinding.FragmentAddDataBinding
import com.pbm.koetaradjatrip.models.Data
import com.pbm.koetaradjatrip.models.DataViewModel
import java.io.ByteArrayOutputStream

class AddDataFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var editTextName: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var buttonSelectImage: Button
    private lateinit var buttonTakePhoto: Button
    private lateinit var buttonAdd: Button

    private val SELECT_IMAGE_REQUEST = 1
    private val TAKE_PHOTO_REQUEST = 2

    private lateinit var viewModel: DataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddDataBinding.inflate(inflater, container, false)
        val rootView = binding.root

        imageView = binding.imagePreview
        editTextName = binding.editTextName
        editTextDescription = binding.editTextDescription
        buttonSelectImage = binding.buttonSelectImage
        buttonTakePhoto = binding.buttonTakePhoto
        buttonAdd = binding.buttonAdd

        buttonSelectImage.setOnClickListener {
            selectImage()
        }

        buttonTakePhoto.setOnClickListener {
            takePhoto()
        }

        buttonAdd.setOnClickListener {
            saveToDatabase()
        }

        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        return rootView
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, SELECT_IMAGE_REQUEST)
    }

    private fun takePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    TAKE_PHOTO_REQUEST
                )
            } else {
                startCamera()
            }
        } else {
            startCamera()
        }
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, TAKE_PHOTO_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                SELECT_IMAGE_REQUEST -> {
                    data?.data?.let { uri ->
                        imageView.setImageURI(uri)
                    }
                }
                TAKE_PHOTO_REQUEST -> {
                    val photo = data?.extras?.get("data") as Bitmap
                    imageView.setImageBitmap(photo)
                }
            }
        }
    }

    private fun saveToDatabase() {
        val drawable = imageView.drawable
        if (drawable != null) {
            val bitmap = (drawable as BitmapDrawable).bitmap
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val imageByteArray = byteArrayOutputStream.toByteArray()
            byteArrayOutputStream.close()

            val name = editTextName.text.toString().trim()
            val description = editTextDescription.text.toString().trim()

            if (name.isNotEmpty() && description.isNotEmpty()) {
                val data = Data(name = name, description = description, image = imageByteArray)
                viewModel.insertDataFromImage(data)

                clearFields()
                Toast.makeText(requireContext(), "Data saved successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Please select an image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        editTextName.text.clear()
        editTextDescription.text.clear()
        imageView.setImageDrawable(null)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == TAKE_PHOTO_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permission Denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

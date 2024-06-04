package com.pbm.koetaradjatrip.halaman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.pbm.koetaradjatrip.databinding.ModalLayoutBinding
import com.pbm.koetaradjatrip.models.Data
import java.io.File

class ModalDialogFragment(private val data: Data) : DialogFragment() {

    private var _binding: ModalLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ModalLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set gambar, judul, dan deskripsi
        Glide.with(requireContext())
            .load(File(data.imagePath))
            .into(binding.modalImageView)

        binding.modalTitleTextView.text = data.name
        binding.modalDescriptionTextView.text = data.description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

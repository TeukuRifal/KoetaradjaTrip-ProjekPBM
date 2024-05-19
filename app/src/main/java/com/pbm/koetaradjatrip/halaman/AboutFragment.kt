package com.pbm.koetaradjatrip.halaman

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pbm.koetaradjatrip.R
import com.pbm.koetaradjatrip.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the image
        binding.imageView.setImageResource(R.drawable.mesjid)

        // Set the onClickListeners for the social media icons
        binding.whatsappIcon.setOnClickListener {
            // Handle WhatsApp click
            openUrl("https://wa.me/6282298489106")
        }

        binding.instagramIcon.setOnClickListener {
            // Handle Instagram click
            openUrl("https://www.instagram.com/hl.24r/")
        }

        binding.telegramIcon.setOnClickListener {
            // Handle Telegram click
            openUrl("https://t.me/hilal24")
        }
    }

    // Helper function to open a URL
    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

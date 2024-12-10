package com.android.outfitly.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.outfitly.data.local.SessionManager
import com.android.outfitly.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        sessionManager = SessionManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Gunakan SessionManager untuk mendapatkan nama/email
        val userName = sessionManager.getUserName()
        val userEmail = sessionManager.getUserEmail()

        // Contoh penggunaan nama atau email
        binding.tvGreetings.text = "Hi $userName"
        // Jika Anda ingin menampilkan email
        // binding.tvGreetings.text = "Hi $userEmail"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.android.outfitly.ui.Profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.outfitly.LoginActivity
import com.android.outfitly.data.local.SessionManager
import com.android.outfitly.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileViewModel: com.android.outfitly.viewmodel.ProfileViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inisialisasi SessionManager
        sessionManager = SessionManager(requireContext())

        // Inisialisasi ViewModel dengan Factory
        val factory = ProfileViewModelFactory(sessionManager)
        profileViewModel = ViewModelProvider(this, factory)[com.android.outfitly.viewmodel.ProfileViewModel::class.java]

        // Inflate layout
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Observasi data username dan email
        profileViewModel.userName.observe(viewLifecycleOwner) { username ->
            binding.tvUsernameProfile.text = username
        }

        profileViewModel.userEmail.observe(viewLifecycleOwner) { email ->
            binding.emailTextView.text = email
        }

        // Setup logout button
        binding.btnLogout.setOnClickListener {
            // Panggil metode logout dari ViewModel
            profileViewModel.logout()

            // Arahkan ke LoginActivity
            val intent = Intent(requireContext(), LoginActivity::class.java)
            // Tambahkan flag untuk membersihkan task
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            // Optional: Pastikan fragment/activity saat ini ditutup
            requireActivity().finish()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
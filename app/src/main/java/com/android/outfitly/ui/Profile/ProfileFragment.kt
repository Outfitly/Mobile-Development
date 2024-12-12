package com.android.outfitly.ui.Profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.outfitly.LoginActivity
import com.android.outfitly.R
import com.android.outfitly.data.local.SessionManager
import com.android.outfitly.databinding.FragmentProfileBinding
import com.android.outfitly.ui.AboutActivity

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val sessionManager by lazy {
        SessionManager(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvUsernameProfile.text = sessionManager.getUserName()
        binding.emailTextView.text = sessionManager.getUserEmail()

        binding.btnLogout.setOnClickListener {
            sessionManager.clearSession()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finishAffinity()
        }


        binding.btnAboutapp.setOnClickListener {
            startActivity(Intent(requireContext(), AboutActivity::class.java))
        }
    }
}
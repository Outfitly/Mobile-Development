package com.android.outfitly.ui.Profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.outfitly.data.local.SessionManager

class ProfileViewModelFactory(private val sessionManager: SessionManager) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(com.android.outfitly.viewmodel.ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return com.android.outfitly.viewmodel.ProfileViewModel(sessionManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
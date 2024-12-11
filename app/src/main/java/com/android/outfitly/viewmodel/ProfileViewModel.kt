package com.android.outfitly.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.outfitly.data.local.SessionManager

class ProfileViewModel(private val sessionManager: SessionManager) : ViewModel() {
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> = _userEmail

    init {
        // Ambil data user dari SessionManager saat ViewModel dibuat
        _userName.value = sessionManager.getUserName()
        _userEmail.value = sessionManager.getUserEmail()
    }

    fun logout() {
        sessionManager.logout()
    }
}
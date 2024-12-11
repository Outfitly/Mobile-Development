package com.android.outfitly.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.outfitly.data.local.SessionManager

class MainViewModel(private val sessionManager: SessionManager) : ViewModel() {
    // State navigasi
    private val _navigationState = MutableLiveData<NavigationState>()
    val navigationState: LiveData<NavigationState> = _navigationState

    // Enum untuk state navigasi
    enum class NavigationState {
        LOGIN,
        MAIN
    }

    fun checkLoginStatus() {
        if (!sessionManager.isLoggedIn()) {
            // Redirect ke halaman login
            _navigationState.value = NavigationState.LOGIN
        } else {
            _navigationState.value = NavigationState.MAIN
        }
    }

    fun performLogin(name: String, email: String) {
        sessionManager.saveLoginSession(name, email)
        _navigationState.value = NavigationState.MAIN
    }

    fun performLogout() {
        sessionManager.logout()
        // Redirect ke halaman login
        _navigationState.value = NavigationState.LOGIN
    }
}
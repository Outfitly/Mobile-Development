package com.android.outfitly

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.android.outfitly.data.local.SessionManager
import com.android.outfitly.databinding.ActivityMainBinding
import com.android.outfitly.viewmodel.MainViewModel
import com.android.outfitly.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi SessionManager
        sessionManager = SessionManager(this)

        // Cek apakah sudah login
        if (!sessionManager.isLoggedIn()) {
            // Jika tidak login, arahkan ke LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        // Lanjutkan dengan setup MainActivity jika sudah login
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi ViewModel
        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(sessionManager)
        ).get(MainViewModel::class.java)

        val navView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        // Ambil data dari SessionManager
        val userName = sessionManager.getUserName()

        val bundle = Bundle()
        bundle.putString("USER_NAME", userName)
        navController.setGraph(R.navigation.mobile_navigation, bundle)

        // Observasi state navigasi (opsional)
        mainViewModel.navigationState.observe(this) { state ->
            when (state) {
                MainViewModel.NavigationState.LOGIN -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                else -> {} // Tidak perlu aksi untuk state MAIN
            }
        }
    }

    // Tambahkan fungsi logout yang bisa dipanggil dari fragment atau menu
    fun logout() {
        mainViewModel.performLogout()
    }
}
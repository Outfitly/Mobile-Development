package com.android.outfitly

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.outfitly.data.api.ApiClient
import com.android.outfitly.data.api.model.LoginBody
import com.android.outfitly.data.local.SessionManager
import com.android.outfitly.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    private val binding by viewBinding(ActivityLoginBinding::bind)
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(this)

        // ... existing code ...

        binding.btnLogin.setOnClickListener {
            val email = binding.edtUsernameLogin.text.toString().trim()
            val password = binding.edtPasswordLogin.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                performLogin(email, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performLogin(email: String, password: String) {
        lifecycleScope.launch {
            try {
                val response = ApiClient.instance.userLogin(LoginBody(email, password))

                // Simpan sesi login
                sessionManager.saveLoginSession(response.name, email)

                Toast.makeText(this@LoginActivity, "Welcome, ${response.name}!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                Log.e("LoginError", "Login failed: ${e.message}")
                Toast.makeText(this@LoginActivity, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

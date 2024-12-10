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
import com.android.outfitly.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    private val binding by viewBinding(ActivityLoginBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Login Button
        binding.btnLogin.setOnClickListener {
            val email = binding.edtUsernameLogin.text.toString().trim()
            val password = binding.edtPasswordLogin.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                performLogin(email, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigate to Sign Up
        binding.tvSignup.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performLogin(email: String, password: String) {
        // Menjalankan coroutine untuk memanggil API
        lifecycleScope.launch {
            try {
                val response = ApiClient.instance.userLogin(LoginBody(email, password))
                // Jika login berhasil
                Toast.makeText(this@LoginActivity, "Welcome, ${response.name}!", Toast.LENGTH_SHORT).show()

                // Navigate ke MainActivity
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.putExtra("USER_NAME", response.name)
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                // Jika terjadi error
                Log.e("LoginError", "Login failed: ${e.message}")
                Toast.makeText(this@LoginActivity, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

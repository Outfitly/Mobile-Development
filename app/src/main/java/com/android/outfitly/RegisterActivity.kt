package com.android.outfitly

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.outfitly.data.api.ApiClient
import com.android.outfitly.data.api.model.SignUpBody
import com.android.outfitly.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity(R.layout.activity_register) {
    private val binding by viewBinding(ActivityRegisterBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnSignUp.setOnClickListener {
            val name = binding.edtUsername.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val confirmPassword = binding.edtConfirmPassword.text.toString()
            val birthdate = binding.edtBirthdate.text.toString()

            if (validateInput(name, email, password, confirmPassword, birthdate)) {
                registerUser(name, email, password, birthdate)
            }
        }
    }

    private fun validateInput(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        birthdate: String
    ): Boolean {
        return when {
            name.isEmpty() || email.isEmpty() || password.isEmpty() || birthdate.isEmpty() -> {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                false
            }
            password != confirmPassword -> {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun registerUser(name: String, email: String, password: String, birthdate: String) {
        binding.pbRegister.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val response = ApiClient.instance.userRegister(
                    SignUpBody(email = email, password = password, name = name, birthdate = birthdate)
                )
                binding.pbRegister.visibility = View.GONE
                Toast.makeText(this@RegisterActivity, response.message, Toast.LENGTH_SHORT).show()

                // Redirect to LoginActivity on success
                if (response.uid.isNotEmpty()) {
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } catch (e: Exception) {
                binding.pbRegister.visibility = View.GONE
                Toast.makeText(this@RegisterActivity, "Registration failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

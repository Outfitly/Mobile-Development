package com.android.outfitly

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.outfitly.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(R.layout.activity_register)  {
    private val binding by viewBinding(ActivityRegisterBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
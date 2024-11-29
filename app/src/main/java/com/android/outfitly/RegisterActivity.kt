package com.android.outfitly

import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.outfitly.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(R.layout.activity_register)  {
    private val binding by viewBinding(ActivityRegisterBinding::bind)

}
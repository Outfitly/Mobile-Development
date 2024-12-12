package com.android.outfitly.ui

import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.outfitly.R
import com.android.outfitly.databinding.ActivityAboutAppBinding
import com.android.outfitly.databinding.ActivityRegisterBinding

class AboutActivity : AppCompatActivity(R.layout.activity_about_app) {
    private val binding by viewBinding(ActivityAboutAppBinding::bind)

}
package com.android.outfitly.ui.recommendation

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.outfitly.MainActivity
import com.android.outfitly.databinding.ActivityRecommendationBinding
import com.android.outfitly.ui.dashboard.ScanViewModel
import com.android.outfitly.util.DataHelper
import com.bumptech.glide.Glide

class RecommendationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (DataHelper.recommendationResponse != null) {
            Glide.with(this)
                .load(DataHelper.recommendationResponse!!.url)
                .into(binding.ivOriginalImage)

            // Set category
            binding.tvCategory.text = "Category: ${DataHelper.recommendationResponse!!.category}"

            // Set labels
            binding.tvLabels.text = "Labels: ${DataHelper.recommendationResponse!!.labels.joinToString(", ")}"

            // Set up RecyclerView
            binding.rvRecommendations.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            binding.rvRecommendations.adapter = RecommendationAdapter(DataHelper.recommendationResponse!!.recommendations)
        }
    }
}
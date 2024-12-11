package com.android.outfitly.ui.recommendation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.outfitly.data.model.RecommendationResponse
import com.android.outfitly.databinding.ActivityRecommendationBinding
import com.bumptech.glide.Glide

class RecommendationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Terima data dari intent

        val recommendationResponse = intent.getParcelableExtra<RecommendationResponse>("RECOMMENDATION")
        recommendationResponse?.let { response ->
            // Set original image
            Glide.with(this)
                .load(response.url)
                .into(binding.ivOriginalImage)

            // Set category
            binding.tvCategory.text = "Category: ${response.category}"

            // Set labels
            binding.tvLabels.text = "Labels: ${response.labels.joinToString(", ")}"

            // Set up RecyclerView
            binding.rvRecommendations.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            binding.rvRecommendations.adapter = RecommendationAdapter(response.recommendations)

            if (recommendationResponse == null) {
                Log.e("RecommendationActivity", "RecommendationResponse is null!")
            } else {
                Log.d("RecommendationActivity", "RecommendationResponse: $recommendationResponse")
            }
        }
    }
}
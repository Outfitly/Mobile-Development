package com.android.outfitly.ui.recommendation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.outfitly.data.model.Recommendation
import com.android.outfitly.databinding.ItemRecommendationBinding
import com.bumptech.glide.Glide

class RecommendationAdapter(private val recommendations: List<Recommendation>) :
    RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recommendation: Recommendation) {
            binding.tvRecommendationName.text = recommendation.name
            Glide.with(binding.root.context)
                .load(recommendation.image)
                .into(binding.ivRecommendation)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecommendationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recommendations[position])
    }

    override fun getItemCount() = recommendations.size
}
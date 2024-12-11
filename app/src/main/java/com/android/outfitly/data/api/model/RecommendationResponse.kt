package com.android.outfitly.data.model

data class RecommendationResponse(
    val message: String,
    val category: String,
    val recommendations: List<Recommendation>,
    val labels: List<String>,
    val url: String
)

data class Recommendation(
    val name: String,
    val image: String
)
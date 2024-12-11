package com.android.outfitly.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecommendationResponse(
    val message: String,
    val category: String,
    val recommendations: List<Recommendation>,
    val labels: List<String>,
    val url: String
) : Parcelable

@Parcelize
data class Recommendation(
    val name: String,
    val image: String
) : Parcelable
package com.android.outfitly.repository

import com.android.outfitly.data.api.ApiService
import com.android.outfitly.data.api.model.ListNews

class NewsRepository (private val apiService: ApiService) {
    suspend fun getHeadingNews(query: String, apiKey: String): ListNews {
        return apiService.getHeadingNews(query, apiKey)



    }

}

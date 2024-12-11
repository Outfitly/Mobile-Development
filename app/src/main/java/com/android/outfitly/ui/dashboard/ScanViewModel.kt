package com.android.outfitly.ui.dashboard

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.outfitly.data.api.ApiClient
import com.android.outfitly.data.model.RecommendationResponse
import com.android.outfitly.ui.recommendation.RecommendationActivity
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ScanViewModel(application: Application) : AndroidViewModel(application) {
    val recommendationResponse = MutableLiveData<RecommendationResponse>()
    val exception = MutableLiveData<Boolean>()

    fun getRecommendation(imagePart: MultipartBody.Part) {
        viewModelScope.launch {
            try {
                val response = ApiClient.uploadService.uploadImage(imagePart)
                if (response.isSuccessful) {
                    response.body()?.let { recommendationResponse ->
                        this@ScanViewModel.recommendationResponse.postValue(recommendationResponse)
                    }
                    exception.postValue(false)
                } else {
                    exception.postValue(true)
                }
            } catch (e: Exception) {
                exception.postValue(true)
            }
        }
    }
}
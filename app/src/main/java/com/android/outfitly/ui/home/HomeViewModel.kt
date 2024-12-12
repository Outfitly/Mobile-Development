package com.android.outfitly.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.outfitly.data.api.ApiClient
import com.android.outfitly.data.api.model.Article
import com.android.outfitly.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val newsRepository = NewsRepository(ApiClient.apiClient)
    val newsFashion = MutableLiveData<List<Article>>()
    val error = MutableLiveData<Boolean>()

    fun getNewsFashion() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                newsFashion.postValue(newsRepository.getHeadingNews("uniqlo", "dd1d1090bb2543fb9553dc9c1626aa3c").articles)
                error.postValue(false)
            }
            catch (e: Exception) {

                error.postValue(true)
            }
        }
    }

    fun resetErrorValue() {
        error.value = false
    }

}


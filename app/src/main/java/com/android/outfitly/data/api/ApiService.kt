package com.android.outfitly.data.api

import com.android.outfitly.data.api.model.ListNews
import com.android.outfitly.data.api.model.LoginBody
import com.android.outfitly.data.api.model.LoginResponse
import com.android.outfitly.data.api.model.SignUpBody
import com.android.outfitly.data.api.model.SignUpResponse
import com.android.outfitly.data.model.RecommendationResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @POST("signup")
    suspend fun userRegister(@Body signUpBody: SignUpBody): SignUpResponse

    @POST("login")
    suspend fun userLogin(@Body loginBody: LoginBody): LoginResponse

    @Multipart
    @POST("upload-and-recommend")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<RecommendationResponse>

    @GET("everything")
    suspend fun getHeadingNews(@Query("q") query: String, @Query("apiKey") apiKey: String) : ListNews
}
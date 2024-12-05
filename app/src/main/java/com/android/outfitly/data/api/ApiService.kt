package com.android.outfitly.data.api

import com.android.outfitly.data.api.model.LoginBody
import com.android.outfitly.data.api.model.LoginResponse
import com.android.outfitly.data.api.model.SignUpBody
import com.android.outfitly.data.api.model.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("signup")
    suspend fun userRegister(@Body signUpBody: SignUpBody): SignUpResponse

    @POST("login")
    suspend fun userLogin(@Body loginBody: LoginBody): LoginResponse

}
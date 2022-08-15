package com.example.suitmediatest.service

import com.example.suitmediatest.model.ResponseReqres
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RegresServiceInstance {
    @GET("users")
    suspend fun getListRegres(
        @Query("page") page:Int,
        @Query("per_page") size:Int
    ): Response<ResponseReqres>
}
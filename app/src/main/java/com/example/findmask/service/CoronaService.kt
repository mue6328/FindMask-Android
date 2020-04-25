package com.example.findmask.service

import com.example.findmask.model.CoronaInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CoronaService {
    @GET("korea/")
    fun getCoronaInfo(
       @Query("serviceKey") serviceKey: String
    ) : Call<CoronaInfo>
}
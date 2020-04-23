package com.example.findmask.service

import com.example.findmask.model.CoronaInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface CoronaService {
    @GET("korea/")
    fun getCoronaInfo(
       @Header("serviceKey") serviceKey: String
    ) : Call<CoronaInfo>
}
package com.example.findmask.service

import retrofit2.http.GET
import retrofit2.http.Header

interface CoronaService {
    @GET("korea/")
    fun getCoronaInfo(
       // @Header("serviceKey")
    )
}
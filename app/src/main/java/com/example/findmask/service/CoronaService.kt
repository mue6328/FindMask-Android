package com.example.findmask.service

import com.example.findmask.Utils
import com.example.findmask.model.CoronaInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

class CoronaService {
    interface CoronaServiceImpl {
        @GET("korea/")
        fun getCoronaInfo(
            @Query("serviceKey") serviceKey: String
        ) : Call<CoronaInfo>
    }

    companion object {
        fun getCoronaInfo(serviceKey: String) : Call<CoronaInfo> {
            return Utils.retrofit_CORONA.create(CoronaServiceImpl::class.java).getCoronaInfo(serviceKey)
        }
    }

}
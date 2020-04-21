package com.example.findmask.service

import com.example.findmask.model.MaskByGeoInfo
import com.example.findmask.model.MaskInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MaskService {
    @GET("stores/json/{page}/{perPage}")
    fun getStoresInfo(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ) : Call<MaskInfo>

    @GET("storesByGeo/json?")
    fun getStoreByGeoInfo(
        @Query("lat") lat: Number,
        @Query("lng") lng: Number,
        @Query("m") m: Int
    ) : Call<MaskByGeoInfo>
}
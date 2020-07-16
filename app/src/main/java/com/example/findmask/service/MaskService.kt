package com.example.findmask.service

import com.example.findmask.Utils
import com.example.findmask.model.MaskByGeoInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

class MaskService {

    interface MaskServiceImpl {
        @GET("storesByGeo/json?")
        fun getStoreByGeoInfo(
            @Query("lat") lat: Number,
            @Query("lng") lng: Number,
            @Query("m") m: Int
        ): Call<MaskByGeoInfo>

        @GET("sample.json")
        fun getStoreByGeoInfoSample(
        ): Call<MaskByGeoInfo>
    }

    companion object {
        fun getStoreByGeoInfo(lat: Number, lng: Number, m: Int): Call<MaskByGeoInfo> {
            return Utils.retrofit_MASK.create(MaskServiceImpl::class.java)
                .getStoreByGeoInfo(lat, lng, m)
        }

        fun getStoreByGeoInfoSample(): Call<MaskByGeoInfo> {
            return Utils.retrofit.create(MaskServiceImpl::class.java)
                .getStoreByGeoInfoSample()
        }
    }
}
package com.example.findmask

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Utils {

    companion object {
        private const val MASK_BASE_URL = "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/"

        private const val CORONA_BASE_URL = "http://api.corona-19.kr/"

        private const val URL = "https://gist.githubusercontent.com/junsuk5/bb7485d5f70974deee920b8f0cd1e2f0/raw/063f64d9b343120c2cb01a6555cf9b38761b1d94/"

        var API_KEY: String = "a7b30c61e5dffb05b51900967fe4ba8a1"

        val retrofit_MASK: Retrofit = Retrofit
            .Builder()
            .baseUrl(MASK_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofit: Retrofit = Retrofit
            .Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofit_CORONA: Retrofit = Retrofit
            .Builder()
            .baseUrl(CORONA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}
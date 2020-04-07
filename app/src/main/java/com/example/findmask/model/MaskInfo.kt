package com.example.findmask.model

data class MaskInfo(
    var count: Int,
    var page: Int,
    var storeInfo: List<StoreInfo>
)

data class StoreInfo(
    var addr: String,
    var code: String,
    var lat: Float,
    var lng: Float,
    var name: String,
    var type: String
)
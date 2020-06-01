package com.example.findmask.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class MaskInfo(
    var count: Int,
    var page: Int,
    var storeInfos: List<StoreInfo>
)

data class StoreInfo(
    var addr: String,
    var code: String,
    var lat: Float,
    var lng: Float,
    var name: String,
    var type: String
)

data class MaskByGeoInfo(
    var count: Int,
    var stores: List<StoreSale>
)

data class StoreSale(
    var addr: String,
    var code: String,
    var created_at: String,
    var lat: Number,
    var lng: Number,
    var name: String,
    var remain_stat: String,
    var stock_at: String,
    var type: String
)

@Entity
data class MoreInfo(
    @PrimaryKey
    var name: String,
    var addr: String,
    var remain_stat: String,
    var stock_at: String,
    var created_at: String,
    var isfavorite: Boolean
)
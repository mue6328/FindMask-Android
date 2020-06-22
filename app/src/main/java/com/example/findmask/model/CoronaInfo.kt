package com.example.findmask.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class CoronaInfo(
    var resultCode: String,
    var TotalCase: String,
    var TotalRecovered: String,
    var TotalDeath: String,
    var NowCase: String,
    var city1n: String,
    var city2n: String,
    var city3n: String,
    var city4n: String,
    var city5n: String,
    var city1p: Number,
    var city2p: Number,
    var city3p: Number,
    var city4p: Number,
    var city5p: Number,
    var recoveredPercentage: Number,
    var deathPercentage: Number,
    var checkingCounter: String,
    var checkingPercentage: String,
    var caseCount: String,
    var casePercentage: String,
    var notcaseCount: String,
    var notcasePercentage: String,
    var TotalChecking: String,
    var TodayRecovered: String,
    var TodayDeath: String,
    var TotalCaseBefore: String,
    var updateTime: String,
    var resultMessage: String
)

@Entity
data class CoronaArea(
    @PrimaryKey
    var areaName: String,
    var newCase: String,
    var newFcase: String,
    var newCcase: String,
    var totalCase: String,
    var recovered: String,
    var death: String
)

data class CoronaInfoNew(
    var resultCode: String,
    var resultMessage: String,
    var korea: Korea,
    var seoul: Seoul,
    var busan: Busan,
    var daegu: Daegu,
    var incheon: Incheon,
    var gwangju: Gwangju,
    var daejeon: Daejeon,
    var ulsan: Ulsan,
    var sejong: Sejong,
    var gyeonggi: Gyeonggi,
    var gangwon: Gangwon,
    var chungbuk: ChungBuk,
    var chungnam: ChungNam,
    var jeonbuk: Jeonbuk,
    var jeonnam: Jeonnam,
    var gyeongbuk: Gyeongbuk,
    var gyeongnam: Gyeongnam,
    var jeju: Jeju,
    var quarantine: Quarantine
)

data class Korea(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)

data class Seoul(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)

data class Busan(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)

data class Daegu(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)

data class Incheon(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)

data class Gwangju(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)

data class Daejeon(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)

data class Ulsan(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)

data class Sejong(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)

data class Gyeonggi(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)

data class Gangwon(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)

data class ChungBuk(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)

data class ChungNam(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)

data class Jeonbuk(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)

data class Jeonnam(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)

data class Gyeongbuk(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)

data class Gyeongnam(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)

data class Jeju(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)

data class Quarantine(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)
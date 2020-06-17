package com.example.findmask.model

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

data class CoronaInfoNew(
    var resultCode: String,
    var resultMessage: String,
    var korea: newCase
)

data class newCase(
    var countryName: String,
    var newCase: String,
    var totalCase: String,
    var recovered: String,
    var death: String,
    var percentage: String,
    var newFcase: String,
    var newCcase: String
)
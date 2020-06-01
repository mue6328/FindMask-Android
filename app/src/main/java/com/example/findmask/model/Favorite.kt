package com.example.findmask.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Favorite(
    var addr: String,
    var favorite: Boolean)
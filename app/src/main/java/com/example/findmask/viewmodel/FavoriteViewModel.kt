package com.example.findmask.viewmodel

import androidx.lifecycle.AndroidViewModel
import android.app.Application
import androidx.lifecycle.LiveData
import com.example.findmask.database.CoronaAreaRepository
import com.example.findmask.database.FavoriteRepository
import com.example.findmask.model.CoronaArea
import com.example.findmask.model.MoreInfo

class FavoriteViewModel(app: Application) : AndroidViewModel(app) {
    private val favoriteRepository = FavoriteRepository(app)
    private val allFavorites = favoriteRepository.getFavorites()

    fun getAllFavorites() : LiveData<List<MoreInfo>> {
        return allFavorites
    }

    fun insert(moreInfo: MoreInfo) {
        favoriteRepository.insert(moreInfo)
    }
}
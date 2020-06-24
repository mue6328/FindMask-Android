package com.example.findmask.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.findmask.model.MoreInfo
import java.lang.Exception

class FavoriteRepository(app: Application) {
    private val db = FavoriteDatabase.getInstance(app)
    private val favoriteDao = db!!.favoriteDao()
    private val allFavorites = favoriteDao.getFavorites()

    fun getFavorites(): LiveData<List<MoreInfo>> {
        return allFavorites
    }

    fun deleteAll() {
        try {
            var thread = Thread(Runnable {
                favoriteDao.deleteAll()
            })
            thread.start()
        } catch (e: Exception) {}
    }
}
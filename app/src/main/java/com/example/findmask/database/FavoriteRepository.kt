package com.example.findmask.database

import com.example.findmask.dao.CoronaAreaDao
import android.os.AsyncTask
import com.example.findmask.model.CoronaArea
import android.app.Application
import androidx.lifecycle.LiveData
import com.example.findmask.dao.FavoriteDao
import com.example.findmask.model.MoreInfo
import java.lang.Exception

class FavoriteRepository(app: Application) {
    private val db = FavoriteDatabase.getInstance(app)
    private val favoriteDao = db!!.favoriteDao()
    private val allFavorites = favoriteDao.getFavorites()

    fun getFavorites(): LiveData<List<MoreInfo>> {
        return allFavorites
    }

    fun insert(moreinfo: MoreInfo) {
        try {
            var thread = Thread(Runnable {
                favoriteDao.insert(moreinfo)
            })
            thread.start()
        } catch (e: Exception) {}
    }
}
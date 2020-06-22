package com.example.findmask.database

import com.example.findmask.dao.CoronaAreaDao
import android.os.AsyncTask
import com.example.findmask.model.CoronaArea
import android.app.Application
import androidx.lifecycle.LiveData
import java.lang.Exception

class CoronaAreaRepository(app: Application) {
    private val db = CoronaAreaDatabase.getInstance(app)
    private val coronaAreaDao = db!!.coronaAreaDao()
    private val allCoronaArea = coronaAreaDao.getAreas()

    fun getAllAreas(): LiveData<List<CoronaArea>> {
        return allCoronaArea
    }

    fun insert(coronaArea: CoronaArea) {
        try {
            var thread = Thread(Runnable {
                coronaAreaDao.insert(coronaArea)
            })
            thread.start()
        } catch (e: Exception) {

        }
    }
}
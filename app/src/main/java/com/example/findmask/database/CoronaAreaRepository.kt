package com.example.findmask.database

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

    fun deleteAll() {
        try {
            var thread = Thread(Runnable {
                coronaAreaDao.deleteAll()
            })
            thread.start()
        } catch (e: Exception) {

        }
    }
}
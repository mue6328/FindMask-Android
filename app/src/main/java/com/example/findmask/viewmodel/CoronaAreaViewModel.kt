package com.example.findmask.viewmodel

import androidx.lifecycle.AndroidViewModel
import android.app.Application
import androidx.lifecycle.LiveData
import com.example.findmask.database.CoronaAreaRepository
import com.example.findmask.model.CoronaArea

class CoronaAreaViewModel(app: Application) : AndroidViewModel(app) {
    private val coronaAreaRepository = CoronaAreaRepository(app)
    private val allAreas = coronaAreaRepository.getAllAreas()

    fun getAllAreas() : LiveData<List<CoronaArea>> {
        return allAreas
    }

    fun deleteAll() {
        coronaAreaRepository.deleteAll()
    }
}
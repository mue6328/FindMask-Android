package com.example.findmask.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.findmask.model.CoronaArea

@Dao
interface CoronaAreaDao {
    @Query("SELECT * FROM CoronaArea ORDER BY areaName ASC")
    fun getAreas(): LiveData<List<CoronaArea>>

    @Insert(onConflict = REPLACE)
    fun insert(coronaArea: CoronaArea)

    @Delete
    fun delete(coronaArea: CoronaArea)

    @Query("DELETE from CoronaArea")
    fun deleteAll()
}
package com.example.findmask.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.findmask.model.MoreInfo

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM MoreInfo ORDER BY name ASC")
    fun getFavorites(): LiveData<List<MoreInfo>>

    @Insert(onConflict = REPLACE)
    fun insert(moreInfo: MoreInfo)

    @Delete
    fun delete(moreInfo: MoreInfo)

    @Query("DELETE from MoreInfo")
    fun deleteAll()
}
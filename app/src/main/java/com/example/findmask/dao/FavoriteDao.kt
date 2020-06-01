package com.example.findmask.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.findmask.model.MoreInfo

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM MoreInfo")
    fun getFavorites() : List<MoreInfo>

    @Insert(onConflict = REPLACE)
    fun insert(moreInfo: MoreInfo)

    @Delete
    fun delete(moreInfo: MoreInfo)

//    @Query("select * from MoreInfo where id=:id")
//    fun getFavorite(id: Long) : MoreInfo

    @Query("DELETE from MoreInfo")
    fun deleteAll()
}
package com.example.findmask.database

import com.example.findmask.dao.FavoriteDao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.findmask.model.Favorite
import com.example.findmask.model.MoreInfo

@Database(entities = [MoreInfo::class], version = 1)
abstract class FavoriteDatabase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private var instance : FavoriteDatabase? = null

        fun getInstance(context: Context): FavoriteDatabase? {
            if (instance == null) {
                synchronized(FavoriteDatabase::class) {
                    instance = Room.databaseBuilder(context,
                        FavoriteDatabase::class.java, "favorite.db")
                        .build()
                }
            }
            return instance
        }
    }


}
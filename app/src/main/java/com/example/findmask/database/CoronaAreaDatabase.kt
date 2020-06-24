package com.example.findmask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.findmask.dao.CoronaAreaDao
import com.example.findmask.model.CoronaArea

@Database(entities = [CoronaArea::class], version = 2)
abstract class CoronaAreaDatabase : RoomDatabase() {
    abstract fun coronaAreaDao(): CoronaAreaDao

    companion object {
        private var instance: CoronaAreaDatabase? = null

        fun getInstance(context: Context): CoronaAreaDatabase? {
            if (instance == null) {
                synchronized(CoronaAreaDatabase::class) {
                    instance = Room.databaseBuilder(
                        context,
                        CoronaAreaDatabase::class.java, "corona.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }
    }


}
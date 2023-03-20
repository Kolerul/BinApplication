package com.example.binapplication.data.database

import android.content.Context
import androidx.room.*


@Database(entities = [Number::class], version = 1, exportSchema = false)
abstract class HistoryRoomDatabase: RoomDatabase() {

    abstract fun numberDao(): NumberDao

    companion object{
        @Volatile
        private var INSTANCE: HistoryRoomDatabase? = null

        fun getDatabase(context: Context): HistoryRoomDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryRoomDatabase::class.java,
                    "history_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
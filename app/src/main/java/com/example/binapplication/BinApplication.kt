package com.example.binapplication

import android.app.Application
import com.example.binapplication.data.database.HistoryRoomDatabase

class BinApplication: Application() {
    val database: HistoryRoomDatabase by lazy { HistoryRoomDatabase.getDatabase(this)}
}
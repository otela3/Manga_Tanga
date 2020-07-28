package com.example.mangatangajava.room

import android.app.Application
import androidx.room.Room

class RoomAplication: Application() {
    companion object{
var mangadatabase: MangaDataBase? = null
    }

    override fun onCreate() {
        super.onCreate()
mangadatabase = Room.databaseBuilder(this,MangaDataBase::class.java,"manga_database").build()
    }
}
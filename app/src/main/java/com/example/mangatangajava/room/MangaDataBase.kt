package com.example.mangatangajava.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(MangaEntity::class)], version = 1)
abstract class MangaDataBase: RoomDatabase() {
abstract fun getMangaDao(): MangaDAO

companion object{
    @Volatile
private var INSTANCE:MangaDataBase? = null

fun getDatabase(context: Context): MangaDataBase{

val tempInstance = INSTANCE
    if(tempInstance!=null){
return tempInstance
    }

synchronized(this){
val instance = Room.databaseBuilder(context.applicationContext,MangaDataBase::class.java, "manga_database").build()
  INSTANCE =instance
    return instance
}

}

}
}
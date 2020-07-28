package com.example.mangatangajava.livedata

import androidx.lifecycle.LiveData
import com.example.mangatangajava.room.MangaDAO
import com.example.mangatangajava.room.MangaDataBase
import com.example.mangatangajava.room.MangaEntity
import com.example.mangatangajava.room.RoomAplication
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MangaRepository(private val dataBase: MangaDataBase) {
private val mangaDAO: MangaDAO = RoomAplication.mangadatabase!!.getMangaDao()

val allmanga:LiveData<List<MangaEntity>> = mangaDAO.getAllManga()


suspend fun refreshManga(){
withContext(Dispatchers.IO){
val characterListIbanners = FirebaseDatabase.getInstance().getReference("Banners")
    val characterListIcomics = FirebaseDatabase.getInstance().getReference("Comic")

dataBase.getMangaDao().insertAll(characterListIcomics)
    dataBase.getMangaDao().insertAll(characterListIbanners)
}
}

}
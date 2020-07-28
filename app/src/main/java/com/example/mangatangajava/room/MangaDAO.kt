package com.example.mangatangajava.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.firebase.database.DatabaseReference

@Dao
interface MangaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertManga(mangaEntity: MangaEntity)

    @Insert
    suspend fun updateManga(mangaEntity: MangaEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(comics: DatabaseReference)


    @Delete
    fun clearAllManga(vararg mangas: MangaEntity)

    @Query("SELECT * FROM manga_tanga")
    fun getAllManga(): LiveData<List<MangaEntity>>
}
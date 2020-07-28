package com.example.mangatangajava.room

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mangatangajava.model.Chapter

@Entity(tableName = "manga_tanga")
data class MangaEntity (
        @PrimaryKey @NonNull val Name: String,
        val Image: String,
        val Category: String,
        //val Chapter: List<Chapter>

)

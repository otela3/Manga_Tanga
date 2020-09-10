package com.example.mangatangajava.ui.chapter;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mangatangajava.Interface.IComics;
import com.example.mangatangajava.model.Chapter;
import com.example.mangatangajava.model.Manga;
import com.example.mangatangajava.ui.home.RepoComic;

import java.util.ArrayList;

public class ChapterViewModel extends ViewModel {
    MutableLiveData<ArrayList<Chapter>> chapters;
    public void init3(IComics comics, Context context){
        if(chapters!=null){
            return;
        }
        chapters = RepoComic.getInstance(comics).getChapter(context);
    }
    public LiveData<ArrayList<Chapter>> getChapter(){
        return chapters;
    }
}


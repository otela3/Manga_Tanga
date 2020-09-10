package com.example.mangatangajava.ui.slideshow;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mangatangajava.Interface.IComics;
import com.example.mangatangajava.model.Manga;

import java.util.ArrayList;

public class SlideshowViewModel extends ViewModel {

    MutableLiveData<ArrayList<Manga>> fav;
    public void init4(IComics comics, Context context){
        if(fav!=null){
            return;
        }
        fav = RepoFav.getInstance(comics).getFav(context);
    }
    public LiveData<ArrayList<Manga>> getFav(){
        return fav;
    }
}
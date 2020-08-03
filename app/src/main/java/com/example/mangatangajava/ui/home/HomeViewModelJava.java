package com.example.mangatangajava.ui.home;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mangatangajava.Interface.IBanners;
import com.example.mangatangajava.Interface.IComics;
import com.example.mangatangajava.model.Manga;

import java.util.ArrayList;


public class HomeViewModelJava extends ViewModel {
    MutableLiveData<ArrayList<String>> banner;
    MutableLiveData<ArrayList<Manga>> manga;

    public void init(IBanners ibanez){
        if(banner!=null){
            return;
        }
        banner = Repo.getInstance(ibanez).getBanner();
    }
    public LiveData<ArrayList<String>> getBanner(){
        return banner;
    }

    public void init2(IComics comics, Context context){
        if(manga!=null){
            return;
        }
        manga = RepoComic.getInstance(comics).getManga(context);
    }
    public LiveData<ArrayList<Manga>> getManga(){
        return manga;
    }
}


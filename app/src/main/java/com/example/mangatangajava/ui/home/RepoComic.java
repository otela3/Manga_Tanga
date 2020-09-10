package com.example.mangatangajava.ui.home;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mangatangajava.Interface.IComics;
import com.example.mangatangajava.common.Common;
import com.example.mangatangajava.model.Chapter;
import com.example.mangatangajava.model.Manga;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class RepoComic implements IComics {
    static RepoComic instance;
    private ArrayList<Manga> mangaModel = new ArrayList<>();
    private ArrayList<Chapter> chapterModel = new ArrayList<>();
    DatabaseReference comics;
    static IComics comicListener;


    public static RepoComic getInstance(IComics comics){
        if(instance==null){
            instance = new RepoComic();
        }
        comicListener = comics;
        return instance;
    }
    //almacenado comic
    public MutableLiveData<ArrayList<Manga>> getManga(Context context){
        loadComic(context);
        MutableLiveData<ArrayList<Manga>> manga = new MutableLiveData<>();
        manga.setValue(mangaModel);
        return manga;
    }
    //almacenado capitulos
    public MutableLiveData<ArrayList<Chapter>>getChapter(Context context) {
        loadComic(context);
        MutableLiveData<ArrayList<Chapter>>chapters = new MutableLiveData<>();
        chapters.setValue(chapterModel);
        return chapters;
    }
    //lectura comic y capitulos
    private void loadComic(Context context) {
        comics = FirebaseDatabase.getInstance().getReference("Comic");
        comics.keepSynced(true);

        comics.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot comicSnapShot : dataSnapshot.getChildren()) {
                    DataSnapshot chi = comicSnapShot.child("Chapters");
                    ArrayList<Chapter> chapters = new ArrayList<>();
                     for(DataSnapshot chapter:chi.getChildren()){
                         Chapter c = chapter.getValue(Chapter.class);
                         chapters.add(c);
                     }
                    Manga mangas = comicSnapShot.getValue(Manga.class);
                     chapterModel = chapters;
                     mangas.Chapter = chapterModel;
                    mangaModel.add(mangas);
                }

                comicListener.onComicLoadDoneList(mangaModel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onComicLoadDoneList(List<Manga> comicList) {
        Common.comicList = comicList;


    }


}

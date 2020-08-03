package com.example.mangatangajava.ui.home;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mangatangajava.Interface.IComics;
import com.example.mangatangajava.common.Common;
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
    DatabaseReference comics;
    static IComics comicListener;


    public static RepoComic getInstance(IComics comics){
        if(instance==null){
            instance = new RepoComic();
        }
        comicListener = comics;
        return instance;
    }
    public MutableLiveData<ArrayList<Manga>> getManga(Context context){
        loadComic(context);
        MutableLiveData<ArrayList<Manga>> manga = new MutableLiveData<>();
        manga.setValue(mangaModel);
        return manga;
    }
    private void loadComic(Context context) {
        comics = FirebaseDatabase.getInstance().getReference("Comic");
        comics.keepSynced(true);

        comics.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot comicSnapShot : dataSnapshot.getChildren()) {
                    Manga mangas = comicSnapShot.getValue(Manga.class);
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

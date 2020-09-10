package com.example.mangatangajava.ui.slideshow;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mangatangajava.Interface.IComics;
import com.example.mangatangajava.adapter.MyFavoriteAdapter;
import com.example.mangatangajava.common.Common;
import com.example.mangatangajava.model.Manga;
import com.example.mangatangajava.ui.home.RepoComic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RepoFav implements IComics {
    static RepoFav instance;
    private ArrayList<Manga> favModel = new ArrayList<>();
    DatabaseReference favoritos;
    static IComics comicListener;

    public static RepoFav getInstance(IComics comics){
        if(instance==null){
            instance = new RepoFav();
        }
        comicListener = comics;
        return instance;
    }
    //almacenado favoritos
    public MutableLiveData<ArrayList<Manga>> getFav(Context context){
        loadFav(context);
        MutableLiveData<ArrayList<Manga>> fav = new MutableLiveData<>();
        fav.setValue(favModel);
        return fav;
    }
//lectura favoritos
    private void loadFav(final Context context) {
        favoritos = FirebaseDatabase.getInstance().getReference("Favoritos");
        favoritos.keepSynced(true);
        favoritos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot comicSnapShot : dataSnapshot.getChildren())  {
                    Manga mangas = comicSnapShot.getValue(Manga.class);
                    favModel.add(mangas);
                }

                comicListener.onComicLoadDoneList(favModel);

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

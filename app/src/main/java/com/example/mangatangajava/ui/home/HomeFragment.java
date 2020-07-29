package com.example.mangatangajava.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mangatangajava.Interface.IBanners;
import com.example.mangatangajava.Interface.IComics;
import com.example.mangatangajava.R;
import com.example.mangatangajava.adapter.MyComicAdapter;
import com.example.mangatangajava.adapter.MySliderAdapter;


import com.example.mangatangajava.common.Common;
import com.example.mangatangajava.model.Manga;
import com.example.mangatangajava.service.PicassoLoadingService;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import ss.com.bannerslider.Slider;

public class HomeFragment extends Fragment implements IBanners, IComics {

    private HomeViewModelJava homeViewModel;
    private MySliderAdapter mySliderAdapter;
    Slider slider;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recycler_comic;
    TextView txt_comic;


    //database

    DatabaseReference comics;

    //Listener
    //IBanners bannerListener;
    IComics comicListener;
    android.app.AlertDialog alertDialog;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModelJava.class);
        homeViewModel.init(this);


        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Init Database
/*
        banners = FirebaseDatabase.getInstance().getReference("Banners");
        banners.keepSynced(true);

 */
        comics = FirebaseDatabase.getInstance().getReference("Comic");
        comics.keepSynced(true);



        //Init Listener
               //bannerListener = this;
               comicListener = this;

        slider = (Slider)root.findViewById(R.id.slider);
        Slider.init(new PicassoLoadingService());

        swipeRefreshLayout = (SwipeRefreshLayout)root.findViewById(R.id.swipe_to_refresh);


        recycler_comic = (RecyclerView)root.findViewById(R.id.recycler_comic);
        recycler_comic.setHasFixedSize(true);
        recycler_comic.setLayoutManager(new GridLayoutManager( requireContext(),2));

        txt_comic = (TextView)root.findViewById(R.id.txt_comic);
/*
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBanner();
                loadComic();
            }
        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
              loadBanner();
              loadComic();
            }
        });

 */


        return root;
    }

    private void loadComic() {

        //show Dialog
         alertDialog = new SpotsDialog.Builder().setContext(getContext())
                .setCancelable(false)
                .setMessage("porfavor espere...")
                .build();
        if(!swipeRefreshLayout.isRefreshing())
        alertDialog.show();
        comics.addListenerForSingleValueEvent(new ValueEventListener() {
            List<Manga> manga_load = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot comicSnapShot:dataSnapshot.getChildren()){
                    Manga manga = comicSnapShot.getValue(Manga.class);
                    manga_load.add(manga);
                }

                comicListener.onComicLoadDoneList(manga_load);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
/*
    public void loadBanner() {
banners.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        List<String> bannerList = new ArrayList<>();
        for(DataSnapshot bannerSnapShot: dataSnapshot.getChildren()){
            String image = bannerSnapShot.getValue(String.class);
            bannerList.add(image);
        }
        //Call listener
        bannerListener.onBannerLoadDoneList(bannerList);

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Toast.makeText(getContext(), ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
    }
});
    }

    @Override
    public void onBannerLoadDoneList(List<String> banners) {
        slider.setAdapter(new MySliderAdapter(banners));

    }

 */

    @Override
    public void onComicLoadDoneList(List<Manga> comicList) {
        Common.comicList = comicList;

        recycler_comic.setAdapter(new MyComicAdapter(getContext(),comicList));

        txt_comic.setText(new StringBuilder("NEW COMIC (")
                .append(comicList.size())
                .append(")"));
        if(!swipeRefreshLayout.isRefreshing())
            alertDialog.dismiss();


    }

    @Override
    public void onBannerLoadDoneList(List<String> banners) {
        homeViewModel.getBAnner().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {



            }
        });
    }
}
package com.example.mangatangajava.ui.home;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mangatangajava.Interface.IBanners;
import com.example.mangatangajava.Interface.IComics;
import com.example.mangatangajava.adapter.MyComicAdapter;
import com.example.mangatangajava.adapter.MySliderAdapter;
import com.example.mangatangajava.common.Common;
import com.example.mangatangajava.model.Manga;
import com.example.mangatangajava.service.PicassoLoadingService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import ss.com.bannerslider.Slider;

public class Repo implements IBanners {

    static Repo instance;
   private ArrayList<String> bannerModel = new ArrayList<>();
    DatabaseReference banners;
   static IBanners bannerListener;
    Slider slider;

   public static Repo getInstance(IBanners ibanez){

       if(instance==null){
           instance = new Repo();
       }
       bannerListener =  ibanez;
       return instance;
   }
   //almacenado
   public MutableLiveData<ArrayList<String>> getBanner(){

       loadBanner();

       MutableLiveData<ArrayList<String>> banner = new MutableLiveData<>();
       banner.setValue(bannerModel);

       return banner;
   }
//lectura del slider
    private void loadBanner() {
        banners = FirebaseDatabase.getInstance().getReference("Banners");
        banners.keepSynced(true);
        banners.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // List<String> bannerList = new ArrayList<>();
                for(DataSnapshot bannerSnapShot: dataSnapshot.getChildren()){
                    String image = bannerSnapShot.getValue(String.class);
                    bannerModel.add(image);

            }
                bannerListener.onBannerLoadDoneList(bannerModel);

        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               // Toast.makeText(, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }

            });
    }


    @Override
    public void onBannerLoadDoneList(List<String> banners) {
        slider.setAdapter(new MySliderAdapter(banners));

    }


}

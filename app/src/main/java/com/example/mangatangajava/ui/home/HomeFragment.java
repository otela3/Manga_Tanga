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
    private MySliderAdapter mySliderAdapter;
    private MyComicAdapter myComicAdapter;
    private HomeViewModelJava homeViewModel = new HomeViewModelJava();
    Slider slider;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recycler_comic;
    TextView txt_comic;
    IComics comics;
    Context context;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModelJava.class);
        homeViewModel.init(this);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        slider = (Slider)root.findViewById(R.id.slider);
        Slider.init(new PicassoLoadingService());

        swipeRefreshLayout = (SwipeRefreshLayout)root.findViewById(R.id.swipe_to_refresh);

        recycler_comic = (RecyclerView)root.findViewById(R.id.recycler_comic);
        recycler_comic.setHasFixedSize(true);
        recycler_comic.setLayoutManager(new GridLayoutManager( requireContext(),2));

        txt_comic = (TextView)root.findViewById(R.id.txt_comic);

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModelJava.class);
        homeViewModel.init(this);

        comics = new HomeFragment();

        homeViewModel.init2(this, context);

        mySliderAdapter = new MySliderAdapter(homeViewModel.getBanner().getValue());
        myComicAdapter = new MyComicAdapter(context,homeViewModel.getManga().getValue());

        return root;
    }

    @Override
    public void onComicLoadDoneList(final List<Manga> comicList) {
       // homeViewModel.init2(this, context);
        homeViewModel.getManga().observe(getViewLifecycleOwner(), new Observer<ArrayList<Manga>>() {
            @Override
            public void onChanged(ArrayList<Manga> mangas) {

                Common.comicList = comicList;

                recycler_comic.setAdapter(new MyComicAdapter(getContext(),comicList));

                txt_comic.setText(new StringBuilder("NEW COMIC (")
                        .append(comicList.size())
                        .append(")"));

            }

        });

    }

    @Override
    public void onBannerLoadDoneList (List < String > banners) {

        homeViewModel.getBanner().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                slider.setAdapter(new MySliderAdapter(strings));
            }
        });
     }
}



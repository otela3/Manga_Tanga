package com.example.mangatangajava.ui.slideshow;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangatangajava.Interface.IComics;
import com.example.mangatangajava.R;
import com.example.mangatangajava.adapter.MyComicAdapter;
import com.example.mangatangajava.adapter.MyFavoriteAdapter;
import com.example.mangatangajava.common.Common;
import com.example.mangatangajava.model.Manga;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class SlideshowFragment extends Fragment implements IComics {

    private SlideshowViewModel slideshowViewModel;
    RecyclerView recycler_fav;
    TextView txt_comic;
    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        slideshowViewModel.init4(this,context);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        recycler_fav = (RecyclerView)root.findViewById(R.id.Recycler_fav);
        recycler_fav.setHasFixedSize(true);
        recycler_fav.setLayoutManager(new GridLayoutManager( requireContext(),2));

        txt_comic = (TextView)root.findViewById(R.id.txt_comic);

        return root;
    }


    @Override
    public void onComicLoadDoneList(final List<Manga> comicList) {
        slideshowViewModel.getFav().observe(getViewLifecycleOwner(), new Observer<ArrayList<Manga>>() {
            @Override
            public void onChanged(ArrayList<Manga> mangas) {

                Common.comicList = comicList;
                recycler_fav.setAdapter(new MyFavoriteAdapter(getContext(),comicList));
            }
        });

    }
}
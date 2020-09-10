package com.example.mangatangajava.ui.chapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.mangatangajava.Interface.IComics;
import com.example.mangatangajava.R;
import com.example.mangatangajava.adapter.MyChapterAdapter;
import com.example.mangatangajava.adapter.MyComicAdapter;
import com.example.mangatangajava.common.Common;
import com.example.mangatangajava.model.Chapter;
import com.example.mangatangajava.model.Manga;
import com.example.mangatangajava.ui.home.HomeViewModelJava;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class ChapterActivity extends AppCompatActivity implements IComics{
    private ChapterViewModel chapterViewModel = new ChapterViewModel();
    RecyclerView recycler_chapter;
    TextView txt_chapter_name;
    LinearLayoutManager LayoutManager;
    Context context;
    private FirebaseFirestore dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        chapterViewModel = ViewModelProviders.of(this).get(ChapterViewModel.class);
        chapterViewModel.init3((IComics) this, context);

        txt_chapter_name = (TextView)findViewById(R.id.txt_chapter_name);
        recycler_chapter = (RecyclerView)findViewById(R.id.recycler_chapter);
        recycler_chapter.setHasFixedSize(true);
        LayoutManager = new LinearLayoutManager(this);
        recycler_chapter.setLayoutManager(LayoutManager);
        recycler_chapter.addItemDecoration(new DividerItemDecoration(this,LayoutManager.getOrientation()));
        dataBase = FirebaseFirestore.getInstance();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(Common.comicSelected.Name);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_chevron_left_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button Follow = findViewById(R.id.Follow);
        Follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

    }
//metodo para guardar manga favorito
    private void save() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Favoritos");
        DatabaseReference comicRef = ref.child("Favoritos");
        comicRef.setValue(Common.comicSelected);



    }

//metodo carga de capitulos
    private void fetchChapter(@NotNull final Manga comicSelected) {

        Common.chapterList = comicSelected.Chapter;
        recycler_chapter.setAdapter(new MyChapterAdapter(this,comicSelected.Chapter));
        txt_chapter_name.setText(new StringBuilder("CHAPTERS (").append(comicSelected.Chapter.size())
                .append(")"));

    }



    @Override
//Observer
    public void onComicLoadDoneList(List<Manga> comicList) {
        chapterViewModel.getChapter().observe(this, new Observer<ArrayList<Chapter>>() {
            @Override
            public void onChanged(ArrayList<Chapter> chapters) {
                fetchChapter(Common.comicSelected);



            }
        });
    }
}
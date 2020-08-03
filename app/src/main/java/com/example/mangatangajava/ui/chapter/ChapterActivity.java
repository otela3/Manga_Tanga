package com.example.mangatangajava.ui.chapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.mangatangajava.R;
import com.example.mangatangajava.adapter.MyChapterAdapter;
import com.example.mangatangajava.adapter.MyComicAdapter;
import com.example.mangatangajava.common.Common;
import com.example.mangatangajava.model.Manga;

import org.jetbrains.annotations.NotNull;


public class ChapterActivity extends AppCompatActivity {
    RecyclerView recycler_chapter;
    TextView txt_chapter_name;
    LinearLayoutManager LayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        txt_chapter_name = (TextView)findViewById(R.id.txt_chapter_name);
        recycler_chapter = (RecyclerView)findViewById(R.id.recycler_chapter);
        recycler_chapter.setHasFixedSize(true);
        LayoutManager = new LinearLayoutManager(this);
        recycler_chapter.setLayoutManager(LayoutManager);
        recycler_chapter.addItemDecoration(new DividerItemDecoration(this,LayoutManager.getOrientation()));

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(Common.comicSelected.Name);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_chevron_left_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        

        fetchChapter(Common.comicSelected);
    }

    private void fetchChapter(@NotNull Manga comicSelected) {

        Common.chapterList = comicSelected.Chapter;
        recycler_chapter.setAdapter(new MyChapterAdapter(this,comicSelected.Chapter));
        txt_chapter_name.setText(new StringBuilder("CHAPTERS (").append(comicSelected.Chapter.size())
                .append(")"));
    }
}
package com.example.mangatangajava.ui.chapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangatangajava.Interface.IComics;
import com.example.mangatangajava.R;
import com.example.mangatangajava.adapter.MyLinksAdapter;
import com.example.mangatangajava.common.Common;
import com.example.mangatangajava.model.Chapter;
import com.example.mangatangajava.model.Manga;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class LinkActivity extends AppCompatActivity implements IComics {

    private ChapterViewModel chapterViewModel = new ChapterViewModel();
    ViewPager viewPager;
    TextView txt_chapter_name;
    View back,next;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        chapterViewModel = ViewModelProviders.of(this).get(ChapterViewModel.class);
        chapterViewModel.init3((IComics) this, context);

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        txt_chapter_name = (TextView)findViewById(R.id.txt_chapter_name);
        back = findViewById(R.id.chapter_back);
        next = findViewById(R.id.chapter_next);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Common.chapterIndex==0){
                    Toast.makeText(LinkActivity.this,"tas en el primer cap", Toast.LENGTH_SHORT).show();
                }
                else{
                    Common.chapterIndex--;
                    fetchLinks(Common.chapterList.get(Common.chapterIndex));
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Common.chapterIndex== Common.chapterList.size()-1){
                    Toast.makeText(LinkActivity.this,"tas en el ultimo cap", Toast.LENGTH_SHORT).show();
                }
                else{
                    Common.chapterIndex++;
                    fetchLinks(Common.chapterList.get(Common.chapterIndex));
                }
            }
        });


    }
//carga de capitulos pero las imagenes
    private void fetchLinks(Chapter chapter) {
        if(chapter.Links != null){
            if (chapter.Links.size()>0){
                MyLinksAdapter adapter = new MyLinksAdapter(getBaseContext(),chapter.Links);
                viewPager.setAdapter(adapter);

                txt_chapter_name.setText(Common.formatString(Common.chapterSelected.Name));

                BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();
                bookFlipPageTransformer.setScaleAmountPercent(10f);
                viewPager.setPageTransformer(true,bookFlipPageTransformer);
            }
            else {
                Toast.makeText(this, "el cap que sigue pa", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "no hay cap bro sorry:C", Toast.LENGTH_SHORT).show();
        }

    }

//observer
    @Override
    public void onComicLoadDoneList(List<Manga> comicList) {
        chapterViewModel.getChapter().observe(this, new Observer<ArrayList<Chapter>>() {
            @Override
            public void onChanged(ArrayList<Chapter> chapters) {
                fetchLinks(Common.chapterSelected);
            }
        });

    }
}
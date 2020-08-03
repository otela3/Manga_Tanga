package com.example.mangatangajava.Interface;

import com.example.mangatangajava.model.Manga;

import java.util.List;

public interface IComics {
    void onComicLoadDoneList(List<Manga> comicList);
    //void onChapterDoneLoad(List<Manga> chapterList);
}

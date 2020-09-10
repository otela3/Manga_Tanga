package com.example.mangatangajava.common;

import com.example.mangatangajava.model.Chapter;
import com.example.mangatangajava.model.Manga;

import java.util.ArrayList;
import java.util.List;

public class Common {
    public static List<Manga> comicList = new ArrayList<>();
    public static Manga comicSelected;
    public static List<Chapter> chapterList = new ArrayList<>();
    public static Chapter chapterSelected;
    public static int chapterIndex;

    public static String formatString(String name) {
        StringBuilder finalResult = new StringBuilder(name.length()> 15?name.substring(0,15)+"...":name);
        return finalResult.toString();
    }
}

package com.example.mangatangajava.model;

import java.util.ArrayList;
import java.util.List;

public class Manga {
    public String Name;
    public String Image;
    public String Category;
    public List<Chapter> Chapter = new ArrayList<>();
//filtro por categoria en construccion

    public Manga() {
    }
}

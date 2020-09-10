package com.example.mangatangajava.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mangatangajava.R;
import com.example.mangatangajava.model.Manga;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyFavoriteAdapter extends RecyclerView.Adapter<MyFavoriteAdapter.MyViewHolder> {
    Context context;
    List<Manga> comicList;
    LayoutInflater inflater;

    public MyFavoriteAdapter(Context context, List<Manga> comicList) {
        this.context = context;
        this.comicList = comicList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.slideshow_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(comicList.get(position).Image).into(holder.image_comic);
        holder.comic_name.setText(comicList.get(position).Name);

    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView comic_name;
        ImageView image_comic;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image_comic = (ImageView)itemView.findViewById(R.id.image_comic);
            comic_name = (TextView)itemView.findViewById(R.id.comic_name);
        }
    }
}

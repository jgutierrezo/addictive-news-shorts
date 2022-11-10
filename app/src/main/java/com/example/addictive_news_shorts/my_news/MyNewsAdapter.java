package com.example.addictive_news_shorts.my_news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.addictive_news_shorts.R;
import com.example.addictive_news_shorts.models.NewsModel;

import java.util.ArrayList;
import java.util.List;

public class MyNewsAdapter extends RecyclerView.Adapter<MyNewsHolder>{

    Context context;
    List<NewsModel> myNews;
    private MyNewsListner listener;

    public MyNewsAdapter(Context context, MyNewsListner listener) {
        this.context = context;
        this.myNews = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyNewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyNewsHolder(LayoutInflater.from(context).inflate(R.layout.my_news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyNewsHolder holder, int position) {
        holder.title.setText(myNews.get(position).getTitle());
        Glide.with(context).load(myNews.get(position).getImage()).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onImageClicked(myNews.get(holder.getAdapterPosition()));
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onShareClicked(myNews.get(holder.getAdapterPosition()));
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteClicked(myNews.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return myNews.size();
    }

    public void setMyNews(List<NewsModel> myNews) {
        this.myNews = myNews;
        notifyDataSetChanged();
    }
}

package com.example.addictive_news_shorts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsHolder>{

    Context context;
    List<NewsModel> news;

    public NewsAdapter(Context context) {
        this.context = context;
        this.news = new ArrayList<>();
    }
    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsHolder(LayoutInflater.from(context).inflate(R.layout.news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        holder.title.setText(news.get(position).getTitle());
        holder.content.setText(news.get(position).getContent());
        Glide.with(context).load(news.get(position).getImage()).into(holder.image);
    }

    public void setNews(List<NewsModel> news) {
        this.news = news;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
}


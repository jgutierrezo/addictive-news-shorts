package com.example.addictive_news_shorts.news;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addictive_news_shorts.R;

public class NewsHolder extends RecyclerView.ViewHolder{

    TextView title;
    TextView content;
    ImageView image;

    public NewsHolder(@NonNull View itemView) {
        super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.news_content);
            image = itemView.findViewById(R.id.img);
        }
}

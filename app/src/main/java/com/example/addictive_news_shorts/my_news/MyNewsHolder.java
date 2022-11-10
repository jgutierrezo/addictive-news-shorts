package com.example.addictive_news_shorts.my_news;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addictive_news_shorts.R;

public class MyNewsHolder extends RecyclerView.ViewHolder{

    TextView title;
    ImageView image;
    AppCompatImageButton share;
    AppCompatImageButton delete;

    public MyNewsHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        image = itemView.findViewById(R.id.img);
        share = itemView.findViewById(R.id.share);
        delete = itemView.findViewById(R.id.delete);
    }
}

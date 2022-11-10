package com.example.addictive_news_shorts.my_news;

import com.example.addictive_news_shorts.models.NewsModel;

public interface MyNewsListner {
    void onImageClicked(NewsModel myNews);
    void onShareClicked(NewsModel myNews);
    void onDeleteClicked(NewsModel myNews);
}

package com.example.addictive_news_shorts.models;

import com.example.addictive_news_shorts.models.NewsModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("totalResults")
    private  int totalResults;
    @SerializedName("articles")
    private List<NewsModel> articles;

    public NewsResponse(String status, int totalResults, List<NewsModel> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<NewsModel> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsModel> articles) {
        this.articles = articles;
    }
}

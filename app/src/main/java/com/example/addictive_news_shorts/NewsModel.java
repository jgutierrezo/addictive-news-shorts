package com.example.addictive_news_shorts;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class NewsModel {
    @SerializedName("author")
    private String author;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("url")
    private String url;
    @SerializedName("urlToImage")
    private String image;
    @SerializedName("publishedAt")
    private String date;
    @SerializedName("content")
    private String content;

    public NewsModel(String author, String title, String description, String url, String image, String date, String content) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.image = image;
        this.date = date;
        this.content = content;
    }

    public Map<String, Object> toHashMap(String user) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("author", this.author);
        map.put("title",this.title);
        map.put("description",this.description = description);
        map.put("url",this.url = url);
        map.put("image",this.image = image);
        map.put("date",this.date = date);
        map.put("content",this.content = content);
        return map;

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

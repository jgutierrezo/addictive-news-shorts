package com.example.addictive_news_shorts;

public class NewsModel {
    private String author;
    private String title;
    private String description;
    private String url;
    private String image;
    private String date;
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

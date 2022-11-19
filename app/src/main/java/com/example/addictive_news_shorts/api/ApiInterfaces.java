package com.example.addictive_news_shorts.api;

import com.example.addictive_news_shorts.models.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaces {

    @GET("top-headlines")
    Call<NewsResponse> getNews(@Query("country") String country,
                               @Query("apiKey") String apiKey,
                               @Query("category") String category,
                               @Query("pageSize") int pageSize);
}

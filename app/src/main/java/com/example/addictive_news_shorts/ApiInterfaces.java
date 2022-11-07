package com.example.addictive_news_shorts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaces {

    @GET("top-headlines")
    Call<NewsResponse> getNews(@Query("country") String country,
                               @Query("apiKey") String apiKey,
                               @Query("pageSize") int pageSize);
}

package com.example.addictive_news_shorts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class newsActivity extends AppCompatActivity implements CardStackListener {

    private NewsAdapter adapter;
    private CardStackLayoutManager layoutManager;
    private CardStackView stackView;
    private String url;
    List<NewsModel> news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        stackView = findViewById(R.id.stack_view);
        adapter = new NewsAdapter(getApplicationContext());
        layoutManager = new CardStackLayoutManager(this, this);
        layoutManager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        layoutManager.setDirections(Direction.HORIZONTAL);
        layoutManager.setOverlayInterpolator(new LinearInterpolator());
        stackView.setLayoutManager(layoutManager);
        stackView.setAdapter(adapter);
        ApiInterfaces apiService = ApiClient.getClient().create(ApiInterfaces.class);
        Call<NewsResponse> call = apiService.getNews("ca", "72492bb78d284e6badef389a803220be", 100);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                news = response.body().getArticles();
                adapter.setNews(news);
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {
        if (direction == Direction.Right) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }

    }

    public void rewind(View view) {
        stackView.rewind();
    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {
        url = news.get(position).getUrl();
    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }
}
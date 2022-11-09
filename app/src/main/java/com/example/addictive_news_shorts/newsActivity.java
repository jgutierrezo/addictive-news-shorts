package com.example.addictive_news_shorts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
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
    private List<NewsModel> news;
    private int position = 0;

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
        layoutManager.setCanScrollVertical(false);
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
        this.position = position;
    }

    @Override
    public void onCardDisappeared(View view, int position) {
    }

    public void save(View view) {
        saveMyNews(news.get(position));
    }

    public void saveMyNews(NewsModel news) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("my-news").add(news.toHashMap("rodrigo")).addOnSuccessListener(
                new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"You saved the news", Toast.LENGTH_SHORT).show();
                    }
                }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error! Try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToMyNews(View view) {
        Intent intent = new Intent(this, MyNewsActivity.class);
        startActivity(intent);
    }
}
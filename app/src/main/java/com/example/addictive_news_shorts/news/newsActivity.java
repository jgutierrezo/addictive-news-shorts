package com.example.addictive_news_shorts.news;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.addictive_news_shorts.LoginActivity;
import com.example.addictive_news_shorts.SelectCategoryActivity;
import com.example.addictive_news_shorts.api.ApiClient;
import com.example.addictive_news_shorts.api.ApiInterfaces;
import com.example.addictive_news_shorts.models.NewsModel;
import com.example.addictive_news_shorts.models.NewsResponse;
import com.example.addictive_news_shorts.R;
import com.example.addictive_news_shorts.my_news.MyNewsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

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
    private static final int SELECT_CATEGORY = 1000;
    private static final int LOGIN = 2000;
    private HashSet<String> savedNews = new HashSet<String>();
    private String username;
    ImageButton saveButton;
    ImageButton myNews;
    ImageButton login;
    ImageButton logout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        stackView = findViewById(R.id.stack_view);
        saveButton = findViewById(R.id.save);
        myNews = findViewById(R.id.my_news);
        login = findViewById(R.id.login);
        logout = findViewById(R.id.logout);

        adapter = new NewsAdapter(getApplicationContext());
        layoutManager = new CardStackLayoutManager(this, this);
        layoutManager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        layoutManager.setDirections(Direction.HORIZONTAL);
        layoutManager.setOverlayInterpolator(new LinearInterpolator());
        layoutManager.setCanScrollVertical(false);
        stackView.setLayoutManager(layoutManager);
        stackView.setAdapter(adapter);
        getNews("business");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            username = currentUser.getEmail();
        }
        if (username != null) {
            getMyNews();
        } else {
            logout.setVisibility(View.INVISIBLE);
            login.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            username = currentUser.getEmail();
        }
        if (username != null) {
            getMyNews();
        } else {
            logout.setVisibility(View.INVISIBLE);
            login.setVisibility(View.VISIBLE);
        }
    }

    public void getNews(String category) {
        ApiInterfaces apiService = ApiClient.getClient().create(ApiInterfaces.class);
        Call<NewsResponse> call = apiService.getNews("ca", "72492bb78d284e6badef389a803220be", category, 100);
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
        updateButtonsVisibility();
    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {
        url = news.get(position).getUrl();
        this.position = position;
        updateButtonsVisibility();
    }

    @Override
    public void onCardDisappeared(View view, int position) {
    }

    public void save(View view) {
        if (username == null) {
            goToLogin(view);
            return;
        }
        if (!savedNews.contains(url)) {
            saveMyNews(news.get(position));
        } else {
            Toast.makeText(getApplicationContext(),"Already saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveMyNews(NewsModel news) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("my-news").add(news.toHashMap(username)).addOnSuccessListener(
                new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"You saved the news", Toast.LENGTH_SHORT).show();
                        savedNews.add(news.getUrl());
                        updateButtonsVisibility();
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
        if (username != null) {
            Intent intent = new Intent(this, MyNewsActivity.class);
            startActivity(intent);
        } else {
            goToLogin(view);
        }
    }

    public void logout(View view) {
        username = null;
        mAuth.signOut();
        savedNews = new HashSet<String>();
        updateButtonsVisibility();
    }

    public void selectCategory(View view) {
        Intent intent = new Intent(this, SelectCategoryActivity.class);
        startActivityForResult(intent, SELECT_CATEGORY);
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_CATEGORY) {
            if (resultCode == RESULT_OK) {
                String category = data.getStringExtra("category");
                getNews(category);
            }
        }
        if (requestCode == LOGIN) {
            if (resultCode == RESULT_OK) {
                username = data.getStringExtra("username");
                FirebaseUser currentUser = mAuth.getCurrentUser();
                username = currentUser.getEmail();
                getMyNews();
            } else {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    username = currentUser.getEmail();
                    getMyNews();
                }
            }
        }
    }

    public void getMyNews() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("my-news")
                .whereEqualTo("user", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            savedNews = new HashSet<String>();
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                savedNews.add((String) document.getData().get("url"));
                            }
                            updateButtonsVisibility();
                        }
                    }
                });

    }

    public void updateButtonsVisibility() {
        if (username != null) {
            login.setVisibility(View.INVISIBLE);
            logout.setVisibility(View.VISIBLE);
        } else {
            logout.setVisibility(View.INVISIBLE);
            login.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (username != null) {
            getMyNews();
        }
    }
}
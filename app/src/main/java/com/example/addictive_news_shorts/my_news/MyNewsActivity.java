package com.example.addictive_news_shorts.my_news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.addictive_news_shorts.R;
import com.example.addictive_news_shorts.models.NewsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyNewsActivity extends AppCompatActivity {

    MyNewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_news);
        RecyclerView recyclerView = findViewById(R.id.my_news_rc);
        adapter = new MyNewsAdapter(getApplicationContext(), new MyNewsListner() {
            @Override
            public void onImageClicked(NewsModel myNews) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(myNews.getUrl()));
                startActivity(i);
            }

            @Override
            public void onShareClicked(NewsModel myNews) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
                i.putExtra(Intent.EXTRA_TEXT, myNews.getUrl());
                startActivity(Intent.createChooser(i, "Share URL"));
            }

            @Override
            public void onDeleteClicked(NewsModel myNews) {
                deleteMyNews(myNews.getFirebaseId());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        getMyNews();
    }

    public void deleteMyNews(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("my-news")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Delete it!", Toast.LENGTH_SHORT).show();
                        getMyNews();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Error! Try again", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void getMyNews() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("my-news")
                .whereEqualTo("user", "rodrigo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<NewsModel> myNews = new ArrayList<>();
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                myNews.add(new NewsModel(document.getData(), document.getId()));
                            }
                            adapter.setMyNews(myNews);
                        }
                    }
                });

    }
}
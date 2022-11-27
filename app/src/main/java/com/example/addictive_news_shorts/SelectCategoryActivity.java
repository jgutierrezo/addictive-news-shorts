package com.example.addictive_news_shorts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class SelectCategoryActivity extends AppCompatActivity {

    String category = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void goToNews(View view) {
        switch (view.getId()){
            case R.id.business:  category = "Business"; break;
            case R.id.entertainment:  category = "Entertainment"; break;
            case R.id.general:  category = "General"; break;
            case R.id.health: category = "health"; break;
            case R.id.science: category = "Science"; break;
            case R.id.sports:  category = "Sports"; break;
            case R.id.technology:  category = "Technology"; break;
        }
        selectCategory();
    }

    public void selectCategory() {
        Intent replyIntent = new Intent();
        replyIntent.putExtra("category", category);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}
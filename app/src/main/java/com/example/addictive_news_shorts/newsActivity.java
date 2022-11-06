package com.example.addictive_news_shorts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class newsActivity extends AppCompatActivity implements CardStackListener {

    private NewsAdapter adapter;
    private CardStackLayoutManager layoutManager;
    private CardStackView stackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        stackView = findViewById(R.id.stack_view);
        List<NewsModel> news = new ArrayList<>();
        news.add(new NewsModel(
                "NHL.com",
                "Tkachuk",
                "Matthew Tkachuk will have a hearing with the NHL Department of Player Safety on Sunday.",
                "https://www.nhl.com/news/florida-panthers-matthew-tkachuk-to-have-player-safety-hearing-for-high-sticking/c-337289068",
                "https://cms.nhl.bamgrid.com/images/photos/337289070/1024x576/cut.jpg",
                "2022-11-06T19:01:05Z",
                "some content"));
        news.add(new NewsModel(
                "CBC News",
                "The prime",
                "Ahead of meetings between provincial, territorial and federal health ministers in Vancouver this week, B.C. Health Minister Adrian Dix says the prime minister has not been willing to meet on augmented health transfers.",
                "https://www.cbc.ca/news/politics/dix-health-ministers-meeting-funding-trudeau-1.6642405",
                "https://i.cbc.ca/1.6304273.1641339293!/cumulusImage/httpImage/image.jpg_gen/derivatives/16x9_620/henry-dix-update.jpg",
                "2022-11-06T19:01:05Z",
                "Even as health ministers from across the country prepare to meet in Vancouver this week, B.C. Health Minister Adrian Dix says the federal government is not interested in a serious conversation about … [+3249 chars]"));

        adapter = new NewsAdapter(getApplicationContext(), news);
        layoutManager = new CardStackLayoutManager(this, this);
        layoutManager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        layoutManager.setDirections(Direction.FREEDOM);
        layoutManager.setOverlayInterpolator(new LinearInterpolator());
        stackView.setLayoutManager(layoutManager);
        stackView.setAdapter(adapter);
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {

    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }
}
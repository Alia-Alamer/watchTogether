package com.example.watchtogether;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderboardActivity extends AppCompatActivity {

    private RecyclerView rvLeaderboard;
    private Button btnRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leaderboard);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.leaderboardRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rvLeaderboard = findViewById(R.id.rvLeaderboard);
        btnRestart = findViewById(R.id.btnRestart);

        Intent intent = getIntent();

        ArrayList<String> movies = intent.getStringArrayListExtra(SwipeActivity.EXTRA_MOVIES);
        if (movies == null) movies = new ArrayList<>();

        int[] likes = intent.getIntArrayExtra(SwipeActivity.EXTRA_LIKES);
        if (likes == null) likes = new int[movies.size()];

        ArrayList<LeaderboardAdapter.Row> rows = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            String title = movies.get(i);
            int likeCount = (i < likes.length) ? likes[i] : 0;
            rows.add(new LeaderboardAdapter.Row(title, likeCount));
        }

        Collections.sort(rows, (a, b) -> Integer.compare(b.likes, a.likes));

        rvLeaderboard.setLayoutManager(new LinearLayoutManager(this));
        rvLeaderboard.setAdapter(new LeaderboardAdapter(rows));

        btnRestart.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });
    }
}

package com.example.watchtogether;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class SwipeActivity extends AppCompatActivity {

    public static final String EXTRA_PLAYER_COUNT = "extra_player_count";
    public static final String EXTRA_MOVIES = "extra_movies";
    public static final String EXTRA_LIKES = "extra_likes";

    private TextView tvProgress;
    private TextView tvTitle;
    private ImageView ivPoster;
    private Button btnNope;
    private Button btnLike;

    private int playerCount = 2;
    private ArrayList<String> movies = new ArrayList<>();
    private int[] likes;

    private int currentPlayerIndex = 0;
    private int currentMovieIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_swipe);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.swipeRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvProgress = findViewById(R.id.tvProgress);
        tvTitle = findViewById(R.id.tvTitle);
        ivPoster = findViewById(R.id.ivPoster);
        btnNope = findViewById(R.id.btnNope);
        btnLike = findViewById(R.id.btnLike);

        Intent intent = getIntent();
        playerCount = intent.getIntExtra(EXTRA_PLAYER_COUNT, 2);

        ArrayList<String> incomingMovies = intent.getStringArrayListExtra(EXTRA_MOVIES);
        if (incomingMovies != null) movies = incomingMovies;

        int[] likesFromIntent = intent.getIntArrayExtra(EXTRA_LIKES);
        likes = (likesFromIntent != null) ? likesFromIntent : new int[movies.size()];

        if (movies.isEmpty()) {
            goToLeaderboard();
            return;
        }

        render();

        btnNope.setOnClickListener(v -> onSwipe(false));
        btnLike.setOnClickListener(v -> onSwipe(true));
    }

    private void onSwipe(boolean liked) {
        if (liked) {
            likes[currentMovieIndex] = likes[currentMovieIndex] + 1;
        }

        if (currentMovieIndex < movies.size() - 1) {
            currentMovieIndex++;
            render();
            return;
        }

        if (currentPlayerIndex < playerCount - 1) {
            currentPlayerIndex++;
            currentMovieIndex = 0;
            render();
            return;
        }

        goToLeaderboard();
    }

    private void render() {
        String title = movies.get(currentMovieIndex);

        tvProgress.setText(
                "Player " + (currentPlayerIndex + 1) + "/" + playerCount +
                        " • Movie " + (currentMovieIndex + 1) + "/" + movies.size()
        );
        tvTitle.setText(title);

        ivPoster.setImageResource(android.R.color.darker_gray);
    }

    private void goToLeaderboard() {
        Intent i = new Intent(this, LeaderboardActivity.class);
        i.putExtra(EXTRA_PLAYER_COUNT, playerCount);
        i.putStringArrayListExtra(EXTRA_MOVIES, movies);
        i.putExtra(EXTRA_LIKES, likes);
        startActivity(i);
        finish();
    }
}

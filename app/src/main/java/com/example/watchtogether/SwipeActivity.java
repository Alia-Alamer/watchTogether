package com.example.watchtogether;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class SwipeActivity extends AppCompatActivity {

    public static final String EXTRA_PLAYER_COUNT = "extra_player_count";
    public static final String EXTRA_MOVIES = "extra_movies";
    public static final String EXTRA_LIKES = "extra_likes";

    private TextView tvProgress;
    private TextView tvTitleOverlay;
    private ImageView ivPoster;
    private ImageButton btnNope;
    private ImageButton btnLike;
    private View posterFrame;

    private int playerCount = 2;
    private ArrayList<String> movies = new ArrayList<>();
    private int[] likes;

    private int currentPlayerIndex = 0;
    private int currentMovieIndex = 0;

    private GestureDetectorCompat gestureDetector;

    private static final int SWIPE_THRESHOLD_DISTANCE = 100;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

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
        tvTitleOverlay = findViewById(R.id.titleOverlay);
        ivPoster = findViewById(R.id.ivPoster);
        btnNope = findViewById(R.id.btnNope);
        btnLike = findViewById(R.id.btnLike);
        posterFrame = findViewById(R.id.posterFrame);

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

        gestureDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true; // MUSS true sein
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1 == null || e2 == null) return false;

                float diffX = e2.getX() - e1.getX();
                float diffY = e2.getY() - e1.getY();

                if (Math.abs(diffX) > Math.abs(diffY)
                        && Math.abs(diffX) > SWIPE_THRESHOLD_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    if (diffX > 0) onSwipe(true);   // rechts = Like
                    else onSwipe(false);            // links = Nope
                    return true;
                }
                return false;
            }
        });

        View.OnTouchListener swipeTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        };

        posterFrame.setClickable(true);
        posterFrame.setFocusable(true);
        posterFrame.setOnTouchListener(swipeTouchListener);

        ivPoster.setOnTouchListener(swipeTouchListener);

        btnNope.setOnClickListener(v -> onSwipe(false));
        btnLike.setOnClickListener(v -> onSwipe(true));

        render();
    }

    private void onSwipe(boolean liked) {
        if (liked) likes[currentMovieIndex]++;

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

        tvProgress.setText("Spieler " + (currentPlayerIndex + 1) + " von " + playerCount);
        tvTitleOverlay.setText(title);

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
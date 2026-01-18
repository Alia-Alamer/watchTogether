package com.example.watchtogether;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class AddMoviesActivity extends AppCompatActivity {

    private int personCount = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_movies);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.addMovies), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        personCount = getIntent().getIntExtra(MainActivity.EXTRA_PERSON_COUNT, 2);

        EditText movieTitleInput = findViewById(R.id.movieTitleInput);
        TextView addMovieBtn = findViewById(R.id.addMovieButton);
        TextView movieListDisplay = findViewById(R.id.movieListDisplay);
        Button startSwipingBtn = findViewById(R.id.startSwipingButton);

        ArrayList<String> movieList = new ArrayList<>();

        addMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieTitle = movieTitleInput.getText().toString().trim();

                if (movieTitle.isEmpty()) {
                    movieTitleInput.setError("Please enter a movie Title");
                    return;
                }

                movieList.add(movieTitle);
                movieTitleInput.setText("");
                updateMovieList(movieList, movieListDisplay);
            }
        });

        startSwipingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movieList.size() < 2) {
                    movieTitleInput.setError("Please add at least 2 movies");
                    return;
                }

                Intent intent = new Intent(AddMoviesActivity.this, SwipeActivity.class);
                intent.putExtra(SwipeActivity.EXTRA_PLAYER_COUNT, personCount);
                intent.putStringArrayListExtra(SwipeActivity.EXTRA_MOVIES, movieList);
                startActivity(intent);
            }
        });

        updateMovieList(movieList, movieListDisplay);
    }

    public void updateMovieList(ArrayList<String> movieList, TextView display) {
        if (movieList.isEmpty()) {
            display.setText("No movies added yet");
        } else {
            String text = "Movies (" + movieList.size() + "):\n";
            for (String movie : movieList) {
                text = text + "- " + movie + "\n";
            }
            display.setText(text);
        }
    }
}
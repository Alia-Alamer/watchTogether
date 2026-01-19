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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddMoviesActivity extends AppCompatActivity {

    private int personCount = 2;
    private ArrayList<String> movieList;
    private MovieListAdapter adapter;

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
        Button startSwipingBtn = findViewById(R.id.startSwipingButton);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);

        movieList = new ArrayList<>();

        adapter = new MovieListAdapter(movieList, position -> {
            if (position >= 0 && position < movieList.size()) {
                movieList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, movieList.size());
            }
        });

        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);

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

                adapter.notifyItemInserted(movieList.size() - 1);
                rvMovies.scrollToPosition(movieList.size() - 1);
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
    }
}
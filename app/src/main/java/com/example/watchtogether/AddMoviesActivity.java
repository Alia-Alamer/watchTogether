package com.example.watchtogether;

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

        EditText movieTitleInput = findViewById(R.id.movieTitleInput);
        TextView addMovieBtn = findViewById(R.id.addMovieButton);
        Button startSwipingBtn = findViewById(R.id.startSwipingButton);

        ArrayList<String> movieList = new ArrayList<>();

        addMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieTitle = movieTitleInput.getText().toString();

                if(movieTitle.isEmpty()){
                    movieTitleInput.setError("Please enter a movie Title");
                    return;
                }

                movieList.add(movieTitle);
                movieTitleInput.setText("");
            }
        });


        startSwipingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movieList.size() < 2){
                    movieTitleInput.setError("Please add at least 2 movies");
                    return;
                }

                //später > zur swipe activity wechseln
            }
        });


    }
}
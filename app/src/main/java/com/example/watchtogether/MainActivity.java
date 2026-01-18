package com.example.watchtogether;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_PERSON_COUNT = "extra_person_count";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText numberOfPersons = findViewById(R.id.numberOfPersons);
        Button continueBtn = findViewById(R.id.continueBtn);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input = numberOfPersons.getText().toString().trim();
                if (input.isEmpty()) {
                    numberOfPersons.setError("Please enter number of People");
                    return;
                }

                int number;
                try {
                    number = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    numberOfPersons.setError("Please enter a valid number");
                    return;
                }

                if (number < 1) {
                    numberOfPersons.setError("Must be at least 1 person");
                    return;
                }

                Intent intent = new Intent(MainActivity.this, AddMoviesActivity.class);
                intent.putExtra(EXTRA_PERSON_COUNT, number);
                startActivity(intent);
            }
        });
    }
}

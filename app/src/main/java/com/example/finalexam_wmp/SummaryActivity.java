package com.example.finalexam_wmp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {
    private TextView textViewSummary, textViewTotalCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        textViewSummary = findViewById(R.id.textViewSummary);
        textViewTotalCredits = findViewById(R.id.textViewTotalCredits);

        String selectedSubjects = getIntent().getStringExtra("selectedSubjects");
        int totalCredits = getIntent().getIntExtra("totalCredits", 0);

        if (selectedSubjects == null || selectedSubjects.isEmpty()) {
            selectedSubjects = "No subjects selected.";
        }

        textViewSummary.setText(selectedSubjects);
        textViewTotalCredits.setText("Total Credits: " + totalCredits);
    }
}
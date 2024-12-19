package com.example.finalexam_wmp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EnrollmentActivity extends AppCompatActivity {
    public CheckBox checkBoxDSA, checkBoxNumerical, checkBoxWMP, checkBoxAI, checkBoxSE, checkBoxCalculus;
    private Button buttonSubmit, buttonSummary;
    private TextView textViewTotalCredits;
    private int totalCredits = 0;
    private final int maxCredits = 24;
    private boolean isSubmitted = false; // Untuk memastikan data belum dikirim sebelum submit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);

        checkBoxDSA = findViewById(R.id.checkBoxDSA);
        checkBoxNumerical = findViewById(R.id.checkBoxNumerical);
        checkBoxWMP = findViewById(R.id.checkBoxWMP);
        checkBoxAI = findViewById(R.id.checkBoxAI);
        checkBoxSE = findViewById(R.id.checkBoxSE);
        checkBoxCalculus = findViewById(R.id.checkBoxCalculus);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSummary = findViewById(R.id.buttonSummary);
        textViewTotalCredits = findViewById(R.id.textViewTotalCredits);

        // Atur click listener untuk semua checkbox
        checkBoxDSA.setOnClickListener(this::updateCredits);
        checkBoxNumerical.setOnClickListener(this::updateCredits);
        checkBoxWMP.setOnClickListener(this::updateCredits);
        checkBoxAI.setOnClickListener(this::updateCredits);
        checkBoxSE.setOnClickListener(this::updateCredits);
        checkBoxCalculus.setOnClickListener(this::updateCredits);

        // Tombol Submit
        buttonSubmit.setOnClickListener(v -> {
            if (totalCredits > maxCredits) {
                Toast.makeText(EnrollmentActivity.this, "Maximum credits exceeded!", Toast.LENGTH_SHORT).show();
                return;
            }
            isSubmitted = true; // Tandai bahwa data telah disubmit
            Toast.makeText(EnrollmentActivity.this, "Enrollment successful!", Toast.LENGTH_SHORT).show();
        });

        // Tombol Summary
        buttonSummary.setOnClickListener(v -> {
            if (!isSubmitted) {
                Toast.makeText(EnrollmentActivity.this, "Please submit your enrollment first!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(EnrollmentActivity.this, SummaryActivity.class);
            intent.putExtra("totalCredits", totalCredits);

            StringBuilder selectedSubjects = new StringBuilder();
            if (checkBoxDSA.isChecked()) {
                selectedSubjects.append("Data Structure and Algorithm (6 credits)\n");
            }
            if (checkBoxNumerical.isChecked()) {
                selectedSubjects.append("Numerical Method (6 credits)\n");
            }
            if (checkBoxWMP.isChecked()) {
                selectedSubjects.append("Wireless and Mobile Programming (6 credits)\n");
            }
            if (checkBoxAI.isChecked()) {
                selectedSubjects.append("Artificial Intelligence (6 credits)\n");
            }
            if (checkBoxSE.isChecked()) {
                selectedSubjects.append("Software Engineering (6 credits)\n");
            }
            if (checkBoxCalculus.isChecked()) {
                selectedSubjects.append("Calculus (6 credits)\n");
            }

            intent.putExtra("selectedSubjects", selectedSubjects.toString());
            startActivity(intent);
        });

        updateButtonState();
    }

    private void updateCredits(View view) {
        CheckBox checkBox = (CheckBox) view;
        int credits = 6;

        if (checkBox.isChecked()) {
            totalCredits += credits;
        } else {
            totalCredits -= credits;
        }

        textViewTotalCredits.setText("Total Credits: " + totalCredits);

        updateButtonState();
    }

    private void updateButtonState() {
        if (totalCredits == 0 || totalCredits > maxCredits) {
            buttonSubmit.setEnabled(false);
            buttonSummary.setEnabled(false);
        } else {
            buttonSubmit.setEnabled(true);
            buttonSummary.setEnabled(true);
        }
    }
}

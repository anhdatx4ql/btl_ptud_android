package com.example.btl_ptud_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_ptud_android.R;
import com.example.btl_ptud_android.databinding.ActivityLibraryBinding;

public class LibraryActivity extends AppCompatActivity {
    ActivityLibraryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLibraryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ProfileHome();
        MainActivityHome();
        AddQuizHome();
    }

    private void MainActivityHome() {
        binding.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AddQuizHome() {
        binding.btnAddQuiz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryActivity.this, AddQuizActivity.class);
                startActivity(intent);
            }

        });
    }

    private void ProfileHome() {
        binding.btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
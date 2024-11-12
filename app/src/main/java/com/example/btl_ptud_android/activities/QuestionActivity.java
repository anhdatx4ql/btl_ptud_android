package com.example.btl_ptud_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_ptud_android.R;

public class QuestionActivity extends AppCompatActivity {

    TextView txt_question_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question);

        txt_question_id = findViewById(R.id.id_question);

        Intent myIntent = getIntent();
        Integer idQuestion = myIntent.getIntExtra("category_id",-1);
        txt_question_id.setText("123123");
    }
}
package com.example.btl_ptud_android.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_ptud_android.R;
import com.example.btl_ptud_android.models.Questions;

import java.util.ArrayList;

public class AddLibraryActivity extends AppCompatActivity {

    private LinearLayout questionContainer; // layout câu hỏi
    private LinearLayout numberListContainer; // layout số câu hỏi
    private Button addQuestionButton; // button thêm câu hỏi
    private ArrayList<View> questionViews; // Danh sách chứa các view của câu hỏi
    private int currentQuestionIndex = 0; // Chỉ số câu hỏi hiện tại (1, 2, 3...)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_library);

        // Khởi tạo các thành phần giao diện
        questionContainer = findViewById(R.id.questionContainer);
        numberListContainer = findViewById(R.id.numberListContainer);
        addQuestionButton = findViewById(R.id.addQuestionButton);

        // Khởi tạo danh sách câu hỏi
        questionViews = new ArrayList<>();

        // Khi bấm vào nút "Thêm câu hỏi mới"
        addQuestionButton.setOnClickListener(v -> addNewQuestion());
    }

    // Hàm thêm câu hỏi mới
    private void addNewQuestion() {
        // Ẩn câu hỏi cũ nếu có
        if (currentQuestionIndex > 0) {
            View previousQuestionView = questionViews.get(currentQuestionIndex - 1);
            previousQuestionView.setVisibility(View.GONE);
        }

        // Tạo layout câu hỏi mới
        View newQuestionView = getLayoutInflater().inflate(R.layout.activity_add_questions, null);

        // Cập nhật số thứ tự câu hỏi
        TextView questionNumberText = newQuestionView.findViewById(R.id.questionNumberText);
        questionNumberText.setText(String.valueOf(currentQuestionIndex + 1));

        // Thêm câu hỏi vào container
        questionContainer.addView(newQuestionView);

        // Lưu câu hỏi vào danh sách
        questionViews.add(newQuestionView);

        // Tạo và thêm Button cho số thứ tự câu hỏi
        Button questionNumberButton = new Button(this);
        questionNumberButton.setText(String.valueOf(currentQuestionIndex + 1));
        questionNumberButton.setTextSize(18);
        questionNumberButton.setPadding(20, 20, 20, 20);

        final int questionIndex = currentQuestionIndex;  // Lưu giá trị chính xác của chỉ số câu hỏi
        Log.i("index", questionIndex+"");
        questionNumberButton.setOnClickListener(v -> showQuestion(questionIndex));

        // Thêm Button vào container chứa số thứ tự câu hỏi
        numberListContainer.addView(questionNumberButton);

        // Tăng chỉ số câu hỏi hiện tại
        currentQuestionIndex++;

        // Hiển thị câu hỏi mới
        showQuestion(currentQuestionIndex - 1);
    }

    // Hàm hiển thị câu hỏi theo chỉ số
    private void showQuestion(int questionIndex) {
        // Kiểm tra chỉ số hợp lệ
        if (questionIndex < 0 || questionIndex >= questionViews.size()) {
            Toast.makeText(this, "Invalid question index", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ẩn tất cả câu hỏi
        for (View questionView : questionViews) {
            questionView.setVisibility(View.GONE);
        }

        // Hiển thị câu hỏi theo chỉ số
        View questionView = questionViews.get(questionIndex);
        questionView.setVisibility(View.VISIBLE);
    }
}
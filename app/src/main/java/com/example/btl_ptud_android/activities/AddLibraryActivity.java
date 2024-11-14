package com.example.btl_ptud_android.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private ArrayList<View> questionViews; // Danh sách chứa các view của câu hỏi
    private ArrayList<Questions> questionList; // Danh sách chứa các câu hỏi
    private int currentQuestionIndex = 0; // Chỉ số câu hỏi hiện tại (1, 2, 3...)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_library);

        // Khởi tạo các thành phần giao diện
        questionContainer = findViewById(R.id.questionContainer);
        numberListContainer = findViewById(R.id.numberListContainer);
        // button thêm câu hỏi
        Button addQuestionButton = findViewById(R.id.addQuestionButton);
        Button saveButton = findViewById((R.id.saveLibraryAndQuestions)); // Nút lưu câu hỏi


        // Khởi tạo danh sách view câu hỏi
        questionViews = new ArrayList<>();

        // Khởi tạo danh sách câu hỏi
        questionList = new ArrayList<>();

        // Khi bấm vào nút "Thêm câu hỏi mới"
        addQuestionButton.setOnClickListener(v -> addNewQuestion());

        // Khi bấm vào nút "Lưu câu hỏi"
        saveButton.setOnClickListener(v -> saveQuestionsToFirebase());
    }

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

        // Lấy các EditText trong layout của câu hỏi
        EditText titleEditText = newQuestionView.findViewById(R.id.title_question);
        EditText answerAEditText = newQuestionView.findViewById(R.id.answer_1);
        EditText answerBEditText = newQuestionView.findViewById(R.id.answer_2);
        EditText answerCEditText = newQuestionView.findViewById(R.id.answer_3);
        EditText answerDEditText = newQuestionView.findViewById(R.id.answer_4);
        RadioGroup correctAnswerGroup = newQuestionView.findViewById(R.id.correctAnswerGroup);


        // Tạo một đối tượng question mới và thêm vào questionList
        Questions newQuestion = new Questions(
                currentQuestionIndex + 1,
                1,
                "", "", "", "", "", 1
        );
        questionList.add(newQuestion);

        // Lắng nghe thay đổi trong EditText để cập nhật câu hỏi
        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Questions question = questionList.get(questionList.indexOf(newQuestion));
                question.setTitle(charSequence.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        answerAEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Questions question = questionList.get(questionList.indexOf(newQuestion));
                question.setAnswerA(charSequence.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        // ... (Repeat for other answer EditTexts similarly)

        // Cập nhật đáp án đúng khi thay đổi
        correctAnswerGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int correctAnswer = 1;
            if (checkedId == R.id.radio_correct_answer_1) correctAnswer = 1;
            else if (checkedId == R.id.radio_correct_answer_2) correctAnswer = 2;
            else if (checkedId == R.id.radio_correct_answer_3) correctAnswer = 3;
            else if (checkedId == R.id.radio_correct_answer_4) correctAnswer = 4;

            Questions question = questionList.get(questionList.indexOf(newQuestion));
            question.setAnswerTrue(correctAnswer);
        });

        Button deleteQuestionButton = newQuestionView.findViewById(R.id.deleteQuestionButton);
        deleteQuestionButton.setOnClickListener(v -> {
            // Lấy vị trí hiện tại của câu hỏi cần xóa
            int questionIndex = questionList.indexOf(newQuestion);
            deleteQuestion(questionIndex);
        });

        // Thêm câu hỏi vào container và danh sách
        questionContainer.addView(newQuestionView);
        questionViews.add(newQuestionView);

        // Tạo và thêm Button cho số thứ tự câu hỏi
        Button questionNumberButton = new Button(this);
        questionNumberButton.setText(String.valueOf(currentQuestionIndex + 1));
        questionNumberButton.setOnClickListener(v -> showQuestion(questionList.indexOf(newQuestion)));
        numberListContainer.addView(questionNumberButton);

        showQuestion(questionList.indexOf(newQuestion));

        // Tăng chỉ số câu hỏi hiện tại
        currentQuestionIndex++;

    }

    // Hàm hiển thị câu hỏi theo chỉ số
    private void showQuestion(int  questionIndex) {
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

    // xóa câu hỏi
    private void deleteQuestion(int questionIndex) {
        if (questionIndex < 0 || questionIndex >= questionList.size()) {
            Toast.makeText(this, "Invalid question index", Toast.LENGTH_SHORT).show();
            return;
        }

        questionList.remove(questionIndex);
        questionContainer.removeView(questionViews.get(questionIndex));
        questionViews.remove(questionIndex);
        numberListContainer.removeViewAt(questionIndex);

        updateQuestionNumbers();

        // Cập nhật currentQuestionIndex để không vượt quá giới hạn của danh sách
        if (questionIndex < questionViews.size()) {
            currentQuestionIndex = questionIndex;
        } else {
            currentQuestionIndex = questionViews.size() - 1;
        }
        // Kiểm tra nếu không còn câu hỏi nào, đặt lại currentQuestionIndex về 0
        if (questionList.isEmpty()) {
            currentQuestionIndex = 0;
        } else {
            // Nếu vẫn còn câu hỏi, cập nhật currentQuestionIndex
            currentQuestionIndex = questionList.size();
        }

        if (currentQuestionIndex >= 0) {
            showQuestion(currentQuestionIndex);
        }
    }

    // Hàm cập nhật lại số thứ tự câu hỏi
    private void updateQuestionNumbers() {
        for (int i = 0; i < questionViews.size(); i++) {
            // Cập nhật lại số thứ tự câu hỏi trên TextView trong layout của câu hỏi
            TextView questionNumberText = questionViews.get(i).findViewById(R.id.questionNumberText);
            questionNumberText.setText(String.valueOf(i + 1));

            // Cập nhật lại số thứ tự trên Button và thiết lập sự kiện onClick
            Button questionNumberButton = (Button) numberListContainer.getChildAt(i);
            questionNumberButton.setText(String.valueOf(i + 1));

            // Cập nhật sự kiện onClick của Button để hiển thị câu hỏi theo chỉ số mới
            int finalIndex = i;  // Lưu lại chỉ số chính xác cho sự kiện onClick
            questionNumberButton.setOnClickListener(v -> showQuestion(finalIndex));
        }

        // Cập nhật lại chỉ số câu hỏi hiện tại
        currentQuestionIndex = questionViews.size();
    }

    // Hàm lưu danh sách câu hỏi vào Firebase
    private void saveQuestionsToFirebase() {
        // Lấy reference đến Firebase Database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference questionsRef = database.getReference("questions");

        // Lưu tất cả câu hỏi vào Firebase dưới dạng một mảng
        for (Questions question : questionList) {
//            String questionId = "question" + question.getID();  // Đặt id câu hỏi là "question" + ID
//            questionsRef.child(questionId).setValue(question)
//                    .addOnSuccessListener(aVoid -> {
//                        // Nếu lưu thành công
//                        Toast.makeText(AddLibraryActivity.this, "Câu hỏi đã được lưu!", Toast.LENGTH_SHORT).show();
//                    })
//                    .addOnFailureListener(e -> {
//                        // Nếu có lỗi
//                        Toast.makeText(AddLibraryActivity.this, "Lỗi lưu câu hỏi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    });
            Log.i("info_question", question.toString());
        }
    }
}
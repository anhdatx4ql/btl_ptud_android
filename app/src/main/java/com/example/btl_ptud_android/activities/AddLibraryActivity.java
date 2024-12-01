package com.example.btl_ptud_android.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_ptud_android.R;
import com.example.btl_ptud_android.models.Categories;
import com.example.btl_ptud_android.models.Questions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import common.GuidGenerator;

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
        Button saveButton = findViewById(R.id.saveLibraryAndQuestions); // Nút lưu câu hỏi


        // Khởi tạo danh sách view câu hỏi
        questionViews = new ArrayList<>();

        // Khởi tạo danh sách câu hỏi
        questionList = new ArrayList<>();

        // Khi bấm vào nút "Thêm câu hỏi mới"
        addQuestionButton.setOnClickListener(v -> addNewQuestion());

        // Khi bấm vào nút "Lưu câu hỏi"
        saveButton.setOnClickListener(v -> saveLibraryAndQuestionsToFirebase());
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
                GuidGenerator.generateGUID(),
                "",
                "", "", "", "", "", 1, currentQuestionIndex + 1
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

        answerBEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Questions question = questionList.get(questionList.indexOf(newQuestion));
                question.setAnswerB(charSequence.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        answerCEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Questions question = questionList.get(questionList.indexOf(newQuestion));
                question.setAnswerC(charSequence.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        answerDEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Questions question = questionList.get(questionList.indexOf(newQuestion));
                question.setAnswerD(charSequence.toString());
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

        // Xóa câu hỏi khỏi danh sách và giao diện
        questionList.remove(questionIndex);
        questionContainer.removeView(questionViews.get(questionIndex));
        questionViews.remove(questionIndex);
        numberListContainer.removeViewAt(questionIndex);

        // Cập nhật số thứ tự câu hỏi
        updateQuestionNumbers();

        // Tính toán chỉ số câu hỏi cần focus sau khi xóa
        int focusQuestionIndex = questionIndex - 1;
        if (focusQuestionIndex < 0 && !questionViews.isEmpty()) {
            focusQuestionIndex = 0; // Nếu đã xóa câu đầu tiên, focus vào câu đầu mới
        }

        // Kiểm tra nếu không còn câu hỏi nào thì reset currentQuestionIndex về 0
        if (questionList.isEmpty()) {
            currentQuestionIndex = 0;
        } else {
            // Cập nhật currentQuestionIndex để đảm bảo không vượt quá giới hạn
            currentQuestionIndex = questionList.size();
            // Hiển thị câu hỏi cần focus
            if (focusQuestionIndex >= 0) {
                showQuestion(focusQuestionIndex);
            }
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

    // Hàm lưu danh sách câu hỏi vào Firebase với xác nhận từ người dùng
    private void saveLibraryAndQuestionsToFirebase() {
        // Lấy tên đề tài
        EditText titleLibrary = findViewById(R.id.titleLibrary);
        String libraryTitle = titleLibrary.getText().toString().trim();

        if (libraryTitle.isEmpty()) {
            Toast toast = Toast.makeText(this, "Bạn phải nhập tên đề tài!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0); // Hiển thị ở giữa màn hình
            toast.show();
            return;
        }

        // Xử lý hiển thị câu cảnh báo
        new AlertDialog.Builder(this)
                .setTitle("Cảnh báo")
                .setMessage("Một số câu hỏi không có tiêu đề. Những câu hỏi này sẽ không được lưu. Bạn có muốn tiếp tục không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    String libraryID = GuidGenerator.generateGUID();
                    // Continue to save questions first
                    saveQuestionsListToFirebaseAsync(libraryID)
                            .thenRun(() -> {
                                // Sau khi tất cả câu hỏi đã được lưu, tiếp tục lưu thư viện vào Firebase
                                saveCategoryToFirebase(libraryTitle, libraryID);
                            })
                            .exceptionally(ex -> {
                                // Nếu có lỗi khi lưu câu hỏi
                                Log.e("saveError", "Lỗi khi lưu câu hỏi: " + ex.getMessage());
                                return null;
                            });
                })
                .setNegativeButton("Không", (dialog, which) -> {
                    // Hủy bỏ lưu dữ liệu
                    Toast.makeText(this, "Lưu câu hỏi bị hủy bỏ", Toast.LENGTH_SHORT).show();
                })
                .setCancelable(false)
                .show();
    }

    // Cập nhật hàm lưu câu hỏi để loại bỏ những câu hỏi không có tiêu đề
    private CompletableFuture<Integer> saveQuestionsListToFirebaseAsync(String libraryID) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference questionsRef = database.getReference("questions");

        int totalQuestions = questionList.size();
        final int[] successCount = {0}; // Bộ đếm thành công
        final int[] failureCount = {0}; // Bộ đếm thất bại

        List<Task<Void>> tasks = new ArrayList<>();
        Log.i("saveQuestionsListToFirebaseAsync", "run");

        // Tạo một task cho mỗi câu hỏi
        for (Questions question : questionList) {
            // Kiểm tra nếu câu hỏi không có tiêu đề
            if (question.getTitle().isEmpty()) {
                // Bỏ qua câu hỏi này nếu không có tiêu đề
                continue; // Bỏ qua câu hỏi này
            }

            String questionId = question.getID();
            question.setCategory_ID(libraryID);  // Gán ID danh mục cho câu hỏi

            // Tạo Task cho hành động setValue
            Task<Void> task = questionsRef.child(questionId).setValue(question)
                    .addOnSuccessListener(aVoid -> {
                        successCount[0]++;
                    })
                    .addOnFailureListener(e -> {
                        failureCount[0]++;
                        Log.e("info_question", "Lỗi lưu câu hỏi: " + e.getMessage());
                    });

            // Thêm task vào danh sách
            tasks.add(task);
        }

        // Chờ cho tất cả tasks hoàn thành và trả về số câu hỏi thành công
        CompletableFuture<Integer> allTasksComplete = new CompletableFuture<>();
        Tasks.whenAllComplete(tasks)
                .addOnCompleteListener(task -> {
                    // Khi tất cả các task đã hoàn tất, hoàn thành CompletableFuture và trả về số câu hỏi thành công
                    allTasksComplete.complete(successCount[0]);
                    if (successCount[0] + failureCount[0] == totalQuestions) {
                        showCompletionMessage(successCount[0], failureCount[0]);
                    }
                });

        return allTasksComplete; // Trả về CompletableFuture để có thể đợi kết quả
    }

    private void saveCategoryToFirebase(String libraryTitle, String libraryID) {
        // Lấy FirebaseDatabase instance
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Tạo reference đến bảng categories
        DatabaseReference categoriesRef = database.getReference("categories");

        // Tạo đối tượng thư viện với libraryTitle
        Categories newCategory = new Categories(libraryID, libraryTitle, questionList.size());

        // Lưu thư viện vào bảng categories
        categoriesRef.child(libraryID).setValue(newCategory)
                .addOnSuccessListener(aVoid -> {
                    // Lưu thành công, quay lại màn hình LibraryActivity
                    Toast.makeText(this, "Lưu dữ liệu thành công!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddLibraryActivity.this, LibraryActivity.class);
                    startActivity(intent);
                    finish(); // Kết thúc AddLibraryActivity để ngăn người dùng quay lại nó
                })
                .addOnFailureListener(e -> {
                    // Nếu có lỗi, hiển thị thông báo
                    Toast toast = Toast.makeText(this, "Lỗi lưu tên đề tài!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0); // Hiển thị ở giữa màn hình
                    toast.show();
                });
    }

    // Hàm hiển thị thông báo khi lưu hoàn tất
    private void showCompletionMessage(int successCount, int failureCount) {
        if (failureCount == 0) {
            Toast toast = Toast.makeText(this, "Tất cả câu hỏi đã được lưu thành công!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0); // Hiển thị ở giữa màn hình
            toast.show();
        } else {
            Toast toast = Toast.makeText(this, "Đã lưu thành công " + successCount + " câu hỏi. Có " + failureCount + " câu hỏi gặp lỗi.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0); // Hiển thị ở giữa màn hình
            toast.show();
        }
    }
}
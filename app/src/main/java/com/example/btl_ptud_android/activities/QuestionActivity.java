package com.example.btl_ptud_android.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_ptud_android.AdapterCustom.QuestionListAdapter;
import com.example.btl_ptud_android.R;
import com.example.btl_ptud_android.models.Questions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import com.example.btl_ptud_android.R;

public class QuestionActivity extends AppCompatActivity {

    private ListView listViewQuestions;
    private QuestionListAdapter questionListAdapter;
    private List<Questions> questionList;
    private Button btnDelete;
    private String idCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        EditText editText = findViewById(R.id.editTextText);

        // Khởi tạo ListView
        listViewQuestions = findViewById(R.id.listViewQuestions);

        // Khởi tạo danh sách câu hỏi
        questionList = new ArrayList<>();

        // Tạo adapter và liên kết với ListView
        questionListAdapter = new QuestionListAdapter(this, questionList);
        listViewQuestions.setAdapter(questionListAdapter);

        listViewQuestions.setOnItemClickListener((parent, view, position, id) -> {
            Questions selectedQuestion = questionList.get(position);

            Intent intent = new Intent(QuestionActivity.this, QuestionDetailActivity.class);
            intent.putExtra("question_id", selectedQuestion.getID());
            intent.putExtra("category_id", selectedQuestion.getCategory_ID());
            intent.putExtra("question_title", selectedQuestion.getTitle());
            intent.putExtra("option1", selectedQuestion.getAnswerA());
            intent.putExtra("option2", selectedQuestion.getAnswerB());
            intent.putExtra("option3", selectedQuestion.getAnswerC());
            intent.putExtra("option4", selectedQuestion.getAnswerD());
            intent.putExtra("correct_answer", selectedQuestion.getAnswerTrue());
            intent.putExtra("sort_order", selectedQuestion.getSort_order());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        // Lấy idCategory từ Intent
        idCategory = getIntent().getStringExtra("category_id");
        String categoryTitle = getIntent().getStringExtra("category_title");
        if (idCategory != null && categoryTitle != null) {
            // binding
            // Khởi tạo EditText
            editText.setText(categoryTitle);

            // Lắng nghe sự kiện click cho nút cập nhật
            Button btnUpdate = findViewById(R.id.button2); // Button cập nhật
            btnUpdate.setOnClickListener(v -> {
                String newCategoryTitle = editText.getText().toString();
                if (!newCategoryTitle.isEmpty()) {
                    updateCategoryTitle(idCategory, newCategoryTitle);
                } else {
                    Log.e("QuestionActivity", "Category title cannot be empty.");
                }
            });

            btnDelete = findViewById(R.id.button); // Button cập nhật
            btnDelete.setOnClickListener(v -> deleteCategoryAndQuestions());

            loadQuestionsFromFirebase(idCategory);
        } else {
            Log.e("QuestionActivity", "Invalid category ID");
        }
    }

    private void loadQuestionsFromFirebase(String categoryID) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference questionsRef = database.getReference("questions");
        questionsRef.orderByChild("category_ID").equalTo(categoryID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        questionList.clear();  // Xóa dữ liệu cũ

                        if (!dataSnapshot.exists()) {
                            Log.e("QuestionActivity", "No questions found for this category.");
                        }

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Questions question = snapshot.getValue(Questions.class);
                            if (question != null) {
                                questionList.add(question);
                                Log.d("QuestionActivity", "Question: " + question.getTitle());
                            } else {
                                Log.e("QuestionActivity", "Failed to parse question.");
                            }
                        }

                        // Cập nhật adapter
                        questionListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("FirebaseError", databaseError.getMessage());
                    }
                });
    }

    private void updateCategoryTitle(String categoryId, String newTitle) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference categoriesRef = database.getReference("categories").child(categoryId);

        // Cập nhật title mới cho danh mục
        categoriesRef.child("title").setValue(newTitle)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(QuestionActivity.this, "Cập nhật tiêu đề thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(QuestionActivity.this, "Cập nhật tiêu đề thất bại!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteCategoryAndQuestions() {
        if (idCategory == null || idCategory.isEmpty()) {
            Toast.makeText(this, "ID danh mục không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hiển thị hộp thoại xác nhận
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa danh mục này và tất cả các câu hỏi liên quan?")
                .setPositiveButton("Có", (dialog, which) -> {
                    // Thực hiện xóa nếu người dùng chọn "Có"
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference categoriesRef = database.getReference("categories").child(idCategory);
                    DatabaseReference questionsRef = database.getReference("questions");

                    // Xóa danh mục
                    categoriesRef.removeValue().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Xóa các câu hỏi có liên quan
                            questionsRef.orderByChild("category_ID").equalTo(idCategory)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
                                                questionSnapshot.getRef().removeValue(); // Xóa từng câu hỏi
                                            }

                                            Toast.makeText(QuestionActivity.this, "Xóa danh mục và câu hỏi thành công!", Toast.LENGTH_SHORT).show();
                                            finish(); // Quay lại màn hình trước
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Toast.makeText(QuestionActivity.this, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                                            Log.e("FirebaseError", databaseError.getMessage());
                                        }
                                    });
                        } else {
                            Toast.makeText(this, "Xóa danh mục thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Không", (dialog, which) -> {
                    // Đóng dialog nếu người dùng chọn "Không"
                    dialog.dismiss();
                })
                .show();
    }
}
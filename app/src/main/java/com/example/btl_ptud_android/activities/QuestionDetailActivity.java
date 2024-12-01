package com.example.btl_ptud_android.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl_ptud_android.R;
import com.example.btl_ptud_android.models.Questions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuestionDetailActivity extends AppCompatActivity {

    private EditText edtTitle, edtOption1, edtOption2, edtOption3, edtOption4;
    private Spinner spnCorrectAnswer;
    private Button btnUpdate, btnDelete;
    private String questionId;
    private String categoryId;
    private Integer sortOrder;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo các view
        edtTitle = findViewById(R.id.edtTitle);
        edtOption1 = findViewById(R.id.edtOption1);
        edtOption2 = findViewById(R.id.edtOption2);
        edtOption3 = findViewById(R.id.edtOption3);
        edtOption4 = findViewById(R.id.edtOption4);
        spnCorrectAnswer = findViewById(R.id.spnCorrectAnswer);

        btnUpdate = findViewById(R.id.btnUpdateQuestion);
        btnDelete = findViewById(R.id.btnDeleteQuestion);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        questionId = intent.getStringExtra("question_id");
        categoryId = intent.getStringExtra("category_id");
        sortOrder = intent.getIntExtra("sort_order", 1);
        edtTitle.setText(intent.getStringExtra("question_title"));
        edtOption1.setText(intent.getStringExtra("option1"));
        edtOption2.setText(intent.getStringExtra("option2"));
        edtOption3.setText(intent.getStringExtra("option3"));
        edtOption4.setText(intent.getStringExtra("option4"));

        String correctAnswer = intent.getStringExtra("correct_answer");
        if (correctAnswer != null) {
            int position = mapAnswerToPosition(correctAnswer);
            spnCorrectAnswer.setSelection(position);
            Log.i("info_test: ", "sort_order:" +sortOrder );
        } else {
            spnCorrectAnswer.setSelection(0); // Giá trị mặc định nếu không có correct_answer
            Log.i("info_test: ", "sort_order:" +sortOrder );
        }


        // Sự kiện cập nhật câu hỏi
        btnUpdate.setOnClickListener(v -> updateQuestion());

        // Sự kiện xóa câu hỏi
        btnDelete.setOnClickListener(v -> deleteQuestion());

    }

    // Hàm ánh xạ giá trị "A", "B", "C", "D" sang vị trí trong Spinner
    private int mapAnswerToPosition(String answer) {
        Log.i("correct_answer", answer);
        switch (answer) {
            case "A":
                return 0;
            case "B":
                return 1;
            case "C":
                return 2;
            case "D":
                return 3;
            default:
                return -1; // Giá trị mặc định nếu answer không hợp lệ
        }
    }

    private void updateQuestion() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference questionRef = database.getReference("questions").child(questionId);

        // Lấy lại giá trị từ các ô input
        String updatedTitle = edtTitle.getText().toString();
        String updatedOption1 = edtOption1.getText().toString();
        String updatedOption2 = edtOption2.getText().toString();
        String updatedOption3 = edtOption3.getText().toString();
        String updatedOption4 = edtOption4.getText().toString();

        // Lấy giá trị chọn trong Spinner (A, B, C, D)
        String updatedCorrectAnswer = mapPositionToAnswer(spnCorrectAnswer.getSelectedItemPosition());

        // Kiểm tra xem các giá trị có thay đổi không (nếu cần thiết)
        if (updatedTitle.isEmpty() || updatedOption1.isEmpty() || updatedOption2.isEmpty() ||
                updatedOption3.isEmpty() || updatedOption4.isEmpty()) {
            Toast.makeText(QuestionDetailActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng Questions mới với dữ liệu đã cập nhật
        Questions updatedQuestion = new Questions(
                questionId, // ID câu hỏi
                categoryId, // ID danh mục
                updatedTitle, // Tiêu đề câu hỏi
                updatedOption1, // Lựa chọn A
                updatedOption2, // Lựa chọn B
                updatedOption3, // Lựa chọn C
                updatedOption4, // Lựa chọn D
                updatedCorrectAnswer, // Câu trả lời đúng (A, B, C, D)
                sortOrder // Thứ tự sắp xếp
        );
        Log.i("updatedQuestion:", updatedQuestion.toString());

        questionRef.setValue(updatedQuestion).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Hàm ánh xạ từ vị trí Spinner sang A, B, C, D
    private String mapPositionToAnswer(int position) {
        switch (position) {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            default:
                return "A"; // Giá trị mặc định nếu vị trí không hợp lệ
        }
    }

    private void deleteQuestion() {

        // Tạo một AlertDialog để xác nhận việc xóa
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có muốn xóa câu hỏi này không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    // Nếu người dùng chọn "Có", thực hiện xóa câu hỏi
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference questionRef = database.getReference("questions").child(questionId);

                    // Kiểm tra nếu categoryId là hợp lệ
                    if (questionId == null || questionId.isEmpty()) {
                        Log.e("deleteQuestion", "Question ID is null or empty.");
                        Toast.makeText(this, "Không thể xóa. ID câu hỏi không hợp lệ!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Xóa câu hỏi
                    questionRef.removeValue().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Xóa câu hỏi thành công!", Toast.LENGTH_SHORT).show();
                            finish();  // Quay lại màn hình trước sau khi xóa thành công
                        } else {
                            Toast.makeText(this, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                            Log.e("deleteQuestion", "Failed to delete question: " + task.getException());
                        }
                    });
                })
                .setNegativeButton("Không", (dialog, which) -> {
                    // Nếu người dùng chọn "Không", không làm gì
                    dialog.dismiss();  // Đóng hộp thoại
                })
                .show();

    }
}
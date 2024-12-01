package com.example.btl_ptud_android.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.btl_ptud_android.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var user: FirebaseAuth
    private lateinit var name: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance()

        name = ""

        name = intent.getStringExtra("name").toString()
        binding.txtName.setText(name)

        ProfileSetting()
        SystemSetting()
        LogOut()
        EditProfile()
        //menu
        MainActivityHome()
        LibraryHome()
        AddQuizHome()

    }

    private fun EditProfile() {
        binding.btnProfileSetting.setOnClickListener {
            showEditNameDialog(name)
        }
    }

    private fun showEditNameDialog(currentName: String) {
        // Tạo một AlertDialog.Builder
        val builder = AlertDialog.Builder(this)

        // Cài đặt tiêu đề
        builder.setTitle("Chỉnh sửa tên")
        builder.setMessage("Vui lòng nhập tên mới:")

        // Tạo một EditText để nhập tên mới
        val input = EditText(this).apply {
            hint = "Nhập tên mới"
            setText(currentName) // Gán tên hiện tại
        }
        builder.setView(input)

        // Nút Xác nhận
        builder.setPositiveButton("Cập nhật") { dialog, _ ->
            val newName = input.text.toString().trim()
            if (newName.isNotEmpty()) {
                // Gọi hàm cập nhật tên
                updateUserName(newName)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Tên không được để trống!", Toast.LENGTH_SHORT).show()
            }
        }

        // Nút Hủy
        builder.setNegativeButton("Hủy") { dialog, _ ->
            dialog.dismiss()
        }

        // Hiển thị AlertDialog
        builder.show()
    }

    private fun updateUserName(newName: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            val databaseRef = FirebaseDatabase.getInstance().getReference("users").child(uid)
            databaseRef.child("name").setValue(newName)
                .addOnSuccessListener {
                    Toast.makeText(this, "Tên đã được cập nhật!", Toast.LENGTH_SHORT).show()
                    // Cập nhật giao diện
                    binding.txtName.text = newName
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Lỗi: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Không thể xác định người dùng hiện tại!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun LogOut() {
        binding.btnLogOut.setOnClickListener {
            user.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun SystemSetting() {
        binding.btnMode.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updatePassword() {
        TODO("Not yet implemented")
    }

    private fun ProfileSetting() {

    }

    private fun MainActivityHome() {
        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun LibraryHome() {
        binding.btnLibrary.setOnClickListener {
            val intent = Intent(this, LibraryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun AddQuizHome() {
        binding.btnAddQuiz.setOnClickListener {
            val intent = Intent(this, AddLibraryActivity::class.java)
            startActivity(intent)
        }

    }
}
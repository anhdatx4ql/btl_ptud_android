package com.example.btl_ptud_android.activities

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.btl_ptud_android.databinding.ActivityChangePasswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseRef: DatabaseReference
    private var admin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()



        binding.btnChange.setOnClickListener {
            binding.prgSignup.visibility = android.view.View.VISIBLE
            val oldPassword = binding.edtOldPassword.text.toString().trim()
            val newPassword = binding.edtNewPassword.text.toString().trim()
            val confirmPassword = binding.edtNewPassword2.text.toString().trim()

            // Kiểm tra đầu vào
            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                binding.prgSignup.visibility = android.view.View.GONE
                return@setOnClickListener
            }

            if (newPassword.length < 6) {
                Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show()
                binding.prgSignup.visibility = android.view.View.GONE
                return@setOnClickListener
            }

            if (newPassword != confirmPassword) {
                Toast.makeText(this, "Mật khẩu mới không trùng khớp", Toast.LENGTH_SHORT).show()
                binding.prgSignup.visibility = android.view.View.GONE
                return@setOnClickListener
            }
            // Xác thực lại người dùng trước khi đổi mật khẩu
            reAuthenticateAndChangePassword(oldPassword, newPassword)
        }

        firebaseRef = FirebaseDatabase.getInstance().getReference("users")
        val userId = auth.currentUser?.uid
        if (userId != null) {
            firebaseRef.child(userId).child("admin").get().addOnSuccessListener {
                if (it.exists()) {
                    val isAdmin = it.getValue(Boolean::class.java)
                    admin = isAdmin ?: false
                }
            }
        }

        binding.prgSignup.visibility = android.view.View.GONE
        binding.btnBack.setOnClickListener {
            if (admin == true) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, HomeUserActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


        binding.btnCancel.setOnClickListener {
            Toast.makeText(this, "Bạn đã huỷ đổi mật khẩu", Toast.LENGTH_SHORT).show()
            if (admin == true) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, HomeUserActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }



    private fun reAuthenticateAndChangePassword(oldPassword: String, newPassword: String) {
        val user = auth.currentUser
        if (user != null && user.email != null) {
            val credential = EmailAuthProvider.getCredential(user.email!!, oldPassword)

            user.reauthenticate(credential).addOnCompleteListener { reAuthTask ->
                if (reAuthTask.isSuccessful) {
                    // Nếu xác thực thành công, cập nhật mật khẩu
                    user.updatePassword(newPassword).addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Lỗi khi đổi mật khẩu: ${updateTask.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Mật khẩu cũ không đúng!", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Không thể xác định người dùng hiện tại!", Toast.LENGTH_SHORT).show()
        }
    }
}

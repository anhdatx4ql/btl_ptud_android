package com.example.btl_ptud_android.activities

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.btl_ptud_android.R
import com.example.btl_ptud_android.databinding.ActivityHomeLoginBinding
import com.example.btl_ptud_android.databinding.ActivitySignUpBinding
import com.example.btl_ptud_android.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tao instants cua Firebase
        firebaseAuth = FirebaseAuth.getInstance()


        AlreadyHaveAccount()
        SignUpForNewUser() // phuong thuc dang ky
        ShowPassWord()
    }

    fun SignUpForNewUser() {
        binding.btnSignup.setOnClickListener {
            binding.prgSignup.visibility = android.view.View.VISIBLE
            val edtEmail = binding.edtUsernameSignup.text.toString()
            val passWord = binding.edtPasswordSignup1.text.toString()
            val passWord2 = binding.edtPasswordSignup2.text.toString()

            if (edtEmail.isNotEmpty() && passWord.isNotEmpty() && passWord2.isNotEmpty()) {
                if (passWord == passWord2) {

                    //dang ky tai khoan
                    firebaseAuth.createUserWithEmailAndPassword(edtEmail,passWord).addOnCompleteListener {
                        if (it.isSuccessful){

                            val uid = firebaseAuth.currentUser?.uid ?: ""
                            val name = edtEmail.substringBefore("@")
                            var isAdmin = false
                            if (edtEmail.contains("@admin.haui.com")){
                                isAdmin = true
                            }else{
                                isAdmin = false
                            }

                            val user = User(
                                uId = uid,
                                name = name,
                                email = edtEmail,
                                password = passWord,
                                admin = isAdmin // Gán giá trị true/false cho isAdmin tùy theo vai trò
                            )

                            val db = Firebase.database
                            val usersRef = db.getReference("users")
                            usersRef.child(uid).setValue(user)
                                .addOnSuccessListener {
                                    //dki thanh cong thi chuyen sang dang nhap
                                    val intent = Intent(this, LoginActivity::class.java)
                                    intent.putExtra("email",edtEmail)
                                    startActivity(intent)
                                    Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Lỗi khi lưu thông tin người dùng: ${e.message}", Toast.LENGTH_SHORT).show()
                                    Log.e("Firebase1", "Error saving user data: ${e.message}")
                                }
                        }else{
                            //khong thanh cong -> thong bao exception
                            Toast.makeText(this, it.exception?.localizedMessage ?: "Unknown error", Toast.LENGTH_LONG).show()
//
                        }
                    }

                }else{
                    Toast.makeText(this,"Mật khẩu không trùng khớp!",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Vui lòng nhập đủ thông tin!",Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun AlreadyHaveAccount() {
        binding.btnHaveAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun ShowPassWord() {
        var isPasswordVisible = false
        binding.btnHidePassword.setOnClickListener{
            // Kiểm tra trạng thái hiện tại của mật khẩu và thay đổi
            if (isPasswordVisible) {
                // Ẩn mật khẩu, chuyển về dấu "*"
                binding.edtPasswordSignup1.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.edtPasswordSignup2.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                // Hiện mật khẩu, hiển thị ký tự thực
                binding.edtPasswordSignup1.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.edtPasswordSignup2.transformationMethod = HideReturnsTransformationMethod.getInstance()

            }
            isPasswordVisible = !isPasswordVisible
            // Đảm bảo con trỏ vẫn ở vị trí cuối sau khi thay đổi
            binding.edtPasswordSignup1.setSelection(binding.edtPasswordSignup1.text.length)
        }
    }
}

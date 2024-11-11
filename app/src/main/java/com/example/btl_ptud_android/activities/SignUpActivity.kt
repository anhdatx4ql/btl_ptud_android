package com.example.btl_ptud_android.activities

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.btl_ptud_android.R
import com.example.btl_ptud_android.databinding.ActivityHomeLoginBinding
import com.example.btl_ptud_android.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AlreadyHaveAccount()
        SignUpForNewUser()
        ShowPassWord()
    }

    private fun SignUpForNewUser() {
        binding.btnSignup.setOnClickListener {
            Toast.makeText(this,"Sign Up",Toast.LENGTH_LONG).show()
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
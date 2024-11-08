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
import com.example.btl_ptud_android.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        LoginCheck()
        ShowPassWord()
        ForgotPassword()
        RememberMe()
    }

    private fun RememberMe() {
        binding.swRemember.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Lưu mật khẩu", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Không lưu mật khẩu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun ForgotPassword() {
        binding.btnForgot.setOnClickListener{
            Toast.makeText(this,"Forgot Password",Toast.LENGTH_LONG).show()
        }
    }

    private fun ShowPassWord() {
        var isPasswordVisible = false
        binding.btnHidePassword.setOnClickListener{
            // Kiểm tra trạng thái hiện tại của mật khẩu và thay đổi
            if (isPasswordVisible) {
                // Ẩn mật khẩu, chuyển về dấu "*"
                binding.edtPasswordLogin.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                // Hiện mật khẩu, hiển thị ký tự thực
                binding.edtPasswordLogin.transformationMethod = HideReturnsTransformationMethod.getInstance()

            }
            isPasswordVisible = !isPasswordVisible
            // Đảm bảo con trỏ vẫn ở vị trí cuối sau khi thay đổi
            binding.edtPasswordLogin.setSelection(binding.edtPasswordLogin.text.length)
        }
    }

    private fun LoginCheck() {
        binding.btnLogin.setOnClickListener{
            val username = binding.edtUsernameLogin.text.toString()
            val password = binding.edtPasswordLogin.text.toString()

            Toast.makeText(this,"Login",Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
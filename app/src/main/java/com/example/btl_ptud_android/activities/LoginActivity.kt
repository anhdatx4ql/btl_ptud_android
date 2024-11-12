package com.example.btl_ptud_android.activities

import android.content.Intent
import android.content.SharedPreferences
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
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences // để lưu thng tin đăng nhập khi click lưu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //tao instants
        firebaseAuth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        //functions
        LoadInformation() // nếu có lưu thông tin đăng nhập thì load lên
        LoginCheck() //kiem tra dang nhap
        ShowPassWord()
        ForgotPassword()
        RememberMe()

        // chuyen tu dang ky sang se luu tai khoan
        val emailSignUp = intent.getStringExtra("email")
        if (emailSignUp != null) {
            binding.edtUsernameLogin.setText(emailSignUp)
        }


    }

    private fun LoadInformation() {
        val rememberMe = sharedPreferences.getBoolean("remember", false)
        if (rememberMe) {
            val email = sharedPreferences.getString("email", "")
            val password = sharedPreferences.getString("password", "")
            binding.edtUsernameLogin.setText(email)
            binding.edtPasswordLogin.setText(password)
            binding.swRemember.isChecked = true
        }
    }

    private fun RememberMe() {
        binding.swRemember.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val email = binding.edtUsernameLogin.text.toString()
                val password = binding.edtPasswordLogin.text.toString()
                val editor = sharedPreferences.edit()
                editor.putString("email", email)
                editor.putString("password", password)
                editor.putBoolean("remember", true)
                editor.apply()
                Toast.makeText(this, "Lưu mật khẩu", Toast.LENGTH_SHORT).show()
            } else {
                // Xóa thông tin nếu người dùng bỏ chọn
                val editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()
                Toast.makeText(this, "Không lưu mật khẩu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun ForgotPassword() {
        binding.btnForgot.setOnClickListener{
            val email = binding.edtUsernameLogin.text.toString()
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            intent.putExtra("email",email )
            startActivity(intent)
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
            val edtEmail = binding.edtUsernameLogin.text.toString()
            val passWord = binding.edtPasswordLogin.text.toString()

            if (edtEmail.isNotEmpty() && passWord.isNotEmpty()  ) {
                if (passWord.isNotEmpty()) {

                    //dang ky tai khoan
                    firebaseAuth.signInWithEmailAndPassword(edtEmail,passWord).addOnCompleteListener {
                        if (it.isSuccessful){
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                            // luu thong tin mat khau
                            RememberMe()
                        }else{
                            //khong thanh cong -> thong bao exception
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_LONG).show()
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
}
package com.example.btl_ptud_android.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.transition.Visibility
import com.example.btl_ptud_android.R
import com.example.btl_ptud_android.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //tao instant
        firebaseAuth = FirebaseAuth.getInstance()
        val email = intent.getStringExtra("email")
        if (email != null) {
            binding.edtUsernameLogin.setText(email)
        }

        ResetPass()
    }

    private fun ResetPass() {
        binding.btnReset.setOnClickListener {
           var email = binding.edtUsernameLogin.text.toString()
            if (email.isEmpty()){
                Toast.makeText(this,"Vui Long Nhap Du Thong Tin",Toast.LENGTH_SHORT).show()
            }else{
                binding.progressCircular.visibility = View.VISIBLE
                firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener {
                    Toast.makeText(this,"Vui Long Kiem Tra Email",Toast.LENGTH_SHORT).show()
                    binding.progressCircular.visibility = View.GONE
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                }.addOnFailureListener {
                    Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}
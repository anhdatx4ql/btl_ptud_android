package com.example.btl_ptud_android.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.btl_ptud_android.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var user: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance()

        val name = intent.getStringExtra("name")
        binding.txtName.setText(name)

        ProfileSetting()
        SystemSetting()
        LogOut()

        //menu
        MainActivityHome()
        LibraryHome()
        AddQuizHome()

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
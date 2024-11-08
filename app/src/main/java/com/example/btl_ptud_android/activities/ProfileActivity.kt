package com.example.btl_ptud_android.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.btl_ptud_android.R
import com.example.btl_ptud_android.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ProfileSetting()
        SystemSetting()
        LogOut()

        //menu
        MainActivityHome()
        LibraryHome()
        AddQuizHome()

    }

    private fun LogOut() {

    }

    private fun SystemSetting() {

    }

    private fun ProfileSetting() {

    }

    private fun MainActivityHome() {
        binding.btnHome.setOnClickListener {
            val intent = Intent(this, HomeLoginActivity::class.java)
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
            val intent = Intent(this, AddQuizActivity::class.java)
            startActivity(intent)
        }

    }
}
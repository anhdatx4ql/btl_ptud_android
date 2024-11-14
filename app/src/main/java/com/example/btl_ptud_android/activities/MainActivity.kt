package com.example.btl_ptud_android.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.btl_ptud_android.R
import com.example.btl_ptud_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ProfileHome()
        LibraryHome()
        AddQuizHome()
        SearchButton()
    }

    private fun SearchButton() {

    }

    private fun ProfileHome() {
        binding.btnUser.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun LibraryHome() {
        binding.btnLibrary.setOnClickListener{
            val intent = Intent(this, LibraryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun AddQuizHome() {
        binding.btnAddQuiz.setOnClickListener{
            val intent = Intent(this, AddLibraryActivity::class.java)
            startActivity(intent)
        }
    }
}
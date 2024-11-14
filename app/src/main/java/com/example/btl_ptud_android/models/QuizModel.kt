package com.example.btl_ptud_android.models

data class QuizModel(
    val id: String,
    val title: String,
    val description: String,

) {
    constructor() : this("", "", "")
}
package com.example.btl_ptud_android.AdapterCustom

import androidx.recyclerview.widget.RecyclerView
import com.example.btl_ptud_android.databinding.QuizItemRecycleRowBinding
import com.example.btl_ptud_android.models.QuizModel

class QuizListAdapter {

    class MyViewHolder(private val binding: QuizItemRecycleRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(model: QuizModel){

        }
    }
}
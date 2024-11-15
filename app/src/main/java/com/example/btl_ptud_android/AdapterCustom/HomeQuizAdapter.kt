package com.example.btl_ptud_android.AdapterCustom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.btl_ptud_android.databinding.QuizRecycleRowBinding
import com.example.btl_ptud_android.interfaces.rvCateInterface
import com.example.btl_ptud_android.models.Categories

class HomeQuizAdapter(private val categoryList: ArrayList<Categories>,
                    val onClickCateItem: rvCateInterface
):RecyclerView.Adapter<HomeQuizAdapter.myViewHolder>() {
    class myViewHolder(val binding:QuizRecycleRowBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        return myViewHolder(QuizRecycleRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return  categoryList.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentItem = categoryList[position]
        holder.apply {
            binding.apply {
                txtTitle.text = currentItem.title
                txtCountQuestion.text = currentItem.countQuestion.toString() + " questions"
            }
        }

        holder.itemView.setOnClickListener{
            onClickCateItem.onClickCateItem(position)
        }
    }
}
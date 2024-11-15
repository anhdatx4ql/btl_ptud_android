package com.example.btl_ptud_android.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.btl_ptud_android.AdapterCustom.HomeQuizAdapter
import com.example.btl_ptud_android.R
import com.example.btl_ptud_android.databinding.ActivityHomeUserBinding
import com.example.btl_ptud_android.interfaces.rvCateInterface
import com.example.btl_ptud_android.models.Categories
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeUserBinding
    // tao mang de luu dl tu firebase
    private lateinit var categoryArrayList: ArrayList<Categories>
    private lateinit var firebaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ProfileUser()

        // tao instant
        firebaseRef = FirebaseDatabase.getInstance().getReference("categories")
        categoryArrayList = arrayListOf<Categories>()


        //lay du lieu categories tu firebase
        GetDataFromFirebase()

        //hien thi ra recycleView
        binding.rvSubject.apply {
            layoutManager = LinearLayoutManager(this.context)
        }
    }
    private fun GetDataFromFirebase() {
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                categoryArrayList.clear()
                if (snapshot.exists()){
                    for (categorySnap in snapshot.children){
                        val id = categorySnap.child("id").value.toString()
                        val title = categorySnap.child("title").value.toString()
                        val countQuestion = categorySnap.child("countQuestion").value.toString().toInt()
                        val model = Categories(id,title,countQuestion)
                        categoryArrayList.add(model)
                    }
                }
                //tao adapter va phuong thuc onclick
                val adapter = HomeQuizAdapter(categoryArrayList,object : rvCateInterface {
                    override fun onClickCateItem(position: Int) {

                        //kiem tra id truyen di
//                        Toast.makeText(this@HomeUserActivity,"${categoryArrayList[position].id}", Toast.LENGTH_SHORT).show()

                        //tao intent truyen du lieu
                        val intent = Intent(this@HomeUserActivity,PlayQuizActivity::class.java)
                        intent.putExtra("category_id",categoryArrayList[position].id.toString())
                        startActivity(intent)
                    }
                } )

                //gan dl vao adapter
                binding.rvSubject.adapter = adapter


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HomeUserActivity,error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }


    private fun ProfileUser() {
        binding.btnUser.setOnClickListener{
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }
    }

}

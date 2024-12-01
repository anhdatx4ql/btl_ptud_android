package com.example.btl_ptud_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_ptud_android.AdapterCustom.MyCategoriesAdapter;
import com.example.btl_ptud_android.R;
import com.example.btl_ptud_android.databinding.ActivityLibraryBinding;
import com.example.btl_ptud_android.models.Categories;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {
    ActivityLibraryBinding binding;

    // danh sách title
    List<Categories> lstCategory = new ArrayList<>();

    // khai báo listview
    ArrayList<Categories> myCategories;
    MyCategoriesAdapter myCategoriesAdapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLibraryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ProfileHome();
        MainActivityHome();
        AddQuizHome();

        // binding dữ liệu
        lv = findViewById(R.id.listViewCategories);

        myCategories = new ArrayList<>();

        // Lấy dữ liệu từ Firebase
        getCategoriesFromFirebase();

        myCategoriesAdapter = new MyCategoriesAdapter(LibraryActivity.this, R.layout.layout_item_library, myCategories);
        lv.setAdapter(myCategoriesAdapter);

        // xử lý sự kiện click
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy danh mục từ danh sách
                Categories item = myCategories.get(position);

                // Hiển thị title hoặc thực hiện hành động khác
                Log.i("CategoryTitle", "Category: " + item.getTitle());
                // Chuyển đến Activity khác (ví dụ: để hiển thị câu hỏi thuộc danh mục này)
                Intent intent = new Intent(LibraryActivity.this, QuestionActivity.class);
                intent.putExtra("category_id", item.getID());
                intent.putExtra("category_title", item.getTitle());
                startActivity(intent);
            }
        });
    }

    private void MainActivityHome() {
        binding.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AddQuizHome() {
        binding.btnAddLibrary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryActivity.this, AddLibraryActivity.class);
                startActivity(intent);
            }

        });
    }

    private void ProfileHome() {
        binding.btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LibraryActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    // Lấy danh sách Categories từ Firebase và đếm số câu hỏi trong mỗi category
    private void getCategoriesFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference categoriesRef = database.getReference("categories");
        DatabaseReference questionsRef = database.getReference("questions");

        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myCategories.clear(); // Xóa danh sách cũ để tránh trùng lặp

                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    // Lấy thông tin từ Firebase
                    String categoryId = categorySnapshot.child("id").getValue(String.class);
                    String categoryTitle = categorySnapshot.child("title").getValue(String.class);
                    Integer questionCount = categorySnapshot.child("countQuestion").getValue(Integer.class);

                    // Kiểm tra dữ liệu không null trước khi thêm vào danh sách
                    if (categoryId != null && categoryTitle != null && questionCount != null) {
                        Categories category = new Categories(categoryId, categoryTitle, questionCount);
                        myCategories.add(category);
                    }
                }

                // Cập nhật Adapter sau khi thêm dữ liệu
                myCategoriesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Lỗi khi lấy categories: " + databaseError.getMessage());
            }
        });
    }
}
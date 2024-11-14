package com.example.btl_ptud_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_ptud_android.AdapterCustom.MyCategoriesAdapter;
import com.example.btl_ptud_android.R;
import com.example.btl_ptud_android.databinding.ActivityLibraryBinding;
import com.example.btl_ptud_android.models.Categories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {
    ActivityLibraryBinding binding;

    // danh sách title
    List<Categories> lstCategory = Arrays.asList(
            new Categories(123, "Bộ đề 1", 1, 30),
            new Categories(124, "Bộ đề 2", 1, 30),
            new Categories(125, "Bộ đề 3", 1, 30)
    );

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

        myCategories = new ArrayList<>(); // khởi tạo mảng rỗng

        // pvdat gán dữ liệu vào mảng
        for (Categories category : lstCategory) {
            myCategories.add(new Categories(category.getID(), category.getTitle(), category.getUserID(), category.getCountQuestion()));
        }

        myCategoriesAdapter = new MyCategoriesAdapter(LibraryActivity.this, R.layout.layout_item_library, myCategories);
        lv.setAdapter(myCategoriesAdapter);

        // xử lý sự kiện click
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(LibraryActivity.this, QuestionActivity.class);
                Categories item = myCategories.get(position);
                myIntent.putExtra("category_id", item.getID());
                startActivity(myIntent);
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
        binding.btnAdd.setOnClickListener(new View.OnClickListener(){
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
}
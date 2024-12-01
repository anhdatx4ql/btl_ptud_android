package com.example.btl_ptud_android.AdapterCustom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.btl_ptud_android.R;
import com.example.btl_ptud_android.models.Categories;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyCategoriesAdapter extends ArrayAdapter<Categories> {
    private Context context;
    private int resource;
    private ArrayList<Categories> categoriesList;
    // tạo constructor để main_activity gọi đến

    public MyCategoriesAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Categories> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.categoriesList = objects;
    }

    // gọi hàm getView để sắp xếp và trả về dữ liệu
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        // Lấy category hiện tại
        Categories category = categoriesList.get(position);

        // Ánh xạ các TextView
        TextView txtTitle = convertView.findViewById(R.id.txtCategoryTitle);
        TextView txtCount = convertView.findViewById(R.id.txtQuestionCount);

        // Hiển thị dữ liệu
        txtTitle.setText(category.getTitle());
        txtCount.setText(category.getCountQuestion() + "");

        return convertView;
    }

}

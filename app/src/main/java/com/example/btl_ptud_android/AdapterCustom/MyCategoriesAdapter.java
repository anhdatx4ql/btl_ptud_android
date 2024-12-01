package com.example.btl_ptud_android.AdapterCustom;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.btl_ptud_android.R;
import com.example.btl_ptud_android.models.Categories;

import java.util.List;

public class MyCategoriesAdapter extends ArrayAdapter<Categories> {
    Activity context;
    int IdLayout;
    List<Categories> myList;
    // tạo constructor để main_activity gọi đến

    public MyCategoriesAdapter(Activity context, int idLayout, List<Categories> myList) {
        super(context, idLayout, myList);
        this.context = context;
        this.IdLayout = idLayout;
        this.myList = myList;
    }

    // gọi hàm getView để sắp xếp và trả về dữ liệu
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_list_library, parent, false);
        }

        Categories category = myList.get(position);

        TextView titleTextView = convertView.findViewById(R.id.categoryTitle);
        TextView questionCountTextView = convertView.findViewById(R.id.categoryQuestionCount);

        titleTextView.setText(category.getTitle());
        questionCountTextView.setText(category.getCountQuestion() + "");

        return convertView;
    }
}

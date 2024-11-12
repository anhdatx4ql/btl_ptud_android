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
        LayoutInflater myInflater = context.getLayoutInflater();

        // đặt ID layout lên để tạo đối tượng view
        convertView = myInflater.inflate(IdLayout, null);

        // lấy 1 phần tử trong mảng
        Categories myCategory = myList.get(position);

        // khai báo, tham chiếu ID và hiển thị lên view
        TextView titleCategory = convertView.findViewById(R.id.category_title);
        TextView countQuestion = convertView.findViewById(R.id.category_count_question_text);

        // set giá trị cho model
        titleCategory.setText(myCategory.getTitle());
        countQuestion.setText(myCategory.getCountQuestion() + "");
        return convertView;
    }
}

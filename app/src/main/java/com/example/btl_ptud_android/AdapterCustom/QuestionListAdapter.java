package com.example.btl_ptud_android.AdapterCustom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import com.example.btl_ptud_android.R;
import com.example.btl_ptud_android.models.Questions;

import java.util.List;

public class QuestionListAdapter extends ArrayAdapter<Questions> {

    private Context context;
    private List<Questions> questionList;

    public QuestionListAdapter(Context context, List<Questions> questionList) {
        super(context, 0, questionList);
        this.context = context;
        this.questionList = questionList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Kiểm tra nếu convertView là null, nếu có thì tái sử dụng view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        }

        // Lấy câu hỏi tại vị trí hiện tại
        Questions question = questionList.get(position);

        // Thiết lập dữ liệu lên các TextView
        TextView txtQuestionTitle = convertView.findViewById(R.id.txtQuestionTitle);
        TextView txtCorrectAnswer = convertView.findViewById(R.id.txtCorrectAnswer);

        txtQuestionTitle.setText(question.getTitle());
        txtCorrectAnswer.setText(question.getAnswerTrue());

        return convertView;
    }
}
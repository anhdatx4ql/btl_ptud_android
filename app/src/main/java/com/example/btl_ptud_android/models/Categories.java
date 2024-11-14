package com.example.btl_ptud_android.models;

public class Categories {
    private String ID; // id danh mục
    private String title; // tiêu đề danh mục
    private int countQuestion; // tiêu đề danh mục

    // tạo constructor
    public Categories(String ID, String title, int countQuestion) {
        this.ID = ID;
        this.title = title;
        this.countQuestion = countQuestion;
    }

    public int getCountQuestion() {
        return countQuestion;
    }

    public void setCountQuestion(int countQuestion) {
        this.countQuestion = countQuestion;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

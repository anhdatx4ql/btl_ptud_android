package com.example.btl_ptud_android.models;

public class Categories {
    private int ID; // id danh mục
    private String title; // tiêu đề danh mục
    private int userID; // người tạo
    private int countQuestion; // người tạo

    // tạo constructor
    public Categories(int ID, String title, int userID, int countQuestion) {
        this.ID = ID;
        this.title = title;
        this.countQuestion = countQuestion;
        this.userID = userID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserID() {
        return userID;
    }

    public int getCountQuestion() {
        return countQuestion;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}

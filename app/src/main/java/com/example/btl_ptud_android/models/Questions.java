package com.example.btl_ptud_android.models;

public class Questions {
    private int ID; // id câu hỏi
    private int category_ID; // id danh mục
    private String title; // tiêu đề câu hỏi
    private String answerA; // câu trả lời a
    private String answerB; // câu trả lời b
    private String answerC; // câu trả lời c
    private String answerD; // câu trả lời d
    private int answerTrue;  // đáp án đúng 1,2,3,4

    public Questions(int ID, int category_ID, String title, String answerA, String answerB, String answerC, String answerD, int answerTrue) {
        this.ID = ID;
        this.category_ID = category_ID;
        this.title = title;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.answerTrue = answerTrue;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCategory_ID() {
        return category_ID;
    }

    public void setCategory_ID(int category_ID) {
        this.category_ID = category_ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public int getAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(int answerTrue) {
        this.answerTrue = answerTrue;
    }
}

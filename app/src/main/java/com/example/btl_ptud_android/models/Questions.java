package com.example.btl_ptud_android.models;

public class Questions {
    private String ID; // id câu hỏi
    private String category_ID; // id danh mục
    private String title; // tiêu đề câu hỏi
    private String answerA; // câu trả lời A
    private String answerB; // câu trả lời B
    private String answerC; // câu trả lời C
    private String answerD; // câu trả lời D
    private int sort_order;
    private String answerTrue;  // đáp án đúng (enum)

    // Enum for answers A, B, C, D
    public enum Answer {
        A(1), B(2), C(3), D(4);

        private final int index;

        Answer(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        // Get enum by index
        public static Answer fromIndex(int index) {
            for (Answer answer : values()) {
                if (answer.index == index) {
                    return answer;
                }
            }
            throw new IllegalArgumentException("Invalid answer index: " + index);
        }
    }

    // Constructor không đối số (Firebase yêu cầu)
    public Questions() {
    }

    // Constructor
    public Questions(String ID, String category_ID, String title, String answerA, String answerB, String answerC, String answerD, String answerTrue, int sort_order) {
        this.ID = ID;
        this.category_ID = category_ID;
        this.title = title;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.sort_order = sort_order;
        // Set the correct answer using the enum
        this.answerTrue = answerTrue;
    }

    // Getters and Setters
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCategory_ID() {
        return category_ID;
    }

    public void setCategory_ID(String category_ID) {
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

    public String getAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(String answerTrue) {
        this.answerTrue = answerTrue;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }

    // Override toString for better logging
    @Override
    public String toString() {
        return "Questions{" +
                "ID=" + ID +
                ", category_ID=" + category_ID +
                ", title='" + title + '\'' +
                ", answerA='" + answerA + '\'' +
                ", answerB='" + answerB + '\'' +
                ", answerC='" + answerC + '\'' +
                ", answerD='" + answerD + '\'' +
                ", answerTrue=" + answerTrue +
                '}';
    }

    // Override equals and hashCode for proper comparison and storage in collections
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Questions that = (Questions) obj;
        return ID == that.ID && category_ID == that.category_ID;
    }
}
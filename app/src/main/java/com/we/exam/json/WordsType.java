package com.we.exam.json;

/**
 * Created by zhou on 2017/8/1.
 */

public enum WordsType {
     HightSchool(1,35,"high_school_quiz.json"),TemFour(2,30,"tem4_quiz.json");
    private int value;
    private int parts;
    private String fileName;
    WordsType(int value,int parts,String fileName) {
        this.value = value;
        this.parts=parts;
        this.fileName=fileName;
    }

    public int getValue() {
        return value;
    }

    public int getParts() {
        return parts;
    }

    public String getFileName() {
        return fileName;
    }
}

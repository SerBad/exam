package com.we.exam.json;

/**
 * Created by zhou on 2017/8/1.
 */

public enum WordsType {
     HightSchool(1),TemFour(2);
    private int value;

    private WordsType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}

package com.we.exam.json;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhou on 2017/8/1.
 */

public class CatchData {
    private Context context;
    private WordsType wordsType;

    public CatchData(Context context, WordsType wordsType) {
        this.context = context;
        this.wordsType = wordsType;

    }

    public List<ExamWords> getExamData() {
        List<ExamWords> list = new ArrayList<>();
        Gson gson = new Gson();
        list=gson.fromJson(getAssetsFile(), new TypeToken<ArrayList<ExamWords>>() {
        }.getType());
        return list;
    }


    private String getAssetsFile() {
        InputStream inputStream;
        if (WordsType.HightSchool.getValue() == wordsType.getValue()) {
            try {
                inputStream = context.getAssets().open("high_school_quiz.json");
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = in.readLine()) != null) {
                    buffer.append(line);
                }
                return buffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (WordsType.TemFour.getValue() == wordsType.getValue()) {


        }
        return "";
    }

}

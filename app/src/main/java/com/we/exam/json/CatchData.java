package com.we.exam.json;

import android.content.Context;

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

    public interface DataInfo {
        void complete(List<ExamWords> list);

    }

    public CatchData(Context context, WordsType wordsType) {
        this.context = context;
        this.wordsType = wordsType;

    }

    public void getExamData(DataInfo dataInfo) {
        List<ExamWords> list = new ArrayList<>();
        Gson gson = new Gson();
        list = gson.fromJson(getAssetsFile(), new TypeToken<ArrayList<ExamWords>>() {
        }.getType());
        dataInfo.complete(list);
    }


    private String getAssetsFile() {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(wordsType.getFileName());
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

        return "";
    }

}

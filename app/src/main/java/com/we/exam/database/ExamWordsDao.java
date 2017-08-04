package com.we.exam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.we.exam.json.ExamWords;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhou on 2017/8/3.
 */

public class ExamWordsDao {
    private static String splitString = "\\+";
    private ExamWordsDaoHelper openHelper;
    private static String[] fieldString = new String[]{"id","answer","question","options","checkvalue"};

    public ExamWordsDao(Context context) {
        openHelper = new ExamWordsDaoHelper(context);
    }

    public void addWords(List<ExamWords> list) {
        SQLiteDatabase sql = openHelper.getWritableDatabase();
        for(ExamWords word:list){
            ContentValues values = new ContentValues();
            values.put(fieldString[0], word.getId());
            values.put(fieldString[1], word.getAnswer());
            values.put(fieldString[2], word.getQuestion()+"");

            String sen = "";
            if (word.getOptions() != null) {
                for (String s : word.getOptions()) {
                    sen += s + splitString;
                }
            }
            values.put(fieldString[3], sen+"");
            sql.insert(ExamWordsDaoHelper.dataBaseName, null, values);
        }

        sql.close();
    }
    public void updateCheck(String id,String check){
        SQLiteDatabase db = openHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(fieldString[4], check);//key为字段名，value为值
        db.update(ExamWordsDaoHelper.dataBaseName, values, "id=?", new String[]{id});
        db.close();
    }
    public List<ExamWords> getCheckById(String id){
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor cursor = db.query(ExamWordsDaoHelper.dataBaseName, fieldString,
                "id=?", new String[]{id}, null, null, null);
        List<ExamWords> words = new ArrayList<>();
        while (cursor.moveToNext()) {
            words.add(cursorToWord(cursor));
        }
        cursor.close();
        db.close();
        return words;
    }
    public ExamWords cursorToWord(Cursor cursor) {
        ExamWords word = new ExamWords();
        word.setId(String.valueOf(cursor.getInt(0)));
        word.setAnswer(cursor.getString(1));
        word.setQuestion(cursor.getString(2));
        String[] strings = cursor.getString(3).split(splitString);
        List<String> options = new ArrayList<String>();
        for (String s:strings) {
            options.add(s.replace("\\",""));
        }
        word.setOptions(options);
        word.setCheck(cursor.getString(4));
        return word;
    }

    public void deleteAll() {
        SQLiteDatabase sql = openHelper.getWritableDatabase();
        sql.delete(ExamWordsDaoHelper.dataBaseName, null, new String[]{});
        sql.close();
    }

    public List<ExamWords> queryAll() {
        SQLiteDatabase sql = openHelper.getReadableDatabase();
        Cursor cursor = sql.query(ExamWordsDaoHelper.dataBaseName, fieldString, null, null, null, null, null);
        List<ExamWords> words = new ArrayList<>();
        while (cursor.moveToNext()) {
            words.add(cursorToWord(cursor));
        }
        cursor.close();
        sql.close();
        return words;
    }

    public int getCorrectPercent(){
        List<ExamWords> wordses=this.queryAll();
        float correcetNumber=0f;
        for (ExamWords words:wordses){
            if(!TextUtils.isEmpty(words.getCheck())){
                if(TextUtils.equals(words.getAnswer(),words.getCheck())){
                    correcetNumber++;
                  //  Log.i("xxx","正确答案是："+words.getOptions().get(Integer.valueOf(words.getAnswer()))+"所选答案是："+words.getOptions().get(Integer.valueOf(words.getCheck())));
                }
            }
        }
        return  (int)(correcetNumber/wordses.size()*100);
    }
}

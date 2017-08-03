package com.we.exam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.we.exam.database.ExamWordsDao;
import com.we.exam.json.CatchData;
import com.we.exam.json.ExamWords;
import com.we.exam.json.WordsType;
import com.we.exam.util.SharedPreferencesUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import static com.we.exam.SplashActivity.NAMEKEY;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
    public static final String TIMEKEY = "timer";
    public static final String LISTKET="list";
    public static final String WORDSTYPEKEY="words_type";
    public static final int examParts=7;

    private EditText nameView;
    private TextView loginView;
    private Toast toast;
    private RadioGroup word_group;
    private List<ExamWords> list;
    private ExamWordsDao dao;
    private LinearLayout ll_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dao=new ExamWordsDao(this);

        ll_loading=(LinearLayout)findViewById(R.id.ll_loading);
        word_group=(RadioGroup)findViewById(R.id.word_group);
        nameView = (EditText) findViewById(R.id.name);
        loginView = (TextView) findViewById(R.id.login);
        loginView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_loading.setVisibility(View.VISIBLE);
                login();
            }
        });

    }

    private void login() {
        if (!TextUtils.isEmpty(nameView.getText())) {
            gotoMainPager();
        } else {
            showToast("请填写您的姓名");
            ll_loading.setVisibility(View.GONE);
        }
    }

    private void gotoMainPager() {
        switch (word_group.getCheckedRadioButtonId()){
            case -1:
                showToast("请选择要考试的课程");
                ll_loading.setVisibility(View.GONE);
                break;
            case R.id.tem:
                showToast("专四单词考试马上开始");
                startExam(WordsType.TemFour);
                break;
            case R.id.high_school:
                showToast("高中单词考试马上开始");
                startExam(WordsType.HightSchool);
                break;
            default:
                ll_loading.setVisibility(View.GONE);
                break;
        }
    }

    private void startExam(WordsType wordsType){

        //指定考试类型，后面的操作全部以这个为准
        SharedPreferencesUtil.setString(this,WORDSTYPEKEY, wordsType.getValue()+"");
        //设置考试者的姓名
        SharedPreferencesUtil.setString(this, NAMEKEY, nameView.getText().toString());

        new CatchData(this, wordsType).getExamData(new CatchData.DataInfo() {
            @Override
            public void complete(List<ExamWords> listAll) {
                TreeSet<Integer> partRandom= getRandom();
                list=new ArrayList<>();
                for(Integer i:partRandom){
                    list.addAll(listAll.subList(i*50,i*50+50));
                }
                dao.deleteAll();
                dao.addWords(list);
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                SharedPreferencesUtil.setString(LoginActivity.this, TIMEKEY, Calendar.getInstance().getTimeInMillis() + "");
                intent.putExtra(LISTKET,(Serializable) list);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showToast(String s) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
        toast.show();
    }

    private TreeSet<Integer> getRandom(){
        TreeSet<Integer> partRandom=new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if(o1>o2){
                    return 1;
                }else if(o1==o2){
                    return 0;
                }else {
                    return -1;
                }
            }
        });
        partRandom.comparator();
        Random random=new Random();
        int wordtype=Integer.valueOf(SharedPreferencesUtil.getString(this,WORDSTYPEKEY,"0"));
        int nextInt=0;
        if(wordtype==WordsType.HightSchool.getValue()){
            nextInt=WordsType.HightSchool.getParts();
        }else if(wordtype==WordsType.TemFour.getValue()){
            nextInt=WordsType.TemFour.getParts();
        }
        for (int i = 0; partRandom.size() <examParts; i++) {
            int randomInt=random.nextInt(nextInt);
            if (!partRandom.contains(randomInt)) {
                partRandom.add(randomInt);
            }
        }
        return partRandom;
    }
}


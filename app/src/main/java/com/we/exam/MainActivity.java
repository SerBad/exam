package com.we.exam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.we.exam.helper.BaseViewHelper;
import com.we.exam.json.CatchData;
import com.we.exam.json.WordsType;
import com.we.exam.recyclerview.ExamRecyclerViewAdapter;
import com.we.exam.recyclerview.FixLinearSnapHelper;
import com.we.exam.util.SharedPreferencesUtil;

import java.util.Calendar;

import static com.we.exam.LoginActivity.TIMEKEY;
import static com.we.exam.SplashActivity.NAMEKEY;

public class MainActivity extends Activity implements View.OnClickListener {
    private BaseViewHelper helper;
    private RecyclerView recycler_exam;
    private ExamRecyclerViewAdapter adapter;

    private TextView name_view, exitView, time_view;
    private Handler handler;

    private int time = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        initView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                intent.putExtra("id", view.getId());
                new BaseViewHelper
                        .Builder(MainActivity.this, view)
                        .startActivity(intent);
            }
        });

        countDown();

    }


    private void countDown() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String s=getCountDownTime(Calendar.getInstance().getTimeInMillis());
                        if(TextUtils.isEmpty(s)){
                            Intent intent=new Intent(MainActivity.this,ResultActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            time_view.setText(s);
                            handler.postDelayed(this, 1000);
                        }
                    }
                });


            }
        }, 1000);
    }


    private void initView() {
        name_view = (TextView) findViewById(R.id.name_view);
        name_view.setText(SharedPreferencesUtil.getString(this, NAMEKEY, ""));

        exitView = (TextView) findViewById(R.id.exit);
        time_view = (TextView) findViewById(R.id.time_view);
        exitView.setOnClickListener(this);

        recycler_exam = (RecyclerView) findViewById(R.id.recycler_exam);
        adapter = new ExamRecyclerViewAdapter(this, new CatchData(MainActivity.this, WordsType.HightSchool).getExamData());

        recycler_exam.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler_exam.setAdapter(adapter);
        new FixLinearSnapHelper().attachToRecyclerView(recycler_exam);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit:
                SharedPreferencesUtil.clearCacheInfo(this);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }


//    @Override
//    public void onBackPressed() {
//        if (helper != null && helper.isShowing()) {
//            helper.back();
//        } else {
//            super.onBackPressed();
//        }
//    }


    private String getCountDownTime(long l) {
        long lasttime = (l - Long.valueOf(SharedPreferencesUtil.getString(this, TIMEKEY, 0 + ""))) / 1000;
        long minute = (time - 1 - lasttime / 60);
        long second = (60 - lasttime % 60);
        String s = (minute < 10 ? "0" : "") + minute + ":" + (second < 10 ? "0" : "") + second + "";
        if(minute<0){
            return "";
        }else {
            return s;
        }

    }
}



package com.we.exam;

import android.app.Activity;
import android.content.DialogInterface;
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

import com.we.exam.database.ExamWordsDao;
import com.we.exam.helper.BaseViewHelper;
import com.we.exam.json.ExamWords;
import com.we.exam.recyclerview.ExamRecyclerViewAdapter;
import com.we.exam.recyclerview.FixLinearSnapHelper;
import com.we.exam.util.SharedPreferencesUtil;
import com.we.exam.view.QuitDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.we.exam.LoginActivity.LISTKET;
import static com.we.exam.LoginActivity.TIMEKEY;
import static com.we.exam.SplashActivity.NAMEKEY;

public class MainActivity extends Activity implements View.OnClickListener {
    private RecyclerView recycler_exam;
    private ExamRecyclerViewAdapter adapter;

    private TextView name_view, exitView, time_view,current_page;
    private FloatingActionButton fab,paper;
    private Handler handler;
    private List<ExamWords> list;
    private int time = 30;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ArrayList<ExamWords>) getIntent().getSerializableExtra(LISTKET);
        if (list == null || list.isEmpty()) {
            list = new ExamWordsDao(this).queryAll();
        }
        handler = new Handler();
        initView();


        countDown();
    }


    private void countDown() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String s = getCountDownTime(Calendar.getInstance().getTimeInMillis());
                        if (TextUtils.isEmpty(s)) {
                            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
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
        current_page=(TextView)findViewById(R.id.current_page);
        exitView = (TextView) findViewById(R.id.exit);
        time_view = (TextView) findViewById(R.id.time_view);
        exitView.setOnClickListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        paper = (FloatingActionButton) findViewById(R.id.paper);
        paper.setOnClickListener(this);

        recycler_exam = (RecyclerView) findViewById(R.id.recycler_exam);
        adapter = new ExamRecyclerViewAdapter(this, list);

        linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler_exam.setLayoutManager(linearLayoutManager);
        recycler_exam.setAdapter(adapter);
        current_page.setText(1 + "/"+adapter.getItemCount());
        recycler_exam.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    if(lastVisibleItem>0){
                        current_page.setText(lastVisibleItem + 1 + "/"+adapter.getItemCount());
                    }
                }
            }
        });
        new FixLinearSnapHelper().attachToRecyclerView(recycler_exam);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit:
                handler.removeCallbacksAndMessages(null);
                SharedPreferencesUtil.clearCacheInfo(this);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.fab:
                Intent intentFab = new Intent(MainActivity.this, QuestionActivity.class);
                new BaseViewHelper
                        .Builder(MainActivity.this, v)
                        .startActivityForResult(intentFab,true,100);
                break;

            case R.id.paper:
                new QuitDialog.Builder(this).setConfirmListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.removeCallbacksAndMessages(null);
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
                break;
        }

    }

    private String getCountDownTime(long l) {
        long lasttime = (l - Long.valueOf(SharedPreferencesUtil.getString(this, TIMEKEY, 0 + ""))) / 1000;
        long minute = (time - 1 - lasttime / 60);
        long second = (60 - lasttime % 60);
        String s = (minute < 10 ? "0" : "") + minute + ":" + (second < 10 ? "0" : "") + second + "";
        if (minute < 0) {
            return "";
        } else {
            return s;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==120&&data!=null){
            recycler_exam.smoothScrollToPosition((Integer) data.getSerializableExtra("page")-1);
            int number=(Integer) data.getSerializableExtra("number");
            if(number==4){
                adapter.smoothScrollToPosition(4);
            }else {
                adapter.smoothScrollToPosition(number-1);
            }
        }
    }

}



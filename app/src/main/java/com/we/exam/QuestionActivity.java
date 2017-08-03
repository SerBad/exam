package com.we.exam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.we.exam.database.ExamWordsDao;
import com.we.exam.helper.BaseViewHelper;
import com.we.exam.json.CatchData;
import com.we.exam.json.ExamWords;
import com.we.exam.json.WordsType;
import com.we.exam.recyclerview.QuestionRecyclerViewAdapter;

import java.io.Serializable;
import java.util.List;

public class QuestionActivity extends Activity implements View.OnClickListener {
    private ImageView close_image;
    private BaseViewHelper helper;
    private RecyclerView question_recycler;
    private QuestionRecyclerViewAdapter adapter;
    private List<ExamWords> list;
    private ExamWordsDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTranslation();
        setContentView(R.layout.activity_question);
        dao = new ExamWordsDao(this);
        inintView();
    }

    private void inintView() {
        close_image = (ImageView) findViewById(R.id.close_image);
        close_image.setOnClickListener(this);
        question_recycler = (RecyclerView) findViewById(R.id.question_recycler);
        list = dao.queryAll();
        adapter = new QuestionRecyclerViewAdapter(this, list);
        question_recycler.setLayoutManager(new GridLayoutManager(this, 5));
        question_recycler.setAdapter(adapter);
        adapter.setQuestionInfo(new QuestionRecyclerViewAdapter.QuestionInfo() {
            @Override
            public void click(int page, int number) {
                if (helper != null && helper.isShowing()) {
                    Intent intent=new Intent();
                    intent.putExtra("page",(Serializable) page);
                    intent.putExtra("number",(Serializable)number);
                    Log.i("xxx","page:"+page+"  number:"+number);
                    QuestionActivity.this.setResult(120,intent);
                    helper.backActivity(QuestionActivity.this);
                }
//                finish();
            }
        });

    }

    public void startTranslation() {
        helper = new BaseViewHelper
                .Builder(QuestionActivity.this)
                //.setEndView()//如果是两个切换的视图  这里设定最终显示的视图
//                .setTranslationView(question_recycler)//设置过渡视图
                .isFullWindow(true)//是否全屏显示
                .isShowTransition(true)//是否显示过渡动画
//                .setDimColor(getColor(R.color.mainColor))//遮罩颜色
                .setDimColor(Color.WHITE)//遮罩颜色
                .setDimAlpha(200)//遮罩透明度
                .setTranslationX(0)//x轴平移
                .setRotation(360)//旋转
                .setScaleX(0)//x轴缩放
                .setScaleY(0)//y轴缩放
                .setTranslationY(0)//y轴平移
                .setDuration(100)//过渡时长
                .setInterpolator(new AccelerateDecelerateInterpolator())//设置插值器
                //设置监听
                .setOnAnimationListener(new BaseViewHelper.OnAnimationListener() {
                    @Override
                    public void onAnimationStartIn() {
                        Log.e("TAG", "onAnimationStartIn");
                    }

                    @Override
                    public void onAnimationEndIn() {
                        Log.e("TAG", "onAnimationEndIn");
                    }

                    @Override
                    public void onAnimationStartOut() {
                        Log.e("TAG", "onAnimationStartOut");
                    }

                    @Override
                    public void onAnimationEndOut() {
                        Log.e("TAG", "onAnimationEndOut");
                    }
                })
                .create();//开始动画
    }

    @Override
    public void onBackPressed() {
        if (helper != null && helper.isShowing()) {
            helper.backActivity(this);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_image:
                if (helper != null && helper.isShowing()) {
                    helper.backActivity(this);
                }
//                finish();
                break;
        }
    }
}

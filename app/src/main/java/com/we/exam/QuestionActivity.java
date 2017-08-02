package com.we.exam;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

public class QuestionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
    }
//    public void startTranslation1(){
//        helper = new BaseViewHelper
//                .Builder(SecondActivity.this)
//                //.setEndView()//如果是两个切换的视图  这里设定最终显示的视图
//                //.setTranslationView(viewById)//设置过渡视图
//                .isFullWindow(true)//是否全屏显示
//                .isShowTransition(true)//是否显示过渡动画
//                .setDimColor(Color.WHITE)//遮罩颜色
//                .setDimAlpha(200)//遮罩透明度
//                .setTranslationX(0)//x轴平移
//                .setRotation(360)//旋转
//                .setScaleX(0)//x轴缩放
//                .setScaleY(0)//y轴缩放
//                .setTranslationY(0)//y轴平移
//                .setDuration(800)//过渡时长
//                .setInterpolator(new AccelerateDecelerateInterpolator())//设置插值器
//                //设置监听
//                .setOnAnimationListener(new BaseViewHelper.OnAnimationListener() {
//                    @Override
//                    public void onAnimationStartIn() {
//                        Log.e("TAG","onAnimationStartIn");
//                    }
//
//                    @Override
//                    public void onAnimationEndIn() {
//                        Log.e("TAG","onAnimationEndIn");
//                    }
//
//                    @Override
//                    public void onAnimationStartOut() {
//                        Log.e("TAG","onAnimationStartOut");
//                    }
//
//                    @Override
//                    public void onAnimationEndOut() {
//                        Log.e("TAG","onAnimationEndOut");
//                    }
//                })
//                .create();//开始动画
//    }
}

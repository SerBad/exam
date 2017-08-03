package com.we.exam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.we.exam.util.SharedPreferencesUtil;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import static com.we.exam.LoginActivity.TIMEKEY;
import static com.we.exam.MainActivity.COMPLETEKEY;

public class SplashActivity extends Activity {
    public static final String NAMEKEY = "username";
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        intent = new Intent();
        String time=SharedPreferencesUtil.getString(this, TIMEKEY, "");

        if (!TextUtils.isEmpty(SharedPreferencesUtil.getString(this, NAMEKEY, ""))) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    intent.setClass(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        } else if(SharedPreferencesUtil.getBoolean(SplashActivity.this,COMPLETEKEY,false)&&!TextUtils.isEmpty(time)&&((Calendar.getInstance().getTimeInMillis()-Long.valueOf(time))/1000)>MainActivity.time){
            Intent intent = new Intent(SplashActivity.this, ResultActivity.class);
            startActivity(intent);
            finish();
        }else {
            intent.setClass(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

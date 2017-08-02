package com.we.exam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.we.exam.util.SharedPreferencesUtil;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {
    public static final String NAMEKEY = "username";
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        intent = new Intent();

        if (!TextUtils.isEmpty(SharedPreferencesUtil.getString(this, NAMEKEY, ""))) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    intent.setClass(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);

        } else {
            intent.setClass(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }


    }
}

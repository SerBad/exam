package com.we.exam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.we.exam.util.SharedPreferencesUtil;

import java.util.Calendar;

import static com.we.exam.SplashActivity.NAMEKEY;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
    public static final String TIMEKEY = "timer";

    private EditText nameView;
    private TextView loginView;
    private Toast toast;
    private RadioGroup word_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        word_group=(RadioGroup)findViewById(R.id.word_group);
        nameView = (EditText) findViewById(R.id.name);
        loginView = (TextView) findViewById(R.id.login);
        loginView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login() {
        if (!TextUtils.isEmpty(nameView.getText())) {
            gotoMainPager();
        } else {
            showToast("请填写您的姓名");
        }
    }

    private void gotoMainPager() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);

        switch (word_group.getCheckedRadioButtonId()){
            case -1:
                showToast("请选择要考试的课程");
                break;
            case R.id.tem:
                showToast("专四单词考试马上开始");
                SharedPreferencesUtil.setString(this, NAMEKEY, nameView.getText().toString());
                SharedPreferencesUtil.setString(this, TIMEKEY, Calendar.getInstance().getTimeInMillis() + "");
                startActivity(intent);
                finish();
                break;
            case R.id.high_school:
                showToast("高中单词考试马上开始");
                SharedPreferencesUtil.setString(this, NAMEKEY, nameView.getText().toString());
                SharedPreferencesUtil.setString(this, TIMEKEY, Calendar.getInstance().getTimeInMillis() + "");
                startActivity(intent);
                finish();
                break;
        }


    }

    private void showToast(String s) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
        toast.show();
    }

}


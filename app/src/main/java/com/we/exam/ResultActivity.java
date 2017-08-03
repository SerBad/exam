package com.we.exam;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.we.exam.database.ExamWordsDao;
import com.we.exam.util.SharedPreferencesUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import static com.we.exam.SplashActivity.NAMEKEY;

//需要补齐剩下的权限请求
public class ResultActivity extends Activity {

    private static int requestCode = 100;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private TextView name_view, result_view;
    private ExamWordsDao dao;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        dao=new ExamWordsDao(this);
        initView();
    }

    private void initView() {
        name_view = (TextView) findViewById(R.id.name_view);
        result_view = (TextView) findViewById(R.id.result_view);
        name_view.setText(SharedPreferencesUtil.getString(this, NAMEKEY, ""));
        result_view.setText(Html.fromHtml(getResources().getString(R.string.result_score, dao.getCorrectPercent() + "")));
        result_view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                result_view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                verifyStoragePermissions(ResultActivity.this);
            }
        });
    }

    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, requestCode);
            }else {
                showToast("滑到最下面打开权限管理打开存储空间的权限");
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 9) {
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    localIntent.setAction(Intent.ACTION_VIEW);
                    localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
                    localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
                }
                startActivity(localIntent);
            }

        }else {
            screenShortcut();
        }
    }

    private void screenShortcut() {
        Bitmap bitmap = null;
        View view = this.getWindow().getDecorView();
        // 设置是否可以进行绘图缓存
        view.setDrawingCacheEnabled(true);
        // 如果绘图缓存无法，强制构建绘图缓存
        view.buildDrawingCache();
        // 返回这个缓存视图
        bitmap = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        view.getWindowVisibleDisplayFrame(frame);
        int stautsHeight = frame.top;

        int width = this.getWindowManager().getDefaultDisplay().getWidth();
        int height = this.getWindowManager().getDefaultDisplay().getHeight();
        // 根据坐标点和需要的宽和高创建bitmap
        bitmap = Bitmap.createBitmap(bitmap, 0, stautsHeight, width, height - stautsHeight);
        FileOutputStream fos = null;
        try {
            Calendar calendar = Calendar.getInstance();
            fos = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + File.separator + SharedPreferencesUtil.getString(this, NAMEKEY, "")+"-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.DATE) + ".png");
            if (null != fos) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == this.requestCode) {
            screenShortcut();
        } else {
            verifyStoragePermissions(ResultActivity.this);
        }
    }
    private void showToast(String s) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, s, Toast.LENGTH_LONG);
        toast.show();
    }
}

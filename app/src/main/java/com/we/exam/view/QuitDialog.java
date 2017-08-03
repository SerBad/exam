package com.we.exam.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.we.exam.R;


/**
 * Created by zhoutingjie on 2017/3/13.
 */

public class QuitDialog extends Dialog {
    public QuitDialog(Context context) {
        super(context);
    }

    public QuitDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected QuitDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {
        private Context context;
        private OnClickListener confirmListener;
        private OnClickListener cancelListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setConfirmListener(OnClickListener confirmListener) {
            this.confirmListener = confirmListener;
            return this;
        }

        public Builder setCancelListener(OnClickListener cancelListener) {
            this.cancelListener = cancelListener;
            return this;
        }


        public QuitDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final QuitDialog dialog = new QuitDialog(context, R.style.homedialog_style);
            final View layout = inflater.inflate(R.layout.dialog_quit, null);

            dialog.setContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            // dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(true);


            layout.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cancelListener != null) {
                        dialog.dismiss();
                        cancelListener.onClick(dialog, Dialog.BUTTON_NEGATIVE);
                    } else {
                        dialog.dismiss();
                    }

                }
            });
            layout.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (confirmListener != null) {
                        dialog.dismiss();
                        confirmListener.onClick(dialog, Dialog.BUTTON_POSITIVE);
                    } else {
                        dialog.dismiss();
                    }
                }
            });

            dialog.setContentView(layout);
            Window window = dialog.getWindow();
            window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
            // window.setWindowAnimations(R.style.AnimationPreview);  //添加动画

            return dialog;
        }
    }


}

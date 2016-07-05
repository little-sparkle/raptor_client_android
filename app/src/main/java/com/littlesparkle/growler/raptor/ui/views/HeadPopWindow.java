package com.littlesparkle.growler.raptor.ui.views;

import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.littlesparkle.growler.raptor.R;
import com.littlesparkle.growler.raptor.ui.activity.SettingActivity;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by dell on 2016/7/4.
 */
public class HeadPopWindow extends PopupWindow implements View.OnClickListener {
    private SettingActivity mSettingActivity;
    private View mContentView;
    private LayoutInflater mLayoutInflater;

    private TextView cancleTextView = null;

    private TextView cameraTextView = null;

    private TextView photoTextview = null;

    public HeadPopWindow(SettingActivity settingActivity, ViewGroup viewGroup) {
        mSettingActivity = settingActivity;
        mLayoutInflater = settingActivity.mLayoutInflater;
        mContentView = mLayoutInflater.inflate(R.layout.head_popwindow, viewGroup, false);
        this.setContentView(mContentView);
        mContentView.setFocusable(true);
        mContentView.setFocusableInTouchMode(true);
        mContentView.requestFocus();
        this.setBackgroundDrawable(new BitmapDrawable());
        mContentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    HeadPopWindow.this.dismiss();
                }
                return true;
            }
        });
        this.setOutsideTouchable(true);

        photoTextview= (TextView) mContentView.findViewById(R.id.tv_photo);
        cameraTextView= (TextView) mContentView.findViewById(R.id.tv_camera);
        cancleTextView= (TextView) mContentView.findViewById(R.id.tv_cancale_popwindow);
        photoTextview.setOnClickListener(this);
        cameraTextView.setOnClickListener(this);
        cancleTextView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancale_popwindow:
                Toast.makeText(mSettingActivity, "cancle", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_photo:
                Toast.makeText(mSettingActivity, "photo", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_camera:
                Toast.makeText(mSettingActivity, "camera", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

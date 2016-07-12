package com.littlesparkle.growler.raptor.ui.views;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.littlesparkle.growler.raptor.R;
import com.littlesparkle.growler.raptor.listener.OnPopwindowClickListener;
import com.littlesparkle.growler.raptor.ui.activity.InfoActivity;

import java.io.File;

/**
 * Created by dell on 2016/7/4.
 */
public class HeadPopWindow extends PopupWindow implements View.OnClickListener {
    private InfoActivity mInfoActivity;
    private View mContentView;
    private LayoutInflater mLayoutInflater;

    private OnPopwindowClickListener mOnPopwindowClickListener;


    private TextView cancleTextView = null;

    private TextView cameraTextView = null;

    private TextView photoTextview = null;


    private File mHeadPhoto = null;

    public HeadPopWindow(InfoActivity infoActivity, ViewGroup viewGroup) {
        mInfoActivity = infoActivity;
        mLayoutInflater = infoActivity.mLayoutInflater;
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

        photoTextview = (TextView) mContentView.findViewById(R.id.tv_photo);
        cameraTextView = (TextView) mContentView.findViewById(R.id.tv_camera);
        cancleTextView = (TextView) mContentView.findViewById(R.id.tv_cancale_popwindow);
        photoTextview.setOnClickListener(this);
        cameraTextView.setOnClickListener(this);
        cancleTextView.setOnClickListener(this);
    }

    public void setOnPopwindowClickListener(OnPopwindowClickListener onPopwindowClickListener) {
        mOnPopwindowClickListener = onPopwindowClickListener;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancale_popwindow:
                this.dismiss();
                break;
            case R.id.tv_photo:
                Toast.makeText(mInfoActivity, "photo", Toast.LENGTH_SHORT).show();
                mOnPopwindowClickListener.getPhotoByAlbums();
                break;
            case R.id.tv_camera:
                mOnPopwindowClickListener.getPhotoByCamera();
                break;
        }
    }


}

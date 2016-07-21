package com.littlesparkle.growler.raptor.ui.activity;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.littlesparkle.growler.library.activity.BaseActivity;
import com.littlesparkle.growler.library.activity.BaseFragmentActivity;
import com.littlesparkle.growler.library.activity.BaseTitleBarActivity;
import com.littlesparkle.growler.library.activity.HandlerActivity;
import com.littlesparkle.growler.library.user.UserManager;
import com.littlesparkle.growler.raptor.R;

public class SettingActivity extends BaseTitleBarActivity implements View.OnClickListener {

    private RelativeLayout mRelativeLayoutAddress;
    private RelativeLayout mRelativeLayoutAbout;
    private RelativeLayout mRelativeLayoutLogout;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        super.initView();
        mRelativeLayoutAddress = (RelativeLayout) findViewById(R.id.relative_address);
        mRelativeLayoutAddress.setOnClickListener(this);
        mRelativeLayoutAbout = (RelativeLayout) findViewById(R.id.relative_about);
        mRelativeLayoutAbout.setOnClickListener(this);
        mRelativeLayoutLogout = (RelativeLayout) findViewById(R.id.relative_logout);
        mRelativeLayoutLogout.setOnClickListener(this);

    }

    @Override
    protected int getTitleResourceId() {
        return R.string.setting_title;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_address:
                startActivity(new Intent(SettingActivity.this, CommonAddressActivity.class));
                break;

            case R.id.relative_about:
                startActivity(new Intent(SettingActivity.this, AboutActivity.class));
                break;
            case R.id.relative_logout:
                UserManager.signOut(SettingActivity.this);
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                MainActivity.mainActivity.finish();
                finish();
                break;
        }
    }
}

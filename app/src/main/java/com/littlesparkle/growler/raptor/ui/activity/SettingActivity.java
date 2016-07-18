package com.littlesparkle.growler.raptor.ui.activity;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.littlesparkle.growler.library.activity.BaseActivity;
import com.littlesparkle.growler.library.activity.BaseFragmentActivity;
import com.littlesparkle.growler.library.activity.HandlerActivity;
import com.littlesparkle.growler.raptor.R;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout mRelativeLayoutAddress;
    private RelativeLayout mRelativeLayoutAbout;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mRelativeLayoutAddress = (RelativeLayout) this.findViewById(R.id.relative_address);
        mRelativeLayoutAddress.setOnClickListener(this);
        mRelativeLayoutAbout = (RelativeLayout) this.findViewById(R.id.relative_about);
        mRelativeLayoutAbout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_address:
                startActivity(CommonAddressActivity.class);
                break;

            case R.id.relative_about:
                startActivity(AboutActivity.class);
                break;
        }
    }
}

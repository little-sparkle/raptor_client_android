package com.littlesparkle.growler.raptor.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;


import com.littlesparkle.growler.library.activity.BaseFragmentActivity;
import com.littlesparkle.growler.library.activity.HandlerActivity;
import com.littlesparkle.growler.raptor.R;


/**
 * Created by dell on 2016/7/6.
 */
public class LoginActivity extends BaseFragmentActivity implements View.OnClickListener {

    private TextView goToRegister = null;


    @Override
    public int setActivityContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        goToRegister = (TextView) this.findViewById(R.id.go_to_register);
        goToRegister.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_to_register:
                startActivity(RegisterActivity.class);
                break;
        }
    }
}

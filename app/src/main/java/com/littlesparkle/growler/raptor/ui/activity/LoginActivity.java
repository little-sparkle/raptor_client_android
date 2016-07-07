package com.littlesparkle.growler.raptor.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.littlesparkle.growler.library.activity.HandlerActivity;
import com.littlesparkle.growler.raptor.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2016/7/6.
 */
public class LoginActivity extends HandlerActivity {

    @Override
    protected void onHandlerMessage(Message msg) {

    }

    @Override
    public int setActivityContentView() {
        return R.layout.login_activity;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

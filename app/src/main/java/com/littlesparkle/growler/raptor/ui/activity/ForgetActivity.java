package com.littlesparkle.growler.raptor.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.littlesparkle.growler.library.activity.BaseForgetPwdActivity;

public class ForgetActivity extends BaseForgetPwdActivity {


    @Override
    protected void onSendAuthCodeClick() {
        Toast.makeText(this, "验证码", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResetPwdClick() {
        Toast.makeText(this, "重置密码", Toast.LENGTH_SHORT).show();
    }
}

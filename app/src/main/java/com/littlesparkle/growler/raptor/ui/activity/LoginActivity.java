package com.littlesparkle.growler.raptor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.littlesparkle.growler.library.activity.BaseFragmentActivity;
import com.littlesparkle.growler.library.activity.BaseLoginActivity;
import com.littlesparkle.growler.library.activity.HandlerActivity;
import com.littlesparkle.growler.raptor.R;


/**
 * Created by dell on 2016/7/6.
 */
public class LoginActivity extends BaseLoginActivity {


    @Override
    protected void onRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    protected void onForgetPasswordClick() {
       startActivity(new Intent(this, ForgetActivity.class));
    }

    @Override
    protected void onLoginClick() {
        Toast.makeText(this, "发起登陆请求", Toast.LENGTH_SHORT).show();
    }
}

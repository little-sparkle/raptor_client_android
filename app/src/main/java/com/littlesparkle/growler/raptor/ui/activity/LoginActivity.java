package com.littlesparkle.growler.raptor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.littlesparkle.growler.library.activity.BaseFragmentActivity;
import com.littlesparkle.growler.library.activity.BaseLoginActivity;
import com.littlesparkle.growler.library.activity.HandlerActivity;
import com.littlesparkle.growler.library.bean.User;
import com.littlesparkle.growler.library.http.BaseHttpSubscriber;
import com.littlesparkle.growler.library.log.Logger;
import com.littlesparkle.growler.library.misc.MiscHelper;
import com.littlesparkle.growler.library.preference.PrefHelper;
import com.littlesparkle.growler.library.user.UserManager;
import com.littlesparkle.growler.library.user.UserRequest;
import com.littlesparkle.growler.library.user.UserSignInResponse;
import com.littlesparkle.growler.raptor.R;


/**
 * Created by dell on 2016/7/6.
 */
public class LoginActivity extends BaseLoginActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String mobile = intent.getStringExtra("mobile");
        if (!TextUtils.isEmpty(mobile)) {
            mMobileInput.setText(mobile);
        }
    }

    @Override
    protected void onRegisterClick() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    protected void onForgetPasswordClick() {
        startActivity(new Intent(this, ForgetActivity.class));
    }

    @Override
    protected void onLoginClick() {
        String phoneNumber = mMobileInput.getText().toString();
        if ("".equals(phoneNumber)) {
            return;
        }
        if (!MiscHelper.checkPhoneNumber(phoneNumber)) {
            return;
        }

        String pwd = mPwdInput.getText().toString();
        if ("".equals(pwd)) {
            return;
        }
        if (!MiscHelper.checkPassword(pwd)) {
            return;
        }

        new UserRequest().signin(new BaseHttpSubscriber<UserSignInResponse>(this, this) {
            @Override
            protected void onError(String errorMessage) {
                super.onError(errorMessage);
                Logger.e("driver signin error: " + errorMessage);
            }

            @Override
            public void onNext(UserSignInResponse userSignInResponse) {
                Logger.log("user signin : " + userSignInResponse);
                UserManager.signIn(LoginActivity.this);
                User user = userSignInResponse.data.user;
                user.persist(LoginActivity.this);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();


            }
        }, phoneNumber, pwd);
    }
}

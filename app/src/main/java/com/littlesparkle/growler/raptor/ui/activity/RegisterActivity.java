package com.littlesparkle.growler.raptor.ui.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.littlesparkle.growler.library.activity.BaseFragmentActivity;
import com.littlesparkle.growler.library.activity.BaseRegisterActivity;
import com.littlesparkle.growler.library.activity.HandlerActivity;
import com.littlesparkle.growler.library.http.BaseHttpSubscriber;
import com.littlesparkle.growler.library.http.DefaultResponse;
import com.littlesparkle.growler.library.http.ErrorResponse;
import com.littlesparkle.growler.library.misc.MiscHelper;
import com.littlesparkle.growler.library.preference.PrefHelper;
import com.littlesparkle.growler.library.service.DownloadService;
import com.littlesparkle.growler.library.user.UserRequest;
import com.littlesparkle.growler.library.user.UserSignUpResponse;
import com.littlesparkle.growler.raptor.R;

public class RegisterActivity extends BaseRegisterActivity {

    private TextView mTextViewToBeDriver;
    private DownloadManager downloadManager;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        super.initView();
        mTextViewToBeDriver = (TextView) findViewById(R.id.to_be_driver);
        mTextViewToBeDriver.setVisibility(View.VISIBLE);
        mTextViewToBeDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToBeDriverClick();
            }
        });
    }

    private void onToBeDriverClick() {
        boolean isDownloading = PrefHelper.getBoolean(this, "isDownloading", false);

        if (isDownloading) {

            Toast.makeText(this, "不要急~在下载啦~", Toast.LENGTH_SHORT).show();
            return;
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://storage.360buyimg.com/jdmobile/JDMALL-PC2.apk"));
//        在什么样的网络情况下下载
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);
        request.setTitle("快打车司机版下载中~~");
        request.setDescription("人生苦短~快来打车吧~");
        request.setAllowedOverRoaming(false);
        //设置文件存放目录
        request.setMimeType("application/vnd.android.package-archive");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "raptor.apk");
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        id = downloadManager.enqueue(request);
        PrefHelper.setBoolean(this, "isDownloading", true);

    }

    @Override
    protected void onSendAuthCodeClick() {

        String phoneNumber = mMobileInput.getText().toString();

        if ("".equals(phoneNumber)) {
            Toast.makeText(this, "请填写手机号码~", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!MiscHelper.checkPhoneNumber(phoneNumber)) {
            Toast.makeText(this, "格式不对~", Toast.LENGTH_SHORT).show();
            return;
        }
        new UserRequest().signupSendSms(new BaseHttpSubscriber<DefaultResponse>(this, this) {
            @Override
            protected void onError(ErrorResponse error) {
                super.onError(error);
            }

            @Override
            public void onNext(DefaultResponse defaultResponse) {
                Toast.makeText(RegisterActivity.this, "验证码发送成功~请注意查收", Toast.LENGTH_SHORT).show();
            }
        }, phoneNumber);
    }

    @Override
    protected void onRegisterClick() {
        final String phoneNumber = mMobileInput.getText().toString();

        if ("".equals(phoneNumber)) {
            return;
        }
        if (!MiscHelper.checkPhoneNumber(phoneNumber)) {
            return;
        }

        String pwd = mPwdInput.getText().toString();
        String pwdConfirm = mPwdConfInput.getText().toString();

        if ("".equals(pwd) || "".equals(pwdConfirm)) {
            Toast.makeText(this, "请确认密码哦~", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pwd.equals(pwdConfirm)) {
            Toast.makeText(this, "两次的密码不一样", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!MiscHelper.checkPassword(pwd)) {
            Toast.makeText(this, "密码格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }

        new UserRequest().signup(new BaseHttpSubscriber<UserSignUpResponse>(this, this) {
            @Override
            public void onNext(UserSignUpResponse driverSignUpResponse) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.putExtra("mobile", phoneNumber);
                startActivity(intent);
                finish();
            }
        }, phoneNumber, pwd, mAuthCodeInput.getText().toString());
    }
}


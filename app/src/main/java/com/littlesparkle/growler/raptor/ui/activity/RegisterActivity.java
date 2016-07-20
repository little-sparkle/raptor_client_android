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
import com.littlesparkle.growler.library.preference.PrefHelper;
import com.littlesparkle.growler.library.service.DownloadService;
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
        Toast.makeText(this, "验证码", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRegisterClick() {
        Toast.makeText(this, "注册", Toast.LENGTH_SHORT).show();
    }
}

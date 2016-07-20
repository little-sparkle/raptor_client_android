package com.littlesparkle.growler.raptor.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.littlesparkle.growler.library.activity.BaseResetPwdActivity;

public class ResetActivity extends BaseResetPwdActivity {


    @Override
    protected void onResetPwdClick() {
        Toast.makeText(this, "重置密码", Toast.LENGTH_SHORT).show();
    }
}

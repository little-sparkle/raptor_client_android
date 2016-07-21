package com.littlesparkle.growler.raptor.ui.activity;

import android.content.Intent;

import com.littlesparkle.growler.library.activity.BaseSplashActivity;
import com.littlesparkle.growler.library.user.UserManager;
import com.littlesparkle.growler.raptor.R;

/**
 * Created by dell on 2016/7/18.
 */
public class SplashActivity extends BaseSplashActivity {
    @Override
    protected void onSplashEnd() {
        if (UserManager.isSignedIn(this)) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }
}

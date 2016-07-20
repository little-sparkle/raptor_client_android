package com.littlesparkle.growler.raptor.ui.activity;

import com.littlesparkle.growler.library.activity.BaseActivity;
import com.littlesparkle.growler.library.activity.BaseTitleBarActivity;
import com.littlesparkle.growler.raptor.R;

public class AboutActivity extends BaseTitleBarActivity {


    @Override
    public int getLayoutResId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected int getTitleResourceId() {
        return R.string.about;
    }
}

package com.littlesparkle.growler.raptor.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.littlesparkle.growler.library.activity.BaseActivity;
import com.littlesparkle.growler.raptor.R;
import com.littlesparkle.growler.raptor.ui.views.HeadPopWindow;



/**
 * Created by dell on 2016/7/4.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView headImageView = null;
    private HeadPopWindow mHeadPopWindow = null;
    private RelativeLayout driverLayout = null;
    private TextView mTextViewForPop = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setActivityContentView() {
        return R.layout.activity_setting;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        driverLayout = (RelativeLayout) this.findViewById(R.id.setting_driver);
        mTextViewForPop= (TextView) this.findViewById(R.id.text_for_pop);
        headImageView = (ImageView) this.findViewById(R.id.imgv_header_setting);
        headImageView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgv_header_setting:
                Toast.makeText(mBaseActivity, "head", Toast.LENGTH_SHORT).show();
                mHeadPopWindow = new HeadPopWindow(SettingActivity.this, driverLayout);
                mHeadPopWindow.setWidth(driverLayout.getWidth());
                mHeadPopWindow.setHeight(600);
                mHeadPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {

                    }
                });
                mHeadPopWindow.showAsDropDown(mTextViewForPop);

                break;
        }
    }
}

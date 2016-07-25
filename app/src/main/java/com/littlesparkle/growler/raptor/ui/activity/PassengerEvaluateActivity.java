package com.littlesparkle.growler.raptor.ui.activity;

import android.widget.Toast;

import com.littlesparkle.growler.library.activity.EvaluateActivity;
import com.littlesparkle.growler.library.http.BaseHttpSubscriber;
import com.littlesparkle.growler.library.http.DefaultResponse;
import com.littlesparkle.growler.library.order.OrderRequest;
import com.littlesparkle.growler.library.preference.PrefHelper;
import com.littlesparkle.growler.library.user.UserManager;
import com.littlesparkle.growler.raptor.R;

/**
 * Created by dell on 2016/7/25.
 */
public class PassengerEvaluateActivity extends EvaluateActivity {
    private float ratingCount;

    @Override
    public void onEvaluateButtonClick() {
        new OrderRequest().orderRate(new BaseHttpSubscriber<DefaultResponse>() {
            @Override
            public void onNext(DefaultResponse o) {
                Toast.makeText(PassengerEvaluateActivity.this, "成功", Toast.LENGTH_SHORT).show();
            }
        }, PrefHelper.getInteger(this, "user_id"), UserManager.getToken(PassengerEvaluateActivity.this), 0, (int) ratingCount);
    }

    @Override
    public void getRatingCount(float ratingCount) {
        this.ratingCount = ratingCount * 10;
        Toast.makeText(this, "this.ratingCount:" + this.ratingCount, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getTitleResourceId() {
        return R.string.evaluate;
    }


}

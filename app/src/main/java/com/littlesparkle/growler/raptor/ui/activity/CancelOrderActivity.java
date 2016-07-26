package com.littlesparkle.growler.raptor.ui.activity;

import android.content.Entity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.littlesparkle.growler.library.activity.CancelActivity;
import com.littlesparkle.growler.library.http.BaseHttpSubscriber;
import com.littlesparkle.growler.library.http.DefaultResponse;
import com.littlesparkle.growler.library.order.OrderCustomerRequest;
import com.littlesparkle.growler.library.preference.PrefHelper;
import com.littlesparkle.growler.library.user.UserManager;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by dell on 2016/7/25.
 */
public class CancelOrderActivity extends CancelActivity {


    private int reason_code;
    private String reason;
    private static List<String> entities = new ArrayList<>();

    static {
        entities.add("理由1");
        entities.add("理由2");
        entities.add("理由3");
        entities.add("理由4");
        entities.add("理由5");
        entities.add("理由6");
        entities.add("理由7");
        entities.add("理由8");
    }

    @Override
    public List<String> setReasonList() {
        return entities;
    }

    @Override
    protected void onCancelButtonClick() {
        new OrderCustomerRequest()
                .cancelOrder(new BaseHttpSubscriber<DefaultResponse>(this, this) {
                                 @Override
                                 public void onNext(DefaultResponse defaultResponse) {
                                     Toast.makeText(CancelOrderActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                                 }
                             }, PrefHelper.getInteger(CancelOrderActivity.this, "user_id"),
                        UserManager.getToken(CancelOrderActivity.this),
                        PrefHelper.getInteger(CancelOrderActivity.this, "order_id"),
                        2000,
                        reason_code,
                        reason);

    }

    @Override
    protected void onCancelLvItemClick(AdapterView<?> parent, View view, int position, long id) {
        reason = entities.get(position);
        reason_code = position;
    }
}

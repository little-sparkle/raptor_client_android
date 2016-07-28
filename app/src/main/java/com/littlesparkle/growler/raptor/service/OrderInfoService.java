package com.littlesparkle.growler.raptor.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.littlesparkle.growler.library.http.BaseHttpSubscriber;
import com.littlesparkle.growler.library.http.ErrorResponse;
import com.littlesparkle.growler.library.order.OrderRequest;
import com.littlesparkle.growler.library.order.response.OrderInfoResponse;
import com.littlesparkle.growler.library.preference.PrefHelper;
import com.littlesparkle.growler.library.service.RequestOrderInfoService;
import com.littlesparkle.growler.library.user.UserManager;

import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

/**
 * Created by dell on 2016/7/27.
 */
public class OrderInfoService extends RequestOrderInfoService {

    @Override
    protected void getOrderInfo() {
        int order_id = PrefHelper.getInteger(getApplicationContext(), "order_id");
        new OrderRequest().getOrderInfo(new BaseHttpSubscriber<OrderInfoResponse>() {
            @Override
            protected void onError(ErrorResponse error) {
                super.onError(error);
                Toast.makeText(getApplicationContext(), error.data.err_msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(OrderInfoResponse orderInfoResponse) {
                EventBus.getDefault().post(orderInfoResponse);
            }
        }, PrefHelper.getInteger(this, "user_id"), UserManager.getToken(this), order_id);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}

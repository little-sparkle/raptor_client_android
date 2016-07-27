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
import com.littlesparkle.growler.library.user.UserManager;

import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

/**
 * Created by dell on 2016/7/27.
 */
public class OrderInfoService extends Service {

    Timer mTimer = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                getOrderInfo(PrefHelper.getInteger(getApplicationContext(), "order_id"));
            }
        }, 5000, 5000);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void getOrderInfo(int order_id) {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }
}

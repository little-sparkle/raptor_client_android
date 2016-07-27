package com.littlesparkle.growler.raptor.boradcastreceiver;

import android.content.Context;

import com.google.gson.Gson;
import com.littlesparkle.growler.library.boradcastreceiver.PushGeTuiReceiver;
import com.littlesparkle.growler.library.order.response.OrderInfoResponse;

import de.greenrobot.event.EventBus;

/**
 * Created by dell on 2016/7/21.
 */
public class OrderReceiver extends PushGeTuiReceiver {

    public static final int is_called_car = 0;
    public static final int has_received_orders = 1;
    public static final int has_orders = 2;
    public static final int arrive_at_the_boarding = 3;
    public static final int began = 4;
    public static final int Trip_over_waiting_for_payment = 5;
    public static final int Trip_to_suspend = 6;
    public static final int Trip_over_has_been_payment = -1;
    public static final int Trip_accident_terminates = -2;
    public static final int No_driver_response = -3;
    public static final int Drivers_to_cancel_the_order = -4;
    public static final int Passengers_to_cancel_the_order = -5;

    @Override
    public void onMessageGet(Context context, String data) {
        Gson gson = new Gson();
        OrderInfoResponse orderInfoResponse = gson.fromJson(data, OrderInfoResponse.class);
        EventBus.getDefault().post(orderInfoResponse);
    }

    @Override
    public void onCidGet(Context context, String cid) {

    }
}

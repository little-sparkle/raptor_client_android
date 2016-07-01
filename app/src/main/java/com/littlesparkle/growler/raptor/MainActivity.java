package com.littlesparkle.growler.raptor;

import android.os.Bundle;
import android.os.Message;

import com.littlesparkle.growler.library.activity.MapActivity;

public class MainActivity extends MapActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getMapViewId() {
        return R.id.map;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onHandlerMessage(Message msg) {
    }
}

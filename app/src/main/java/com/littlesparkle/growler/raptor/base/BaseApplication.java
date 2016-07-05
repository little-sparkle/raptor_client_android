package com.littlesparkle.growler.raptor.base;

import android.app.Application;

import org.xutils.x;

/**
 * Created by dell on 2016/7/4.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}

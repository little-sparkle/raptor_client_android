package com.littlesparkle.growler.raptor.map;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.UiSettings;

/**
 * Created by dell on 2016/7/5.
 */
public class LocationTask {
    private UiSettings mUiSettings = null;
    private AMap mAMap = null;
    private AMapLocationClient mAMapLocationClient = null;
    private AMapLocationClientOption mAMapLocationClientOption = null;
    private Context mContext = null;
    private AMapLocationListener mAMapLocationListener = null;
    private static LocationTask mLocation = null;

    public static LocationTask getInstance(AMap aMap, Context context, AMapLocationListener aMapLocationListener) {
        if (mLocation == null) {
            synchronized (context) {
                if (mLocation == null) {
                    mLocation = new LocationTask(aMap, context, aMapLocationListener);
                }
            }
        }
        return mLocation;
    }

    private LocationTask(AMap aMap, Context context, AMapLocationListener aMapLocationListener) {
        mAMap = aMap;
        mUiSettings = aMap.getUiSettings();
        mContext = context;
        mAMapLocationListener = aMapLocationListener;
        mAMapLocationClient = new AMapLocationClient(mContext.getApplicationContext());
    }

    //    只定位一次
    public void LocationOnce() {
        mUiSettings.setMyLocationButtonEnabled(false);
        mAMap.setMyLocationEnabled(true);
        mAMapLocationClient.setLocationListener(mAMapLocationListener);
        mAMapLocationClientOption = new AMapLocationClientOption();
        mAMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mAMapLocationClientOption.setOnceLocation(true);
        mAMapLocationClient.setLocationOption(mAMapLocationClientOption);
        mAMapLocationClient.startLocation();
    }

    public void LocationByTime(int time) {

        mUiSettings.setMyLocationButtonEnabled(false);
        mAMap.setMyLocationEnabled(true);
        mAMapLocationClient.setLocationListener(mAMapLocationListener);
        mAMapLocationClientOption = new AMapLocationClientOption();
        mAMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mAMapLocationClientOption.setInterval(time);
        mAMapLocationClient.setLocationOption(mAMapLocationClientOption);
        mAMapLocationClient.startLocation();
    }

    public void stopLocation() {
        mAMapLocationClient.stopLocation();
        mAMapLocationClient.onDestroy();
    }
}

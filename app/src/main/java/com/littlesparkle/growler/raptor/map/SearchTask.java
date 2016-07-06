package com.littlesparkle.growler.raptor.map;

import android.content.Context;
import android.widget.Toast;


import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.littlesparkle.growler.raptor.listener.OnLocationGetListener;

/**
 * Created by dell on 2016/7/5.
 */
public class SearchTask implements GeocodeSearch.OnGeocodeSearchListener {

    private GeocodeSearch geocoderSearch = null;
    private Context mContext = null;
    private static final float SEARCH_RADIUS = 50;
    private OnLocationGetListener mOnLocationGetListener;

    public void setOnLocationGetListener(OnLocationGetListener onLocationGetListener) {
        mOnLocationGetListener = onLocationGetListener;
    }


    public SearchTask(Context context) {
        this.mContext = context;
        geocoderSearch = new GeocodeSearch(context);
        geocoderSearch.setOnGeocodeSearchListener(this);
    }


    //根据地址查询经纬度
    public void searchByAddress(String name, String cityCode) {


        GeocodeQuery queryByAddress = new GeocodeQuery(name, cityCode);
        geocoderSearch.getFromLocationNameAsyn(queryByAddress);
    }

    //根据经纬度查询地址
    public void searchByLatLng(LatLng latLng) {

        //latLonPoint参数表示一个Latlng，第二参数表示范围多少米，GeocodeSearch.AMAP表示是国测局坐标系还是GPS原生坐标系
        double lat = latLng.latitude;
        double lng = latLng.longitude;
        LatLonPoint latLonPoint = new LatLonPoint(lat, lng);
        RegeocodeQuery queryByLatLng = new RegeocodeQuery(latLonPoint, SEARCH_RADIUS, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(queryByLatLng);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == 1000) {
            RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
            regeocodeAddress.getBuilding();
            regeocodeAddress.getCity();
            mOnLocationGetListener.onLocationGet(regeocodeResult);
        }

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}

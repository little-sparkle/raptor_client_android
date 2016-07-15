package com.littlesparkle.growler.raptor.map;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.littlesparkle.growler.raptor.R;

import java.util.ArrayList;

/**
 * Created by dell on 2016/7/5.
 */
public class MarkerTask {
    private AMap mAMap = null;
    private MarkerOptions mainMarkerOptions = new MarkerOptions();
    private ArrayList<Marker> markers = new ArrayList<Marker>();
    private Marker mainMarker = null;

    public MarkerTask(AMap aMap) {
        mAMap = aMap;
    }

    //添加定位点marker
    public void addMainMarker(LatLng latLng) {
        mainMarkerOptions.position(latLng);
        mainMarkerOptions.draggable(false);
        mainMarkerOptions.anchor(0.1f, 0.1f);
//        设置图片
        mainMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_header_default));
        if (mainMarker == null) {
            mainMarker = mAMap.addMarker(mainMarkerOptions);
            mainMarker.setTitle("当前位置");

        } else {
            mainMarker.setPosition(latLng);

        }
    }

    //    根据坐标添加marker
    public void addMarkerWithLatLng(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.draggable(false);
        markerOptions.anchor(0.1f, 0.1f);
//        设置图片
//        mainMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
//        mAMap.clear();
        mAMap.addMarker(markerOptions);
    }

    public void addCarMarker(LatLng center) {

        if (markers.size() == 0) {
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_car);

            for (int i = 0; i < 20; i++) {
                double latitudeDelt = (Math.random() - 0.5) * 0.1 * 0.1;
                double longtitudeDelt = (Math.random() - 0.5) * 0.1 * 0.1;
                MarkerOptions carMarkerOptions = new MarkerOptions();

                carMarkerOptions.anchor(0.5f, 0.5f);
                carMarkerOptions.icon(bitmapDescriptor);
                carMarkerOptions.position(new LatLng(center.latitude + latitudeDelt, center.longitude + longtitudeDelt));
                Marker marker = mAMap.addMarker(carMarkerOptions);
                markers.add(marker);
            }
        } else {
            for (Marker marker : markers) {
                double latitudeDelt = (Math.random() - 0.5) * 0.1 * 0.1;
                double longtitudeDelt = (Math.random() - 0.5) * 0.1 * 0.1;
                marker.setPosition(new LatLng(center.latitude + latitudeDelt, center.longitude + longtitudeDelt));

            }
        }

    }

}

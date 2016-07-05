package com.littlesparkle.growler.raptor.ui.activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.Projection;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.littlesparkle.growler.library.activity.HandlerActivity;
import com.littlesparkle.growler.raptor.R;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.sql.DriverManager;



public class MainActivity extends HandlerActivity implements
        AMapLocationListener, View.OnClickListener {

    private MapView mMapView = null;
    private AMap mAMap = null;
    private UiSettings mUiSettings = null;
    private AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mAMapLocationClient = null;
    private Drawer mainDrawer = null;
    private Button menuButton = null;

    private Drawer mDrawer = null;
    private IProfile mProfile = null;
    private AccountHeader mAccountHeader = null;

    private MarkerOptions mainMarkerOptions = null;


    public void location() {

        mUiSettings = mAMap.getUiSettings();
        mUiSettings.setMyLocationButtonEnabled(false);
        mAMap.setMyLocationEnabled(true);
        mAMapLocationClient = new AMapLocationClient(this.getApplicationContext());
        mAMapLocationClient.setLocationListener(this);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(10000);
        mAMapLocationClient.setLocationOption(mLocationOption);
        mAMapLocationClient.startLocation();
    }

    private void initAccountHeader() {
        mProfile = new ProfileDrawerItem()
                .withIcon(R.mipmap.ic_launcher)
                .withIdentifier(1);

        mAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.md_red_50)
                .addProfiles(mProfile)
                .withSelectionListEnabled(false)
                .withTranslucentStatusBar(false)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        return true;
                    }
                })
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        startActivity(SettingActivity.class);
//                        mDrawer.closeDrawer();
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        return true;
                    }
                })
                .build();
    }

    private void initDrawer() {
        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
//                .withToolbar(mToolbar)
                .withAccountHeader(mAccountHeader, true)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withHasStableIds(true)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.drawer_item_xingcheng)
                                .withIcon(R.mipmap.ic_launcher)
                                .withIdentifier(R.integer.slide_menu_xingcheng)
                                .withSelectable(false),
                        new PrimaryDrawerItem()
                                .withName(R.string.drawer_item_qianbao)
                                .withIcon(R.mipmap.ic_launcher)
                                .withIdentifier(R.integer.slide_menu_qianbao)
                                .withSelectable(false),
                        new PrimaryDrawerItem()
                                .withName(R.string.drawer_item_kefu)
                                .withIcon(R.mipmap.ic_launcher)
                                .withIdentifier(R.integer.slide_menu_kefu)
                                .withSelectable(false),
                        new PrimaryDrawerItem()
                                .withName(R.string.drawer_item_settings)
                                .withIcon(R.mipmap.ic_launcher)
                                .withIdentifier(R.integer.slide_menu_settings)
                                .withSelectable(false)
                )
                .withSelectedItem(R.integer.slide_menu_null_id)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        return false;
                    }
                })
                .build();
    }

    //根据坐标添加marker
    public void addMainMarker(LatLng latLng) {
        mainMarkerOptions.position(latLng);
        mainMarkerOptions.draggable(false);
        mainMarkerOptions.anchor(0.1f, 0.1f);
//        设置图片
//        mainMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        mAMap.clear();
        mAMap.addMarker(mainMarkerOptions);
    }

    public void addCenterMarker() {

    }

    public LatLng pointToLatLng(int ScreenWidth, int ScreenHeight) {
        Projection projection = mAMap.getProjection();
        Point point = new Point(ScreenWidth, ScreenHeight);
        LatLng latLng = projection.fromScreenLocation(point);
        return latLng;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        initAccountHeader();
        initDrawer();

        addCenterMarker();
        location();
    }

    public void moveToLatLng(LatLng latLng) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.changeLatLng(latLng);
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        mAMap.moveCamera(cameraUpdate);
    }

    @Override
    protected void onHandlerMessage(Message msg) {

    }

    @Override
    public int setActivityContentView() {
        return R.layout.activity_main;
    }


    @Override
    public void initData() {

    }


    @Override
    public void initView() {

        mMapView = (MapView) this.findViewById(R.id.mapView);
        menuButton = (Button) this.findViewById(R.id.bt_menu);
        menuButton.setOnClickListener(this);
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
        mainMarkerOptions = new MarkerOptions();

    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState = mainDrawer.saveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    //定位回调
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                LatLng latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                moveToLatLng(latLng);
                addMainMarker(latLng);

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_menu:
                mDrawer.openDrawer();
                break;
        }
    }
}


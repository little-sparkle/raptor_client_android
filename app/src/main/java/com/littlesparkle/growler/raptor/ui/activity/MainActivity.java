package com.littlesparkle.growler.raptor.ui.activity;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;

import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;

import com.amap.api.maps2d.MapView;

import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;

import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.littlesparkle.growler.library.activity.HandlerActivity;
import com.littlesparkle.growler.raptor.R;
import com.littlesparkle.growler.raptor.listener.OnLocationGetListener;
import com.littlesparkle.growler.raptor.map.LocationTask;
import com.littlesparkle.growler.raptor.map.MarkerTask;
import com.littlesparkle.growler.raptor.map.SearchTask;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;


public class MainActivity extends HandlerActivity implements
        AMapLocationListener, View.OnClickListener, AMap.OnCameraChangeListener, AMap.OnMapLoadedListener, OnLocationGetListener {

    private MapView mMapView = null;
    private AMap mAMap = null;

    private Drawer mainDrawer = null;
    private Button menuButton = null;
    private TextView mTextViewFrom = null;
    private TextView mTextViewTo = null;

    private Drawer mDrawer = null;
    private IProfile mProfile = null;
    private AccountHeader mAccountHeader = null;

    private SearchTask mSearchTask = null;
    private MarkerTask mMarkerTask = null;
    private LocationTask mLocationTask = null;
    private Marker mPositionMark = null;

    public static final int REQUEST_CODE_DESTINATION = 50;
    public static final int RESULT_CODE_DESTINATION_SUCCESS = 110;
    public static final int RESULT_CODE_DESTINATION_FAILED = 120;

    public static boolean locationAndMove = true;

    private static LatLng centerLatlng = null;

    // 设置侧滑菜单的header
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


//根据屏幕坐标得到经纬度
//    public LatLng pointToLatLng(int ScreenWidth, int ScreenHeight) {
//        Projection projection = mAMap.getProjection();
//        Point point = new Point(ScreenWidth, ScreenHeight);
//        LatLng latLng = projection.fromScreenLocation(point);
//        return latLng;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        initAccountHeader();
        initDrawer();

        System.out.println("MainActivity.onCreate");

    }

    public void moveToLatLng(LatLng latLng) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.changeLatLng(latLng);
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        mAMap.moveCamera(cameraUpdate);
        System.out.println("MainActivity.moveToLatLng");
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
        mTextViewFrom = (TextView) this.findViewById(R.id.tv_from);
        mTextViewTo = (TextView) this.findViewById(R.id.tv_to);
        mTextViewTo.setOnClickListener(this);
        mTextViewFrom.setOnClickListener(this);
        menuButton.setOnClickListener(this);

        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }

        mAMap.setOnCameraChangeListener(this);
        mAMap.setOnMapLoadedListener(this);
        mSearchTask = new SearchTask(mBaseActivity.getApplicationContext());
        mMarkerTask = new MarkerTask(mAMap);
        mLocationTask = LocationTask.getInstance(mAMap, mBaseActivity.getApplicationContext(), this);
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
        System.out.println("MainActivity.onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        locationAndMove = true;
        System.out.println("MainActivity.onPause");
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("MainActivity.onDestroy");
        mLocationTask.stopLocation();
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
//                判断用户是否拖动了地图，如果拖动了，则不进行视图的移动
                if (locationAndMove) {
                    moveToLatLng(latLng);
                    mSearchTask.searchByLatLng(latLng);
                }
                mMarkerTask.addMainMarker(latLng);
                mMarkerTask.addCarMarker(latLng);
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
            case R.id.tv_to:
                Intent intent = new Intent(this, DestinationActivity.class);
                startActivityForResult(intent, REQUEST_CODE_DESTINATION);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_DESTINATION) {
            if (resultCode == RESULT_CODE_DESTINATION_SUCCESS) {
                String address = data.getStringExtra("address");
                mTextViewTo.setText(address);
            } else if (resultCode == RESULT_CODE_DESTINATION_FAILED) {

            }
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
//        设置如果用户拖动了地图则不再次回到定位点
        locationAndMove = false;
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        locationAndMove = false;
//        在地图拖动完成后获取屏幕中心点坐标
        centerLatlng = cameraPosition.target;
        mSearchTask.setOnLocationGetListener(this);
        mSearchTask.searchByLatLng(centerLatlng);
    }

    @Override
    public void onMapLoaded() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.position(new LatLng(0, 0));
        markerOptions
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),
                                R.drawable.icon_loaction_start)));
        mPositionMark = mAMap.addMarker(markerOptions);
        mPositionMark.setPositionByPixels(mMapView.getWidth() / 2, mMapView.getHeight() / 2);
        mLocationTask.LocationByTime(40000);
        System.out.println("MainActivity.onMapLoaded");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("MainActivity.onStop");
        locationAndMove = true;
    }


    @Override
    public void onLocationGet(RegeocodeResult regeocodeResult) {
        String address = regeocodeResult.getRegeocodeAddress().getFormatAddress();
        mTextViewFrom.setText(address);
    }
}


package com.littlesparkle.growler.raptor.ui.activity;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.amap.api.location.AMapLocation;

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
import com.amap.api.services.geocoder.RegeocodeResult;
import com.igexin.sdk.PushManager;
import com.littlesparkle.growler.library.activity.BaseActivity;
import com.littlesparkle.growler.library.activity.BaseFragmentActivity;
import com.littlesparkle.growler.raptor.R;
import com.littlesparkle.growler.raptor.listener.OnLocationGetListener;
import com.littlesparkle.growler.raptor.map.LocationTask;
import com.littlesparkle.growler.raptor.map.MarkerTask;
import com.littlesparkle.growler.raptor.map.SearchTask;
import com.littlesparkle.growler.raptor.ui.views.TimerPickerPopWindow;
import com.littlesparkle.growler.raptor.utils.DensityUtils;
import com.littlesparkle.growler.raptor.utils.SystemStatusManager;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;


public class MainActivity extends BaseActivity implements
        AMapLocationListener, View.OnClickListener, AMap.OnCameraChangeListener, AMap.OnMapLoadedListener, OnLocationGetListener {

    private MapView mMapView = null;
    private AMap mAMap = null;

    private Drawer mainDrawer = null;
    private ImageView menuButton = null;
    private TextView mTextViewFrom = null;
    private TextView mTextViewTo = null;
    private RelativeLayout mRelativeTime = null;
    private Button mButtonCallNow = null;
    private Button mButtonCallLater = null;
    private RadioButton mButtonShunFengChe = null;
    private RadioButton mButtonKuaiChe = null;
    private LinearLayout mLinearNowOrLater = null;
    private TextView mTextViewTimeCheck = null;
    private RelativeLayout mRelativePickContent = null;
    private TimerPickerPopWindow timerPickerPopWindow = null;
//    private View footerView = null;

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
                .withIcon(R.drawable.default_header_menu)
                .withIdentifier(1)
                .withName("引擎");


        mAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.md_white_1000)
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
//                        startActivity(InfoActivity.class);
                        startActivity(new Intent(MainActivity.this, InfoActivity.class));
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        return true;
                    }
                })
                .build();
    }

    //设置侧滑菜单
    private void initDrawer() {
        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
//                .withToolbar(mToolbar)
                .withAccountHeader(mAccountHeader, true)
//                .withFooter(R.layout.drawer_footer)
                .withFooterClickable(true)
                .withStickyFooterShadow(false)
                .withStickyFooter(R.layout.drawer_footer)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withHasStableIds(true)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(R.string.drawer_item_xingcheng)
                                .withIcon(R.drawable.trip)
                                .withIdentifier(R.integer.slide_menu_xingcheng)
                                .withSelectable(false),
                        new PrimaryDrawerItem()
                                .withName(R.string.drawer_item_qianbao)
                                .withIcon(R.drawable.purse)
                                .withIdentifier(R.integer.slide_menu_qianbao)
                                .withSelectable(false),
                        new PrimaryDrawerItem()
                                .withName(R.string.drawer_item_kefu)
                                .withIcon(R.drawable.customer)
                                .withIdentifier(R.integer.slide_menu_kefu)
                                .withSelectable(false),
                        new PrimaryDrawerItem()
                                .withName(R.string.drawer_item_settings)
                                .withIcon(R.drawable.setting)
                                .withIdentifier(R.integer.slide_menu_settings)
                                .withSelectable(false)
                )
                .withSelectedItem(R.integer.slide_menu_null_id)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 0:
//                                startActivity(HistoricalJourneyActivity.class);
                                startActivity(new Intent(MainActivity.this, HistoricalJourneyActivity.class));
                                break;
                            case 1:

                                break;
                            case 2:

                                break;
                            case 3:
//                                startActivity(SettingActivity.class);
                                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                                break;
                        }
                        return false;
                    }
                })
                .build();
        mDrawer.getStickyFooter().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "footer", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemStatusManager tintManager = new SystemStatusManager(this);
            tintManager.setStatusBarTintEnabled(true);
            // 设置状态栏的颜色
            tintManager.setStatusBarTintResource(R.color.primary_light);
            getWindow().getDecorView().setFitsSystemWindows(true);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTranslucentStatus();
        super.onCreate(savedInstanceState);
        PushManager.getInstance().initialize(getApplicationContext());
        mMapView.onCreate(savedInstanceState);
        initAccountHeader();
        initDrawer();
        mLocationTask.LocationByTime(40000);
    }

    public void moveToLatLng(LatLng latLng) {

        CameraUpdate cameraUpdate = CameraUpdateFactory.changeLatLng(latLng);
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        mAMap.moveCamera(cameraUpdate);
//        mAMap.animateCamera(cameraUpdate, 10000, new AMap.CancelableCallback() {
//            @Override
//            public void onFinish() {
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        });

    }


    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;

    }


    @Override
    public void initData() {

    }


    @Override
    public void initView() {
        mMapView = (MapView) findViewById(R.id.mapView);
        menuButton = (ImageView) findViewById(R.id.bt_menu);
        mTextViewFrom = (TextView) findViewById(R.id.tv_from);
        mTextViewTo = (TextView) findViewById(R.id.tv_to);
        mRelativeTime = (RelativeLayout) findViewById(R.id.relativeTime);
        mButtonCallLater = (Button) findViewById(R.id.call_car_later);
        mButtonShunFengChe = (RadioButton) findViewById(R.id.bt_shunfengche);
        mButtonCallNow = (Button) findViewById(R.id.call_car_now);
        mButtonKuaiChe = (RadioButton) findViewById(R.id.bt_kuaiche);
        mLinearNowOrLater = (LinearLayout) findViewById(R.id.linear_Now_Later);
        mTextViewTimeCheck = (TextView) findViewById(R.id.check_time);
        mRelativePickContent = (RelativeLayout) findViewById(R.id.relative_pick_content);
        timerPickerPopWindow = new TimerPickerPopWindow(this, mRelativePickContent);

        mButtonCallNow.setOnClickListener(this);
        mButtonCallLater.setOnClickListener(this);
        mTextViewTo.setOnClickListener(this);
        mTextViewFrom.setOnClickListener(this);
        mButtonShunFengChe.setOnClickListener(this);
        mButtonKuaiChe.setOnClickListener(this);
        menuButton.setOnClickListener(this);
        mTextViewTimeCheck.setOnClickListener(this);

        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
        mAMap.setOnCameraChangeListener(this);
        mAMap.setOnMapLoadedListener(this);
        mSearchTask = new SearchTask(this.getApplicationContext());
        mMarkerTask = new MarkerTask(mAMap);
        mLocationTask = new LocationTask(mAMap, this.getApplicationContext(), this);
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
        locationAndMove = true;
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationTask.stopLocation();
        mMapView.onDestroy();
    }

    //定位回调
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {

        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
//                定位成功回调信息，设置相关消息
                LatLng latLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
//                判断用户是否拖动了地图，如果拖动了，则不进行视图的移动

                if (locationAndMove) {
                    moveToLatLng(latLng);
                    mSearchTask.searchByLatLng(latLng);
                }

//                有待更改，这里是在定位成功后进行加marker
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
//                这里需要进行判断是否登录过了，如果没有，跳转到登陆界面
//                mDrawer.openDrawer();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.tv_to:
                Intent intent = new Intent(this, DestinationActivity.class);
                startActivityForResult(intent, REQUEST_CODE_DESTINATION);
                break;
            case R.id.call_car_now:
                mRelativeTime.setVisibility(View.GONE);
                break;
            case R.id.call_car_later:
                mRelativeTime.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_shunfengche:
                mLinearNowOrLater.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_kuaiche:
                mLinearNowOrLater.setVisibility(View.GONE);
                mRelativeTime.setVisibility(View.GONE);
                break;

            case R.id.check_time:
                timerPickerPopWindow.setWidth(mRelativePickContent.getWidth());
                timerPickerPopWindow.setHeight(DensityUtils.dp2px(this, 300));
                timerPickerPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        backgroundAlpha(1.0f);
                    }
                });
                backgroundAlpha(0.7f);
                timerPickerPopWindow.showAsDropDown(mRelativePickContent);

                break;


        }
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
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
                                R.drawable.icon_location_start)));
        mPositionMark = mAMap.addMarker(markerOptions);
        mPositionMark.setPositionByPixels(mMapView.getWidth() / 2, mMapView.getHeight() / 2);
//        mLocationTask.LocationByTime(40000);

    }

    @Override
    protected void onStop() {
        super.onStop();
        locationAndMove = true;
    }


    @Override
    public void onLocationGet(RegeocodeResult regeocodeResult) {


        String address = null;
        String pois = regeocodeResult.getRegeocodeAddress().getPois().get(0).toString();
        String street = regeocodeResult.getRegeocodeAddress().getStreetNumber().getStreet();
        String streetNumber = regeocodeResult.getRegeocodeAddress().getStreetNumber().getNumber();

        String building = regeocodeResult.getRegeocodeAddress().getBuilding();
        if (!TextUtils.isEmpty(building)) {
            address = street + building;
        } else if (!TextUtils.isEmpty(pois)) {
            address = street + pois;
        } else {
            address = street + streetNumber;
        }

        mTextViewFrom.setText(address);
    }

    @Override
    public void onBackPressed() {

        if (mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else if (timerPickerPopWindow.isShowing()) {
            timerPickerPopWindow.dismiss();
        } else {
            super.onBackPressed();
        }

    }


}


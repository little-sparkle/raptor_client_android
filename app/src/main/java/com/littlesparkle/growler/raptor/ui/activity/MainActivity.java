package com.littlesparkle.growler.raptor.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.littlesparkle.growler.library.http.BaseHttpSubscriber;
import com.littlesparkle.growler.library.http.ErrorResponse;
import com.littlesparkle.growler.library.order.OrderRequest;
import com.littlesparkle.growler.library.order.response.OrderInfoResponse;
import com.littlesparkle.growler.library.order.response.RequestOrderResponse;
import com.littlesparkle.growler.library.preference.PrefHelper;
import com.littlesparkle.growler.library.user.UserManager;
import com.littlesparkle.growler.raptor.R;
import com.littlesparkle.growler.raptor.boradcastreceiver.OrderReceiver;
import com.littlesparkle.growler.raptor.entity.PositionEntity;
import com.littlesparkle.growler.raptor.listener.OnLocationGetListener;
import com.littlesparkle.growler.raptor.map.LocationTask;
import com.littlesparkle.growler.raptor.map.MarkerTask;
import com.littlesparkle.growler.raptor.map.SearchTask;
import com.littlesparkle.growler.raptor.service.OrderInfoService;
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

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


public class MainActivity extends BaseActivity implements
        AMapLocationListener, View.OnClickListener, AMap.OnCameraChangeListener, AMap.OnMapLoadedListener, OnLocationGetListener {

    public static Activity mainActivity;

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
    private RadioButton mButtonTaxi = null;
    private LinearLayout mLinearNowOrLater = null;
    private TextView mTextViewTimeCheck = null;
    private RelativeLayout mRelativePickContent = null;
    private TimerPickerPopWindow timerPickerPopWindow = null;
    private RelativeLayout mRelativeLayoutMoney = null;
    private Button mButtonOrderRequest = null;
    private RelativeLayout mRelativeOrderTitle = null;
    private TextView mTextViewCancelOrder = null;
    private RadioGroup mRadioGroupNaviBar = null;


    //叫车相关参数
    private double src_latitude;
    private double src_longitude;
    private double dest_latitude;
    private double dest_longitude;
    private int car_type = 1;
    private int type = 1;
    private long TimeStamp;


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
    public static final int RESULT_CODE_CANCEL_ORDER = 180;


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
                                startActivity(new Intent(MainActivity.this, HistoricalJourneyActivity.class));
                                break;
                            case 1:
//                                Intent purseIntent = new Intent(MainActivity.this, PurseActivity.class);
//                                purseIntent.putExtra("title", "钱包");
//                                purseIntent.putExtra("url", "http://www.baidu.com");
//                                //还需要传入user_id 和 token
//                                startActivity(purseIntent);
//                                这里是进行调试取消接口用得
                                startActivity(new Intent(MainActivity.this, CancelOrderActivity.class));
                                break;
                            case 2:
                                Intent serviceIntent = new Intent(MainActivity.this, PurseActivity.class);
                                serviceIntent.putExtra("title", "客服");
                                serviceIntent.putExtra("url", "http://www.sina.com");
                                //还需要传入user_id 和 token
                                startActivity(serviceIntent);

                                break;
                            case 3:
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
        EventBus.getDefault().register(this);
        mainActivity = this;
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
        mButtonTaxi = (RadioButton) findViewById(R.id.bt_taxi);
        mLinearNowOrLater = (LinearLayout) findViewById(R.id.linear_Now_Later);
        mTextViewTimeCheck = (TextView) findViewById(R.id.check_time);
        mRelativePickContent = (RelativeLayout) findViewById(R.id.relative_pick_content);
        mRelativeLayoutMoney = (RelativeLayout) findViewById(R.id.relative_money);
        mButtonOrderRequest = (Button) findViewById(R.id.bt_order_request);
        timerPickerPopWindow = new TimerPickerPopWindow(this, mRelativePickContent);
        mRelativeOrderTitle = (RelativeLayout) findViewById(R.id.relative_main_title_order);
        mTextViewCancelOrder = (TextView) findViewById(R.id.tv_cancel_order);
        mRadioGroupNaviBar = (RadioGroup) findViewById(R.id.main_navi_bar);


        mTextViewCancelOrder.setOnClickListener(this);
        mButtonOrderRequest.setOnClickListener(this);
        mButtonCallNow.setOnClickListener(this);
        mButtonCallLater.setOnClickListener(this);
        mTextViewTo.setOnClickListener(this);
        mTextViewFrom.setOnClickListener(this);
        mButtonShunFengChe.setOnClickListener(this);
        mButtonTaxi.setOnClickListener(this);
        menuButton.setOnClickListener(this);
        mTextViewTimeCheck.setOnClickListener(this);

        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
        mAMap.setOnCameraChangeListener(this);
        mAMap.setOnMapLoadedListener(this);
//        mAMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
//            @Override
//            public View getInfoWindow(Marker marker) {
//                return null;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                return null;
//            }
//        });
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
        EventBus.getDefault().unregister(this);
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
                src_latitude = amapLocation.getLatitude();
                src_longitude = amapLocation.getLongitude();

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
                if (UserManager.isSignedIn(MainActivity.this)) {
                    mDrawer.openDrawer();
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }

                break;
            case R.id.tv_to:
                Intent intent = new Intent(this, DestinationActivity.class);
                startActivityForResult(intent, REQUEST_CODE_DESTINATION);
                break;
            case R.id.call_car_now:
                mRelativeTime.setVisibility(View.GONE);
                type = 1;
                break;
            case R.id.call_car_later:
                mRelativeTime.setVisibility(View.VISIBLE);
                type = 2;
                break;
            case R.id.bt_shunfengche:
                mLinearNowOrLater.setVisibility(View.VISIBLE);
                car_type = 2;
                System.out.println(car_type);
                break;
            case R.id.bt_taxi:
                car_type = 1;
                System.out.println(car_type);
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
            case R.id.bt_order_request:
                showProgress();
                requestOrder();
                break;

            case R.id.tv_cancel_order:
                startActivityForResult(new Intent(MainActivity.this, CancelOrderActivity.class), RESULT_CODE_CANCEL_ORDER);
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
                Bundle bundle = data.getBundleExtra("positionEntity");
                PositionEntity positionEntity = (PositionEntity) bundle.getSerializable("positionEntity");
                String address = positionEntity.address;
                mTextViewTo.setText(address);
                if (!TextUtils.isEmpty(mTextViewTo.getText().toString())) {
                    mRelativeLayoutMoney.setVisibility(View.VISIBLE);
                    mButtonOrderRequest.setVisibility(View.VISIBLE);
                    LatLng latLng = new LatLng(positionEntity.latitue, positionEntity.longitude);
                    dest_latitude = positionEntity.latitue;
                    dest_longitude = positionEntity.longitude;
                    mMarkerTask.addMarkerDestination(latLng);
                }

            } else if (resultCode == RESULT_CODE_DESTINATION_FAILED) {

            }
        } else if (requestCode == RESULT_CODE_CANCEL_ORDER) {
            if (resultCode == CancelOrderActivity.CANCEL_SUCCESS) {
                mRelativeOrderTitle.setVisibility(View.GONE);
                mButtonOrderRequest.setVisibility(View.GONE);
                mRelativeLayoutMoney.setVisibility(View.GONE);
                mRadioGroupNaviBar.setVisibility(View.VISIBLE);
                mTextViewTo.setText("");
                mMarkerTask.showMarker();
                mPositionMark.setTitle("");
                stopService(new Intent(MainActivity.this, OrderInfoService.class));
                mPositionMark.hideInfoWindow();
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
        src_latitude = centerLatlng.latitude;
        src_longitude = centerLatlng.longitude;
    }

    @Override
    public void onMapLoaded() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.anchor(0.5f, 1f);
        markerOptions.position(new LatLng(0, 0));
        markerOptions
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),
                                R.drawable.icon_location_start)));
        mPositionMark = mAMap.addMarker(markerOptions);
        mPositionMark.setZIndex(220);
        mPositionMark.setPositionByPixels(mMapView.getWidth() / 2, mMapView.getHeight() / 2);
        mLocationTask.LocationByTime(4000);

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


    public void sendRequestOrder() {
        int userID = PrefHelper.getInteger(this, "user_id");
        new OrderRequest()
                .requestNow(new BaseHttpSubscriber<RequestOrderResponse>() {
                                @Override
                                protected void onError(ErrorResponse error) {
                                    super.onError(error);
                                    Toast.makeText(MainActivity.this, error.data.err_msg, Toast.LENGTH_SHORT).show();
                                    dismissProgress();
                                }

                                @Override
                                public void onNext(RequestOrderResponse requestOrderResponse) {
                                    System.out.println(requestOrderResponse.toString());
                                    dismissProgress();
                                    //完成请求后，根据请求状态改变页面
                                    mRelativeOrderTitle.setVisibility(View.VISIBLE);
                                    mRadioGroupNaviBar.setVisibility(View.GONE);
                                    mButtonOrderRequest.setVisibility(View.GONE);
                                    int orderId = requestOrderResponse.data.order.order_id;
                                    mPositionMark.setTitle("正为您寻找车辆");
                                    mPositionMark.showInfoWindow();
                                    if (type == 2) {
                                        PrefHelper.setInteger(MainActivity.this, "appointment_order_id", orderId);
                                    } else {
                                        PrefHelper.setInteger(MainActivity.this, "order_id", orderId);
                                    }
                                    mMarkerTask.hideMarker();

                                    startService(new Intent(MainActivity.this, OrderInfoService.class));
                                }
                            },
                        userID,
                        UserManager.getToken(this),
                        car_type,
                        src_latitude, src_longitude,
                        dest_latitude, dest_longitude,
                        type,
                        TimeStamp);
    }


    public void requestOrder() {

        if (car_type == 2 && type == 2) {
            if (timerPickerPopWindow.getAppointmentTime() != 0) {
                TimeStamp = timerPickerPopWindow.getAppointmentTime();
                sendRequestOrder();
            } else {
                dismissProgress();
                Toast.makeText(MainActivity.this, "请选择出发时间~", Toast.LENGTH_SHORT).show();
                timerPickerPopWindow.showAsDropDown(mRelativePickContent);
                return;
            }

        } else {
            TimeStamp = System.currentTimeMillis();
            sendRequestOrder();
        }


    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onOrderResponse(OrderInfoResponse orderInfoResponse) {
        switch (orderInfoResponse.data.order.status_code) {
            case OrderReceiver.is_called_car:
                break;
            case OrderReceiver.has_received_orders:

                break;
            case OrderReceiver.has_orders:

                break;
            case OrderReceiver.arrive_at_the_boarding:

                break;
            case OrderReceiver.began:

                break;
            case OrderReceiver.Trip_over_waiting_for_payment:

                break;
            case OrderReceiver.Trip_to_suspend:

                break;
            case OrderReceiver.Trip_over_has_been_payment:

                break;
            case OrderReceiver.Trip_accident_terminates:

                break;
            case OrderReceiver.No_driver_response:

                break;
            case OrderReceiver.Drivers_to_cancel_the_order:

                break;
            case OrderReceiver.Passengers_to_cancel_the_order:

                break;
        }
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


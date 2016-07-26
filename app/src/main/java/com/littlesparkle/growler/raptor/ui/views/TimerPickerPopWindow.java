package com.littlesparkle.growler.raptor.ui.views;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.littlesparkle.growler.raptor.R;
import com.littlesparkle.growler.raptor.ui.activity.InfoActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * Created by dell on 2016/7/11.
 */
public class TimerPickerPopWindow extends PopupWindow implements View.OnClickListener {

    private Activity mActivity = null;
    private View mContentView = null;
    private LayoutInflater mLayoutInflater = null;

    private TextView mTextViewCancel = null;
    private TextView mTextViewOk = null;
    private NumberPickerView mPickerDate = null;
    private NumberPickerView mPickerHour = null;
    private NumberPickerView mPickerMinute = null;

    private SimpleDateFormat format = null;

    private String[] date = null;
    private String[] hour = null;
    private String[] minutes = null;

    private String mStringDate;
    private String mStringHour;
    private String mStringMinutes;

    private String mStringNowYear;
    private String mStringNowMonth;
    private String mStringNowDate;
    private String mStringNowHour;
    private String mStringNowMinutes;

    public TimerPickerPopWindow(Activity activity, ViewGroup viewGroup) {
        mActivity = activity;
        mLayoutInflater = mActivity.getLayoutInflater();
        mContentView = mLayoutInflater.inflate(R.layout.timer_picker_popwindow, viewGroup, false);
        this.setContentView(mContentView);
        mContentView.setFocusable(true);
        mContentView.setFocusableInTouchMode(true);
        mContentView.requestFocus();
        this.setBackgroundDrawable(new BitmapDrawable());
        mContentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    TimerPickerPopWindow.this.dismiss();
                }
                return true;
            }
        });
        this.setOutsideTouchable(true);


        date = mActivity.getResources().getStringArray(R.array.date_display);
        hour = mActivity.getResources().getStringArray(R.array.hour_display);
        minutes = mActivity.getResources().getStringArray(R.array.minute_display);

        mTextViewCancel = (TextView) mContentView.findViewById(R.id.tv_cancel_timer_picker);
        mTextViewOk = (TextView) mContentView.findViewById(R.id.tv_ok_timer_picker);
        mPickerDate = (NumberPickerView) mContentView.findViewById(R.id.picker_date);
        mPickerHour = (NumberPickerView) mContentView.findViewById(R.id.picker_hour);
        mPickerMinute = (NumberPickerView) mContentView.findViewById(R.id.picker_minute);
        mTextViewCancel.setOnClickListener(this);
        mTextViewOk.setOnClickListener(this);
        mPickerDate.setOnScrollListener(new NumberPickerView.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPickerView view, int scrollState) {
                if (scrollState != 2) {
                    mStringDate = date[scrollState];
                }
            }
        });
        mPickerHour.setOnScrollListener(new NumberPickerView.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPickerView view, int scrollState) {
                mStringHour = hour[scrollState];
            }
        });
        mPickerMinute.setOnScrollListener(new NumberPickerView.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPickerView view, int scrollState) {
                mStringMinutes = minutes[scrollState];
            }
        });


        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long nowTime = System.currentTimeMillis();
        String mStringNow = format.format(nowTime);
        Date nowDate = null;
        try {
            nowDate = format.parse(mStringNow);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mStringNowYear = nowDate.getYear() + "";
        mStringNowMonth = nowDate.getMonth() + 1 + "";
        mStringNowDate = nowDate.getDate() + "";
        mStringNowHour = nowDate.getHours() + "";
        mStringNowMinutes = nowDate.getMinutes() + "";


        mPickerDate.refreshByNewDisplayedValues(date);
        mPickerDate.setMinValue(0);
        mPickerDate.setMaxValue(1);
        mPickerDate.setValue(0);

        mPickerHour.refreshByNewDisplayedValues(hour);
        mPickerHour.setMinValue(0);
        mPickerHour.setMaxValue(23);
        mPickerHour.setValue(0);

        mPickerMinute.refreshByNewDisplayedValues(minutes);
        mPickerMinute.setMinValue(0);
        mPickerMinute.setMaxValue(5);
        mPickerMinute.setValue(0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel_timer_picker:
                TimerPickerPopWindow.this.dismiss();
                break;
            case R.id.tv_ok_timer_picker:

        }
    }
}

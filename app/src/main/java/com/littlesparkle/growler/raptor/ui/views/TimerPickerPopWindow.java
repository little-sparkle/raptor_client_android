package com.littlesparkle.growler.raptor.ui.views;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.littlesparkle.growler.raptor.R;
import com.littlesparkle.growler.raptor.ui.activity.InfoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/7/11.
 */
public class TimerPickerPopWindow extends PopupWindow {

    private Activity mActivity = null;
    private View mContentView = null;
    private LayoutInflater mLayoutInflater = null;

    private TextView mTextViewCancel = null;
    private TextView mTextViewOk = null;
    private Picker mPickerDate = null;
    private Picker mPickerHour = null;
    private Picker mPickerMinute = null;

    private List<String> date = new ArrayList<>();
    private List<String> hour = new ArrayList<>();
    private List<String> minute = new ArrayList<>();

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
        mTextViewCancel = (TextView) mContentView.findViewById(R.id.tv_cancel_timer_picker);
        mTextViewOk = (TextView) mContentView.findViewById(R.id.tv_ok_timer_picker);
        mPickerDate = (Picker) mContentView.findViewById(R.id.picker_date);
        mPickerHour = (Picker) mContentView.findViewById(R.id.picker_hour);
        mPickerMinute = (Picker) mContentView.findViewById(R.id.picker_minute);

        date.add("今天");
        date.add("明天");
        for (int i = 0; i <= 23; i++) {
            hour.add(i + "点");
        }
        for (int i = 0; i <= 5; i++) {
            minute.add(i + "0" + "分");
        }
        mPickerDate.setData(date);
        mPickerHour.setData(hour);
        mPickerMinute.setData(minute);

        mPickerDate.setOnSelectListener(new Picker.onSelectListener() {
            @Override
            public void onSelect(String text) {

            }
        });

        mPickerHour.setOnSelectListener(new Picker.onSelectListener() {
            @Override
            public void onSelect(String text) {

            }
        });

        mPickerMinute.setOnSelectListener(new Picker.onSelectListener() {
            @Override
            public void onSelect(String text) {

            }
        });

    }
}

package com.littlesparkle.growler.raptor.ui.activity;

import android.os.Message;
import android.widget.ListView;

import com.littlesparkle.growler.library.activity.BaseActivity;
import com.littlesparkle.growler.library.activity.BaseFragmentActivity;
import com.littlesparkle.growler.library.activity.HandlerActivity;
import com.littlesparkle.growler.raptor.R;
import com.littlesparkle.growler.raptor.entity.JourneyEntity;
import com.littlesparkle.growler.raptor.ui.adapter.ItemLvJourneyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/7/7.
 */
public class HistoricalJourneyActivity extends BaseActivity {

    private ListView mListView = null;
    private ItemLvJourneyAdapter mItemLvJourneyAdapter = null;
    private static List<JourneyEntity> mJourneyEntities = null;

    static {
        mJourneyEntities = new ArrayList<>();
        mJourneyEntities.add(new JourneyEntity("燕郊华北科技学院北门", "燕郊火车站", "已完成", "今天6：00"));
        mJourneyEntities.add(new JourneyEntity("燕郊华北科技学院北门", "燕郊火车站", "已完成", "昨天7：20"));
        mJourneyEntities.add(new JourneyEntity("燕郊华北科技学院北门", "燕郊火车站", "已完成", "昨天8：00"));
        mJourneyEntities.add(new JourneyEntity("燕郊华北科技学院北门", "燕郊火车站", "已完成", "昨天6：50"));
        mJourneyEntities.add(new JourneyEntity("燕郊华北科技学院北门", "燕郊火车站", "已完成", "昨天6：30"));
        mJourneyEntities.add(new JourneyEntity("燕郊华北科技学院北门", "燕郊火车站", "已完成", "前天6：10"));
        mJourneyEntities.add(new JourneyEntity("燕郊华北科技学院北门", "燕郊火车站", "已完成", "前天6：00"));
        mJourneyEntities.add(new JourneyEntity("燕郊华北科技学院北门", "燕郊火车站", "已完成", "前天6：00"));
        mJourneyEntities.add(new JourneyEntity("燕郊华北科技学院北门", "燕郊火车站", "已完成", "前天6：00"));
        mJourneyEntities.add(new JourneyEntity("燕郊华北科技学院北门", "燕郊火车站", "已完成", "前天6：00"));

    }



    @Override
    public int getLayoutResId() {
        return R.layout.activity_historical_journey;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mListView = (ListView) this.findViewById(R.id.lv_history);
        if (mItemLvJourneyAdapter == null) {
            mItemLvJourneyAdapter = new ItemLvJourneyAdapter(this, mJourneyEntities);
        }
        mListView.setAdapter(mItemLvJourneyAdapter);
    }
}

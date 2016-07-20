package com.littlesparkle.growler.raptor.ui.activity;

import android.widget.ListView;

import com.littlesparkle.growler.library.activity.BaseActivity;
import com.littlesparkle.growler.library.activity.BaseTitleBarActivity;
import com.littlesparkle.growler.raptor.R;
import com.littlesparkle.growler.raptor.entity.JourneyEntity;
import com.littlesparkle.growler.raptor.ui.adapter.ItemLvJourneyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/7/7.
 */
public class HistoricalJourneyActivity extends BaseTitleBarActivity {

    private ListView mListView = null;
    private ItemLvJourneyAdapter mItemLvJourneyAdapter = null;
    private static List<JourneyEntity> mJourneyEntities = null;

    static {
        mJourneyEntities = new ArrayList<>();
        mJourneyEntities.add(new JourneyEntity("燕郊华北科技学院北门", "燕郊火车站", "已完成", "今天6：00"));
        mJourneyEntities.add(new JourneyEntity("冠捷科技", "王府井APM", "已完成", "昨天7：20"));
        mJourneyEntities.add(new JourneyEntity("京旺家园", "世纪互联", "已完成", "昨天8：00"));
        mJourneyEntities.add(new JourneyEntity("燕郊华北科技学院北门", "燕郊火车站", "已完成", "昨天6：50"));
        mJourneyEntities.add(new JourneyEntity("酒仙桥东路北口", "798艺术区", "已完成", "昨天16：30"));
        mJourneyEntities.add(new JourneyEntity("华北科技学院", "燕郊火车站", "已完成", "前天6：10"));
        mJourneyEntities.add(new JourneyEntity("沃尔玛", "中燕小区", "已完成", "前天19：00"));
        mJourneyEntities.add(new JourneyEntity("世纪互联", "国贸", "已完成", "前天18：24"));
        mJourneyEntities.add(new JourneyEntity("燕郊文化大厦", "燕郊火车站", "已完成", "前天12：37"));
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
        super.initView();
        mListView = (ListView) findViewById(R.id.lv_history);
        if (mItemLvJourneyAdapter == null) {
            mItemLvJourneyAdapter = new ItemLvJourneyAdapter(this, R.layout.item_lv_journey, mJourneyEntities);
        }
        mListView.setAdapter(mItemLvJourneyAdapter);
    }

    @Override
    protected int getTitleResourceId() {
        return R.string.journey;
    }
}

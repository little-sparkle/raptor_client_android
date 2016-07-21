package com.littlesparkle.growler.raptor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.littlesparkle.growler.library.activity.BaseActivity;
import com.littlesparkle.growler.library.activity.BaseFragmentActivity;
import com.littlesparkle.growler.library.activity.HandlerActivity;
import com.littlesparkle.growler.raptor.R;
import com.littlesparkle.growler.raptor.entity.PositionEntity;
import com.littlesparkle.growler.raptor.map.PoiSearchTask;
import com.littlesparkle.growler.raptor.map.SearchTask;
import com.littlesparkle.growler.raptor.ui.adapter.ItemLvDestinationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/7/6.
 */
public class DestinationActivity extends BaseActivity implements TextWatcher, View.OnClickListener, AdapterView.OnItemClickListener {
    private EditText mEditText = null;
    private TextView bt_search = null;
    private ListView mListView = null;
    private List<String> entities = new ArrayList<>();
    private ItemLvDestinationAdapter mItemLvDestinationAdapter = null;
    private PoiSearchTask mPoiSearchTask = null;
    private List<String> poiEntities = null;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_destination;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView() {
        super.initView();
        mEditText = (EditText) findViewById(R.id.EditText_destination);
        mEditText.addTextChangedListener(this);
        bt_search = (TextView) findViewById(R.id.search_destination);
        bt_search.setOnClickListener(this);

        mListView = (ListView) findViewById(R.id.lv_destination);
        mListView.setOnItemClickListener(this);
        if (mItemLvDestinationAdapter == null) {
            mItemLvDestinationAdapter = new ItemLvDestinationAdapter(this, R.layout.item_lv_destination, entities);
        }
        mPoiSearchTask = new PoiSearchTask(this, mItemLvDestinationAdapter);
        mListView.setAdapter(mItemLvDestinationAdapter);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_destination:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                mPoiSearchTask.search(mEditText.getText().toString(), "beijing");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        List<PositionEntity> positionEntities = mPoiSearchTask.entities;

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("positionEntity", positionEntities.get(position));
        intent.putExtra("positionEntity", bundle);
        setResult(MainActivity.RESULT_CODE_DESTINATION_SUCCESS, intent);
        this.finish();
    }
}

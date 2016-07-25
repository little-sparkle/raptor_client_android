package com.littlesparkle.growler.raptor.ui.activity;

import android.content.Entity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.littlesparkle.growler.library.activity.CancelActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/7/25.
 */
public class CancelOrderActivity extends CancelActivity {

    private static List<String> entities = new ArrayList<>();

    static {
        entities.add("理由1");
        entities.add("理由2");
        entities.add("理由3");
        entities.add("理由4");
        entities.add("理由5");
        entities.add("理由6");
        entities.add("理由7");
        entities.add("理由8");
    }

    @Override
    public List<String> setReasonList() {
        return entities;
    }

    @Override
    protected void onCancelButtonClick() {
        Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCancelLvItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, entities.get(position), Toast.LENGTH_SHORT).show();
    }
}

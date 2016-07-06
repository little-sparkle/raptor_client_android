package com.littlesparkle.growler.raptor.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.littlesparkle.growler.raptor.R;

import java.util.List;

public class ItemLvDestinationAdapter extends BaseAdapter {

    private List<String> mEntities;

    private Activity context;
    private LayoutInflater layoutInflater;

    public ItemLvDestinationAdapter(Activity context, List<String> mEntities) {
        this.context = context;
        this.mEntities = mEntities;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setEntities(List<String> entities) {
        mEntities = entities;
    }

    @Override
    public int getCount() {
        return mEntities.size();
    }

    @Override
    public String getItem(int position) {
        return mEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_lv_destination, parent, false);
            ViewHolder viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }
        initializeViews((String) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(String entity, ViewHolder holder) {
        holder.tvItemDestination.setText(entity);
    }

    protected class ViewHolder {
        private TextView tvItemDestination;

        public ViewHolder(View view) {
            tvItemDestination = (TextView) view.findViewById(R.id.tv_item_destination);
        }
    }
}


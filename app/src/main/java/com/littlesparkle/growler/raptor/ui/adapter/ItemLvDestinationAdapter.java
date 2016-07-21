package com.littlesparkle.growler.raptor.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.littlesparkle.growler.library.base.NormalBaseAdapter;
import com.littlesparkle.growler.raptor.R;

import java.util.List;

/**
 * Created by dell on 2016/7/18.
 */
public class ItemLvDestinationAdapter extends NormalBaseAdapter<String, ItemLvDestinationAdapter.DestinationViewHolder> {



    public ItemLvDestinationAdapter(Context context, @LayoutRes int resource, List<String> dataList) {
        super(context, resource, dataList);
    }



    @Override
    protected DestinationViewHolder onCreateViewHolder(ViewGroup parent, View convertView) {
        return  new DestinationViewHolder(convertView);
    }

    @Override
    protected void onBindViewHolder(DestinationViewHolder holder, String item, int position) {
        holder.tvItemDestination.setText(item);
    }

    public class DestinationViewHolder extends NormalBaseAdapter.ViewHolder {
        private TextView tvItemDestination;

        public DestinationViewHolder(View v) {
            super(v);
            tvItemDestination = (TextView) v.findViewById(R.id.tv_item_destination);
        }
    }
}

package com.littlesparkle.growler.raptor.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.littlesparkle.growler.library.base.NormalBaseAdapter;
import com.littlesparkle.growler.raptor.R;
import com.littlesparkle.growler.raptor.entity.JourneyEntity;

import java.util.List;

/**
 * Created by dell on 2016/7/18.
 */
public class ItemLvJourneyAdapter extends NormalBaseAdapter<JourneyEntity, ItemLvJourneyAdapter.JourneyViewHolder> {
    public ItemLvJourneyAdapter(Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public ItemLvJourneyAdapter(Context context, @LayoutRes int resource, List dataList) {
        super(context, resource, dataList);
    }

    @Override
    protected JourneyViewHolder onCreateViewHolder(ViewGroup parent, View convertView) {
        return new JourneyViewHolder(convertView);
    }

    @Override
    protected void onBindViewHolder(JourneyViewHolder holder, JourneyEntity item, int position) {
        holder.tvTimeJourney.setText(item.getJourneyTime());
        holder.tvJourneyFrom.setText(item.getJourneyFrom());
        holder.tvJourneyTo.setText(item.getJourneyTO());
        holder.stateJourney.setText(item.getJourneyState());
    }


    public class JourneyViewHolder extends NormalBaseAdapter.ViewHolder {
        private TextView tvTimeJourney;
        private ImageView imgvJourneyFrom;
        private ImageView imgvJourneyTo;
        private TextView tvJourneyFrom;
        private TextView tvJourneyTo;
        private ImageView mImageView;
        private TextView stateJourney;

        public JourneyViewHolder(View v) {
            super(v);
            tvTimeJourney = (TextView) v.findViewById(R.id.tv_time_journey);
            imgvJourneyFrom = (ImageView) v.findViewById(R.id.imgv_journey_from);
            imgvJourneyTo = (ImageView) v.findViewById(R.id.imgv_journey_to);
            tvJourneyFrom = (TextView) v.findViewById(R.id.tv_journey_from);
            tvJourneyTo = (TextView) v.findViewById(R.id.tv_journey_to);
            mImageView = (ImageView) v.findViewById(R.id.button);
            stateJourney = (TextView) v.findViewById(R.id.state_journey);
        }
    }

}

package com.littlesparkle.growler.raptor.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.littlesparkle.growler.raptor.R;
import com.littlesparkle.growler.raptor.entity.JourneyEntity;

import java.util.List;

/**
 * Created by dell on 2016/7/7.
 */
public class ItemLvJourneyAdapter extends BaseAdapter {

    private List<JourneyEntity> mEntities;

    private Activity context;
    private LayoutInflater layoutInflater;

    public ItemLvJourneyAdapter(Activity context, List<JourneyEntity> mEntities) {
        this.context = context;
        this.mEntities = mEntities;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mEntities.size();
    }

    @Override
    public JourneyEntity getItem(int position) {
        return mEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_lv_journey, parent, false);
            ViewHolder viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }
        initializeViews((JourneyEntity) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(JourneyEntity entity, ViewHolder holder) {
        holder.tvTimeJourney.setText(entity.getJourneyTime());
        holder.tvJourneyFrom.setText(entity.getJourneyFrom());
        holder.tvJourneyTo.setText(entity.getJourneyTO());
        holder.stateJourney.setText(entity.getJourneyState());
    }

    protected class ViewHolder {
        private TextView tvTimeJourney;
        private ImageView imgvJourneyFrom;
        private ImageView imgvJourneyTo;
        private TextView tvJourneyFrom;
        private TextView tvJourneyTo;
        private Button button;
        private TextView stateJourney;


        public ViewHolder(View view) {
            tvTimeJourney = (TextView) view.findViewById(R.id.tv_time_journey);
            imgvJourneyFrom = (ImageView) view.findViewById(R.id.imgv_journey_from);
            imgvJourneyTo = (ImageView) view.findViewById(R.id.imgv_journey_to);
            tvJourneyFrom = (TextView) view.findViewById(R.id.tv_journey_from);
            tvJourneyTo = (TextView) view.findViewById(R.id.tv_journey_to);
            button = (Button) view.findViewById(R.id.button);
            stateJourney = (TextView) view.findViewById(R.id.state_journey);
        }
    }

}

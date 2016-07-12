package com.littlesparkle.growler.raptor.map;

import android.content.Context;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.littlesparkle.growler.raptor.entity.PositionEntity;
import com.littlesparkle.growler.raptor.ui.adapter.ItemLvDestinationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/7/6.
 */
public class PoiSearchTask implements PoiSearch.OnPoiSearchListener {

    private Context mContext = null;
    private ItemLvDestinationAdapter mItemLvDestinationAdapter = null;

    public PoiSearchTask(Context context, ItemLvDestinationAdapter itemLvDestinationAdapter) {
        mContext = context;
        mItemLvDestinationAdapter = itemLvDestinationAdapter;
    }

    public void search(String keyWord, String city) {
        PoiSearch.Query query = new PoiSearch.Query(keyWord, "", city);
        query.setPageSize(10);
        query.setPageNum(0);
        PoiSearch poiSearch = new PoiSearch(mContext, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == 1000 && poiResult != null) {
            ArrayList<PoiItem> pois = poiResult.getPois();
            if (pois == null) {
                return;
            }
            List<String> entities = new ArrayList<>();


            for (PoiItem poiItem : pois) {
                PositionEntity entity = new PositionEntity(poiItem.getLatLonPoint().getLatitude(),
                        poiItem.getLatLonPoint().getLongitude(), poiItem.getTitle()
                        , poiItem.getCityName());
                entities.add(entity.address);
                System.out.println(entity.address);
            }
            mItemLvDestinationAdapter.setEntities(entities);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}

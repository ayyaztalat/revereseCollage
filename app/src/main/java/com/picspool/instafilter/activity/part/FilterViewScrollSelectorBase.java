package com.picspool.instafilter.activity.part;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;
import com.picspool.lib.resource.view.DMWBMaterialUrlInterface;
import com.picspool.lib.resource.view.DMWBOnResourceChangedListener;
import com.picspool.lib.resource.widget.DMWBHorizontalListView;
import com.picspool.lib.resource.widget.DMWBScrollBarArrayAdapter;

/* loaded from: classes3.dex */
public class FilterViewScrollSelectorBase extends FrameLayout implements AdapterView.OnItemClickListener {
    private DMWBManager mManager;
    protected DMWBOnResourceChangedListener mResListener;
    protected DMWBScrollBarArrayAdapter scrollDataAdapter;
    protected DMWBHorizontalListView scrollView;

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
    }

    protected String resultToUrl(String str) {
        return str;
    }

    public void setWBMaterialUrlInterface(DMWBMaterialUrlInterface dMWBMaterialUrlInterface) {
    }

    public FilterViewScrollSelectorBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setDataAdapter(DMWBManager dMWBManager) {
        this.mManager = dMWBManager;
        int count = dMWBManager.getCount();
        DMWBRes[] dMWBResArr = new DMWBRes[count];
        for (int i = 0; i < count; i++) {
            dMWBResArr[i] = this.mManager.getRes(i);
        }
        DMWBScrollBarArrayAdapter dMWBScrollBarArrayAdapter = new DMWBScrollBarArrayAdapter(getContext(), dMWBResArr);
        this.scrollDataAdapter = dMWBScrollBarArrayAdapter;
        dMWBScrollBarArrayAdapter.setImageBorderViewLayout(40, 40);
        this.scrollView.setAdapter((ListAdapter) this.scrollDataAdapter);
        this.scrollView.setOnItemClickListener(this);
    }

    public void setWBOnResourceChangedListener(DMWBOnResourceChangedListener dMWBOnResourceChangedListener) {
        this.mResListener = dMWBOnResourceChangedListener;
    }
}

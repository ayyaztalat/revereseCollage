package com.picspool.snappic.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.snappic.adapter.CSBottomRecyclerViewAdapter;
import com.picspool.snappic.manager.CSBottomBarManager;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSBottomBar extends FrameLayout {
    private CSBottomRecyclerViewAdapter bottomRecyclerViewAdapter;
    private Context mContext;
    private RecyclerView recyclerView;

    public CSBottomBar(Context context) {
        super(context);
        initView(context);
    }

    public CSBottomBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public CSBottomBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.bottombar_mainview, (ViewGroup) this, true);
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    public void setData(CSBottomBarManager.BottomBarEntity bottomBarEntity) {
        List<DMWBRes> resList = new CSBottomBarManager(this.mContext).getResList();
        if (bottomBarEntity != null) {
            resList.add(bottomBarEntity);
        }
        CSBottomRecyclerViewAdapter cSBottomRecyclerViewAdapter = new CSBottomRecyclerViewAdapter(this.mContext, resList);
        this.bottomRecyclerViewAdapter = cSBottomRecyclerViewAdapter;
        this.recyclerView.setAdapter(cSBottomRecyclerViewAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.HORIZONTAL, false));
    }

    public CSBottomRecyclerViewAdapter getBottomRecyclerViewAdapter() {
        return this.bottomRecyclerViewAdapter;
    }
}

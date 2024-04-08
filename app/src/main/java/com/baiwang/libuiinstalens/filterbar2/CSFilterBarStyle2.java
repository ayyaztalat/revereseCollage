package com.baiwang.libuiinstalens.filterbar2;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;

import java.util.List;

/* loaded from: classes.dex */
public class CSFilterBarStyle2 extends FrameLayout {
    private static final String TAG = "CSFilterBarStyle2";
    public CSFilterBarStyle2Manager style2Manager;
    private List<DMWBRes> DMWBResGroupList;
    private List<DMWBRes> DMWBResList;
    private CSFilterBarViewAdapter filterBarViewAdapter;
    private CSFilterBarViewGroupAdapter filterBarViewGroupAdapter;
    private boolean isListScrollDragging;
    private LinearLayoutManager linearLayoutGroupManager;
    private CSCenterLayoutManager linearLayoutManager;
    private Context mContext;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewGroup;
    private Bitmap srcbmp;

    public CSFilterBarStyle2(Context context, Bitmap bitmap) {
        super(context);
        mContext = context;
        srcbmp = bitmap;
        initView();
    }

    private void initView() {
        ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_bar_filter_style2, (ViewGroup) this, true);
        recyclerViewGroup = (RecyclerView) findViewById(R.id.recyclerview_2);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_1);
        style2Manager = new CSFilterBarStyle2Manager(mContext, srcbmp);
        DMWBResGroupList = style2Manager.getFilterGroupList();
        DMWBResList = style2Manager.getFilterList();
        filterBarViewGroupAdapter = new CSFilterBarViewGroupAdapter(mContext, DMWBResGroupList);
        filterBarViewAdapter = new CSFilterBarViewAdapter(mContext, DMWBResList, srcbmp);
        linearLayoutGroupManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        recyclerViewGroup.setAdapter(filterBarViewGroupAdapter);
        recyclerViewGroup.setLayoutManager(linearLayoutGroupManager);
        linearLayoutManager = new CSCenterLayoutManager(mContext, 0, false);
        recyclerView.setAdapter(filterBarViewAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        filterBarViewGroupAdapter.setOnBarViewItemClikListener(i -> {
            CSFilterGroup cSFilterGroup = (CSFilterGroup) DMWBResGroupList.get(i);
            if (isSmoothScroll(DMWBResGroupList, filterBarViewGroupAdapter.getSelectedPos(), i)) {
                linearLayoutManager.smoothScrollToPosition(recyclerView, null, cSFilterGroup.getFirstItemIndex());
            } else {
                linearLayoutManager.scrollToPosition(cSFilterGroup.getFirstItemIndex());
            }
            isListScrollDragging = false;
            cSFilterGroup.setHasShow(true);
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.baiwang.libuiinstalens.filterbar2.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 1) {
                    isListScrollDragging = true;
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                if (isListScrollDragging) {
                    int findLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    for (int i3 = 0; i3 < DMWBResGroupList.size(); i3++) {
                        CSFilterGroup cSFilterGroup = (CSFilterGroup) DMWBResGroupList.get(i3);
                        if (findLastVisibleItemPosition <= (cSFilterGroup.getFirstItemIndex() + cSFilterGroup.getFilterResList().size()) - 1) {
                            if (i3 != filterBarViewGroupAdapter.getSelectedPos()) {
                                recyclerViewGroup.smoothScrollToPosition(i3);
                                ((CSFilterGroup) DMWBResGroupList.get(i3)).setHasShow(true);
                                filterBarViewGroupAdapter.setSelectedPos(i3);
                                return;
                            }
                            return;
                        }
                    }
                }
            }
        });
    }

    public CSFilterBarViewAdapter getFilterBarViewAdapter() {
        return filterBarViewAdapter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSmoothScroll(List<DMWBRes> list, int i, int i2) {
        if (i == i2) {
            return false;
        }
        if (i < i2) {
            while (i < i2) {
                if (!((CSFilterGroup) list.get(i)).smoothScroll()) {
                    return false;
                }
                i++;
            }
            return true;
        }
        while (i2 < i) {
            if (!((CSFilterGroup) list.get(i2)).smoothScroll()) {
                return false;
            }
            i2++;
        }
        return true;
    }
}

package com.picspool.libfuncview.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.libfuncview.res.CSBarViewControlListener;
import com.picspool.libfuncview.utils.CSObjectAnimatorUtils;
import java.util.List;
import com.picspool.instafilter.GPUFilter;
import com.picspool.lib.filter.gpu.GPUImageView;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.listener.OnPostFilteredListener;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSFilterBarStyle2 extends FrameLayout {
    private static final String TAG = "CSFilterBarStyle2";
    private CSFilterBarViewAdapter filterBarViewAdapter;
    private CSFilterBarViewGroupAdapter filterBarViewGroupAdapter;
    private GPUImageView gpuImageView;
    private boolean isListScrollDragging;
    private LinearLayoutManager linearLayoutGroupManager;
    private CSCenterLayoutManager linearLayoutManager;
    private Context mContext;
    private CSBarViewControlListener mListener;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewGroup;
    private Bitmap srcbmp;
    private CSFilterBarStyle2Manager style2Manager;
    private List<DMWBRes> wbResGroupList;
    private List<DMWBRes> wbResList;

    public void setBarViewControlListener(CSBarViewControlListener cSBarViewControlListener) {
        this.mListener = cSBarViewControlListener;
    }

    public CSFilterBarStyle2(Context context, Bitmap bitmap) {
        super(context);
        this.mContext = context;
        this.srcbmp = bitmap;
        initView();
    }

    private void initView() {
        ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_bar_filter_style2, (ViewGroup) this, true);
        setFocusableInTouchMode(true);
        requestFocusFromTouch();
        findViewById(R.id.ly_cancel).setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.filter.CSFilterBarStyle2.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSFilterBarStyle2.this.mListener != null) {
                    CSFilterBarStyle2.this.mListener.onCancel();
                }
            }
        });
        findViewById(R.id.ly_confirm).setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.filter.CSFilterBarStyle2.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSFilterBarStyle2.this.mListener == null || CSFilterBarStyle2.this.srcbmp == null || CSFilterBarStyle2.this.srcbmp.isRecycled() || CSFilterBarStyle2.this.gpuImageView == null) {
                    return;
                }
                GPUFilter.asyncFilterForFilter(CSFilterBarStyle2.this.srcbmp, CSFilterBarStyle2.this.gpuImageView.getFilter(), new OnPostFilteredListener() { // from class: com.picspool.libfuncview.filter.CSFilterBarStyle2.2.1
                    @Override // com.picspool.lib.filter.listener.OnPostFilteredListener
                    public void postFiltered(Bitmap bitmap) {
                        CSFilterBarStyle2.this.mListener.onOk(bitmap);
                    }
                });
            }
        });
        if (this.srcbmp == null) {
            return;
        }
        GPUImageView gPUImageView = (GPUImageView) findViewById(R.id.gpu_imageview);
        this.gpuImageView = gPUImageView;
        gPUImageView.setImage(this.srcbmp);
        this.gpuImageView.setBackgroundColor(this.mContext.getResources().getColor(R.color.libui_gpubg_grey));
        int screenWidth = (int) (DMScreenInfoUtil.screenWidth(this.mContext) - (this.mContext.getResources().getDimension(R.dimen.libui_barmainview_margin) * 2.0f));
        int screenHeight = (int) (((DMScreenInfoUtil.screenHeight(this.mContext) - (this.mContext.getResources().getDimension(R.dimen.libui_barmainview_margin) * 2.0f)) - this.mContext.getResources().getDimension(R.dimen.libui_func_bar_height)) - this.mContext.getResources().getDimension(R.dimen.libui_bottom_title_bar_height));
        if (screenWidth <= 0 || screenHeight <= 0) {
            return;
        }
        float f = screenWidth;
        float f2 = screenHeight;
        float width = this.srcbmp.getWidth() / this.srcbmp.getHeight();
        if ((f / f2) - width > 0.01d) {
            this.gpuImageView.getLayoutParams().width = (int) (f2 * width);
        } else {
            this.gpuImageView.getLayoutParams().height = (int) (f / width);
        }
        this.recyclerViewGroup = (RecyclerView) findViewById(R.id.recyclerview_2);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview_1);
        CSFilterBarStyle2Manager cSFilterBarStyle2Manager = new CSFilterBarStyle2Manager(this.mContext, this.srcbmp);
        this.style2Manager = cSFilterBarStyle2Manager;
        this.wbResGroupList = cSFilterBarStyle2Manager.getFilterGroupList();
        this.wbResList = this.style2Manager.getFilterList();
        this.filterBarViewGroupAdapter = new CSFilterBarViewGroupAdapter(this.mContext, this.wbResGroupList);
        this.filterBarViewAdapter = new CSFilterBarViewAdapter(this.mContext, this.wbResList, this.srcbmp);
        this.linearLayoutGroupManager = new LinearLayoutManager(this.mContext, RecyclerView.HORIZONTAL, false);
        this.recyclerViewGroup.setAdapter(this.filterBarViewGroupAdapter);
        this.recyclerViewGroup.setLayoutManager(this.linearLayoutGroupManager);
        this.linearLayoutManager = new CSCenterLayoutManager(this.mContext, 0, false);
        this.recyclerView.setAdapter(this.filterBarViewAdapter);
        this.recyclerView.setLayoutManager(this.linearLayoutManager);
        this.filterBarViewGroupAdapter.setOnBarViewItemClikListener(new CSFilterBarViewGroupAdapter.onGroupBarViewItemClikListener() { // from class: com.picspool.libfuncview.filter.CSFilterBarStyle2.3
            @Override // com.picspool.libfuncview.filter.CSFilterBarViewGroupAdapter.onGroupBarViewItemClikListener
            public void onClick(int i) {
                if (CSFilterBarStyle2.this.wbResGroupList == null || i >= CSFilterBarStyle2.this.wbResGroupList.size()) {
                    return;
                }
                CSFilterGroup cSFilterGroup = (CSFilterGroup) CSFilterBarStyle2.this.wbResGroupList.get(i);
                CSFilterBarStyle2 cSFilterBarStyle2 = CSFilterBarStyle2.this;
                if (cSFilterBarStyle2.isSmoothScroll(cSFilterBarStyle2.wbResGroupList, CSFilterBarStyle2.this.filterBarViewGroupAdapter.getSelectedPos(), i)) {
                    CSFilterBarStyle2.this.linearLayoutManager.smoothScrollToPosition(CSFilterBarStyle2.this.recyclerView, null, cSFilterGroup.getFirstItemIndex());
                } else {
                    CSFilterBarStyle2.this.linearLayoutManager.scrollToPosition(cSFilterGroup.getFirstItemIndex());
                }
                CSFilterBarStyle2.this.isListScrollDragging = false;
            }
        });
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.picspool.libfuncview.filter.CSFilterBarStyle2.4
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 1) {
                    CSFilterBarStyle2.this.isListScrollDragging = true;
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                if (CSFilterBarStyle2.this.isListScrollDragging) {
                    int findLastVisibleItemPosition = CSFilterBarStyle2.this.linearLayoutManager.findLastVisibleItemPosition();
                    for (int i3 = 0; i3 < CSFilterBarStyle2.this.wbResGroupList.size(); i3++) {
                        CSFilterGroup cSFilterGroup = (CSFilterGroup) CSFilterBarStyle2.this.wbResGroupList.get(i3);
                        if (findLastVisibleItemPosition <= (cSFilterGroup.getFirstItemIndex() + cSFilterGroup.getFilterResList().size()) - 1) {
                            if (i3 != CSFilterBarStyle2.this.filterBarViewGroupAdapter.getSelectedPos()) {
                                CSFilterBarStyle2.this.recyclerViewGroup.smoothScrollToPosition(i3);
                                ((CSFilterGroup) CSFilterBarStyle2.this.wbResGroupList.get(i3)).setHasShow(true);
                                CSFilterBarStyle2.this.filterBarViewGroupAdapter.setSelectedPos(i3);
                                return;
                            }
                            return;
                        }
                    }
                }
            }
        });
        this.filterBarViewAdapter.setOnBarViewItemClikListener(new CSFilterBarViewAdapter.onFilterBarViewItemClikListener() { // from class: com.picspool.libfuncview.filter.CSFilterBarStyle2.5
            @Override // com.picspool.libfuncview.filter.CSFilterBarViewAdapter.onFilterBarViewItemClikListener
            public void onClick(int i, DMWBRes dMWBRes) {
                if (dMWBRes instanceof CSGPUFilterImageRes) {
                    GPUImageFilter createFilterForType = GPUFilter.createFilterForType(CSFilterBarStyle2.this.mContext, ((CSGPUFilterImageRes) dMWBRes).getFilterType());
                    if (dMWBRes instanceof CSAdjustableFilterRes) {
                        createFilterForType.setMix(((CSAdjustableFilterRes) dMWBRes).getMix() / 100.0f);
                    }
                    if (CSFilterBarStyle2.this.gpuImageView != null) {
                        CSFilterBarStyle2.this.gpuImageView.setFilter(createFilterForType);
                    }
                }
            }
        });
        View findViewById = findViewById(R.id.ly_funcbar);
        Context context = this.mContext;
        CSObjectAnimatorUtils.performYAnimate(findViewById, DMScreenInfoUtil.dip2px(context, context.getResources().getDimension(R.dimen.libui_func_bar_height)), 0.0f, CSObjectAnimatorUtils.FucBarDuration, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSmoothScroll(List<DMWBRes> list, int i, int i2) {
        if (i == i2) {
            return false;
        }
        if (i < i2) {
            while (i <= i2) {
                if (!((CSFilterGroup) list.get(i)).isHasShow()) {
                    return false;
                }
                i++;
            }
            return true;
        }
        while (i2 <= i) {
            if (!((CSFilterGroup) list.get(i2)).isHasShow()) {
                return false;
            }
            i2++;
        }
        return true;
    }

    public CSFilterBarViewAdapter getFilterBarViewAdapter() {
        return this.filterBarViewAdapter;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        CSBarViewControlListener cSBarViewControlListener;
        if (i != 4 || (cSBarViewControlListener = this.mListener) == null) {
            return true;
        }
        cSBarViewControlListener.onCancel();
        return true;
    }
}

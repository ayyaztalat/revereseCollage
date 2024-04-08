package com.picspool.libsquare.uiview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import com.picspool.libsquare.manager.CSSquareUiFilterManager;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.resource.widget.DMWBHorizontalListView;
import com.picspool.lib.resource.widget.DMWBScrollBarArrayAdapter;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSquareUiFilterToolBarView extends FrameLayout {
    private DMWBHorizontalListView hrzFilter;
    private Context mContext;
    protected DMWBScrollBarArrayAdapter mFilterAdapter;
    private CSSquareUiFilterManager mFiterManager;
    private onSquareUiFilterToolBarViewListener mListener;
    private int mPos;

    /* loaded from: classes.dex */
    public interface onSquareUiFilterToolBarViewListener {
        void onFilterClick(GPUFilterRes gPUFilterRes, int i);
    }

    public CSSquareUiFilterToolBarView(Context context, int i) {
        super(context);
        this.mPos = 0;
        this.mContext = context;
        this.mPos = i;
        init(context);
    }

    public CSSquareUiFilterToolBarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        this.mPos = 0;
        this.mContext = context;
        this.mPos = i;
        init(context);
    }

    public void setOnSquareUiFilterToolBarViewListener(onSquareUiFilterToolBarViewListener onsquareuifiltertoolbarviewlistener) {
        this.mListener = onsquareuifiltertoolbarviewlistener;
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.square_ui_filter_view, (ViewGroup) this, true);
        init();
    }

    private void init() {
        this.hrzFilter = (DMWBHorizontalListView) findViewById(R.id.hrzFilter);
        this.mFiterManager = new CSSquareUiFilterManager(getContext());
        this.hrzFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.picspool.libsquare.uiview.CSSquareUiFilterToolBarView.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                GPUFilterRes gPUFilterRes = (GPUFilterRes) CSSquareUiFilterToolBarView.this.mFiterManager.getRes(i);
                if (CSSquareUiFilterToolBarView.this.mListener != null) {
                    CSSquareUiFilterToolBarView.this.mListener.onFilterClick(gPUFilterRes, i);
                    if (CSSquareUiFilterToolBarView.this.hrzFilter != null) {
                        CSSquareUiFilterToolBarView.this.hrzFilter.scrollTo((DMScreenInfoUtil.dip2px(CSSquareUiFilterToolBarView.this.mContext, 75.0f) * i) + ((DMScreenInfoUtil.dip2px(CSSquareUiFilterToolBarView.this.mContext, 75.0f) - DMScreenInfoUtil.screenWidth(CSSquareUiFilterToolBarView.this.mContext)) / 2));
                    }
                    CSSquareUiFilterToolBarView.this.mFilterAdapter.setSelectPosition(i);
                }
            }
        });
        int count = this.mFiterManager.getCount();
        GPUFilterRes[] gPUFilterResArr = new GPUFilterRes[count];
        Bitmap imageFromAssetsFile = DMBitmapUtil.getImageFromAssetsFile(getResources(), "filter/f2.jpg");
        for (int i = 0; i < count; i++) {
            gPUFilterResArr[i] = (GPUFilterRes) this.mFiterManager.getRes(i);
            gPUFilterResArr[i].setSRC(imageFromAssetsFile);
        }
        this.mFilterAdapter = null;
        DMWBScrollBarArrayAdapter dMWBScrollBarArrayAdapter = new DMWBScrollBarArrayAdapter(getContext(), gPUFilterResArr);
        this.mFilterAdapter = dMWBScrollBarArrayAdapter;
        dMWBScrollBarArrayAdapter.setViewWidthDp(75);
        this.mFilterAdapter.setImageBorderViewLayout(90, 67, 67);
        this.mFilterAdapter.setTextViewWidthDp(75);
        this.mFilterAdapter.setTextViewHeightDp(15);
        this.mFilterAdapter.setTextViewColor(-1);
        this.mFilterAdapter.setTextViewBackColor(-7829368);
        this.mFilterAdapter.setTextMarginBottomDp(3);
        this.mFilterAdapter.setBottomSelState(true);
        this.mFilterAdapter.setSelectPosition(this.mPos);
        this.hrzFilter.setAdapter((ListAdapter) this.mFilterAdapter);
    }

    public void dispose() {
        DMWBHorizontalListView dMWBHorizontalListView = this.hrzFilter;
        if (dMWBHorizontalListView != null) {
            dMWBHorizontalListView.setAdapter((ListAdapter) null);
            this.hrzFilter = null;
        }
        DMWBScrollBarArrayAdapter dMWBScrollBarArrayAdapter = this.mFilterAdapter;
        if (dMWBScrollBarArrayAdapter != null) {
            dMWBScrollBarArrayAdapter.dispose();
        }
        this.mFilterAdapter = null;
    }
}

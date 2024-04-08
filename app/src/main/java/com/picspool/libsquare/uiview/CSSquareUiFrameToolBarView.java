package com.picspool.libsquare.uiview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import com.picspool.libsquare.manager.CSSquareUiFrameManager;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.widget.DMWBHorizontalListView;
import com.picspool.lib.resource.widget.DMWBScrollBarArrayAdapter;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSquareUiFrameToolBarView extends FrameLayout {
    private DMWBHorizontalListView hrzFrame;
    private Context mContext;
    protected DMWBScrollBarArrayAdapter mFrameAdapter;
    private CSSquareUiFrameManager mFrameManager;
    private OnSquareUiFrameToolBarViewListener mListener;
    private int mPos;

    /* loaded from: classes.dex */
    public interface OnSquareUiFrameToolBarViewListener {
        void onFrameClick(DMWBRes dMWBRes, int i);
    }

    public CSSquareUiFrameToolBarView(Context context, int i) {
        super(context);
        this.mPos = 0;
        this.mContext = context;
        this.mPos = i;
        init(context);
    }

    public CSSquareUiFrameToolBarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        this.mPos = 0;
        this.mContext = context;
        this.mPos = i;
        init(context);
    }

    public void setOnSquareUiFrameToolBarViewListener(OnSquareUiFrameToolBarViewListener onSquareUiFrameToolBarViewListener) {
        this.mListener = onSquareUiFrameToolBarViewListener;
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.square_ui_frame_view, (ViewGroup) this, true);
        init();
    }

    private void init() {
        this.hrzFrame = (DMWBHorizontalListView) findViewById(R.id.recyclerview);
        this.mFrameManager = new CSSquareUiFrameManager(getContext());
        this.hrzFrame.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.picspool.libsquare.uiview.CSSquareUiFrameToolBarView.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                DMWBRes res = CSSquareUiFrameToolBarView.this.mFrameManager.getRes(i);
                if (CSSquareUiFrameToolBarView.this.mListener != null) {
                    CSSquareUiFrameToolBarView.this.mListener.onFrameClick(res, i);
                    if (CSSquareUiFrameToolBarView.this.hrzFrame != null) {
                        CSSquareUiFrameToolBarView.this.hrzFrame.scrollTo((DMScreenInfoUtil.dip2px(CSSquareUiFrameToolBarView.this.mContext, 75.0f) * i) + ((DMScreenInfoUtil.dip2px(CSSquareUiFrameToolBarView.this.mContext, 75.0f) - DMScreenInfoUtil.screenWidth(CSSquareUiFrameToolBarView.this.mContext)) / 2));
                    }
                    CSSquareUiFrameToolBarView.this.mFrameAdapter.setSelectPosition(i);
                }
            }
        });
        int count = this.mFrameManager.getCount();
        DMWBRes[] dMWBResArr = new DMWBRes[count];
        for (int i = 0; i < count; i++) {
            dMWBResArr[i] = this.mFrameManager.getRes(i);
        }
        this.mFrameAdapter = null;
        DMWBScrollBarArrayAdapter dMWBScrollBarArrayAdapter = new DMWBScrollBarArrayAdapter(getContext(), dMWBResArr);
        this.mFrameAdapter = dMWBScrollBarArrayAdapter;
        dMWBScrollBarArrayAdapter.setViewWidthDp(75);
        this.mFrameAdapter.setImageBorderViewLayout(90, 67, 67);
        this.mFrameAdapter.setTextViewWidthDp(75);
        this.mFrameAdapter.setTextViewHeightDp(15);
        this.mFrameAdapter.setTextViewColor(-1);
        this.mFrameAdapter.setTextViewBackColor(-7829368);
        this.mFrameAdapter.setBottomSelState(true);
        this.mFrameAdapter.setTextMarginBottomDp(3);
        this.mFrameAdapter.setSelectPosition(this.mPos);
        this.hrzFrame.setAdapter((ListAdapter) this.mFrameAdapter);
    }

    public void dispose() {
        DMWBScrollBarArrayAdapter dMWBScrollBarArrayAdapter = this.mFrameAdapter;
        if (dMWBScrollBarArrayAdapter != null) {
            dMWBScrollBarArrayAdapter.dispose();
        }
        this.mFrameAdapter = null;
    }
}

package com.winflag.libsquare.uiview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.lib.border.CSTBorderRes;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;
import com.winflag.libsquare.adapter.CSSquareFrameBarViewAdapter;
import com.winflag.libsquare.manager.CSSquareUiFrameManager;

/* loaded from: classes3.dex */
public class CSSquareUiFrameToolBarView extends FrameLayout {
    private Context mContext;
    private CSSquareUiFrameManager mFrameManager;
    private OnSquareUiFrameToolBarViewListener mListener;
    private int mPos;
    private RecyclerView recyclerView;
    private CSSquareFrameBarViewAdapter squareFrameBarViewAdapter;

    /* loaded from: classes3.dex */
    public interface OnSquareUiFrameToolBarViewListener {
        void onFrameClick(DMWBRes dMWBRes, int i, boolean z);
    }

    public void dispose() {
    }

    public CSSquareUiFrameToolBarView(Context context, int i) {
        super(context);
        this.mPos = 0;
        this.mContext = context;
        this.mPos = i;
        init(context);
    }

    public CSSquareUiFrameToolBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPos = 0;
        this.mContext = context;
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
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        this.mFrameManager = new CSSquareUiFrameManager(this.mContext);
        CSSquareFrameBarViewAdapter cSSquareFrameBarViewAdapter = new CSSquareFrameBarViewAdapter(this.mContext, this.mFrameManager.getResList());
        this.squareFrameBarViewAdapter = cSSquareFrameBarViewAdapter;
        this.recyclerView.setAdapter(cSSquareFrameBarViewAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.HORIZONTAL, false));
        this.squareFrameBarViewAdapter.setSelectedPos(this.mPos);
        this.squareFrameBarViewAdapter.setOnFrameBarViewItemClikListener(new CSSquareFrameBarViewAdapter.onFrameBarViewItemClikListener() { // from class: com.winflag.libsquare.uiview.CSSquareUiFrameToolBarView.1
            @Override // com.winflag.libsquare.adapter.CSSquareFrameBarViewAdapter.onFrameBarViewItemClikListener
            public void onClick(int i, CSTBorderRes cSTBorderRes, boolean z) {
                if (CSSquareUiFrameToolBarView.this.mListener != null) {
                    CSSquareUiFrameToolBarView.this.mListener.onFrameClick(cSTBorderRes, i, z);
                }
            }
        });
    }

    public CSSquareFrameBarViewAdapter getSquareFrameBarViewAdapter() {
        return this.squareFrameBarViewAdapter;
    }
}

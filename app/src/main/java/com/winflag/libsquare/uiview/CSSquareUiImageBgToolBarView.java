package com.winflag.libsquare.uiview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;

import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;
import com.picspool.lib.resource.widget.DMWBHorizontalListView;
import com.picspool.lib.resource.widget.DMWBScrollBarArrayAdapter;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class CSSquareUiImageBgToolBarView extends FrameLayout implements AdapterView.OnItemClickListener {
    private View ly_back;
    private DMWBHorizontalListView mFrameList;
    private OnSquareImageBgSeletorListener mListener;
    private DMWBManager mManager;
    DMWBScrollBarArrayAdapter scrollBarAdapter;

    /* loaded from: classes3.dex */
    public interface OnSquareImageBgSeletorListener {
        void onSquareImagBgChange(DMWBRes dMWBRes);

        void onSquareImageBgCancel();
    }

    public CSSquareUiImageBgToolBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.square_ui_imagebg_toolbar_view, (ViewGroup) this, true);
        this.mFrameList = (DMWBHorizontalListView) findViewById(R.id.imgbgList);
        View findViewById = findViewById(R.id.ly_back);
        this.ly_back = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.winflag.libsquare.uiview.CSSquareUiImageBgToolBarView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSSquareUiImageBgToolBarView.this.mListener.onSquareImageBgCancel();
            }
        });
    }

    public void setOnSquareImageBgSeletorListener(OnSquareImageBgSeletorListener onSquareImageBgSeletorListener) {
        this.mListener = onSquareImageBgSeletorListener;
    }

    public void setBgImageManager(DMWBManager dMWBManager) {
        this.mManager = dMWBManager;
        setImageBgGroupAdapter();
    }

    private void setImageBgGroupAdapter() {
        int count = this.mManager.getCount();
        DMWBRes[] dMWBResArr = new DMWBRes[count];
        for (int i = 0; i < count; i++) {
            dMWBResArr[i] = this.mManager.getRes(i);
        }
        DMWBScrollBarArrayAdapter dMWBScrollBarArrayAdapter = this.scrollBarAdapter;
        if (dMWBScrollBarArrayAdapter != null) {
            dMWBScrollBarArrayAdapter.dispose();
        }
        this.scrollBarAdapter = null;
        this.mFrameList.setVisibility(View.VISIBLE);
        DMWBScrollBarArrayAdapter dMWBScrollBarArrayAdapter2 = new DMWBScrollBarArrayAdapter(getContext(), dMWBResArr);
        this.scrollBarAdapter = dMWBScrollBarArrayAdapter2;
        this.mFrameList.setAdapter((ListAdapter) dMWBScrollBarArrayAdapter2);
        this.mFrameList.setOnItemClickListener(this);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.scrollBarAdapter.setSelectPosition(i);
        DMWBManager dMWBManager = this.mManager;
        DMWBRes res = dMWBManager != null ? dMWBManager.getRes(i) : null;
        OnSquareImageBgSeletorListener onSquareImageBgSeletorListener = this.mListener;
        if (onSquareImageBgSeletorListener != null) {
            onSquareImageBgSeletorListener.onSquareImagBgChange(res);
        }
    }

    public void dispose() {
        if (this.mManager != null) {
            this.mManager = null;
        }
        DMWBHorizontalListView dMWBHorizontalListView = this.mFrameList;
        if (dMWBHorizontalListView != null) {
            dMWBHorizontalListView.setAdapter((ListAdapter) null);
            this.mFrameList = null;
        }
        DMWBScrollBarArrayAdapter dMWBScrollBarArrayAdapter = this.scrollBarAdapter;
        if (dMWBScrollBarArrayAdapter != null) {
            dMWBScrollBarArrayAdapter.dispose();
        }
        this.scrollBarAdapter = null;
    }

    public void updateIcon() {
        this.scrollBarAdapter.notifyDataSetChanged();
    }
}

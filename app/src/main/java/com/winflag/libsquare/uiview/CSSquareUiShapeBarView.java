package com.winflag.libsquare.uiview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sky.testproject.R;
import com.winflag.libsquare.adapter.CSSquareShapeBarViewAdapter;
import com.winflag.libsquare.manager.CSSquareUiShapeManager;

/* loaded from: classes3.dex */
public class CSSquareUiShapeBarView extends FrameLayout {
    private Context mContext;
    private RecyclerView recyclerView;
    private CSSquareShapeBarViewAdapter shapadapter;

    public CSSquareUiShapeBarView(Context context) {
        super(context);
        initView(context);
    }

    public CSSquareShapeBarViewAdapter getShapadapter() {
        return this.shapadapter;
    }

    public CSSquareUiShapeBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public CSSquareUiShapeBarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.square_ui_shape_toolbar_view, (ViewGroup) this, true);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        CSSquareShapeBarViewAdapter cSSquareShapeBarViewAdapter = new CSSquareShapeBarViewAdapter(this.mContext, new CSSquareUiShapeManager(this.mContext).getShapeResList());
        this.shapadapter = cSSquareShapeBarViewAdapter;
        this.recyclerView.setAdapter(cSSquareShapeBarViewAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.HORIZONTAL, false));
    }
}

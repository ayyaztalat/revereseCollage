package com.picspool.snappic.widget.square;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.libsquare.res.CSShapeRes;
import com.picspool.snappic.adapter.CSShapeRecyclerViewAdapter;
import com.picspool.snappic.manager.CSSquareShapeManager;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSShapeBar extends FrameLayout {
    private Context mContext;
    private onShapeBarClickListner mListenrer;
    private RecyclerView recyclerView;
    private CSShapeRecyclerViewAdapter shapadapter;

    /* loaded from: classes.dex */
    public interface onShapeBarClickListner {
        void onCancel();

        void onShapeItemClicke(int i, CSShapeRes cSShapeRes, boolean z);
    }

    public CSShapeBar(Context context) {
        super(context);
        initView(context);
    }

    public CSShapeRecyclerViewAdapter getShapadapter() {
        return this.shapadapter;
    }

    public CSShapeBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public CSShapeBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_square_shape_bar, (ViewGroup) this, true);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        CSShapeRecyclerViewAdapter cSShapeRecyclerViewAdapter = new CSShapeRecyclerViewAdapter(this.mContext, new CSSquareShapeManager(this.mContext).getShapeResList());
        this.shapadapter = cSShapeRecyclerViewAdapter;
        this.recyclerView.setAdapter(cSShapeRecyclerViewAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.HORIZONTAL, false));
        this.shapadapter.setShapeBarItemClikListener(new CSShapeRecyclerViewAdapter.onShapeBarViewItemClikListener() { // from class: com.picspool.snappic.widget.square.CSShapeBar.1
            @Override // com.picspool.snappic.adapter.CSShapeRecyclerViewAdapter.onShapeBarViewItemClikListener
            public void onClick(int i, CSShapeRes cSShapeRes, boolean z) {
                if (CSShapeBar.this.mListenrer != null) {
                    CSShapeBar.this.mListenrer.onShapeItemClicke(i, cSShapeRes, z);
                }
            }
        });
        findViewById(R.id.ly_cancel).setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.widget.square.CSShapeBar.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSShapeBar.this.mListenrer != null) {
                    CSShapeBar.this.mListenrer.onCancel();
                }
            }
        });
    }

    public void setShapeBarClickListner(onShapeBarClickListner onshapebarclicklistner) {
        this.mListenrer = onshapebarclicklistner;
    }
}

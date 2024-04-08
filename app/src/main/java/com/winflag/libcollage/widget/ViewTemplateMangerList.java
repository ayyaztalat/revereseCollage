package com.winflag.libcollage.widget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.photo.editor.square.splash.utils.AssBitManage;

import com.sky.testproject.R;
import com.winflag.libcollage.adapter.BordRecyclerViewAdapter;
import com.winflag.libcollage.manager.TemplateManager;
import java.util.List;

/* loaded from: classes.dex */
public class ViewTemplateMangerList extends FrameLayout implements AdapterView.OnItemClickListener {
    private BordRecyclerViewAdapter boredRecyclerViewAdapter;
    private Context mContext;
    public OnTemplateChangedListener mListener;
    private TemplateManager mManager;
    private RecyclerView recyclerView;

    /* loaded from: classes.dex */
    public interface OnTemplateChangedListener {
        void onTemplateChanged(List<AssBitManage.bitBean> list, int i);
    }

    public void dispose() {
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
    }

    public void setmListener(OnTemplateChangedListener onTemplateChangedListener) {
        this.mListener = onTemplateChangedListener;
    }

    public ViewTemplateMangerList(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_layout_border, (ViewGroup) this, true);
    }

    public void setManager(TemplateManager templateManager) {
        if (templateManager == null) {
            return;
        }
        this.mManager = templateManager;
        setAdapter();
    }

    private void setAdapter() {
        BordRecyclerViewAdapter bordRecyclerViewAdapter = new BordRecyclerViewAdapter(this.mContext, AssBitManage.getBit(), this.recyclerView, BitmapFactory.decodeResource(getResources(), R.drawable.collage_border_corner));
        this.boredRecyclerViewAdapter = bordRecyclerViewAdapter;
        bordRecyclerViewAdapter.setShowText(false);
        this.recyclerView.setAdapter(this.boredRecyclerViewAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.HORIZONTAL, false));
        this.boredRecyclerViewAdapter.setOnRecyclerViewItemClikListener(new BordRecyclerViewAdapter.onRecyclerViewItemClikListener() { // from class: com.winflag.libcollage.widget.ViewTemplateMangerList.1
            @Override // com.winflag.libcollage.adapter.BordRecyclerViewAdapter.onRecyclerViewItemClikListener
            public void onClick(List<AssBitManage.bitBean> list, int i) {
                if (ViewTemplateMangerList.this.mListener != null) {
                    ViewTemplateMangerList.this.mListener.onTemplateChanged(list, i);
                }
            }
        });
    }

    public void setOnTemplateChangedListener(OnTemplateChangedListener onTemplateChangedListener) {
        this.mListener = onTemplateChangedListener;
    }
}

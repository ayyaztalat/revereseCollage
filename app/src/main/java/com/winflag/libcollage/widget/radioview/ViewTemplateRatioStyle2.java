package com.winflag.libcollage.widget.radioview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sky.testproject.R;
import com.winflag.libcollage.resource.RatioRes;
import java.util.List;

/* loaded from: classes.dex */
public class ViewTemplateRatioStyle2 extends FrameLayout {
    private TemplateRatioAdapter adapter;
    private Context mContext;
    private int pos;

    public TemplateRatioAdapter getAdapter() {
        return this.adapter;
    }

    public ViewTemplateRatioStyle2(Context context) {
        super(context);
        initView(context);
    }

    public ViewTemplateRatioStyle2(Context context, int i) {
        super(context);
        this.pos = i;
        initView(context);
    }

    public ViewTemplateRatioStyle2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ViewTemplateRatioStyle2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void initView(Context context) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_template_ratio_style_2, (ViewGroup) this, true);
        List<RatioRes> radtio_list = new TemplateRatioManager(this.mContext).getRadtio_list();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        TemplateRatioAdapter templateRatioAdapter = new TemplateRatioAdapter(this.mContext, radtio_list, this.pos);
        this.adapter = templateRatioAdapter;
        recyclerView.setAdapter(templateRatioAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.HORIZONTAL, false));
    }
}

package com.picspool.instafilter.view;

import android.content.Context;
import android.util.AttributeSet;
import java.util.LinkedList;
import java.util.List;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.filter.gpu.GPUImageFilterTools;
import com.picspool.lib.filter.gpu.GPUImageView;
import com.picspool.lib.filter.gpu.blend.GPUImageScreenBlendFilter;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.gpu.father.GPUImageFilterGroup;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes3.dex */
public class GPUBlendFilterView extends GPUImageView {
    GPUImageFilterGroup filterGroup;
    List<GPUImageFilter> filters;

    public GPUBlendFilterView(Context context) {
        super(context);
        this.filters = new LinkedList();
        this.filterGroup = new GPUImageFilterGroup(this.filters);
    }

    public GPUBlendFilterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.filters = new LinkedList();
        this.filterGroup = new GPUImageFilterGroup(this.filters);
    }

    public void setFilter(DMWBRes dMWBRes, String str) {
        if (dMWBRes == null) {
            return;
        }
        this.filters.clear();
        if (dMWBRes.getName().equalsIgnoreCase("blend1")) {
            this.filters.clear();
            GPUImageScreenBlendFilter gPUImageScreenBlendFilter = new GPUImageScreenBlendFilter();
            gPUImageScreenBlendFilter.setBitmap(DMBitmapUtil.getImageFromAssetsFile(getResources(), "art/pencil.jpg"));
            this.filters.add(gPUImageScreenBlendFilter);
            GPUImageScreenBlendFilter gPUImageScreenBlendFilter2 = new GPUImageScreenBlendFilter();
            gPUImageScreenBlendFilter2.setBitmap(DMBitmapUtil.getImageFromAssetsFile(getResources(), "art/colorpencil.jpg"));
            this.filters.add(gPUImageScreenBlendFilter2);
            GPUImageFilterGroup gPUImageFilterGroup = new GPUImageFilterGroup(this.filters);
            this.filterGroup = gPUImageFilterGroup;
            setFilter(gPUImageFilterGroup);
        }
    }

    public void setAdjust(int i) {
        for (int i2 = 0; i2 < this.filters.size(); i2++) {
            new GPUImageFilterTools.FilterAdjuster(this.filters.get(i2)).adjust(i);
        }
        requestRender();
    }
}

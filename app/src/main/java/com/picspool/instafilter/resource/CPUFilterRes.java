package com.picspool.instafilter.resource;

import android.graphics.Bitmap;
import com.picspool.instafilter.CPUFilter;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.filter.cpu.CPUFilterType;
import com.picspool.lib.filter.listener.OnPostFilteredListener;
import com.picspool.lib.resource.DMWBAsyncPostIconListener;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes3.dex */
public class CPUFilterRes extends DMWBRes {
    private CPUFilterType filterType = CPUFilterType.NOFILTER;
    private Bitmap src = null;
    private Bitmap filtered = null;

    public void setFilterType(CPUFilterType cPUFilterType) {
        this.filterType = cPUFilterType;
    }

    public CPUFilterType getFilterType() {
        return this.filterType;
    }

    public void setSRC(Bitmap bitmap) {
        this.src = bitmap;
    }

    @Override // com.picspool.lib.resource.DMWBRes
    public Bitmap getIconBitmap() {
        if (getIconType() == DMWBRes.LocationType.FILTERED) {
            this.asyncIcon = true;
            return this.src;
        }
        return DMBitmapUtil.getImageFromAssetsFile(getResources(), getIconFileName());
    }

    @Override // com.picspool.lib.resource.DMWBRes
    public void getAsyncIconBitmap(final DMWBAsyncPostIconListener dMWBAsyncPostIconListener) {
        Bitmap bitmap = this.filtered;
        if (bitmap == null || bitmap.isRecycled()) {
            CPUFilter.asyncFilterForType(this.context, this.src, this.filterType, new OnPostFilteredListener() { // from class: com.picspool.instafilter.resource.CPUFilterRes.1
                @Override // com.picspool.lib.filter.listener.OnPostFilteredListener
                public void postFiltered(Bitmap bitmap2) {
                    CPUFilterRes.this.filtered = bitmap2;
                    dMWBAsyncPostIconListener.postIcon(CPUFilterRes.this.filtered);
                }
            });
        } else {
            dMWBAsyncPostIconListener.postIcon(this.filtered);
        }
    }
}

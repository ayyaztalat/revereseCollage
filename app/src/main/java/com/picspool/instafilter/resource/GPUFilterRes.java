package com.picspool.instafilter.resource;

import android.graphics.Bitmap;
import com.picspool.instafilter.GPUFilter;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.filter.listener.OnPostFilteredListener;
import com.picspool.lib.resource.DMWBAsyncPostIconListener;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes3.dex */
public class GPUFilterRes extends DMWBImageRes {
    private GPUFilterType filterType = GPUFilterType.NOFILTER;
    private Bitmap src = null;
    private Bitmap filtered = null;

    public void setFilterType(GPUFilterType gPUFilterType) {
        this.filterType = gPUFilterType;
    }

    public GPUFilterType getFilterType() {
        return this.filterType;
    }

    public void setSRC(Bitmap bitmap) {
        this.src = bitmap;
    }

    public Bitmap getSRC() {
        return this.src;
    }

    @Override // com.picspool.lib.resource.DMWBRes
    public Bitmap getIconBitmap() {
        if (getIconType() == DMWBRes.LocationType.FILTERED) {
            this.asyncIcon = true;
            return this.src;
        } else if (getIconType() == DMWBRes.LocationType.RES) {
            return DMBitmapDbUtil.getImageFromResourceFile(this.context, getIconID());
        } else {
            return DMBitmapUtil.getImageFromAssetsFile(getResources(), getIconFileName());
        }
    }

    @Override // com.picspool.lib.resource.DMWBRes
    public void getAsyncIconBitmap(final DMWBAsyncPostIconListener dMWBAsyncPostIconListener) {
        Bitmap bitmap = this.filtered;
        if (bitmap == null || bitmap.isRecycled()) {
            try {
                synchronized (this.src) {
                    GPUFilter.asyncFilterForType(this.context, this.src, this.filterType, new OnPostFilteredListener() { // from class: com.picspool.instafilter.resource.GPUFilterRes.1
                        @Override // com.picspool.lib.filter.listener.OnPostFilteredListener
                        public void postFiltered(Bitmap bitmap2) {
                            GPUFilterRes.this.filtered = bitmap2;
                            dMWBAsyncPostIconListener.postIcon(GPUFilterRes.this.filtered);
                        }
                    });
                }
                return;
            } catch (Throwable unused) {
                return;
            }
        }
        dMWBAsyncPostIconListener.postIcon(this.filtered);
    }

    public void dispose() {
        Bitmap bitmap = this.filtered;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.filtered.recycle();
        }
        this.filtered = null;
    }
}

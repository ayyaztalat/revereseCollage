package com.picspool.lib.collagelib.resource;

import android.content.Context;
import android.graphics.Bitmap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.picspool.lib.collagelib.LibDMTemplateIconView;

/* loaded from: classes3.dex */
public class LibDMTemplateWithImageRes extends LibDMTemplateRes {
    private LibDMTemplateIconView collageTemplateView;
    public Context mContext;
    private List<Bitmap> mSrcBitmaps = new ArrayList();

    public LibDMTemplateWithImageRes(Context context) {
        this.mContext = context;
    }

    public void setData() {
        LibDMTemplateIconView libDMTemplateIconView = new LibDMTemplateIconView(this.mContext);
        this.collageTemplateView = libDMTemplateIconView;
        libDMTemplateIconView.setRes(this);
        this.collageTemplateView.drawResultBmp();
    }

    public Bitmap getCustomBitmap() {
        return this.collageTemplateView.getResultBmp();
    }

    public void setBitmaps(HashMap<String, Bitmap> hashMap) {
        for (Map.Entry<String, Bitmap> entry : hashMap.entrySet()) {
            this.mSrcBitmaps.add(entry.getValue());
        }
    }

    public List<Bitmap> getBitmaps() {
        return this.mSrcBitmaps;
    }
}

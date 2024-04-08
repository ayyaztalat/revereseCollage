package com.picspool.lib.loader;

import android.content.ContentResolver;
import android.content.Context;
import com.picspool.lib.service.DMImageMediaItem;

/* loaded from: classes3.dex */
public class DMImageRequest {
    private DMImageMediaItem mBMImageMediaItem;
    private DMImageCallBack mCallBack;
    private Context mContext;

    public DMImageRequest(Context context, DMImageMediaItem dMImageMediaItem, DMImageCallBack dMImageCallBack) {
        this.mCallBack = null;
        this.mCallBack = dMImageCallBack;
        this.mBMImageMediaItem = dMImageMediaItem;
        this.mContext = context;
    }

    public String getmImgId() {
        return this.mBMImageMediaItem.getImgId();
    }

    public String getPath() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        DMImageMediaItem dMImageMediaItem = this.mBMImageMediaItem;
        return dMImageMediaItem.thumbPath(contentResolver, dMImageMediaItem.getImgId());
    }

    public DMImageMediaItem getmImageMediaItem() {
        return this.mBMImageMediaItem;
    }

    public void setmImageMediaItem(DMImageMediaItem dMImageMediaItem) {
        this.mBMImageMediaItem = dMImageMediaItem;
    }

    public Context getmContext() {
        return this.mContext;
    }

    public void setmContext(Context context) {
        this.mContext = context;
    }

    public DMImageCallBack getCallBack() {
        return this.mCallBack;
    }

    public void setCallBack(DMImageCallBack dMImageCallBack) {
        this.mCallBack = dMImageCallBack;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.mBMImageMediaItem.getImgId().equalsIgnoreCase(((DMImageRequest) obj).getmImageMediaItem().getImgId());
    }

    public String toString() {
        return "DMImageRequest DMImageMediaItem.ImgId=" + this.mBMImageMediaItem.getImgId();
    }
}

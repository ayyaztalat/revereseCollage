package com.winflag.libcollage.frame.res.border;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.picspool.lib.resource.DMWBImageRes;

import java.io.IOException;


/* loaded from: classes.dex */
public class TResInfo extends DMWBImageRes {
    public boolean bRetainFirst;
    public long downloadTime;
    private long expireTime;
    public long expiredTime;
    public boolean isDirectDownload;
    public boolean isDownloading = false;
    public Boolean isVip;
    public String otherAppStoreId;
    public String previewUrl;
    public int price;
    public int resId;
    protected EResType resType;
    private String shareTag;
    public int useCount;

    public EResType getResType() {
        return this.resType;
    }

    public void setResType(EResType eResType) {
        this.resType = eResType;
    }

    public boolean isShouldFirst() {
        if (this.bRetainFirst) {
            long j = this.expiredTime;
            if (j > 0 && j * 1000 >= System.currentTimeMillis()) {
                return true;
            }
        }
        return false;
    }

    public String getShareTag() {
        return this.shareTag;
    }

    public void setShareTag(String str) {
        this.shareTag = str;
    }

    public long getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(long j) {
        this.expireTime = j;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Bitmap readFromAssert(Context context, String str) {
        try {
            return BitmapFactory.decodeStream(context.getAssets().open(str));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

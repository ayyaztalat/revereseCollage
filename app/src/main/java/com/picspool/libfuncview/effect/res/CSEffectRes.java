package com.picspool.libfuncview.effect.res;

import android.content.Context;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;

/* loaded from: classes.dex */
public class CSEffectRes extends GPUFilterRes {
    private String groupName;
    private Context mContext;
    private String olFilePath;
    private int order;
    private int effetStrength = 50;
    private int rotation = 0;
    private boolean isStoreAddIcon = false;

    public CSEffectRes(Context context) {
        this.mContext = context;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int i) {
        this.order = i;
    }

    public void setOrder(String str) {
        Context context = this.mContext;
        String str2 = DMPreferencesUtil.get(context, str, this.groupName + getName());
        if (str2 != null) {
            setOrder(Integer.valueOf(str2).intValue());
        } else {
            setOrder(0);
        }
    }

    public int getEffetStrength() {
        return this.effetStrength;
    }

    public void setEffetStrength(int i) {
        this.effetStrength = i;
    }

    public int getRotation() {
        return this.rotation;
    }

    public void setRotation(int i) {
        this.rotation = i;
    }

    public boolean isStoreAddIcon() {
        return this.isStoreAddIcon;
    }

    public void setStoreAddIcon(boolean z) {
        this.isStoreAddIcon = z;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String str) {
        this.groupName = str;
    }

    public String getOlFilePath() {
        return this.olFilePath;
    }

    public void setOlFilePath(String str) {
        this.olFilePath = str;
    }
}

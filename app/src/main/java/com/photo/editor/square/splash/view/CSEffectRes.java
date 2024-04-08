package com.photo.editor.square.splash.view;

import android.content.Context;
import android.graphics.Bitmap;
import com.photo.editor.square.splash.view.view.bean.BackgroundGroupsItems;
import com.picspool.lib.bitmap.DMBitmapUtil;

/* loaded from: classes2.dex */
public class CSEffectRes {
    public String bgPath;
    public String disIconPath;
    public String fgPath;
    public int groupsort;
    public String iconPath;
    public boolean isOnlineRes;
    public String name;
    public int sort;
    public String groupname = "";
    public Type type = Type.Spiral;

    /* loaded from: classes2.dex */
    public enum Type {
        DoubleExposure,
        Drip,
        Spiral
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getIconPath() {
        return BackgroundGroupsItems.ANDROID_ASSETS_PATH + this.iconPath;
    }

    public void setIconPath(String str) {
        this.iconPath = str;
    }

    public String getFgPath() {
        return this.fgPath;
    }

    public void setFgPath(String str) {
        this.fgPath = str;
    }

    public String getBgPath() {
        return this.bgPath;
    }

    public void setBgPath(String str) {
        this.bgPath = str;
    }

    public String getDisIconPath() {
        return BackgroundGroupsItems.ANDROID_ASSETS_PATH + this.disIconPath;
    }

    public void setDisIconPath(String str) {
        this.disIconPath = str;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isOnlineRes() {
        return this.isOnlineRes;
    }

    public void setOnlineRes(boolean z) {
        this.isOnlineRes = z;
    }

    public Bitmap getBgBitmap(Context context) {
        return DMBitmapUtil.getImageFromAssetsFile(context.getResources(), getBgPath());
    }

    public Bitmap getFgBitmap(Context context) {
        return DMBitmapUtil.getImageFromAssetsFile(context.getResources(), getFgPath());
    }

    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String str) {
        this.groupname = str;
    }

    public int getSort() {
        return this.sort;
    }

    public void setSort(int i) {
        this.sort = i;
    }

    public int getGroupsort() {
        return this.groupsort;
    }

    public void setGroupsort(int i) {
        this.groupsort = i;
    }
}

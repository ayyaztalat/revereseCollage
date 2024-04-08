package com.baiwang.libuiinstalens.xlbsticker.stickerbar;

import android.graphics.Bitmap;

import com.picspool.lib.resource.DMWBImageRes;


/* loaded from: classes.dex */
public class CSStickerRes extends DMWBImageRes {

    /* renamed from: ID */
    private int f110ID;
    private String group_name;
    private Bitmap icon;
    private int iconid;
    private String name;

    public int getID() {
        return this.f110ID;
    }

    public void setID(int i) {
        this.f110ID = i;
    }

    public String getGroup_name() {
        return this.group_name;
    }

    public void setGroup_name(String str) {
        this.group_name = str;
    }

    public Bitmap getIcon() {
        return this.icon;
    }

    public void setIcon(Bitmap bitmap) {
        this.icon = bitmap;
    }

    @Override // org.picspool.lib.resource.DMWBRes
    public String getName() {
        return this.name;
    }

    @Override // org.picspool.lib.resource.DMWBRes
    public void setName(String str) {
        this.name = str;
    }
}

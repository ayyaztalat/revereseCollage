package com.baiwang.libuiinstalens.xlbsticker.stickerbar;

import android.content.Context;

import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CSStickerGroup extends DMWBRes {
    public static final int COLORSTYLE1 = 1;
    public static final int COLORSTYLE2 = 2;
    private String group_name;
    private int lastModified;
    private Context mContext;
    private int order;
    private StickerGroupType stickerGroupType;
    private int stylecolor1;
    private int stylecolor2;
    private List<CSStickerRes> list_sticker = new ArrayList();
    private int stickercolorstyle = 1;

    /* loaded from: classes.dex */
    public enum StickerGroupType {
        ASSERT,
        ONLINE
    }

    public CSStickerGroup(Context context) {
        this.mContext = context;
        this.stylecolor1 = context.getResources().getColor(R.color.style1_color1);
        this.stylecolor2 = this.mContext.getResources().getColor(R.color.style1_color2);
    }

    public int getStylecolor2() {
        return this.stylecolor2;
    }

    public int getStylecolor1() {
        return this.stylecolor1;
    }

    public int getStickercolorstyle() {
        return this.stickercolorstyle;
    }

    public void setStickercolorstyle(int i) {
        this.stickercolorstyle = i;
        if (i == 1) {
            this.stylecolor1 = this.mContext.getResources().getColor(R.color.style1_color1);
            this.stylecolor2 = this.mContext.getResources().getColor(R.color.style1_color2);
        }
        if (i == 2) {
            this.stylecolor1 = this.mContext.getResources().getColor(R.color.style2_color1);
            this.stylecolor2 = this.mContext.getResources().getColor(R.color.style2_color2);
        }
    }

    public String getGroup_name() {
        return this.group_name;
    }

    public void setGroup_name(String str) {
        this.group_name = str;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int i) {
        this.order = i;
    }

    public List<CSStickerRes> getList_sticker() {
        return this.list_sticker;
    }

    public int getStickerListSize() {
        return this.list_sticker.size();
    }

    public void setList_sticker(List<CSStickerRes> list) {
        this.list_sticker = list;
    }

    public void addStickers(CSStickerRes cSStickerRes) {
        this.list_sticker.add(cSStickerRes);
    }

    public int getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(int i) {
        this.lastModified = i;
    }

    public StickerGroupType getStickerGroupType() {
        return this.stickerGroupType;
    }

    public void setStickerGroupType(StickerGroupType stickerGroupType) {
        this.stickerGroupType = stickerGroupType;
    }
}

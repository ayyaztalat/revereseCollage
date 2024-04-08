package com.picspool.snappic.snap;

import com.picspool.lib.resource.DMWBImageRes;

/* loaded from: classes.dex */
public class CSBestFrameRes extends DMWBImageRes {
    private String jsonFilePath;
    private int tagHeight;
    private int tagStartY;
    private int tagWidth;
    private String typeId;

    public void setTyppeId(String str) {
        this.typeId = str;
    }

    public String getTypeId() {
        return this.typeId;
    }

    public void setTagWidth(int i) {
        this.tagWidth = i;
    }

    public int getTagWidth() {
        return this.tagWidth;
    }

    public void setTagHeight(int i) {
        this.tagHeight = i;
    }

    public int getTagHeight() {
        return this.tagHeight;
    }

    public void setTagStartY(int i) {
        this.tagStartY = i;
    }

    public int getTagStartY() {
        return this.tagStartY;
    }

    public void setJsonFilePath(String str) {
        this.jsonFilePath = str;
    }

    public String getJsonFilePath() {
        return this.jsonFilePath;
    }
}

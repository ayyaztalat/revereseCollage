package com.picspool.libfuncview.effect.onlinestore.resource;

import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes.dex */
public class CSEMaterialD2Res extends DMWBRes {
    private ItemType itemType;

    /* loaded from: classes.dex */
    public enum ItemType {
        BIGIMAGE,
        TITLE,
        NORMAL
    }

    public ItemType getItemType() {
        return this.itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
}

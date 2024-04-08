package com.picspool.libfuncview.effect.onlinestore.resource;

import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes.dex */
public class CSEMaterialDStoreRes extends DMWBRes {
    private List<CSEMaterialRes> bmwbResList = new ArrayList();
    private ItemType itemType;
    private CSEMaterialRes wbeMaterialRes;

    /* loaded from: classes.dex */
    public enum ItemType {
        VPBANNER,
        TITLE,
        NORMAL
    }

    public ItemType getItemType() {
        return this.itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public List<CSEMaterialRes> getBmwbResList() {
        return this.bmwbResList;
    }

    public void setBmwbResList(List<CSEMaterialRes> list) {
        this.bmwbResList = list;
    }

    public CSEMaterialRes getWbeMaterialRes() {
        return this.wbeMaterialRes;
    }

    public void setWbeMaterialRes(CSEMaterialRes cSEMaterialRes) {
        this.wbeMaterialRes = cSEMaterialRes;
    }
}

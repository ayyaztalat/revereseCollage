package com.picspool.libfuncview.effect.onlinestore.resource;

import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes.dex */
public class CSEMaterialGroupRes extends DMWBRes {
    private String groupID;
    private String groupIconUriPath;
    private String groupName;
    private int groupOrder;
    private List<CSEMaterialRes> list_material_res;
    private String uniqueGroupName;

    public int getGroupOrder() {
        return this.groupOrder;
    }

    public void setGroupOrder(int i) {
        this.groupOrder = i;
    }

    public String getGroupID() {
        return this.groupID;
    }

    public void setGroupID(String str) {
        this.groupID = str;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String str) {
        this.groupName = str;
    }

    public String getGroupIconUriPath() {
        return this.groupIconUriPath;
    }

    public void setGroupIconUriPath(String str) {
        this.groupIconUriPath = str;
    }

    public String getUniqueGroupName() {
        return this.uniqueGroupName;
    }

    public void setUniqueGroupName(String str) {
        this.uniqueGroupName = str;
    }

    public List<CSEMaterialRes> getWBMaterialResList() {
        return this.list_material_res;
    }

    public void setWBMaterialResList(List<CSEMaterialRes> list) {
        this.list_material_res = list;
    }

    public void addContentItem(CSEMaterialRes cSEMaterialRes) {
        if (this.list_material_res == null) {
            this.list_material_res = new ArrayList();
        }
        this.list_material_res.add(cSEMaterialRes);
    }
}

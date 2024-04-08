package com.picspool.libfuncview.res;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;

/* loaded from: classes.dex */
public class CSGroupRes extends DMWBRes {
    private GroupType groupType;
    private String group_unique_name;
    private int itemcount;
    private int lastModified;
    private Context mContext;
    private String olFilePath;
    private int order;
    private List<DMWBRes> list_res = new ArrayList();
    private boolean isExpanded = false;

    /* loaded from: classes.dex */
    public enum GroupType {
        ASSERT,
        ONLINE
    }

    public CSGroupRes(Context context) {
        this.mContext = context;
    }

    public String getGroup_unique_name() {
        return this.group_unique_name;
    }

    public void setGroup_unique_name(String str) {
        this.group_unique_name = str;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int i) {
        this.order = i;
    }

    public void setOrder(String str) {
        String str2 = DMPreferencesUtil.get(this.mContext, str, getName());
        if (str2 != null) {
            setOrder(Integer.valueOf(str2).intValue());
        } else {
            setOrder(0);
        }
    }

    public List<DMWBRes> getList_res() {
        return this.list_res;
    }

    public int getStickerListSize() {
        return this.list_res.size();
    }

    public void setList_res(List<DMWBRes> list) {
        this.list_res = list;
    }

    public void addRes(DMWBRes dMWBRes) {
        this.list_res.add(dMWBRes);
    }

    public int getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(int i) {
        this.lastModified = i;
    }

    public GroupType getGroupType() {
        return this.groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public boolean isExpanded() {
        return this.isExpanded;
    }

    public void setExpanded(boolean z) {
        this.isExpanded = z;
    }

    public int getItemcount() {
        return this.itemcount;
    }

    public void setItemcount(int i) {
        this.itemcount = i;
    }

    public String getOlFilePath() {
        return this.olFilePath;
    }

    public void setOlFilePath(String str) {
        this.olFilePath = str;
    }
}

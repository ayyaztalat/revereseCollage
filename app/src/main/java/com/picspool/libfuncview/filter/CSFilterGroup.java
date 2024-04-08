package com.picspool.libfuncview.filter;

import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes.dex */
public class CSFilterGroup extends DMWBRes {
    private List<CSGPUFilterImageRes> filterResList = new ArrayList();
    private int firstItemIndex = 0;
    private boolean hasShow = false;

    public void addItem(CSGPUFilterImageRes cSGPUFilterImageRes) {
        this.filterResList.add(cSGPUFilterImageRes);
    }

    public List<CSGPUFilterImageRes> getFilterResList() {
        return this.filterResList;
    }

    public int getFirstItemIndex() {
        return this.firstItemIndex;
    }

    public void setFirstItemIndex(int i) {
        this.firstItemIndex = i;
    }

    public boolean isHasShow() {
        return this.hasShow;
    }

    public void setHasShow(boolean z) {
        this.hasShow = z;
    }
}

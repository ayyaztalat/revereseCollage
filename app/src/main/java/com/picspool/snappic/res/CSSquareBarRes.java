package com.picspool.snappic.res;

import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes.dex */
public class CSSquareBarRes extends DMWBRes {
    private List<DMWBImageRes> imageResList = new ArrayList();
    private int current_index = 0;
    public boolean clickable = true;

    public List<DMWBImageRes> getImageResList() {
        return this.imageResList;
    }

    public void addResListItem(DMWBImageRes dMWBImageRes) {
        this.imageResList.add(dMWBImageRes);
    }

    public DMWBImageRes getCurrentRes() {
        return this.imageResList.get(this.current_index);
    }

    public boolean isClickable() {
        return this.clickable;
    }

    public void setClickable(boolean z) {
        this.clickable = z;
    }

    public DMWBImageRes getNextRes() {
        int i = this.current_index + 1;
        this.current_index = i;
        if (i >= this.imageResList.size()) {
            this.current_index = 0;
        }
        return this.imageResList.get(this.current_index);
    }

    public void movetonextRes() {
        int i = this.current_index + 1;
        this.current_index = i;
        if (i >= this.imageResList.size()) {
            this.current_index = 0;
        }
    }
}

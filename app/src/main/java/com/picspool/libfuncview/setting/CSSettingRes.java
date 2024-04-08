package com.picspool.libfuncview.setting;

import com.picspool.libfuncview.res.CSResManagerInterface;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes.dex */
public class CSSettingRes extends DMWBRes {
    private CSResManagerInterface resManagerInterface;
    private String sdFileFunName;

    public CSResManagerInterface getResManagerInterface() {
        return this.resManagerInterface;
    }

    public void setResManagerInterface(CSResManagerInterface cSResManagerInterface) {
        this.resManagerInterface = cSResManagerInterface;
    }

    public String getSdFileFunName() {
        return this.sdFileFunName;
    }

    public void setSdFileFunName(String str) {
        this.sdFileFunName = str;
    }
}

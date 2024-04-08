package com.picspool.lib.sysphotoselector;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class DMCommonSelectedManager {
    private static DMCommonSelectedManager instance;
    private HashMap<String, Integer> selectImgIdListMap = new HashMap<>();
    private int max = 9;

    private DMCommonSelectedManager() {
    }

    public static synchronized DMCommonSelectedManager getInstance() {
        DMCommonSelectedManager dMCommonSelectedManager;
        synchronized (DMCommonSelectedManager.class) {
            if (instance == null) {
                instance = new DMCommonSelectedManager();
            }
            dMCommonSelectedManager = instance;
        }
        return dMCommonSelectedManager;
    }

    public void putAllSelectedMap(Map<String, Integer> map) {
        this.selectImgIdListMap.putAll(map);
    }

    public void removeFromSelectImgList(String str) {
        if (this.selectImgIdListMap.containsKey(str)) {
            int intValue = Integer.valueOf(this.selectImgIdListMap.get(str).intValue()).intValue() - 1;
            if (intValue <= 0) {
                this.selectImgIdListMap.remove(str);
            } else {
                this.selectImgIdListMap.put(str, Integer.valueOf(intValue));
            }
        }
    }

    public void removeAllSelectImgList() {
        this.selectImgIdListMap.clear();
    }

    public void addToSelectImgList(String str) {
        if (this.selectImgIdListMap.containsKey(str)) {
            this.selectImgIdListMap.put(str, Integer.valueOf(Integer.valueOf(this.selectImgIdListMap.get(str).intValue()).intValue() + 1));
        } else {
            this.selectImgIdListMap.put(str, 1);
        }
    }

    public void resetSelectImgList() {
        this.selectImgIdListMap.clear();
    }

    public HashMap<String, Integer> getSelectImageList() {
        return this.selectImgIdListMap;
    }

    public boolean isContainsKey(String str) {
        return this.selectImgIdListMap.containsKey(str);
    }

    public int getKeyValue(String str) {
        if (isContainsKey(str)) {
            return this.selectImgIdListMap.get(str).intValue();
        }
        return 0;
    }

    public void setMax(int i) {
        this.max = i;
    }

    public boolean getCountOfAllValus() {
        int i = 0;
        for (Map.Entry<String, Integer> entry : this.selectImgIdListMap.entrySet()) {
            i += Integer.valueOf(String.valueOf(entry.getValue())).intValue();
        }
        return i < this.max;
    }
}

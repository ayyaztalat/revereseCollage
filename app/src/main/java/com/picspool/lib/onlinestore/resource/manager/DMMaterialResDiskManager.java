package com.picspool.lib.onlinestore.resource.manager;

import android.content.Context;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.picspool.lib.onlinestore.resource.WBMaterialFactory;
import com.picspool.lib.onlinestore.resource.WBMaterialRes;

/* loaded from: classes3.dex */
public class DMMaterialResDiskManager {
    private static String defaultKey = "default";
    private static Map<String, Boolean> downMaps;
    private static Map<String, List<WBMaterialRes>> maps;

    public static void UpdateDiskMaterialRes(Context context, String str) {
        if (maps == null) {
            maps = new HashMap();
        }
        if (downMaps == null) {
            downMaps = new HashMap();
        }
        List<WBMaterialRes> CreateMaterialFromFilesDir = WBMaterialFactory.CreateMaterialFromFilesDir(context, str);
        maps.put(str, CreateMaterialFromFilesDir);
        downMaps.put(str, false);
        Iterator<WBMaterialRes> it2 = CreateMaterialFromFilesDir.iterator();
        while (it2.hasNext()) {
            if (!it2.next().isContentExist()) {
                it2.remove();
                downMaps.put(str, true);
            }
        }
        if (CreateMaterialFromFilesDir.size() == 0) {
            downMaps.put(str, true);
        }
    }

    public static void UpdateDiskMaterialRes(Context context) {
        if (maps == null) {
            maps = new HashMap();
        }
        if (downMaps == null) {
            downMaps = new HashMap();
        }
        List<WBMaterialRes> CreateMaterialFromFilesDir = WBMaterialFactory.CreateMaterialFromFilesDir(context);
        maps.put(defaultKey, CreateMaterialFromFilesDir);
        downMaps.put(defaultKey, false);
        Iterator<WBMaterialRes> it2 = CreateMaterialFromFilesDir.iterator();
        while (it2.hasNext()) {
            if (!it2.next().isContentExist()) {
                it2.remove();
                downMaps.put(defaultKey, true);
            }
        }
        if (CreateMaterialFromFilesDir.size() == 0) {
            downMaps.put(defaultKey, true);
        }
    }

    public static List<WBMaterialRes> getDisRess() {
        Map<String, List<WBMaterialRes>> map = maps;
        if (map != null) {
            return map.get(defaultKey);
        }
        return null;
    }

    public static List<WBMaterialRes> getDisRess(String str) {
        Map<String, List<WBMaterialRes>> map = maps;
        if (map != null) {
            return map.get(str);
        }
        return null;
    }

    public static void clearList() {
        maps.clear();
    }

    public static boolean isNotDownload() {
        Map<String, Boolean> map = downMaps;
        if (map == null || map.get(defaultKey) == null) {
            return true;
        }
        return downMaps.get(defaultKey).booleanValue();
    }

    public static boolean isNotDownload(String str) {
        Map<String, Boolean> map = downMaps;
        if (map == null || map.get(str) == null) {
            return true;
        }
        return downMaps.get(str).booleanValue();
    }
}

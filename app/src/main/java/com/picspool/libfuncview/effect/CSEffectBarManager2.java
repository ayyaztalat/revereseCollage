package com.picspool.libfuncview.effect;

import android.content.Context;
import com.alibaba.fastjson.JSON;
import com.picspool.libfuncview.effect.onlinestore.resource.CSEffectDataWrapper;
import com.picspool.libfuncview.effect.res.CSEffectJsonRes;
import com.picspool.libfuncview.effect.res.CSEffectRes;
import com.picspool.libfuncview.effect.res.CSGroupJsonRes;
import com.picspool.libfuncview.onlinestore.CSMaterialFactory;
import com.picspool.libfuncview.res.CSGroupRes;
import com.picspool.libfuncview.res.CSResManagerInterface;
import com.picspool.libfuncview.utils.CSLibUiConfig;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes.dex */
public class CSEffectBarManager2 implements CSResManagerInterface {
    private static String EffectGroupIconName = "groupicon.png";
    private static String EffectGroupIconNameOL = "groupicon.data";
    private static String EffectGroupInfoName = "groupinfo.json";
    private static String EffectGroupInfoNameOL = "groupinfo.json";
    private static String EffectIconsName = "img_main_icon.png";
    private static String EffectIconsNameOL = "img_main_icon.data";
    private static String EffectImagesName = "img_main.png";
    private static String EffectImagesNameOL = "img_main.data";
    private static String EffectInfoName = "info.json";
    private static String EffectInfoNameOL = "info.json";
    private static String EffectItemStartString = "effect_";
    private static String EffectOrderKey = "EffectManagerOrder2";
    public static String EffectStoreResName = "store_add";
    private static final String TAG = "EffectManager";
    private String customeffectfilename = "effect/reses";
    private List<DMWBRes> groupResList = new ArrayList();
    private Context mContext;

    public CSEffectBarManager2(Context context, boolean z) {
        this.mContext = context;
        initLocalResGroup();
        initOnlineResGroup();
        listCollections();
    }

    public void listCollections() {
        Collections.sort(this.groupResList, new Comparator<DMWBRes>() { // from class: com.picspool.libfuncview.effect.CSEffectBarManager2.1
            @Override // java.util.Comparator
            public int compare(DMWBRes dMWBRes, DMWBRes dMWBRes2) {
                CSGroupRes cSGroupRes = (CSGroupRes) dMWBRes;
                CSGroupRes cSGroupRes2 = (CSGroupRes) dMWBRes2;
                if (cSGroupRes.getOrder() == cSGroupRes2.getOrder()) {
                    return 0;
                }
                return cSGroupRes.getOrder() > cSGroupRes2.getOrder() ? 1 : -1;
            }
        });
    }

    private void initLocalResGroup() {
        try {
            String[] list = this.mContext.getAssets().list(this.customeffectfilename);
            if (list != null) {
                for (String str : list) {
                    CSGroupRes cSGroupRes = (CSGroupRes) initLocalGroupItem(str);
                    if (cSGroupRes != null && cSGroupRes.getList_res().size() > 0) {
                        this.groupResList.add(cSGroupRes);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DMWBRes creatStoreRes() {
        CSGroupRes cSGroupRes = new CSGroupRes(this.mContext);
        cSGroupRes.setIconFileName("effect/effect_onlinestore.png");
        cSGroupRes.setIconType(DMWBRes.LocationType.ASSERT);
        cSGroupRes.setGroupType(CSGroupRes.GroupType.ASSERT);
        cSGroupRes.setName(EffectStoreResName);
        cSGroupRes.setOrder(-1);
        cSGroupRes.setShowText("Store");
        cSGroupRes.setOlFilePath("effect/effect_onlinestore.png");
        return cSGroupRes;
    }

    private DMWBRes initLocalGroupItem(String str) {
        int i;
        String[] strArr;
        try {
            String str2 = this.customeffectfilename + "/" + str;
            CSGroupRes cSGroupRes = new CSGroupRes(this.mContext);
            cSGroupRes.setIconType(DMWBRes.LocationType.ASSERT);
            cSGroupRes.setGroupType(CSGroupRes.GroupType.ASSERT);
            cSGroupRes.setOlFilePath(str2);
            String[] list = this.mContext.getAssets().list(str2);
            if (list != null) {
                int i2 = 0;
                while (i2 < list.length) {
                    if (list[i2].equals(EffectGroupIconName)) {
                        cSGroupRes.setIconFileName(str2 + "/" + EffectGroupIconName);
                    } else if (list[i2].equals(EffectGroupInfoName)) {
                        CSGroupJsonRes cSGroupJsonRes = (CSGroupJsonRes) JSON.parseObject(CSLibUiConfig.readFromAssets(this.mContext, str2 + "/" + EffectGroupInfoName), CSGroupJsonRes.class);
                        cSGroupRes.setName(cSGroupJsonRes.getName());
                        cSGroupRes.setOrder(getOrderKey());
                        cSGroupRes.setShowText(cSGroupJsonRes.getShowtext());
                        cSGroupRes.setItemcount(cSGroupJsonRes.getItemcount());
                        cSGroupRes.setGroup_unique_name(cSGroupJsonRes.getGroup_unique_name());
                    } else {
                        String str3 = str2 + "/" + list[i2];
                        CSEffectJsonRes cSEffectJsonRes = (CSEffectJsonRes) JSON.parseObject(CSLibUiConfig.readFromAssets(this.mContext, str3 + "/" + EffectInfoName), CSEffectJsonRes.class);
                        i = i2;
                        strArr = list;
                        DMWBRes initEffectItem = initEffectItem(cSEffectJsonRes.getName(), cSEffectJsonRes.getShowtext(), cSEffectJsonRes.getGroupname(), str3 + "/" + EffectImagesName, str3 + "/" + EffectIconsName, str3, cSEffectJsonRes.getGpufiltertype(), cSEffectJsonRes.getStrength(), cSEffectJsonRes.getRotation(), false, false);
                        if (initEffectItem != null) {
                            cSGroupRes.addRes(initEffectItem);
                        }
                        i2 = i + 1;
                        list = strArr;
                    }
                    i = i2;
                    strArr = list;
                    i2 = i + 1;
                    list = strArr;
                }
            }
            return cSGroupRes;
        } catch (IOException unused) {
            return null;
        }
    }

    public void initOnlineResGroup() {
        File file = null;
        String str;
        int i;
        int i2;
        File[] fileArr;
        CSGroupRes cSGroupRes;
        int i3;
        String str2 = "/";
        try {
            File[] listFiles = new File(getOnlineResPath()).listFiles();
            if (listFiles != null) {
                int length = listFiles.length;
                int i4 = 0;
                while (i4 < length) {
                    File file2 = listFiles[i4];
                    String str3 = file2.getPath() + str2 + "material";
                    String str4 = str3 + str2 + EffectGroupInfoNameOL;
                    if (new File(str4).exists()) {
                        CSGroupRes cSGroupRes2 = new CSGroupRes(this.mContext);
                        this.groupResList.add(cSGroupRes2);
                        cSGroupRes2.setIconType(DMWBRes.LocationType.ONLINE);
                        cSGroupRes2.setGroupType(CSGroupRes.GroupType.ONLINE);
                        cSGroupRes2.setIconFileName(str3 + str2 + EffectGroupIconNameOL);
                        CSGroupJsonRes cSGroupJsonRes = (CSGroupJsonRes) JSON.parseObject(CSLibUiConfig.readLocalTxtToString(str4), CSGroupJsonRes.class);
                        cSGroupRes2.setName(cSGroupJsonRes.getName());
                        cSGroupRes2.setOrder(getOrderKey());
                        cSGroupRes2.setShowText(cSGroupJsonRes.getShowtext());
                        cSGroupRes2.setOlFilePath(str3);
                        File[] listFiles2 = new File(str3).listFiles();
                        int length2 = listFiles2.length;
                        int i5 = 0;
                        while (i5 < length2) {
                            if (listFiles2[i5].getName().startsWith(EffectItemStartString)) {
                                String str5 = file.getPath() + str2 + EffectImagesNameOL;
                                String str6 = file.getPath() + str2 + EffectIconsNameOL;
                                String str7 = file.getPath() + str2 + EffectInfoNameOL;
                                File file3 = new File(str5);
                                File file4 = new File(str6);
                                File file5 = new File(str7);
                                if (file3.exists() && file4.exists() && file5.exists()) {
                                    CSEffectJsonRes cSEffectJsonRes = (CSEffectJsonRes) JSON.parseObject(CSLibUiConfig.readLocalTxtToString(str7), CSEffectJsonRes.class);
                                    i = i5;
                                    i2 = length2;
                                    fileArr = listFiles2;
                                    str = str2;
                                    cSGroupRes = cSGroupRes2;
                                    i3 = i4;
                                    DMWBRes initEffectItem = initEffectItem(cSEffectJsonRes.getName(), cSEffectJsonRes.getShowtext(), cSEffectJsonRes.getGroupname(), str5, str6, "", cSEffectJsonRes.getGpufiltertype(), cSEffectJsonRes.getStrength(), cSEffectJsonRes.getRotation(), true, false);
                                    if (initEffectItem != null) {
                                        cSGroupRes.addRes(initEffectItem);
                                    }
                                    i5 = i + 1;
                                    cSGroupRes2 = cSGroupRes;
                                    length2 = i2;
                                    listFiles2 = fileArr;
                                    str2 = str;
                                    i4 = i3;
                                }
                            }
                            str = str2;
                            i = i5;
                            i2 = length2;
                            fileArr = listFiles2;
                            cSGroupRes = cSGroupRes2;
                            i3 = i4;
                            i5 = i + 1;
                            cSGroupRes2 = cSGroupRes;
                            length2 = i2;
                            listFiles2 = fileArr;
                            str2 = str;
                            i4 = i3;
                        }
                    }
                    i4++;
                    str2 = str2;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifyDownloadResApply(String str) {
        List<DMWBRes> list = this.groupResList;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i = 0; i < this.groupResList.size(); i++) {
            CSGroupRes cSGroupRes = (CSGroupRes) this.groupResList.get(i);
            if (cSGroupRes.getOlFilePath().equals(str)) {
                cSGroupRes.setExpanded(true);
                return;
            }
        }
    }

    private DMWBRes initEffectItem(String str, String str2, String str3, String str4, String str5, String str6, String str7, int i, int i2, boolean z, boolean z2) {
        CSEffectRes cSEffectRes = new CSEffectRes(this.mContext);
        cSEffectRes.setName(str);
        cSEffectRes.setShowText(str2);
        cSEffectRes.setIconFileName(str5);
        cSEffectRes.setGroupName(str3);
        cSEffectRes.setOrder(getOrderKey());
        cSEffectRes.setOlFilePath(str6);
        cSEffectRes.setIconType(z ? DMWBRes.LocationType.ONLINE : DMWBRes.LocationType.ASSERT);
        cSEffectRes.setImageFileName(str4);
        cSEffectRes.setImageType(z ? DMWBRes.LocationType.ONLINE : DMWBRes.LocationType.ASSERT);
        GPUFilterType enumFromString = getEnumFromString(str7);
        if (enumFromString != null) {
            cSEffectRes.setFilterType(enumFromString);
        } else {
            cSEffectRes.setFilterType(GPUFilterType.BLEND_NORMAL);
        }
        cSEffectRes.setEffetStrength(i);
        cSEffectRes.setRotation(i2);
        cSEffectRes.setStoreAddIcon(z2);
        return cSEffectRes;
    }

    public List<DMWBRes> getGroupResList() {
        return this.groupResList;
    }

    private GPUFilterType getEnumFromString(String str) {
        if (str != null) {
            try {
                return (GPUFilterType) Enum.valueOf(GPUFilterType.class, str.trim());
            } catch (IllegalArgumentException unused) {
            }
        }
        return null;
    }

    @Override // com.picspool.libfuncview.res.CSResManagerInterface
    public String getOnlineResPath() {
        if (CSMaterialFactory.isSDAvailable()) {
            return CSEffectDataWrapper.DIR + "/" + CSEffectDataWrapper.EffectFunName;
        }
        File filesDir = this.mContext.getFilesDir();
        return filesDir.getAbsolutePath() + "/picsjoin/" + this.mContext.getPackageName() + "/" + CSEffectDataWrapper.EffectFunName;
    }

    @Override // com.picspool.libfuncview.res.CSResManagerInterface
    public String getOrderKey() {
        return EffectOrderKey;
    }

    @Override // com.picspool.libfuncview.res.CSResManagerInterface
    public List<CSGroupRes> getResGroupList() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.groupResList.size(); i++) {
            arrayList.add((CSGroupRes) this.groupResList.get(i));
        }
        return arrayList;
    }
}

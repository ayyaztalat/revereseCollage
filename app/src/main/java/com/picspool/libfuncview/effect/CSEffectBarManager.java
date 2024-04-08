package com.picspool.libfuncview.effect;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.alibaba.fastjson.JSON;
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
import java.util.Iterator;
import java.util.List;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes.dex */
public class CSEffectBarManager implements CSResManagerInterface {
    private static String EffectGroupIconName = "groupicon.jpg";
    private static String EffectIconsName = "img_main_icon.png";
    private static String EffectIconsNameOL = "img_icon.jpg";
    private static String EffectImagesName = "img_main.png";
    private static String EffectImagesNameOL = "img_main.data";
    private static String EffectInfoName = "info.json";
    private static String EffectInfoNameOL = "info.json";
    private static String EffectOrderKey = "EffectManagerOrder2";
    private static final String TAG = "EffectManager";
    private String customeffectfilename = "effect/reses";
    private List<DMWBRes> groupResList = new ArrayList();
    private Context mContext;

    public CSEffectBarManager(Context context) {
        this.mContext = context;
        initLocalResGroup();
        initOnlineResGroup();
        listCollections();
    }

    public void listCollections() {
        Collections.sort(this.groupResList, new Comparator<DMWBRes>() { // from class: com.picspool.libfuncview.effect.CSEffectBarManager.1
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
        if (this.groupResList.size() > 0) {
            Iterator<DMWBRes> it2 = this.groupResList.iterator();
            while (it2.hasNext()) {
                Collections.sort(((CSGroupRes) it2.next()).getList_res(), new Comparator<DMWBRes>() { // from class: com.picspool.libfuncview.effect.CSEffectBarManager.2
                    @Override // java.util.Comparator
                    public int compare(DMWBRes dMWBRes, DMWBRes dMWBRes2) {
                        if ((dMWBRes instanceof CSEffectRes) && (dMWBRes2 instanceof CSEffectRes)) {
                            if (dMWBRes.getName().endsWith("_add")) {
                                return 1;
                            }
                            CSEffectRes cSEffectRes = (CSEffectRes) dMWBRes;
                            CSEffectRes cSEffectRes2 = (CSEffectRes) dMWBRes2;
                            if (cSEffectRes.getOrder() == cSEffectRes2.getOrder()) {
                                return 0;
                            }
                            return cSEffectRes.getOrder() > cSEffectRes2.getOrder() ? 1 : -1;
                        }
                        return 0;
                    }
                });
            }
        }
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

    private DMWBRes initLocalGroupItem(String str) {
        int i;
        String[] strArr;
        CSGroupRes cSGroupRes;
        CSEffectBarManager cSEffectBarManager = this;
        try {
            String str2 = cSEffectBarManager.customeffectfilename + "/" + str;
            CSGroupRes cSGroupRes2 = new CSGroupRes(cSEffectBarManager.mContext);
            cSGroupRes2.setOlFilePath(str2);
            cSGroupRes2.setIconType(DMWBRes.LocationType.ASSERT);
            cSGroupRes2.setGroupType(CSGroupRes.GroupType.ASSERT);
            String[] list = cSEffectBarManager.mContext.getAssets().list(str2);
            if (list != null) {
                int i2 = 0;
                while (i2 < list.length) {
                    if (list[i2].equals(EffectGroupIconName)) {
                        cSGroupRes2.setIconFileName(str2 + "/" + EffectGroupIconName);
                    } else if (list[i2].equals(EffectInfoName)) {
                        CSGroupJsonRes cSGroupJsonRes = (CSGroupJsonRes) JSON.parseObject(CSLibUiConfig.readFromAssets(cSEffectBarManager.mContext, str2 + "/" + EffectInfoName), CSGroupJsonRes.class);
                        cSGroupRes2.setName(cSGroupJsonRes.getName());
                        cSGroupRes2.setOrder(getOrderKey());
                        cSGroupRes2.setShowText(cSGroupJsonRes.getShowtext());
                        cSGroupRes2.setItemcount(cSGroupJsonRes.getItemcount());
                        cSGroupRes2.setGroup_unique_name(cSGroupJsonRes.getGroup_unique_name());
                    } else {
                        String str3 = str2 + "/" + list[i2];
                        CSEffectJsonRes cSEffectJsonRes = (CSEffectJsonRes) JSON.parseObject(CSLibUiConfig.readFromAssets(cSEffectBarManager.mContext, str3 + "/" + EffectInfoName), CSEffectJsonRes.class);
                        i = i2;
                        strArr = list;
                        cSGroupRes = cSGroupRes2;
                        DMWBRes initEffectItem = initEffectItem(cSEffectJsonRes.getName(), cSEffectJsonRes.getShowtext(), cSEffectJsonRes.getGroupname(), str3 + "/" + EffectImagesName, str3 + "/" + EffectIconsName, str3, cSEffectJsonRes.getGpufiltertype(), cSEffectJsonRes.getStrength(), cSEffectJsonRes.getRotation(), false, false);
                        if (initEffectItem != null) {
                            cSGroupRes.addRes(initEffectItem);
                        }
                        i2 = i + 1;
                        cSGroupRes2 = cSGroupRes;
                        list = strArr;
                        cSEffectBarManager = this;
                    }
                    i = i2;
                    strArr = list;
                    cSGroupRes = cSGroupRes2;
                    i2 = i + 1;
                    cSGroupRes2 = cSGroupRes;
                    list = strArr;
                    cSEffectBarManager = this;
                }
            }
            CSGroupRes cSGroupRes3 = cSGroupRes2;
            cSGroupRes3.addRes(initEffectItem(str + "_add", "Add", str, "", "effect/effect_onlinestore.png", "", null, 0, 0, false, true));
            return cSGroupRes3;
        } catch (IOException unused) {
            return null;
        }
    }

    public void initOnlineResGroup() {
        boolean z;
        int i;
        try {
            File[] listFiles = new File(getOnlineResPath()).listFiles();
            if (listFiles != null) {
                CSGroupRes cSGroupRes = new CSGroupRes(this.mContext);
                cSGroupRes.setIconType(DMWBRes.LocationType.ONLINE);
                int i2 = 0;
                while (i2 < listFiles.length) {
                    if (listFiles[i2] != null) {
                        String str = listFiles[i2].getPath() + "/" + listFiles[i2].getName();
                        File file = new File(str);
                        if (file.exists()) {
                            String str2 = file.getPath() + "/" + EffectImagesNameOL;
                            file.getPath();
                            String str3 = file.getPath() + "/" + EffectInfoNameOL;
                            File file2 = new File(str2);
                            File file3 = new File(str3);
                            if (file2.exists() && file3.exists()) {
                                CSEffectJsonRes cSEffectJsonRes = (CSEffectJsonRes) JSON.parseObject(CSLibUiConfig.readLocalTxtToString(str3), CSEffectJsonRes.class);
                                i = i2;
                                DMWBRes initEffectItem = initEffectItem(cSEffectJsonRes.getName(), cSEffectJsonRes.getShowtext(), cSEffectJsonRes.getGroupname(), str2, str2, str, cSEffectJsonRes.getGpufiltertype(), cSEffectJsonRes.getStrength(), cSEffectJsonRes.getRotation(), true, false);
                                if (initEffectItem != null) {
                                    cSGroupRes.addRes(initEffectItem);
                                }
                                i2 = i + 1;
                            }
                        }
                    }
                    i = i2;
                    i2 = i + 1;
                }
                if (cSGroupRes.getList_res().size() <= 0 || cSGroupRes.getList_res().size() <= 0 || this.groupResList.size() <= 0) {
                    return;
                }
                for (int i3 = 0; i3 < cSGroupRes.getList_res().size(); i3++) {
                    CSEffectRes cSEffectRes = (CSEffectRes) cSGroupRes.getList_res().get(i3);
                    String groupName = cSEffectRes.getGroupName();
                    for (int i4 = 0; i4 < this.groupResList.size(); i4++) {
                        if (this.groupResList.get(i4).getName().equals(groupName)) {
                            List<DMWBRes> list_res = ((CSGroupRes) this.groupResList.get(i4)).getList_res();
                            Iterator<DMWBRes> it2 = list_res.iterator();
                            while (true) {
                                if (it2.hasNext()) {
                                    if (it2.next().getName() == cSEffectRes.getName()) {
                                        z = true;
                                        break;
                                    }
                                } else {
                                    z = false;
                                    break;
                                }
                            }
                            if (!z) {
                                list_res.add(list_res.size() - 1, cSEffectRes);
                            }
                        }
                    }
                }
            }
        } catch (IOException unused) {
        }
    }

    public int notifyDownloadResApply(String str) {
        File file = new File(str);
        int i = -1;
        if (file.exists()) {
            String str2 = file.getPath() + "/" + EffectImagesNameOL;
            file.getPath();
            String str3 = file.getPath() + "/" + EffectInfoNameOL;
            File file2 = new File(str2);
            File file3 = new File(str3);
            if (file2.exists() && file3.exists()) {
                try {
                    CSEffectJsonRes cSEffectJsonRes = (CSEffectJsonRes) JSON.parseObject(CSLibUiConfig.readLocalTxtToString(str3), CSEffectJsonRes.class);
                    CSEffectRes cSEffectRes = (CSEffectRes) initEffectItem(cSEffectJsonRes.getName(), cSEffectJsonRes.getShowtext(), cSEffectJsonRes.getGroupname(), str2, str2, str, cSEffectJsonRes.getGpufiltertype(), cSEffectJsonRes.getStrength(), cSEffectJsonRes.getRotation(), true, false);
                    String groupName = cSEffectRes.getGroupName();
                    String name = cSEffectRes.getName();
                    CSGroupRes cSGroupRes = null;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= this.groupResList.size()) {
                            i2 = -1;
                            break;
                        }
                        CSGroupRes cSGroupRes2 = (CSGroupRes) this.groupResList.get(i2);
                        if (cSGroupRes2.getName().equals(groupName)) {
                            cSGroupRes2.setExpanded(true);
                            cSGroupRes = cSGroupRes2;
                            break;
                        }
                        i2++;
                    }
                    if (i2 >= 0 && cSGroupRes != null) {
                        int size = cSGroupRes.getList_res().size();
                        for (int i3 = 0; i3 < size; i3++) {
                            DMWBRes dMWBRes = cSGroupRes.getList_res().get(i3);
                            int i4 = i2 + i3 + 1;
                            this.groupResList.add(i4, dMWBRes);
                            if (name.equals(dMWBRes.getName())) {
                                i = i4;
                            }
                        }
                        return i;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "notifyDownloadResApply: " + e.toString());
                } catch (Throwable th) {
                    th.printStackTrace();
                    Log.d(TAG, "notifyDownloadResApply: " + th.toString());
                    return -1;
                }
            }
        }
        return -1;
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

    private CSGroupRes initLocalGroup(String str, String str2, String str3) {
        CSGroupRes cSGroupRes = new CSGroupRes(this.mContext);
        cSGroupRes.setName(str);
        cSGroupRes.setOrder(getOrderKey());
        cSGroupRes.setIconFileName(str2);
        cSGroupRes.setIconType(DMWBRes.LocationType.ASSERT);
        cSGroupRes.setGroupType(CSGroupRes.GroupType.ASSERT);
        cSGroupRes.setShowText(str3);
        if (str.equals("blend1")) {
            cSGroupRes.addRes(initGroupItems("blend", "effect/blend/blend_icon_1.jpg", "effect/blend/blend_img_1.jpg", "blend1"));
            cSGroupRes.addRes(initGroupItems("blend", "effect/blend/blend_icon_5.jpg", "effect/blend/blend_img_5.jpg", "blend2"));
            cSGroupRes.addRes(initGroupItems("blend", "effect/blend/blend_icon_1.jpg", "effect/blend/blend_img_1.jpg", "blend3"));
            cSGroupRes.addRes(initGroupItems("blend", "effect/blend/blend_icon_5.jpg", "effect/blend/blend_img_5.jpg", "blend2"));
        } else if (str.equals("raindrop")) {
            cSGroupRes.addRes(initGroupItems("", "effect/raindrop/raindrop_01/img_main.jpg", "effect/raindrop/raindrop_01/img_main.jpg", "R1"));
            cSGroupRes.addRes(initGroupItems("", "effect/raindrop/raindrop_02/img_main.jpg", "effect/raindrop/raindrop_02/img_main.jpg", "R2"));
            cSGroupRes.addRes(initGroupItems("", "effect/raindrop/raindrop_03/img_main.jpg", "effect/raindrop/raindrop_03/img_main.jpg", "R3"));
            cSGroupRes.addRes(initGroupItems("", "effect/raindrop/raindrop_04/img_main.jpg", "effect/raindrop/raindrop_04/img_main.jpg", "R4"));
            cSGroupRes.addRes(initGroupItems("", "effect/raindrop/raindrop_05/img_main.jpg", "effect/raindrop/raindrop_05/img_main.jpg", "R5"));
            cSGroupRes.addRes(initGroupItems("", "effect/raindrop/raindrop_06/img_main.jpg", "effect/raindrop/raindrop_06/img_main.jpg", "R6"));
            cSGroupRes.addRes(initGroupItems("", "effect/raindrop/raindrop_07/img_main.jpg", "effect/raindrop/raindrop_07/img_main.jpg", "R7"));
            cSGroupRes.addRes(initGroupItems("", "effect/raindrop/raindrop_08/img_main.jpg", "effect/raindrop/raindrop_08/img_main.jpg", "R8"));
        } else if (str.equals("grain")) {
            cSGroupRes.addRes(initGroupItems("", "effect/grain/grain_01/img_main.jpg", "effect/grain/grain_01/img_main.jpg", "G1"));
            cSGroupRes.addRes(initGroupItems("", "effect/grain/grain_02/img_main.jpg", "effect/grain/grain_02/img_main.jpg", "G2"));
            cSGroupRes.addRes(initGroupItems("", "effect/grain/grain_03/img_main.jpg", "effect/grain/grain_03/img_main.jpg", "G3"));
            cSGroupRes.addRes(initGroupItems("", "effect/grain/grain_04/img_main.jpg", "effect/grain/grain_04/img_main.jpg", "G4"));
            cSGroupRes.addRes(initGroupItems("", "effect/grain/grain_05/img_main.jpg", "effect/grain/grain_05/img_main.jpg", "G5"));
            cSGroupRes.addRes(initGroupItems("", "effect/grain/grain_06/img_main.jpg", "effect/grain/grain_06/img_main.jpg", "G6"));
        } else if (str.equals("leak")) {
            cSGroupRes.addRes(initGroupItems("", "effect/leak/blend_screen_leak_01/blend_screen_leak_01.jpg", "effect/leak/blend_screen_leak_01/blend_screen_leak_01.jpg", "L1"));
            cSGroupRes.addRes(initGroupItems("", "effect/leak/blend_screen_leak_02/blend_screen_leak_02.jpg", "effect/leak/blend_screen_leak_02/blend_screen_leak_02.jpg", "L2"));
            cSGroupRes.addRes(initGroupItems("", "effect/leak/blend_screen_leak_03/blend_screen_leak_03.jpg", "effect/leak/blend_screen_leak_03/blend_screen_leak_03.jpg", "L3"));
            cSGroupRes.addRes(initGroupItems("", "effect/leak/blend_screen_leak_04/blend_screen_leak_04.jpg", "effect/leak/blend_screen_leak_04/blend_screen_leak_04.jpg", "L4"));
            cSGroupRes.addRes(initGroupItems("", "effect/leak/blend_screen_leak_05/blend_screen_leak_05.jpg", "effect/leak/blend_screen_leak_05/blend_screen_leak_05.jpg", "L5"));
            cSGroupRes.addRes(initGroupItems("", "effect/leak/blend_screen_leak_06/blend_screen_leak_06.jpg", "effect/leak/blend_screen_leak_06/blend_screen_leak_06.jpg", "L6"));
            cSGroupRes.addRes(initGroupItems("", "effect/leak/blend_screen_leak_23/blend_screen_leak_23.jpg", "effect/leak/blend_screen_leak_23/blend_screen_leak_23.jpg", "L7"));
            cSGroupRes.addRes(initGroupItems("", "effect/leak/blend_screen_leak_24/blend_screen_leak_24.jpg", "effect/leak/blend_screen_leak_24/blend_screen_leak_24.jpg", "L8"));
            cSGroupRes.addRes(initGroupItems("", "effect/leak/blend_screen_leak_25/blend_screen_leak_25.jpg", "effect/leak/blend_screen_leak_25/blend_screen_leak_25.jpg", "L9"));
            cSGroupRes.addRes(initGroupItems("", "effect/leak/blend_screen_leak_26/blend_screen_leak_26.jpg", "effect/leak/blend_screen_leak_26/blend_screen_leak_26.jpg", "L10"));
            cSGroupRes.addRes(initGroupItems("", "effect/leak/blend_screen_leak_27/blend_screen_leak_27.jpg", "effect/leak/blend_screen_leak_27/blend_screen_leak_27.jpg", "L11"));
            cSGroupRes.addRes(initGroupItems("", "effect/leak/blend_screen_leak_28/blend_screen_leak_28.jpg", "effect/leak/blend_screen_leak_28/blend_screen_leak_28.jpg", "L12"));
            cSGroupRes.addRes(initGroupItems("", "effect/leak/blend_screen_leak_29/blend_screen_leak_29.jpg", "effect/leak/blend_screen_leak_29/blend_screen_leak_29.jpg", "L13"));
            cSGroupRes.addRes(initGroupItems("", "effect/leak/blend_screen_leak_31/blend_screen_leak_31.jpg", "effect/leak/blend_screen_leak_31/blend_screen_leak_31.jpg", "L14"));
            cSGroupRes.addRes(initGroupItems("", "effect/leak/blend_screen_leak_32/blend_screen_leak_32.jpg", "effect/leak/blend_screen_leak_32/blend_screen_leak_32.jpg", "L15"));
            cSGroupRes.addRes(initGroupItems("", "effect/leak/blend_screen_leak_33/blend_screen_leak_33.jpg", "effect/leak/blend_screen_leak_33/blend_screen_leak_33.jpg", "L16"));
            cSGroupRes.addRes(initGroupItems("", "effect/leak/blend_screen_leak_97/blend_screen_leak_97.jpg", "effect/leak/blend_screen_leak_97/blend_screen_leak_97.jpg", "L17"));
        } else if (str.equals("spot")) {
            cSGroupRes.addRes(initGroupItems("", "effect/spot/blend_screen_spot_01/blend_screen_spot_01.jpg", "effect/spot/blend_screen_spot_01/blend_screen_spot_01.jpg", "S1"));
            cSGroupRes.addRes(initGroupItems("", "effect/spot/blend_screen_spot_02/blend_screen_spot_02.jpg", "effect/spot/blend_screen_spot_02/blend_screen_spot_02.jpg", "S2"));
            cSGroupRes.addRes(initGroupItems("", "effect/spot/blend_screen_spot_03/blend_screen_spot_03.jpg", "effect/spot/blend_screen_spot_03/blend_screen_spot_03.jpg", "S3"));
            cSGroupRes.addRes(initGroupItems("", "effect/spot/blend_screen_spot_04/blend_screen_spot_04.jpg", "effect/spot/blend_screen_spot_04/blend_screen_spot_04.jpg", "S4"));
            cSGroupRes.addRes(initGroupItems("", "effect/spot/blend_screen_spot_05/blend_screen_spot_05.jpg", "effect/spot/blend_screen_spot_05/blend_screen_spot_05.jpg", "S5"));
            cSGroupRes.addRes(initGroupItems("", "effect/spot/blend_screen_spot_06/blend_screen_spot_06.jpg", "effect/spot/blend_screen_spot_06/blend_screen_spot_06.jpg", "S6"));
            cSGroupRes.addRes(initGroupItems("", "effect/spot/blend_screen_spot_07/blend_screen_spot_07.jpg", "effect/spot/blend_screen_spot_07/blend_screen_spot_07.jpg", "S7"));
            cSGroupRes.addRes(initGroupItems("", "effect/spot/blend_screen_spot_08/blend_screen_spot_08.jpg", "effect/spot/blend_screen_spot_08/blend_screen_spot_08.jpg", "S8"));
            cSGroupRes.addRes(initGroupItems("", "effect/spot/blend_screen_spot_18/blend_screen_spot_18.jpg", "effect/spot/blend_screen_spot_18/blend_screen_spot_18.jpg", "S9"));
            cSGroupRes.addRes(initGroupItems("", "effect/spot/blend_screen_spot_19/blend_screen_spot_19.jpg", "effect/spot/blend_screen_spot_19/blend_screen_spot_19.jpg", "S10"));
            cSGroupRes.addRes(initGroupItems("", "effect/spot/blend_screen_spot_21/blend_screen_spot_21.jpg", "effect/spot/blend_screen_spot_21/blend_screen_spot_21.jpg", "S11"));
            cSGroupRes.addRes(initGroupItems("", "effect/spot/blend_screen_spot_98/blend_screen_spot_98.jpg", "effect/spot/blend_screen_spot_98/blend_screen_spot_98.jpg", "S12"));
        } else if (str.equals("weather")) {
            cSGroupRes.addRes(initGroupItems("", "effect/weather/weather_01/weather_01.jpg", "effect/weather/weather_01/weather_01.jpg", "W1"));
            cSGroupRes.addRes(initGroupItems("", "effect/weather/weather_02/weather_02.jpg", "effect/weather/weather_02/weather_02.jpg", "W2"));
            cSGroupRes.addRes(initGroupItems("", "effect/weather/weather_03/weather_03.jpg", "effect/weather/weather_03/weather_03.jpg", "W3"));
            cSGroupRes.addRes(initGroupItems("", "effect/weather/weather_04/weather_04.jpg", "effect/weather/weather_04/weather_04.jpg", "W4"));
            cSGroupRes.addRes(initGroupItems("", "effect/weather/weather_05/weather_05.jpg", "effect/weather/weather_05/weather_05.jpg", "W5"));
        } else if (str.equals("halo")) {
            cSGroupRes.addRes(initGroupItems("", "effect/halo/halo_11/halo_11.jpg", "effect/halo/halo_11/halo_11.jpg", "H1"));
            cSGroupRes.addRes(initGroupItems("", "effect/halo/halo_12/halo_12.jpg", "effect/halo/halo_12/halo_12.jpg", "H2"));
            cSGroupRes.addRes(initGroupItems("", "effect/halo/halo_14/halo_14.jpg", "effect/halo/halo_14/halo_14.jpg", "H3"));
            cSGroupRes.addRes(initGroupItems("", "effect/halo/halo_15/halo_15.jpg", "effect/halo/halo_15/halo_15.jpg", "H4"));
            cSGroupRes.addRes(initGroupItems("", "effect/halo/halo_16/halo_16.jpg", "effect/halo/halo_16/halo_16.jpg", "H5"));
            cSGroupRes.addRes(initGroupItems("", "effect/halo/halo_17/halo_17.jpg", "effect/halo/halo_17/halo_17.jpg", "H6"));
            cSGroupRes.addRes(initGroupItems("", "effect/halo/halo_22/halo_22.jpg", "effect/halo/halo_22/halo_22.jpg", "H7"));
            cSGroupRes.addRes(initGroupItems("", "effect/halo/halo_50/halo_50.jpg", "effect/halo/halo_50/halo_50.jpg", "H8"));
            cSGroupRes.addRes(initGroupItems("", "effect/halo/halo_51/halo_51.jpg", "effect/halo/halo_51/halo_51.jpg", "H9"));
            cSGroupRes.addRes(initGroupItems("", "effect/halo/halo_52/halo_52.jpg", "effect/halo/halo_52/halo_52.jpg", "H10"));
            cSGroupRes.addRes(initGroupItems("", "effect/halo/halo_53/halo_53.jpg", "effect/halo/halo_53/halo_53.jpg", "H11"));
            cSGroupRes.addRes(initGroupItems("", "effect/halo/halo_54/halo_54.jpg", "effect/halo/halo_54/halo_54.jpg", "H12"));
            cSGroupRes.addRes(initGroupItems("", "effect/halo/halo_55/halo_55.jpg", "effect/halo/halo_55/halo_55.jpg", "H13"));
            cSGroupRes.addRes(initGroupItems("", "effect/halo/halo_56/halo_56.jpg", "effect/halo/halo_56/halo_56.jpg", "H14"));
            cSGroupRes.addRes(initGroupItems("", "effect/halo/halo_57/halo_57.jpg", "effect/halo/halo_57/halo_57.jpg", "H15"));
        } else if (str.equals("swatches")) {
            cSGroupRes.addRes(initGroupItems("", "effect/swatches/swatches_01/swatches_01.jpg", "effect/swatches/swatches_01/swatches_01.jpg", "S1"));
            cSGroupRes.addRes(initGroupItems("", "effect/swatches/swatches_02/swatches_02.jpg", "effect/swatches/swatches_02/swatches_02.jpg", "S2"));
            cSGroupRes.addRes(initGroupItems("", "effect/swatches/swatches_03/swatches_03.jpg", "effect/swatches/swatches_03/swatches_03.jpg", "S3"));
            cSGroupRes.addRes(initGroupItems("", "effect/swatches/swatches_04/swatches_04.jpg", "effect/swatches/swatches_04/swatches_04.jpg", "S4"));
            cSGroupRes.addRes(initGroupItems("", "effect/swatches/swatches_05/swatches_05.jpg", "effect/swatches/swatches_05/swatches_05.jpg", "S5"));
            cSGroupRes.addRes(initGroupItems("", "effect/swatches/swatches_06/swatches_06.jpg", "effect/swatches/swatches_06/swatches_06.jpg", "S6"));
            cSGroupRes.addRes(initGroupItems("", "effect/swatches/swatches_07/swatches_07.jpg", "effect/swatches/swatches_07/swatches_07.jpg", "S7"));
        } else {
            cSGroupRes.addRes(initGroupItems("add", "effect/effect_add.png", "effect/blend/blend_img_5.jpg", "Sky"));
        }
        return cSGroupRes;
    }

    private DMWBRes initGroupItems(String str, String str2, String str3, String str4) {
        CSEffectRes cSEffectRes = new CSEffectRes(this.mContext);
        cSEffectRes.setName(str);
        cSEffectRes.setGroupName(str + "group");
        cSEffectRes.setOrder(getOrderKey());
        cSEffectRes.setIconFileName(str2);
        cSEffectRes.setIconType(DMWBRes.LocationType.ASSERT);
        cSEffectRes.setImageFileName(str3);
        cSEffectRes.setImageType(DMWBRes.LocationType.ASSERT);
        cSEffectRes.setShowText(str4);
        return cSEffectRes;
    }

    public List<DMWBRes> getGroupResList() {
        return this.groupResList;
    }

    public List<DMWBRes> getGroupBMWBResList() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.groupResList.size(); i++) {
            arrayList.add(this.groupResList.get(i));
        }
        return arrayList;
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
        String packageName = this.mContext.getApplicationContext().getPackageName();
        if (CSMaterialFactory.isSDAvailable()) {
            String path = Environment.getExternalStorageDirectory().getPath();
            return path + "/" + packageName + CSMaterialFactory.SDRootDirName + "/.material/" + CSMaterialFactory.EffectFunName;
        }
        File filesDir = this.mContext.getFilesDir();
        return filesDir.getAbsolutePath() + "/" + packageName + CSMaterialFactory.SDRootDirName + "/.material/" + CSMaterialFactory.EffectFunName;
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

package com.picspool.libfuncview.effect.onlinestore.resource;

import android.content.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.picspool.libfuncview.effect.onlinestore.resource.CSNetJsonCache;
import com.picspool.libfuncview.res.CSMaterialGroupJsonRes;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;

/* loaded from: classes.dex */
public class CSEffectDataWrapper extends Observable {
    public static String DIR = "";
    public static final String EFFECT_NET_URL = "http://s1.picsjoin.com/Material_library/public/V1/FastSquareEditor/getGroupFilters?statue=2";
    public static final String EffectContentFileName = "material";
    public static final String EffectFunName = "effects";
    public static final String EffectIconZipFileName = "desicons";
    private static CSEffectDataWrapper effectDataWrapper;
    private List<CSEMaterialGroupRes> wbMaterialGroupResList = new ArrayList();

    private CSEffectDataWrapper(Context context) {
        DIR = context.getExternalFilesDir(null).getAbsolutePath() + "/effect";
        setChanged();
    }

    public static CSEffectDataWrapper getInstance(Context context) {
        if (effectDataWrapper == null) {
            synchronized (CSEffectDataWrapper.class) {
                if (effectDataWrapper == null) {
                    effectDataWrapper = new CSEffectDataWrapper(context);
                }
            }
        }
        return effectDataWrapper;
    }

    public void getData(final Context context) {
        final CSNetJsonCache cSNetJsonCache = new CSNetJsonCache(context);
        cSNetJsonCache.setCacheCallback(new CSNetJsonCache.CacheCallback() { // from class: com.picspool.libfuncview.effect.onlinestore.resource.CSEffectDataWrapper.1
            @Override // com.picspool.libfuncview.effect.onlinestore.resource.CSNetJsonCache.CacheCallback
            public void dataError() {
            }

            @Override // com.picspool.libfuncview.effect.onlinestore.resource.CSNetJsonCache.CacheCallback
            public void jsonDown(String str) {
                cSNetJsonCache.setNetApiMaxAge(context, CSEffectDataWrapper.EFFECT_NET_URL, 864000000L);
                CSEffectDataWrapper.this.parseJson(str, context);
            }
        });
        if (cSNetJsonCache.isExpires(context, EFFECT_NET_URL)) {
            if (cSNetJsonCache.isMaxSet(context, EFFECT_NET_URL)) {
                cSNetJsonCache.getJsonFromNet(EFFECT_NET_URL, 1);
                return;
            } else {
                cSNetJsonCache.getJsonFromNet(EFFECT_NET_URL, 0);
                return;
            }
        }
        parseJson(cSNetJsonCache.get(EFFECT_NET_URL), context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseJson(String str, Context context) {
        if (str == null) {
            return;
        }
        try {
            List<CSMaterialGroupJsonRes.DataBean> data = ((CSMaterialGroupJsonRes) JSON.parseObject(str, CSMaterialGroupJsonRes.class)).getData();
            this.wbMaterialGroupResList = new ArrayList();
            for (int i = 0; i < data.size(); i++) {
                this.wbMaterialGroupResList.add(initGroupItem(context, data.get(i)));
            }
            Collections.sort(this.wbMaterialGroupResList, new Comparator<CSEMaterialGroupRes>() { // from class: com.picspool.libfuncview.effect.onlinestore.resource.CSEffectDataWrapper.2
                @Override // java.util.Comparator
                public int compare(CSEMaterialGroupRes cSEMaterialGroupRes, CSEMaterialGroupRes cSEMaterialGroupRes2) {
                    if (cSEMaterialGroupRes.getGroupOrder() == cSEMaterialGroupRes2.getGroupOrder()) {
                        return 0;
                    }
                    return cSEMaterialGroupRes.getGroupOrder() > cSEMaterialGroupRes2.getGroupOrder() ? 1 : -1;
                }
            });
            notifyObservers();
        } catch (JSONException | NullPointerException unused) {
        }
    }

    private CSEMaterialGroupRes initGroupItem(Context context, CSMaterialGroupJsonRes.DataBean dataBean) {
        CSEMaterialGroupRes cSEMaterialGroupRes = new CSEMaterialGroupRes();
        cSEMaterialGroupRes.setGroupName(dataBean.getName());
        cSEMaterialGroupRes.setGroupIconUriPath(dataBean.getIcon());
        try {
            cSEMaterialGroupRes.setGroupOrder(Integer.parseInt(dataBean.getSort_num()));
        } catch (NumberFormatException unused) {
            cSEMaterialGroupRes.setGroupOrder(0);
        }
        List<CSMaterialGroupJsonRes.DataBean.ConfBean> conf = dataBean.getConf();
        for (int i = 0; i < conf.size(); i++) {
            cSEMaterialGroupRes.addContentItem(initContentItem(context, conf.get(i)));
        }
        return cSEMaterialGroupRes;
    }

    private CSEMaterialRes initContentItem(Context context, CSMaterialGroupJsonRes.DataBean.ConfBean confBean) {
        CSEMaterialRes cSEMaterialRes = new CSEMaterialRes();
        cSEMaterialRes.setUniqid(confBean.getUniqid());
        cSEMaterialRes.setPosition(confBean.getPosition());
        cSEMaterialRes.setIs_lock(confBean.getIs_lock());
        cSEMaterialRes.setIs_hot(confBean.getIs_hot());
        cSEMaterialRes.setIs_new(confBean.getIs_new());
        cSEMaterialRes.setIs_rec(confBean.getIs_rec());
        cSEMaterialRes.setIs_m_banner(confBean.getIs_m_banner());
        cSEMaterialRes.setIs_h_banner(confBean.getIs_h_banner());
        cSEMaterialRes.setIs_h_cell(confBean.getIs_h_cell());
        cSEMaterialRes.setIs_paid(confBean.getIs_paid());
        cSEMaterialRes.setSort_num(confBean.getSort_num());
        cSEMaterialRes.setMin_version(confBean.getMin_version());
        cSEMaterialRes.setMax_version(confBean.getMax_version());
        cSEMaterialRes.setUpdate_time(confBean.getUpdate_time());
        cSEMaterialRes.setG_id(confBean.getG_id());
        CSMaterialGroupJsonRes.DataBean.ConfBean.MaterialBean material = confBean.getMaterial();
        cSEMaterialRes.setId(material.getId());
        cSEMaterialRes.setName(material.getName());
        cSEMaterialRes.setIcon(material.getIcon());
        cSEMaterialRes.setImage(material.getImage());
        cSEMaterialRes.setBanner(material.getBanner());
        cSEMaterialRes.setEffect_zip(material.getEffect_zip());
        cSEMaterialRes.setData_zip(material.getData_zip());
        cSEMaterialRes.setData_size(material.getData_size());
        cSEMaterialRes.setData_number(material.getData_number());
        cSEMaterialRes.setDesc(material.getDesc());
        String str = DIR + "/" + EffectFunName + "/" + confBean.getUniqid();
        String str2 = str + "/material";
        String str3 = str + "/" + EffectIconZipFileName + "/";
        cSEMaterialRes.setRootFileName(str);
        cSEMaterialRes.setContentFilePath(str2);
        cSEMaterialRes.setContentDownFilePath(str2 + "/" + confBean.getUniqid() + ".zip");
        cSEMaterialRes.setDesiconFilePath(str3);
        if (material.getEffect_zip().substring(material.getEffect_zip().lastIndexOf(".") + 1).equals("zip")) {
            cSEMaterialRes.setDesiconsZipFilePath(str3 + cSEMaterialRes.getUniqid() + ".zip");
        } else {
            cSEMaterialRes.setDesiconsZipFilePath(str3 + cSEMaterialRes.getUniqid());
        }
        File file = new File(cSEMaterialRes.getContentFilePath());
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(cSEMaterialRes.getDesiconFilePath());
        if (!file2.exists()) {
            file2.mkdirs();
        }
        return cSEMaterialRes;
    }

    public List<CSEMaterialGroupRes> getWbMaterialGroupResList() {
        return this.wbMaterialGroupResList;
    }

    public CSEMaterialRes getEffectByUniqid(String str) {
        for (CSEMaterialGroupRes cSEMaterialGroupRes : this.wbMaterialGroupResList) {
            for (CSEMaterialRes cSEMaterialRes : cSEMaterialGroupRes.getWBMaterialResList()) {
                if (cSEMaterialRes.getUniqid().equals(str)) {
                    return cSEMaterialRes;
                }
            }
        }
        return null;
    }
}

package com.picspool.libfuncview.effect.onlinestore.manager;

import android.content.Context;
import com.picspool.libfuncview.effect.onlinestore.resource.CSEMaterialDStoreRes;
import com.picspool.libfuncview.effect.onlinestore.resource.CSEMaterialGroupRes;
import com.picspool.libfuncview.effect.onlinestore.resource.CSEMaterialRes;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CSOLSWBEEffectManager {
    private Context mContext;
    private List<CSEMaterialDStoreRes> wbeMaterialDStoreResList = new ArrayList();

    public CSOLSWBEEffectManager(Context context, List<CSEMaterialGroupRes> list) {
        this.mContext = context;
        CSEMaterialDStoreRes cSEMaterialDStoreRes = new CSEMaterialDStoreRes();
        cSEMaterialDStoreRes.setItemType(CSEMaterialDStoreRes.ItemType.VPBANNER);
        this.wbeMaterialDStoreResList.add(cSEMaterialDStoreRes);
        for (CSEMaterialGroupRes cSEMaterialGroupRes : list) {
            CSEMaterialDStoreRes cSEMaterialDStoreRes2 = new CSEMaterialDStoreRes();
            cSEMaterialDStoreRes2.setItemType(CSEMaterialDStoreRes.ItemType.TITLE);
            cSEMaterialDStoreRes2.setShowText(cSEMaterialGroupRes.getGroupName());
            this.wbeMaterialDStoreResList.add(cSEMaterialDStoreRes2);
            for (CSEMaterialRes cSEMaterialRes : cSEMaterialGroupRes.getWBMaterialResList()) {
                if (cSEMaterialRes.getIs_m_banner().equals("1")) {
                    cSEMaterialDStoreRes.getBmwbResList().add(cSEMaterialRes);
                }
                CSEMaterialDStoreRes cSEMaterialDStoreRes3 = new CSEMaterialDStoreRes();
                cSEMaterialDStoreRes3.setItemType(CSEMaterialDStoreRes.ItemType.NORMAL);
                cSEMaterialDStoreRes3.setWbeMaterialRes(cSEMaterialRes);
                this.wbeMaterialDStoreResList.add(cSEMaterialDStoreRes3);
            }
        }
    }

    public List<CSEMaterialDStoreRes> getWbeMaterialDStoreResList() {
        return this.wbeMaterialDStoreResList;
    }
}

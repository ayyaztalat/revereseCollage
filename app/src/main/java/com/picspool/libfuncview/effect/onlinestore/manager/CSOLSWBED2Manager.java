package com.picspool.libfuncview.effect.onlinestore.manager;

import android.content.Context;
import com.picspool.libfuncview.effect.onlinestore.resource.CSAsyncDownloadFileLoad;
import com.picspool.libfuncview.effect.onlinestore.resource.CSEMaterialD2Res;
import com.picspool.libfuncview.effect.onlinestore.resource.CSEMaterialRes;
import com.picspool.libfuncview.utils.CSLibUiConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipException;
import com.picspool.lib.sysutillib.DMPreferencesUtil;

/* loaded from: classes.dex */
public class CSOLSWBED2Manager {
    private static final String EFFECT_OL_DESICONS_REQUEST_MANAGER = "online_store_effect_desicons_cache_expiration";
    private static final String TAG = "CSOLSWBED2Manager";
    private static String itemNameEndwith = ".data";
    private List<CSEMaterialD2Res> bmwbImageResList;
    private long cacheExpiration = 86400000;
    private Iconsloaded iconsloaded;
    private Context mContext;
    private CSEMaterialRes wbeMaterialRes;

    /* loaded from: classes.dex */
    public interface Iconsloaded {
        void notifyiconslist();
    }

    public CSOLSWBED2Manager(Context context, final CSEMaterialRes cSEMaterialRes) {
        ArrayList arrayList = new ArrayList();
        this.bmwbImageResList = arrayList;
        this.mContext = context;
        this.wbeMaterialRes = cSEMaterialRes;
        arrayList.add(initBigImageItem(cSEMaterialRes));
        this.bmwbImageResList.add(initTitleItem(cSEMaterialRes));
        int parseInt = Integer.parseInt(cSEMaterialRes.getData_number());
        boolean isExpires = isExpires(cSEMaterialRes.getUniqid() + cSEMaterialRes.getName());
        File[] listFiles = new File(cSEMaterialRes.getDesiconFilePath()).listFiles(new CSLibUiConfig.OnlineResFilenameFilter(itemNameEndwith));
        if (listFiles != null && listFiles.length == parseInt && !isExpires) {
            initNormalItem(cSEMaterialRes);
            return;
        }
        cSEMaterialRes.delAllFile(cSEMaterialRes.getDesiconFilePath());
        cSEMaterialRes.downloadcontentRes(context, cSEMaterialRes.getEffect_zip(), cSEMaterialRes.getDesiconsZipFilePath(), new CSAsyncDownloadFileLoad.AsyncDownloadFileListener() { // from class: com.picspool.libfuncview.effect.onlinestore.manager.CSOLSWBED2Manager.1
            @Override // com.picspool.libfuncview.effect.onlinestore.resource.CSAsyncDownloadFileLoad.AsyncDownloadFileListener
            public void onProgressUpdate(Integer... numArr) {
            }

            @Override // com.picspool.libfuncview.effect.onlinestore.resource.CSAsyncDownloadFileLoad.AsyncDownloadFileListener
            public void onPostExecute(Object obj) {
                CSEMaterialRes cSEMaterialRes2;
                if (!((Boolean) obj).booleanValue() || (cSEMaterialRes2 = cSEMaterialRes) == null) {
                    return;
                }
                try {
                    cSEMaterialRes2.upZip(cSEMaterialRes2.getDesiconsZipFilePath(), cSEMaterialRes.getDesiconFilePath());
                    cSEMaterialRes.deleteZip(cSEMaterialRes.getDesiconsZipFilePath());
                    CSOLSWBED2Manager.this.initNormalItem(cSEMaterialRes);
                    if (CSOLSWBED2Manager.this.iconsloaded != null) {
                        CSOLSWBED2Manager.this.iconsloaded.notifyiconslist();
                    }
                } catch (ZipException e) {
                    e.printStackTrace();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

            @Override // com.picspool.libfuncview.effect.onlinestore.resource.CSAsyncDownloadFileLoad.AsyncDownloadFileListener
            public void onImageDownLoadFaile() {
                CSOLSWBED2Manager cSOLSWBED2Manager = CSOLSWBED2Manager.this;
                cSOLSWBED2Manager.resetExpires(cSEMaterialRes.getUniqid() + cSEMaterialRes.getName());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initNormalItem(CSEMaterialRes cSEMaterialRes) {
        File[] listFiles = new File(cSEMaterialRes.getDesiconFilePath()).listFiles(new CSLibUiConfig.OnlineResFilenameFilter(itemNameEndwith));
        if (listFiles != null) {
            for (File file : listFiles) {
                CSEMaterialD2Res cSEMaterialD2Res = new CSEMaterialD2Res();
                cSEMaterialD2Res.setItemType(CSEMaterialD2Res.ItemType.NORMAL);
                cSEMaterialD2Res.setIconFileName(file.getPath());
                this.bmwbImageResList.add(cSEMaterialD2Res);
            }
        }
    }

    private CSEMaterialD2Res initTitleItem(CSEMaterialRes cSEMaterialRes) {
        CSEMaterialD2Res cSEMaterialD2Res = new CSEMaterialD2Res();
        cSEMaterialD2Res.setItemType(CSEMaterialD2Res.ItemType.TITLE);
        cSEMaterialD2Res.setShowText(cSEMaterialRes.getName());
        return cSEMaterialD2Res;
    }

    private CSEMaterialD2Res initBigImageItem(CSEMaterialRes cSEMaterialRes) {
        CSEMaterialD2Res cSEMaterialD2Res = new CSEMaterialD2Res();
        cSEMaterialD2Res.setItemType(CSEMaterialD2Res.ItemType.BIGIMAGE);
        cSEMaterialD2Res.setIconFileName(cSEMaterialRes.getBanner());
        return cSEMaterialD2Res;
    }

    private boolean isExpires(String str) {
        String str2 = DMPreferencesUtil.get(this.mContext, EFFECT_OL_DESICONS_REQUEST_MANAGER, str);
        if (str2 != null && !str2.equals("")) {
            long currentTimeMillis = System.currentTimeMillis();
            if (Long.parseLong(str2) + this.cacheExpiration > currentTimeMillis) {
                return false;
            }
            Context context = this.mContext;
            DMPreferencesUtil.save(context, EFFECT_OL_DESICONS_REQUEST_MANAGER, str, currentTimeMillis + "");
            return true;
        }
        Context context2 = this.mContext;
        DMPreferencesUtil.save(context2, EFFECT_OL_DESICONS_REQUEST_MANAGER, str, System.currentTimeMillis() + "");
        return true;
    }

    public void resetExpires(String str) {
        DMPreferencesUtil.save(this.mContext, EFFECT_OL_DESICONS_REQUEST_MANAGER, str, "0");
    }

    public List<CSEMaterialD2Res> getBmwbImageResList() {
        return this.bmwbImageResList;
    }

    public void setIconsloaded(Iconsloaded iconsloaded) {
        this.iconsloaded = iconsloaded;
    }
}

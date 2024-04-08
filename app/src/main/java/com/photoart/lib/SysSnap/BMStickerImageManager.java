package com.photoart.lib.SysSnap;

import android.content.Context;


import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.picspool.lib.onlinestore.resource.WBMaterialFactory;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;
import com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase;
import com.picspool.lib.sysutillib.DMPreferencesUtil;

/* loaded from: classes3.dex */
public class BMStickerImageManager implements DMWBManager {
    public static String CONFIG = "config";
    public static String STICKER_CONFIG = "stickerconfig";
    private Context mContext;
    private List<BMStickerImageRes> resList = new ArrayList();

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public BMStickerImageManager(Context context, BMStickerModeManager.StickerMode stickerMode) {
        this.mContext = context;
        int i = 1;
        if (stickerMode == BMStickerModeManager.StickerMode.STICKERALL) {
            for (int i2 = 1; i2 <= 32; i2++) {
                this.resList.add(initAssetItem("sticker1_" + i2, "sticker/emoji/" + i2 + ".png", "sticker/emoji/" + i2 + ".png"));
            }
            while (i <= 32) {
                this.resList.add(initAssetItem("sticker2_" + i, "sticker/heart/" + i + ".png", "sticker/heart/" + i + ".png"));
                i++;
            }
            String str = DMPreferencesUtil.get(this.mContext, CONFIG, STICKER_CONFIG);
            if (str != null) {
                try {
                    JSONArray jSONArray = new JSONObject(str).getJSONArray("stickers_data");
                    for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i3);
                        jSONObject.getString(WBMaterialFactory.MaterialIconName);
                        String string = jSONObject.getString("name");
                        jSONObject.getString(ImageViewTouchBase.LOG_TAG);
                        String string2 = jSONObject.getString("stickers");
                        jSONObject.getInt("position");
                        int i4 = jSONObject.getInt("sticker_number");
                        for (int i5 = 0; i5 < i4; i5++) {
                            if (string.equals("gesture") || string.equals("symbol") || string.equals("animal") || string.equals("face")) {
                                List<BMStickerImageRes> list = this.resList;
                                StringBuilder sb = new StringBuilder();
                                sb.append(string2);
                                int i6 = i5 + 1;
                                sb.append(i6);
                                sb.append(".png");
                                list.add(initOnLineItem(string, sb.toString(), string2 + i6 + ".png"));
                            }
                        }
                    }
                } catch (Exception unused) {
                }
            }
        } else if (stickerMode == BMStickerModeManager.StickerMode.STICKER1) {
            while (i <= 32) {
                this.resList.add(initAssetItem("sticker1_" + i, "sticker/emoji/" + i + ".png", "sticker/emoji/" + i + ".png"));
                i++;
            }
        } else if (stickerMode == BMStickerModeManager.StickerMode.STICKER2) {
            while (i <= 32) {
                this.resList.add(initAssetItem("sticker2_" + i, "sticker/heart/" + i + ".png", "sticker/heart/" + i + ".png"));
                i++;
            }
        } else if (stickerMode == BMStickerModeManager.StickerMode.STICKER3) {
            while (i <= 40) {
                this.resList.add(initAssetItem("sticker3_" + i, "sticker/gesture/" + i + ".png", "sticker/gesture/" + i + ".png"));
                i++;
            }
        } else if (stickerMode == BMStickerModeManager.StickerMode.STICKER4) {
            while (i <= 54) {
                this.resList.add(initAssetItem("sticker4_" + i, "sticker/symbol/" + i + ".png", "sticker/symbol/" + i + ".png"));
                i++;
            }
        } else if (stickerMode == BMStickerModeManager.StickerMode.STICKER5) {
            while (i <= 32) {
                this.resList.add(initAssetItem("sticker5_" + i, "sticker/face/" + i + ".png", "sticker/face/" + i + ".png"));
                i++;
            }
        } else if (stickerMode == BMStickerModeManager.StickerMode.STICKER6) {
            while (i <= 40) {
                this.resList.add(initAssetItem("sticker6_" + i, "sticker/animal/" + i + ".png", "sticker/animal/" + i + ".png"));
                i++;
            }
        }
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public int getCount() {
        if (this.resList.size() <= 0) {
            return 0;
        }
        return this.resList.size();
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(String str) {
        List<BMStickerImageRes> list = this.resList;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < this.resList.size(); i++) {
                BMStickerImageRes bMStickerImageRes = this.resList.get(i);
                if (bMStickerImageRes.getName().compareTo(str) == 0) {
                    return bMStickerImageRes;
                }
            }
        }
        return null;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public BMStickerImageRes getRes(int i) {
        List<BMStickerImageRes> list = this.resList;
        if (list == null || list.isEmpty()) {
            return null;
        }
        return this.resList.get(i);
    }

    protected BMStickerImageRes initAssetItem(String str, String str2, String str3) {
        BMStickerImageRes bMStickerImageRes = new BMStickerImageRes();
        bMStickerImageRes.setContext(this.mContext);
        bMStickerImageRes.setName(str);
        bMStickerImageRes.setIconFileName(str2);
        bMStickerImageRes.setIconType(DMWBRes.LocationType.ASSERT);
        bMStickerImageRes.setImageFileName(str3);
        bMStickerImageRes.setImageType(DMWBRes.LocationType.ASSERT);
        return bMStickerImageRes;
    }

    protected BMStickerImageRes initOnLineItem(String str, String str2, String str3) {
        BMStickerImageRes bMStickerImageRes = new BMStickerImageRes();
        bMStickerImageRes.setContext(this.mContext);
        bMStickerImageRes.setName(str);
        bMStickerImageRes.setIconFileName(str2);
        bMStickerImageRes.setIconType(DMWBRes.LocationType.ONLINE);
        bMStickerImageRes.setImageFileName(str3);
        bMStickerImageRes.setImageType(DMWBRes.LocationType.ONLINE);
        return bMStickerImageRes;
    }
}

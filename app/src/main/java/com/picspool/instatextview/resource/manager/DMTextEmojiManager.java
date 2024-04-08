package com.picspool.instatextview.resource.manager;

import android.content.Context;

import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.picspool.instatextview.resource.DMTextEmojiRes;
import com.picspool.lib.onlinestore.resource.WBMaterialFactory;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;
import com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase;
import com.picspool.lib.sysutillib.DMPreferencesUtil;

/* loaded from: classes3.dex */
public class DMTextEmojiManager implements DMWBManager {
    public static String CONFIG = "config";
    public static String STICKER_CONFIG = "stickerconfig";
    private static DMTextEmojiManager instance;
    private List<DMTextEmojiRes> resList = new ArrayList();

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public static DMTextEmojiManager getInstance(Context context) {
        if (instance == null) {
            instance = new DMTextEmojiManager(context.getApplicationContext());
        }
        return instance;
    }

    public DMTextEmojiManager(Context context) {
        for (int i = 1; i <= 32; i++) {
            this.resList.add(initAssetItem("sticker1_" + i, "sticker/emoji/" + i + ".png", "sticker/emoji/" + i + ".png", context));
        }
        for (int i2 = 1; i2 <= 32; i2++) {
            this.resList.add(initAssetItem("sticker2_" + i2, "sticker/heart/" + i2 + ".png", "sticker/heart/" + i2 + ".png", context));
        }
        String str = DMPreferencesUtil.get(context, CONFIG, STICKER_CONFIG);
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
                        if (string.equals("FatMoji") || string.equals("gesture") || string.equals("symbol") || string.equals("animal") || string.equals("face")) {
                            List<DMTextEmojiRes> list = this.resList;
                            StringBuilder sb = new StringBuilder();
                            sb.append(string2);
                            int i6 = i5 + 1;
                            sb.append(i6);
                            sb.append(".png");
                            list.add(initOnLineItem(string, sb.toString(), string2 + i6 + ".png", context));
                        }
                    }
                }
            } catch (Exception unused) {
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
        List<DMTextEmojiRes> list = this.resList;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < this.resList.size(); i++) {
                DMTextEmojiRes dMTextEmojiRes = this.resList.get(i);
                if (dMTextEmojiRes.getName().compareTo(str) == 0) {
                    return dMTextEmojiRes;
                }
            }
        }
        return null;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMTextEmojiRes getRes(int i) {
        List<DMTextEmojiRes> list = this.resList;
        if (list == null || list.size() <= 0) {
            return null;
        }
        return this.resList.get(i);
    }

    protected DMTextEmojiRes initAssetItem(String str, String str2, String str3, Context context) {
        DMTextEmojiRes dMTextEmojiRes = new DMTextEmojiRes();
        dMTextEmojiRes.setContext(context);
        dMTextEmojiRes.setName(str);
        dMTextEmojiRes.setIconFileName(str2);
        dMTextEmojiRes.setIconType(DMWBRes.LocationType.ASSERT);
        dMTextEmojiRes.setImageFileName(str3);
        dMTextEmojiRes.setImageType(DMWBRes.LocationType.ASSERT);
        return dMTextEmojiRes;
    }

    protected DMTextEmojiRes initOnLineItem(String str, String str2, String str3, Context context) {
        DMTextEmojiRes dMTextEmojiRes = new DMTextEmojiRes();
        dMTextEmojiRes.setContext(context);
        dMTextEmojiRes.setName(str);
        dMTextEmojiRes.setIconFileName(str2);
        dMTextEmojiRes.setIconType(DMWBRes.LocationType.ONLINE);
        dMTextEmojiRes.setImageFileName(str3);
        dMTextEmojiRes.setImageType(DMWBRes.LocationType.ONLINE);
        return dMTextEmojiRes;
    }
}

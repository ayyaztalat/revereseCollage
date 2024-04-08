package com.picspool.instatextview.online;

import android.content.Context;

import com.picspool.lib.resource.DMWBRes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;

/* loaded from: classes3.dex */
public class DM_OnlineFontManager implements DMWBManager {
    private Context context;
    List<DM_OnlineFontRes> resList = new ArrayList();

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public DM_OnlineFontManager(Context context) {
        this.context = context;
        initManagerLibrary();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private String onlineImageResLocalFile(Context context, String str) {
        String absolutePath = context.getFilesDir().getAbsolutePath();
        File file = new File(absolutePath + "/nfonts");
        if (!file.exists()) {
            file.mkdir();
        }
        String str2 = absolutePath + "/nfonts/" + str;
        if (new File(str2).exists()) {
            return str2;
        }
        return null;
    }

    public void initManagerLibrary() {
        this.resList.add(initAssetItem("Default", "", "nfontsui/Default.png"));
        this.resList.add(initAssetItem("Alex_Brush_ROB.ttf", "nfonts/Alex_Brush_ROB.ttf", "nfontsui/Alex_Brush_ROB.png", 5));
        this.resList.add(getFromOnlineFontRes("KOMIKA", "ttf"));
        this.resList.add(getFromOnlineFontRes("ALLEGRO", "TTF", 15));
        this.resList.add(getFromOnlineFontRes("Dancing_Script", "ttf", 15));
        this.resList.add(getFromOnlineFontRes("BAUHS93", "TTF", 5));
        this.resList.add(getFromOnlineFontRes("BEBAS", "TTF"));
        this.resList.add(getFromOnlineFontRes("Chunkfive", "otf"));
        this.resList.add(getFromOnlineFontRes("Helvetica", "ttf"));
        this.resList.add(getFromOnlineFontRes("ITC_Avant_Garde_Gothic_LT_Extra_Light", "ttf"));
        this.resList.add(getFromOnlineFontRes("Salamander_Script", "otf"));
        this.resList.add(getFromOnlineFontRes("SignPainter", "otf"));
        this.resList.add(getFromOnlineFontRes("FREESCPT", "TTF", 10));
        this.resList.add(getFromOnlineFontRes("BebasNeue_Light", "otf"));
        this.resList.add(getFromOnlineFontRes("Impact", "ttf"));
        this.resList.add(getFromOnlineFontRes("Ostrich_Bold", "ttf"));
        this.resList.add(getFromOnlineFontRes("Brain_Flower", "ttf"));
        this.resList.add(getFromOnlineFontRes("Canter_Light", "otf"));
        this.resList.add(getFromOnlineFontRes("burnstown_dam", "otf"));
        this.resList.add(getFromOnlineFontRes("Joyful_Juliana", "ttf"));
        this.resList.add(getFromOnlineFontRes("always_forever", "ttf"));
        this.resList.add(getFromOnlineFontRes("AmaticSC_Regular", "ttf"));
        this.resList.add(getFromOnlineFontRes("Barrio_Regular", "otf"));
        this.resList.add(getFromOnlineFontRes("Lumberjack_New_jane", "otf"));
        this.resList.add(getFromOnlineFontRes("Blueshift_Stick", "otf"));
        this.resList.add(getFromOnlineFontRes("Blackout_Two_AM", "ttf"));
    }

    public DM_OnlineFontRes getFromOnlineFontRes(String str, String str2) {
        DM_OnlineFontRes onlineInitAssetItem = onlineInitAssetItem(str + "." + str2, "http://shuguangwuxian.oss-us-west-1.aliyuncs.com/material_pics/squaremaker/rtf/" + str + "." + str2 + ".zip", "nfontsui/" + str + ".png");
        String onlineImageResLocalFile = onlineImageResLocalFile(this.context, str + "." + str2);
        if (onlineImageResLocalFile != null && onlineImageResLocalFile != "") {
            onlineInitAssetItem.setFontFileName(onlineImageResLocalFile);
            onlineInitAssetItem.setDownLoad(true);
        } else {
            onlineInitAssetItem.setFontFileName(this.context.getFilesDir().getAbsolutePath() + "/nfonts/" + str + "." + str2);
            onlineInitAssetItem.setDownLoad(false);
        }
        return onlineInitAssetItem;
    }

    public DM_OnlineFontRes getFromOnlineFontRes(String str, String str2, int i) {
        DM_OnlineFontRes onlineInitAssetItem = onlineInitAssetItem(str + "." + str2, "http://shuguangwuxian.oss-us-west-1.aliyuncs.com/material_pics/squaremaker/rtf/" + str + "." + str2 + ".zip", "nfontsui/" + str + ".png", i);
        String onlineImageResLocalFile = onlineImageResLocalFile(this.context, str + "." + str2);
        if (onlineImageResLocalFile != null && onlineImageResLocalFile != "") {
            onlineInitAssetItem.setFontFileName(onlineImageResLocalFile);
            onlineInitAssetItem.setDownLoad(true);
        } else {
            onlineInitAssetItem.setFontFileName(this.context.getFilesDir().getAbsolutePath() + "/nfonts/" + str + "." + str2);
            onlineInitAssetItem.setDownLoad(false);
        }
        return onlineInitAssetItem;
    }

    protected DM_OnlineFontRes initAssetItem(String str, String str2, String str3) {
        DM_OnlineFontRes dM_OnlineFontRes = new DM_OnlineFontRes();
        dM_OnlineFontRes.setContext(this.context);
        dM_OnlineFontRes.setName(str);
        dM_OnlineFontRes.setFontFileName(str2);
        dM_OnlineFontRes.setLocationType(DMWBRes.LocationType.ASSERT);
        dM_OnlineFontRes.setFontImgPath(str3);
        return dM_OnlineFontRes;
    }

    protected DM_OnlineFontRes initAssetItem(String str, String str2, String str3, DMWBRes.LocationType locationType) {
        DM_OnlineFontRes dM_OnlineFontRes = new DM_OnlineFontRes();
        dM_OnlineFontRes.setContext(this.context);
        dM_OnlineFontRes.setName(str);
        dM_OnlineFontRes.setFontFileName(str2);
        dM_OnlineFontRes.setLocationType(locationType);
        dM_OnlineFontRes.setFontImgPath(str3);
        return dM_OnlineFontRes;
    }

    protected DM_OnlineFontRes initAssetItem(String str, String str2, String str3, int i) {
        DM_OnlineFontRes dM_OnlineFontRes = new DM_OnlineFontRes();
        dM_OnlineFontRes.setContext(this.context);
        dM_OnlineFontRes.setName(str);
        dM_OnlineFontRes.setFontFileName(str2);
        dM_OnlineFontRes.setLocationType(DMWBRes.LocationType.ASSERT);
        dM_OnlineFontRes.setTextAddHeight(i);
        dM_OnlineFontRes.setFontImgPath(str3);
        return dM_OnlineFontRes;
    }

    protected DM_OnlineFontRes initAssetItem(String str, String str2, String str3, int i, DMWBRes.LocationType locationType) {
        DM_OnlineFontRes dM_OnlineFontRes = new DM_OnlineFontRes();
        dM_OnlineFontRes.setContext(this.context);
        dM_OnlineFontRes.setName(str);
        dM_OnlineFontRes.setFontFileName(str2);
        dM_OnlineFontRes.setLocationType(locationType);
        dM_OnlineFontRes.setTextAddHeight(i);
        dM_OnlineFontRes.setFontImgPath(str3);
        return dM_OnlineFontRes;
    }

    protected DM_OnlineFontRes onlineInitAssetItem(String str, String str2, String str3) {
        DM_OnlineFontRes dM_OnlineFontRes = new DM_OnlineFontRes();
        dM_OnlineFontRes.setContext(this.context);
        dM_OnlineFontRes.setName(str);
        dM_OnlineFontRes.setHttpFilePath(str2);
        dM_OnlineFontRes.setLocationType(DMWBRes.LocationType.ONLINE);
        dM_OnlineFontRes.setFontImgPath(str3);
        return dM_OnlineFontRes;
    }

    protected DM_OnlineFontRes onlineInitAssetItem(String str, String str2, String str3, int i) {
        DM_OnlineFontRes dM_OnlineFontRes = new DM_OnlineFontRes();
        dM_OnlineFontRes.setContext(this.context);
        dM_OnlineFontRes.setName(str);
        dM_OnlineFontRes.setHttpFilePath(str2);
        dM_OnlineFontRes.setLocationType(DMWBRes.LocationType.ONLINE);
        dM_OnlineFontRes.setTextAddHeight(i);
        dM_OnlineFontRes.setFontImgPath(str3);
        return dM_OnlineFontRes;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public int getCount() {
        return this.resList.size();
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(int i) {
        return this.resList.get(i);
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(String str) {
        for (int i = 0; i < this.resList.size(); i++) {
            DM_OnlineFontRes dM_OnlineFontRes = this.resList.get(i);
            if (dM_OnlineFontRes.getName().compareTo(str) == 0) {
                return dM_OnlineFontRes;
            }
        }
        return null;
    }
}

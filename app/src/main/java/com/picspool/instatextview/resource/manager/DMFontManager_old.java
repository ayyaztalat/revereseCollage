package com.picspool.instatextview.resource.manager;

import android.content.Context;

import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.List;
import com.picspool.instatextview.resource.DMFontRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;

/* loaded from: classes3.dex */
public class DMFontManager_old implements DMWBManager {
    private Context context;
    List<DMFontRes> resList = new ArrayList();

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public DMFontManager_old() {
        initManagerLibrary();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void initManagerLibrary() {
        this.resList.add(initAssetItem("Default", ""));
        this.resList.add(initAssetItem("Alex Brush ROB", "nfonts/Alex_Brush_ROB.ttf", 5));
        this.resList.add(initAssetItem("KOMIKA", "nfonts/KOMIKA.ttf"));
        this.resList.add(initAssetItem("ALLEGRO", "nfonts/ALLEGRO.TTF", 15));
        this.resList.add(initAssetItem("Dancing Script", "nfonts/Dancing_Script.ttf", 15));
        this.resList.add(initAssetItem("BAUHS93", "nfonts/BAUHS93.TTF", 5));
        this.resList.add(initAssetItem("  BEBAS", "nfonts/BEBAS.TTF"));
        this.resList.add(initAssetItem("Chunkfive", "nfonts/Chunkfive.otf"));
        this.resList.add(initAssetItem("Helvetica", "nfonts/Helvetica.ttf"));
        this.resList.add(initAssetItem("ITC Avant Garde Gothic", "nfonts/ITC_Avant_Garde_Gothic_LT_Extra_Light.ttf"));
        this.resList.add(initAssetItem("Salamander_Script", "nfonts/Salamander_Script.otf"));
        this.resList.add(initAssetItem("SignPainter", "nfonts/SignPainter.otf"));
        this.resList.add(initAssetItem("FREESCPT", "nfonts/FREESCPT.TTF", 10));
        this.resList.add(initAssetItem("BebasNeue_Light", "nfonts/BebasNeue_Light.otf"));
        this.resList.add(initAssetItem("Impact", "nfonts/Impact.ttf"));
        this.resList.add(initAssetItem("Ostrich_Bold", "nfonts/Ostrich_Bold.ttf"));
        this.resList.add(initAssetItem("Brain_Flower", "nfonts/Brain_Flower.ttf"));
        this.resList.add(initAssetItem("Canter_Light", "nfonts/Canter_Light.otf"));
        this.resList.add(initAssetItem("burnstown_dam", "nfonts/burnstown_dam.otf"));
        this.resList.add(initAssetItem("Joyful_Juliana", "nfonts/Joyful_Juliana.ttf"));
        this.resList.add(initAssetItem("always_forever", "nfonts/always_forever.ttf"));
        this.resList.add(initAssetItem("AmaticSC_Regular", "nfonts/AmaticSC_Regular.ttf"));
        this.resList.add(initAssetItem("Barrio_Regular", "nfonts/Barrio_Regular.otf"));
        this.resList.add(initAssetItem("Lumberjack_New_jane", "nfonts/Lumberjack_New_jane.otf"));
        this.resList.add(initAssetItem("PintassilgoPrints_Blueshift_Stick", "nfonts/PintassilgoPrints_Blueshift_Stick.otf"));
        this.resList.add(initAssetItem("Blackout_Two_AM", "nfonts/Blackout_Two_AM.ttf"));
    }

    protected DMFontRes initAssetItem(String str, String str2) {
        DMFontRes dMFontRes = new DMFontRes();
        dMFontRes.setContext(this.context);
        dMFontRes.setName(str);
        dMFontRes.setFontFileName(str2);
        dMFontRes.setLocationType(DMWBRes.LocationType.ASSERT);
        return dMFontRes;
    }

    protected DMFontRes initAssetItem(String str, String str2, int i) {
        DMFontRes dMFontRes = new DMFontRes();
        dMFontRes.setContext(this.context);
        dMFontRes.setName(str);
        dMFontRes.setFontFileName(str2);
        dMFontRes.setLocationType(DMWBRes.LocationType.ASSERT);
        dMFontRes.setTextAddHeight(i);
        return dMFontRes;
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
            DMFontRes dMFontRes = this.resList.get(i);
            if (dMFontRes.getName().compareTo(str) == 0) {
                return dMFontRes;
            }
        }
        return null;
    }
}

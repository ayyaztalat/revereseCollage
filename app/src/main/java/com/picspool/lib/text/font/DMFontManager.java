package com.picspool.lib.text.font;

import android.content.Context;
import android.util.Log;

import com.picspool.lib.resource.DMWBRes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;
import com.picspool.lib.sysutillib.DMPreferencesUtil;

/* loaded from: classes3.dex */
public class DMFontManager implements DMWBManager {
    private Context context;
    List<DMWBFontRes> resList = new ArrayList();

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public DMFontManager(Context context) {
        setContext(context);
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
        getJsonLocalFontList(this.context);
        Log.i("test", "resList size=" + this.resList.size());
    }

    public void getJsonLocalFontList(Context context) {
        String str = DMPreferencesUtil.get(context, "textfont_json", "all");
        if (str != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.getInt("status") == 0) {
                    JSONArray jSONArray = jSONObject.getJSONArray("font_info");
                    Log.i("test", "font_info size=" + jSONArray.length());
                    for (int i = 0; i < jSONArray.length(); i++) {
                        if (jSONArray.get(i) != null) {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                            String string = jSONObject2.getString("exist");
                            if (string.equals("1")) {
                                Log.i("test", "font_info item exist=" + string);
                            } else {
                                Log.i("test", "font_info item exist=" + string);
                                this.resList.add(initOnLineItem(jSONObject2.getString("name"), jSONObject2.getString("url").replace("\\", "")));
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected DMWBFontRes initAssetItem(String str, String str2) {
        DMWBFontRes dMWBFontRes = new DMWBFontRes();
        dMWBFontRes.setContext(this.context);
        dMWBFontRes.setName(str);
        dMWBFontRes.setFontFileName(str2);
        dMWBFontRes.setLocationType(DMWBRes.LocationType.ASSERT);
        return dMWBFontRes;
    }

    protected DMWBFontRes initAssetItem(String str, String str2, int i) {
        DMWBFontRes dMWBFontRes = new DMWBFontRes();
        dMWBFontRes.setContext(this.context);
        dMWBFontRes.setName(str);
        dMWBFontRes.setFontFileName(str2);
        dMWBFontRes.setLocationType(DMWBRes.LocationType.ASSERT);
        dMWBFontRes.setTextAddHeight(i);
        return dMWBFontRes;
    }

    protected DMWBFontRes initOnLineItem(String str, String str2) {
        DMWBFontRes dMWBFontRes = new DMWBFontRes();
        dMWBFontRes.setContext(this.context);
        dMWBFontRes.setName(str);
        dMWBFontRes.setFontUrl(str2);
        dMWBFontRes.setLocationType(DMWBRes.LocationType.ONLINE);
        File file = new File(this.context.getCacheDir() + "/picsjoin/" + str2.split("/")[str2.split("/").length - 1]);
        if (file.exists()) {
            dMWBFontRes.setFontFileName(file.getAbsolutePath());
        } else {
            dMWBFontRes.setFontFileName(null);
        }
        return dMWBFontRes;
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
    public DMWBFontRes getRes(String str) {
        for (int i = 0; i < this.resList.size(); i++) {
            DMWBFontRes dMWBFontRes = this.resList.get(i);
            if (dMWBFontRes.getName().compareTo(str) == 0) {
                return dMWBFontRes;
            }
        }
        return null;
    }

    public List<DMWBFontRes> getResList() {
        return this.resList;
    }
}

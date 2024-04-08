package com.picspool.instatextview.resource.manager;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.Constants;
import com.picspool.lib.resource.DMWBRes;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.picspool.instatextview.online.DM_FontOnlineManager;
import com.picspool.instatextview.resource.DMWBFontRes;
import com.picspool.lib.http.DMAsyncTextHttp;
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
        this.context = context;
        initManagerLibrary();
        try {
            DM_FontOnlineManager.getInstance().initLidowFONT(context, DM_FontOnlineManager.APPNAME);
        } catch (Throwable unused) {
        }
    }

    private static boolean isFitTimeConditon(Context context) {
        String str = DMPreferencesUtil.get(context, "getFontList", "last_time_dy");
        if (str == null) {
            recordHaveRequestRec(context);
            return true;
        }
        if (new Date().getTime() - Long.parseLong(str) >= 10800000) {
            recordHaveRequestRec(context);
            return true;
        }
        return false;
    }

    private static void recordHaveRequestRec(Context context) {
        DMPreferencesUtil.save(context, "getFontList", "last_time_dy", String.valueOf(new Date().getTime()));
    }

    private void initTextOnLine() {
        if (isFitTimeConditon(this.context)) {
            recordHaveRequestRec(this.context);
            DMAsyncTextHttp.asyncHttpRequest("http://www.picsjoin.com:8000/material/getFontList", new DMAsyncTextHttp.AsyncTextHttpTaskListener() { // from class: com.picspool.instatextview.resource.manager.DMFontManager.1
                @Override // com.picspool.lib.http.DMAsyncTextHttp.AsyncTextHttpTaskListener
                public void onRequestDidFinishLoad(final String str) {
                    Log.e("fontline", FirebaseAnalytics.Param.SUCCESS);
                    new Handler().post(new Runnable() { // from class: com.picspool.instatextview.resource.manager.DMFontManager.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            String str2 = str;
                            if (str2 == null || str2.length() <= 0) {
                                return;
                            }
                            DMPreferencesUtil.save(DMFontManager.this.context, "textfont_json", "all", str);
                        }
                    });
                }

                @Override // com.picspool.lib.http.DMAsyncTextHttp.AsyncTextHttpTaskListener
                public void onRequestDidFailedStatus(Exception exc) {
                    Log.e("fontline", Constants.IPC_BUNDLE_KEY_SEND_ERROR);
                }
            });
        }
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
    }

    public void getJsonLocalFontList(Context context) {
        String str = DMPreferencesUtil.get(context, "textfont_json", "all");
        if (str != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i = jSONObject.getInt("status");
                if (i == 0 || i == 1) {
                    JSONArray jSONArray = jSONObject.getJSONArray("font_info");
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        if (jSONArray.get(i2) != null) {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                            this.resList.add(initOnLineItem(jSONObject2.getString("name"), jSONObject2.getString("url").replace("\\", "")));
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
    public DMWBFontRes getRes(int i) {
        return this.resList.get(i);
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(String str) {
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

package com.picspool.instatextview.online;

import android.content.Context;
import java.io.IOException;
import java.util.Date;
import com.picspool.instatextview.online.DM_FontOkHttpClient;
import com.picspool.lib.sysutillib.DMPreferencesUtil;

/* loaded from: classes3.dex */
public class DM_FontOnlineManager {
    public static String APPNAME = "TextFont";
    public static String FONT_ERJSON = "all";
    public static String TEXTFONT_JSON = "textfont_json";
    private static DM_FontOnlineManager instance;
    private boolean isTest = false;

    public static DM_FontOnlineManager getInstance() {
        if (instance == null) {
            instance = new DM_FontOnlineManager();
        }
        return instance;
    }

    public void setTestStatus(boolean z) {
        this.isTest = z;
    }

    public boolean getTestStatus() {
        return this.isTest;
    }

    public void initLidowFONT(final Context context, String str) {
        if (str == null || !isFitTimeConditon(context)) {
            return;
        }
        DM_FontOkHttpClient.getInstance(context).asyncPost(new DM_FontOkHttpClient.ResponseListener() { // from class: com.picspool.instatextview.online.DM_FontOnlineManager.1
            @Override // com.picspool.instatextview.online.DM_FontOkHttpClient.ResponseListener
            public void onResponseFail(IOException iOException) {
            }

            @Override // com.picspool.instatextview.online.DM_FontOkHttpClient.ResponseListener
            public void onResponseSuccess(String str2) {
                DMPreferencesUtil.save(context, DM_FontOnlineManager.TEXTFONT_JSON, DM_FontOnlineManager.FONT_ERJSON, str2);
            }
        }, str, "getFontList");
    }

    public static boolean isFitTimeConditon(Context context) {
        String str = DMPreferencesUtil.get(context, "font__online_check", "last_time_dy");
        if (str == null) {
            recordHaveRequestRec(context);
            return true;
        }
        if (new Date().getTime() - Long.parseLong(str) >= 108000000) {
            recordHaveRequestRec(context);
            return true;
        }
        return false;
    }

    public static void recordHaveRequestRec(Context context) {
        DMPreferencesUtil.save(context, "font__online_check", "last_time_dy", String.valueOf(new Date().getTime()));
    }
}

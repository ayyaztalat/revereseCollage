package com.picspool.libfuncview.utils;

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/* loaded from: classes.dex */
public class CSLibUiConfig {
    public static final String TipsWindowManager = "libui_tips_window_show_manager";

    public static String readFromAssets(Context context, String str) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(str)));
        StringBuilder sb = new StringBuilder();
        for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
            sb.append(readLine);
        }
        bufferedReader.close();
        return sb.toString();
    }

    public static String readLocalTxtToString(String str) throws IOException {
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            bufferedReader = new BufferedReader(new FileReader(new File(str)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            bufferedReader = null;
        }
        if (bufferedReader == null) {
            return null;
        }
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                stringBuffer.append(readLine);
            } else {
                bufferedReader.close();
                return stringBuffer.toString();
            }
        }
    }

    /* loaded from: classes.dex */
    public static class OnlineResFilenameFilter implements FilenameFilter {
        private String type;

        public OnlineResFilenameFilter(String str) {
            this.type = str;
        }

        @Override // java.io.FilenameFilter
        public boolean accept(File file, String str) {
            return str.endsWith(this.type);
        }
    }

    public static void pointEventSingleSaveMode1(Context context, String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put(str, str2);
//        FlurryAgent.logEvent("single_save", hashMap);
    }
}

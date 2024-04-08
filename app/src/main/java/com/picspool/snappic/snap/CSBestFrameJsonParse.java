package com.picspool.snappic.snap;

import android.content.Context;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class CSBestFrameJsonParse {
    public static CSBestFrameRes CreateObjectFromJSON(CSBestFrameRes cSBestFrameRes, Context context) {
        String file2String;
        if (cSBestFrameRes != null && cSBestFrameRes.getJsonFilePath() != null && cSBestFrameRes.getJsonFilePath().length() > 0 && (file2String = file2String(cSBestFrameRes.getJsonFilePath(), context)) != null && file2String.length() > 0) {
            try {
                JSONObject jSONObject = new JSONObject(file2String);
                String string = jSONObject.getString("typeId");
                int i = jSONObject.getInt("width");
                int i2 = jSONObject.getInt("height");
                int i3 = jSONObject.getInt("startY");
                cSBestFrameRes.setTyppeId(string);
                cSBestFrameRes.setTagWidth(i);
                cSBestFrameRes.setTagHeight(i2);
                cSBestFrameRes.setTagStartY(i3);
                return cSBestFrameRes;
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static String file2String(String str, Context context) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(str)));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(readLine);
                } else {
                    bufferedReader.close();
                    return sb.toString();
                }
            }
        } catch (Exception unused) {
            return null;
        }
    }
}

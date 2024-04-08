package com.picspool.instatextview.resource.manager;

import android.content.Context;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class DMTextBgJsonParse {
    public static DMTextBgRes CreateObjectFromJSON(DMTextBgRes dMTextBgRes, Context context) {
        String file2String;
        if (dMTextBgRes != null && dMTextBgRes.getJsonFilePath() != null && dMTextBgRes.getJsonFilePath().length() > 0 && (file2String = file2String(dMTextBgRes.getJsonFilePath(), context)) != null && file2String.length() > 0) {
            try {
                JSONObject jSONObject = new JSONObject(file2String);
                String string = jSONObject.getString("typeId");
                int i = jSONObject.getInt("x");
                int i2 = jSONObject.getInt("y");
                int i3 = jSONObject.getInt("w");
                int i4 = jSONObject.getInt("h");
                int i5 = jSONObject.getInt("containerW");
                int i6 = jSONObject.getInt("containerH");
                String string2 = jSONObject.getString("textColor");
                dMTextBgRes.setTyppeId(string);
                dMTextBgRes.setTextBgX(i);
                dMTextBgRes.setTextBgY(i2);
                dMTextBgRes.setTextBgW(i3);
                dMTextBgRes.setTextBgH(i4);
                dMTextBgRes.setTextBgContainerW(i5);
                dMTextBgRes.setTextBgContainerH(i6);
                dMTextBgRes.setTextBgTextColor(string2);
                return dMTextBgRes;
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

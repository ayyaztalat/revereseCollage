package com.picspool.lib.onlinestore.resource;

import android.content.Context;
import android.os.Environment;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.picspool.lib.packages.DMAppPackages;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes3.dex */
public class WBMaterialFactory {
    public static final String AddGroupIconName = "_icon";
    public static final String GroupIconDir = "group_icons";
    public static final String MaterialIconName = "icon";
    public static final String MaterialJSONName = "JSONInfo.txt";
    public static final String MaterialRootDir = "material";
    private static String SDRootDirName;

    public static List<WBMaterialRes> CreateMaterialsFromJSON(Context context, String str) {
        JSONObject jSONObject = null;
        String string = null;
        String string2 = null;
        ArrayList arrayList = new ArrayList();
        boolean equals = Environment.getExternalStorageState().equals("mounted");
        try {
            jSONObject = new JSONObject(str);
            string = jSONObject.getString("status");
            string2 = jSONObject.getString("utc");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (string == null) {
            return null;
        }
        try {
            int parseInt = Integer.parseInt(string);
            Integer.parseInt(string2);
            if (parseInt != 1) {
                return null;
            }
            JSONArray jSONArray = jSONObject.getJSONArray("data");
            for (int i = 0; i < jSONArray.length(); i++) {
                WBMaterialRes CreateMaterialFromJSONObject = CreateMaterialFromJSONObject(context, jSONArray.getJSONObject(i), equals);
                if (CreateMaterialFromJSONObject != null) {
                    arrayList.add(CreateMaterialFromJSONObject);
                }
            }
            return arrayList;
        } catch (Exception unused) {
            return null;
        }
    }

    public static WBMaterialRes CreateMaterialFromJSONObject(Context context, JSONObject jSONObject, boolean z) {
        String absolutePath;
        if (jSONObject != null) {
            try {
                if (z) {
                    absolutePath = Environment.getExternalStorageDirectory().getPath() + "/" + getSDRootDirName(context);
                } else {
                    absolutePath = context.getFilesDir().getAbsolutePath();
                }
                WBMaterialRes wBMaterialRes = new WBMaterialRes();
                wBMaterialRes.setContext(context);
                wBMaterialRes.setContentType(DMWBRes.LocationType.ONLINE);
                wBMaterialRes.setIconType(DMWBRes.LocationType.ONLINE);
                wBMaterialRes.setName(jSONObject.getString("content_name"));
                wBMaterialRes.setFunName(jSONObject.getString("fun_name"));
                wBMaterialRes.setIconUriPath(jSONObject.getString("content_icon"));
                wBMaterialRes.setContentUriPath(jSONObject.getString("content_zip"));
                wBMaterialRes.setMaterialID(jSONObject.getString("content_id"));
                wBMaterialRes.setMaterialUTC(jSONObject.getString("content_utc"));
                wBMaterialRes.setContentOrder(Integer.valueOf(jSONObject.getString("content_order")).intValue());
                wBMaterialRes.setContentHot(jSONObject.getString("content_hot"));
                wBMaterialRes.setContentMinVersion(jSONObject.getString("content_min_version"));
                wBMaterialRes.setUniqueName(jSONObject.getString("unique_name_s"));
                wBMaterialRes.setBackups(jSONObject.getString("content_backup_1"), jSONObject.getString("content_backup_2"), jSONObject.getString("content_backup_3"), jSONObject.getString("content_backup_4"), jSONObject.getString("content_backup_5"));
                wBMaterialRes.setGroupID(jSONObject.getString(FirebaseAnalytics.Param.GROUP_ID));
                wBMaterialRes.setGroupName(jSONObject.getString("group_name"));
                wBMaterialRes.setUniqueGroupName(jSONObject.getString("group_unique_name"));
                wBMaterialRes.setGroupOrder(Integer.valueOf(jSONObject.getString("group_order")).intValue());
                wBMaterialRes.setGroupIconUriPath(jSONObject.getString("group_icon"));
                String str = absolutePath + "/material/" + wBMaterialRes.getFunName() + "/" + wBMaterialRes.getUniqueName();
                File file = new File(str);
                if (!file.exists()) {
                    file.mkdirs();
                }
                wBMaterialRes.setRootFileName(str);
                String str2 = str + "/" + wBMaterialRes.getUniqueName();
                wBMaterialRes.setContentFilePath(str2);
                if (wBMaterialRes.getContentUriPath().substring(wBMaterialRes.getContentUriPath().lastIndexOf(".") + 1).equals("zip")) {
                    wBMaterialRes.setContentDownFilePath(str2 + ".zip");
                } else {
                    wBMaterialRes.setContentDownFilePath(str2);
                }
                wBMaterialRes.setIconFileName(wBMaterialRes.getRootFileName() + "/" + MaterialIconName);
                wBMaterialRes.setMaterialJSONInfo(wBMaterialRes.getRootFileName() + "/" + MaterialJSONName);
                File file2 = new File(wBMaterialRes.getMaterialJSONInfo());
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file2));
                bufferedWriter.write(jSONObject.toString());
                bufferedWriter.close();
                File file3 = new File(absolutePath + "/material/" + wBMaterialRes.getFunName() + "/" + GroupIconDir);
                if (!file3.exists()) {
                    file3.mkdirs();
                }
                wBMaterialRes.setGroupIconFilePath(file3 + "/" + wBMaterialRes.getUniqueGroupName() + AddGroupIconName);
                return wBMaterialRes;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<WBMaterialRes> CreateMaterialFromFilesDir(Context context) {
        return CreateMaterialFromFilesDir(context, "");
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x011b A[Catch: Exception -> 0x0172, TryCatch #1 {Exception -> 0x0172, blocks: (B:25:0x00d8, B:29:0x00e0, B:31:0x0111, B:32:0x0115, B:34:0x011b, B:36:0x013e, B:38:0x0143, B:40:0x0149, B:42:0x015d, B:45:0x0165, B:47:0x016e, B:30:0x00fc), top: B:54:0x00d8 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static List<com.picspool.lib.onlinestore.resource.WBMaterialRes> CreateMaterialFromFilesDir(Context r10, String r11) {
        /*
            Method dump skipped, instructions count: 375
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.picspool.lib.onlinestore.resource.WBMaterialFactory.CreateMaterialFromFilesDir(android.content.Context, java.lang.String):java.util.List");
    }

    public static String getSDRootDirName(Context context) {
        if (SDRootDirName == null) {
            SDRootDirName = "." + DMAppPackages.getAppName(context.getPackageName());
        }
        return SDRootDirName;
    }

    private static void getFiles(String str, List<String> list) {
        if (list == null) {
            return;
        }
        try {
            File[] listFiles = new File(str).listFiles();
            if (listFiles == null || listFiles.length <= 0) {
                return;
            }
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    getFiles(file.getAbsolutePath(), list);
                } else if (file.getName().contains(MaterialJSONName)) {
                    list.add(file.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

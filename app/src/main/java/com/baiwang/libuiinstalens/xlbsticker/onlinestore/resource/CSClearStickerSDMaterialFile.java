package com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource;

import android.content.Context;
import android.os.Environment;
import java.io.File;

/* loaded from: classes.dex */
public class CSClearStickerSDMaterialFile {
    public static void clearFile(Context context) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            String packageName = context.getApplicationContext().getPackageName();
            String path = context.getExternalFilesDir(null).getPath();
            delFolder(path + "/" + packageName + CSWBMaterialFactory.SDRootDirName);
        }
    }

    private static void delFolder(String str) {
        if (str == null) {
            return;
        }
        try {
            delAllFile(str);
            new File(str.toString()).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean delAllFile(String str) {
        String[] list;
        File file;
        File file2 = new File(str);
        if (file2.exists() && file2.isDirectory() && (list = file2.list()) != null) {
            boolean z = false;
            for (String str2 : list) {
                if (str.endsWith(File.separator)) {
                    file = new File(str + str2);
                } else {
                    file = new File(str + File.separator + str2);
                }
                if (file.isFile()) {
                    file.delete();
                }
                if (file.isDirectory()) {
                    delAllFile(str + "/" + str2);
                    delFolder(str + "/" + str2);
                    z = true;
                }
            }
            return z;
        }
        return false;
    }
}

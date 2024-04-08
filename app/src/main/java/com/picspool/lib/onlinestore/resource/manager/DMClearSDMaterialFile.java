package com.picspool.lib.onlinestore.resource.manager;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import com.picspool.lib.onlinestore.resource.WBMaterialFactory;

/* loaded from: classes3.dex */
public class DMClearSDMaterialFile {
    public static void clearFile(Context context) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            String path = Environment.getExternalStorageDirectory().getPath();
            delFolder(path + "/" + WBMaterialFactory.getSDRootDirName(context) + "/material");
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
        File file;
        File file2 = new File(str);
        if (file2.exists() && file2.isDirectory()) {
            String[] list = file2.list();
            boolean z = false;
            for (int i = 0; i < list.length; i++) {
                if (str.endsWith(File.separator)) {
                    file = new File(str + list[i]);
                } else {
                    file = new File(str + File.separator + list[i]);
                }
                if (file.isFile()) {
                    file.delete();
                }
                if (file.isDirectory()) {
                    delAllFile(str + "/" + list[i]);
                    delFolder(str + "/" + list[i]);
                    z = true;
                }
            }
            return z;
        }
        return false;
    }
}

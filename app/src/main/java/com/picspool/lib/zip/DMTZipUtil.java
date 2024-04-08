package com.picspool.lib.zip;

import android.util.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* loaded from: classes3.dex */
public class DMTZipUtil {
    public static void unZip(String str, String str2) {
        if (!str2.endsWith("/")) {
            str2 = str2 + "/";
        }
        byte[] bArr = new byte[4096];
        try {
            ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(str)));
            String canonicalPath = new File(str2).getCanonicalPath();
            while (true) {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry == null) {
                    return;
                }
                File file = new File(str2, nextEntry.getName());
                if (!file.getCanonicalPath().startsWith(canonicalPath)) {
                    Log.e("ZipUtils", "entryName: " + nextEntry.getName() + " is dangerous!");
                } else {
                    if (!nextEntry.isDirectory()) {
                        File parentFile = file.getParentFile();
                        if (!parentFile.getName().equals("__MACOSX")) {
                            if (!parentFile.exists()) {
                                parentFile.mkdirs();
                            }
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            while (true) {
                                int read = zipInputStream.read(bArr);
                                if (read <= 0) {
                                    break;
                                }
                                fileOutputStream.write(bArr, 0, read);
                            }
                            fileOutputStream.close();
                        }
                    }
                    zipInputStream.closeEntry();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

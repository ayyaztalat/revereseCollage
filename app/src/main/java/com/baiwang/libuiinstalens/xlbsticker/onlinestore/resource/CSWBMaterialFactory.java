package com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.media.session.PlaybackStateCompat;

import java.io.File;
import java.io.FileOutputStream;

/* loaded from: classes.dex */
public class CSWBMaterialFactory {
    public static final String MaterialIconName = ".icon";
    public static final String MaterialRootDir = ".material";
    public static final String PHOTOLABJSONCACHE = ".stickerjsoncache.txt";
    public static final String PhotolabContentInfo = "info.txt";
    public static final String PhotolabContentMain = "1.data";
    public static final String SDRootDirName = ".sticker";
    public static final String StickerFunName = "sticker";

    /* JADX WARN: Removed duplicated region for block: B:44:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01ab A[Catch: Exception -> 0x0063, TRY_LEAVE, TryCatch #8 {Exception -> 0x0063, blocks: (B:11:0x0043, B:19:0x0091, B:22:0x00af, B:34:0x00e6, B:36:0x00fa, B:41:0x0109, B:46:0x0120, B:48:0x0132, B:50:0x0138, B:51:0x013f, B:53:0x0145, B:55:0x014b, B:56:0x0152, B:58:0x0158, B:60:0x015e, B:61:0x0165, B:63:0x016b, B:70:0x017e, B:72:0x0184, B:75:0x01a5, B:77:0x01ab, B:82:0x01b7, B:86:0x01c4, B:88:0x01dc, B:90:0x01e2, B:91:0x01e9, B:93:0x01ef, B:95:0x01f5, B:96:0x01fc, B:98:0x0202, B:100:0x0208, B:101:0x020f, B:103:0x0217, B:105:0x021d, B:106:0x0224, B:108:0x022c, B:110:0x0232, B:111:0x0239, B:113:0x023f, B:115:0x0245, B:116:0x024c, B:118:0x0254, B:120:0x025a, B:121:0x0261, B:123:0x0269, B:125:0x026f, B:127:0x027b, B:129:0x0283, B:135:0x0297, B:137:0x029d, B:139:0x02d6, B:140:0x02d9, B:142:0x0315, B:144:0x032d, B:146:0x0351, B:148:0x0357, B:143:0x032a), top: B:208:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List<CSWBMaterialGroupRes> CreateGroupMaterialListFromJSON(android.content.Context r26, final String r27, String r28, boolean r29) {
        /*
            Method dump skipped, instructions count: 1019
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource.CSWBMaterialFactory.CreateGroupMaterialListFromJSON(android.content.Context, java.lang.String, java.lang.String, boolean):java.util.List");
    }

    public static boolean isSDAvailable() {
        return Environment.getExternalStorageState().equals("mounted") && getSDFreeSize() > 50;
    }

    private static long getSDFreeSize() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return ((statFs.getAvailableBlocks() * statFs.getBlockSize()) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
    }

    public static void saveIconBitmapDisk(Bitmap bitmap, String str) {
        try {
            if (new File(str).exists()) {
                return;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

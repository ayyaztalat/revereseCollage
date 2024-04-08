package com.picspool.libfuncview.onlinestore;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.media.session.PlaybackStateCompat;

import java.io.File;
import java.io.FileOutputStream;

/* loaded from: classes.dex */
public class CSMaterialFactory {
    public static final String BgFunName = "background";
    public static final String BgJSONCACHE = ".backgroundjsoncache.txt";
    public static final String EffectFunName = "effect";
    public static final String EffectJSONCACHE = ".effectjsoncache.txt";
    public static final String MaterialAPPName = "collagemakerplus";
    public static final String MaterialIconName = ".icon";
    public static final String MaterialRootDir = ".material";
    public static final String SDRootDirName = ".onlinestore";
    public static final String StickerFunName = "sticker";
    public static final String StickerJSONCACHE = ".stickerjsoncache.txt";

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

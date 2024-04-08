package com.picspool.lib.p017io;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.media.session.PlaybackStateCompat;


/* renamed from: com.picspool.lib.io.DMDiskSpace */
/* loaded from: classes3.dex */
public class DMDiskSpace {
    public static long sizeofSDCard() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            if (Build.VERSION.SDK_INT >= 18) {
                return getAvaiableKb(statFs);
            }
            return getAvaiableKb_d(statFs);
        }
        return 0L;
    }

    public static long sizeofSystem() {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getPath());
        if (Build.VERSION.SDK_INT >= 18) {
            return getAvaiableKb(statFs);
        }
        return getAvaiableKb_d(statFs);
    }

    private static long getAvaiableKb(StatFs statFs) {
        return (statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong()) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
    }

    private static long getAvaiableKb_d(StatFs statFs) {
        statFs.getBlockCount();
        return (statFs.getAvailableBlocks() * statFs.getBlockSize()) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
    }
}

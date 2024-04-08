package com.magic.video.editor.effect.cut.utils;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.media.session.PlaybackStateCompat;


/* loaded from: classes2.dex */
public class CSDiskSpace {
    public static long sizeofSDCard() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return Build.VERSION.SDK_INT >= 18 ? getAvaiableKb(statFs) : getAvaiableKb_d(statFs);
        }
        return 0L;
    }

    public static long sizeofSystem() {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getPath());
        return Build.VERSION.SDK_INT >= 18 ? getAvaiableKb(statFs) : getAvaiableKb_d(statFs);
    }

    private static long getAvaiableKb(StatFs statFs) {
        return (statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong()) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
    }

    private static long getAvaiableKb_d(StatFs statFs) {
        statFs.getBlockCount();
        return (statFs.getAvailableBlocks() * statFs.getBlockSize()) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
    }
}

package com.picspool.lib.filter.cpu.art;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.filter.cpu.normal.GrayscaleFilter;
import com.picspool.lib.filter.cpu.normal.MaskFilter;

/* loaded from: classes3.dex */
public class OldMovie {
    public static Bitmap filter(Resources resources, Bitmap bitmap, String str, String str2) {
        return MaskFilter.filter(MaskFilter.filter(GrayscaleFilter.filter(bitmap, false), DMBitmapUtil.getImageFromAssetsFile(resources, str), PorterDuff.Mode.SCREEN, true), DMBitmapUtil.getImageFromAssetsFile(resources, str2), PorterDuff.Mode.MULTIPLY, true);
    }

    public static Bitmap filter(Resources resources, Bitmap bitmap) {
        return filter(resources, bitmap, "art/moviespot.jpg", "art/oldmovie.jpg");
    }
}

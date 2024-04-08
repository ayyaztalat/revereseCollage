package com.picspool.lib.bitmap.output.wallpaper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.widget.Toast;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMWallpaperSetter {
    public void setImage(Activity activity, Bitmap bitmap) {
        if (bitmap != null) {
            try {
                if (!bitmap.isRecycled()) {
                    DMScreenInfoUtil.screenWidth(activity);
                    DMScreenInfoUtil.screenHeight(activity);
                    WallpaperManager.getInstance(activity).setBitmap(bitmap);
                    return;
                }
            } catch (Throwable unused) {
                Toast.makeText(activity, R.string.warning_failed_wallpaper, 1).show();
                return;
            }
        }
        Toast.makeText(activity, R.string.warning_no_image, 1).show();
    }
}

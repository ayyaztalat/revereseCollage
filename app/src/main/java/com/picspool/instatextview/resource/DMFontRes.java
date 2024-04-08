package com.picspool.instatextview.resource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes3.dex */
public class DMFontRes extends DMWBRes {
    private String fontFileName;
    private Bitmap fontIcon;
    private DMWBRes.LocationType locationType;
    private int mTextAddHeight;

    @Override // com.picspool.lib.resource.DMWBRes
    public String getType() {
        return "DMFontRes";
    }

    public String getFontFileName() {
        return this.fontFileName;
    }

    public void setFontFileName(String str) {
        this.fontFileName = str;
    }

    public DMWBRes.LocationType getLocationType() {
        return this.locationType;
    }

    public void setLocationType(DMWBRes.LocationType locationType) {
        this.locationType = locationType;
    }

    public void setIconBitmap(Bitmap bitmap) {
        this.fontIcon = bitmap;
    }

    @Override // com.picspool.lib.resource.DMWBRes
    public Bitmap getIconBitmap() {
        Bitmap bitmap = this.fontIcon;
        return bitmap == null ? Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565) : bitmap;
    }

    public Typeface getFontTypeface(Context context) {
        DMWBRes.LocationType locationType = this.locationType;
        if (locationType != null && locationType == DMWBRes.LocationType.ASSERT) {
            try {
                if (getName() == "Default") {
                    return Typeface.DEFAULT;
                }
                return Typeface.createFromAsset(context.getAssets(), this.fontFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int getTextAddHeight() {
        return this.mTextAddHeight;
    }

    public void setTextAddHeight(int i) {
        this.mTextAddHeight = i;
    }
}

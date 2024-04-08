package com.picspool.lib.onlineImage;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageButton;
import com.picspool.lib.onlineImage.DMAsyncImageLoader;

/* loaded from: classes3.dex */
public class DMImageButtonOnLine extends androidx.appcompat.widget.AppCompatImageButton {
    private Bitmap bitmap;
    private DMAsyncImageLoader loader;

    public DMImageButtonOnLine(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.loader = new DMAsyncImageLoader();
    }

    public void setImageBitmapFromUrl(String str) {
        Bitmap loadImageBitamp = this.loader.loadImageBitamp(getContext(), str, new DMAsyncImageLoader.ImageCallback() { // from class: com.picspool.lib.onlineImage.DMImageButtonOnLine.1
            @Override // com.picspool.lib.onlineImage.DMAsyncImageLoader.ImageCallback
            public void imageLoadedError(Exception exc) {
            }

            @Override // com.picspool.lib.onlineImage.DMAsyncImageLoader.ImageCallback
            public void imageLoaded(Bitmap bitmap) {
                DMImageButtonOnLine.this.clearBitmap();
                DMImageButtonOnLine.this.bitmap = bitmap;
                DMImageButtonOnLine dMImageButtonOnLine = DMImageButtonOnLine.this;
                dMImageButtonOnLine.setImageBitmap(dMImageButtonOnLine.bitmap);
            }
        });
        if (loadImageBitamp != null) {
            clearBitmap();
            this.bitmap = loadImageBitamp;
            setImageBitmap(loadImageBitamp);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearBitmap() {
        super.setImageBitmap(null);
        Bitmap bitmap = this.bitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        this.bitmap.recycle();
        this.bitmap = null;
    }
}

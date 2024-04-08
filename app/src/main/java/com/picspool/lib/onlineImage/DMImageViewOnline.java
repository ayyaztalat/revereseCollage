package com.picspool.lib.onlineImage;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.picspool.lib.onlineImage.DMAsyncImageLoader;

/* loaded from: classes3.dex */
public class DMImageViewOnline extends androidx.appcompat.widget.AppCompatImageView {
    private Bitmap bitmap;
    private DMAsyncImageLoader loader;

    /* loaded from: classes3.dex */
    public interface OnImageLoadListener {
        void onLoadFail();

        void onLoadSucc();
    }

    public DMImageViewOnline(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.loader = new DMAsyncImageLoader();
    }

    public void setImageBitmapFromUrl(String str) {
        setImageBitmapFromUrl(str, null);
    }

    public void setImageBitmapFromUrl(String str, final OnImageLoadListener onImageLoadListener) {
        Bitmap loadImageBitamp = this.loader.loadImageBitamp(getContext(), str, new DMAsyncImageLoader.ImageCallback() { // from class: com.picspool.lib.onlineImage.DMImageViewOnline.1
            @Override // com.picspool.lib.onlineImage.DMAsyncImageLoader.ImageCallback
            public void imageLoaded(Bitmap bitmap) {
                DMImageViewOnline.this.clearBitmap();
                DMImageViewOnline.this.bitmap = bitmap;
                DMImageViewOnline dMImageViewOnline = DMImageViewOnline.this;
                dMImageViewOnline.setImageBitmap(dMImageViewOnline.bitmap);
                OnImageLoadListener onImageLoadListener2 = onImageLoadListener;
                if (onImageLoadListener2 != null) {
                    onImageLoadListener2.onLoadSucc();
                }
            }

            @Override // com.picspool.lib.onlineImage.DMAsyncImageLoader.ImageCallback
            public void imageLoadedError(Exception exc) {
                OnImageLoadListener onImageLoadListener2 = onImageLoadListener;
                if (onImageLoadListener2 != null) {
                    onImageLoadListener2.onLoadFail();
                }
            }
        });
        if (loadImageBitamp != null) {
            clearBitmap();
            this.bitmap = loadImageBitamp;
            setImageBitmap(loadImageBitamp);
            if (onImageLoadListener != null) {
                onImageLoadListener.onLoadSucc();
            }
        }
    }

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

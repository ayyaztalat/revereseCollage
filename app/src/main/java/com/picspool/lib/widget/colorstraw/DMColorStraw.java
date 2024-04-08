package com.picspool.lib.widget.colorstraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.widget.listener.OnDMViewColorChangedListener;
import com.picspool.lib.widget.pointer.DMTouchPointView;

/* loaded from: classes3.dex */
public class DMColorStraw implements DMTouchPointView.OnTouchPointToListener {
    private OnDMViewColorChangedListener mColorChangedListener;
    private Context mContext;
    private int mStrawWidth;
    private OnStrawingListener mStrawingListener;
    private float touchX;
    private DMTouchPointView view_touchPointer;
    private String pointerIconName = "ui/colorstraw.png";
    private Bitmap mStrawBitmap = null;
    private Bitmap touchPoint = null;
    private float touchY = 0.0f;
    private int mStrawHeight = 0;

    /* loaded from: classes3.dex */
    public interface OnStrawingListener {
        void Stawing(Boolean bool);
    }

    public DMColorStraw(Context context, DMTouchPointView dMTouchPointView) {
        this.mContext = context;
        this.view_touchPointer = dMTouchPointView;
        dMTouchPointView.setListener(this);
    }

    public void setStrawBitmap(Bitmap bitmap) {
        recycleStrawBitmap();
        this.mStrawBitmap = bitmap;
        this.mStrawWidth = bitmap.getWidth();
        this.mStrawHeight = bitmap.getHeight();
    }

    public void setListener(OnStrawingListener onStrawingListener, OnDMViewColorChangedListener onDMViewColorChangedListener) {
        this.mStrawingListener = onStrawingListener;
        this.mColorChangedListener = onDMViewColorChangedListener;
    }

    public void setStrawable(Boolean bool) {
        Bitmap bitmap = null;
        if (this.touchPoint == null) {
            this.touchPoint = DMBitmapUtil.getImageFromAssetsFile(this.mContext.getResources(), this.pointerIconName);
            Bitmap sampeMinZoomFromBitmap = DMBitmapUtil.sampeMinZoomFromBitmap(this.touchPoint, (int) ((bitmap.getWidth() * (this.mContext.getResources().getDisplayMetrics().density / 1.5f)) + 0.5f));
            Bitmap bitmap2 = this.touchPoint;
            if (bitmap2 != sampeMinZoomFromBitmap) {
                bitmap2.recycle();
            }
            this.touchPoint = sampeMinZoomFromBitmap;
            this.view_touchPointer.setPointerIcon(sampeMinZoomFromBitmap);
        }
        if (bool.booleanValue()) {
            this.view_touchPointer.setVisibility(View.VISIBLE);
            this.view_touchPointer.showPointer = true;
            float f = this.mStrawWidth / 2;
            this.touchX = f;
            float f2 = this.mStrawHeight / 2;
            this.touchY = f2;
            this.view_touchPointer.setPoint(f, f2);
            this.view_touchPointer.invalidate();
            OnStrawingListener onStrawingListener = this.mStrawingListener;
            if (onStrawingListener != null) {
                onStrawingListener.Stawing(true);
            }
            strawColor(this.touchX, this.touchY, true);
            return;
        }
        this.view_touchPointer.setVisibility(View.INVISIBLE);
        this.view_touchPointer.showPointer = false;
        recycleStrawBitmap();
        recyclePointerBitmap();
        OnStrawingListener onStrawingListener2 = this.mStrawingListener;
        if (onStrawingListener2 != null) {
            onStrawingListener2.Stawing(false);
        }
    }

    public void strawColor(float f, float f2, boolean z) {
        OnDMViewColorChangedListener onDMViewColorChangedListener;
        this.view_touchPointer.setPoint(f, f2);
        this.view_touchPointer.invalidate();
        int i = (int) f;
        int i2 = (int) f2;
        if (i < 0) {
            i = 0;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        int i3 = this.mStrawWidth;
        if (i >= i3) {
            i = i3 - 1;
        }
        int i4 = this.mStrawHeight;
        if (i2 >= i4) {
            i2 = i4 - 1;
        }
        Bitmap bitmap = this.mStrawBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        int pixel = this.mStrawBitmap.getPixel(i, i2);
        this.view_touchPointer.setPointerColor(pixel);
        if (pixel == 0 || (onDMViewColorChangedListener = this.mColorChangedListener) == null) {
            return;
        }
        onDMViewColorChangedListener.onColorChanged(pixel, z);
    }

    private void recycleStrawBitmap() {
        Bitmap bitmap = this.mStrawBitmap;
        if (bitmap != null) {
            synchronized (bitmap) {
                if (this.mStrawBitmap != null && !this.mStrawBitmap.isRecycled()) {
                    this.mStrawBitmap.recycle();
                    this.mStrawBitmap = null;
                }
            }
        }
    }

    private void recyclePointerBitmap() {
        Bitmap bitmap = this.touchPoint;
        if (bitmap != null) {
            synchronized (bitmap) {
                if (this.touchPoint != null && !this.touchPoint.isRecycled()) {
                    this.touchPoint.recycle();
                    this.touchPoint = null;
                }
            }
        }
    }

    @Override // com.picspool.lib.widget.pointer.DMTouchPointView.OnTouchPointToListener
    public void pointTo(float f, float f2) {
        strawColor(f, f2, false);
    }
}

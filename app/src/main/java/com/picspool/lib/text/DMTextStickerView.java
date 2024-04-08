package com.picspool.lib.text;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.picspool.lib.sticker.core.DMSticker;
import com.picspool.lib.sticker.core.DMStickerBMRenderable;
import com.picspool.lib.sticker.drawonview.DMStickerCanvasView;
import com.picspool.lib.sticker.util.DMStickerStateCallback;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMTextStickerView extends FrameLayout implements DMStickerStateCallback {
    private Handler handler;
    private StikcerViewOnClickListener mClickListener;
    private RelativeLayout rootLayout;
    protected DMSticker selectedBMSticker;
    private float surfaceHeight;
    protected DMStickerCanvasView surfaceView;
    private float surfaceWidth;

    /* loaded from: classes3.dex */
    public enum StickerCanvasLocation {
        TextView,
        Other
    }

    /* loaded from: classes3.dex */
    public interface StikcerViewOnClickListener {
        void clickSticker(DMSticker dMSticker);

        void deleteSticker(DMSticker dMSticker);
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void onImageDown(DMSticker dMSticker) {
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void onStickerChanged() {
    }

    public DMTextStickerView(Context context) {
        super(context);
        this.handler = new Handler();
        this.surfaceHeight = 0.0f;
        this.surfaceWidth = 0.0f;
        iniView();
    }

    public DMTextStickerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.handler = new Handler();
        this.surfaceHeight = 0.0f;
        this.surfaceWidth = 0.0f;
        iniView();
    }

    private void iniView() {
        RelativeLayout relativeLayout = (RelativeLayout) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.dm_systext_show_text_view, (ViewGroup) null);
        this.rootLayout = relativeLayout;
        addView(relativeLayout);
        DMStickerCanvasView dMStickerCanvasView = (DMStickerCanvasView) this.rootLayout.findViewById(R.id.text_surface_view);
        this.surfaceView = dMStickerCanvasView;
        dMStickerCanvasView.setTag(StickerCanvasLocation.TextView);
        this.surfaceView.startRender();
        this.surfaceView.setStickerCallBack(this);
        this.surfaceView.setVisibility(0);
    }

    public void setStickerViewClickListener(StikcerViewOnClickListener stikcerViewOnClickListener) {
        this.mClickListener = stikcerViewOnClickListener;
    }

    public void setStickerCanvasView(DMStickerCanvasView dMStickerCanvasView) {
        if (dMStickerCanvasView != null) {
            this.rootLayout.removeAllViews();
            this.surfaceView = dMStickerCanvasView;
        }
    }

    public int getStickerCount() {
        DMStickerCanvasView dMStickerCanvasView = this.surfaceView;
        if (dMStickerCanvasView == null) {
            return 0;
        }
        return dMStickerCanvasView.getStickersCount();
    }

    public void addTextSticker(DMSticker dMSticker) {
        float f;
        float f2;
        if (dMSticker != null) {
            int width = this.surfaceView.getWidth();
            int height = this.surfaceView.getHeight();
            float width2 = dMSticker.getWidth();
            float height2 = dMSticker.getHeight();
            Matrix matrix = new Matrix();
            Matrix matrix2 = new Matrix();
            Matrix matrix3 = new Matrix();
            if (width2 == 0.0f || height2 == 0.0f) {
                f = width2;
                f2 = height2;
            } else {
                float f3 = width2 / height2;
                float f4 = width2;
                while (true) {
                    float f5 = width;
                    if (f4 <= f5 - (f5 / 6.0f)) {
                        break;
                    }
                    f4 -= 6.0f;
                }
                f2 = (int) (f4 / f3);
                while (true) {
                    float f6 = height;
                    if (f2 <= f6 - (f6 / 6.0f)) {
                        break;
                    }
                    f2 -= 6.0f;
                }
                f = f3 * f2;
            }
            float f7 = (width - f) / 2.0f;
            if (f7 < 0.0f) {
                f7 = DMScreenInfoUtil.dip2px(getContext(), 5.0f);
            }
            float f8 = (height - f2) / 2.0f;
            if (f8 < 0.0f) {
                f8 = height / 2;
            }
            float f9 = f / width2;
            matrix2.setScale(f9, f9);
            matrix2.postTranslate(f7, f8);
            this.surfaceView.addSticker(dMSticker, matrix, matrix2, matrix3);
            this.selectedBMSticker = dMSticker;
            this.surfaceView.setFocusable(true);
            this.surfaceView.setTouchResult(true);
            this.surfaceView.replaceCurrentStickerSize((int) width2, (int) height2);
        }
        if (this.surfaceView.getVisibility() != 0) {
            this.surfaceView.setVisibility(0);
        }
        this.surfaceView.onShow();
        this.surfaceView.invalidate();
    }

    public void updateTextSticker(int i, int i2) {
        if (this.selectedBMSticker != null) {
            this.surfaceView.replaceCurrentStickerSize(i, i2);
        }
        if (this.surfaceView.getVisibility() != 0) {
            this.surfaceView.setVisibility(0);
        }
        this.surfaceView.onShow();
        this.surfaceView.invalidate();
    }

    public DMSticker getSelectedSticker() {
        return this.selectedBMSticker;
    }

    public Bitmap getResultBitmap() {
        if (getStickerCount() > 0) {
            return this.surfaceView.getResultBitmap();
        }
        return null;
    }

    public void changeSufaceSize(final RectF rectF) {
        this.handler.post(new Runnable() { // from class: com.picspool.lib.text.DMTextStickerView.1
            @Override // java.lang.Runnable
            public void run() {
                if (DMTextStickerView.this.surfaceView == null) {
                    return;
                }
                if (DMTextStickerView.this.surfaceWidth != 0.0f && DMTextStickerView.this.surfaceHeight != 0.0f) {
                    for (DMStickerBMRenderable dMStickerBMRenderable : DMTextStickerView.this.surfaceView.getStickers()) {
                        if (dMStickerBMRenderable.getSticker().getIsFreePuzzleBitmap() && DMTextStickerView.this.surfaceWidth < 400.0f && DMTextStickerView.this.surfaceHeight < 400.0f) {
                            break;
                        }
                        float[] fArr = new float[9];
                        dMStickerBMRenderable.lastPanTransform().getValues(fArr);
                        float width = (fArr[2] * rectF.width()) / DMTextStickerView.this.surfaceWidth;
                        float height = (fArr[5] * rectF.height()) / DMTextStickerView.this.surfaceHeight;
                        if (width < 0.0f) {
                            width = 0.0f;
                        }
                        if (height < 0.0f) {
                            height = 0.0f;
                        }
                        if (width > rectF.width()) {
                            width = rectF.width() - (rectF.width() / 7.0f);
                        }
                        if (height > rectF.height()) {
                            height = rectF.height() - (rectF.height() / 7.0f);
                        }
                        dMStickerBMRenderable.lastPanTransform().setTranslate(width, height);
                    }
                }
                DMTextStickerView.this.setSurfaceSize(rectF);
                DMTextStickerView.this.surfaceWidth = rectF.width();
                DMTextStickerView.this.surfaceHeight = rectF.height();
            }
        });
    }

    public void changeShowSufaceSize(final RectF rectF) {
        this.handler.post(new Runnable() { // from class: com.picspool.lib.text.DMTextStickerView.2
            @Override // java.lang.Runnable
            public void run() {
                DMTextStickerView.this.setSurfaceSize(rectF);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSurfaceSize(RectF rectF) {
        if (this.surfaceView == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 11) {
            this.surfaceView.setX(rectF.left);
            this.surfaceView.setY(rectF.top);
            this.surfaceView.setLayoutParams(new RelativeLayout.LayoutParams((int) rectF.width(), (int) rectF.height()));
            return;
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) rectF.width(), (int) rectF.height());
        layoutParams.leftMargin = (int) rectF.left;
        layoutParams.topMargin = (int) rectF.top;
        this.surfaceView.setLayoutParams(layoutParams);
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void stickerSelected(DMSticker dMSticker) {
        if (dMSticker != null) {
            this.selectedBMSticker = dMSticker;
        }
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void editButtonClicked() {
        DMSticker curRemoveSticker = this.surfaceView.getCurRemoveSticker();
        this.selectedBMSticker = curRemoveSticker;
        if (curRemoveSticker != null) {
            StikcerViewOnClickListener stikcerViewOnClickListener = this.mClickListener;
            if (stikcerViewOnClickListener != null) {
                stikcerViewOnClickListener.deleteSticker(curRemoveSticker);
            }
            this.surfaceView.removeCurSelectedSticker();
            this.selectedBMSticker = null;
        }
        System.gc();
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void noStickerSelected() {
        this.surfaceView.setTouchResult(false);
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void onDoubleClicked() {
        StikcerViewOnClickListener stikcerViewOnClickListener;
        DMSticker dMSticker = this.selectedBMSticker;
        if (dMSticker == null || (stikcerViewOnClickListener = this.mClickListener) == null) {
            return;
        }
        stikcerViewOnClickListener.clickSticker(dMSticker);
    }

    public void setSurfaceVisibility(int i) {
        DMStickerCanvasView dMStickerCanvasView = this.surfaceView;
        if (dMStickerCanvasView == null) {
            return;
        }
        if (i == 0) {
            if (dMStickerCanvasView.getVisibility() != 0) {
                this.surfaceView.setVisibility(0);
            }
            this.surfaceView.onShow();
        } else {
            dMStickerCanvasView.onHide();
        }
        this.surfaceView.invalidate();
    }

    public void dipose() {
        DMStickerCanvasView dMStickerCanvasView = this.surfaceView;
        if (dMStickerCanvasView != null) {
            dMStickerCanvasView.clearStickers();
            this.surfaceView.destroyDrawingCache();
            this.surfaceView = null;
        }
    }
}

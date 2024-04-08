package com.picspool.instatextview.online;

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
import com.sky.testproject.R;
import com.picspool.instatextview.labelview.DMLabelSticker;
import com.picspool.lib.sticker.core.DMSticker;
import com.picspool.lib.sticker.core.DMStickerBMRenderable;
import com.picspool.lib.sticker.drawonview.DMStickerCanvasView;
import com.picspool.lib.sticker.util.DMStickerStateCallback;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.text.DMTextDrawer;
import com.picspool.lib.text.sticker.core.DMSmallTextSticker;

/* loaded from: classes3.dex */
public class DM_OnlineShowTextBMStickerView extends FrameLayout implements DMStickerStateCallback {
    private Handler handler;
    private DM_OnlineInstaTextView instaTextView;
    private RelativeLayout rootLayout;
    protected DMSticker seletedBMSticker;
    private float surfaceHeight;
    protected DMStickerCanvasView surfaceView;
    private float surfaceWidth;

    /* loaded from: classes3.dex */
    public enum StickerCanvasLocation {
        TextView,
        Other
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void onImageDown(DMSticker dMSticker) {
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void onStickerChanged() {
    }

    public DM_OnlineShowTextBMStickerView(Context context) {
        super(context);
        this.handler = new Handler();
        this.surfaceHeight = 0.0f;
        this.surfaceWidth = 0.0f;
        iniView();
    }

    public DM_OnlineShowTextBMStickerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.handler = new Handler();
        this.surfaceHeight = 0.0f;
        this.surfaceWidth = 0.0f;
        iniView();
    }

    public void setStickerCanvasView(DMStickerCanvasView dMStickerCanvasView) {
        if (dMStickerCanvasView != null) {
            this.rootLayout.removeAllViews();
            this.surfaceView = dMStickerCanvasView;
        }
    }

    private void iniView() {
        RelativeLayout relativeLayout = (RelativeLayout) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.dm_text_show_text_view, (ViewGroup) null);
        this.rootLayout = relativeLayout;
        addView(relativeLayout);
        DMStickerCanvasView dMStickerCanvasView = (DMStickerCanvasView) this.rootLayout.findViewById(R.id.text_surface_view);
        this.surfaceView = dMStickerCanvasView;
        dMStickerCanvasView.setTag(StickerCanvasLocation.TextView);
        this.surfaceView.startRender();
        this.surfaceView.setStickerCallBack(this);
        this.surfaceView.setVisibility(0);
    }

    public int getStickerCount() {
        DMStickerCanvasView dMStickerCanvasView = this.surfaceView;
        if (dMStickerCanvasView == null) {
            return 0;
        }
        return dMStickerCanvasView.getStickersCount();
    }

    public void addTextView(DMTextDrawer dMTextDrawer) {
        float f;
        float f2;
        if (dMTextDrawer != null && dMTextDrawer.getTextString() != null && dMTextDrawer.getTextString().length() != 0) {
            int width = this.surfaceView.getWidth();
            int height = this.surfaceView.getHeight();
            DMSmallTextSticker dMSmallTextSticker = new DMSmallTextSticker(dMTextDrawer, width);
            dMSmallTextSticker.updateBitmap();
            float width2 = dMSmallTextSticker.getWidth();
            float height2 = dMSmallTextSticker.getHeight();
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
            this.surfaceView.addSticker(dMSmallTextSticker, matrix, matrix2, matrix3);
            this.seletedBMSticker = dMSmallTextSticker;
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

    public void addLabelView(DMTextDrawer dMTextDrawer) {
        float f;
        float f2;
        if (dMTextDrawer != null && dMTextDrawer.getTextString().length() != 0) {
            int width = this.surfaceView.getWidth();
            int height = this.surfaceView.getHeight();
            DMLabelSticker dMLabelSticker = new DMLabelSticker(getContext(), dMTextDrawer);
            dMLabelSticker.updateBitmap();
            float width2 = dMLabelSticker.getWidth();
            float height2 = dMLabelSticker.getHeight();
            Matrix matrix = new Matrix();
            Matrix matrix2 = new Matrix();
            Matrix matrix3 = new Matrix();
            if (width2 == 0.0f || height2 == 0.0f) {
                f = width2;
                f2 = height2;
            } else {
                float f3 = width2 / height2;
                f = width2;
                while (true) {
                    float f4 = width;
                    if (f <= f4 - (f4 / 6.0f)) {
                        break;
                    }
                    f -= 6.0f;
                }
                f2 = (int) (f / f3);
            }
            float f5 = (width - f) / 2.0f;
            if (f5 < 0.0f) {
                f5 = DMScreenInfoUtil.dip2px(getContext(), 5.0f);
            }
            float f6 = (height - f2) / 2.0f;
            if (f6 < 0.0f) {
                f6 = height / 2;
            }
            float f7 = f / width2;
            matrix2.setScale(f7, f7);
            matrix2.postTranslate(f5, f6);
            this.surfaceView.addSticker(dMLabelSticker, matrix, matrix2, matrix3);
            this.seletedBMSticker = dMLabelSticker;
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

    public void changeTextView() {
        DMSticker dMSticker = this.seletedBMSticker;
        if (dMSticker != null) {
            if (dMSticker instanceof DMSmallTextSticker) {
                DMSmallTextSticker dMSmallTextSticker = (DMSmallTextSticker) dMSticker;
                dMSmallTextSticker.updateBitmap();
                this.surfaceView.replaceCurrentStickerSize(dMSmallTextSticker.getWidth(), dMSmallTextSticker.getHeight());
            } else if (dMSticker instanceof DMLabelSticker) {
                DMLabelSticker dMLabelSticker = (DMLabelSticker) dMSticker;
                dMLabelSticker.updateBitmap();
                this.surfaceView.replaceCurrentStickerSize(dMLabelSticker.getWidth(), dMLabelSticker.getHeight());
            }
        }
        if (this.surfaceView.getVisibility() != 0) {
            this.surfaceView.setVisibility(0);
        }
        this.surfaceView.onShow();
        this.surfaceView.invalidate();
    }

    public Bitmap getResultBitmap() {
        if (getStickerCount() > 0) {
            return this.surfaceView.getResultBitmap();
        }
        return null;
    }

    public void changeSufaceSize(final RectF rectF) {
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.online.DM_OnlineShowTextBMStickerView.1
            @Override // java.lang.Runnable
            public void run() {
                if (DM_OnlineShowTextBMStickerView.this.surfaceView == null) {
                    return;
                }
                if (DM_OnlineShowTextBMStickerView.this.surfaceWidth != 0.0f || DM_OnlineShowTextBMStickerView.this.surfaceHeight != 0.0f) {
                    for (DMStickerBMRenderable dMStickerBMRenderable : DM_OnlineShowTextBMStickerView.this.surfaceView.getStickers()) {
                        float[] fArr = new float[9];
                        dMStickerBMRenderable.lastPanTransform().getValues(fArr);
                        float width = (fArr[2] * rectF.width()) / DM_OnlineShowTextBMStickerView.this.surfaceWidth;
                        float height = (fArr[5] * rectF.height()) / DM_OnlineShowTextBMStickerView.this.surfaceHeight;
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
                DM_OnlineShowTextBMStickerView.this.setSurfaceSize(rectF);
                DM_OnlineShowTextBMStickerView.this.surfaceWidth = rectF.width();
                DM_OnlineShowTextBMStickerView.this.surfaceHeight = rectF.height();
            }
        });
    }

    public void changeShowSufaceSize(final RectF rectF) {
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.online.DM_OnlineShowTextBMStickerView.2
            @Override // java.lang.Runnable
            public void run() {
                DM_OnlineShowTextBMStickerView.this.setSurfaceSize(rectF);
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
            this.seletedBMSticker = dMSticker;
        }
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void editButtonClicked() {
        DMSticker curRemoveSticker = this.surfaceView.getCurRemoveSticker();
        this.seletedBMSticker = curRemoveSticker;
        if (curRemoveSticker != null) {
            if (curRemoveSticker instanceof DMSmallTextSticker) {
                ((DMSmallTextSticker) curRemoveSticker).releaseImage();
                this.surfaceView.removeCurSelectedSticker();
                this.seletedBMSticker = null;
            } else if (curRemoveSticker instanceof DMLabelSticker) {
                ((DMLabelSticker) curRemoveSticker).releaseImage();
                this.surfaceView.removeCurSelectedSticker();
                this.seletedBMSticker = null;
            }
        }
        System.gc();
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void noStickerSelected() {
        this.surfaceView.setTouchResult(false);
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void onDoubleClicked() {
        DMSticker dMSticker;
        if (this.instaTextView == null || (dMSticker = this.seletedBMSticker) == null) {
            return;
        }
        if (dMSticker instanceof DMSmallTextSticker) {
            final DMSmallTextSticker dMSmallTextSticker = (DMSmallTextSticker) dMSticker;
            this.handler.post(new Runnable() { // from class: com.picspool.instatextview.online.DM_OnlineShowTextBMStickerView.3
                @Override // java.lang.Runnable
                public void run() {
                    DM_OnlineShowTextBMStickerView.this.instaTextView.editText(dMSmallTextSticker.getTextDrawer());
                    DM_OnlineShowTextBMStickerView.this.surfaceView.onHide();
                }
            });
        } else if (dMSticker instanceof DMLabelSticker) {
            final DMLabelSticker dMLabelSticker = (DMLabelSticker) dMSticker;
            this.handler.post(new Runnable() { // from class: com.picspool.instatextview.online.DM_OnlineShowTextBMStickerView.4
                @Override // java.lang.Runnable
                public void run() {
                    DM_OnlineShowTextBMStickerView.this.instaTextView.editLabel(dMLabelSticker.getTextDrawer());
                    DM_OnlineShowTextBMStickerView.this.surfaceView.onHide();
                }
            });
        }
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

    public DM_OnlineInstaTextView getInstaTextView() {
        return this.instaTextView;
    }

    public void setInstaTextView(DM_OnlineInstaTextView dM_OnlineInstaTextView) {
        this.instaTextView = dM_OnlineInstaTextView;
    }

    public void dipose() {
        this.instaTextView = null;
        DMStickerCanvasView dMStickerCanvasView = this.surfaceView;
        if (dMStickerCanvasView != null) {
            dMStickerCanvasView.clearStickers();
            this.surfaceView.destroyDrawingCache();
            this.surfaceView = null;
        }
    }
}

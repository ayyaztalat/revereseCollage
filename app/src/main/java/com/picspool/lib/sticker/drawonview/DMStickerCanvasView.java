package com.picspool.lib.sticker.drawonview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import java.util.List;
import com.picspool.lib.bitmap.output.save.DMSaveDoneListener;
import com.picspool.lib.border.res.DMWBBorderRes;
import com.picspool.lib.sticker.core.DMSticker;
import com.picspool.lib.sticker.core.DMStickerBMRenderable;
import com.picspool.lib.sticker.resource.DMSingleStickerRes;
import com.picspool.lib.sticker.util.DMStickerStateCallback;
import com.picspool.lib.sticker.util.DM_ImageBackground;
import com.picspool.lib.sticker.util.DM_ImageTransformPanel;
import com.picspool.lib.sticker.view.DMStickersRenderer;

/* loaded from: classes3.dex */
public class DMStickerCanvasView extends View {
    public static int stickerIdIndex = 272;
    private CanvasThread mCanvasThread;
    private boolean mSizeChanged;
    private boolean mTouchResult;
    private DMWBBorderRes stickerBorderRes;

    private void init() {
    }

    public SurfaceHolder getSurfaceHolder() {
        return null;
    }

    public DMStickerCanvasView(Context context) {
        super(context);
        this.mSizeChanged = true;
        this.mTouchResult = false;
        init();
    }

    public DMStickerCanvasView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSizeChanged = true;
        this.mTouchResult = false;
        init();
    }

    public void setRenderer(DMStickersRenderer dMStickersRenderer) {
        this.mCanvasThread = createCanvasThread(dMStickersRenderer);
    }

    public CanvasThread createCanvasThread(DMStickersRenderer dMStickersRenderer) {
        return new CanvasThread(dMStickersRenderer);
    }

    public void startRender() {
        setRenderer(new DMViewBMStickersRenderer());
    }

    public Bitmap getResultBitmap() {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return null;
        }
        return canvasThread.getResultBitmap();
    }

    public List<DMSingleStickerRes> getResultBitmapSingle(int i) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return null;
        }
        return canvasThread.getResultBitmapSingle(i);
    }

    public List<DMSingleStickerRes> saveStickerToSD(Context context, String str, String str2, int i, DMSaveDoneListener dMSaveDoneListener) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread != null) {
            return canvasThread.saveStickerToSD(context, str, str2, i, dMSaveDoneListener);
        }
        return null;
    }

    public List<DMSingleStickerRes> saveStickerToSD(Context context, String str, String str2, int i, int i2, int i3, DMSaveDoneListener dMSaveDoneListener) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread != null) {
            return canvasThread.saveStickerToSD(context, str, str2, i, i2, i3, dMSaveDoneListener);
        }
        return null;
    }

    public void setBackgroundBitmap(int i, Bitmap bitmap, int i2, int i3, int i4, boolean z) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.setBackgroundBitmap(i, bitmap, i2, i3, i4, z);
    }

    public void setForeGroundBitmap(Bitmap bitmap, int i, int i2) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.setForeGroundBitmap(bitmap, i, i2);
    }

    public void setStickerCallBack(DMStickerStateCallback dMStickerStateCallback) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.setStickerCallBack(dMStickerStateCallback);
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread != null) {
            canvasThread.surfaceCreated();
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread != null) {
            canvasThread.surfaceDestroyed();
        }
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.onWindowResize(i2, i3);
    }

    public void onPause() {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.onPause();
    }

    public void onResume() {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.onResume();
    }

    public void onShow() {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.onShow();
    }

    public void onHide() {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.onHide();
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread != null) {
            canvasThread.onWindowFocusChanged(z);
        }
    }

    public void setEvent(Runnable runnable) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.setEvent(runnable);
    }

    public void clearEvent() {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.clearEvent();
    }

    public int addSticker(DMSticker dMSticker, Matrix matrix, Matrix matrix2, Matrix matrix3) {
        if (this.mCanvasThread == null) {
            return -1;
        }
        dMSticker.stickerId = stickerIdIndex;
        Log.i("InstaSticker", "Add DMSticker Id : " + stickerIdIndex);
        int i = stickerIdIndex + 1;
        stickerIdIndex = i;
        if (i == Integer.MAX_VALUE) {
            stickerIdIndex = 1;
        }
        this.mCanvasThread.addSticker(dMSticker, matrix, matrix2, matrix3);
        return dMSticker.stickerId;
    }

    public void addStickerBorder(DMWBBorderRes dMWBBorderRes) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.addStickerBorder(dMWBBorderRes);
    }

    public void replaceCurrentStickerWithImage(Bitmap bitmap) {
        CanvasThread canvasThread;
        if (bitmap == null || bitmap.isRecycled() || (canvasThread = this.mCanvasThread) == null) {
            return;
        }
        canvasThread.replaceCurrentStickerWithImage(bitmap);
    }

    public void replaceCurrentStickerSize(int i, int i2) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.replaceCurrentStickerSize(i, i2);
    }

    public void replaceCurrentStickerWithImageNoBorder(Bitmap bitmap) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.replaceCurrentStickerWithImageNoBorder(bitmap);
    }

    public void clearStickers() {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.clearStickers();
    }

    public void clearStickersOnlyFreePuzzzle() {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.clearStickersOnlyFreePuzzle();
    }

    public List<DMStickerBMRenderable> getStickers() {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return null;
        }
        return canvasThread.getStickers();
    }

    public void removeCurSelectedSticker() {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.removeCurSelectedSticker();
    }

    public DMSticker getCurRemoveSticker() {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return null;
        }
        return canvasThread.getCurRemoveSticker();
    }

    public void hideCurSelectedSticker() {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.hideCurSelectedSticker();
    }

    public void cancelSelected() {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.cancelSelected();
    }

    public void setCurSelected(int i) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.setCurSelected(i);
    }

    public void showSticker(int i) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.showSticker(i);
    }

    public void hideSticker(int i) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.hideSticker(i);
    }

    public void cloneCurSelectedSticker() {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.cloneCurSelectedSticker();
    }

    public int getStickersCount() {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return 0;
        }
        return canvasThread.getStickersCount();
    }

    public int getStickersNoFreePuzzleCount() {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return -1;
        }
        return canvasThread.getStickersNoFreePuzzleCount();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.requestExitAndWait();
    }

    protected void stopDrawing() {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.requestExitAndWait();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return super.onTouchEvent(motionEvent);
        }
        boolean onTouchEvent = canvasThread.onTouchEvent(motionEvent);
        invalidate();
        if (this.mCanvasThread.getCuRenderable() != null || onTouchEvent) {
            return true;
        }
        return this.mTouchResult;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread != null) {
            canvasThread.run(canvas);
        }
    }

    public void setTouchResult(boolean z) {
        this.mTouchResult = z;
    }

    public void setIsShowShadow(boolean z) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.setIsShowShadow(z);
    }

    public void setIsShowShadow(boolean z, int i) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread == null) {
            return;
        }
        canvasThread.setIsShowShadow(z, i);
    }

    /* loaded from: classes3.dex */
    public class CanvasThread {
        private DM_ImageBackground mBackground;
        private boolean mContextLost;
        private Runnable mEvent;
        private boolean mHasFocus;
        private boolean mHasSurface;
        private boolean mPaused;
        private DMStickersRenderer mRenderer;
        private boolean mDone = false;
        private int mWidth = 0;
        private int mHeight = 0;
        private DM_ImageTransformPanel mPanel = createPanel();

        public void requestExitAndWait() {
        }

        public CanvasThread(DMStickersRenderer dMStickersRenderer) {
            this.mRenderer = dMStickersRenderer;
            DM_ImageBackground dM_ImageBackground = new DM_ImageBackground(null);
            this.mBackground = dM_ImageBackground;
            this.mRenderer.setBackground(dM_ImageBackground);
            this.mPanel.isVisible = false;
            this.mRenderer.setTransPanel(this.mPanel);
        }

        public DM_ImageTransformPanel createPanel() {
            return new DMViewTransformPanelBM(DMStickerCanvasView.this.getContext());
        }

        public void setStickerCallBack(DMStickerStateCallback dMStickerStateCallback) {
            this.mRenderer.setCallback(dMStickerStateCallback);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return this.mRenderer.onTouchEvent(motionEvent);
        }

        public Bitmap getResultBitmap() {
            setBackgroundBitmap(0, null, DMStickerCanvasView.this.getWidth(), DMStickerCanvasView.this.getHeight(), 0, false);
            return this.mRenderer.createFrameBitmap();
        }

        public List<DMSingleStickerRes> getResultBitmapSingle(int i) {
            setBackgroundBitmap(0, null, DMStickerCanvasView.this.getWidth(), DMStickerCanvasView.this.getHeight(), 0, false);
            return this.mRenderer.getResultBitmapSingle(i);
        }

        public List<DMSingleStickerRes> saveStickerToSD(Context context, String str, String str2, int i, DMSaveDoneListener dMSaveDoneListener) {
            setBackgroundBitmap(0, null, DMStickerCanvasView.this.getWidth(), DMStickerCanvasView.this.getHeight(), 0, false);
            return this.mRenderer.saveStickerToSD(context, str, str2, i, dMSaveDoneListener);
        }

        public List<DMSingleStickerRes> saveStickerToSD(Context context, String str, String str2, int i, int i2, int i3, DMSaveDoneListener dMSaveDoneListener) {
            setBackgroundBitmap(0, null, DMStickerCanvasView.this.getWidth(), DMStickerCanvasView.this.getHeight(), 0, false);
            return this.mRenderer.saveStickerToSD(context, str, str2, i, i2, i3, dMSaveDoneListener);
        }

        public void addSticker(DMSticker dMSticker, Matrix matrix, Matrix matrix2, Matrix matrix3) {
            DMStickerBMRenderable dMStickerBMRenderable = new DMStickerBMRenderable(dMSticker);
            dMStickerBMRenderable.setLastRotateTransform(matrix);
            dMStickerBMRenderable.setLastPanTransform(matrix2);
            dMStickerBMRenderable.setLastScaleTransform(matrix3);
            this.mRenderer.addSticker(dMStickerBMRenderable);
            this.mPanel.setStickerRenderable(dMStickerBMRenderable);
            this.mPanel.isVisible = true;
        }

        public void clearStickers() {
            this.mRenderer.clearStickers();
        }

        public void clearStickersOnlyFreePuzzle() {
            this.mRenderer.clearStickersOnlyFreePuzzle();
        }

        public List<DMStickerBMRenderable> getStickers() {
            return this.mRenderer.getStickerRenderables();
        }

        public void addStickerBorder(DMWBBorderRes dMWBBorderRes) {
            this.mRenderer.addStickerBorder(dMWBBorderRes);
        }

        public void setBackgroundBitmap(int i, Bitmap bitmap, int i2, int i3, int i4, boolean z) {
            this.mBackground.setWidth(i2);
            this.mBackground.setHeight(i3);
            this.mBackground.setBackgroundColor(i);
            this.mBackground.bitmap = bitmap;
            this.mBackground.setIsTile(z);
        }

        public void setForeGroundBitmap(Bitmap bitmap, int i, int i2) {
            if (bitmap != null) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                bitmapDrawable.setBounds(0, 0, i, i2);
                this.mRenderer.setForegroundBitmapDrawable(bitmapDrawable);
                return;
            }
            this.mRenderer.setForegroundBitmapDrawable(null);
        }

        public void removeCurSelectedSticker() {
            this.mRenderer.removeCurSelectedSticker();
        }

        public DMSticker getCurRemoveSticker() {
            return this.mRenderer.getCurRemoveSticker();
        }

        public void hideCurSelectedSticker() {
            this.mRenderer.hideCurSelectedSticker();
        }

        public void cancelSelected() {
            this.mRenderer.cancelSelected();
        }

        public void setCurSelected(int i) {
            this.mRenderer.setCurSelected(i);
        }

        public void showSticker(int i) {
            this.mRenderer.showSticker(i);
        }

        public void hideSticker(int i) {
            this.mRenderer.hideSticker(i);
        }

        public void replaceCurrentStickerWithImage(Bitmap bitmap) {
            DMStickersRenderer dMStickersRenderer;
            if (bitmap == null || bitmap.isRecycled() || (dMStickersRenderer = this.mRenderer) == null) {
                return;
            }
            dMStickersRenderer.replaceCurrentStickerWithImage(bitmap);
        }

        public void replaceCurrentStickerWithImageNoBorder(Bitmap bitmap) {
            this.mRenderer.replaceCurrentStickerWithImageNoBorder(bitmap);
        }

        public void replaceCurrentStickerSize(int i, int i2) {
            this.mRenderer.replaceCurrentStickerSize(i, i2);
        }

        public void cloneCurSelectedSticker() {
            this.mRenderer.cloneCurSelectedSticker();
        }

        public int getStickersCount() {
            return this.mRenderer.getStickersCount();
        }

        public int getStickersNoFreePuzzleCount() {
            return this.mRenderer.getStickersNoFreePuzzleCount();
        }

        public DMStickerBMRenderable getCuRenderable() {
            return this.mRenderer.getCurRenderable();
        }

        public void run(Canvas canvas) {
            Runnable runnable = this.mEvent;
            if (runnable != null) {
                runnable.run();
            }
            boolean z = DMStickerCanvasView.this.mSizeChanged;
            int i = this.mWidth;
            int i2 = this.mHeight;
            DMStickerCanvasView.this.mSizeChanged = false;
            if (z) {
                this.mRenderer.sizeChanged(i, i2);
            }
            this.mRenderer.drawFrame(canvas);
        }

        private boolean needToWait() {
            return (this.mPaused || !this.mHasFocus || !this.mHasSurface || this.mContextLost) && !this.mDone;
        }

        public void surfaceCreated() {
            this.mHasSurface = true;
            this.mContextLost = false;
        }

        public void surfaceDestroyed() {
            this.mHasSurface = false;
        }

        public void onPause() {
            this.mPaused = true;
        }

        public void onResume() {
            this.mPaused = false;
            this.mRenderer.onResume();
        }

        public void onShow() {
            this.mRenderer.onShow();
            this.mHasFocus = true;
        }

        public void onHide() {
            this.mRenderer.onHide();
        }

        public void onWindowFocusChanged(boolean z) {
            this.mHasFocus = z;
        }

        public void onWindowResize(int i, int i2) {
            this.mWidth = i;
            this.mHeight = i2;
            DMStickerCanvasView.this.mSizeChanged = true;
        }

        public void setEvent(Runnable runnable) {
            this.mEvent = runnable;
        }

        public void clearEvent() {
            this.mEvent = null;
        }

        public void setIsShowShadow(boolean z) {
            synchronized (this) {
                this.mRenderer.setIsShowShadow(z);
            }
        }

        public void setIsShowShadow(boolean z, int i) {
            synchronized (this) {
                this.mRenderer.setIsShowShadow(z, i);
            }
        }
    }
}

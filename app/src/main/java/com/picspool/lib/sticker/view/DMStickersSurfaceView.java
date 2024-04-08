package com.picspool.lib.sticker.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.List;
import com.picspool.lib.sticker.core.DMSticker;
import com.picspool.lib.sticker.core.DMStickerBMRenderable;
import com.picspool.lib.sticker.util.DMStickerStateCallback;
import com.picspool.lib.sticker.util.DM_ImageBackground;
import com.picspool.lib.sticker.util.DM_ImageTransformPanel;

/* loaded from: classes3.dex */
public class DMStickersSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private CanvasThread mCanvasThread;
    private SurfaceHolder mHolder;
    private boolean mSizeChanged;
    private boolean mTouchResult;

    public DMStickersSurfaceView(Context context) {
        super(context);
        this.mSizeChanged = true;
        this.mTouchResult = false;
        init();
    }

    public DMStickersSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSizeChanged = true;
        this.mTouchResult = false;
        init();
    }

    private void init() {
        SurfaceHolder holder = getHolder();
        this.mHolder = holder;
        holder.addCallback(this);
        this.mHolder.setType(0);
        this.mHolder.setFormat(-2);
        this.mHolder.setFormat(-3);
    }

    public SurfaceHolder getSurfaceHolder() {
        return this.mHolder;
    }

    public void setRenderer(DMStickersRenderer dMStickersRenderer) {
        CanvasThread createCanvasThread = createCanvasThread(this.mHolder, dMStickersRenderer);
        this.mCanvasThread = createCanvasThread;
        createCanvasThread.start();
    }

    public CanvasThread createCanvasThread(SurfaceHolder surfaceHolder, DMStickersRenderer dMStickersRenderer) {
        return new CanvasThread(surfaceHolder, dMStickersRenderer);
    }

    public void startRender() {
        setRenderer(new DMStickersRenderer());
    }

    public Bitmap getResultBitmap() {
        return this.mCanvasThread.getResultBitmap();
    }

    public void setBackgroundBitmap(int i, Bitmap bitmap, int i2, int i3, int i4, boolean z) {
        this.mCanvasThread.setBackgroundBitmap(i, bitmap, i2, i3, i4, z);
    }

    public void setForeGroundBitmap(Bitmap bitmap, int i, int i2) {
        this.mCanvasThread.setForeGroundBitmap(bitmap, i, i2);
    }

    public void setStickerCallBack(DMStickerStateCallback dMStickerStateCallback) {
        this.mCanvasThread.setStickerCallBack(dMStickerStateCallback);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread != null) {
            canvasThread.surfaceCreated();
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        CanvasThread canvasThread = this.mCanvasThread;
        if (canvasThread != null) {
            canvasThread.surfaceDestroyed();
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        this.mCanvasThread.onWindowResize(i2, i3);
    }

    public void onPause() {
        this.mCanvasThread.onPause();
    }

    public void onResume() {
        this.mCanvasThread.onResume();
    }

    public void onShow() {
        this.mCanvasThread.onShow();
    }

    public void onHide() {
        this.mCanvasThread.onHide();
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        this.mCanvasThread.onWindowFocusChanged(z);
    }

    public void setEvent(Runnable runnable) {
        this.mCanvasThread.setEvent(runnable);
    }

    public void clearEvent() {
        this.mCanvasThread.clearEvent();
    }

    public void addSticker(DMSticker dMSticker, Matrix matrix, Matrix matrix2, Matrix matrix3) {
        this.mCanvasThread.addSticker(dMSticker, matrix, matrix2, matrix3);
    }

    public void replaceCurrentStickerWithImage(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        this.mCanvasThread.replaceCurrentStickerWithImage(bitmap);
    }

    public void replaceCurrentStickerSize(int i, int i2) {
        this.mCanvasThread.replaceCurrentStickerSize(i, i2);
    }

    public void replaceCurrentStickerWithImageNoBorder(Bitmap bitmap) {
        this.mCanvasThread.replaceCurrentStickerWithImageNoBorder(bitmap);
    }

    public void clearStickers() {
        this.mCanvasThread.clearStickers();
    }

    public void clearStickersOnlyFreePuzzzle() {
        this.mCanvasThread.clearStickersOnlyFreePuzzle();
    }

    public List<DMStickerBMRenderable> getStickers() {
        return this.mCanvasThread.getStickers();
    }

    public void removeCurSelectedSticker() {
        this.mCanvasThread.removeCurSelectedSticker();
    }

    public DMSticker getCurRemoveSticker() {
        return this.mCanvasThread.getCurRemoveSticker();
    }

    public void hideCurSelectedSticker() {
        this.mCanvasThread.hideCurSelectedSticker();
    }

    public void cancelSelected() {
        this.mCanvasThread.cancelSelected();
    }

    public void cloneCurSelectedSticker() {
        this.mCanvasThread.cloneCurSelectedSticker();
    }

    public int getStickersCount() {
        return this.mCanvasThread.getStickersCount();
    }

    public int getStickersNoFreePuzzleCount() {
        return this.mCanvasThread.getStickersNoFreePuzzleCount();
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mCanvasThread.requestExitAndWait();
    }

    protected void stopDrawing() {
        this.mCanvasThread.requestExitAndWait();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mCanvasThread.onTouchEvent(motionEvent);
        if (this.mCanvasThread.getCuRenderable() == null) {
            return this.mTouchResult;
        }
        return true;
    }

    public void setTouchResult(boolean z) {
        this.mTouchResult = z;
    }

    public void setIsShowShadow(boolean z) {
        this.mCanvasThread.setIsShowShadow(z);
    }

    /* loaded from: classes3.dex */
    public class CanvasThread extends Thread {
        private Canvas canvas;
        private DM_ImageBackground mBackground;
        private boolean mContextLost;
        private Runnable mEvent;
        private boolean mHasFocus;
        private boolean mHasSurface;
        private boolean mPaused;
        private DMStickersRenderer mRenderer;
        private SurfaceHolder mSurfaceHolder;
        private boolean mDone = false;
        private int mWidth = 0;
        private int mHeight = 0;
        private DM_ImageTransformPanel mPanel = createPanel();

        public CanvasThread(SurfaceHolder surfaceHolder, DMStickersRenderer dMStickersRenderer) {
            this.mRenderer = dMStickersRenderer;
            this.mSurfaceHolder = surfaceHolder;
            DM_ImageBackground dM_ImageBackground = new DM_ImageBackground(null);
            this.mBackground = dM_ImageBackground;
            this.mRenderer.setBackground(dM_ImageBackground);
            this.mPanel.isVisible = false;
            this.mRenderer.setTransPanel(this.mPanel);
            setName("CanvasThread");
        }

        public DM_ImageTransformPanel createPanel() {
            return new DM_ImageTransformPanel(DMStickersSurfaceView.this.getContext());
        }

        public void setStickerCallBack(DMStickerStateCallback dMStickerStateCallback) {
            synchronized (this) {
                this.mRenderer.setCallback(dMStickerStateCallback);
            }
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            synchronized (this) {
                this.mRenderer.onTouchEvent(motionEvent);
            }
            return true;
        }

        public Bitmap getResultBitmap() {
            Bitmap createFrameBitmap;
            synchronized (this) {
                createFrameBitmap = this.mRenderer.createFrameBitmap();
            }
            return createFrameBitmap;
        }

        public void addSticker(DMSticker dMSticker, Matrix matrix, Matrix matrix2, Matrix matrix3) {
            synchronized (this) {
                DMStickerBMRenderable dMStickerBMRenderable = new DMStickerBMRenderable(dMSticker);
                dMStickerBMRenderable.setLastRotateTransform(matrix);
                dMStickerBMRenderable.setLastPanTransform(matrix2);
                dMStickerBMRenderable.setLastScaleTransform(matrix3);
                this.mRenderer.addSticker(dMStickerBMRenderable);
                this.mPanel.setStickerRenderable(dMStickerBMRenderable);
                this.mPanel.isVisible = true;
            }
        }

        public void clearStickers() {
            synchronized (this) {
                this.mRenderer.clearStickers();
            }
        }

        public void clearStickersOnlyFreePuzzle() {
            synchronized (this) {
                this.mRenderer.clearStickersOnlyFreePuzzle();
            }
        }

        public List<DMStickerBMRenderable> getStickers() {
            List<DMStickerBMRenderable> stickerRenderables;
            synchronized (this) {
                stickerRenderables = this.mRenderer.getStickerRenderables();
            }
            return stickerRenderables;
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
            synchronized (this) {
                this.mRenderer.removeCurSelectedSticker();
            }
        }

        public DMSticker getCurRemoveSticker() {
            DMSticker curRemoveSticker;
            synchronized (this) {
                curRemoveSticker = this.mRenderer.getCurRemoveSticker();
            }
            return curRemoveSticker;
        }

        public void hideCurSelectedSticker() {
            synchronized (this) {
                this.mRenderer.hideCurSelectedSticker();
            }
        }

        public void cancelSelected() {
            synchronized (this) {
                this.mRenderer.cancelSelected();
            }
        }

        public void replaceCurrentStickerWithImage(Bitmap bitmap) {
            synchronized (this) {
                if (bitmap != null) {
                    if (!bitmap.isRecycled()) {
                        if (this.mRenderer == null) {
                            return;
                        }
                        this.mRenderer.replaceCurrentStickerWithImage(bitmap);
                    }
                }
            }
        }

        public void replaceCurrentStickerWithImageNoBorder(Bitmap bitmap) {
            synchronized (this) {
                this.mRenderer.replaceCurrentStickerWithImageNoBorder(bitmap);
            }
        }

        public void replaceCurrentStickerSize(int i, int i2) {
            synchronized (this) {
                this.mRenderer.replaceCurrentStickerSize(i, i2);
            }
        }

        public void cloneCurSelectedSticker() {
            synchronized (this) {
                this.mRenderer.cloneCurSelectedSticker();
            }
        }

        public int getStickersCount() {
            int stickersCount;
            synchronized (this) {
                stickersCount = this.mRenderer.getStickersCount();
            }
            return stickersCount;
        }

        public int getStickersNoFreePuzzleCount() {
            int stickersNoFreePuzzleCount;
            synchronized (this) {
                stickersNoFreePuzzleCount = this.mRenderer.getStickersNoFreePuzzleCount();
            }
            return stickersNoFreePuzzleCount;
        }

        public DMStickerBMRenderable getCuRenderable() {
            DMStickerBMRenderable curRenderable;
            synchronized (this) {
                curRenderable = this.mRenderer.getCurRenderable();
            }
            return curRenderable;
        }

        public void setIsShowShadow(boolean z) {
            synchronized (this) {
                this.mRenderer.setIsShowShadow(z);
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            boolean z;
            int i;
            int i2;
            SurfaceHolder surfaceHolder;
            Canvas canvas;
            while (!this.mDone) {
                synchronized (this) {
                    if (this.mEvent != null) {
                        this.mEvent.run();
                    }
                    if (needToWait()) {
                        while (needToWait()) {
                            try {
                                wait();
                            } catch (InterruptedException unused) {
                            }
                        }
                    }
                    if (this.mDone) {
                        return;
                    }
                    z = DMStickersSurfaceView.this.mSizeChanged;
                    i = this.mWidth;
                    i2 = this.mHeight;
                    DMStickersSurfaceView.this.mSizeChanged = false;
                }
                if (z) {
                    this.mRenderer.sizeChanged(i, i2);
                }
                if (i > 0 && i2 > 0) {
                    try {
                        if (this.mSurfaceHolder != null) {
                            Canvas lockCanvas = this.mSurfaceHolder.lockCanvas();
                            this.canvas = lockCanvas;
                            if (lockCanvas != null) {
                                synchronized (this) {
                                    if (this.canvas != null) {
                                        this.mRenderer.drawFrame(this.canvas);
                                    }
                                }
                            }
                        }
                    } catch (Exception unused2) {
                        if (this.mSurfaceHolder != null) {
                            Surface surface = this.mSurfaceHolder.getSurface();
                            if (this.canvas != null && surface != null && surface.isValid()) {
                                surfaceHolder = this.mSurfaceHolder;
                                canvas = this.canvas;
                            }
                        }
                    } catch (Throwable unused3) {
                        if (this.mSurfaceHolder != null) {
                            Surface surface2 = this.mSurfaceHolder.getSurface();
                            if (this.canvas != null && surface2 != null && surface2.isValid()) {
                                surfaceHolder = this.mSurfaceHolder;
                                canvas = this.canvas;
                            }
                        }
                    }
                    try {
                        if (this.mSurfaceHolder != null) {
                            Surface surface3 = this.mSurfaceHolder.getSurface();
                            if (this.canvas != null && surface3 != null && surface3.isValid()) {
                                surfaceHolder = this.mSurfaceHolder;
                                canvas = this.canvas;
                                surfaceHolder.unlockCanvasAndPost(canvas);
                            }
                        }
                    } catch (Throwable unused4) {
                    }
                }
            }
        }

        private boolean needToWait() {
            return (this.mPaused || !this.mHasFocus || !this.mHasSurface || this.mContextLost) && !this.mDone;
        }

        public void surfaceCreated() {
            synchronized (this) {
                this.mHasSurface = true;
                this.mContextLost = false;
                notify();
            }
        }

        public void surfaceDestroyed() {
            synchronized (this) {
                this.mHasSurface = false;
                notify();
            }
        }

        public void onPause() {
            synchronized (this) {
                this.mPaused = true;
            }
        }

        public void onResume() {
            synchronized (this) {
                this.mPaused = false;
                notify();
                this.mRenderer.onResume();
            }
        }

        public void onShow() {
            synchronized (this) {
                this.mRenderer.onShow();
                this.mHasFocus = true;
            }
        }

        public void onHide() {
            synchronized (this) {
                this.mRenderer.onHide();
            }
        }

        public void onWindowFocusChanged(boolean z) {
            synchronized (this) {
                this.mHasFocus = z;
                if (z) {
                    notify();
                }
            }
        }

        public void onWindowResize(int i, int i2) {
            synchronized (this) {
                this.mWidth = i;
                this.mHeight = i2;
                DMStickersSurfaceView.this.mSizeChanged = true;
            }
        }

        public void requestExitAndWait() {
            synchronized (this) {
                this.mDone = true;
                notify();
            }
            try {
                join();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
        }

        public void setEvent(Runnable runnable) {
            synchronized (this) {
                this.mEvent = runnable;
            }
        }

        public void clearEvent() {
            synchronized (this) {
                this.mEvent = null;
            }
        }
    }
}

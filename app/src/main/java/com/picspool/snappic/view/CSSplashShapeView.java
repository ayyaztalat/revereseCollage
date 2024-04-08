package com.picspool.snappic.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.picspool.instafilter.GPUFilter;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.filter.gpu.normal.GPUImagePixelationFilter;
import com.picspool.lib.filter.gpu.normal.GPUImagePolkaDotFilter;
import com.picspool.lib.filter.listener.OnPostFilteredListener;
import com.picspool.lib.view.redraw.DMReDrawView;

/* loaded from: classes.dex */
public class CSSplashShapeView extends DMReDrawView {
    protected static final int DRAG = 1;
    protected static final int JUMP = 2;
    protected static final int NONE = 0;
    private static final float TOUCH_TOLERANCE = 4.0f;
    protected static final int ZOOM = 3;
    private DrawHandle bwDrawHandle;
    private int curShapeMode;
    private Bitmap filterBitmap;
    private DrawHandle filterDrawHandle;
    private ColorMatrixColorFilter grayColorFilter;
    protected PointF mCurPoint;
    private Bitmap mImageBitmap;
    private Matrix mImageMatrix;
    private float mImageScale;
    private Matrix mMaskMatrix;
    protected PointF mMid;
    private Path mPath;
    private Bitmap mSplashFrame;
    private boolean mSplashInverse;
    private Bitmap mSplashMask;
    private SplashType mSplashType;
    protected PointF mStart;

    /* renamed from: mX */
    private float f1696mX;

    /* renamed from: mY */
    private float f1697mY;
    protected int mode;
    private StyleMode myStyleMode;
    protected float oldDegree;
    protected float oldDist;

    /* loaded from: classes.dex */
    private interface DrawHandle {
        void drawImage(Canvas canvas);

        void drawView(Canvas canvas);
    }

    /* loaded from: classes.dex */
    public enum SplashType {
        shape,
        touch
    }

    /* loaded from: classes.dex */
    public enum StyleMode {
        B_W,
        MOSAIC,
        POLKA_DOT
    }

    public CSSplashShapeView(Context context) {
        this(context, null);
    }

    public CSSplashShapeView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CSSplashShapeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.myStyleMode = StyleMode.B_W;
        this.mSplashType = SplashType.shape;
        this.curShapeMode = 0;
        this.mImageScale = 1.0f;
        this.mSplashInverse = false;
        this.bwDrawHandle = new DrawHandle() { // from class: com.picspool.snappic.view.CSSplashShapeView.3
            @Override // com.picspool.snappic.view.CSSplashShapeView.DrawHandle
            public void drawView(Canvas canvas) {
                if (CSSplashShapeView.this.mImageBitmap != null && !CSSplashShapeView.this.mImageBitmap.isRecycled()) {
                    if (!CSSplashShapeView.this.mSplashInverse) {
                        CSSplashShapeView.this.mPaint.setColorFilter(CSSplashShapeView.this.grayColorFilter);
                    }
                    canvas.drawBitmap(CSSplashShapeView.this.mImageBitmap, CSSplashShapeView.this.mImageMatrix, CSSplashShapeView.this.mPaint);
                    CSSplashShapeView.this.mPaint.setColorFilter(null);
                }
                if (CSSplashShapeView.this.mSplashType == SplashType.shape) {
                    int saveLayer = canvas.saveLayer(0.0f, 0.0f, CSSplashShapeView.this.canvasWidth, CSSplashShapeView.this.canvasHeight, null, 31);
                    if (CSSplashShapeView.this.mSplashMask != null && !CSSplashShapeView.this.mSplashMask.isRecycled()) {
                        canvas.drawBitmap(CSSplashShapeView.this.mSplashMask, CSSplashShapeView.this.mMaskMatrix, CSSplashShapeView.this.mPaint);
                    }
                    if (CSSplashShapeView.this.mImageBitmap != null && !CSSplashShapeView.this.mImageBitmap.isRecycled()) {
                        CSSplashShapeView.this.mPaint.setXfermode(CSSplashShapeView.this.srcInXfermode);
                        if (CSSplashShapeView.this.mSplashInverse) {
                            CSSplashShapeView.this.mPaint.setColorFilter(CSSplashShapeView.this.grayColorFilter);
                        }
                        canvas.drawBitmap(CSSplashShapeView.this.mImageBitmap, CSSplashShapeView.this.mImageMatrix, CSSplashShapeView.this.mPaint);
                        CSSplashShapeView.this.mPaint.setColorFilter(null);
                        CSSplashShapeView.this.mPaint.setXfermode(null);
                    }
                    canvas.restoreToCount(saveLayer);
                    if (CSSplashShapeView.this.mSplashFrame != null && !CSSplashShapeView.this.mSplashFrame.isRecycled()) {
                        canvas.drawBitmap(CSSplashShapeView.this.mSplashFrame, CSSplashShapeView.this.mMaskMatrix, CSSplashShapeView.this.mPaint);
                    }
                }
                if (CSSplashShapeView.this.mSplashType == SplashType.touch) {
                    int saveLayer2 = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null, 31);
                    canvas.drawPath(CSSplashShapeView.this.mPath, CSSplashShapeView.this.mPaint);
                    CSSplashShapeView.this.mPaint.setXfermode(CSSplashShapeView.this.srcInXfermode);
                    if (CSSplashShapeView.this.mSplashInverse) {
                        CSSplashShapeView.this.mPaint.setColorFilter(CSSplashShapeView.this.grayColorFilter);
                    }
                    canvas.drawBitmap(CSSplashShapeView.this.mImageBitmap, CSSplashShapeView.this.mImageMatrix, CSSplashShapeView.this.mPaint);
                    CSSplashShapeView.this.mPaint.setColorFilter(null);
                    CSSplashShapeView.this.mPaint.setXfermode(null);
                    canvas.restoreToCount(saveLayer2);
                }
            }

            @Override // com.picspool.snappic.view.CSSplashShapeView.DrawHandle
            public void drawImage(Canvas canvas) {
                int width = canvas.getWidth();
                int height = canvas.getHeight();
                float f = width;
                float f2 = f / CSSplashShapeView.this.canvasWidth;
                Matrix matrix = new Matrix();
                matrix.set(CSSplashShapeView.this.mImageMatrix);
                Matrix matrix2 = new Matrix();
                matrix2.set(CSSplashShapeView.this.mMaskMatrix);
                matrix.postScale(f2, f2);
                matrix2.postScale(f2, f2);
                if (CSSplashShapeView.this.mImageBitmap != null && !CSSplashShapeView.this.mImageBitmap.isRecycled()) {
                    if (!CSSplashShapeView.this.mSplashInverse) {
                        CSSplashShapeView.this.mPaint.setColorFilter(CSSplashShapeView.this.grayColorFilter);
                    }
                    canvas.drawBitmap(CSSplashShapeView.this.mImageBitmap, matrix, CSSplashShapeView.this.mPaint);
                    CSSplashShapeView.this.mPaint.setColorFilter(null);
                }
                if (CSSplashShapeView.this.mSplashType == SplashType.shape) {
                    int saveLayer = canvas.saveLayer(0.0f, 0.0f, f, height, null, 31);
                    if (CSSplashShapeView.this.mSplashMask != null && !CSSplashShapeView.this.mSplashMask.isRecycled()) {
                        canvas.drawBitmap(CSSplashShapeView.this.mSplashMask, matrix2, CSSplashShapeView.this.mPaint);
                    }
                    if (CSSplashShapeView.this.mImageBitmap != null && !CSSplashShapeView.this.mImageBitmap.isRecycled()) {
                        CSSplashShapeView.this.mPaint.setXfermode(CSSplashShapeView.this.srcInXfermode);
                        if (CSSplashShapeView.this.mSplashInverse) {
                            CSSplashShapeView.this.mPaint.setColorFilter(CSSplashShapeView.this.grayColorFilter);
                        }
                        canvas.drawBitmap(CSSplashShapeView.this.mImageBitmap, matrix, CSSplashShapeView.this.mPaint);
                        CSSplashShapeView.this.mPaint.setColorFilter(null);
                        CSSplashShapeView.this.mPaint.setXfermode(null);
                    }
                    canvas.restoreToCount(saveLayer);
                    if (CSSplashShapeView.this.mSplashFrame != null && !CSSplashShapeView.this.mSplashFrame.isRecycled()) {
                        canvas.drawBitmap(CSSplashShapeView.this.mSplashFrame, matrix2, CSSplashShapeView.this.mPaint);
                    }
                }
                if (CSSplashShapeView.this.mSplashType == SplashType.touch) {
                    int saveLayer2 = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null, 31);
                    canvas.drawPath(CSSplashShapeView.this.mPath, CSSplashShapeView.this.mPaint);
                    CSSplashShapeView.this.mPaint.setXfermode(CSSplashShapeView.this.srcInXfermode);
                    if (CSSplashShapeView.this.mSplashInverse) {
                        CSSplashShapeView.this.mPaint.setColorFilter(CSSplashShapeView.this.grayColorFilter);
                    }
                    canvas.drawBitmap(CSSplashShapeView.this.mImageBitmap, CSSplashShapeView.this.mImageMatrix, CSSplashShapeView.this.mPaint);
                    CSSplashShapeView.this.mPaint.setColorFilter(null);
                    CSSplashShapeView.this.mPaint.setXfermode(null);
                    canvas.restoreToCount(saveLayer2);
                }
            }
        };
        this.filterDrawHandle = new DrawHandle() { // from class: com.picspool.snappic.view.CSSplashShapeView.4
            @Override // com.picspool.snappic.view.CSSplashShapeView.DrawHandle
            public void drawView(Canvas canvas) {
                if (!CSSplashShapeView.this.mSplashInverse) {
                    if (CSSplashShapeView.this.filterBitmap != null && !CSSplashShapeView.this.filterBitmap.isRecycled()) {
                        canvas.drawBitmap(CSSplashShapeView.this.filterBitmap, CSSplashShapeView.this.mImageMatrix, CSSplashShapeView.this.mPaint);
                        CSSplashShapeView.this.mPaint.setColorFilter(null);
                    }
                } else if (CSSplashShapeView.this.mImageBitmap != null && !CSSplashShapeView.this.mImageBitmap.isRecycled()) {
                    canvas.drawBitmap(CSSplashShapeView.this.mImageBitmap, CSSplashShapeView.this.mImageMatrix, CSSplashShapeView.this.mPaint);
                    CSSplashShapeView.this.mPaint.setColorFilter(null);
                }
                if (CSSplashShapeView.this.mSplashType == SplashType.shape) {
                    int saveLayer = canvas.saveLayer(0.0f, 0.0f, CSSplashShapeView.this.canvasWidth, CSSplashShapeView.this.canvasHeight, null, 31);
                    if (CSSplashShapeView.this.mSplashMask != null && !CSSplashShapeView.this.mSplashMask.isRecycled()) {
                        canvas.drawBitmap(CSSplashShapeView.this.mSplashMask, CSSplashShapeView.this.mMaskMatrix, CSSplashShapeView.this.mPaint);
                    }
                    if (CSSplashShapeView.this.mSplashInverse) {
                        if (CSSplashShapeView.this.filterBitmap != null && !CSSplashShapeView.this.filterBitmap.isRecycled()) {
                            CSSplashShapeView.this.mPaint.setXfermode(CSSplashShapeView.this.srcInXfermode);
                            canvas.drawBitmap(CSSplashShapeView.this.filterBitmap, CSSplashShapeView.this.mImageMatrix, CSSplashShapeView.this.mPaint);
                            CSSplashShapeView.this.mPaint.setColorFilter(null);
                            CSSplashShapeView.this.mPaint.setXfermode(null);
                        }
                    } else if (CSSplashShapeView.this.mImageBitmap != null && !CSSplashShapeView.this.mImageBitmap.isRecycled()) {
                        CSSplashShapeView.this.mPaint.setXfermode(CSSplashShapeView.this.srcInXfermode);
                        canvas.drawBitmap(CSSplashShapeView.this.mImageBitmap, CSSplashShapeView.this.mImageMatrix, CSSplashShapeView.this.mPaint);
                        CSSplashShapeView.this.mPaint.setColorFilter(null);
                        CSSplashShapeView.this.mPaint.setXfermode(null);
                    }
                    canvas.restoreToCount(saveLayer);
                    if (CSSplashShapeView.this.mSplashFrame != null && !CSSplashShapeView.this.mSplashFrame.isRecycled()) {
                        canvas.drawBitmap(CSSplashShapeView.this.mSplashFrame, CSSplashShapeView.this.mMaskMatrix, CSSplashShapeView.this.mPaint);
                    }
                }
                if (CSSplashShapeView.this.mSplashType == SplashType.touch) {
                    int saveLayer2 = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null, 31);
                    canvas.drawPath(CSSplashShapeView.this.mPath, CSSplashShapeView.this.mPaint);
                    CSSplashShapeView.this.mPaint.setXfermode(CSSplashShapeView.this.srcInXfermode);
                    if (CSSplashShapeView.this.mSplashInverse) {
                        CSSplashShapeView.this.mPaint.setColorFilter(CSSplashShapeView.this.grayColorFilter);
                    }
                    canvas.drawBitmap(CSSplashShapeView.this.mImageBitmap, CSSplashShapeView.this.mImageMatrix, CSSplashShapeView.this.mPaint);
                    CSSplashShapeView.this.mPaint.setColorFilter(null);
                    CSSplashShapeView.this.mPaint.setXfermode(null);
                    canvas.restoreToCount(saveLayer2);
                }
            }

            @Override // com.picspool.snappic.view.CSSplashShapeView.DrawHandle
            public void drawImage(Canvas canvas) {
                int width = canvas.getWidth();
                int height = canvas.getHeight();
                float f = width;
                float f2 = f / CSSplashShapeView.this.canvasWidth;
                Matrix matrix = new Matrix();
                matrix.set(CSSplashShapeView.this.mImageMatrix);
                Matrix matrix2 = new Matrix();
                matrix2.set(CSSplashShapeView.this.mMaskMatrix);
                matrix.postScale(f2, f2);
                matrix2.postScale(f2, f2);
                if (!CSSplashShapeView.this.mSplashInverse) {
                    if (CSSplashShapeView.this.filterBitmap != null && !CSSplashShapeView.this.filterBitmap.isRecycled()) {
                        canvas.drawBitmap(CSSplashShapeView.this.filterBitmap, matrix, CSSplashShapeView.this.mPaint);
                        CSSplashShapeView.this.mPaint.setColorFilter(null);
                    }
                } else if (CSSplashShapeView.this.mImageBitmap != null && !CSSplashShapeView.this.mImageBitmap.isRecycled()) {
                    canvas.drawBitmap(CSSplashShapeView.this.mImageBitmap, matrix, CSSplashShapeView.this.mPaint);
                    CSSplashShapeView.this.mPaint.setColorFilter(null);
                }
                if (CSSplashShapeView.this.mSplashType == SplashType.shape) {
                    int saveLayer = canvas.saveLayer(0.0f, 0.0f, f, height, null, 31);
                    if (CSSplashShapeView.this.mSplashMask != null && !CSSplashShapeView.this.mSplashMask.isRecycled()) {
                        canvas.drawBitmap(CSSplashShapeView.this.mSplashMask, matrix2, CSSplashShapeView.this.mPaint);
                    }
                    if (CSSplashShapeView.this.mSplashInverse) {
                        if (CSSplashShapeView.this.filterBitmap != null && !CSSplashShapeView.this.filterBitmap.isRecycled()) {
                            CSSplashShapeView.this.mPaint.setXfermode(CSSplashShapeView.this.srcInXfermode);
                            canvas.drawBitmap(CSSplashShapeView.this.filterBitmap, matrix, CSSplashShapeView.this.mPaint);
                            CSSplashShapeView.this.mPaint.setColorFilter(null);
                            CSSplashShapeView.this.mPaint.setXfermode(null);
                        }
                    } else if (CSSplashShapeView.this.mImageBitmap != null && !CSSplashShapeView.this.mImageBitmap.isRecycled()) {
                        CSSplashShapeView.this.mPaint.setXfermode(CSSplashShapeView.this.srcInXfermode);
                        canvas.drawBitmap(CSSplashShapeView.this.mImageBitmap, matrix, CSSplashShapeView.this.mPaint);
                        CSSplashShapeView.this.mPaint.setColorFilter(null);
                        CSSplashShapeView.this.mPaint.setXfermode(null);
                    }
                    canvas.restoreToCount(saveLayer);
                    if (CSSplashShapeView.this.mSplashFrame != null && !CSSplashShapeView.this.mSplashFrame.isRecycled()) {
                        canvas.drawBitmap(CSSplashShapeView.this.mSplashFrame, matrix2, CSSplashShapeView.this.mPaint);
                    }
                }
                if (CSSplashShapeView.this.mSplashType == SplashType.touch) {
                    int saveLayer2 = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null, 31);
                    canvas.drawPath(CSSplashShapeView.this.mPath, CSSplashShapeView.this.mPaint);
                    CSSplashShapeView.this.mPaint.setXfermode(CSSplashShapeView.this.srcInXfermode);
                    if (CSSplashShapeView.this.mSplashInverse) {
                        CSSplashShapeView.this.mPaint.setColorFilter(CSSplashShapeView.this.grayColorFilter);
                    }
                    canvas.drawBitmap(CSSplashShapeView.this.mImageBitmap, CSSplashShapeView.this.mImageMatrix, CSSplashShapeView.this.mPaint);
                    CSSplashShapeView.this.mPaint.setColorFilter(null);
                    CSSplashShapeView.this.mPaint.setXfermode(null);
                    canvas.restoreToCount(saveLayer2);
                }
            }
        };
        this.mode = 0;
        this.mStart = new PointF();
        this.mMid = new PointF();
        this.mCurPoint = new PointF();
        this.mPath = new Path();
        initColorMatrix();
    }

    private void initColorMatrix() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        this.grayColorFilter = new ColorMatrixColorFilter(colorMatrix);
    }

    public void setSplashType(SplashType splashType) {
        this.mSplashType = splashType;
        if (splashType == SplashType.touch) {
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeJoin(Paint.Join.ROUND);
            this.mPaint.setStrokeCap(Paint.Cap.ROUND);
            this.mPaint.setStrokeWidth(12.0f);
        }
    }

    public void loadSplashShape(int i) {
        if (i == this.curShapeMode) {
            setSplashInverse();
            return;
        }
        Bitmap bitmap = this.mSplashMask;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mSplashMask.recycle();
        }
        this.mSplashMask = null;
        Bitmap bitmap2 = this.mSplashFrame;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.mSplashFrame.recycle();
        }
        Resources resources = getResources();
        this.mSplashFrame = DMBitmapUtil.getImageFromAssetsFile(resources, "splash/" + i + "_frame.png");
        Resources resources2 = getResources();
        this.mSplashMask = DMBitmapUtil.getImageFromAssetsFile(resources2, "splash/" + i + "_mask.png");
        this.curShapeMode = i;
        resetMaskMatrix();
        invalidate();
    }

    public void setStyleMode(StyleMode styleMode) {
        Bitmap bitmap;
        if (this.myStyleMode == styleMode) {
            return;
        }
        if (styleMode == StyleMode.B_W) {
            this.myStyleMode = StyleMode.B_W;
            Bitmap bitmap2 = this.filterBitmap;
            if (bitmap2 != null && !bitmap2.isRecycled()) {
                this.filterBitmap.recycle();
                this.filterBitmap = null;
            }
            invalidate();
        } else if (styleMode == StyleMode.MOSAIC) {
            Bitmap bitmap3 = this.mImageBitmap;
            if (bitmap3 == null || bitmap3.isRecycled()) {
                return;
            }
            Bitmap bitmap4 = this.filterBitmap;
            if (bitmap4 != null && !bitmap4.isRecycled()) {
                this.filterBitmap.recycle();
                this.filterBitmap = null;
            }
            GPUImagePixelationFilter gPUImagePixelationFilter = new GPUImagePixelationFilter();
            gPUImagePixelationFilter.setOutBitmap(true);
            gPUImagePixelationFilter.setFractionalWidthOfPixel(0.04f);
            GPUFilter.asyncFilterForFilter(this.mImageBitmap, gPUImagePixelationFilter, new OnPostFilteredListener() { // from class: com.picspool.snappic.view.CSSplashShapeView.1
                @Override // com.picspool.lib.filter.listener.OnPostFilteredListener
                public void postFiltered(Bitmap bitmap5) {
                    CSSplashShapeView.this.filterBitmap = bitmap5;
                    CSSplashShapeView.this.myStyleMode = StyleMode.MOSAIC;
                    CSSplashShapeView.this.invalidate();
                }
            });
        } else if (styleMode != StyleMode.POLKA_DOT || (bitmap = this.mImageBitmap) == null || bitmap.isRecycled()) {
        } else {
            Bitmap bitmap5 = this.filterBitmap;
            if (bitmap5 != null && !bitmap5.isRecycled()) {
                this.filterBitmap.recycle();
                this.filterBitmap = null;
            }
            GPUImagePolkaDotFilter gPUImagePolkaDotFilter = new GPUImagePolkaDotFilter();
            gPUImagePolkaDotFilter.setOutBitmap(true);
            gPUImagePolkaDotFilter.setFractionalWidthOfPixel(0.04f);
            gPUImagePolkaDotFilter.setDotScaling(0.8f);
            GPUFilter.asyncFilterForFilter(this.mImageBitmap, gPUImagePolkaDotFilter, new OnPostFilteredListener() { // from class: com.picspool.snappic.view.CSSplashShapeView.2
                @Override // com.picspool.lib.filter.listener.OnPostFilteredListener
                public void postFiltered(Bitmap bitmap6) {
                    CSSplashShapeView.this.filterBitmap = bitmap6;
                    CSSplashShapeView.this.myStyleMode = StyleMode.POLKA_DOT;
                    CSSplashShapeView.this.invalidate();
                }
            });
        }
    }

    private void resetMaskMatrix() {
        Bitmap bitmap;
        this.mMaskMatrix = new Matrix();
        Bitmap bitmap2 = this.mImageBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled() && (bitmap = this.mSplashMask) != null && !bitmap.isRecycled()) {
            int width = this.mImageBitmap.getWidth();
            int height = this.mImageBitmap.getHeight();
            float f = width;
            float width2 = f / (this.mSplashMask.getWidth() * 2.0f);
            if (width > height) {
                width2 = height / (this.mSplashMask.getHeight() * 2.0f);
            }
            int i = this.curShapeMode;
            if (i == 2) {
                if (width < height) {
                    float width3 = f / this.mSplashMask.getWidth();
                    this.mMaskMatrix.postScale(width3, width3);
                    this.mMaskMatrix.postTranslate(0.0f, (height - width) / 2.0f);
                } else {
                    float width4 = height / this.mSplashMask.getWidth();
                    this.mMaskMatrix.postScale(width4, width4);
                    this.mMaskMatrix.postTranslate((width - height) / 2.0f, 0.0f);
                }
            } else if (i != 4 && i != 5 && i != 6) {
                this.mMaskMatrix.postScale(width2, width2);
                this.mMaskMatrix.postTranslate(f / 5.0f, height / TOUCH_TOLERANCE);
                this.mMaskMatrix.postRotate(-15.0f);
            } else if (width < height) {
                float width5 = (f / 1.3f) / this.mSplashMask.getWidth();
                this.mMaskMatrix.postScale(width5, width5);
                float[] fArr = {this.mSplashMask.getWidth(), this.mSplashMask.getHeight()};
                this.mMaskMatrix.mapPoints(fArr);
                this.mMaskMatrix.postTranslate((f - fArr[0]) / 2.0f, (height - fArr[1]) / 2.0f);
            } else {
                float f2 = height;
                float height2 = (f2 / 1.3f) / this.mSplashMask.getHeight();
                this.mMaskMatrix.postScale(height2, height2);
                float[] fArr2 = {this.mSplashMask.getWidth(), this.mSplashMask.getHeight()};
                this.mMaskMatrix.mapPoints(fArr2);
                this.mMaskMatrix.postTranslate((f - fArr2[0]) / 2.0f, (f2 - fArr2[1]) / 2.0f);
            }
        }
        Matrix matrix = this.mMaskMatrix;
        float f3 = this.mImageScale;
        matrix.postScale(f3, f3);
    }

    public void setImageBitmap(Bitmap bitmap, float f) {
        this.mImageBitmap = bitmap;
        Matrix matrix = new Matrix();
        this.mImageMatrix = matrix;
        matrix.postScale(f, f);
        this.mImageScale = f;
        resetMaskMatrix();
    }

    public void destroy() {
        Bitmap bitmap = this.mSplashMask;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mSplashMask.recycle();
        }
        this.mSplashMask = null;
        Bitmap bitmap2 = this.mSplashFrame;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.mSplashFrame.recycle();
        }
        Bitmap bitmap3 = this.filterBitmap;
        if (bitmap3 != null && !bitmap3.isRecycled()) {
            this.filterBitmap.recycle();
        }
        this.filterBitmap = null;
    }

    public void setSplashInverse() {
        this.mSplashInverse = !this.mSplashInverse;
        invalidate();
    }

    @Override // com.picspool.lib.view.redraw.DMReDrawView
    public void drawView(Canvas canvas) {
        if (this.myStyleMode == StyleMode.B_W) {
            this.bwDrawHandle.drawView(canvas);
        } else {
            this.filterDrawHandle.drawView(canvas);
        }
    }

    public void drawImage(Canvas canvas) {
        if (this.myStyleMode == StyleMode.B_W) {
            this.bwDrawHandle.drawImage(canvas);
        } else {
            this.filterDrawHandle.drawImage(canvas);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mCurPoint.set(motionEvent.getX(), motionEvent.getY());
        try {
            int action = motionEvent.getAction() & 255;
            if (action == 0) {
                touch_start(this.mCurPoint);
                this.mode = 1;
                this.mStart.set(this.mCurPoint.x, this.mCurPoint.y);
            } else if (action == 1) {
                touch_up();
                this.mode = 0;
            } else if (action == 2) {
                touch_move(this.mCurPoint);
                float f = this.mCurPoint.x - this.mStart.x;
                float f2 = this.mCurPoint.y - this.mStart.y;
                if (this.mode == 1) {
                    postTranslate(f, f2);
                    this.mStart.set(this.mCurPoint.x, this.mCurPoint.y);
                }
                if (this.mode == 2) {
                    this.mode = 1;
                    this.mStart.set(this.mCurPoint.x, this.mCurPoint.y);
                }
                if (this.mode == 3) {
                    float spacing = (float) spacing(motionEvent);
                    midPoint(this.mMid, motionEvent);
                    postScale(spacing / this.oldDist);
                    this.oldDist = spacing;
                    float rotation = rotation(motionEvent);
                    postRotation(rotation - this.oldDegree);
                    this.oldDegree = rotation;
                }
            } else if (action == 5) {
                if (motionEvent.getActionIndex() < 1) {
                    this.mStart.set(this.mCurPoint.x, this.mCurPoint.y);
                }
                this.oldDist = (float) spacing(motionEvent);
                this.oldDegree = rotation(motionEvent);
                this.mode = 3;
                midPoint(this.mMid, motionEvent);
            } else if (action == 6) {
                this.mode = 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private double spacing(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return Math.sqrt((x * x) + (y * y));
    }

    private void midPoint(PointF pointF, MotionEvent motionEvent) {
        pointF.set((motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f, (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f);
    }

    private float rotation(MotionEvent motionEvent) {
        return (float) Math.toDegrees(Math.atan2(motionEvent.getY(0) - motionEvent.getY(1), motionEvent.getX(0) - motionEvent.getX(1)));
    }

    public void postTranslate(float f, float f2) {
        this.mMaskMatrix.postTranslate(f, f2);
        invalidate();
    }

    public void postScale(float f) {
        this.mMaskMatrix.postScale(f, f, this.mMid.x, this.mMid.y);
        invalidate();
    }

    public void postRotation(float f) {
        this.mMaskMatrix.postRotate(f, this.mMid.x, this.mMid.y);
        invalidate();
    }

    private void touch_start(PointF pointF) {
        this.mPath.reset();
        this.mPath.moveTo(pointF.x, pointF.y);
        this.f1696mX = pointF.x;
        this.f1697mY = pointF.y;
    }

    private void touch_move(PointF pointF) {
        float abs = Math.abs(pointF.x - this.f1696mX);
        float abs2 = Math.abs(pointF.y - this.f1697mY);
        if (abs >= TOUCH_TOLERANCE || abs2 >= TOUCH_TOLERANCE) {
            this.mPath.quadTo(this.f1696mX, this.f1697mY, (pointF.x + this.f1696mX) / 2.0f, (pointF.y + this.f1697mY) / 2.0f);
            this.f1696mX = pointF.x;
            this.f1697mY = pointF.y;
        }
    }

    private void touch_up() {
        this.mPath.lineTo(this.f1696mX, this.f1697mY);
        this.mPath.reset();
    }
}

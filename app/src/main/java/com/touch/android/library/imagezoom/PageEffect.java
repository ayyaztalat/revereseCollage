package com.touch.android.library.imagezoom;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Region;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import androidx.core.view.InputDeviceCompat;

/* loaded from: classes3.dex */
public class PageEffect {
    private static final String TAG = "hmg";
    BlurMaskFilter blurFilter;
    int[] mBackShadowColors;
    GradientDrawable mBackShadowDrawableLR;
    GradientDrawable mBackShadowDrawableRL;
    PointF mBezierControl1;
    PointF mBezierControl2;
    PointF mBezierEnd1;
    PointF mBezierEnd2;
    PointF mBezierStart1;
    PointF mBezierStart2;
    PointF mBeziervertex1;
    PointF mBeziervertex2;
    private Bitmap mBitmap;
    private Paint mBitmapPaint;
    private Canvas mCanvas;
    private int mCornerX;
    private int mCornerY;
    Bitmap mCurPageBackBitmap;
    Bitmap mCurPageBitmap;
    float mDegrees;
    GradientDrawable mFolderShadowDrawableLR;
    GradientDrawable mFolderShadowDrawableRL;
    int[] mFrontShadowColors;
    GradientDrawable mFrontShadowDrawableHBT;
    GradientDrawable mFrontShadowDrawableHTB;
    GradientDrawable mFrontShadowDrawableVLR;
    GradientDrawable mFrontShadowDrawableVRL;
    private int mHeight;
    boolean mIsRTandLB;
    float mMaxLength;
    float mMiddleX;
    float mMiddleY;
    Bitmap mNextPageBitmap;
    private Path mPath0;
    private Path mPath1;
    PointF mTouch;
    float mTouchToCornerDis;
    PointF mTransparent1;
    PointF mTransparent2;
    private int mWidth;
    Paint paint;

    public PageEffect() {
        this.mWidth = 480;
        this.mHeight = 800;
        this.mCornerX = 0;
        this.mCornerY = 0;
        this.mCurPageBitmap = null;
        this.mCurPageBackBitmap = null;
        this.mNextPageBitmap = null;
        this.mTouch = new PointF();
        this.mBezierStart1 = new PointF();
        this.mBezierControl1 = new PointF();
        this.mBeziervertex1 = new PointF();
        this.mBezierEnd1 = new PointF();
        this.mBezierStart2 = new PointF();
        this.mBezierControl2 = new PointF();
        this.mBeziervertex2 = new PointF();
        this.mBezierEnd2 = new PointF();
        this.mTransparent1 = new PointF();
        this.mTransparent2 = new PointF();
        this.mMaxLength = (float) Math.hypot(480.0d, 800.0d);
        this.blurFilter = new BlurMaskFilter(15.0f, BlurMaskFilter.Blur.OUTER);
        this.mPath0 = new Path();
        this.mPath1 = new Path();
        createDrawable();
        this.mBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
        this.mCanvas = new Canvas(this.mBitmap);
        this.mBitmapPaint = new Paint(4);
        this.mCurPageBitmap = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(this.mCurPageBitmap);
        this.paint = new Paint();
        canvas.drawColor(InputDeviceCompat.SOURCE_ANY);
        canvas.drawBitmap(this.mCurPageBitmap, 0.0f, 0.0f, this.paint);
        this.mNextPageBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
        new Canvas(this.mNextPageBitmap).drawBitmap(this.mNextPageBitmap, 0.0f, 0.0f, this.paint);
    }

    public PageEffect(Bitmap bitmap, boolean z) {
        this.mWidth = 480;
        this.mHeight = 800;
        this.mCornerX = 0;
        this.mCornerY = 0;
        this.mCurPageBitmap = null;
        this.mCurPageBackBitmap = null;
        this.mNextPageBitmap = null;
        this.mTouch = new PointF();
        this.mBezierStart1 = new PointF();
        this.mBezierControl1 = new PointF();
        this.mBeziervertex1 = new PointF();
        this.mBezierEnd1 = new PointF();
        this.mBezierStart2 = new PointF();
        this.mBezierControl2 = new PointF();
        this.mBeziervertex2 = new PointF();
        this.mBezierEnd2 = new PointF();
        this.mTransparent1 = new PointF();
        this.mTransparent2 = new PointF();
        this.mMaxLength = (float) Math.hypot(480.0d, 800.0d);
        this.blurFilter = new BlurMaskFilter(15.0f, BlurMaskFilter.Blur.OUTER);
        this.mPath0 = new Path();
        this.mPath1 = new Path();
        createDrawable();
        if (z) {
            if (bitmap == null) {
                this.mBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
                this.mCanvas = new Canvas(this.mBitmap);
                this.mBitmapPaint = new Paint(4);
                this.mCurPageBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(this.mCurPageBitmap);
                this.paint = new Paint();
                canvas.drawColor(InputDeviceCompat.SOURCE_ANY);
                canvas.drawBitmap(this.mCurPageBitmap, 0.0f, 0.0f, this.paint);
                this.mNextPageBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
                return;
            }
            this.mWidth = bitmap.getWidth();
            int height = bitmap.getHeight();
            this.mHeight = height;
            this.mBitmap = Bitmap.createBitmap(this.mWidth, height, Bitmap.Config.ARGB_8888);
            this.mCanvas = new Canvas(this.mBitmap);
            this.mBitmapPaint = new Paint(4);
            this.mCurPageBitmap = bitmap;
            this.mNextPageBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
        }
    }

    private void calcCornerXY(float f, float f2) {
        int i = this.mWidth;
        if (f <= i / 2) {
            this.mCornerX = 0;
        } else {
            this.mCornerX = i;
        }
        int i2 = this.mHeight;
        if (f2 <= i2 / 2) {
            this.mCornerY = 0;
        } else {
            this.mCornerY = i2;
        }
        if ((this.mCornerX == 0 && this.mCornerY == this.mHeight) || (this.mCornerX == this.mWidth && this.mCornerY == 0)) {
            this.mIsRTandLB = true;
        } else {
            this.mIsRTandLB = false;
        }
    }

    public PointF getCross(PointF pointF, PointF pointF2, PointF pointF3, PointF pointF4) {
        PointF pointF5 = new PointF();
        float f = (pointF2.y - pointF.y) / (pointF2.x - pointF.x);
        float f2 = ((pointF.x * pointF2.y) - (pointF2.x * pointF.y)) / (pointF.x - pointF2.x);
        pointF5.x = ((((pointF3.x * pointF4.y) - (pointF4.x * pointF3.y)) / (pointF3.x - pointF4.x)) - f2) / (f - ((pointF4.y - pointF3.y) / (pointF4.x - pointF3.x)));
        pointF5.y = (f * pointF5.x) + f2;
        return pointF5;
    }

    private void calcPoints() {
        this.mMiddleX = (this.mTouch.x + this.mCornerX) / 2.0f;
        float f = this.mTouch.y;
        int i = this.mCornerY;
        float f2 = (f + i) / 2.0f;
        this.mMiddleY = f2;
        PointF pointF = this.mBezierControl1;
        float f3 = this.mMiddleX;
        pointF.x = f3 - (((i - f2) * (i - f2)) / (this.mCornerX - f3));
        this.mBezierControl1.y = this.mCornerY;
        this.mBezierControl2.x = this.mCornerX;
        PointF pointF2 = this.mBezierControl2;
        float f4 = this.mMiddleY;
        int i2 = this.mCornerX;
        float f5 = this.mMiddleX;
        pointF2.y = f4 - (((i2 - f5) * (i2 - f5)) / (this.mCornerY - f4));
        Log.i(TAG, "mTouchX  " + this.mTouch.x + "  mTouchY  " + this.mTouch.y);
        Log.i(TAG, "mBezierControl1.x  " + this.mBezierControl1.x + "  mBezierControl1.y  " + this.mBezierControl1.y);
        Log.i(TAG, "mBezierControl2.x  " + this.mBezierControl2.x + "  mBezierControl2.y  " + this.mBezierControl2.y);
        this.mBezierStart1.x = this.mBezierControl1.x - ((((float) this.mCornerX) - this.mBezierControl1.x) / 2.0f);
        this.mBezierStart1.y = (float) this.mCornerY;
        this.mBezierStart2.x = (float) this.mCornerX;
        this.mBezierStart2.y = this.mBezierControl2.y - ((((float) this.mCornerY) - this.mBezierControl2.y) / 2.0f);
        Log.i(TAG, "mBezierStart1.x  " + this.mBezierStart1.x + "  mBezierStart1.y  " + this.mBezierStart1.y);
        Log.i(TAG, "mBezierStart2.x  " + this.mBezierStart2.x + "  mBezierStart2.y  " + this.mBezierStart2.y);
        this.mTouchToCornerDis = (float) Math.hypot((double) (this.mTouch.x - ((float) this.mCornerX)), (double) (this.mTouch.y - ((float) this.mCornerY)));
        this.mBezierEnd1 = getCross(this.mTouch, this.mBezierControl1, this.mBezierStart1, this.mBezierStart2);
        this.mBezierEnd2 = getCross(this.mTouch, this.mBezierControl2, this.mBezierStart1, this.mBezierStart2);
        Log.i(TAG, "mBezierEnd1.x  " + this.mBezierEnd1.x + "  mBezierEnd1.y  " + this.mBezierEnd1.y);
        Log.i(TAG, "mBezierEnd2.x  " + this.mBezierEnd2.x + "  mBezierEnd2.y  " + this.mBezierEnd2.y);
        this.mBeziervertex1.x = ((this.mBezierStart1.x + (this.mBezierControl1.x * 2.0f)) + this.mBezierEnd1.x) / 4.0f;
        this.mBeziervertex1.y = (((this.mBezierControl1.y * 2.0f) + this.mBezierStart1.y) + this.mBezierEnd1.y) / 4.0f;
        this.mBeziervertex2.x = ((this.mBezierStart2.x + (this.mBezierControl2.x * 2.0f)) + this.mBezierEnd2.x) / 4.0f;
        this.mBeziervertex2.y = (((this.mBezierControl2.y * 2.0f) + this.mBezierStart2.y) + this.mBezierEnd2.y) / 4.0f;
        Log.i(TAG, "mBeziervertex1.x  " + this.mBeziervertex1.x + "  mBeziervertex1.y  " + this.mBeziervertex1.y);
        Log.i(TAG, "mBeziervertex2.x  " + this.mBeziervertex2.x + "  mBeziervertex2.y  " + this.mBeziervertex2.y);
        float f6 = (this.mBeziervertex2.y - this.mBeziervertex1.y) / (this.mBeziervertex2.x - this.mBeziervertex1.x);
        float f7 = ((this.mBeziervertex2.x * this.mBeziervertex1.y) - (this.mBeziervertex2.y * this.mBeziervertex1.x)) / (this.mBeziervertex2.x - this.mBeziervertex1.x);
        this.mTransparent1.y = (float) this.mCornerY;
        this.mTransparent1.x = (((float) this.mCornerY) - f7) / f6;
        this.mTransparent2.x = (float) this.mCornerX;
        this.mTransparent2.y = (f6 * this.mCornerX) + f7;
    }

    private void drawCurrentPageArea(Canvas canvas, Bitmap bitmap, Path path) {
        this.mPath0.reset();
        this.mPath0.moveTo(this.mBezierStart1.x, this.mBezierStart1.y);
        this.mPath0.quadTo(this.mBezierControl1.x, this.mBezierControl1.y, this.mBezierEnd1.x, this.mBezierEnd1.y);
        this.mPath0.lineTo(this.mTouch.x, this.mTouch.y);
        this.mPath0.lineTo(this.mBezierEnd2.x, this.mBezierEnd2.y);
        this.mPath0.quadTo(this.mBezierControl2.x, this.mBezierControl2.y, this.mBezierStart2.x, this.mBezierStart2.y);
        this.mPath0.lineTo(this.mCornerX, this.mCornerY);
        this.mPath0.close();
        canvas.save();
        Path path2 = new Path();
        path2.moveTo(this.mCornerX, this.mCornerY);
        path2.lineTo(this.mBezierStart1.x, this.mBezierStart1.y);
        path2.quadTo(this.mTransparent1.x, this.mTransparent1.y, this.mBeziervertex1.x, this.mBeziervertex1.y);
        path2.lineTo(this.mBeziervertex2.x, this.mBeziervertex2.y);
        path2.quadTo(this.mTransparent2.x, this.mTransparent2.y, this.mBezierStart2.x, this.mBezierStart2.y);
        path2.close();
        canvas.clipPath(path2, Region.Op.XOR);
        if (this.paint == null) {
            this.paint = new Paint();
        }
        this.paint.setAntiAlias(true);
        this.paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.paint);
        this.paint.setColor(-1);
        canvas.drawPath(path, this.paint);
        this.paint.setColor(0);
        canvas.restore();
    }

    private void drawNextPageAreaAndShadow(Canvas canvas, Bitmap bitmap) {
        int i;
        int i2;
        GradientDrawable gradientDrawable;
        this.mPath1.reset();
        this.mPath1.moveTo(this.mBezierStart1.x, this.mBezierStart1.y);
        this.mPath1.lineTo(this.mBeziervertex1.x, this.mBeziervertex1.y);
        this.mPath1.lineTo(this.mBeziervertex2.x, this.mBeziervertex2.y);
        this.mPath1.lineTo(this.mBezierStart2.x, this.mBezierStart2.y);
        this.mPath1.lineTo(this.mCornerX, this.mCornerY);
        this.mPath1.close();
        this.mDegrees = (float) Math.toDegrees(Math.atan2(this.mBezierControl1.x - this.mCornerX, this.mBezierControl2.y - this.mCornerY));
        if (this.mIsRTandLB) {
            i = (int) (this.mBezierStart1.x - 1.0f);
            i2 = (int) (this.mBezierStart1.x + (this.mTouchToCornerDis / 6.0f) + 1.0f);
            gradientDrawable = this.mBackShadowDrawableLR;
        } else {
            i = (int) ((this.mBezierStart1.x - (this.mTouchToCornerDis / 6.0f)) - 1.0f);
            i2 = ((int) this.mBezierStart1.x) + 1;
            gradientDrawable = this.mBackShadowDrawableRL;
        }
        Log.i(TAG, "leftx  " + i + "   rightx  " + i2);
        canvas.save();
        canvas.clipPath(this.mPath0);
        canvas.clipPath(this.mPath1, Region.Op.INTERSECT);
        if (this.paint == null) {
            this.paint = new Paint();
        }
        this.paint.setAntiAlias(true);
        this.paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.paint);
        canvas.rotate(this.mDegrees, this.mBezierStart1.x, this.mBezierStart1.y);
        gradientDrawable.setBounds(i, (int) this.mBezierStart1.y, i2, (int) (this.mMaxLength + this.mBezierStart1.y));
        gradientDrawable.draw(canvas);
        canvas.restore();
    }

    public void setBitmaps(Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3) {
        this.mCurPageBitmap = bitmap;
        this.mCurPageBackBitmap = bitmap2;
        this.mNextPageBitmap = bitmap3;
    }

    public Bitmap getResultBitmap(Bitmap bitmap, int i) {
        this.mWidth = bitmap.getWidth();
        int height = bitmap.getHeight();
        this.mHeight = height;
        this.mMaxLength = (float) Math.hypot(this.mWidth, height);
        this.mBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
        this.mCanvas = new Canvas(this.mBitmap);
        Paint paint = new Paint(4);
        this.mBitmapPaint = paint;
        paint.setAntiAlias(true);
        this.mBitmapPaint.setFilterBitmap(true);
        this.mCurPageBitmap = bitmap;
        this.mNextPageBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
        PointF pointF = new PointF((this.mWidth * 9) / 10, (this.mHeight * 9) / 10);
        this.mTouch = pointF;
        calcCornerXY(pointF.x, this.mTouch.y);
        if (this.mWidth == this.mHeight) {
            this.mTouch = new PointF((this.mWidth * i) / 100.0f, this.mHeight * ((i + 5) / 100.0f));
        } else {
            float f = i;
            this.mTouch = new PointF((this.mWidth * f) / 100.0f, (this.mHeight * f) / 100.0f);
        }
        calcPoints();
        drawCurrentPageArea(this.mCanvas, this.mCurPageBitmap, this.mPath0);
        drawNextPageAreaAndShadow(this.mCanvas, this.mNextPageBitmap);
        Bitmap bitmap2 = this.mNextPageBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.mNextPageBitmap.recycle();
            this.mNextPageBitmap = null;
        }
        drawCurrentPageShadow(this.mCanvas);
        return this.mBitmap;
    }

    public void DrawPageEffect(Canvas canvas, PointF pointF) {
        this.mTouch = pointF;
        calcCornerXY(pointF.x, this.mTouch.y);
        calcPoints();
        drawCurrentPageArea(this.mCanvas, this.mCurPageBitmap, this.mPath0);
        drawNextPageAreaAndShadow(this.mCanvas, this.mNextPageBitmap);
        drawCurrentPageShadow(this.mCanvas);
        Bitmap bitmap = this.mNextPageBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mNextPageBitmap.recycle();
            this.mNextPageBitmap = null;
        }
        Paint paint = new Paint();
        this.paint = paint;
        paint.setAntiAlias(true);
        this.paint.setFilterBitmap(true);
        canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, this.paint);
    }

    private void createDrawable() {
        int[] iArr = {3355443, -1338821837};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, iArr);
        this.mFolderShadowDrawableRL = gradientDrawable;
        gradientDrawable.setGradientType(0);
        GradientDrawable gradientDrawable2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, iArr);
        this.mFolderShadowDrawableLR = gradientDrawable2;
        gradientDrawable2.setGradientType(0);
        this.mBackShadowColors = new int[]{-15658735, 1118481};
        GradientDrawable gradientDrawable3 = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, this.mBackShadowColors);
        this.mBackShadowDrawableRL = gradientDrawable3;
        gradientDrawable3.setGradientType(0);
        GradientDrawable gradientDrawable4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, this.mBackShadowColors);
        this.mBackShadowDrawableLR = gradientDrawable4;
        gradientDrawable4.setGradientType(0);
        this.mFrontShadowColors = new int[]{-2138535800, 8947848};
        GradientDrawable gradientDrawable5 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, this.mFrontShadowColors);
        this.mFrontShadowDrawableVLR = gradientDrawable5;
        gradientDrawable5.setGradientType(0);
        GradientDrawable gradientDrawable6 = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, this.mFrontShadowColors);
        this.mFrontShadowDrawableVRL = gradientDrawable6;
        gradientDrawable6.setGradientType(0);
        GradientDrawable gradientDrawable7 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, this.mFrontShadowColors);
        this.mFrontShadowDrawableHTB = gradientDrawable7;
        gradientDrawable7.setGradientType(0);
        GradientDrawable gradientDrawable8 = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, this.mFrontShadowColors);
        this.mFrontShadowDrawableHBT = gradientDrawable8;
        gradientDrawable8.setGradientType(0);
    }

    public void drawCurrentPageShadow(Canvas canvas) {
        int i;
        int i2;
        GradientDrawable gradientDrawable;
        int i3;
        int i4;
        GradientDrawable gradientDrawable2;
        float f = 10;
        double cos = f / Math.cos(Math.atan2(this.mBezierControl1.y - this.mTouch.y, this.mTouch.x - this.mBezierControl1.x));
        float f2 = (float) (this.mTouch.x - cos);
        float f3 = (float) (this.mTouch.y - cos);
        this.mPath1.reset();
        this.mPath1.moveTo(f2, f3);
        this.mPath1.lineTo(this.mTouch.x, this.mTouch.y);
        this.mPath1.lineTo(this.mBezierControl1.x, this.mBezierControl1.y);
        this.mPath1.lineTo(this.mBezierStart1.x, this.mBezierStart1.y);
        this.mPath1.close();
        canvas.save();
        canvas.clipPath(this.mPath0, Region.Op.XOR);
        canvas.clipPath(this.mPath1, Region.Op.INTERSECT);
        if (this.mIsRTandLB) {
            i = (int) this.mBezierControl1.x;
            i2 = ((int) this.mBezierControl1.x) + 10;
            gradientDrawable = this.mFrontShadowDrawableVLR;
        } else {
            i = (int) (this.mBezierControl1.x - f);
            i2 = (int) this.mBezierControl1.x;
            gradientDrawable = this.mFrontShadowDrawableVRL;
        }
        canvas.rotate((float) Math.toDegrees(Math.atan2(this.mTouch.x - this.mBezierControl1.x, this.mBezierControl1.y - this.mTouch.y)), this.mBezierControl1.x, this.mBezierControl1.y);
        gradientDrawable.setBounds(i, (int) (this.mBezierControl1.y - 500.0f), i2, (int) this.mBezierControl1.y);
        gradientDrawable.draw(canvas);
        canvas.restore();
        this.mPath1.reset();
        this.mPath1.moveTo(f2, f3);
        this.mPath1.lineTo(this.mTouch.x, this.mTouch.y);
        this.mPath1.lineTo(this.mBezierControl2.x, this.mBezierControl2.y);
        this.mPath1.lineTo(this.mBezierStart2.x, this.mBezierStart2.y);
        this.mPath1.close();
        canvas.save();
        canvas.clipPath(this.mPath0, Region.Op.XOR);
        canvas.clipPath(this.mPath1, Region.Op.INTERSECT);
        if (this.mIsRTandLB) {
            i3 = (int) this.mBezierControl2.y;
            i4 = (int) (this.mBezierControl2.y + f);
            gradientDrawable2 = this.mFrontShadowDrawableHTB;
        } else {
            i3 = (int) (this.mBezierControl2.y - f);
            i4 = (int) this.mBezierControl2.y;
            gradientDrawable2 = this.mFrontShadowDrawableHBT;
        }
        canvas.rotate((float) Math.toDegrees(Math.atan2(this.mBezierControl2.y - this.mTouch.y, this.mBezierControl2.x - this.mTouch.x)), this.mBezierControl2.x, this.mBezierControl2.y);
        gradientDrawable2.setBounds((int) (this.mBezierControl2.x - 500.0f), i3, (int) this.mBezierControl2.x, i4);
        gradientDrawable2.draw(canvas);
        canvas.restore();
    }
}

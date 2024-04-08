package com.picspool.lib.sticker.core;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.core.view.ViewCompat;
import com.picspool.lib.bitmap.DMBitmapCrop;
import com.picspool.lib.border.res.DMWBBorderRes;

/* loaded from: classes3.dex */
public class DMSticker {
    static int mapSize = 1250;
    protected int alpha;
    private Bitmap bgBitmap;
    private int bgColor;
    private Bitmap bitmap;
    public int borderColor;
    BlurMaskFilter filter;
    public float height;
    private boolean isFreePuzzleBitmap;
    protected boolean isNoDrag;
    private boolean isShowBorder;
    private boolean isShowShadow;
    private int keyIndex;
    Paint mBorderPaint;
    protected Paint mPaint;
    public float marginBottom;
    public float marginLeft;
    public float marginRight;
    public float marginTop;
    protected int screenHeight;
    protected int screenWidth;
    Bitmap shadowBitmap;
    private int shadowColor;
    Paint shadowPaint;
    private DMWBBorderRes stickerBorderRes;
    public int stickerHeight;
    public int stickerId;
    public int stickerWidth;
    public Matrix transform;
    public float width;

    private static float calcWithRateF(int i, float f) {
        return i * f;
    }

    public DMSticker(int i) {
        this.stickerId = 0;
        this.alpha = 255;
        this.transform = new Matrix();
        this.mPaint = new Paint();
        this.isNoDrag = false;
        this.bgColor = 0;
        this.isFreePuzzleBitmap = false;
        this.isShowBorder = true;
        this.isShowShadow = false;
        this.shadowColor = ViewCompat.MEASURED_STATE_MASK;
        this.mBorderPaint = new Paint();
        this.filter = new BlurMaskFilter(10.0f, BlurMaskFilter.Blur.OUTER);
        this.shadowPaint = new Paint();
        this.mPaint.setDither(true);
        this.mPaint.setFilterBitmap(true);
        this.mPaint.setAntiAlias(true);
        this.screenWidth = i;
        this.screenHeight = i;
    }

    public DMSticker(int i, int i2, int i3) {
        this.stickerId = 0;
        this.alpha = 255;
        this.transform = new Matrix();
        this.mPaint = new Paint();
        this.isNoDrag = false;
        this.bgColor = 0;
        this.isFreePuzzleBitmap = false;
        this.isShowBorder = true;
        this.isShowShadow = false;
        this.shadowColor = ViewCompat.MEASURED_STATE_MASK;
        this.mBorderPaint = new Paint();
        this.filter = new BlurMaskFilter(10.0f, BlurMaskFilter.Blur.OUTER);
        this.shadowPaint = new Paint();
        this.mPaint.setDither(true);
        this.mPaint.setFilterBitmap(true);
        this.mPaint.setAntiAlias(true);
        this.screenWidth = i;
        this.stickerWidth = i2;
        this.stickerHeight = i3;
    }

    public Bitmap getStickerIcon(int i, int i2) {
        Bitmap bitmap = getBitmap();
        if (bitmap != null) {
            Bitmap cropCenterScaleBitmap = DMBitmapCrop.cropCenterScaleBitmap(bitmap, i, i2);
            return bitmap == cropCenterScaleBitmap ? bitmap.copy(Bitmap.Config.ARGB_8888, true) : cropCenterScaleBitmap;
        }
        return null;
    }

    public boolean getIsFreePuzzleBitmap() {
        return this.isFreePuzzleBitmap;
    }

    public void setIsFreePuzzleBitmap(boolean z) {
        this.isFreePuzzleBitmap = z;
    }

    public void setIsShowBorder(boolean z) {
        this.isShowBorder = z;
    }

    public boolean getIsShowBorder() {
        return this.isShowBorder;
    }

    public void setIsShowShadow(boolean z) {
        Bitmap bitmap = getBitmap();
        this.isShowShadow = z;
        if (z) {
            return;
        }
        Bitmap bitmap2 = this.shadowBitmap;
        if (bitmap2 != null && bitmap2 != bitmap && !bitmap2.isRecycled()) {
            this.shadowBitmap.recycle();
        }
        this.shadowBitmap = null;
    }

    public void setIsShowShadow(boolean z, int i) {
        Bitmap bitmap = getBitmap();
        this.isShowShadow = z;
        if (!z || (z && this.shadowColor != i)) {
            Bitmap bitmap2 = this.shadowBitmap;
            if (bitmap2 != null && bitmap2 != bitmap && !bitmap2.isRecycled()) {
                this.shadowBitmap.recycle();
            }
            this.shadowBitmap = null;
        }
        this.shadowColor = i;
    }

    public boolean getIsShowShadow() {
        return this.isShowShadow;
    }

    public int getKeyIndex() {
        return this.keyIndex;
    }

    public DMSticker(int i, int i2, boolean z, int i3) {
        this.stickerId = 0;
        this.alpha = 255;
        this.transform = new Matrix();
        this.mPaint = new Paint();
        this.isNoDrag = false;
        this.bgColor = 0;
        this.isFreePuzzleBitmap = false;
        this.isShowBorder = true;
        this.isShowShadow = false;
        this.shadowColor = ViewCompat.MEASURED_STATE_MASK;
        this.mBorderPaint = new Paint();
        this.filter = new BlurMaskFilter(10.0f, BlurMaskFilter.Blur.OUTER);
        this.shadowPaint = new Paint();
        this.mPaint.setDither(true);
        this.mPaint.setFilterBitmap(true);
        this.mPaint.setAntiAlias(true);
        this.screenWidth = i;
        this.screenHeight = i2;
        this.isFreePuzzleBitmap = z;
        this.isShowBorder = true;
        this.keyIndex = i3;
    }

    public DMSticker(boolean z, int i) {
        this.stickerId = 0;
        this.alpha = 255;
        this.transform = new Matrix();
        this.mPaint = new Paint();
        this.isNoDrag = false;
        this.bgColor = 0;
        this.isFreePuzzleBitmap = false;
        this.isShowBorder = true;
        this.isShowShadow = false;
        this.shadowColor = ViewCompat.MEASURED_STATE_MASK;
        this.mBorderPaint = new Paint();
        this.filter = new BlurMaskFilter(10.0f, BlurMaskFilter.Blur.OUTER);
        this.shadowPaint = new Paint();
        this.isFreePuzzleBitmap = z;
        this.isShowBorder = true;
        this.keyIndex = i;
    }

    public void setBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        if (this.isFreePuzzleBitmap) {
            Bitmap bitmap2 = this.shadowBitmap;
            if (bitmap2 != null && bitmap2 != this.bitmap && !bitmap2.isRecycled()) {
                this.shadowBitmap.recycle();
                this.shadowBitmap = null;
            }
            this.bitmap = bitmap;
        } else if (bitmap.getWidth() < this.screenWidth && bitmap.getHeight() < this.screenHeight) {
            this.bitmap = bitmap;
        } else {
            float width = bitmap.getWidth() / this.screenWidth;
            float height = bitmap.getHeight() / this.screenHeight;
            if (width <= height) {
                width = height;
            }
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() / width), (int) (bitmap.getHeight() / width), false);
            if (bitmap != createScaledBitmap) {
                bitmap.recycle();
            }
            this.bitmap = createScaledBitmap;
        }
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public int getWidth() {
        Bitmap bitmap = getBitmap();
        return this.isNoDrag ? this.stickerWidth : bitmap != null ? bitmap.getWidth() : 0;
    }

    public int getHeight() {
        Bitmap bitmap = getBitmap();
        return this.isNoDrag ? this.stickerHeight : bitmap != null ? bitmap.getHeight() : 0;
    }

    public void drawInCanvas(Canvas canvas) {
        Bitmap bitmap = getBitmap();
        if (!this.isFreePuzzleBitmap) {
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
            Bitmap bitmap2 = this.bgBitmap;
            if (bitmap2 != null && !bitmap2.isRecycled()) {
                Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                Canvas canvas2 = new Canvas(createBitmap);
                canvas2.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                canvas2.drawPaint(paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas2.drawBitmap(bitmap, rect, rect, paint);
                if (this.bgBitmap != null) {
                    try {
                        paint.setShader(new BitmapShader(this.bgBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
                        canvas2.translate(0.0f, 0.0f);
                        canvas2.drawRect(new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight()), paint);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                canvas.drawBitmap(createBitmap, this.transform, this.mPaint);
                return;
            } else if (this.bgColor != 0) {
                Bitmap createBitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                try {
                    Rect rect2 = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                    Canvas canvas3 = new Canvas(createBitmap2);
                    canvas3.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
                    Paint paint2 = new Paint();
                    paint2.setAntiAlias(true);
                    canvas3.drawPaint(paint2);
                    paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                    canvas3.drawBitmap(bitmap, rect2, rect2, paint2);
                    paint2.setColor(this.bgColor);
                    canvas3.translate(0.0f, 0.0f);
                    canvas3.drawRect(new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight()), paint2);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                canvas.drawBitmap(createBitmap2, this.transform, this.mPaint);
                return;
            } else if (bitmap == null || bitmap.isRecycled()) {
                return;
            } else {
                canvas.drawBitmap(bitmap, this.transform, this.mPaint);
                return;
            }
        }
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.lineTo(this.width, 0.0f);
        path.lineTo(this.width, this.height);
        path.lineTo(0.0f, this.height);
        path.close();
        path.transform(this.transform);
        this.mBorderPaint.setAntiAlias(true);
        this.mBorderPaint.setFilterBitmap(true);
        float f = 0;
        float f2 = this.width;
        float f3 = this.height;
        float f4 = 0;
        float[] fArr = {f, 0.0f, f2, f, f2, f3 + f4, 0.0f, f3 + f4};
        this.transform.mapPoints(fArr);
        synchronized (bitmap) {
            if (bitmap != null) {
                if (!bitmap.isRecycled()) {
                    if (!this.isShowShadow) {
                        if (this.shadowBitmap != null && this.shadowBitmap != bitmap && !this.shadowBitmap.isRecycled()) {
                            this.shadowBitmap.recycle();
                            this.shadowBitmap = null;
                        }
                        if (this.stickerBorderRes != null) {
                            canvas.drawBitmap(bitmap, this.transform, this.mPaint);
                            processBorder(bitmap.getWidth(), bitmap.getHeight(), this.stickerBorderRes, canvas, this.transform);
                        } else {
                            this.mBorderPaint.setStyle(Paint.Style.STROKE);
                            this.mBorderPaint.setStrokeWidth((this.marginLeft / 2.0f) + 1.0f);
                            this.mBorderPaint.setColor(this.borderColor);
                            if (this.isShowBorder) {
                                canvas.drawPath(path, this.mBorderPaint);
                            }
                            canvas.drawBitmap(bitmap, this.transform, this.mPaint);
                            if (this.isShowBorder) {
                                this.mBorderPaint.setStyle(Paint.Style.STROKE);
                                this.mBorderPaint.setStrokeWidth((this.marginLeft / 2.0f) + 1.0f);
                                canvas.drawLine(fArr[0], fArr[1], fArr[2], fArr[3], this.mBorderPaint);
                                canvas.drawLine(fArr[2], fArr[3], fArr[4], fArr[5], this.mBorderPaint);
                                canvas.drawLine(fArr[0], fArr[1], fArr[6], fArr[7], this.mBorderPaint);
                                canvas.drawLine(fArr[6], fArr[7], fArr[4], fArr[5], this.mBorderPaint);
                            }
                        }
                    } else {
                        if (this.shadowBitmap == null) {
                            this.shadowBitmap = creatBlurMaskBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), this.filter);
                        }
                        canvas.drawBitmap(this.shadowBitmap, this.transform, this.mPaint);
                    }
                }
            }
        }
    }

    protected static void processBorder(int i, int i2, DMWBBorderRes dMWBBorderRes, Canvas canvas, Matrix matrix) {
        float borderRate = 1.0f / getBorderRate(i, i2, dMWBBorderRes);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 2));
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFlags(1);
        try {
            Bitmap leftTopCornorBitmap = dMWBBorderRes.getLeftTopCornorBitmap();
            float calcWithRateF = calcWithRateF(leftTopCornorBitmap.getWidth(), borderRate);
            float calcWithRateF2 = calcWithRateF(leftTopCornorBitmap.getHeight(), borderRate);
            Matrix matrix2 = new Matrix(matrix);
            matrix2.preTranslate(((-calcWithRateF) / 10.0f) * 4.0f, ((-calcWithRateF2) / 10.0f) * 4.0f);
            matrix2.preScale(borderRate, borderRate);
            canvas.drawBitmap(leftTopCornorBitmap, matrix2, paint);
            leftTopCornorBitmap.recycle();
            Bitmap leftBottomCornorBitmap = dMWBBorderRes.getLeftBottomCornorBitmap();
            float calcWithRateF3 = calcWithRateF(leftBottomCornorBitmap.getWidth(), borderRate);
            float calcWithRateF4 = calcWithRateF(leftBottomCornorBitmap.getHeight(), borderRate);
            Matrix matrix3 = new Matrix(matrix);
            float f = i2;
            float f2 = (calcWithRateF4 / 10.0f) * 6.0f;
            matrix3.preTranslate(((-calcWithRateF3) / 10.0f) * 4.0f, f - f2);
            matrix3.preScale(borderRate, borderRate);
            canvas.drawBitmap(leftBottomCornorBitmap, matrix3, null);
            leftBottomCornorBitmap.recycle();
            Bitmap rightTopCornorBitmap = dMWBBorderRes.getRightTopCornorBitmap();
            float calcWithRateF5 = calcWithRateF(rightTopCornorBitmap.getWidth(), borderRate);
            float calcWithRateF6 = calcWithRateF(rightTopCornorBitmap.getHeight(), borderRate);
            Matrix matrix4 = new Matrix(matrix);
            float f3 = i;
            float f4 = (calcWithRateF5 / 10.0f) * 6.0f;
            matrix4.preTranslate(f3 - f4, ((-calcWithRateF6) / 10.0f) * 4.0f);
            matrix4.preScale(borderRate, borderRate);
            canvas.drawBitmap(rightTopCornorBitmap, matrix4, null);
            rightTopCornorBitmap.recycle();
            Bitmap rightBottomCornorBitmap = dMWBBorderRes.getRightBottomCornorBitmap();
            float calcWithRateF7 = calcWithRateF(rightBottomCornorBitmap.getWidth(), borderRate);
            float calcWithRateF8 = calcWithRateF(rightBottomCornorBitmap.getHeight(), borderRate);
            Matrix matrix5 = new Matrix(matrix);
            float f5 = (calcWithRateF7 / 10.0f) * 6.0f;
            float f6 = (calcWithRateF8 / 10.0f) * 6.0f;
            matrix5.preTranslate(f3 - f5, f - f6);
            matrix5.preScale(borderRate, borderRate);
            canvas.drawBitmap(rightBottomCornorBitmap, matrix5, null);
            rightBottomCornorBitmap.recycle();
            Bitmap leftBitmap = dMWBBorderRes.getLeftBitmap();
            float calcWithRateF9 = calcWithRateF(leftBitmap.getWidth(), borderRate);
            float calcWithRateF10 = calcWithRateF(leftBitmap.getHeight(), borderRate);
            float f7 = (calcWithRateF2 / 10.0f) * 6.0f;
            int i3 = (int) ((f - f7) - f2);
            if (i3 > 0) {
                Bitmap TileBitmapVertical = TileBitmapVertical(leftBitmap, i3, (int) calcWithRateF9, (int) calcWithRateF10);
                if (TileBitmapVertical != leftBitmap) {
                    leftBitmap.recycle();
                }
                Matrix matrix6 = new Matrix(matrix);
                matrix6.preTranslate((-calcWithRateF9) / 2.0f, f7);
                canvas.drawBitmap(TileBitmapVertical, matrix6, null);
                TileBitmapVertical.recycle();
            }
            Bitmap topBitmap = dMWBBorderRes.getTopBitmap();
            float calcWithRateF11 = calcWithRateF(topBitmap.getHeight(), borderRate);
            float calcWithRateF12 = calcWithRateF(topBitmap.getWidth(), borderRate);
            float f8 = (calcWithRateF / 10.0f) * 6.0f;
            int i4 = (int) ((f3 - f8) - f4);
            if (i4 > 0) {
                Bitmap TileBitmapHorizon = TileBitmapHorizon(topBitmap, i4, (int) calcWithRateF12, (int) calcWithRateF11);
                if (TileBitmapHorizon != topBitmap) {
                    topBitmap.recycle();
                }
                Matrix matrix7 = new Matrix(matrix);
                matrix7.preTranslate(f8 + 0.0f, (0.0f - (calcWithRateF11 / 2.0f)) + 0.5f);
                canvas.drawBitmap(TileBitmapHorizon, matrix7, null);
                TileBitmapHorizon.recycle();
            }
            Bitmap rightBitmap = dMWBBorderRes.getRightBitmap();
            float calcWithRateF13 = calcWithRateF(rightBitmap.getWidth(), borderRate);
            float calcWithRateF14 = calcWithRateF(rightBitmap.getHeight(), borderRate);
            float f9 = (calcWithRateF6 / 10.0f) * 6.0f;
            int i5 = (int) ((f - f9) - f6);
            if (i5 > 0) {
                Bitmap TileBitmapVertical2 = TileBitmapVertical(rightBitmap, i5, (int) calcWithRateF13, (int) calcWithRateF14);
                if (TileBitmapVertical2 != rightBitmap) {
                    rightBitmap.recycle();
                }
                Matrix matrix8 = new Matrix(matrix);
                matrix8.preTranslate(f3 - (calcWithRateF13 / 2.0f), f9 + 0.0f);
                canvas.drawBitmap(TileBitmapVertical2, matrix8, null);
                TileBitmapVertical2.recycle();
            }
            Bitmap bottomBitmap = dMWBBorderRes.getBottomBitmap();
            float calcWithRateF15 = calcWithRateF(bottomBitmap.getWidth(), borderRate);
            float calcWithRateF16 = calcWithRateF(bottomBitmap.getHeight(), borderRate);
            float f10 = (calcWithRateF3 / 10.0f) * 6.0f;
            int i6 = (int) ((f3 - f10) - f5);
            if (i6 > 0) {
                Bitmap TileBitmapHorizon2 = TileBitmapHorizon(bottomBitmap, i6, (int) calcWithRateF15, (int) calcWithRateF16);
                if (TileBitmapHorizon2 != bottomBitmap) {
                    bottomBitmap.recycle();
                }
                Matrix matrix9 = new Matrix(matrix);
                matrix9.preTranslate(f10 + 0.0f, f - (calcWithRateF16 / 2.0f));
                canvas.drawBitmap(TileBitmapHorizon2, matrix9, null);
                TileBitmapHorizon2.recycle();
            }
        } catch (Exception unused) {
        }
    }

    protected static float getBorderRate(int i, int i2, DMWBBorderRes dMWBBorderRes) {
        float innerPx = ((mapSize - dMWBBorderRes.getInnerPx()) - dMWBBorderRes.getInnerPx2()) / i;
        float innerPy = ((mapSize - dMWBBorderRes.getInnerPy()) - dMWBBorderRes.getInnerPy2()) / i2;
        return innerPx < innerPy ? innerPx : innerPy;
    }

    private static int calcWithRate(int i, float f) {
        float f2 = i * f;
        int i2 = (int) f2;
        return Math.abs(f2 - ((float) i2)) >= 0.5f ? i2 + 1 : i2;
    }

    private static Bitmap TileBitmapVertical(Bitmap bitmap, int i, int i2, int i3) {
        int i4;
        float f;
        int i5 = i / i3;
        if (i % i3 != 0) {
            float f2 = i3;
            float f3 = i5;
            float f4 = f2 * (((i * 1.0f) / f2) - f3);
            f = f4 / f3;
            i4 = (i5 * i3) + ((int) f4) + 2;
        } else {
            i4 = i5 * i3;
            f = 0.0f;
        }
        if (i4 > 0) {
            i = i4;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i2, i, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 2));
        new Rect(0, 0, i2, i3);
        Rect rect = new Rect(0, 0, i2, i);
        if (i5 == 0) {
            canvas.drawBitmap(bitmap, (Rect) null, rect, (Paint) null);
        } else {
            float f5 = i3 + f;
            RectF rectF = new RectF(0.0f, 0.0f, i2, f5);
            for (int i6 = 0; i6 <= i5; i6++) {
                canvas.drawBitmap(bitmap, (Rect) null, rectF, (Paint) null);
                rectF.top += f5;
                rectF.bottom += f5;
            }
        }
        return createBitmap;
    }

    private static Bitmap TileBitmapHorizon(Bitmap bitmap, int i, int i2, int i3) {
        int i4;
        float f;
        int i5 = i / i2;
        if (i % i2 != 0) {
            float f2 = i2;
            float f3 = i5;
            float f4 = f2 * (((i * 1.0f) / f2) - f3);
            f = f4 / f3;
            i4 = (i5 * i2) + ((int) f4) + 2;
        } else {
            i4 = i5 * i2;
            f = 0.0f;
        }
        if (i4 > 0) {
            i = i4;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i3, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 2));
        new Rect(0, 0, i2, i3);
        Rect rect = new Rect(0, 0, i, i3);
        if (i5 == 0) {
            canvas.drawBitmap(bitmap, (Rect) null, rect, (Paint) null);
        } else {
            float f5 = i2 + f;
            RectF rectF = new RectF(0.0f, 0.0f, f5, i3);
            for (int i6 = 0; i6 <= i5; i6++) {
                canvas.drawBitmap(bitmap, (Rect) null, rectF, (Paint) null);
                rectF.left += f5;
                rectF.right += f5;
            }
        }
        return createBitmap;
    }

    public Bitmap creatBlurMaskBitmap(Bitmap bitmap, int i, int i2, BlurMaskFilter blurMaskFilter) {
        Bitmap bitmap2;
        if (getWidth() < 1 || getHeight() < 1) {
            return bitmap;
        }
        int i3 = (int) 10.5f;
        int i4 = i3 * 2;
        Bitmap createBitmap = Bitmap.createBitmap(i + i4, i4 + i2, Bitmap.Config.ARGB_8888);
        this.shadowPaint.setAntiAlias(true);
        this.shadowPaint.setFilterBitmap(true);
        this.shadowPaint.setMaskFilter(blurMaskFilter);
        this.shadowPaint.setColor(this.shadowColor);
        Canvas canvas = new Canvas(createBitmap);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setFilterBitmap(true);
        Bitmap extractAlpha = bitmap.extractAlpha(this.shadowPaint, new int[]{bitmap.getWidth() / 2, bitmap.getHeight() / 2});
        if (bitmap == extractAlpha || bitmap == bitmap || bitmap == null || bitmap.isRecycled()) {
            bitmap2 = bitmap;
        } else {
            bitmap.recycle();
            bitmap2 = null;
        }
        this.shadowPaint.setMaskFilter(null);
        float f = i3 - 1;
        canvas.drawBitmap(bitmap, (Rect) null, new RectF(f, f, i + i3, i2 + i3), this.mPaint);
        canvas.drawBitmap(extractAlpha, 0.0f, 0.0f, this.shadowPaint);
        if (extractAlpha != bitmap) {
            extractAlpha.recycle();
        }
        if (bitmap2 != bitmap && bitmap2 != null && !bitmap2.isRecycled()) {
            bitmap2.recycle();
        }
        return createBitmap;
    }

    public void setAlpha(int i) {
        this.alpha = i;
        this.mPaint.setAlpha(i);
    }

    public int getAlpha() {
        return this.alpha;
    }

    public void setColor(int i) {
        try {
            if (this.bgBitmap != null && !this.bgBitmap.isRecycled()) {
                this.bgBitmap.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.bgBitmap = null;
        this.bgColor = i;
    }

    public int getColor() {
        return this.bgColor;
    }

    public void setBgImage(Bitmap bitmap) {
        this.bgColor = 0;
        this.bgBitmap = bitmap;
    }

    public boolean getisNoDrag() {
        return this.isNoDrag;
    }

    public void dispos() {
        Bitmap bitmap = this.shadowBitmap;
        if (bitmap == null || bitmap == this.bitmap || bitmap.isRecycled()) {
            return;
        }
        this.shadowBitmap.recycle();
        this.shadowBitmap = null;
    }

    public void addWBBorderRes(DMWBBorderRes dMWBBorderRes) {
        this.stickerBorderRes = dMWBBorderRes;
    }
}

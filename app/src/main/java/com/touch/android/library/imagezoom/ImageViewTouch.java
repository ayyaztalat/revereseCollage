package com.touch.android.library.imagezoom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewConfiguration;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.picspool.lib.border.CSBorderReturns;
import com.picspool.lib.border.CSTBorderProcess;
import com.picspool.lib.border.CSTBorderRes;
import com.picspool.lib.collagelib.DMColorFilterGenerator;
import com.picspool.lib.filter.cpu.normal.FastBlurFilter;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;
import com.picspool.lib.bitmap.DMBitmapUtil;
import java.io.PrintStream;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;

/* loaded from: classes3.dex */
public class ImageViewTouch extends ImageViewTouchBase {
    static final float SCROLL_DELTA_THRESHOLD = 1.0f;
    private static final String TAG = "ImageViewTouch";
    Bitmap blurBgTmpBitmap;
    Paint blurBgpPaint;
    private Handler clickHandler;
    Paint cornerPaint;

    /* renamed from: f */
    boolean f2001f;
    BlurMaskFilter filter;
    boolean isBlurOverlay;
    boolean isOverlapping;
    boolean isOverlay;
    boolean isShowShadow;
    protected boolean isTouched;
    private boolean keyUpDown;
    protected boolean lockTouch;
    private Bitmap mBitmap;
    float mBlurBgHue;
    Shader mBlurBgShader;
    private Paint mBorderPaint;
    private DMWBRes mBorderRes;
    public OnCustomeClickListener mClickListener;
    private Context mContext;
    protected int mDoubleTapDirection;
    protected boolean mDoubleTapEnabled;
    private float mFoldEffectIntensity;
    protected GestureDetector mGestureDetector;
    int mLeakAlpha;
    private Bitmap mLeakBitmap;
    int mLeakHue;
    Paint mLeakPaint;
    private int mMosaicIntensity;
    private int mPageEffectIntensity;
    Paint mPaint;
    PaintFlagsDrawFilter mPaintFlagsDrawFilter;
    private float mRotationDegree;
    private boolean mRotationEnabled;
    private float mRotationScale;
    private float mScale;
    protected ScaleGestureDetector mScaleDetector;
    protected boolean mScaleEnabled;
    protected float mScaleFactor;
    protected boolean mScrollEnabled;
    protected int mTouchSlop;
    private int mTranslateX;
    private int mTranslateY;
    private int mirrorH_Times;
    private int mirrorV_Times;
    private float newRotation;
    PorterDuffXfermode pDuffXfermode;
    int padding;
    private int radius;
    Bitmap realphoto;
    Paint shadowPaint;
    private int timer;
    private TextView txt_rotation;
    PorterDuffXfermode xfermode;

    /* loaded from: classes3.dex */
    public interface OnCustomeClickListener {
        void CustomeClick(int i);
    }

    public ImageViewTouch(Context context) {
        super(context);
        this.mDoubleTapEnabled = true;
        this.mScaleEnabled = true;
        this.mScrollEnabled = true;
        this.mRotationEnabled = true;
        this.radius = 30;
        this.mScale = 1.0f;
        this.lockTouch = false;
        this.isTouched = false;
        this.mirrorH_Times = 0;
        this.mirrorV_Times = 0;
        this.newRotation = 0.0f;
        this.keyUpDown = false;
        this.timer = 0;
        this.clickHandler = new Handler() { // from class: touch.android.library.imagezoom.ImageViewTouch.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 0) {
                    ImageViewTouch.this.keyUpDown = true;
                    if (ImageViewTouch.this.mClickListener != null) {
                        ImageViewTouch.this.mClickListener.CustomeClick(0);
                        return;
                    }
                    return;
                }
                int i = message.what;
            }
        };
        this.f2001f = false;
        this.mPageEffectIntensity = 0;
        this.mMosaicIntensity = 0;
        this.mFoldEffectIntensity = 0.0f;
        this.mLeakPaint = new Paint();
        this.mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, 3);
        this.mLeakAlpha = 255;
        this.mLeakHue = 0;
        this.mPaint = new Paint();
        this.padding = 0;
        this.filter = new BlurMaskFilter(15.0f, BlurMaskFilter.Blur.OUTER);
        this.xfermode = new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY);
        this.mBorderPaint = new Paint();
        this.blurBgTmpBitmap = null;
        this.blurBgpPaint = null;
        this.mBlurBgHue = 0.0f;
        this.mBlurBgShader = null;
        this.mRotationDegree = 3.0f;
        this.mRotationScale = 1.0f;
        this.isShowShadow = false;
        this.isOverlay = false;
        this.isBlurOverlay = false;
        this.isOverlapping = false;
        this.shadowPaint = new Paint();
        this.cornerPaint = new Paint();
        this.pDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        this.mContext = context;
    }

    public ImageViewTouch(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        this.mContext = context;
    }

    public ImageViewTouch(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDoubleTapEnabled = true;
        this.mScaleEnabled = true;
        this.mScrollEnabled = true;
        this.mRotationEnabled = true;
        this.radius = 30;
        this.mScale = 1.0f;
        this.lockTouch = false;
        this.isTouched = false;
        this.mirrorH_Times = 0;
        this.mirrorV_Times = 0;
        this.newRotation = 0.0f;
        this.keyUpDown = false;
        this.timer = 0;
        this.clickHandler = new Handler() { // from class: touch.android.library.imagezoom.ImageViewTouch.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 0) {
                    ImageViewTouch.this.keyUpDown = true;
                    if (ImageViewTouch.this.mClickListener != null) {
                        ImageViewTouch.this.mClickListener.CustomeClick(0);
                        return;
                    }
                    return;
                }
                int i2 = message.what;
            }
        };
        this.f2001f = false;
        this.mPageEffectIntensity = 0;
        this.mMosaicIntensity = 0;
        this.mFoldEffectIntensity = 0.0f;
        this.mLeakPaint = new Paint();
        this.mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, 3);
        this.mLeakAlpha = 255;
        this.mLeakHue = 0;
        this.mPaint = new Paint();
        this.padding = 0;
        this.filter = new BlurMaskFilter(15.0f, BlurMaskFilter.Blur.OUTER);
        this.xfermode = new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY);
        this.mBorderPaint = new Paint();
        this.blurBgTmpBitmap = null;
        this.blurBgpPaint = null;
        this.mBlurBgHue = 0.0f;
        this.mBlurBgShader = null;
        this.mRotationDegree = 3.0f;
        this.mRotationScale = 1.0f;
        this.isShowShadow = false;
        this.isOverlay = false;
        this.isBlurOverlay = false;
        this.isOverlapping = false;
        this.shadowPaint = new Paint();
        this.cornerPaint = new Paint();
        this.pDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // touch.android.library.imagezoom.ImageViewTouchBase
    public void init(Context context, AttributeSet attributeSet, int i) {
        super.init(context, attributeSet, i);
        this.mContext = context;
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.mDoubleTapDirection = 1;
        if (this.txt_rotation == null) {
            this.txt_rotation = new TextView(context);
        }
    }

    public void setScaleEnabled(boolean z) {
        this.mScaleEnabled = z;
    }

    public boolean GetScaleEnable() {
        return this.mScaleEnabled;
    }

    public int getRadius() {
        return this.radius;
    }

    public void changeRotation(float f) {
        postRotation(f);
        invalidate();
    }

    public void changeScale(float f) {
        postScale(f);
        invalidate();
    }

    public boolean getDoubleTapEnabled() {
        return this.mDoubleTapEnabled;
    }

    public void setSquareMode() {
        resetMatrix();
        postScale(1.0f / getScale());
        if (this.mirrorH_Times == 1) {
            reversal(180.0f, false);
        }
        if (this.mirrorV_Times == 1) {
            reversal(0.0f, false);
        }
    }

    public void setFillMode() {
        resetMatrix();
        float f = this.mThisWidth;
        float f2 = this.mThisHeight;
        Drawable drawable = getDrawable();
        float intrinsicWidth = drawable.getIntrinsicWidth();
        float intrinsicHeight = drawable.getIntrinsicHeight();
        postScale(intrinsicHeight > intrinsicWidth ? f / (intrinsicWidth / (intrinsicHeight / f2)) : f2 / (intrinsicHeight / (intrinsicWidth / f)));
        if (this.mirrorH_Times == 1) {
            reversal(180.0f, false);
        }
        if (this.mirrorV_Times == 1) {
            reversal(0.0f, false);
        }
    }

    private void reversal(float f, boolean z) {
        this.mSuppMatrix.postScale(1.0f, -1.0f, getWidth() / 2, getHeight() / 2);
        this.mSuppMatrix.postRotate(f, getWidth() / 2, getHeight() / 2);
        setImageMatrix(getImageViewMatrix());
        if (z) {
            if (f == 180.0f) {
                int i = this.mirrorH_Times + 1;
                this.mirrorH_Times = i;
                this.mirrorH_Times = i % 2;
            }
            if (f == 0.0f) {
                int i2 = this.mirrorV_Times + 1;
                this.mirrorV_Times = i2;
                this.mirrorV_Times = i2 % 2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // touch.android.library.imagezoom.ImageViewTouchBase
    public void _setImageDrawable(Drawable drawable, Matrix matrix, float f, float f2) {
        super._setImageDrawable(drawable, matrix, f, f2);
        this.mScaleFactor = getMaxScale() / 3.0f;
    }

    public void setLockTouch(boolean z) {
        this.lockTouch = z;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.lockTouch) {
            return false;
        }
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            this.isTouched = true;
            this.clickHandler.sendEmptyMessage(0);
        } else if (action == 1) {
            this.isTouched = false;
            this.clickHandler.sendEmptyMessage(1);
        }
        try {
            int action2 = motionEvent.getAction() & 255;
            if (action2 == 0) {
                this.isTouched = true;
                this.mode = 1;
                this.mStart.set(motionEvent.getX(), motionEvent.getY());
                Log.v("ImageViewTouchBase", "ACTION_DOWN mode == DRAG");
            } else if (action2 != 1) {
                if (action2 == 2) {
                    this.isTouched = true;
                    if (this.mode == 1) {
                        postTranslate(motionEvent.getX() - this.mStart.x, motionEvent.getY() - this.mStart.y);
                        this.mStart.set(motionEvent.getX(), motionEvent.getY());
                        Log.v("ImageViewTouchBase", "drag + event x = " + String.valueOf(motionEvent.getX()) + " event y = " + String.valueOf(motionEvent.getY()));
                    }
                    if (this.mode == 2) {
                        this.mode = 1;
                        this.mStart.set(motionEvent.getX(), motionEvent.getY());
                        Log.v("ImageViewTouchBase", "jump + event x = " + String.valueOf(motionEvent.getX()) + " event y = " + String.valueOf(motionEvent.getY()));
                    }
                    if (this.mode == 3) {
                        if (this.mScaleEnabled) {
                            float spacing = spacing(motionEvent);
                            if (this.oldDist != 0.0f) {
                                float f = spacing / this.oldDist;
                                if (f >= 0.2f) {
                                    postScale(f);
                                }
                            }
                            this.oldDist = spacing;
                        }
                        if (this.mRotationEnabled) {
                            float rotation = rotation(motionEvent);
                            postRotation(rotation - this.oldDegree);
                            this.oldDegree = rotation;
                        }
                    }
                } else if (action2 != 5) {
                    if (action2 == 6) {
                        this.isTouched = false;
                        this.mode = 2;
                        Log.v("ImageViewTouchBase", "ACTION_POINTER_UP mode == JUMP");
                    }
                }
                this.isTouched = true;
                int actionIndex = motionEvent.getActionIndex();
                if (actionIndex >= 1) {
                    this.oldDist = spacing(motionEvent);
                    this.oldDegree = rotation(motionEvent);
                    this.mode = 3;
                    midPoint(this.mMid, motionEvent);
                }
                Log.v("ImageViewTouchBase", "ACTION_POINTER_DOWN mode == ZOOM idx = " + String.valueOf(actionIndex));
            } else {
                this.isTouched = false;
            }
        } catch (Exception e) {
            PrintStream printStream = System.out;
            printStream.println("error:" + e.getMessage());
        }
        invalidate();
        return true;
    }

    public void setShowGridLine(boolean z) {
        this.isTouched = z;
        invalidate();
    }

    public void setTop() {
        postTranslate(0.0f, -getBitmapRect().top);
    }

    public void setBottom() {
        postTranslate(0.0f, getHeight() - getBitmapRect().bottom);
    }

    public void setLeft() {
        postTranslate(-getBitmapRect().left, 0.0f);
    }

    public void setRight() {
        postTranslate(getWidth() - getBitmapRect().right, 0.0f);
    }

    public void setCenter() {
        RectF bitmapRect = getBitmapRect();
        postTranslate(((-bitmapRect.left) + (getWidth() - bitmapRect.right)) / 2.0f, ((-bitmapRect.top) + (getHeight() - bitmapRect.bottom)) / 2.0f);
    }

    private float spacing(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((x * x) + (y * y));
    }

    private void midPoint(PointF pointF, MotionEvent motionEvent) {
        pointF.set((motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f, (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f);
    }

    private float rotation(MotionEvent motionEvent) {
        return (float) Math.toDegrees(Math.atan2(motionEvent.getY(0) - motionEvent.getY(1), motionEvent.getX(0) - motionEvent.getX(1)));
    }

    public void SetRotationEnable(boolean z) {
        this.mRotationEnabled = z;
    }

    public boolean GetRotationEnable() {
        return this.mRotationEnabled;
    }

    public void Zoom(float f) {
        postScale(f);
    }

    public void setPageEffectIntensity(int i) {
        Bitmap bitmap;
        this.mPageEffectIntensity = i;
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled() && (bitmap = this.mBitmap) != this.realphoto) {
            bitmap.recycle();
            this.mBitmap = null;
        }
        invalidate();
    }

    public void setMosaicIntensity(int i) {
        Bitmap bitmap;
        this.mMosaicIntensity = i;
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled() && (bitmap = this.mBitmap) != this.realphoto) {
            bitmap.recycle();
            this.mBitmap = null;
        }
        invalidate();
    }

    public void resetBitmap() {
        Bitmap bitmap;
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 == null || bitmap2.isRecycled() || (bitmap = this.mBitmap) == this.realphoto) {
            return;
        }
        bitmap.recycle();
        this.mBitmap = null;
    }

    public void setFoldEffectIntensity(float f) {
        Bitmap bitmap;
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled() && (bitmap = this.mBitmap) != this.realphoto) {
            bitmap.recycle();
            this.mBitmap = null;
        }
        this.mFoldEffectIntensity = (f * 0.65f) + 0.3f;
        invalidate();
    }

    public void setLeakImageBitmap(Bitmap bitmap) {
        Bitmap bitmap2;
        Bitmap bitmap3 = this.mLeakBitmap;
        if (bitmap3 != bitmap && bitmap3 != null && !bitmap3.isRecycled()) {
            this.mLeakBitmap.recycle();
            this.mLeakBitmap = null;
        }
        this.mLeakBitmap = bitmap;
        Bitmap bitmap4 = this.mBitmap;
        if (bitmap4 != null && !bitmap4.isRecycled() && (bitmap2 = this.mBitmap) != this.realphoto) {
            bitmap2.recycle();
            this.mBitmap = null;
        }
        invalidate();
    }

    public void SetLeakAlpha(int i) {
        Bitmap bitmap;
        this.mLeakAlpha = i;
        this.mLeakPaint.setAntiAlias(true);
        this.mLeakPaint.setDither(true);
        this.mLeakPaint.setFilterBitmap(true);
        this.mLeakPaint.setAlpha(i);
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled() && (bitmap = this.mBitmap) != this.realphoto) {
            bitmap.recycle();
            this.mBitmap = null;
        }
        invalidate();
    }

    public void SetLeakHueColorFilter(int i) {
        Bitmap bitmap;
        this.mLeakHue = i;
        this.mLeakPaint.setAntiAlias(true);
        this.mLeakPaint.setDither(true);
        this.mLeakPaint.setFilterBitmap(true);
        this.mLeakPaint.setColorFilter(DMColorFilterGenerator.adjustHue(i));
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled() && (bitmap = this.mBitmap) != this.realphoto) {
            bitmap.recycle();
            this.mBitmap = null;
        }
        invalidate();
    }

    public void setLeakPaintXfermode(PorterDuffXfermode porterDuffXfermode) {
        this.mLeakPaint.setAntiAlias(true);
        this.mLeakPaint.setDither(true);
        this.mLeakPaint.setFilterBitmap(true);
        this.mLeakPaint.setXfermode(porterDuffXfermode);
        invalidate();
    }

    public int getPageEffectIntensity() {
        return this.mPageEffectIntensity;
    }

    @Override // touch.android.library.imagezoom.ImageViewTouchBase, org.picspool.lib.view.image.DMIgnoreRecycleImageView, android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        int i;
        Bitmap bitmap;
        canvas.setDrawFilter(this.mPaintFlagsDrawFilter);
        Bitmap imageBitmap = getImageBitmap();
        this.realphoto = imageBitmap;
        if (imageBitmap == null || imageBitmap.isRecycled()) {
            return;
        }
        Matrix matrix = new Matrix();
        matrix.set(getImageViewMatrix());
        this.mPaint.setAntiAlias(true);
        this.mPaint.setFilterBitmap(true);
        this.mPaint.setDither(true);
        if (!this.isBlurOverlay && (bitmap = this.blurBgTmpBitmap) != null && !bitmap.isRecycled()) {
            this.blurBgTmpBitmap.recycle();
            this.blurBgTmpBitmap = null;
        }
        int i2 = 0;
        if (this.mPageEffectIntensity > 0) {
            if (this.mBitmap == null) {
                Bitmap leakBorderBitmap = getLeakBorderBitmap(this.realphoto);
                this.mBitmap = new PageEffect(null, false).getResultBitmap(leakBorderBitmap, 100 - this.mPageEffectIntensity);
                if (leakBorderBitmap != null && !leakBorderBitmap.isRecycled() && leakBorderBitmap != this.realphoto && leakBorderBitmap != this.mBitmap) {
                    leakBorderBitmap.recycle();
                }
            }
        } else if (this.mFoldEffectIntensity > 0.3f) {
            if (this.mBitmap == null) {
                Bitmap leakBorderBitmap2 = getLeakBorderBitmap(this.realphoto);
                this.mBitmap = new FoldEffect(getContext()).getFoldBitmap(leakBorderBitmap2, this.mFoldEffectIntensity);
                if (leakBorderBitmap2 != null && !leakBorderBitmap2.isRecycled() && leakBorderBitmap2 != this.realphoto && leakBorderBitmap2 != this.mBitmap) {
                    leakBorderBitmap2.recycle();
                }
            }
        } else if (this.mMosaicIntensity > 0) {
            if (this.mBitmap == null) {
                Bitmap leakBorderBitmap3 = getLeakBorderBitmap(this.realphoto);
                Bitmap mosaicSrcBitmap = getMosaicSrcBitmap(leakBorderBitmap3);
                if (this.realphoto != mosaicSrcBitmap) {
                    this.mBitmap = mosaicSrcBitmap;
                }
                if (leakBorderBitmap3 != null && !leakBorderBitmap3.isRecycled() && leakBorderBitmap3 != this.realphoto && leakBorderBitmap3 != this.mBitmap) {
                    leakBorderBitmap3.recycle();
                }
            }
        } else if (this.mBitmap == null) {
            Bitmap leakBorderBitmap4 = getLeakBorderBitmap(this.realphoto);
            Bitmap creatBlurMaskBitmap = creatBlurMaskBitmap(leakBorderBitmap4, leakBorderBitmap4.getWidth(), leakBorderBitmap4.getHeight(), this.filter);
            if (this.realphoto != creatBlurMaskBitmap) {
                this.mBitmap = creatBlurMaskBitmap;
            }
            if (leakBorderBitmap4 != null && !leakBorderBitmap4.isRecycled() && leakBorderBitmap4 != this.realphoto && leakBorderBitmap4 != this.mBitmap) {
                leakBorderBitmap4.recycle();
            }
        }
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 == null) {
            canvas.drawBitmap(this.realphoto, matrix, this.mPaint);
        } else {
            canvas.drawBitmap(bitmap2, matrix, this.mPaint);
        }
        if (this.isTouched) {
            Paint paint = new Paint();
            paint.setColor(-1);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(1.0f);
            float width = getWidth() / 4.0f;
            float f = width;
            int i3 = 0;
            while (true) {
                if (i3 >= 3) {
                    break;
                }
                if (i3 == 1) {
                    paint.setColor(getResources().getColor(R.color.bottom_selected_color));
                } else {
                    paint.setColor(-1);
                }
                canvas.drawLine(0.0f, f, getWidth(), f, paint);
                f += width;
                i3++;
            }
            float height = getHeight() / 4.0f;
            float f2 = height;
            for (i = 3; i2 < i; i = 3) {
                if (i2 == 1) {
                    paint.setColor(getResources().getColor(R.color.bottom_selected_color));
                } else {
                    paint.setColor(-1);
                }
                canvas.drawLine(f2, 0.0f, f2, getHeight(), paint);
                f2 += height;
                i2++;
            }
        }
    }

    public void setBorderRes(DMWBRes dMWBRes) {
        Bitmap bitmap;
        this.mBorderRes = dMWBRes;
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled() && (bitmap = this.mBitmap) != this.realphoto) {
            bitmap.recycle();
            this.mBitmap = null;
        }
        invalidate();
    }

    private Bitmap getLeakBorderBitmap(Bitmap bitmap) {
        if (this.mLeakBitmap != null) {
            Bitmap bitmap2 = getleakBitmap(bitmap);
            if (bitmap2 != bitmap && bitmap != this.realphoto && bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            bitmap = bitmap2;
        }
        if (this.mBorderRes != null) {
            Bitmap borderBitmap = getBorderBitmap(bitmap);
            if (borderBitmap != bitmap && bitmap != this.realphoto && bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            return borderBitmap;
        }
        return bitmap;
    }

    private Bitmap getBorderBitmap(Bitmap bitmap) {
        DMWBRes dMWBRes = this.mBorderRes;
        if (dMWBRes != null) {
            CSTBorderRes cSTBorderRes = (CSTBorderRes) dMWBRes;
            if (cSTBorderRes.getName() != "b00") {
                CSBorderReturns processNinePathBorderOuter = CSTBorderProcess.processNinePathBorderOuter(getContext(), bitmap.getWidth(), bitmap.getHeight(), cSTBorderRes);
                int left = processNinePathBorderOuter.getLeft();
                int right = processNinePathBorderOuter.getRight();
                Rect rect = new Rect(left, processNinePathBorderOuter.getTop(), bitmap.getWidth() - right, bitmap.getHeight() - processNinePathBorderOuter.getBottom());
                Bitmap frameBitmap = processNinePathBorderOuter.getFrameBitmap();
                Bitmap createScaledBitmap = Bitmap.createScaledBitmap(frameBitmap, bitmap.getWidth(), bitmap.getHeight(), false);
                frameBitmap.recycle();
                Canvas canvas = new Canvas(createScaledBitmap);
                canvas.setDrawFilter(this.mPaintFlagsDrawFilter);
                this.mBorderPaint.setAntiAlias(true);
                this.mBorderPaint.setDither(true);
                this.mBorderPaint.setFilterBitmap(true);
                this.mBorderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
                canvas.drawBitmap(bitmap, (Rect) null, rect, this.mBorderPaint);
                return createScaledBitmap;
            }
        }
        return bitmap;
    }

    private Bitmap getleakBitmap(Bitmap bitmap) {
        if (bitmap == null || this.mLeakBitmap == null) {
            return bitmap;
        }
        Bitmap copy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(copy);
        canvas.setDrawFilter(this.mPaintFlagsDrawFilter);
        canvas.drawBitmap(this.mLeakBitmap, (Rect) null, new Rect(0, 0, copy.getWidth(), copy.getHeight()), this.mLeakPaint);
        return copy;
    }

    public Bitmap overlappingBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return bitmap;
        }
        if (bitmap.isRecycled()) {
            return null;
        }
        float rotationScale = getRotationScale(this.mRotationDegree, bitmap.getWidth(), bitmap.getHeight());
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, (Rect) null, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), paint);
        int saveLayer = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.rotate(this.mRotationDegree + 1.0f, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.scale(rotationScale, rotationScale, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.translate(this.mTranslateX, this.mTranslateY);
        canvas.drawBitmap(bitmap, (Rect) null, new Rect(0, 0, (int) ((bitmap.getWidth() * rotationScale) + 0.5f), (int) ((bitmap.getHeight() * rotationScale) + 0.5f)), paint);
        canvas.restoreToCount(saveLayer);
        int saveLayer2 = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.rotate((-this.mRotationDegree) - 1.0f, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.scale(rotationScale, rotationScale, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.translate(this.mTranslateX, this.mTranslateY);
        canvas.drawBitmap(bitmap, (Rect) null, new Rect(0, 0, (int) ((bitmap.getWidth() * rotationScale) + 0.5f), (int) ((bitmap.getHeight() * rotationScale) + 0.5f)), paint);
        canvas.restoreToCount(saveLayer2);
        int saveLayer3 = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.scale(rotationScale, rotationScale, canvas.getWidth() / 2, canvas.getHeight() / 2);
        float f = 1.0f - rotationScale;
        canvas.translate((bitmap.getWidth() * f) / 2.0f, (f * bitmap.getHeight()) / 2.0f);
        canvas.drawBitmap(bitmap, (Rect) null, new Rect(0, 0, (int) ((bitmap.getWidth() * rotationScale) + 0.5f), (int) ((rotationScale * bitmap.getHeight()) + 0.5f)), paint);
        canvas.restoreToCount(saveLayer3);
        return createBitmap;
    }

    public void setBlurBgHueColorFilter(float f) {
        Bitmap bitmap;
        this.mBlurBgHue = f;
        if (f != 0.0f) {
            this.blurBgpPaint.setColorFilter(DMColorFilterGenerator.adjustHue(f));
        } else {
            this.blurBgpPaint.setColorFilter(null);
        }
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled() && (bitmap = this.mBitmap) != this.realphoto) {
            bitmap.recycle();
            this.mBitmap = null;
        }
        invalidate();
    }

    public void setBlurBgGradientShader(Shader shader) {
        Bitmap bitmap;
        if (this.blurBgTmpBitmap == null) {
            if (this.realphoto == null) {
                this.realphoto = getImageBitmap();
            }
            Bitmap bitmap2 = this.realphoto;
            if (bitmap2 != null) {
                Bitmap sampeZoomFromBitmap = DMBitmapUtil.sampeZoomFromBitmap(bitmap2, 300);
                try {
                    Bitmap blur = FastBlurFilter.blur(sampeZoomFromBitmap, 20, true);
                    this.blurBgTmpBitmap = blur;
                    if (blur != sampeZoomFromBitmap && sampeZoomFromBitmap != null && !sampeZoomFromBitmap.isRecycled()) {
                        sampeZoomFromBitmap.recycle();
                    }
                } catch (Throwable ignored) {
                }
            }
        }
        if (this.blurBgTmpBitmap != null) {
            BitmapShader bitmapShader = new BitmapShader(this.blurBgTmpBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            if (shader != null) {
                this.mBlurBgShader = new ComposeShader(bitmapShader, shader, PorterDuff.Mode.MULTIPLY);
            } else {
                this.mBlurBgShader = bitmapShader;
            }
        } else if (this.realphoto != null) {
            BitmapShader bitmapShader2 = new BitmapShader(this.realphoto, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
            if (shader != null) {
                this.mBlurBgShader = new ComposeShader(bitmapShader2, shader, PorterDuff.Mode.MULTIPLY);
            } else {
                this.mBlurBgShader = bitmapShader2;
            }
        }
        Bitmap bitmap3 = this.mBitmap;
        if (bitmap3 != null && !bitmap3.isRecycled() && (bitmap = this.mBitmap) != this.realphoto) {
            bitmap.recycle();
            this.mBitmap = null;
        }
        invalidate();
    }

    public Bitmap BlurOverlayBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return bitmap;
        }
        if (bitmap.isRecycled()) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        if (this.blurBgpPaint == null) {
            Paint paint = new Paint();
            this.blurBgpPaint = paint;
            paint.setAntiAlias(true);
            this.blurBgpPaint.setFilterBitmap(true);
        }
        if (this.mBlurBgShader == null || this.blurBgTmpBitmap == null) {
            setBlurBgGradientShader(null);
        }
        this.blurBgpPaint.setShader(this.mBlurBgShader);
        Bitmap bitmap2 = this.blurBgTmpBitmap;
        if (bitmap2 != null) {
            Bitmap createBitmap2 = Bitmap.createBitmap(bitmap2.getWidth(), this.blurBgTmpBitmap.getHeight(), Bitmap.Config.ARGB_8888);
            new Canvas(createBitmap2).drawRect(new Rect(0, 0, this.blurBgTmpBitmap.getWidth(), this.blurBgTmpBitmap.getHeight()), this.blurBgpPaint);
            canvas.drawBitmap(createBitmap2, (Rect) null, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), this.blurBgpPaint);
            createBitmap2.recycle();
        } else if (this.realphoto != null) {
            canvas.drawRect(new Rect(0, 0, this.realphoto.getWidth(), this.realphoto.getHeight()), this.blurBgpPaint);
        } else {
            canvas.drawBitmap(bitmap, (Rect) null, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), this.mPaint);
        }
        int i = width / 20;
        int i2 = height / 20;
        canvas.drawBitmap(bitmap, (Rect) null, new RectF(i, i2, width - i, height - i2), (Paint) null);
        return createBitmap;
    }

    private float getRotationScale(float f, float f2, float f3) {
        float f4 = f2 / 2.0f;
        float f5 = f3 / 2.0f;
        double d = f;
        Point rotationPoint = getRotationPoint(new PointF(0.0f, 0.0f), new PointF(f4, f5), d);
        Point rotationPoint2 = getRotationPoint(new PointF(f2, 0.0f), new PointF(f4, f5), d);
        Point rotationPoint3 = getRotationPoint(new PointF(f2, f3), new PointF(f4, f5), d);
        Point rotationPoint4 = getRotationPoint(new PointF(0.0f, f3), new PointF(f4, f5), d);
        if (f2 > f3) {
            float max = Math.max(Math.max(rotationPoint.y, rotationPoint2.y), Math.max(rotationPoint3.y, rotationPoint4.y)) - Math.min(Math.min(rotationPoint.y, rotationPoint2.y), Math.min(rotationPoint3.y, rotationPoint4.y));
            float f6 = f3 / max;
            this.mTranslateX = (int) (((((f2 * max) / f3) - f2) / 2.0f) + 0.5f);
            this.mTranslateY = (int) (((max - f3) / 2.0f) + 0.5f);
            return f6;
        }
        float max2 = Math.max(Math.max(rotationPoint.x, rotationPoint2.x), Math.max(rotationPoint3.x, rotationPoint4.x)) - Math.min(Math.min(rotationPoint.x, rotationPoint2.x), Math.min(rotationPoint3.x, rotationPoint4.x));
        float f7 = f2 / max2;
        this.mTranslateX = (int) (((max2 - f2) / 2.0f) + 0.5f);
        this.mTranslateY = (int) (((((f3 * max2) / f2) - f3) / 2.0f) + 0.5f);
        return f7;
    }

    private Point getRotationPoint(PointF pointF, PointF pointF2, double d) {
        double radians = Math.toRadians(d);
        float f = pointF.x;
        float f2 = pointF.y;
        float f3 = pointF2.x;
        float f4 = pointF2.y;
        float f5 = f - f3;
        float f6 = f2 - f4;
        return new Point((int) (((((float) Math.cos(radians)) * f5) - (((float) Math.sin(radians)) * f6)) + f3), (int) ((f5 * ((float) Math.sin(radians))) + (f6 * ((float) Math.cos(radians))) + f4));
    }

    public void setIsShowShadow(boolean z) {
        Bitmap bitmap;
        this.isShowShadow = z;
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled() && (bitmap = this.mBitmap) != this.realphoto) {
            bitmap.recycle();
            this.mBitmap = null;
        }
        if (this.isShowShadow) {
            this.padding = DMScreenInfoUtil.dip2px(getContext(), 4.0f);
            this.filter = new BlurMaskFilter(this.padding, BlurMaskFilter.Blur.OUTER);
        } else {
            this.padding = 0;
            this.filter = null;
        }
        invalidate();
    }

    public void setIsOverlay(boolean z) {
        Bitmap bitmap;
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled() && (bitmap = this.mBitmap) != this.realphoto) {
            bitmap.recycle();
            this.mBitmap = null;
        }
        this.isOverlay = z;
        invalidate();
    }

    public void setIsBlurOverlay(boolean z) {
        Bitmap bitmap;
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled() && (bitmap = this.mBitmap) != this.realphoto) {
            bitmap.recycle();
            this.mBitmap = null;
        }
        this.isBlurOverlay = z;
        invalidate();
    }

    public boolean getIsBlurOverlay() {
        return this.isBlurOverlay;
    }

    public void setIsOverlapping(boolean z) {
        Bitmap bitmap;
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled() && (bitmap = this.mBitmap) != this.realphoto) {
            bitmap.recycle();
            this.mBitmap = null;
        }
        this.isOverlapping = z;
        invalidate();
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01a7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public Bitmap creatBlurMaskBitmap(Bitmap r12, int r13, int r14, BlurMaskFilter r15) {
        /*
            Method dump skipped, instructions count: 428
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: touch.android.library.imagezoom.ImageViewTouch.creatBlurMaskBitmap(android.graphics.Bitmap, int, int, android.graphics.BlurMaskFilter):android.graphics.Bitmap");
    }

    public Bitmap toRoundCorner(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() - (this.padding * 2), bitmap.getHeight() - (this.padding * 2), false);
    }

    public Bitmap getDispalyImage(int i) {
        Bitmap creatBlurMaskBitmap;
        Bitmap imageBitmap = getImageBitmap();
        this.realphoto = imageBitmap;
        Bitmap leakBorderBitmap = getLeakBorderBitmap(imageBitmap);
        if (leakBorderBitmap == null || leakBorderBitmap.isRecycled()) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Matrix matrix = new Matrix();
        matrix.set(getImageViewMatrix());
        float width = i / getWidth();
        if (this.mPageEffectIntensity > 0) {
            creatBlurMaskBitmap = new PageEffect(null, false).getResultBitmap(leakBorderBitmap, 100 - this.mPageEffectIntensity);
        } else if (this.mFoldEffectIntensity > 0.3f) {
            creatBlurMaskBitmap = new FoldEffect(getContext()).getFoldBitmap(leakBorderBitmap, this.mFoldEffectIntensity);
        } else if (this.mMosaicIntensity > 0) {
            creatBlurMaskBitmap = getMosaicSrcBitmap(leakBorderBitmap);
        } else {
            creatBlurMaskBitmap = creatBlurMaskBitmap(leakBorderBitmap, leakBorderBitmap.getWidth(), leakBorderBitmap.getHeight(), this.filter);
        }
        matrix.postScale(width, width);
        canvas.drawBitmap(creatBlurMaskBitmap, matrix, null);
        if (creatBlurMaskBitmap != this.realphoto) {
            creatBlurMaskBitmap.recycle();
        }
        if (leakBorderBitmap != this.realphoto) {
            leakBorderBitmap.recycle();
        }
        float f = 1.0f / width;
        matrix.postScale(f, f);
        return createBitmap;
    }

    public Bitmap getDispalyImage(int i, int i2) {
        Bitmap creatBlurMaskBitmap;
        Bitmap imageBitmap = getImageBitmap();
        this.realphoto = imageBitmap;
        Bitmap leakBorderBitmap = getLeakBorderBitmap(imageBitmap);
        if (leakBorderBitmap == null || leakBorderBitmap.isRecycled()) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Matrix matrix = new Matrix();
        matrix.set(getImageViewMatrix());
        float width = i / getWidth();
        if (this.mPageEffectIntensity > 0) {
            creatBlurMaskBitmap = new PageEffect(null, false).getResultBitmap(leakBorderBitmap, 100 - this.mPageEffectIntensity);
        } else if (this.mFoldEffectIntensity > 0.3f) {
            creatBlurMaskBitmap = new FoldEffect(getContext()).getFoldBitmap(leakBorderBitmap, this.mFoldEffectIntensity);
        } else if (this.mMosaicIntensity > 0) {
            creatBlurMaskBitmap = getMosaicSrcBitmap(leakBorderBitmap);
        } else {
            creatBlurMaskBitmap = creatBlurMaskBitmap(leakBorderBitmap, leakBorderBitmap.getWidth(), leakBorderBitmap.getHeight(), this.filter);
        }
        matrix.postScale(width, width);
        canvas.drawBitmap(creatBlurMaskBitmap, matrix, null);
        if (creatBlurMaskBitmap != this.realphoto) {
            creatBlurMaskBitmap.recycle();
        }
        if (leakBorderBitmap != this.realphoto) {
            leakBorderBitmap.recycle();
        }
        float f = 1.0f / width;
        matrix.postScale(f, f);
        return createBitmap;
    }

    public Bitmap getDispalyImage() {
        Bitmap imageBitmap = getImageBitmap();
        this.realphoto = imageBitmap;
        if (imageBitmap == null || imageBitmap.isRecycled()) {
            return null;
        }
        new Matrix().set(getImageViewMatrix());
        Bitmap bitmap = this.realphoto;
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), this.realphoto.getHeight(), getImageViewMatrix(), false);
    }

    private Bitmap getMosaicSrcBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return bitmap;
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColor(-1);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawRect(rect, paint);
        canvas.drawBitmap(bitmap, (Rect) null, rect, paint);
        Paint paint2 = new Paint();
        int i = this.mMosaicIntensity;
        int i2 = i < 10 ? 10 : i;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = i2;
        paint2.setShader(new LinearGradient(0.0f, 0.0f, f, 0.0f, (int) ViewCompat.MEASURED_SIZE_MASK, -1, Shader.TileMode.CLAMP));
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        float f2 = height;
        canvas.drawRect(0.0f, 0.0f, f, f2, paint2);
        float f3 = width - i2;
        float f4 = width;
        paint2.setShader(new LinearGradient(f3, 0.0f, f4, 0.0f, -1, (int) ViewCompat.MEASURED_SIZE_MASK, Shader.TileMode.CLAMP));
        canvas.drawRect(f3, 0.0f, f4, f2, paint2);
        paint2.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, f, (int) ViewCompat.MEASURED_SIZE_MASK, -1, Shader.TileMode.CLAMP));
        canvas.drawRect(0.0f, 0.0f, f4, f, paint2);
        float f5 = height - i2;
        paint2.setShader(new LinearGradient(0.0f, f5, 0.0f, f2, -1, (int) ViewCompat.MEASURED_SIZE_MASK, Shader.TileMode.CLAMP));
        canvas.drawRect(0.0f, f5, f4, f2, paint2);
        return createBitmap;
    }

    public void dispose(Bitmap bitmap) {
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 != null) {
            if (!bitmap2.isRecycled()) {
                this.mBitmap.recycle();
            }
            this.mBitmap = null;
        }
        Bitmap bitmap3 = this.realphoto;
        if (bitmap3 != null && bitmap != null && bitmap != bitmap3) {
            if (!bitmap3.isRecycled()) {
                this.realphoto.recycle();
            }
            this.realphoto = null;
        }
        this.realphoto = null;
        Bitmap bitmap4 = this.mLeakBitmap;
        if (bitmap4 != null && !bitmap4.isRecycled()) {
            this.mLeakBitmap.recycle();
            this.mLeakBitmap = null;
        }
        Bitmap bitmap5 = this.blurBgTmpBitmap;
        if (bitmap5 == null || bitmap5.isRecycled()) {
            return;
        }
        this.blurBgTmpBitmap.recycle();
        this.blurBgTmpBitmap = null;
    }
}

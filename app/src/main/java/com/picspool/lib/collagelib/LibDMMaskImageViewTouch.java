package com.picspool.lib.collagelib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import androidx.core.view.ViewCompat;
import java.io.PrintStream;
import java.util.HashMap;
import com.picspool.lib.collagelib.resource.collage.LibDMCollageInfo;
import com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouch;
import com.picspool.lib.sephiroth.android.library.imagezoom.graphics.FastBitmapDrawable;

/* loaded from: classes3.dex */
public class LibDMMaskImageViewTouch extends ImageViewTouch {
    public static final int LINE_MODE_EXCHANGE = -65536;
    public static final int LINE_MODE_LONG = -16711936;
    public static final int LINE_MODE_NONE = -1;
    public static final int LINE_MODE_TOUCH = -16711936;
    private static final String TAG = "MaskScrollImageViewTouch";
    private static final int TOUCH_SLOP = 10;
    PorterDuffXfermode Dst_In_Xfermode;
    Paint ShadowPaint;
    PorterDuffXfermode Src_In_Xfermode;
    BlurMaskFilter blurFilter;
    BlurMaskFilter blurFilterInner;
    private Handler clickHandler;
    int currentEvent;
    private int drawLineMode;
    private int index;
    private boolean isCanCorner;
    public Boolean isDrowRect;
    private boolean isMoved;
    private boolean keyUpDown;
    private Boolean longclickEnable;
    Path mAllPath;
    private Bitmap mBitmap;
    private LibDMBitmapInfo mBitmapInfo;
    private Shader mBmpShader;
    int mChangePadding;
    public OnCustomeClickListener mClickListener;
    private LibDMCollageInfo mCollageInfo;
    private RectF mFrame;
    boolean mIsHorizontalMirror;
    private Boolean mIsShowFrame;
    private Boolean mIsUsingShadow;
    private Boolean mIsUstingLongclick;
    private int mLastMotionX;
    private int mLastMotionY;
    public OnCustomeLongClickListener mLongClickListener;
    private Runnable mLongPressRunnable;
    private Bitmap mMask;
    private Paint mPaint;
    private Path mPath;
    CornerPathEffect mPathEffect;
    protected Rect mRect;
    private float mRotationDegree;
    private float mRotationScale;
    private int mShadowColor;
    private int mTranslateX;
    private int mTranslateY;
    private Uri mUri;
    Matrix matrix;
    private int mradius;
    private int padding;
    Path pathrect;
    Paint rcPaint;
    Matrix shadowMatrix;
    Paint shadowPaint;
    private int timer;

    /* loaded from: classes3.dex */
    public interface OnCustomeClickListener {
        void CustomeClick(int i);

        void CustomeTouchUp(int i);
    }

    /* loaded from: classes3.dex */
    public interface OnCustomeLongClickListener {
        void CustomeLongClick(int i);
    }

    static /* synthetic */ int access$108(LibDMMaskImageViewTouch libDMMaskImageViewTouch) {
        int i = libDMMaskImageViewTouch.timer;
        libDMMaskImageViewTouch.timer = i + 1;
        return i;
    }

    public void setShadowColor(int i) {
        this.mShadowColor = i;
        invalidate();
    }

    public LibDMCollageInfo getCollageInfo() {
        return this.mCollageInfo;
    }

    public void setCollageInfo(LibDMCollageInfo libDMCollageInfo) {
        this.mCollageInfo = libDMCollageInfo;
    }

    public int getDrawLineMode() {
        return this.drawLineMode;
    }

    public void setDrawLineMode(int i) {
        this.drawLineMode = i;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public void setUri(Uri uri) {
        this.mUri = uri;
    }

    public Bitmap getSrcBitmap() {
        return this.mBitmap;
    }

    public LibDMBitmapInfo getBitmapInfo() {
        return this.mBitmapInfo;
    }

    public void setBitmapInfo(LibDMBitmapInfo libDMBitmapInfo) {
        this.mBitmapInfo = libDMBitmapInfo;
    }

    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase
    public void setIsMirror(boolean z) {
        this.mIsHorizontalMirror = z;
    }

    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase
    public boolean getIsMirror() {
        return this.mIsHorizontalMirror;
    }

    public void setImagesetImageBitmapWithStatKeepNull() {
        super.setImageBitmapWithStatKeep(null);
    }

    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase
    public void setImageBitmap(Bitmap bitmap, boolean z, Matrix matrix, float f) {
        this.mBitmap = bitmap;
        if (bitmap != null && bitmap.isRecycled()) {
            try {
                new HashMap().put("MaskImageViewTouch_setImageBitmap_Error", "IsRecycled");
                return;
            } catch (Exception unused) {
                return;
            }
        }
        super.setImageBitmap(bitmap, z, matrix, f);
    }

    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase
    public void setImageBitmapWithStatKeep(Bitmap bitmap) {
        this.mBitmap = bitmap;
        if (bitmap != null && bitmap.isRecycled()) {
            try {
                new HashMap().put("MaskImageViewTouch_setImageBitmap_Error", "IsRecycled");
                return;
            } catch (Exception unused) {
                return;
            }
        }
        super.setImageBitmapWithStatKeep(bitmap);
    }

    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase
    public void setImageBitmap(Bitmap bitmap, boolean z, Matrix matrix) {
        this.mBitmap = bitmap;
        if (bitmap != null && bitmap.isRecycled()) {
            try {
                new HashMap().put("MaskImageViewTouch_setImageBitmap_Error", "IsRecycled");
                return;
            } catch (Exception unused) {
                return;
            }
        }
        super.setImageBitmap(bitmap, z, matrix);
    }

    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase
    public void setImageBitmap(Bitmap bitmap, boolean z) {
        this.mBitmap = bitmap;
        if (bitmap != null && bitmap.isRecycled()) {
            try {
                new HashMap().put("MaskImageViewTouch_setImageBitmap_Error", "IsRecycled");
                return;
            } catch (Exception unused) {
                return;
            }
        }
        super.setImageBitmap(bitmap, z);
    }

    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase, android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        if (bitmap != null && bitmap.isRecycled()) {
            try {
                new HashMap().put("MaskImageViewTouch_setImageBitmap_Error", "IsRecycled");
                return;
            } catch (Exception unused) {
                return;
            }
        }
        super.setImageBitmap(bitmap);
    }

    public int getShadowColor() {
        return this.mShadowColor;
    }

    public LibDMMaskImageViewTouch(Context context) {
        super(context);
        this.mFrame = new RectF();
        this.padding = 0;
        this.isDrowRect = false;
        this.mradius = 10;
        this.mIsUsingShadow = false;
        this.mIsShowFrame = false;
        this.mIsUstingLongclick = true;
        this.keyUpDown = false;
        this.timer = 0;
        this.longclickEnable = false;
        this.index = 0;
        this.mShadowColor = ViewCompat.MEASURED_STATE_MASK;
        this.drawLineMode = -1;
        this.mBitmapInfo = new LibDMBitmapInfo();
        this.mIsHorizontalMirror = false;
        this.blurFilter = new BlurMaskFilter(10.0f, BlurMaskFilter.Blur.OUTER);
        this.blurFilterInner = new BlurMaskFilter(10.0f, BlurMaskFilter.Blur.INNER);
        this.mChangePadding = 10;
        this.isCanCorner = true;
        this.clickHandler = new Handler() { // from class: com.picspool.lib.collagelib.LibDMMaskImageViewTouch.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 0) {
                    LibDMMaskImageViewTouch.this.keyUpDown = true;
                    LibDMMaskImageViewTouch.this.timer = 0;
                    LibDMMaskImageViewTouch.this.keyUpDownListener();
                    LibDMMaskImageViewTouch.this.mClickListener.CustomeClick(LibDMMaskImageViewTouch.this.index);
                } else if (message.what == 1) {
                    LibDMMaskImageViewTouch.this.keyUpDown = false;
                    LibDMMaskImageViewTouch.this.timer = 0;
                    if (LibDMMaskImageViewTouch.this.longclickEnable.booleanValue()) {
                        return;
                    }
                    LibDMMaskImageViewTouch.this.mClickListener.CustomeTouchUp(LibDMMaskImageViewTouch.this.index);
                } else if (message.what == 100) {
                    if (LibDMMaskImageViewTouch.this.mLongClickListener != null && LibDMMaskImageViewTouch.this.mIsUstingLongclick.booleanValue()) {
                        LibDMMaskImageViewTouch.this.mLongClickListener.CustomeLongClick(LibDMMaskImageViewTouch.this.index);
                        LibDMMaskImageViewTouch.this.setlongclickEnable(true);
                    }
                    LibDMMaskImageViewTouch.this.timer = 0;
                }
            }
        };
        this.rcPaint = new Paint(1);
        this.Dst_In_Xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        this.Src_In_Xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        this.mRect = new Rect(0, 0, getWidth(), getHeight());
        this.mAllPath = new Path();
        this.pathrect = new Path();
        this.ShadowPaint = new Paint();
        this.shadowPaint = new Paint();
        this.mRotationDegree = 0.0f;
        this.mRotationScale = 1.0f;
    }

    public LibDMMaskImageViewTouch(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFrame = new RectF();
        this.padding = 0;
        this.isDrowRect = false;
        this.mradius = 10;
        this.mIsUsingShadow = false;
        this.mIsShowFrame = false;
        this.mIsUstingLongclick = true;
        this.keyUpDown = false;
        this.timer = 0;
        this.longclickEnable = false;
        this.index = 0;
        this.mShadowColor = ViewCompat.MEASURED_STATE_MASK;
        this.drawLineMode = -1;
        this.mBitmapInfo = new LibDMBitmapInfo();
        this.mIsHorizontalMirror = false;
        this.blurFilter = new BlurMaskFilter(10.0f, BlurMaskFilter.Blur.OUTER);
        this.blurFilterInner = new BlurMaskFilter(10.0f, BlurMaskFilter.Blur.INNER);
        this.mChangePadding = 10;
        this.isCanCorner = true;
        this.clickHandler = new Handler() { // from class: com.picspool.lib.collagelib.LibDMMaskImageViewTouch.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 0) {
                    LibDMMaskImageViewTouch.this.keyUpDown = true;
                    LibDMMaskImageViewTouch.this.timer = 0;
                    LibDMMaskImageViewTouch.this.keyUpDownListener();
                    LibDMMaskImageViewTouch.this.mClickListener.CustomeClick(LibDMMaskImageViewTouch.this.index);
                } else if (message.what == 1) {
                    LibDMMaskImageViewTouch.this.keyUpDown = false;
                    LibDMMaskImageViewTouch.this.timer = 0;
                    if (LibDMMaskImageViewTouch.this.longclickEnable.booleanValue()) {
                        return;
                    }
                    LibDMMaskImageViewTouch.this.mClickListener.CustomeTouchUp(LibDMMaskImageViewTouch.this.index);
                } else if (message.what == 100) {
                    if (LibDMMaskImageViewTouch.this.mLongClickListener != null && LibDMMaskImageViewTouch.this.mIsUstingLongclick.booleanValue()) {
                        LibDMMaskImageViewTouch.this.mLongClickListener.CustomeLongClick(LibDMMaskImageViewTouch.this.index);
                        LibDMMaskImageViewTouch.this.setlongclickEnable(true);
                    }
                    LibDMMaskImageViewTouch.this.timer = 0;
                }
            }
        };
        this.rcPaint = new Paint(1);
        this.Dst_In_Xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        this.Src_In_Xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        this.mRect = new Rect(0, 0, getWidth(), getHeight());
        this.mAllPath = new Path();
        this.pathrect = new Path();
        this.ShadowPaint = new Paint();
        this.shadowPaint = new Paint();
        this.mRotationDegree = 0.0f;
        this.mRotationScale = 1.0f;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouch, com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase
    public void init() {
        super.init();
        setFitToScreen(true);
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setFilterBitmap(true);
    }

    public void setIsLongclick(boolean z) {
        this.mIsUstingLongclick = Boolean.valueOf(z);
    }

    public Boolean getIsLongclick() {
        return this.mIsUstingLongclick;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIsUsingShadow(boolean z) {
        this.mIsUsingShadow = Boolean.valueOf(z);
        if (z) {
            this.padding = this.mChangePadding;
            return;
        }
        this.padding = 0;
        this.mPaint.setMaskFilter(null);
    }

    public void setIsShowFrame(boolean z) {
        this.mIsShowFrame = Boolean.valueOf(z);
    }

    public int getChangePadding() {
        return this.mChangePadding;
    }

    public void setChangePadding(int i) {
        if (!this.mIsUsingShadow.booleanValue() || this.mIsShowFrame.booleanValue()) {
            return;
        }
        this.padding = i;
        this.mChangePadding = i;
        if (i > 0) {
            this.blurFilter = new BlurMaskFilter(this.padding, BlurMaskFilter.Blur.OUTER);
            this.blurFilterInner = new BlurMaskFilter(this.padding, BlurMaskFilter.Blur.INNER);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouch, com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase
    public void onBitmapChanged(Drawable drawable) {
        if (drawable == null) {
            this.mBmpShader = null;
            return;
        }
        Shader createShader = createShader(((FastBitmapDrawable) drawable).getBitmap());
        this.mBmpShader = createShader;
        this.mPaint.setShader(createShader);
        super.onBitmapChanged(drawable);
    }

    private Shader createShader(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        return new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }

    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouch
    public Bitmap getDispalyImage(int i, int i2) {
        String str;
        Bitmap bitmap = null;
        CornerPathEffect cornerPathEffect;
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        String str2 = "MaskImageViewTouch_getDispalyImage_Error";
        try {
            try {
                float f6 = i;
                float width = f6 / getWidth();
                float f7 = i2;
                float height = f7 / getHeight();
                Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                float width2 = f6 / getWidth();
                int i3 = (int) ((this.padding * width2) + 0.5f);
                BlurMaskFilter blurMaskFilter = i3 > 0 ? new BlurMaskFilter(i3, BlurMaskFilter.Blur.OUTER) : null;
                if (this.mRotationDegree != 0.0f) {
                    float rotationScale = getRotationScale(this.mRotationDegree, f6, f7);
                    str = "MaskImageViewTouch_getDispalyImage_Error";
                    try {
                        canvas.rotate(this.mRotationDegree, i / 2, i2 / 2);
                        canvas.scale(rotationScale, rotationScale);
                        canvas.translate(this.mTranslateX, this.mTranslateY);
                        getRotationScale(this.mRotationDegree, f6, f7);
                    } catch (Exception e) {
                        e = e;
                        str2 = str;
                        new HashMap().put(str2, e.toString());
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        new HashMap().put(str, th.toString());
                        return null;
                    }
                } else {
                    str = "MaskImageViewTouch_getDispalyImage_Error";
                }
                Matrix matrix = new Matrix(getImageMatrix());
                matrix.postScale(width, height);
                Matrix matrix2 = new Matrix();
                matrix2.postScale(width, height);
                if (this.mBmpShader != null) {
                    this.mBmpShader.setLocalMatrix(matrix);
                }
                Path path = new Path(this.mPath);
                if (this.mPathEffect != null) {
                    bitmap = createBitmap;
                    cornerPathEffect = new CornerPathEffect(this.mradius * width);
                } else {
                    bitmap = createBitmap;
                    cornerPathEffect = null;
                }
                int i4 = (int) ((width2 * 6.0f) + 0.5f);
                this.mPaint.setPathEffect(cornerPathEffect);
                if (this.mMask == null) {
                    if (this.mIsShowFrame.booleanValue()) {
                        this.ShadowPaint.setMaskFilter(null);
                        this.ShadowPaint.setAntiAlias(true);
                        this.ShadowPaint.setFilterBitmap(true);
                        this.ShadowPaint.setPathEffect(this.mPathEffect);
                        this.ShadowPaint.setColor(-1);
                        Matrix matrix3 = new Matrix();
                        matrix3.postScale(width, height);
                        path.transform(matrix3);
                        canvas.drawPath(path, this.ShadowPaint);
                        Matrix matrix4 = new Matrix();
                        matrix4.postScale(1.0f / width, 1.0f / height);
                        path.transform(matrix4);
                        float f8 = i4;
                        float f9 = 2.0f * f8;
                        f5 = ((f6 - f9) / f6) * width;
                        f4 = ((f7 - f9) / f7) * height;
                        Matrix matrix5 = new Matrix();
                        this.shadowMatrix = matrix5;
                        matrix5.postScale(f5, f4);
                        this.shadowMatrix.postTranslate(f8, f8);
                        path.transform(this.shadowMatrix);
                        canvas.drawPath(path, this.mPaint);
                        Matrix matrix6 = new Matrix();
                        this.shadowMatrix = matrix6;
                        float f10 = -i4;
                        matrix6.postTranslate(f10, f10);
                        path.transform(this.shadowMatrix);
                    } else if (this.mIsUsingShadow.booleanValue()) {
                        float f11 = i3;
                        float f12 = 2.0f * f11;
                        f5 = ((f6 - f12) / f6) * width;
                        float f13 = ((f7 - f12) / f7) * height;
                        Matrix matrix7 = new Matrix();
                        this.shadowMatrix = matrix7;
                        matrix7.postScale(f5, f13);
                        this.shadowMatrix.postTranslate(f11, f11);
                        path.transform(this.shadowMatrix);
                        this.ShadowPaint.setMaskFilter(blurMaskFilter);
                        this.ShadowPaint.setAntiAlias(true);
                        this.ShadowPaint.setPathEffect(cornerPathEffect);
                        this.ShadowPaint.setColor(this.mShadowColor);
                        canvas.drawPath(path, this.ShadowPaint);
                        canvas.drawPath(path, this.mPaint);
                        Matrix matrix8 = new Matrix();
                        this.shadowMatrix = matrix8;
                        float f14 = -i3;
                        matrix8.postTranslate(f14, f14);
                        path.transform(this.shadowMatrix);
                        f4 = f13;
                    } else {
                        path.transform(matrix2);
                        canvas.drawPath(path, this.mPaint);
                        f5 = 1.0f;
                        f4 = 1.0f;
                    }
                } else {
                    if (this.mIsUsingShadow.booleanValue()) {
                        this.mPaint.setPathEffect(null);
                        float f15 = i3;
                        float f16 = 2.0f * f15;
                        f = ((f6 - f16) / f6) * width;
                        f2 = ((f7 - f16) / f7) * height;
                        Matrix matrix9 = new Matrix();
                        this.shadowMatrix = matrix9;
                        matrix9.postScale(f, f2);
                        this.shadowMatrix.postTranslate(f15, f15);
                        path.transform(this.shadowMatrix);
                        canvas.drawPath(path, this.mPaint);
                    } else {
                        path.transform(matrix2);
                        canvas.drawPath(path, this.mPaint);
                        f = 1.0f;
                        f2 = 1.0f;
                    }
                    this.mPaint.setXfermode(this.Dst_In_Xfermode);
                    if (this.mIsUsingShadow.booleanValue()) {
                        this.mRect.top = i3;
                        this.mRect.left = i3;
                        int i5 = (i2 - i3) + 2;
                        this.mRect.bottom = i5;
                        int i6 = (i - i3) + 2;
                        this.mRect.right = i6;
                        if (this.mRotationDegree != 0.0f) {
                            f3 = f;
                            int i7 = i3 - 1;
                            this.mRect.top = i7;
                            this.mRect.left = i7;
                            this.mRect.bottom = i5;
                            this.mRect.right = i6;
                        } else {
                            f3 = f;
                        }
                    } else {
                        f3 = f;
                        if (this.mRotationDegree != 0.0f) {
                            this.mRect.top = -1;
                            this.mRect.left = -1;
                            this.mRect.bottom = i2 + 2;
                            this.mRect.right = i + 2;
                        } else {
                            this.mRect.top = 0;
                            this.mRect.left = 0;
                            this.mRect.bottom = i2;
                            this.mRect.right = i;
                        }
                    }
                    this.mPaint.setMaskFilter(null);
                    this.mPaint.setPathEffect(null);
                    canvas.drawBitmap(this.mMask, (Rect) null, this.mRect, this.mPaint);
                    this.mPaint.setXfermode(null);
                    if (this.mIsUsingShadow.booleanValue() && this.padding > 0) {
                        getWidth();
                        Bitmap creatBlurMaskBitmap = creatBlurMaskBitmap(this.mMask, (int) ((f6 * 1.0f) + 0.5f), (int) ((f7 * 1.0f) + 0.5f), blurMaskFilter);
                        Paint paint = new Paint();
                        paint.setAntiAlias(true);
                        canvas.drawBitmap(creatBlurMaskBitmap, (Rect) null, new Rect(0, 0, i, i2), paint);
                        if (creatBlurMaskBitmap != this.mMask && !creatBlurMaskBitmap.isRecycled()) {
                            creatBlurMaskBitmap.recycle();
                        }
                        Matrix matrix10 = new Matrix();
                        this.shadowMatrix = matrix10;
                        float f17 = -i3;
                        matrix10.postTranslate(f17, f17);
                        path.transform(this.shadowMatrix);
                    }
                    f4 = f2;
                    f5 = f3;
                }
                if (this.mIsUsingShadow.booleanValue() || this.mIsShowFrame.booleanValue()) {
                    Matrix matrix11 = new Matrix();
                    this.matrix = matrix11;
                    matrix11.postScale(1.0f / f5, 1.0f / f4);
                    path.transform(this.matrix);
                }
                this.mPaint.setPathEffect(this.mPathEffect);
                matrix.postScale(1.0f / width, 1.0f / height);
                if (this.mBmpShader != null) {
                    this.mBmpShader.setLocalMatrix(matrix);
                }
                return bitmap;
            } catch (Exception e2) {
                e2.printStackTrace();
            } catch (Throwable th2) {
                th2.printStackTrace();
                str = "MaskImageViewTouch_getDispalyImage_Error";
            }
        } catch (Exception unused) {
            return null;
        }
        return bitmap;
    }

    public void setIsCanCorner(boolean z) {
        this.isCanCorner = z;
        if (z) {
            return;
        }
        this.mPathEffect = null;
    }

    public void setRadius(int i) {
        this.mradius = i;
        this.mPathEffect = new CornerPathEffect(i);
        if (this.isCanCorner) {
            return;
        }
        this.mPathEffect = null;
    }

    public void changeRadius(int i) {
        this.mradius = i;
        this.mPaint.setPathEffect(null);
        if (this.isCanCorner) {
            this.mPathEffect = new CornerPathEffect(i);
        } else {
            this.mPathEffect = null;
        }
        invalidate();
    }

    public Bitmap getMask() {
        return this.mMask;
    }

    public void setPath(Path path) {
        this.mPath = path;
    }

    public void setDrowRectangle(Boolean bool) {
        this.isDrowRect = bool;
        setlongclickEnable(false);
        invalidate();
    }

    public Boolean getDrowRectangle() {
        return this.isDrowRect;
    }

    public void setlongclickEnable(Boolean bool) {
        this.longclickEnable = bool;
        invalidate();
    }

    public void setMask(Bitmap bitmap) {
        Bitmap bitmap2 = this.mMask;
        if (bitmap2 != bitmap && bitmap2 != null && !bitmap2.isRecycled()) {
            this.mMask.recycle();
            this.mMask = null;
        }
        this.mMask = bitmap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.picspool.lib.collagelib.LibDMMaskImageViewTouch$2] */
    public int keyUpDownListener() {
        new Thread() { // from class: com.picspool.lib.collagelib.LibDMMaskImageViewTouch.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                while (LibDMMaskImageViewTouch.this.keyUpDown) {
                    try {
                        sleep(200L);
                        LibDMMaskImageViewTouch.access$108(LibDMMaskImageViewTouch.this);
                        if (LibDMMaskImageViewTouch.this.currentEvent == 0 || !LibDMMaskImageViewTouch.this.isMoved) {
                            if (LibDMMaskImageViewTouch.this.timer > 2 && LibDMMaskImageViewTouch.this.mLongClickListener != null) {
                                Looper.prepare();
                                LibDMMaskImageViewTouch.this.clickHandler.sendEmptyMessage(100);
                                Looper.loop();
                                LibDMMaskImageViewTouch.this.isMoved = false;
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Throwable unused) {
                    }
                }
            }
        }.start();
        return this.timer;
    }

    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouch, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (this.mPath != null) {
            RectF rectF = new RectF();
            this.mPath.computeBounds(rectF, true);
            Path path = new Path();
            this.mPath.transform(new Matrix(), path);
            if (this.mRotationDegree != 0.0f) {
                Matrix matrix = new Matrix();
                matrix.postRotate(this.mRotationDegree, rectF.width() / 2.0f, rectF.height() / 2.0f);
                float f = this.mRotationScale;
                matrix.postScale(f, f);
                matrix.postTranslate(this.mTranslateX, this.mTranslateY);
                path.transform(matrix);
            }
            path.computeBounds(rectF, true);
            Region region = new Region();
            region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
            if (!region.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                return false;
            }
            Bitmap bitmap = this.mMask;
            if (bitmap != null && !bitmap.isRecycled()) {
                try {
                    float width = getWidth() / this.mMask.getWidth();
                    float height = getHeight() / this.mMask.getHeight();
                    int x2 = (int) (motionEvent.getX() / width);
                    int y2 = (int) (motionEvent.getY() / height);
                    if (x2 < 0) {
                        x2 = 0;
                    }
                    if (y2 < 0) {
                        y2 = 0;
                    }
                    if (this.mMask.getPixel(x2, y2) == 0) {
                        return false;
                    }
                } catch (Exception e) {
                    PrintStream printStream = System.out;
                    printStream.println("error:" + e.getMessage());
                }
            }
        }
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            Log.d(TAG, "mode=DRAG");
            this.mLastMotionX = x;
            this.mLastMotionY = y;
            this.isMoved = false;
            this.currentEvent = 0;
            setlongclickEnable(false);
            this.clickHandler.sendEmptyMessage(0);
        } else if (action == 1) {
            this.currentEvent = 1;
            this.clickHandler.sendEmptyMessage(1);
        } else if (action == 2) {
            this.currentEvent = 2;
            if (!this.isMoved && (Math.abs(this.mLastMotionX - x) > 10 || Math.abs(this.mLastMotionY - y) > 10)) {
                this.isMoved = true;
                this.clickHandler.sendEmptyMessage(0);
            }
        } else if (action == 5) {
            this.currentEvent = 5;
            Log.d(TAG, "mode=ZOOM");
        } else if (action == 6) {
            this.currentEvent = 6;
            Log.d(TAG, "mode=NONE");
        } else {
            this.currentEvent = 1000;
        }
        return super.onTouchEvent(motionEvent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mFrame.set(0.0f, 0.0f, i3 - i, i4 - i2);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        float f;
        float f2;
        float f3;
        float f4;
        int saveLayer = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        try {
            try {
                if (this.mRotationDegree != 0.0f) {
                    canvas.rotate(this.mRotationDegree, getWidth() / 2, getHeight() / 2);
                    canvas.scale(this.mRotationScale, this.mRotationScale);
                    canvas.translate(this.mTranslateX, this.mTranslateY);
                }
                this.mFrame.set(0.0f, 0.0f, getWidth() - this.padding, getHeight() - this.padding);
                if (this.mBmpShader != null) {
                    Matrix matrix = new Matrix(getImageViewMatrix());
                    this.matrix = matrix;
                    this.mBmpShader.setLocalMatrix(matrix);
                }
                this.mPaint.setAntiAlias(true);
                this.mPaint.setFilterBitmap(true);
                if (this.mPath != null) {
                    this.mPaint.setPathEffect(this.mPathEffect);
                    if (this.mMask == null) {
                        if (this.mIsShowFrame.booleanValue()) {
                            this.ShadowPaint.setMaskFilter(null);
                            this.ShadowPaint.setAntiAlias(true);
                            this.ShadowPaint.setFilterBitmap(true);
                            this.ShadowPaint.setPathEffect(this.mPathEffect);
                            this.ShadowPaint.setColor(-1);
                            canvas.drawPath(this.mPath, this.ShadowPaint);
                            float f5 = 6;
                            float f6 = f5 * 2.0f;
                            f = (getWidth() - f6) / getWidth();
                            f2 = (getHeight() - f6) / getHeight();
                            Matrix matrix2 = new Matrix();
                            this.shadowMatrix = matrix2;
                            matrix2.postScale(f, f2);
                            this.shadowMatrix.postTranslate(f5, f5);
                            this.mPath.transform(this.shadowMatrix);
                            canvas.drawPath(this.mPath, this.mPaint);
                            Matrix matrix3 = new Matrix();
                            this.shadowMatrix = matrix3;
                            float f7 = -6;
                            matrix3.postTranslate(f7, f7);
                            this.mPath.transform(this.shadowMatrix);
                        } else if (this.mIsUsingShadow.booleanValue()) {
                            f = (getWidth() - (this.padding * 2.0f)) / getWidth();
                            f2 = (getHeight() - (this.padding * 2.0f)) / getHeight();
                            Matrix matrix4 = new Matrix();
                            this.shadowMatrix = matrix4;
                            matrix4.postScale(f, f2);
                            this.shadowMatrix.postTranslate(this.padding, this.padding);
                            this.mPath.transform(this.shadowMatrix);
                            this.ShadowPaint.setMaskFilter(this.blurFilter);
                            this.ShadowPaint.setAntiAlias(true);
                            this.ShadowPaint.setFilterBitmap(true);
                            this.ShadowPaint.setPathEffect(this.mPathEffect);
                            this.ShadowPaint.setColor(this.mShadowColor);
                            canvas.drawPath(this.mPath, this.ShadowPaint);
                            canvas.drawPath(this.mPath, this.mPaint);
                            Matrix matrix5 = new Matrix();
                            this.shadowMatrix = matrix5;
                            matrix5.postTranslate(-this.padding, -this.padding);
                            this.mPath.transform(this.shadowMatrix);
                        } else {
                            canvas.drawPath(this.mPath, this.mPaint);
                            f = 1.0f;
                            f2 = 1.0f;
                        }
                    } else {
                        if (this.mIsUsingShadow.booleanValue()) {
                            this.mPaint.setPathEffect(null);
                            f3 = (getWidth() - (this.padding * 2.0f)) / getWidth();
                            f4 = (getHeight() - (this.padding * 2.0f)) / getHeight();
                            Matrix matrix6 = new Matrix();
                            this.shadowMatrix = matrix6;
                            matrix6.postScale(f3, f4);
                            this.shadowMatrix.postTranslate(this.padding, this.padding);
                            this.mPath.transform(this.shadowMatrix);
                            canvas.drawPath(this.mPath, this.mPaint);
                        } else {
                            canvas.drawPath(this.mPath, this.mPaint);
                            f3 = 1.0f;
                            f4 = 1.0f;
                        }
                        this.mPaint.setXfermode(this.Dst_In_Xfermode);
                        if (this.mIsUsingShadow.booleanValue()) {
                            this.mRect.top = this.padding;
                            this.mRect.left = this.padding;
                            this.mRect.bottom = (getHeight() - this.padding) + 1;
                            this.mRect.right = (getWidth() - this.padding) + 1;
                            if (this.mRotationDegree != 0.0f) {
                                this.mRect.top = this.padding - 1;
                                this.mRect.left = this.padding - 1;
                                this.mRect.bottom = (getHeight() - this.padding) + 2;
                                this.mRect.right = (getWidth() - this.padding) + 2;
                            }
                        } else if (this.mRotationDegree != 0.0f) {
                            this.mRect.top = -1;
                            this.mRect.left = -1;
                            this.mRect.bottom = getHeight() + 2;
                            this.mRect.right = getWidth() + 2;
                        } else {
                            this.mRect.top = 0;
                            this.mRect.left = 0;
                            this.mRect.bottom = getHeight();
                            this.mRect.right = getWidth();
                        }
                        this.mPaint.setMaskFilter(null);
                        canvas.drawBitmap(this.mMask, (Rect) null, this.mRect, this.mPaint);
                        this.mPaint.setXfermode(null);
                        if (this.mIsUsingShadow.booleanValue() && this.padding > 0) {
                            Bitmap creatBlurMaskBitmap = creatBlurMaskBitmap(this.mMask, (int) ((getWidth() * 1.01f) + 0.5f), (int) ((getHeight() * 1.01f) + 0.5f), this.blurFilter);
                            Paint paint = new Paint();
                            paint.setAntiAlias(true);
                            paint.setFilterBitmap(true);
                            canvas.drawBitmap(creatBlurMaskBitmap, (Rect) null, new Rect(0, 0, getWidth(), getHeight()), paint);
                            if (creatBlurMaskBitmap != this.mMask && !creatBlurMaskBitmap.isRecycled()) {
                                creatBlurMaskBitmap.recycle();
                            }
                            Matrix matrix7 = new Matrix();
                            this.shadowMatrix = matrix7;
                            matrix7.postTranslate(-this.padding, -this.padding);
                            this.mPath.transform(this.shadowMatrix);
                        }
                        f = f3;
                        f2 = f4;
                    }
                    if (this.mIsUsingShadow.booleanValue() || this.mIsShowFrame.booleanValue()) {
                        Matrix matrix8 = new Matrix();
                        this.matrix = matrix8;
                        matrix8.postScale(1.0f / f, 1.0f / f2);
                        this.mPath.transform(this.matrix);
                    }
                } else {
                    this.mPaint.setPathEffect(this.mPathEffect);
                    this.mAllPath.addRoundRect(this.mFrame, 0.0f, 0.0f, Path.Direction.CW);
                    canvas.drawPath(this.mAllPath, this.mPaint);
                    f = 1.0f;
                    f2 = 1.0f;
                }
                if (this.isDrowRect.booleanValue()) {
                    if (this.mIsShowFrame.booleanValue() && !this.mIsUsingShadow.booleanValue()) {
                        Matrix matrix9 = new Matrix();
                        this.shadowMatrix = matrix9;
                        float f8 = 6;
                        matrix9.postTranslate(f8, f8);
                        this.mPath.transform(this.shadowMatrix);
                    }
                    float width = getWidth();
                    float height = getHeight();
                    float f9 = ((width - 4.0f) / width) * f;
                    float f10 = ((height - 4.0f) / height) * f2;
                    Matrix matrix10 = new Matrix();
                    this.matrix = matrix10;
                    matrix10.postScale(f9, f10);
                    this.matrix.postTranslate(this.padding + 2, this.padding + 2);
                    this.mPath.transform(this.matrix);
                    this.pathrect = this.mPath;
                    this.rcPaint.setPathEffect(this.mPathEffect);
                    this.rcPaint.setStyle(Paint.Style.STROKE);
                    this.rcPaint.setStrokeWidth(2.0f);
                    if (this.longclickEnable.booleanValue()) {
                        this.rcPaint.setColor(-65536);
                    } else if (this.drawLineMode != -1) {
                        this.rcPaint.setColor(this.drawLineMode);
                    } else {
                        this.rcPaint.setColor(-16711936);
                    }
                    canvas.drawPath(this.pathrect, this.rcPaint);
                    Matrix matrix11 = new Matrix();
                    this.matrix = matrix11;
                    matrix11.postTranslate((-2) - this.padding, (-2) - this.padding);
                    this.matrix.postScale(1.0f / f9, 1.0f / f10);
                    this.mPath.transform(this.matrix);
                    if (this.mIsShowFrame.booleanValue() && !this.mIsUsingShadow.booleanValue()) {
                        Matrix matrix12 = new Matrix();
                        this.shadowMatrix = matrix12;
                        float f11 = -6;
                        matrix12.postTranslate(f11, f11);
                        this.mPath.transform(this.shadowMatrix);
                    }
                }
            } catch (Exception e) {
                new HashMap().put("MaskImageViewTouch_OnDraw_Error", e.toString());
                canvas.restoreToCount(saveLayer);
            } catch (Throwable th) {
                new HashMap().put("MaskImageViewTouch_OnDraw_Error", th.toString());
                canvas.restoreToCount(saveLayer);
            }
        } catch (Exception unused) {
        }
        canvas.restoreToCount(saveLayer);
    }

    public Bitmap creatBlurMaskBitmap(Bitmap bitmap, int i, int i2, BlurMaskFilter blurMaskFilter) {
        if (getWidth() < 1 || getHeight() < 1) {
            return bitmap;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        this.shadowPaint.setAntiAlias(true);
        this.shadowPaint.setFilterBitmap(true);
        this.shadowPaint.setMaskFilter(blurMaskFilter);
        this.shadowPaint.setColor(this.mShadowColor);
        Canvas canvas = new Canvas(createBitmap);
        int width = ((int) (((i / getWidth()) * this.padding) + 0.5f)) * 2;
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, i - width, i2 - width, false);
        Bitmap extractAlpha = createScaledBitmap.extractAlpha(this.shadowPaint, new int[]{createScaledBitmap.getWidth() / 2, createScaledBitmap.getHeight() / 2});
        this.shadowPaint.setMaskFilter(null);
        canvas.drawBitmap(extractAlpha, 0.0f, 0.0f, this.shadowPaint);
        if (extractAlpha != bitmap) {
            extractAlpha.recycle();
        }
        if (createScaledBitmap != bitmap && createScaledBitmap != null && !createScaledBitmap.isRecycled()) {
            createScaledBitmap.recycle();
        }
        return createBitmap;
    }

    public static Bitmap toRoundCorner(Bitmap bitmap, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        float f = i;
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawRoundRect(rectF, f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
    }

    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase
    public void clear() {
        setImageBitmap(null, true);
        Bitmap bitmap = this.mMask;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mMask.recycle();
        }
        this.mMask = null;
    }

    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouch, com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase
    public void ResetImageView() {
        super.ResetImageView();
        this.mFrame = new RectF();
        this.padding = 0;
        this.isCanCorner = true;
        this.mPaint = null;
        Bitmap bitmap = this.mMask;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mMask.recycle();
        }
        this.mMask = null;
        this.mBmpShader = null;
        this.mPath = null;
        this.isDrowRect = false;
        this.mradius = 10;
        this.mIsUsingShadow = false;
        super.init();
        setFitToScreen(true);
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setFilterBitmap(true);
    }

    public void setCustomeLongClickListener(OnCustomeLongClickListener onCustomeLongClickListener) {
        this.mLongClickListener = onCustomeLongClickListener;
    }

    public float getRotationDegree() {
        return this.mRotationDegree;
    }

    public void setRotationDegree(float f) {
        this.mRotationDegree = f;
        this.mRotationScale = getRotationScale(f, getWidth(), getHeight());
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

    public boolean isInViewArea(float f, float f2) {
        return f >= ((float) getLeft()) && f <= ((float) getRight()) && f2 >= ((float) getTop()) && f2 <= ((float) getBottom());
    }
}

package com.picspool.lib.view.superimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.Date;

/* loaded from: classes3.dex */
public class DMSuperImageView extends View {
    protected static final int DRAG = 1;
    protected static final int JUMP = 2;
    protected static final int NONE = 0;
    protected static final int ZOOM = 3;
    private int backgroundColor;
    private GradientDrawable backgroundGradientDrawable;
    private Bitmap backgroundImage;
    private Rect backgroundImageDstRect;
    private Bitmap backgroundPattern;
    private BitmapDrawable backgroundPatternDrawable;
    private BackgroundMode bgMode;
    private Rect clear1;
    private Rect clear2;
    private PorterDuffXfermode clearXfermode;
    private boolean drawTouchingFrame;
    private PorterDuffXfermode dstInXfermode;
    private boolean horizintal;
    private Bitmap image;
    private int imgHeight;
    private float imgRatio;
    private float imgScale;
    private boolean imgScrollable;
    private Rect imgSrcRect;
    private int imgSrcRectHeight;
    private int imgSrcRectWidth;
    private int imgWidth;
    private Boolean inverse;
    protected boolean isMove;
    private boolean mCanFingerScale;
    private ColorFilter mColorFilter;
    protected PointF mCurPoint;
    protected PointF mMid;
    protected PointF mStart;
    protected int mode;
    protected int moveCount;
    protected float oldDegree;
    protected float oldDist;
    private Paint paint;
    private PathEffect pathEffect;
    private int scaleImgSrcRectHeight;
    private int scaleImgSrcRectWidth;
    private Rect shapeDstRect;
    private int shapeHeight;
    private Bitmap shapeImage;
    private boolean shapeOuterSolid;
    private Path shapePath;
    private float shapeRatio;
    private Region shapeRegion;
    private float shapeScale;
    private boolean shapeSmaller;
    private int shapeWidth;
    private ShapeMode spMode;
    private PorterDuffXfermode srcInXfermode;
    protected Date touchDate;
    protected long touchDownTime;
    private OnDMSuperImageViewTouchedListener touchedListener;
    private boolean touching;
    private int touchingColor;
    private OnDMImageTransformChangedListener transformListener;
    private DMUIPath uiPath;
    private boolean vertical;
    private int viewAlpha;
    private int viewHeight;
    private Path viewPath;
    private float viewRatio;
    private Rect viewRect;
    private int viewWidth;
    private boolean waiting;
    private PorterDuffXfermode xorXfermode;

    /* loaded from: classes3.dex */
    public enum BackgroundMode {
        BG_IS_NULL,
        BG_IS_COLOR,
        BG_IS_PATTERN,
        BG_IS_IMAGE,
        BG_IS_GRADIENT
    }

    /* loaded from: classes3.dex */
    public enum ShapeMode {
        SP_IS_NULL,
        SP_IS_PATH,
        SP_IS_IMAGE
    }

    public DMSuperImageView(Context context) {
        super(context);
        this.paint = new Paint();
        this.viewWidth = 0;
        this.viewHeight = 0;
        this.viewRatio = 1.0f;
        this.viewRect = new Rect(0, 0, 0, 0);
        this.viewPath = new Path();
        this.viewAlpha = 255;
        this.mColorFilter = null;
        this.mCanFingerScale = true;
        this.bgMode = BackgroundMode.BG_IS_NULL;
        this.backgroundColor = -1;
        this.backgroundPattern = null;
        this.backgroundPatternDrawable = null;
        this.backgroundImage = null;
        this.backgroundImageDstRect = new Rect(0, 0, 0, 0);
        this.backgroundGradientDrawable = null;
        this.image = null;
        this.imgWidth = 0;
        this.imgHeight = 0;
        this.imgRatio = 1.0f;
        this.imgSrcRectWidth = 0;
        this.imgSrcRectHeight = 0;
        this.imgScale = 1.0f;
        this.scaleImgSrcRectWidth = 0;
        this.scaleImgSrcRectHeight = 0;
        this.imgSrcRect = new Rect(0, 0, 0, 0);
        this.imgScrollable = false;
        this.horizintal = false;
        this.vertical = false;
        this.spMode = ShapeMode.SP_IS_NULL;
        this.shapeImage = null;
        this.shapeWidth = 0;
        this.shapeHeight = 0;
        this.shapeRatio = 1.0f;
        this.shapeScale = 1.0f;
        this.shapeSmaller = false;
        this.shapeOuterSolid = true;
        this.shapeDstRect = new Rect(0, 0, 0, 0);
        this.uiPath = null;
        this.shapePath = null;
        this.shapeRegion = null;
        this.inverse = false;
        this.pathEffect = null;
        this.clear1 = new Rect();
        this.clear2 = new Rect();
        this.clearXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        this.dstInXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        this.xorXfermode = new PorterDuffXfermode(PorterDuff.Mode.XOR);
        this.srcInXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        this.touching = false;
        this.drawTouchingFrame = true;
        this.touchingColor = Color.rgb(0, 200, 0);
        this.waiting = false;
        this.mode = 0;
        this.moveCount = 0;
        this.mStart = new PointF();
        this.mMid = new PointF();
        this.mCurPoint = new PointF();
        this.touchDate = new Date();
        this.touchDownTime = 0L;
        this.isMove = false;
        this.paint.setDither(true);
        this.paint.setAntiAlias(true);
        this.paint.setFilterBitmap(true);
        setWillNotDraw(false);
    }

    public DMSuperImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.paint = new Paint();
        this.viewWidth = 0;
        this.viewHeight = 0;
        this.viewRatio = 1.0f;
        this.viewRect = new Rect(0, 0, 0, 0);
        this.viewPath = new Path();
        this.viewAlpha = 255;
        this.mColorFilter = null;
        this.mCanFingerScale = true;
        this.bgMode = BackgroundMode.BG_IS_NULL;
        this.backgroundColor = -1;
        this.backgroundPattern = null;
        this.backgroundPatternDrawable = null;
        this.backgroundImage = null;
        this.backgroundImageDstRect = new Rect(0, 0, 0, 0);
        this.backgroundGradientDrawable = null;
        this.image = null;
        this.imgWidth = 0;
        this.imgHeight = 0;
        this.imgRatio = 1.0f;
        this.imgSrcRectWidth = 0;
        this.imgSrcRectHeight = 0;
        this.imgScale = 1.0f;
        this.scaleImgSrcRectWidth = 0;
        this.scaleImgSrcRectHeight = 0;
        this.imgSrcRect = new Rect(0, 0, 0, 0);
        this.imgScrollable = false;
        this.horizintal = false;
        this.vertical = false;
        this.spMode = ShapeMode.SP_IS_NULL;
        this.shapeImage = null;
        this.shapeWidth = 0;
        this.shapeHeight = 0;
        this.shapeRatio = 1.0f;
        this.shapeScale = 1.0f;
        this.shapeSmaller = false;
        this.shapeOuterSolid = true;
        this.shapeDstRect = new Rect(0, 0, 0, 0);
        this.uiPath = null;
        this.shapePath = null;
        this.shapeRegion = null;
        this.inverse = false;
        this.pathEffect = null;
        this.clear1 = new Rect();
        this.clear2 = new Rect();
        this.clearXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        this.dstInXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        this.xorXfermode = new PorterDuffXfermode(PorterDuff.Mode.XOR);
        this.srcInXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        this.touching = false;
        this.drawTouchingFrame = true;
        this.touchingColor = Color.rgb(0, 200, 0);
        this.waiting = false;
        this.mode = 0;
        this.moveCount = 0;
        this.mStart = new PointF();
        this.mMid = new PointF();
        this.mCurPoint = new PointF();
        this.touchDate = new Date();
        this.touchDownTime = 0L;
        this.isMove = false;
        this.paint.setDither(true);
        this.paint.setAntiAlias(true);
        this.paint.setFilterBitmap(true);
        setWillNotDraw(false);
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.viewWidth = i;
        this.viewHeight = i2;
        if (i2 > 0) {
            this.viewRatio = i / i2;
        }
        this.viewRect.right = i;
        this.viewRect.bottom = i2;
        this.viewPath.reset();
        this.viewPath.addRect(0.0f, 0.0f, i, i2, Path.Direction.CW);
        this.viewPath.close();
        if (this.backgroundImageDstRect.equals(new Rect(0, 0, 0, 0))) {
            this.backgroundImageDstRect = new Rect(0, 0, i, i2);
        }
        measureImageRect();
        measureShapeRect();
        invalidate();
    }

    public void destroy() {
        Bitmap bitmap = this.backgroundPattern;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.backgroundPatternDrawable = null;
            this.backgroundPattern.recycle();
            this.backgroundPattern = null;
        }
        Bitmap bitmap2 = this.backgroundImage;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.backgroundImage.recycle();
            this.backgroundImage = null;
        }
        Bitmap bitmap3 = this.shapeImage;
        if (bitmap3 == null || bitmap3.isRecycled()) {
            return;
        }
        this.shapeImage.recycle();
        this.shapeImage = null;
    }

    public void setViewAlpha(int i) {
        this.viewAlpha = i;
        invalidate();
    }

    public ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mColorFilter = colorFilter;
    }

    public boolean getCanFingerScale() {
        return this.mCanFingerScale;
    }

    public void setCanFingerScale(boolean z) {
        this.mCanFingerScale = z;
    }

    public void setBackgroundMode(BackgroundMode backgroundMode) {
        this.bgMode = backgroundMode;
        Bitmap bitmap = this.backgroundPattern;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.backgroundPattern.recycle();
            this.backgroundPattern = null;
        }
        Bitmap bitmap2 = this.image;
        if (bitmap2 == null || bitmap2.isRecycled()) {
            return;
        }
        this.image.recycle();
        this.image = null;
    }

    public void drawBackgournd(Canvas canvas) {
        GradientDrawable gradientDrawable;
        BitmapDrawable bitmapDrawable;
        if (this.bgMode == BackgroundMode.BG_IS_COLOR) {
            canvas.drawColor(this.backgroundColor);
        } else if (this.bgMode == BackgroundMode.BG_IS_PATTERN) {
            Bitmap bitmap = this.backgroundPattern;
            if (bitmap == null || bitmap.isRecycled() || (bitmapDrawable = this.backgroundPatternDrawable) == null) {
                return;
            }
            bitmapDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            this.backgroundPatternDrawable.draw(canvas);
        } else if (this.bgMode == BackgroundMode.BG_IS_IMAGE) {
            Bitmap bitmap2 = this.backgroundImage;
            if (bitmap2 == null || bitmap2.isRecycled()) {
                return;
            }
            canvas.drawBitmap(this.backgroundImage, (Rect) null, new Rect(0, 0, canvas.getWidth(), canvas.getHeight()), this.paint);
        } else if (this.bgMode != BackgroundMode.BG_IS_GRADIENT || (gradientDrawable = this.backgroundGradientDrawable) == null) {
        } else {
            gradientDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            this.backgroundGradientDrawable.draw(canvas);
        }
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        this.backgroundColor = i;
        setBackgroundMode(BackgroundMode.BG_IS_COLOR);
        invalidate();
    }

    public void setBackgroundPattern(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        setBackgroundMode(BackgroundMode.BG_IS_PATTERN);
        this.backgroundPattern = bitmap;
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), this.backgroundPattern);
        this.backgroundPatternDrawable = bitmapDrawable;
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        this.backgroundPatternDrawable.setDither(true);
        invalidate();
    }

    public void setBackgroundImage(Bitmap bitmap) {
        setBackgroundImage(bitmap, null);
    }

    public void setBackgroundImage(Bitmap bitmap, Rect rect) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        setBackgroundMode(BackgroundMode.BG_IS_IMAGE);
        this.backgroundImage = bitmap;
        if (rect != null && !rect.equals(new Rect(0, 0, 0, 0))) {
            this.backgroundImageDstRect = rect;
        }
        invalidate();
    }

    public Bitmap getBackgroundImage() {
        return this.backgroundImage;
    }

    public void setBackgroundGradient(GradientDrawable gradientDrawable) {
        this.backgroundGradientDrawable = gradientDrawable;
        setBackgroundMode(BackgroundMode.BG_IS_GRADIENT);
        invalidate();
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.image = bitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.imgWidth = this.image.getWidth();
            this.imgHeight = this.image.getHeight();
            measureImageRect();
        }
        invalidate();
    }

    public int getImageWidth() {
        return this.imgWidth;
    }

    private void measureImageRect() {
        int i;
        int i2 = this.imgWidth;
        if (i2 == 0 || (i = this.imgHeight) == 0 || this.viewWidth == 0 || this.viewHeight == 0) {
            return;
        }
        this.imgSrcRectWidth = i2;
        this.imgSrcRectHeight = i;
        this.imgSrcRect.left = 0;
        this.imgSrcRect.top = 0;
        this.imgSrcRect.right = this.imgWidth;
        this.imgSrcRect.bottom = this.imgHeight;
        int i3 = this.imgWidth;
        float f = i3 / this.imgHeight;
        this.imgRatio = f;
        if (f == this.viewRatio) {
            this.imgScale = i3 / this.viewWidth;
        }
        if (this.imgRatio > this.viewRatio) {
            float f2 = this.imgHeight / this.viewHeight;
            this.imgScale = f2;
            int i4 = (int) (this.viewWidth * f2);
            this.imgSrcRectWidth = i4;
            this.imgSrcRect.left = (this.imgWidth - i4) / 2;
            this.imgSrcRect.top = 0;
            Rect rect = this.imgSrcRect;
            rect.right = rect.left + this.imgSrcRectWidth;
            this.imgSrcRect.bottom = this.imgHeight;
        }
        if (this.imgRatio < this.viewRatio) {
            float f3 = this.imgWidth / this.viewWidth;
            this.imgScale = f3;
            this.imgSrcRectHeight = (int) (this.viewHeight * f3);
            this.imgSrcRect.left = 0;
            this.imgSrcRect.top = (this.imgHeight - this.imgSrcRectHeight) / 2;
            this.imgSrcRect.right = this.imgWidth;
            Rect rect2 = this.imgSrcRect;
            rect2.bottom = rect2.top + this.imgSrcRectHeight;
        }
        this.scaleImgSrcRectWidth = this.imgSrcRectWidth;
        this.scaleImgSrcRectHeight = this.imgSrcRectHeight;
    }

    public void setImageBitmapKeepState(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        this.image = bitmap;
        invalidate();
    }

    public void setImageScrollable(boolean z) {
        this.imgScrollable = z;
    }

    public void setImageMirror(boolean z, boolean z2) {
        this.horizintal = z;
        this.vertical = z2;
        invalidate();
    }

    public void setImageMirrorHorizintal() {
        this.horizintal = !this.horizintal;
        invalidate();
    }

    public void setImageMirrorVertical() {
        this.vertical = !this.vertical;
        invalidate();
    }

    public boolean getImageMirrorHorizintal() {
        return this.horizintal;
    }

    public boolean getImageMirrorVertical() {
        return this.vertical;
    }

    public Rect getImageRect() {
        return this.imgSrcRect;
    }

    public void setShapeMode(ShapeMode shapeMode) {
        this.spMode = shapeMode;
        Bitmap bitmap = this.shapeImage;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        this.shapeImage.recycle();
        this.shapeImage = null;
    }

    public void setShapeImage(Bitmap bitmap, boolean z) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        setShapeMode(ShapeMode.SP_IS_IMAGE);
        this.shapeImage = bitmap;
        this.shapeWidth = bitmap.getWidth();
        this.shapeHeight = this.shapeImage.getHeight();
        this.shapeOuterSolid = z;
        measureShapeRect();
        invalidate();
    }

    private void measureShapeImageRect() {
        if (this.shapeWidth == 0 || this.shapeHeight == 0 || this.viewWidth == 0 || this.viewHeight == 0) {
            return;
        }
        this.shapeDstRect.left = 0;
        this.shapeDstRect.top = 0;
        this.shapeDstRect.right = this.viewWidth;
        this.shapeDstRect.bottom = this.viewHeight;
        int i = this.shapeWidth;
        int i2 = this.shapeHeight;
        float f = i / i2;
        this.shapeRatio = f;
        float f2 = this.viewRatio;
        if (f == f2) {
            this.shapeScale = i / this.viewWidth;
        } else if (f > f2) {
            int i3 = this.viewWidth;
            this.shapeScale = i / i3;
            int i4 = (int) ((i2 * i3) / i);
            if (i4 < 1) {
                this.shapeHeight = 1;
            }
            if (Math.abs(i4 - this.viewHeight) < 2) {
                i4 = this.viewHeight;
            }
            this.shapeDstRect.left = 0;
            this.shapeDstRect.top = (this.viewHeight - i4) / 2;
            this.shapeDstRect.right = this.viewWidth;
            Rect rect = this.shapeDstRect;
            rect.bottom = rect.top + i4;
        } else {
            int i5 = this.viewHeight;
            this.shapeScale = i2 / i5;
            int i6 = (int) ((i * i5) / i2);
            if (i6 < 1) {
                i6 = 1;
            }
            if (Math.abs(i6 - this.viewWidth) < 2) {
                i6 = this.viewWidth;
            }
            this.shapeDstRect.left = (this.viewWidth - i6) / 2;
            this.shapeDstRect.top = 0;
            Rect rect2 = this.shapeDstRect;
            rect2.right = rect2.left + i6;
            this.shapeDstRect.bottom = this.viewHeight;
        }
        if (this.shapeDstRect.right < this.viewWidth || this.shapeDstRect.bottom < this.viewHeight) {
            this.shapeSmaller = true;
        }
    }

    public Bitmap getShapeImage() {
        return this.shapeImage;
    }

    public DMUIPath getShapeUIPath() {
        return this.uiPath;
    }

    public void setShapePath(DMUIPath dMUIPath, boolean z) {
        if (dMUIPath == null) {
            return;
        }
        setShapeMode(ShapeMode.SP_IS_PATH);
        this.uiPath = dMUIPath;
        this.shapeOuterSolid = z;
        measureShapeRect();
        invalidate();
    }

    private void measureShapePathRect() {
        DMUIPath dMUIPath = this.uiPath;
        if (dMUIPath == null || this.viewWidth == 0 || this.viewHeight == 0) {
            return;
        }
        this.shapePath = dMUIPath.getPath(this.viewRect);
        RectF rectF = new RectF();
        this.shapePath.computeBounds(rectF, true);
        Region region = new Region();
        this.shapeRegion = region;
        region.setPath(this.shapePath, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
    }

    private void measureShapeRect() {
        if (this.spMode == ShapeMode.SP_IS_IMAGE) {
            measureShapeImageRect();
        } else if (this.spMode == ShapeMode.SP_IS_PATH) {
            measureShapePathRect();
        }
    }

    public void setInverse(Boolean bool) {
        this.inverse = bool;
        invalidate();
    }

    public void setCornerPathEffect(float f) {
        this.pathEffect = new CornerPathEffect(f);
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        BitmapDrawable bitmapDrawable;
        Path path;
        int saveLayer = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null, 31);
        this.paint.setAlpha(this.viewAlpha);
        PathEffect pathEffect = this.pathEffect;
        if (pathEffect != null) {
            this.paint.setPathEffect(pathEffect);
        }
        if (this.spMode == ShapeMode.SP_IS_PATH) {
            if (this.shapePath == null) {
                return;
            }
            this.paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(this.shapePath, this.paint);
            this.paint.setPathEffect(null);
            if ((this.shapeOuterSolid && !this.inverse.booleanValue()) || (!this.shapeOuterSolid && this.inverse.booleanValue())) {
                this.paint.setXfermode(this.xorXfermode);
            } else {
                this.paint.setXfermode(this.srcInXfermode);
            }
        } else if (this.pathEffect != null) {
            this.paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(this.viewPath, this.paint);
            this.paint.setXfermode(this.srcInXfermode);
        }
        if (this.bgMode == BackgroundMode.BG_IS_COLOR) {
            canvas.drawColor(this.backgroundColor);
        } else if (this.bgMode == BackgroundMode.BG_IS_PATTERN) {
            Bitmap bitmap = this.backgroundPattern;
            if (bitmap == null || bitmap.isRecycled() || (bitmapDrawable = this.backgroundPatternDrawable) == null) {
                return;
            }
            bitmapDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            this.backgroundPatternDrawable.draw(canvas);
        } else if (this.bgMode == BackgroundMode.BG_IS_IMAGE) {
            Bitmap bitmap2 = this.backgroundImage;
            if (bitmap2 == null || bitmap2.isRecycled()) {
                return;
            }
            canvas.drawBitmap(this.backgroundImage, (Rect) null, this.backgroundImageDstRect, this.paint);
            saveLayer += canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null, 31);
        } else if (this.bgMode == BackgroundMode.BG_IS_GRADIENT) {
            GradientDrawable gradientDrawable = this.backgroundGradientDrawable;
            if (gradientDrawable == null) {
                return;
            }
            gradientDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            this.backgroundGradientDrawable.draw(canvas);
        }
        Bitmap bitmap3 = this.image;
        if (bitmap3 != null && !bitmap3.isRecycled()) {
            if (this.horizintal) {
                canvas.scale(-1.0f, 1.0f, this.viewWidth / 2.0f, this.viewHeight / 2.0f);
            }
            if (this.vertical) {
                canvas.scale(1.0f, -1.0f, this.viewWidth / 2.0f, this.viewHeight / 2.0f);
            }
            this.paint.setColorFilter(this.mColorFilter);
            canvas.drawBitmap(this.image, this.imgSrcRect, this.viewRect, this.paint);
            this.paint.setColorFilter(null);
            if (this.vertical) {
                canvas.scale(1.0f, -1.0f, this.viewWidth / 2.0f, this.viewHeight / 2.0f);
            }
            if (this.horizintal) {
                canvas.scale(-1.0f, 1.0f, this.viewWidth / 2.0f, this.viewHeight / 2.0f);
            }
        }
        if (this.spMode != ShapeMode.SP_IS_NULL && this.bgMode == BackgroundMode.BG_IS_NULL) {
            Bitmap bitmap4 = this.image;
            if (bitmap4 == null) {
                canvas.drawColor(-1);
            } else if (bitmap4.isRecycled()) {
                canvas.drawColor(-1);
            }
        }
        if (this.spMode == ShapeMode.SP_IS_IMAGE) {
            Bitmap bitmap5 = this.shapeImage;
            if (bitmap5 == null || bitmap5.isRecycled()) {
                return;
            }
            if (this.shapeSmaller && ((this.shapeOuterSolid && this.inverse.booleanValue()) || (!this.shapeOuterSolid && !this.inverse.booleanValue()))) {
                this.paint.setXfermode(this.clearXfermode);
                this.paint.setStyle(Paint.Style.FILL);
                this.clear1.set(this.viewRect);
                this.clear2.set(this.viewRect);
                if (this.viewWidth > this.shapeDstRect.right) {
                    this.clear1.right = this.shapeDstRect.left;
                    this.clear2.left = this.shapeDstRect.right;
                } else {
                    this.clear1.bottom = this.shapeDstRect.top;
                    this.clear2.top = this.shapeDstRect.bottom;
                }
                canvas.drawRect(this.clear1, this.paint);
                canvas.drawRect(this.clear2, this.paint);
            }
            if (this.inverse.booleanValue() || this.shapeOuterSolid) {
                this.paint.setXfermode(this.xorXfermode);
            } else {
                this.paint.setXfermode(this.dstInXfermode);
            }
            canvas.drawBitmap(this.shapeImage, (Rect) null, this.shapeDstRect, this.paint);
        }
        this.paint.setXfermode(null);
        if (this.touching && this.drawTouchingFrame) {
            PathEffect pathEffect2 = this.pathEffect;
            if (pathEffect2 != null) {
                this.paint.setPathEffect(pathEffect2);
            }
            this.paint.setColor(this.touchingColor);
            this.paint.setStyle(Paint.Style.STROKE);
            this.paint.setStrokeWidth(2.0f);
            if (this.spMode == ShapeMode.SP_IS_PATH && (path = this.shapePath) != null) {
                canvas.drawPath(path, this.paint);
            } else {
                canvas.drawPath(this.viewPath, this.paint);
            }
            this.paint.setStrokeWidth(1.0f);
            this.paint.setColor(this.backgroundColor);
        }
        if (this.waiting) {
            canvas.drawColor(Color.argb(100, 255, 255, 255), PorterDuff.Mode.LIGHTEN);
        }
        this.paint.setPathEffect(null);
        canvas.restoreToCount(saveLayer);
    }

    public void drawForShapeImage(Canvas canvas, Rect rect) {
        BitmapDrawable bitmapDrawable;
        int saveLayer = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null, 31);
        this.paint.setAlpha(this.viewAlpha);
        if (this.bgMode == BackgroundMode.BG_IS_COLOR) {
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setColor(this.backgroundColor);
            canvas.drawRect(rect, this.paint);
        } else if (this.bgMode == BackgroundMode.BG_IS_PATTERN) {
            Bitmap bitmap = this.backgroundPattern;
            if (bitmap == null || bitmap.isRecycled() || (bitmapDrawable = this.backgroundPatternDrawable) == null) {
                return;
            }
            bitmapDrawable.setBounds(rect);
            this.backgroundPatternDrawable.draw(canvas);
        } else if (this.bgMode == BackgroundMode.BG_IS_IMAGE) {
            Bitmap bitmap2 = this.backgroundImage;
            if (bitmap2 == null || bitmap2.isRecycled()) {
                return;
            }
            canvas.drawBitmap(this.backgroundImage, (Rect) null, this.backgroundImageDstRect, this.paint);
            saveLayer += canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null, 31);
        } else if (this.bgMode == BackgroundMode.BG_IS_GRADIENT) {
            GradientDrawable gradientDrawable = this.backgroundGradientDrawable;
            if (gradientDrawable == null) {
                return;
            }
            gradientDrawable.setBounds(rect);
            this.backgroundGradientDrawable.draw(canvas);
        }
        int i = rect.right - rect.left;
        int i2 = rect.bottom - rect.top;
        float f = i;
        float f2 = i2;
        float f3 = f / f2;
        Bitmap bitmap3 = this.image;
        if (bitmap3 != null && !bitmap3.isRecycled()) {
            PointF pointF = new PointF();
            pointF.x = rect.left + (f / 2.0f);
            pointF.y = rect.top + (f2 / 2.0f);
            if (this.horizintal) {
                canvas.scale(-1.0f, 1.0f, pointF.x, pointF.y);
            }
            if (this.vertical) {
                canvas.scale(1.0f, -1.0f, pointF.x, pointF.y);
            }
            canvas.drawBitmap(this.image, this.imgSrcRect, rect, this.paint);
            if (this.vertical) {
                canvas.scale(1.0f, -1.0f, pointF.x, pointF.y);
            }
            if (this.horizintal) {
                canvas.scale(-1.0f, 1.0f, pointF.x, pointF.y);
            }
        }
        if (this.spMode != ShapeMode.SP_IS_NULL && this.bgMode == BackgroundMode.BG_IS_NULL) {
            Bitmap bitmap4 = this.image;
            if (bitmap4 == null) {
                this.paint.setStyle(Paint.Style.FILL);
                this.paint.setColor(-1);
                canvas.drawRect(rect, this.paint);
            } else if (bitmap4.isRecycled()) {
                this.paint.setStyle(Paint.Style.FILL);
                this.paint.setColor(-1);
                canvas.drawRect(rect, this.paint);
            }
        }
        if (this.spMode == ShapeMode.SP_IS_IMAGE) {
            Bitmap bitmap5 = this.shapeImage;
            if (bitmap5 == null || bitmap5.isRecycled()) {
                return;
            }
            Rect rect2 = new Rect();
            float f4 = this.shapeRatio;
            if (f4 == f3) {
                rect2 = rect;
            } else if (f4 > f3) {
                int i3 = (int) ((this.shapeHeight * f) / this.shapeWidth);
                if (i3 < 1) {
                    this.shapeHeight = 1;
                }
                if (Math.abs(i3 - i2) < 2) {
                    i3 = i2;
                }
                rect2.left = rect.left - 1;
                rect2.top = (((i2 - i3) / 2) + rect.top) - 1;
                rect2.right = rect2.left + i + 2;
                rect2.bottom = rect2.top + i3 + 2;
            } else {
                int i4 = (int) ((this.shapeWidth * f2) / this.shapeHeight);
                if (i4 < 1) {
                    i4 = 1;
                }
                if (Math.abs(i4 - i) < 2) {
                    i4 = i;
                }
                rect2.left = (((i - i4) / 2) + rect.left) - 1;
                rect2.top = rect.top - 1;
                rect2.right = rect2.left + i4 + 2;
                rect2.bottom = rect2.top + i2 + 2;
            }
            if (this.shapeSmaller && ((this.shapeOuterSolid && this.inverse.booleanValue()) || (!this.shapeOuterSolid && !this.inverse.booleanValue()))) {
                this.paint.setXfermode(this.clearXfermode);
                this.clear1.set(rect);
                this.clear2.set(rect);
                if (i > rect2.right) {
                    this.clear1.right = rect2.left;
                    this.clear2.left = rect2.right;
                } else {
                    this.clear1.bottom = rect2.top;
                    this.clear2.top = rect2.bottom;
                }
                canvas.drawRect(this.clear1, this.paint);
                canvas.drawRect(this.clear2, this.paint);
            }
            if (this.inverse.booleanValue() || this.shapeOuterSolid) {
                this.paint.setXfermode(this.xorXfermode);
            } else {
                this.paint.setXfermode(this.dstInXfermode);
            }
            canvas.drawBitmap(this.shapeImage, (Rect) null, rect2, this.paint);
        }
        this.paint.setXfermode(null);
        this.paint.setPathEffect(null);
        canvas.restoreToCount(saveLayer);
    }

    public void setViewTouchedListener(OnDMSuperImageViewTouchedListener onDMSuperImageViewTouchedListener) {
        this.touchedListener = onDMSuperImageViewTouchedListener;
    }

    public void setTouchingState(boolean z) {
        this.touching = z;
        invalidate();
    }

    public boolean getTouchingState() {
        return this.touching;
    }

    public void setDrawTouchingFrame(boolean z) {
        this.drawTouchingFrame = z;
    }

    public boolean getDrawTouchingFrame() {
        return this.drawTouchingFrame;
    }

    public void setTouchingColor(int i) {
        this.touchingColor = i;
    }

    public void setWaitingState(boolean z) {
        this.waiting = z;
        if (z) {
            this.touching = false;
        } else {
            this.touching = true;
        }
        invalidate();
    }

    public void setTransformedListener(OnDMImageTransformChangedListener onDMImageTransformChangedListener) {
        this.transformListener = onDMImageTransformChangedListener;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Bitmap bitmap = this.image;
        if (bitmap == null || !this.imgScrollable || bitmap.isRecycled()) {
            return false;
        }
        this.mCurPoint.set(motionEvent.getX(), motionEvent.getY());
        if (this.spMode == ShapeMode.SP_IS_IMAGE && this.mode == 0) {
            Bitmap bitmap2 = this.shapeImage;
            if (bitmap2 != null && !bitmap2.isRecycled()) {
                if (this.shapeDstRect.contains((int) this.mCurPoint.x, (int) this.mCurPoint.y)) {
                    try {
                        int i = (int) ((this.mCurPoint.x - this.shapeDstRect.left) * this.shapeScale);
                        int i2 = (int) ((this.mCurPoint.y - this.shapeDstRect.top) * this.shapeScale);
                        if (i < 0) {
                            i = 0;
                        }
                        if (i2 < 0) {
                            i2 = 0;
                        }
                        int pixel = this.shapeImage.getPixel(i, i2);
                        if ((pixel == 0 && !this.inverse.booleanValue()) || (pixel != 0 && this.inverse.booleanValue())) {
                            setTouchingState(false);
                            return false;
                        }
                    } catch (Exception e) {
                        System.out.println("error:" + e.getMessage());
                    }
                } else if (this.shapeSmaller && ((this.shapeOuterSolid && this.inverse.booleanValue()) || (!this.shapeOuterSolid && !this.inverse.booleanValue()))) {
                    setTouchingState(false);
                    return false;
                }
            }
        } else if (this.spMode == ShapeMode.SP_IS_PATH && this.mode == 0) {
            if (this.shapeRegion.contains((int) this.mCurPoint.x, (int) this.mCurPoint.y)) {
                if ((this.shapeOuterSolid && !this.inverse.booleanValue()) || (!this.shapeOuterSolid && this.inverse.booleanValue())) {
                    setTouchingState(false);
                    return false;
                }
            } else if ((this.shapeOuterSolid && this.inverse.booleanValue()) || (!this.shapeOuterSolid && !this.inverse.booleanValue())) {
                setTouchingState(false);
                return false;
            }
        }
        OnDMSuperImageViewTouchedListener onDMSuperImageViewTouchedListener = this.touchedListener;
        if (onDMSuperImageViewTouchedListener != null) {
            onDMSuperImageViewTouchedListener.onTouching(false, this);
        }
        try {
            int action = motionEvent.getAction() & 255;
            if (action == 0) {
                this.mode = 1;
                this.touchDownTime = System.currentTimeMillis();
                this.mStart.set(this.mCurPoint.x, this.mCurPoint.y);
                this.isMove = false;
            } else if (action == 1) {
                this.mode = 0;
                if (System.currentTimeMillis() - this.touchDownTime < 200 && this.touchedListener != null) {
                    this.touchedListener.onTouching(true, this);
                }
                if (!this.isMove) {
                    if (!this.touching) {
                        setTouchingState(true);
                    } else {
                        setTouchingState(false);
                    }
                }
                this.isMove = false;
            } else if (action == 2) {
                float f = this.mCurPoint.x - this.mStart.x;
                float f2 = this.mCurPoint.y - this.mStart.y;
                if (this.mode == 1) {
                    if (this.transformListener != null) {
                        this.transformListener.translatePost(f, f2);
                    } else {
                        postTranslate(f, f2);
                    }
                    this.mStart.set(this.mCurPoint.x, this.mCurPoint.y);
                }
                if (this.mode == 2) {
                    this.mode = 1;
                    this.mStart.set(this.mCurPoint.x, this.mCurPoint.y);
                }
                if (this.mode == 3) {
                    this.moveCount++;
                    float spacing = (float) spacing(motionEvent);
                    if (this.mCanFingerScale) {
                        midPoint(this.mMid, motionEvent);
                        if (this.moveCount > 10) {
                            float f3 = spacing / this.oldDist;
                            if (this.transformListener != null) {
                                this.transformListener.scalePost(f3);
                                this.transformListener.scalePost(f3, this.mMid, this);
                            } else {
                                postScale(f3);
                            }
                        }
                    }
                    this.oldDist = spacing;
                }
                if (Math.abs(f) > 10.0f || Math.abs(f2) > 10.0f) {
                    this.isMove = true;
                }
            } else if (action != 5) {
                if (action == 6) {
                    this.mode = 2;
                }
            } else if (motionEvent.getActionIndex() >= 1) {
                float spacing2 = (float) spacing(motionEvent);
                this.oldDist = spacing2;
                if (spacing2 > 10.0f) {
                    this.mode = 3;
                    this.moveCount = 0;
                }
                if (this.mCanFingerScale) {
                    midPoint(this.mMid, motionEvent);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
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

    public void postTranslate(float f, float f2) {
        if (this.horizintal) {
            f = -f;
        }
        if (this.vertical) {
            f2 = -f2;
        }
        Rect rect = this.imgSrcRect;
        rect.left = (int) (rect.left - (f * this.imgScale));
        Rect rect2 = this.imgSrcRect;
        rect2.top = (int) (rect2.top - (f2 * this.imgScale));
        fitImageRect();
        invalidate();
    }

    public void postScale(float f) {
        if (this.mMid.x == 0.0f && this.mMid.y == 0.0f) {
            this.mMid.x = this.viewWidth / 2;
            this.mMid.y = this.viewHeight / 2;
        }
        float f2 = (this.mMid.x * this.imgScale) + this.imgSrcRect.left;
        float f3 = (this.mMid.y * this.imgScale) + this.imgSrcRect.top;
        int i = (int) (this.scaleImgSrcRectHeight / f);
        int i2 = (int) (i * this.viewRatio);
        this.scaleImgSrcRectWidth = i2;
        this.scaleImgSrcRectHeight = i;
        if (i2 > this.imgSrcRectWidth || i > this.imgSrcRectHeight) {
            this.scaleImgSrcRectWidth = this.imgSrcRectWidth;
            this.scaleImgSrcRectHeight = this.imgSrcRectHeight;
        }
        float f4 = this.imgRatio;
        float f5 = this.viewRatio;
        if (f4 == f5) {
            this.imgScale = this.scaleImgSrcRectWidth / this.viewWidth;
        } else if (f4 > f5) {
            this.imgScale = this.scaleImgSrcRectHeight / this.viewHeight;
        } else if (f4 < f5) {
            this.imgScale = this.scaleImgSrcRectWidth / this.viewWidth;
        }
        this.imgSrcRect.left = (int) (f2 - (this.mMid.x * this.imgScale));
        this.imgSrcRect.top = (int) (f3 - (this.mMid.y * this.imgScale));
        fitImageRect();
        invalidate();
    }

    public void postScale(float f, PointF pointF, View view) {
        this.mMid.x = (view.getWidth() / getWidth()) * pointF.x;
        this.mMid.y = (view.getHeight() / getHeight()) * pointF.y;
        if (this.mMid.x == 0.0f && this.mMid.y == 0.0f) {
            this.mMid.x = this.viewWidth / 2;
            this.mMid.y = this.viewHeight / 2;
        }
        float f2 = (this.mMid.x * this.imgScale) + this.imgSrcRect.left;
        float f3 = (this.mMid.y * this.imgScale) + this.imgSrcRect.top;
        int i = (int) (this.scaleImgSrcRectHeight / f);
        int i2 = (int) (i * this.viewRatio);
        this.scaleImgSrcRectWidth = i2;
        this.scaleImgSrcRectHeight = i;
        if (i2 > this.imgSrcRectWidth || i > this.imgSrcRectHeight) {
            this.scaleImgSrcRectWidth = this.imgSrcRectWidth;
            this.scaleImgSrcRectHeight = this.imgSrcRectHeight;
        }
        float f4 = this.imgRatio;
        float f5 = this.viewRatio;
        if (f4 == f5) {
            this.imgScale = this.scaleImgSrcRectWidth / this.viewWidth;
        } else if (f4 > f5) {
            this.imgScale = this.scaleImgSrcRectHeight / this.viewHeight;
        } else if (f4 < f5) {
            this.imgScale = this.scaleImgSrcRectWidth / this.viewWidth;
        }
        this.imgSrcRect.left = (int) (f2 - (this.mMid.x * this.imgScale));
        this.imgSrcRect.top = (int) (f3 - (this.mMid.y * this.imgScale));
        fitImageRect();
        invalidate();
    }

    private void fitImageRect() {
        if (this.imgSrcRect.left < 0) {
            this.imgSrcRect.left = 0;
        }
        if (this.imgSrcRect.top < 0) {
            this.imgSrcRect.top = 0;
        }
        Rect rect = this.imgSrcRect;
        rect.right = rect.left + this.scaleImgSrcRectWidth;
        Rect rect2 = this.imgSrcRect;
        rect2.bottom = rect2.top + this.scaleImgSrcRectHeight;
        int i = this.imgSrcRect.right;
        int i2 = this.imgWidth;
        if (i > i2) {
            this.imgSrcRect.right = i2;
            this.imgSrcRect.left = this.imgWidth - this.scaleImgSrcRectWidth;
        }
        int i3 = this.imgSrcRect.bottom;
        int i4 = this.imgHeight;
        if (i3 > i4) {
            this.imgSrcRect.bottom = i4;
            this.imgSrcRect.top = this.imgHeight - this.scaleImgSrcRectHeight;
        }
    }
}

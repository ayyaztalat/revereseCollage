package com.picspool.libfuncview.effect.blend;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class CSBlendImageView extends ImageView {
    public static final int BLEND_MODE = 2;
    public static final int RUBBER_MODE = 1;
    public static final int STATE_RECOVERY = 2;
    public static final int STATE_RUBBER = 1;
    private Bitmap canvasBitmap;
    private Paint coverPaint;
    private Bitmap currentCoverBitmap;
    private int currentMode;
    private int currentPaintWidth;
    private Bitmap currentRubberBitmap;
    private float currentX;
    private float currentY;
    private Canvas duffCanvas;
    private Matrix first;
    private boolean isRubberModeFirstDraw;
    private Matrix mCurrentMatrix;
    private GestureDetector mGestureDetector;
    private Matrix mMatrix;
    private ImageView magnifierImageView;
    private Bitmap mask;
    private PorterDuff.Mode mode;
    int newWidth;
    int newheight;
    Paint paint;
    private int paintStrokeWidth;
    Paint recoveryPaint;
    private Map<Integer, Path> recoveryPaths;
    RectF rectF;
    private Canvas rubberCanvas;
    private Bitmap rubberCanvasBitmap;
    Paint rubberPaint;
    private Map<Integer, Path> rubberPaths;
    private Bitmap srcBitmap;
    private boolean srcHasSet;
    private ImageView srcImageView;
    public int state;
    Paint viewCanvasPaint;

    public void setFirstMatrix(Matrix matrix) {
        Matrix matrix2 = new Matrix();
        matrix.invert(matrix2);
        this.first.set(matrix2);
    }

    public CSBlendImageView(Context context) {
        super(context);
        this.currentMode = 2;
        this.mode = PorterDuff.Mode.SRC_OVER;
        this.paint = new Paint();
        this.rectF = new RectF();
        this.viewCanvasPaint = new Paint();
        this.currentX = -1.0f;
        this.currentY = -1.0f;
        this.isRubberModeFirstDraw = true;
        this.rubberPaths = new HashMap();
        this.recoveryPaths = new HashMap();
        this.currentPaintWidth = 80;
        this.rubberPaint = new Paint();
        this.recoveryPaint = new Paint();
        this.mMatrix = new Matrix();
        this.mCurrentMatrix = new Matrix();
        this.coverPaint = new Paint();
        this.first = new Matrix();
        this.paintStrokeWidth = 80;
        this.srcHasSet = false;
        this.newWidth = 0;
        this.newheight = 0;
        this.state = 1;
        initWork();
    }

    public CSBlendImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.currentMode = 2;
        this.mode = PorterDuff.Mode.SRC_OVER;
        this.paint = new Paint();
        this.rectF = new RectF();
        this.viewCanvasPaint = new Paint();
        this.currentX = -1.0f;
        this.currentY = -1.0f;
        this.isRubberModeFirstDraw = true;
        this.rubberPaths = new HashMap();
        this.recoveryPaths = new HashMap();
        this.currentPaintWidth = 80;
        this.rubberPaint = new Paint();
        this.recoveryPaint = new Paint();
        this.mMatrix = new Matrix();
        this.mCurrentMatrix = new Matrix();
        this.coverPaint = new Paint();
        this.first = new Matrix();
        this.paintStrokeWidth = 80;
        this.srcHasSet = false;
        this.newWidth = 0;
        this.newheight = 0;
        this.state = 1;
        initWork();
    }

    public CSBlendImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.currentMode = 2;
        this.mode = PorterDuff.Mode.SRC_OVER;
        this.paint = new Paint();
        this.rectF = new RectF();
        this.viewCanvasPaint = new Paint();
        this.currentX = -1.0f;
        this.currentY = -1.0f;
        this.isRubberModeFirstDraw = true;
        this.rubberPaths = new HashMap();
        this.recoveryPaths = new HashMap();
        this.currentPaintWidth = 80;
        this.rubberPaint = new Paint();
        this.recoveryPaint = new Paint();
        this.mMatrix = new Matrix();
        this.mCurrentMatrix = new Matrix();
        this.coverPaint = new Paint();
        this.first = new Matrix();
        this.paintStrokeWidth = 80;
        this.srcHasSet = false;
        this.newWidth = 0;
        this.newheight = 0;
        this.state = 1;
        initWork();
    }

    private void initWork() {
        setScaleType(ScaleType.CENTER);
        this.paint.setAntiAlias(true);
        this.paint.setDither(true);
        this.paint.setFilterBitmap(true);
        this.paint.setXfermode(new PorterDuffXfermode(this.mode));
        MatrixTouchListener matrixTouchListener = new MatrixTouchListener();
        setOnTouchListener(matrixTouchListener);
        this.mGestureDetector = new GestureDetector(getContext(), new GestureListener(matrixTouchListener));
        setScaleType(ScaleType.FIT_CENTER);
        this.rubberPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.rubberPaint.setAntiAlias(true);
        this.rubberPaint.setStyle(Paint.Style.STROKE);
        this.rubberPaint.setStrokeJoin(Paint.Join.ROUND);
        this.rubberPaint.setStrokeCap(Paint.Cap.ROUND);
        this.rubberPaint.setPathEffect(new CornerPathEffect(20.0f));
        this.rubberPaint.setStrokeWidth(this.paintStrokeWidth);
        this.rubberPaint.setMaskFilter(new BlurMaskFilter(15.0f, BlurMaskFilter.Blur.NORMAL));
        this.recoveryPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        this.coverPaint.setAntiAlias(true);
        this.coverPaint.setStyle(Paint.Style.STROKE);
        this.coverPaint.setStrokeJoin(Paint.Join.ROUND);
        this.coverPaint.setStrokeCap(Paint.Cap.ROUND);
        this.coverPaint.setPathEffect(new CornerPathEffect(20.0f));
        this.coverPaint.setStrokeWidth(this.paintStrokeWidth);
        this.coverPaint.setMaskFilter(new BlurMaskFilter(15.0f, BlurMaskFilter.Blur.NORMAL));
        initPaths();
    }

    private void initPaths() {
        this.recoveryPaths.clear();
        this.rubberPaths.clear();
        this.rubberPaths.put(Integer.valueOf(this.currentPaintWidth), new Path());
        this.recoveryPaths.put(Integer.valueOf(this.currentPaintWidth), new Path());
    }

    public void setBlendBitmaps(Bitmap bitmap) {
        Bitmap bitmap2 = this.mask;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.mask.recycle();
        }
        this.mask = bitmap;
        invalidate();
    }

    public void setBlendMode(BlendMode blendMode) {
        this.mode = blendMode.mode;
        PorterDuffXfermode softOrHardLightMode = (blendMode.equals(BlendMode.Softlight) || blendMode.equals(BlendMode.Hardlight)) ? setSoftOrHardLightMode(blendMode) : null;
        if (softOrHardLightMode == null) {
            softOrHardLightMode = new PorterDuffXfermode(this.mode);
        }
        this.paint.setXfermode(softOrHardLightMode);
        invalidate();
    }

    private PorterDuffXfermode setSoftOrHardLightMode(BlendMode blendMode) {
        try {
            Field declaredField = getDeclaredField(this.mode, "nativeInt");
            if (declaredField != null) {
                Field declaredField2 = Field.class.getDeclaredField("accessFlags");
                declaredField2.setAccessible(true);
                declaredField2.setInt(declaredField, declaredField.getModifiers() & (-17));
                declaredField.setAccessible(true);
                if (blendMode.equals(BlendMode.Softlight)) {
                    declaredField.set(this.mode, 21);
                } else {
                    declaredField.set(this.mode, 20);
                }
                PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(this.mode);
                Field declaredField3 = getDeclaredField(porterDuffXfermode, "mode");
                if (declaredField3 != null) {
                    declaredField3.setAccessible(true);
                    declaredField3.set(porterDuffXfermode, this.mode);
                }
                return porterDuffXfermode;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setPaintAlpha(int i) {
        Paint paint = this.paint;
        if (paint != null) {
            paint.setAlpha(i);
            invalidate();
        }
    }

    public void setSrcImageView(ImageView imageView) {
        this.srcImageView = imageView;
        Bitmap bitmap = this.srcBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.srcBitmap.recycle();
        }
        this.srcBitmap = drawableToBitmap(imageView.getDrawable());
        this.srcHasSet = true;
    }

    public void setSrcImageView(Bitmap bitmap) {
        Bitmap bitmap2 = this.srcBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.srcBitmap.recycle();
        }
        this.srcBitmap = bitmap;
        this.srcHasSet = true;
    }

    public void setMagnifierImageView(ImageView imageView) {
        this.magnifierImageView = imageView;
    }

    public void setWidthAndHeight(int i, int i2) {
        this.newWidth = i;
        this.newheight = i2;
//        Log.e(Progress.TAG, this.newWidth + "   " + this.newheight);
        this.rectF.set(0.0f, 0.0f, (float) i, (float) i2);
        requestLayout();
        invalidate();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        int i4 = this.newWidth;
        if (i4 == 0 || (i3 = this.newheight) == 0) {
            super.onMeasure(i, i2);
        } else {
            setMeasuredDimension(i4, i3);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.srcHasSet) {
            onPreDraw();
            int i = this.currentMode;
            if (i == 2) {
                onBlendDraw2(canvas);
            } else if (i == 1) {
                onRubberDraw(canvas);
            }
        }
    }

    private void onPreDraw() {
        if (this.srcBitmap != null) {
            return;
        }
        this.srcHasSet = false;
        throw new RuntimeException("srcImageView can not be null,setSrcImageView can install srcImageView");
    }

    private void onBlendDraw(Canvas canvas) {
        Bitmap bitmap = this.canvasBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.canvasBitmap.recycle();
            this.canvasBitmap = null;
        }
        this.canvasBitmap = Bitmap.createBitmap(this.newWidth, this.newheight, Bitmap.Config.ARGB_8888);
        this.duffCanvas = new Canvas(this.canvasBitmap);
        canvas.drawBitmap(this.canvasBitmap, 0.0f, 0.0f, this.viewCanvasPaint);
        Bitmap createBitmap = Bitmap.createBitmap(this.newWidth, this.newheight, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(createBitmap);
        Bitmap bitmap2 = this.srcBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            canvas2.drawBitmap(this.srcBitmap, (Rect) null, this.rectF, this.viewCanvasPaint);
        }
        if (this.mask != null) {
            if (isInitialMatrix()) {
                this.mCurrentMatrix.setRectToRect(new RectF(0.0f, 0.0f, this.mask.getWidth(), this.mask.getHeight()), new RectF(0.0f, 0.0f, this.newWidth, this.newheight), Matrix.ScaleToFit.FILL);
            }
            canvas2.drawBitmap(this.mask, this.mCurrentMatrix, this.paint);
        }
        Bitmap bitmap3 = this.srcBitmap;
        if (bitmap3 != null && !bitmap3.isRecycled()) {
            this.duffCanvas.drawBitmap(this.srcBitmap, (Rect) null, this.rectF, this.viewCanvasPaint);
        }
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        this.duffCanvas.drawBitmap(createBitmap, 0.0f, 0.0f, paint);
    }

    private void onBlendDraw2(Canvas canvas) {
        Bitmap bitmap = this.canvasBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.canvasBitmap.recycle();
            this.canvasBitmap = null;
        }
        this.canvasBitmap = Bitmap.createBitmap(this.newWidth, this.newheight, Bitmap.Config.ARGB_8888);
        this.duffCanvas = new Canvas(this.canvasBitmap);
        canvas.drawBitmap(this.canvasBitmap, 0.0f, 0.0f, this.viewCanvasPaint);
        new Canvas(Bitmap.createBitmap(this.newWidth, this.newheight, Bitmap.Config.ARGB_8888));
        Bitmap bitmap2 = this.srcBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.duffCanvas.drawBitmap(this.srcBitmap, (Rect) null, this.rectF, this.viewCanvasPaint);
        }
        if (this.mask != null) {
            if (isInitialMatrix()) {
                this.mCurrentMatrix.setRectToRect(new RectF(0.0f, 0.0f, this.mask.getWidth(), this.mask.getHeight()), new RectF(0.0f, 0.0f, this.newWidth, this.newheight), Matrix.ScaleToFit.FILL);
            }
            this.duffCanvas.drawBitmap(this.mask, this.mCurrentMatrix, this.paint);
        }
    }

    public void dispose() {
        this.srcHasSet = false;
        if (this.srcImageView != null) {
            this.srcImageView = null;
        }
        if (this.mask != null) {
            this.mask = null;
        }
        Bitmap bitmap = this.srcBitmap;
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                this.srcBitmap.recycle();
            }
            this.srcBitmap = null;
        }
        setOnTouchListener(null);
        this.paint.reset();
        this.mode = PorterDuff.Mode.SRC_OVER;
        resetRubberModeState();
    }

    public void resetRubberModeState() {
        resetPath();
        this.isRubberModeFirstDraw = true;
        Bitmap bitmap = this.currentRubberBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.currentRubberBitmap.recycle();
            this.currentRubberBitmap = null;
        }
        Bitmap bitmap2 = this.currentCoverBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.currentCoverBitmap.recycle();
            this.currentCoverBitmap = null;
        }
        setState(1);
        invalidate();
    }

    public void resetPath() {
        initPaths();
    }

    private void onRubberDraw(Canvas canvas) {
        if (this.isRubberModeFirstDraw) {
            this.isRubberModeFirstDraw = false;
            setMagnifierViewWH();
        }
        Bitmap bitmap = this.rubberCanvasBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.rubberCanvasBitmap.recycle();
        }
        this.rubberCanvasBitmap = Bitmap.createBitmap(this.newWidth, this.newheight, Bitmap.Config.ARGB_8888);
        this.rubberCanvas = new Canvas(this.rubberCanvasBitmap);
        canvas.drawBitmap(this.rubberCanvasBitmap, 0.0f, 0.0f, this.viewCanvasPaint);
        Bitmap bitmap2 = this.srcBitmap;
        if (bitmap2 != null) {
            this.rubberCanvas.drawBitmap(bitmap2, (Rect) null, this.rectF, (Paint) null);
        }
        if (this.state == 1) {
            onRubberStateDraw();
        }
        if (this.state == 2) {
            onRecoveryDraw();
        }
    }

    private void onRubberStateDraw() {
        Bitmap createBitmap = Bitmap.createBitmap(this.newWidth, this.newheight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        if (this.currentCoverBitmap == null) {
            canvas.drawBitmap(this.canvasBitmap, 0.0f, 0.0f, this.viewCanvasPaint);
        }
        if (this.currentX != -1.0f && this.currentY != -1.0f) {
            Bitmap bitmap = this.currentCoverBitmap;
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.viewCanvasPaint);
            }
            for (Integer num : this.rubberPaths.keySet()) {
                this.rubberPaint.setStrokeWidth(num.intValue());
                canvas.drawPath(this.rubberPaths.get(num), this.rubberPaint);
            }
        }
        this.rubberCanvas.drawBitmap(createBitmap, 0.0f, 0.0f, this.viewCanvasPaint);
        Bitmap bitmap2 = this.currentRubberBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.currentRubberBitmap.recycle();
        }
        this.currentRubberBitmap = createBitmap;
    }

    private void onRecoveryDraw() {
        Bitmap createBitmap = Bitmap.createBitmap(this.newWidth, this.newheight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Bitmap bitmap = this.currentRubberBitmap;
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.viewCanvasPaint);
        }
        Bitmap createBitmap2 = Bitmap.createBitmap(this.newWidth, this.newheight, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(createBitmap2);
        for (Integer num : this.recoveryPaths.keySet()) {
            this.coverPaint.setStrokeWidth(num.intValue());
            canvas2.drawPath(this.recoveryPaths.get(num), this.coverPaint);
        }
        canvas2.drawBitmap(this.canvasBitmap, 0.0f, 0.0f, this.recoveryPaint);
        canvas.drawBitmap(createBitmap2, 0.0f, 0.0f, this.viewCanvasPaint);
        this.rubberCanvas.drawBitmap(createBitmap, 0.0f, 0.0f, this.viewCanvasPaint);
        Bitmap bitmap2 = this.currentCoverBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.currentCoverBitmap.recycle();
        }
        this.currentCoverBitmap = createBitmap;
    }

    private void setMagnifierViewWH() {
        ImageView imageView = this.magnifierImageView;
        if (imageView != null) {
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = getWidth() / 3;
            layoutParams.height = getWidth() / 3;
            this.magnifierImageView.setLayoutParams(layoutParams);
        }
    }

    private boolean isInitialMatrix() {
        return this.mMatrix.equals(this.mCurrentMatrix);
    }

    public void setCurrentMode(int i) {
        this.currentMode = i;
        if (i == 2) {
            setOnTouchListener(new MatrixTouchListener());
        } else if (i == 1) {
            setOnTouchListener(new RubberTouchListener());
        }
        invalidate();
    }

    public int getCurrentMode() {
        return this.currentMode;
    }

    public int getState() {
        return this.state;
    }

    public Bitmap getCanvasBitmap() {
        try {
            return Bitmap.createScaledBitmap(this.canvasBitmap, this.srcBitmap.getWidth(), this.srcBitmap.getHeight(), false);
        } catch (Exception unused) {
            return null;
        }
    }

    public Bitmap getRubberCanvasBitmap() {
        Bitmap bitmap = this.rubberCanvasBitmap;
        if (bitmap != null) {
            try {
                return Bitmap.createScaledBitmap(bitmap, this.srcBitmap.getWidth(), this.srcBitmap.getHeight(), false);
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public void setPaintStrokeWidth(int i) {
        if (i <= 0) {
            return;
        }
        this.currentPaintWidth = i;
        if (this.rubberPaths.get(Integer.valueOf(i)) == null) {
            this.rubberPaths.put(Integer.valueOf(this.currentPaintWidth), new Path());
        }
        if (this.recoveryPaths.get(Integer.valueOf(this.currentPaintWidth)) == null) {
            this.recoveryPaths.put(Integer.valueOf(this.currentPaintWidth), new Path());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class MatrixTouchListener implements OnTouchListener {
        private static final int MODE_DRAG = 1;
        private static final int MODE_UNABLE = 3;
        private static final int MODE_ZOOM = 2;
        float mDobleClickScale;
        private int mMode;
        private float mStartDis;
        private PointF startPoint;

        private MatrixTouchListener() {
            this.mDobleClickScale = 2.0f;
            this.mMode = 0;
            this.startPoint = new PointF();
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                this.mMode = 1;
                this.startPoint.set(motionEvent.getX(), motionEvent.getY());
                isMatrixEnable();
            } else if (actionMasked == 2) {
                int i = this.mMode;
                if (i == 2) {
                    setZoomMatrix(motionEvent);
                } else if (i == 1) {
                    setDragMatrix(motionEvent);
                }
            } else if (actionMasked == 5) {
                if (this.mMode == 3) {
                    return true;
                }
                this.mMode = 2;
                this.mStartDis = distance(motionEvent);
            }
            return CSBlendImageView.this.mGestureDetector.onTouchEvent(motionEvent);
        }

        private void setDragMatrix(MotionEvent motionEvent) {
            float x = motionEvent.getX() - this.startPoint.x;
            float y = motionEvent.getY() - this.startPoint.y;
            if (Math.sqrt((x * x) + (y * y)) > 10.0d) {
                this.startPoint.set(motionEvent.getX(), motionEvent.getY());
                CSBlendImageView.this.mCurrentMatrix.getValues(new float[9]);
                CSBlendImageView.this.mCurrentMatrix.postTranslate(x, y);
                if (CSBlendImageView.this.mask != null) {
                    CSBlendImageView.this.invalidate();
                }
            }
        }

        private boolean isZoomChanged() {
            float[] fArr = new float[9];
            CSBlendImageView.this.mCurrentMatrix.getValues(fArr);
            float f = fArr[0];
            CSBlendImageView.this.mMatrix.getValues(fArr);
            return f != fArr[0];
        }

        private void setZoomMatrix(MotionEvent motionEvent) {
            if (motionEvent.getPointerCount() < 2) {
                return;
            }
            float distance = distance(motionEvent);
            if (distance > 10.0f) {
                float f = distance / this.mStartDis;
                this.mStartDis = distance;
                float[] fArr = new float[9];
                CSBlendImageView.this.mCurrentMatrix.getValues(fArr);
                checkMaxScale(f, fArr);
                if (CSBlendImageView.this.mask != null) {
                    CSBlendImageView.this.invalidate();
                }
            }
        }

        private float checkMaxScale(float f, float[] fArr) {
            CSBlendImageView.this.mCurrentMatrix.postScale(f, f, CSBlendImageView.this.getWidth() / 2, CSBlendImageView.this.getHeight() / 2);
            return f;
        }

        private boolean checkRest() {
            float[] fArr = new float[9];
            CSBlendImageView.this.mCurrentMatrix.getValues(fArr);
            float f = fArr[0];
            CSBlendImageView.this.mMatrix.getValues(fArr);
            return f < fArr[0];
        }

        private void isMatrixEnable() {
            if (CSBlendImageView.this.getScaleType() != ScaleType.CENTER) {
                CSBlendImageView.this.setScaleType(ScaleType.MATRIX);
            } else {
                this.mMode = 3;
            }
        }

        private float distance(MotionEvent motionEvent) {
            float x = motionEvent.getX(1) - motionEvent.getX(0);
            float y = motionEvent.getY(1) - motionEvent.getY(0);
            return (float) Math.sqrt((x * x) + (y * y));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onDoubleClick() {
            float f = isZoomChanged() ? 1.0f : this.mDobleClickScale;
            CSBlendImageView.this.mCurrentMatrix.set(CSBlendImageView.this.mMatrix);
            CSBlendImageView.this.mCurrentMatrix.postScale(f, f, CSBlendImageView.this.getWidth() / 2, CSBlendImageView.this.getHeight() / 2);
            if (CSBlendImageView.this.mask != null) {
                CSBlendImageView.this.invalidate();
            }
        }
    }

    public void setState(int i) {
        this.state = i;
        if (i == 2) {
            this.recoveryPaths.clear();
            this.recoveryPaths.put(Integer.valueOf(this.currentPaintWidth), new Path());
            return;
        }
        this.rubberPaths.clear();
        this.rubberPaths.put(Integer.valueOf(this.currentPaintWidth), new Path());
    }

    /* loaded from: classes.dex */
    private class RubberTouchListener implements OnTouchListener {
        private float preX;
        private float preY;

        private RubberTouchListener() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:7:0x000c, code lost:
            if (r4 != 2) goto L11;
         */
        @Override // android.view.View.OnTouchListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean onTouch(View r4, MotionEvent r5) {
            /*
                r3 = this;
                int r4 = r5.getActionMasked()
                r0 = 8
                if (r4 == 0) goto L2f
                r1 = 1
                if (r4 == r1) goto L10
                r0 = 2
                if (r4 == r0) goto Ld4
                goto Le5
            L10:
                com.picspool.libfuncview.effect.blend.CSBlendImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.this
                android.widget.ImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.access$900(r4)
                if (r4 == 0) goto Le5
                com.picspool.libfuncview.effect.blend.CSBlendImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.this
                android.widget.ImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.access$900(r4)
                int r4 = r4.getVisibility()
                if (r4 != 0) goto Le5
                com.picspool.libfuncview.effect.blend.CSBlendImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.this
                android.widget.ImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.access$900(r4)
                r4.setVisibility(r0)
                goto Le5
            L2f:
                float r4 = r5.getX()
                r3.preX = r4
                float r4 = r5.getY()
                r3.preY = r4
                com.picspool.libfuncview.effect.blend.CSBlendImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.this
                java.util.Map r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.access$700(r4)
                com.picspool.libfuncview.effect.blend.CSBlendImageView r1 = com.picspool.libfuncview.effect.blend.CSBlendImageView.this
                int r1 = com.picspool.libfuncview.effect.blend.CSBlendImageView.access$600(r1)
                java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
                java.lang.Object r4 = r4.get(r1)
                if (r4 == 0) goto L72
                com.picspool.libfuncview.effect.blend.CSBlendImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.this
                java.util.Map r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.access$700(r4)
                com.picspool.libfuncview.effect.blend.CSBlendImageView r1 = com.picspool.libfuncview.effect.blend.CSBlendImageView.this
                int r1 = com.picspool.libfuncview.effect.blend.CSBlendImageView.access$600(r1)
                java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
                java.lang.Object r4 = r4.get(r1)
                android.graphics.Path r4 = (android.graphics.Path) r4
                float r1 = r5.getX()
                float r2 = r5.getY()
                r4.moveTo(r1, r2)
            L72:
                com.picspool.libfuncview.effect.blend.CSBlendImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.this
                java.util.Map r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.access$800(r4)
                com.picspool.libfuncview.effect.blend.CSBlendImageView r1 = com.picspool.libfuncview.effect.blend.CSBlendImageView.this
                int r1 = com.picspool.libfuncview.effect.blend.CSBlendImageView.access$600(r1)
                java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
                java.lang.Object r4 = r4.get(r1)
                if (r4 == 0) goto La9
                com.picspool.libfuncview.effect.blend.CSBlendImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.this
                java.util.Map r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.access$800(r4)
                com.picspool.libfuncview.effect.blend.CSBlendImageView r1 = com.picspool.libfuncview.effect.blend.CSBlendImageView.this
                int r1 = com.picspool.libfuncview.effect.blend.CSBlendImageView.access$600(r1)
                java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
                java.lang.Object r4 = r4.get(r1)
                android.graphics.Path r4 = (android.graphics.Path) r4
                float r1 = r5.getX()
                float r2 = r5.getY()
                r4.moveTo(r1, r2)
            La9:
                com.picspool.libfuncview.effect.blend.CSBlendImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.this
                android.widget.ImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.access$900(r4)
                if (r4 == 0) goto Ld4
                com.picspool.libfuncview.effect.blend.CSBlendImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.this
                android.widget.ImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.access$900(r4)
                int r4 = r4.getVisibility()
                if (r4 == r0) goto Lca
                com.picspool.libfuncview.effect.blend.CSBlendImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.this
                android.widget.ImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.access$900(r4)
                int r4 = r4.getVisibility()
                r0 = 4
                if (r4 != r0) goto Ld4
            Lca:
                com.picspool.libfuncview.effect.blend.CSBlendImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.this
                android.widget.ImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.access$900(r4)
                r0 = 0
                r4.setVisibility(r0)
            Ld4:
                r3.setRubberOrRecoveryPath(r5)
                com.picspool.libfuncview.effect.blend.CSBlendImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.this
                android.graphics.Bitmap r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.access$1000(r4)
                if (r4 == 0) goto Le2
                r3.setMagnifierContent(r5)
            Le2:
                r3.changeMagnifierViewPosition(r5)
            Le5:
                com.picspool.libfuncview.effect.blend.CSBlendImageView r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.this
                android.view.GestureDetector r4 = com.picspool.libfuncview.effect.blend.CSBlendImageView.access$200(r4)
                boolean r4 = r4.onTouchEvent(r5)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.picspool.libfuncview.effect.blend.CSBlendImageView.RubberTouchListener.onTouch(android.view.View, android.view.MotionEvent):boolean");
        }

        private void setPaintShader(MotionEvent motionEvent) {
            ComposeShader composeShader = new ComposeShader(new LinearGradient(0.0f, 0.0f, CSBlendImageView.this.getWidth(), 0.0f, new int[]{ViewCompat.MEASURED_SIZE_MASK, -1, -1, ViewCompat.MEASURED_SIZE_MASK}, new float[]{0.0f, 0.1f, 0.9f, 1.0f}, Shader.TileMode.CLAMP), new LinearGradient(0.0f, 0.0f, 0.0f, CSBlendImageView.this.getHeight(), new int[]{ViewCompat.MEASURED_SIZE_MASK, -1, -1, ViewCompat.MEASURED_SIZE_MASK}, new float[]{0.0f, 0.1f, 0.9f, 1.0f}, Shader.TileMode.CLAMP), PorterDuff.Mode.SRC_OVER);
            CSBlendImageView.this.rubberPaint.setShader(composeShader);
            CSBlendImageView.this.coverPaint.setShader(composeShader);
        }

        private void changeMagnifierViewPosition(MotionEvent motionEvent) {
            if (CSBlendImageView.this.magnifierImageView != null) {
                ViewGroup.LayoutParams layoutParams = CSBlendImageView.this.magnifierImageView.getLayoutParams();
                if (CSBlendImageView.this.magnifierImageView.getParent() instanceof FrameLayout) {
                    if (motionEvent.getX() > CSBlendImageView.this.magnifierImageView.getRight() || motionEvent.getY() > CSBlendImageView.this.magnifierImageView.getBottom() || CSBlendImageView.this.magnifierImageView.getLeft() >= CSBlendImageView.this.getWidth() / 2) {
                        if (motionEvent.getX() >= CSBlendImageView.this.magnifierImageView.getLeft() && motionEvent.getY() <= CSBlendImageView.this.magnifierImageView.getBottom() && CSBlendImageView.this.magnifierImageView.getLeft() > CSBlendImageView.this.getWidth() / 2) {
                            ((FrameLayout.LayoutParams) layoutParams).gravity = GravityCompat.START;
                        }
                    } else {
                        ((FrameLayout.LayoutParams) layoutParams).gravity = GravityCompat.END;
                    }
                }
                CSBlendImageView.this.magnifierImageView.setLayoutParams(layoutParams);
            }
        }

        private void setMagnifierContent(MotionEvent motionEvent) {
            int x = ((int) motionEvent.getX()) - (CSBlendImageView.this.getWidth() / 8);
            int y = ((int) motionEvent.getY()) - (CSBlendImageView.this.getWidth() / 8);
            if (x < 0) {
                x = 0;
            }
            if (y < 0) {
                y = 0;
            }
            int width = (CSBlendImageView.this.getWidth() / 4) + x > CSBlendImageView.this.canvasBitmap.getWidth() ? CSBlendImageView.this.canvasBitmap.getWidth() - x : CSBlendImageView.this.getWidth() / 4;
            int height = y + width > CSBlendImageView.this.canvasBitmap.getHeight() ? CSBlendImageView.this.canvasBitmap.getHeight() - y : width;
            if (width <= 0) {
                width = 1;
            }
            if (height <= 0) {
                height = 1;
            }
            if (y + height <= CSBlendImageView.this.canvasBitmap.getHeight() && x + width <= CSBlendImageView.this.canvasBitmap.getWidth() && CSBlendImageView.this.rubberCanvasBitmap != null) {
                Bitmap createBitmap = Bitmap.createBitmap(CSBlendImageView.this.rubberCanvasBitmap, x, y, width, height);
                if (CSBlendImageView.this.magnifierImageView != null) {
                    CSBlendImageView.this.magnifierImageView.setImageBitmap(createBitmap);
                }
            }
        }

        private void setRubberOrRecoveryPath(MotionEvent motionEvent) {
            CSBlendImageView.this.currentX = (int) motionEvent.getX();
            CSBlendImageView.this.currentY = (int) motionEvent.getY();
            if (CSBlendImageView.this.state == 1) {
                if (CSBlendImageView.this.rubberPaths.get(Integer.valueOf(CSBlendImageView.this.currentPaintWidth)) != null) {
                    ((Path) CSBlendImageView.this.rubberPaths.get(Integer.valueOf(CSBlendImageView.this.currentPaintWidth))).lineTo(motionEvent.getX(), motionEvent.getY());
                }
            } else if (CSBlendImageView.this.state == 2 && CSBlendImageView.this.recoveryPaths.get(Integer.valueOf(CSBlendImageView.this.currentPaintWidth)) != null) {
                ((Path) CSBlendImageView.this.recoveryPaths.get(Integer.valueOf(CSBlendImageView.this.currentPaintWidth))).lineTo(motionEvent.getX(), motionEvent.getY());
            }
            CSBlendImageView.this.postInvalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private final MatrixTouchListener listener;

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        GestureListener(MatrixTouchListener matrixTouchListener) {
            this.listener = matrixTouchListener;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            this.listener.onDoubleClick();
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return super.onSingleTapUp(motionEvent);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            super.onLongPress(motionEvent);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return super.onScroll(motionEvent, motionEvent2, f, f2);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return super.onFling(motionEvent, motionEvent2, f, f2);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onShowPress(MotionEvent motionEvent) {
            super.onShowPress(motionEvent);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return super.onDoubleTapEvent(motionEvent);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            return super.onSingleTapConfirmed(motionEvent);
        }
    }

    public static Field getDeclaredField(Object obj, String str) {
        for (Class<?> cls = obj.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
            try {
                return cls.getDeclaredField(str);
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, drawable.getOpacity() != PixelFormat.TRANSLUCENT ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        drawable.draw(canvas);
        return createBitmap;
    }

    /* loaded from: classes.dex */
    public enum BlendMode {
        Normal("Normal", PorterDuff.Mode.SRC_OVER),
        Screen("Screen", PorterDuff.Mode.SCREEN),
        Multiply("Multiply", PorterDuff.Mode.MULTIPLY),
        Overlay("Overlay", PorterDuff.Mode.OVERLAY),
        Softlight("Softlight", PorterDuff.Mode.ADD),
        Hardlight("Hardlight", PorterDuff.Mode.DARKEN);
        
        PorterDuff.Mode mode;
        String name;

        BlendMode(String str, PorterDuff.Mode mode) {
            this.name = str;
            this.mode = mode;
        }
    }
}

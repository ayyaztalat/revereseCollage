package com.picspool.libfuncview.slimbody;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewConfiguration;

import com.itcm.sephiroth.android.library.p010cm.CSImageViewTouchBase;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;

/* loaded from: classes.dex */
public class CSSlimFaceView extends CSImageViewTouchBase {
    static final float SCROLL_DELTA_THRESHOLD = 1.0f;
    private static final String TAG = "CSImageViewTouch";
    private Bitmap endPoint;
    private boolean hasMultiPress;
    private RectF initRect;
    private boolean isDrawEndPoint;
    private boolean isDrawStartPoint;
    protected boolean lockTouch;
    public OnCustomeClickListener mClickListener;
    protected int mDoubleTapDirection;
    protected boolean mDoubleTapEnabled;
    protected GestureDetector mGestureDetector;
    private OnSingleDragListener mListener;
    private boolean mRotationEnabled;
    private float mScale;
    protected ScaleGestureDetector mScaleDetector;
    protected boolean mScaleEnabled;
    protected float mScaleFactor;
    protected boolean mScrollEnabled;
    protected int mTouchSlop;
    private int moveX;
    private int moveY;
    private Paint paint;
    private int radius;
    private int singleDownX;
    private int singleDownY;
    private Point startEvent;
    private Bitmap startPoint;

    /* loaded from: classes.dex */
    public interface OnCustomeClickListener {
        void CustomeClick(int i);
    }

    /* loaded from: classes.dex */
    public interface OnSingleDragListener {
        void onActionDown(MotionEvent motionEvent);

        void onActionMove(MotionEvent motionEvent);

        void onActionPointerDown(MotionEvent motionEvent);

        void onSingleDrag(Point point, MotionEvent motionEvent);
    }

    public CSSlimFaceView(Context context) {
        super(context);
        this.mDoubleTapEnabled = true;
        this.mScaleEnabled = true;
        this.mScrollEnabled = true;
        this.mRotationEnabled = true;
        this.mScale = 1.0f;
        this.lockTouch = false;
        this.hasMultiPress = false;
        this.isDrawStartPoint = false;
        this.isDrawEndPoint = false;
    }

    public CSSlimFaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDoubleTapEnabled = true;
        this.mScaleEnabled = true;
        this.mScrollEnabled = true;
        this.mRotationEnabled = true;
        this.mScale = 1.0f;
        this.lockTouch = false;
        this.hasMultiPress = false;
        this.isDrawStartPoint = false;
        this.isDrawEndPoint = false;
    }

    public CSSlimFaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDoubleTapEnabled = true;
        this.mScaleEnabled = true;
        this.mScrollEnabled = true;
        this.mRotationEnabled = true;
        this.mScale = 1.0f;
        this.lockTouch = false;
        this.hasMultiPress = false;
        this.isDrawStartPoint = false;
        this.isDrawEndPoint = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // itcm.sephiroth.android.library.p010cm.CSImageViewTouchBase
    public void init(Context context, AttributeSet attributeSet, int i) {
        super.init(context, attributeSet, i);
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.mDoubleTapDirection = 1;
        this.radius = DMScreenInfoUtil.dip2px(getContext().getApplicationContext(), 58.0f);
        this.paint = new Paint();
        this.startPoint = DMBitmapUtil.getImageFromAssetsFile(getResources(), "slimbody/ui/slim_canvas_orange.png");
        this.endPoint = DMBitmapUtil.getImageFromAssetsFile(getResources(), "slimbody/ui/slim_canvas_white.png");
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

    public void setRadius(int i) {
        this.radius = i;
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // itcm.sephiroth.android.library.p010cm.CSImageViewTouchBase
    public void _setImageDrawable(Drawable drawable, Matrix matrix, float f, float f2) {
        super._setImageDrawable(drawable, matrix, f, f2);
        this.mScaleFactor = getMaxScale() / 3.0f;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // itcm.sephiroth.android.library.p010cm.CSImageViewTouchBase
    public void onLayoutChanged(int i, int i2, int i3, int i4) {
        super.onLayoutChanged(i, i2, i3, i4);
        RectF rectF = this.initRect;
        if (rectF == null || rectF.isEmpty()) {
            this.initRect = new RectF(getBitmapRect());
        }
    }

    public void setLockTouch(boolean z) {
        this.lockTouch = z;
    }

    @Override // itcm.sephiroth.android.library.p010cm.CSImageViewTouchBase, android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        try {
            int action = motionEvent.getAction() & 255;
            if (action != 0) {
                float f = 1.0f;
                if (action == 1) {
                    this.isDrawStartPoint = false;
                    this.isDrawEndPoint = false;
                    if (this.mListener != null && !this.hasMultiPress && this.lockTouch) {
                        this.mListener.onSingleDrag(this.startEvent, motionEvent);
                    }
                    if (this.hasMultiPress) {
                        RectF bitmapRect = getBitmapRect();
                        if (bitmapRect.left > this.initRect.left || bitmapRect.top > this.initRect.top || bitmapRect.right < this.initRect.right || bitmapRect.bottom < this.initRect.bottom) {
                            if (getScale() < 1.0f) {
                                resetMatrix();
                            }
                            if (bitmapRect.left > this.initRect.left) {
                                postTranslate(-bitmapRect.left, 0.0f);
                            }
                            if (bitmapRect.top > this.initRect.top) {
                                postTranslate(0.0f, -bitmapRect.top);
                            }
                            if (bitmapRect.right < this.initRect.right) {
                                postTranslate(this.initRect.right - bitmapRect.right, 0.0f);
                            }
                            if (bitmapRect.bottom < this.initRect.bottom) {
                                postTranslate(0.0f, this.initRect.bottom - bitmapRect.bottom);
                            }
                        }
                    }
                    invalidate();
                } else if (action == 2) {
                    if (this.mode == 1) {
                        this.isDrawEndPoint = true;
                        this.moveX = (int) motionEvent.getX();
                        this.moveY = (int) motionEvent.getY();
                        if (this.mListener != null && this.lockTouch) {
                            this.mListener.onActionMove(motionEvent);
                        }
                        invalidate();
                    }
                    if (this.mode == 2) {
                        this.mode = 1;
                        this.mStart.set(motionEvent.getX(), motionEvent.getY());
                    }
                    if (this.mode == 3) {
                        if (this.mScaleEnabled) {
                            float spacing = spacing(motionEvent);
                            if (this.oldDist != 0.0f) {
                                float f2 = spacing / this.oldDist;
                                if (f2 >= 0.5f) {
                                    if (this.initRect == null || (this.initRect.width() / (getBitmapRect().width() * f2) <= 1.0f && this.initRect.width() / (getBitmapRect().width() * f2) >= 0.25f)) {
                                        f = f2;
                                    }
                                    postScale(f, (motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f, (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f);
                                }
                            }
                            this.oldDist = spacing;
                        }
                        float x = motionEvent.getX(0) - this.mStart.x;
                        float y = motionEvent.getY(0) - this.mStart.y;
                        RectF bitmapRect2 = getBitmapRect();
                        float screenWidth = DMScreenInfoUtil.screenWidth(getContext());
                        float screenHeight = DMScreenInfoUtil.screenHeight(getContext()) - DMScreenInfoUtil.dip2px(getContext(), 70.0f);
                        if (bitmapRect2.left + x <= 0.0f && bitmapRect2.right + x >= screenWidth && bitmapRect2.top + y <= 0.0f && bitmapRect2.bottom + y >= screenHeight) {
                            postTranslate(x, y);
                            this.mStart.set(motionEvent.getX(), motionEvent.getY());
                        }
                    }
                } else if (action == 5) {
                    this.hasMultiPress = true;
                    int actionIndex = motionEvent.getActionIndex();
                    this.isDrawStartPoint = false;
                    this.isDrawEndPoint = false;
                    if (actionIndex >= 1) {
                        this.oldDist = spacing(motionEvent);
                        this.oldDegree = rotation(motionEvent);
                        this.mode = 3;
                        midPoint(this.mMid, motionEvent);
                    }
                    if (this.mListener != null) {
                        this.mListener.onActionPointerDown(motionEvent);
                    }
                    invalidate();
                }
            } else {
                this.hasMultiPress = false;
                this.mode = 1;
                this.mStart.set(motionEvent.getX(), motionEvent.getY());
                this.isDrawStartPoint = true;
                Point point = new Point();
                this.startEvent = point;
                point.x = (int) motionEvent.getX();
                this.startEvent.y = (int) motionEvent.getY();
                this.singleDownX = (int) motionEvent.getX();
                this.singleDownY = (int) motionEvent.getY();
                if (this.mListener != null && this.lockTouch) {
                    this.mListener.onActionDown(motionEvent);
                }
                invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void setOnSingleDragListener(OnSingleDragListener onSingleDragListener) {
        this.mListener = onSingleDragListener;
    }

    @Override // itcm.sephiroth.android.library.p010cm.CSImageViewTouchBase, android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isDrawStartPoint && this.lockTouch) {
            Bitmap bitmap = this.startPoint;
            int i = this.singleDownX;
            int i2 = this.radius;
            int i3 = this.singleDownY;
            canvas.drawBitmap(bitmap, (Rect) null, new Rect(i - (i2 / 2), i3 - (i2 / 2), i + (i2 / 2), i3 + (i2 / 2)), (Paint) null);
        }
        if (this.isDrawEndPoint && this.lockTouch) {
            this.paint.setAntiAlias(true);
            this.paint.setColor(-1);
            this.paint.setStrokeWidth(DMScreenInfoUtil.dip2px(getContext().getApplicationContext(), 2.0f));
            canvas.drawLine(this.singleDownX, this.singleDownY, this.moveX, this.moveY, this.paint);
            Bitmap bitmap2 = this.endPoint;
            int i4 = this.moveX;
            int i5 = this.radius;
            int i6 = this.moveY;
            canvas.drawBitmap(bitmap2, (Rect) null, new Rect(i4 - (i5 / 2), i6 - (i5 / 2), i4 + (i5 / 2), i6 + (i5 / 2)), (Paint) null);
        }
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
}

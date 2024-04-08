package com.picspool.snappic.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.filter.cpu.normal.FastBlurFilter;
import com.picspool.lib.filter.gpu.GPUImageView;
import com.picspool.lib.filter.gpu.adjust.GPUImageLensBlurFilter;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSLensBlurView extends FrameLayout {
    protected static final int DRAG = 1;
    protected static final int JUMP = 2;
    protected static final int NONE = 0;
    public static final String TAG = "fasebulrtest";
    private static final float TOUCH_TOLERANCE = 4.0f;
    protected static final int ZOOM = 3;
    private float angle;
    private Bitmap blurBitmap;
    private float blurMode;
    private GPUImageLensBlurFilter filter;
    private GPUImageView gpuImageView;
    private float left;
    protected PointF mCurPoint;
    protected PointF mMid;
    private Path mPath;
    protected PointF mStart;

    /* renamed from: mX */
    private float f1692mX;

    /* renamed from: mY */
    private float f1693mY;
    protected int mode;
    private int myProgress;
    protected float oldDegree;
    protected float oldDist;
    private float scale;
    private Bitmap shapeBitmap;
    private Bitmap srcBitmap;
    private float top;
    private float transition;

    private float range(int i, float f, float f2) {
        return (((f2 - f) * i) / 100.0f) + f;
    }

    public CSLensBlurView(Context context) {
        super(context);
        this.left = 0.5f;
        this.top = 0.5f;
        this.scale = 0.4f;
        this.mode = 0;
        this.mStart = new PointF();
        this.mMid = new PointF();
        this.mCurPoint = new PointF();
        this.mPath = new Path();
        iniView();
    }

    public CSLensBlurView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.left = 0.5f;
        this.top = 0.5f;
        this.scale = 0.4f;
        this.mode = 0;
        this.mStart = new PointF();
        this.mMid = new PointF();
        this.mCurPoint = new PointF();
        this.mPath = new Path();
        iniView();
    }

    public CSLensBlurView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.left = 0.5f;
        this.top = 0.5f;
        this.scale = 0.4f;
        this.mode = 0;
        this.mStart = new PointF();
        this.mMid = new PointF();
        this.mCurPoint = new PointF();
        this.mPath = new Path();
        iniView();
    }

    private void iniView() {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_lens_blur, (ViewGroup) this, true);
        this.gpuImageView = (GPUImageView) findViewById(R.id.gpu_image);
        GPUImageLensBlurFilter gPUImageLensBlurFilter = new GPUImageLensBlurFilter(getResources().getString(R.string.lens_blur_vertex_shader), getResources().getString(R.string.lens_blur_fragment_shader));
        this.filter = gPUImageLensBlurFilter;
        this.gpuImageView.setFilter(gPUImageLensBlurFilter);
        iniStyleData();
    }

    public void iniStyleData() {
        this.transition = 0.5f;
        this.myProgress = 0;
        this.blurMode = 2.0f;
    }

    private float getBlurRange(int i) {
        return range(i, 5.0f, 55.0f);
    }

    public void setTransitionPercent(int i) {
        this.myProgress = i;
        if (i > 0) {
            this.transition = (i * 0.5f) / 100.0f;
        }
        updateFilterData();
    }

    public void setBlurPercent(int i) {
        Bitmap createScaledBitmap;
        int blurRange = (int) getBlurRange(i);
        Bitmap bitmap = this.srcBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            int width = this.srcBitmap.getWidth();
            int height = this.srcBitmap.getHeight();
            Bitmap bitmap2 = this.blurBitmap;
            if (bitmap2 != null && !bitmap2.isRecycled()) {
                this.blurBitmap.recycle();
            }
            this.blurBitmap = null;
            if (width <= 400 && height <= 400) {
                createScaledBitmap = this.srcBitmap.copy(Bitmap.Config.ARGB_8888, true);
            } else if (width > 400) {
                createScaledBitmap = Bitmap.createScaledBitmap(this.srcBitmap, 400, (int) (height * (400.0f / width)), true);
            } else {
                createScaledBitmap = Bitmap.createScaledBitmap(this.srcBitmap, (int) (width * (400.0f / height)), 400, true);
            }
            this.blurBitmap = FastBlurFilter.blur(createScaledBitmap, blurRange, true);
        }
        updateFilterBitmap();
    }

    private void updateFilterBitmap() {
        this.filter.setBitmap2(this.blurBitmap);
        Log.i(TAG, "loadBlurShape:  filter.setBitmap2(blurBitmap);");
        this.filter.setBitmap3(this.shapeBitmap);
        Log.i(TAG, "loadBlurShape:  filter.setBitmap3(shapeBitmap);");
        updateFilterData();
    }

    private void updateFilterData() {
        this.filter.setTransform2((int) this.angle, this.left, this.top, this.scale, getWidth(), getHeight());
        this.filter.setBlurMode(this.blurMode);
        float f = this.transition;
        if (this.blurMode == 2.0f) {
            f = -((0.5f - (((f * 0.4f) / 0.5f) + 0.1f)) - 0.5f);
        }
        this.filter.setCircleRadius(f);
        this.gpuImageView.requestRender();
    }

    private void updateLocation() {
        this.filter.setTransform2((int) this.angle, this.left, this.top, this.scale, getWidth(), getHeight());
        this.gpuImageView.requestRender();
    }

    public void loadBlurShape(int i) {
        Bitmap bitmap = this.shapeBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.shapeBitmap.recycle();
            Log.i(TAG, "loadBlurShape: shapeBitmap.recycle");
        }
        float f = this.blurMode;
        if (f == 1.0f) {
            Resources resources = getResources();
            this.shapeBitmap = DMBitmapUtil.getImageFromAssetsFile(resources, "blur/" + i + "_focus.jpg");
            Log.i(TAG, "loadBlurShape: blurmode1_focus");
        } else if (f == 2.0f) {
            Resources resources2 = getResources();
            this.shapeBitmap = DMBitmapUtil.getImageFromAssetsFile(resources2, "blur/" + i + "_shadow.jpg");
            Log.i(TAG, "loadBlurShape: blurmode2_shadow");
        }
    }

    public void loadBlurShape(int i, int i2) {
        Bitmap bitmap = this.shapeBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.shapeBitmap.recycle();
            Log.i(TAG, "loadBlurShape: shapeBitmap.recycle");
        }
        float f = i2;
        if (f == 1.0f) {
            this.shapeBitmap = DMBitmapUtil.getImageFromAssetsFile(getResources(), "blur/" + i + "_focus.jpg");
            Log.i(TAG, "loadBlurShape: blurmode1_focus");
        } else if (f == 2.0f) {
            this.shapeBitmap = DMBitmapUtil.getImageFromAssetsFile(getResources(), "blur/" + i + "_shadow.jpg");
            Log.i(TAG, "loadBlurShape: blurmode2_shadow");
        }
    }

    public void setBlurMode(float f) {
        this.blurMode = f;
    }

    public float getBlurMode() {
        return this.blurMode;
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.srcBitmap = bitmap;
        this.gpuImageView.setImage(bitmap);
        updateFilterBitmap();
    }

    public Bitmap getBlurBitmap() {
        return this.blurBitmap;
    }

    public void setBlurBitmap(Bitmap bitmap) {
        this.blurBitmap = bitmap;
    }

    public float getShapeAngle() {
        return this.angle;
    }

    public void setShapeAngle(float f) {
        this.angle = f;
    }

    public Bitmap getShapeBitmap() {
        return this.shapeBitmap;
    }

    public void setShapeBitmap(Bitmap bitmap) {
        this.shapeBitmap = bitmap;
    }

    public float getShapeTop() {
        return this.top;
    }

    public void setShapeTop(float f) {
        this.top = f;
    }

    public void setShapeLeft(float f) {
        this.left = f;
    }

    public float getShapeLeft() {
        return this.left;
    }

    public float getShapeScale() {
        return this.scale;
    }

    public void setShapeScale(float f) {
        this.scale = f;
    }

    public float getShapeTransition() {
        return this.transition;
    }

    public void setShapeTransition(float f) {
        this.transition = f;
    }

    public void destroy() {
        Bitmap bitmap = this.srcBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.srcBitmap.recycle();
            this.srcBitmap = null;
        }
        Bitmap bitmap2 = this.blurBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.blurBitmap.recycle();
            this.blurBitmap = null;
        }
        Bitmap bitmap3 = this.shapeBitmap;
        if (bitmap3 == null || bitmap3.isRecycled()) {
            return;
        }
        this.shapeBitmap.recycle();
        this.shapeBitmap = null;
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
        this.left += (f / getWidth()) / 2.0f;
        this.top += ((f2 / getHeight()) / 2.0f) / (getWidth() / getHeight());
        updateLocation();
    }

    public void postScale(float f) {
        this.scale -= (1.0f - f) / 1.5f;
        updateLocation();
    }

    public void postRotation(float f) {
        this.angle -= f;
        updateLocation();
    }

    private void touch_start(PointF pointF) {
        this.mPath.reset();
        this.mPath.moveTo(pointF.x, pointF.y);
        this.f1692mX = pointF.x;
        this.f1693mY = pointF.y;
    }

    private void touch_move(PointF pointF) {
        float abs = Math.abs(pointF.x - this.f1692mX);
        float abs2 = Math.abs(pointF.y - this.f1693mY);
        if (abs >= TOUCH_TOLERANCE || abs2 >= TOUCH_TOLERANCE) {
            this.mPath.quadTo(this.f1692mX, this.f1693mY, (pointF.x + this.f1692mX) / 2.0f, (pointF.y + this.f1693mY) / 2.0f);
            this.f1692mX = pointF.x;
            this.f1693mY = pointF.y;
        }
    }

    private void touch_up() {
        this.mPath.lineTo(this.f1692mX, this.f1693mY);
        this.mPath.reset();
    }
}

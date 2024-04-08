package com.picspool.libfuncview.masicview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.picspool.libfuncview.masicview.CSPointMap;
import com.picspool.libfuncview.masicview.CSSgImageView;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSMasicView extends FrameLayout {
    private boolean isMoveAction;
    private Context mContext;
    private GPUImageOnTouchListener mGPUImageOnTouchListener;
    private CSSgImageView.ImageLocation mImageLocation;
    private Matrix mImageMatrix;
    private CSSgImageView mMasicImageView;
    private CSPointMap mPointMap;

    /* loaded from: classes.dex */
    public interface GPUImageOnTouchListener {
        void onGPUImageTouchEvent(MotionEvent motionEvent, float f, float f2, float f3);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    public CSMasicView(Context context) {
        super(context);
        this.isMoveAction = false;
        initView(context);
    }

    public CSMasicView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isMoveAction = false;
        initView(context);
    }

    public CSMasicView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isMoveAction = false;
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_mosaic, (ViewGroup) this, true);
    }

    public void initData(Bitmap bitmap) {
        CSSgImageView cSSgImageView = new CSSgImageView(this.mContext);
        this.mMasicImageView = cSSgImageView;
        cSSgImageView.setBitmap(bitmap);
        addView(this.mMasicImageView, -1, -1);
        this.mImageLocation = this.mMasicImageView.getImageLocation();
        this.mImageMatrix = this.mMasicImageView.getImageMatrix();
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.mMasicImageView.setBitmap(bitmap);
    }

    public Bitmap getImageBitmap() {
        return this.mMasicImageView.getBitmap();
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mMasicImageView.onTouchEvent(motionEvent);
            this.mImageLocation.convert(this.mImageMatrix);
            dispatchGPUImageTouchEvent(motionEvent);
        } else {
            if (actionMasked != 1) {
                if (actionMasked != 2) {
                    if (actionMasked != 3) {
                        if (actionMasked == 5) {
                            this.mMasicImageView.onTouchEvent(motionEvent);
                        } else if (actionMasked == 6) {
                            this.mMasicImageView.onTouchEvent(motionEvent);
                        }
                    }
                } else if (motionEvent.getPointerCount() > 1) {
                    this.mMasicImageView.onTouchEvent(motionEvent);
                    this.isMoveAction = true;
                } else {
                    dispatchGPUImageTouchEvent(motionEvent);
                    this.isMoveAction = false;
                }
            }
            this.mMasicImageView.onTouchEvent(motionEvent);
            dispatchGPUImageTouchEvent(motionEvent);
        }
        return true;
    }

    private void dispatchGPUImageTouchEvent(MotionEvent motionEvent) {
        if (this.mGPUImageOnTouchListener != null) {
            this.mPointMap = getPointMap();
            float curWidth = this.mImageLocation.getCurWidth() / this.mImageLocation.getInitWidth();
            float[] mapToTexture = this.mPointMap.mapToTexture(motionEvent.getX(), motionEvent.getY(), this.mImageLocation);
            GPUImageOnTouchListener gPUImageOnTouchListener = this.mGPUImageOnTouchListener;
            if (gPUImageOnTouchListener != null) {
                gPUImageOnTouchListener.onGPUImageTouchEvent(motionEvent, mapToTexture[0], mapToTexture[1], curWidth);
            }
        }
    }

    public void registerGPUImageOnTouchListener(GPUImageOnTouchListener gPUImageOnTouchListener) {
        this.mGPUImageOnTouchListener = gPUImageOnTouchListener;
    }

    private CSPointMap getPointMap() {
        if (this.mPointMap == null) {
            this.mPointMap = new CSPointMap.Builder().setViewWidth(getWidth()).setViewHeight(getHeight()).setSrcLeftTop(this.mImageLocation.getSrcLeftTop()).setSrcRightBottom(this.mImageLocation.getSrcRightBottom()).build();
        }
        return this.mPointMap;
    }
}

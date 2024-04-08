package com.baiwang.libuiinstalens.masicview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.sky.testproject.R;
import com.baiwang.libuiinstalens.masicview.CSPointMap;
import com.baiwang.libuiinstalens.masicview.CSSgImageView;

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
        isMoveAction = false;
        initView(context);
    }

    public CSMasicView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        isMoveAction = false;
        initView(context);
    }

    public CSMasicView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        isMoveAction = false;
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_masic, (ViewGroup) this, true);
    }

    public void initData(Bitmap bitmap) {
        CSSgImageView cSSgImageView = new CSSgImageView(mContext);
        mMasicImageView = cSSgImageView;
        cSSgImageView.setBitmap(bitmap);
        addView(mMasicImageView, -1, -1);
        mImageLocation = mMasicImageView.getImageLocation();
        mImageMatrix = mMasicImageView.getImageMatrix();
    }

    public void setImageBitmap(Bitmap bitmap) {
        mMasicImageView.setBitmap(bitmap);
    }

    public Bitmap getImageBitmap() {
        return mMasicImageView.getBitmap();
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
        if (mMasicImageView == null) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            mMasicImageView.onTouchEvent(motionEvent);
            mImageLocation.convert(mImageMatrix);
            dispatchGPUImageTouchEvent(motionEvent);
        } else {
            if (actionMasked != 1) {
                if (actionMasked != 2) {
                    if (actionMasked != 3) {
                        if (actionMasked == 5) {
                            mMasicImageView.onTouchEvent(motionEvent);
                        } else if (actionMasked == 6) {
                            mMasicImageView.onTouchEvent(motionEvent);
                        }
                    }
                } else if (motionEvent.getPointerCount() > 1) {
                    mMasicImageView.onTouchEvent(motionEvent);
                    isMoveAction = true;
                } else {
                    dispatchGPUImageTouchEvent(motionEvent);
                    isMoveAction = false;
                }
            }
            mMasicImageView.onTouchEvent(motionEvent);
            dispatchGPUImageTouchEvent(motionEvent);
        }
        return true;
    }

    private void dispatchGPUImageTouchEvent(MotionEvent motionEvent) {
        if (mGPUImageOnTouchListener != null) {
            mPointMap = getPointMap();
            float curWidth = mImageLocation.getCurWidth() / mImageLocation.getInitWidth();
            float[] mapToTexture = mPointMap.mapToTexture(motionEvent.getX(), motionEvent.getY(), mImageLocation);
            GPUImageOnTouchListener gPUImageOnTouchListener = mGPUImageOnTouchListener;
            if (gPUImageOnTouchListener != null) {
                gPUImageOnTouchListener.onGPUImageTouchEvent(motionEvent, mapToTexture[0], mapToTexture[1], curWidth);
            }
        }
    }

    public void registerGPUImageOnTouchListener(GPUImageOnTouchListener gPUImageOnTouchListener) {
        mGPUImageOnTouchListener = gPUImageOnTouchListener;
    }

    private CSPointMap getPointMap() {
        if (mPointMap == null) {
            mPointMap = new CSPointMap.Builder().setViewWidth(getWidth()).setViewHeight(getHeight()).setSrcLeftTop(mImageLocation.getSrcLeftTop()).setSrcRightBottom(mImageLocation.getSrcRightBottom()).build();
        }
        return mPointMap;
    }
}

package com.picspool.lib.sticker.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.picspool.lib.sticker.core.DMStickerBMRenderable;
import com.picspool.lib.sticker.util.DMRotateGestureDetectorBMTwoFingerGestureDetector;
import com.picspool.lib.sticker.util.DM_MoveGestureDetector;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DM_ImageTransformPanel extends DMRenderable {
    private PointF mCenter;
    protected float[] mConnerPtArray;
    private Context mContext;
    private Drawable mDelButton;
    private float mFocusX;
    private float mFocusY;
    private int mLineColor;
    private DM_MoveGestureDetector mMoveDetector;
    private PointF mPtDown;
    private DMRotateGestureDetectorBMTwoFingerGestureDetector mRotateDetector;
    private float mRotationDegrees;
    private Drawable mScaleButton;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor;
    private DMStickerBMRenderable mSprite;
    private State mState;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public enum State {
        Normal,
        SpriteChange,
        SingleFingleTrans
    }

    static /* synthetic */ float access$332(DM_ImageTransformPanel dM_ImageTransformPanel, float f) {
        float f2 = dM_ImageTransformPanel.mScaleFactor * f;
        dM_ImageTransformPanel.mScaleFactor = f2;
        return f2;
    }

    static /* synthetic */ float access$424(DM_ImageTransformPanel dM_ImageTransformPanel, float f) {
        float f2 = dM_ImageTransformPanel.mRotationDegrees - f;
        dM_ImageTransformPanel.mRotationDegrees = f2;
        return f2;
    }

    static /* synthetic */ float access$516(DM_ImageTransformPanel dM_ImageTransformPanel, float f) {
        float f2 = dM_ImageTransformPanel.mFocusX + f;
        dM_ImageTransformPanel.mFocusX = f2;
        return f2;
    }

    static /* synthetic */ float access$616(DM_ImageTransformPanel dM_ImageTransformPanel, float f) {
        float f2 = dM_ImageTransformPanel.mFocusY + f;
        dM_ImageTransformPanel.mFocusY = f2;
        return f2;
    }

    public DM_ImageTransformPanel() {
        this.mPtDown = new PointF();
        this.mState = State.Normal;
        this.mRotationDegrees = 0.0f;
        this.mFocusX = 0.0f;
        this.mFocusY = 0.0f;
        this.mScaleFactor = 1.0f;
        this.mLineColor = Color.rgb(82, 197, 204);
    }

    public DM_ImageTransformPanel(Context context) {
        this.mPtDown = new PointF();
        this.mState = State.Normal;
        this.mRotationDegrees = 0.0f;
        this.mFocusX = 0.0f;
        this.mFocusY = 0.0f;
        this.mScaleFactor = 1.0f;
        this.mLineColor = Color.rgb(82, 197, 204);
        setmContext(context);
        this.mScaleButton = context.getResources().getDrawable(R.drawable.dmsticker_zoom);
        this.mDelButton = context.getResources().getDrawable(R.drawable.dmsticker_del);
        this.mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        this.mRotateDetector = new DMRotateGestureDetectorBMTwoFingerGestureDetector(context, new RotateListener());
        this.mMoveDetector = new DM_MoveGestureDetector(context, new MoveListener());
        try {
            this.mLineColor = context.getResources().getColor(R.color.sticker_line_color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DMStickerBMRenderable getSprite() {
        return this.mSprite;
    }

    public void setStickerRenderable(DMStickerBMRenderable dMStickerBMRenderable) {
        if (this.mSprite != dMStickerBMRenderable) {
            this.mSprite = dMStickerBMRenderable;
            this.mState = State.SpriteChange;
        }
    }

    public void draw(Canvas canvas) {
        if (this.mSprite == null || !this.isVisible) {
            return;
        }
        updateConnerPts();
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.lineTo(this.mSprite.width, 0.0f);
        path.lineTo(this.mSprite.width, this.mSprite.height);
        path.lineTo(0.0f, this.mSprite.height);
        path.close();
        path.transform(this.mSprite.makeTransform());
        Paint paint = new Paint(1);
        paint.setStrokeWidth(2.0f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(this.mLineColor);
        paint.setPathEffect(new DashPathEffect(new float[]{5.0f, 5.0f, 5.0f, 5.0f}, 1.0f));
        if (!this.mSprite.getSticker().getIsFreePuzzleBitmap()) {
            canvas.drawPath(path, paint);
        }
        float dip2px = DMScreenInfoUtil.dip2px(this.mContext, 15.0f);
        float dip2px2 = DMScreenInfoUtil.dip2px(this.mContext, 15.0f);
        Drawable drawable = this.mScaleButton;
        float[] fArr = this.mConnerPtArray;
        drawable.setBounds((int) (fArr[0] - dip2px), (int) (fArr[1] - dip2px2), (int) (fArr[0] + dip2px), (int) (fArr[1] + dip2px2));
        this.mScaleButton.draw(canvas);
        Drawable drawable2 = this.mDelButton;
        float[] fArr2 = this.mConnerPtArray;
        drawable2.setBounds((int) (fArr2[2] - dip2px), (int) (fArr2[3] - dip2px2), (int) (fArr2[2] + dip2px), (int) (fArr2[3] + dip2px2));
        this.mDelButton.draw(canvas);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mSprite == null) {
            return false;
        }
        if (motionEvent.getAction() == 0 && isTransformButtonClicked((int) motionEvent.getX(), (int) motionEvent.getY())) {
            this.mState = State.SingleFingleTrans;
            handleTouchEvent(motionEvent);
        }
        if (this.mState == State.SingleFingleTrans) {
            handleTouchEvent(motionEvent);
            return true;
        }
        if (motionEvent.getPointerCount() >= 2) {
            this.mScaleDetector.onTouchEvent(motionEvent);
            this.mRotateDetector.onTouchEvent(motionEvent);
        }
        this.mMoveDetector.onTouchEvent(motionEvent);
        Matrix matrix = new Matrix();
        float f = this.mScaleFactor;
        matrix.postScale(f, f);
        this.mSprite.setNewScaleTransform(matrix);
        Matrix matrix2 = new Matrix();
        matrix2.postRotate(this.mRotationDegrees);
        this.mSprite.setNewRotateTransform(matrix2);
        Matrix matrix3 = new Matrix();
        matrix3.postTranslate(this.mFocusX, this.mFocusY);
        this.mSprite.setNewPanTransform(matrix3);
        if (motionEvent.getAction() == 1) {
            DMStickerBMRenderable dMStickerBMRenderable = this.mSprite;
            dMStickerBMRenderable.setLastPanTransform(dMStickerBMRenderable.newPanTransform());
            this.mSprite.setNewPanTransform(new Matrix());
            DMStickerBMRenderable dMStickerBMRenderable2 = this.mSprite;
            dMStickerBMRenderable2.setLastScaleTransform(dMStickerBMRenderable2.newScaleTransform());
            this.mSprite.setNewScaleTransform(new Matrix());
            DMStickerBMRenderable dMStickerBMRenderable3 = this.mSprite;
            dMStickerBMRenderable3.setLastRotateTransform(dMStickerBMRenderable3.newRotateTransform());
            this.mSprite.setNewRotateTransform(new Matrix());
            this.mScaleFactor = 1.0f;
            this.mRotationDegrees = 0.0f;
            this.mFocusX = 0.0f;
            this.mFocusY = 0.0f;
        }
        return true;
    }

    public boolean isTransformButtonClicked(int i, int i2) {
        Rect bounds = this.mScaleButton.getBounds();
        bounds.inset(-4, -4);
        return bounds.contains(i, i2);
    }

    public boolean isEditButtonClicked(int i, int i2) {
        Rect bounds = this.mDelButton.getBounds();
        bounds.inset(-4, -4);
        return bounds.contains(i, i2);
    }

    public void handleTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.mPtDown.set(motionEvent.getX(), motionEvent.getY());
            this.mCenter = getSpriteCenter();
        } else if (motionEvent.getAction() == 2) {
            Matrix matrix = new Matrix();
            this.mScaleFactor = distance(this.mCenter, new PointF(motionEvent.getX(), motionEvent.getY())) / distance(this.mCenter, this.mPtDown);
            if (this.mSprite != null) {
                int dip2px = DMScreenInfoUtil.dip2px(this.mContext, 70.0f);
                float[] updateConnerPts = getUpdateConnerPts();
                float f = updateConnerPts[0] - updateConnerPts[2];
                float f2 = updateConnerPts[1] - updateConnerPts[3];
                if ((f * f) + (f2 * f2) < dip2px * dip2px && this.mScaleFactor <= 1.0f) {
                    return;
                }
            }
            float f3 = this.mScaleFactor;
            matrix.setScale(f3, f3);
            this.mSprite.setNewScaleTransform(matrix);
            DM_Vector2D dM_Vector2D = new DM_Vector2D(this.mCenter.x, this.mCenter.y);
            DM_Vector2D dM_Vector2D2 = new DM_Vector2D(this.mPtDown.x, this.mPtDown.y);
            dM_Vector2D2.sub(dM_Vector2D);
            DM_Vector2D dM_Vector2D3 = new DM_Vector2D(motionEvent.getX(), motionEvent.getY());
            dM_Vector2D3.sub(dM_Vector2D);
            double angle = dM_Vector2D3.angle(dM_Vector2D2);
            float degrees = (float) Math.toDegrees(angle);
            Log.v("Angle", "radius    " + angle);
            Log.v("Angle", "angle    " + degrees);
            Matrix matrix2 = new Matrix();
            matrix2.setRotate(degrees);
            this.mSprite.setNewRotateTransform(matrix2);
        } else if (motionEvent.getAction() == 1) {
            DMStickerBMRenderable dMStickerBMRenderable = this.mSprite;
            dMStickerBMRenderable.setLastScaleTransform(dMStickerBMRenderable.newScaleTransform());
            this.mSprite.setNewScaleTransform(new Matrix());
            DMStickerBMRenderable dMStickerBMRenderable2 = this.mSprite;
            dMStickerBMRenderable2.setLastRotateTransform(dMStickerBMRenderable2.newRotateTransform());
            this.mSprite.setNewRotateTransform(new Matrix());
            this.mScaleFactor = 1.0f;
            this.mState = State.Normal;
        }
    }

    private void updateConnerPts() {
        float[] fArr = {this.mSprite.width, this.mSprite.height, 0.0f, 0.0f};
        this.mSprite.makeTransform().mapPoints(fArr);
        this.mConnerPtArray = fArr;
    }

    private float[] getUpdateConnerPts() {
        float[] fArr = {this.mSprite.width, this.mSprite.height, 0.0f, 0.0f};
        this.mSprite.makeTransform().mapPoints(fArr);
        return fArr;
    }

    private PointF getSpriteCenter() {
        if (this.mSprite == null) {
            return null;
        }
        RectF rectF = new RectF(0.0f, 0.0f, this.mSprite.width, this.mSprite.height);
        Matrix makeTransform = this.mSprite.makeTransform();
        float[] fArr = {rectF.centerX(), rectF.centerY()};
        makeTransform.mapPoints(fArr);
        return new PointF(fArr[0], fArr[1]);
    }

    private float distance(PointF pointF, PointF pointF2) {
        return (float) Math.sqrt(((pointF2.x - pointF.x) * (pointF2.x - pointF.x)) + ((pointF2.y - pointF.y) * (pointF2.y - pointF.y)));
    }

    private float angle(PointF pointF, PointF pointF2) {
        return (float) Math.toDegrees(Math.atan2(pointF.y - pointF2.y, pointF.x - pointF2.x));
    }

    public Context getmContext() {
        return this.mContext;
    }

    public void setmContext(Context context) {
        this.mContext = context;
    }

    /* loaded from: classes3.dex */
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            DM_ImageTransformPanel.access$332(DM_ImageTransformPanel.this, scaleGestureDetector.getScaleFactor());
            DM_ImageTransformPanel dM_ImageTransformPanel = DM_ImageTransformPanel.this;
            dM_ImageTransformPanel.mScaleFactor = Math.max(0.1f, Math.min(dM_ImageTransformPanel.mScaleFactor, 10.0f));
            return true;
        }
    }

    /* loaded from: classes3.dex */
    private class RotateListener extends DMRotateGestureDetectorBMTwoFingerGestureDetector.SimpleOnRotateGestureListener {
        private RotateListener() {
        }

        @Override // com.picspool.lib.sticker.util.DMRotateGestureDetectorBMTwoFingerGestureDetector.SimpleOnRotateGestureListener, com.picspool.lib.sticker.util.DMRotateGestureDetectorBMTwoFingerGestureDetector.OnRotateGestureListener
        public boolean onRotate(DMRotateGestureDetectorBMTwoFingerGestureDetector dMRotateGestureDetectorBMTwoFingerGestureDetector) {
            DM_ImageTransformPanel.access$424(DM_ImageTransformPanel.this, dMRotateGestureDetectorBMTwoFingerGestureDetector.getRotationDegreesDelta());
            return true;
        }
    }

    /* loaded from: classes3.dex */
    private class MoveListener extends DM_MoveGestureDetector.SimpleOnMoveGestureListener {
        private MoveListener() {
        }

        @Override // com.picspool.lib.sticker.util.DM_MoveGestureDetector.SimpleOnMoveGestureListener, com.picspool.lib.sticker.util.DM_MoveGestureDetector.OnMoveGestureListener
        public boolean onMove(DM_MoveGestureDetector dM_MoveGestureDetector) {
            PointF focusDelta = dM_MoveGestureDetector.getFocusDelta();
            DM_ImageTransformPanel.access$516(DM_ImageTransformPanel.this, focusDelta.x);
            DM_ImageTransformPanel.access$616(DM_ImageTransformPanel.this, focusDelta.y);
            return true;
        }
    }
}

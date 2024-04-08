package com.picspool.lib.sticker.drawonview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import com.picspool.lib.sticker.core.DMStickerBMRenderable;
import com.picspool.lib.sticker.util.DMRotateGestureDetectorBMTwoFingerGestureDetector;
import com.picspool.lib.sticker.util.DM_ImageTransformPanel;
import com.picspool.lib.sticker.util.DM_MoveGestureDetector;
import com.picspool.lib.sticker.util.DM_Vector2D;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMViewTransformPanelBM extends DM_ImageTransformPanel {
    private PointF mCenter;
    protected float[] mConnerPtArray;
    private Context mContext;
    private Drawable mDelButton;
    private int mLineColor;
    private DM_MoveGestureDetector mMoveDetector;
    private DMRotateGestureDetectorBMTwoFingerGestureDetector mRotateDetector;
    private Drawable mScaleButton;
    private ScaleGestureDetector mScaleDetector;
    private DMStickerBMRenderable mSprite;
    private PointF mPtDown = new PointF();
    private State mState = State.Normal;
    private float mRotationDegrees = 0.0f;
    private float mFocusX = 0.0f;
    private float mFocusY = 0.0f;
    private float mScaleFactor = 1.0f;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public enum State {
        Normal,
        SpriteChange,
        SingleFingleTrans
    }

    static /* synthetic */ float access$332(DMViewTransformPanelBM dMViewTransformPanelBM, float f) {
        float f2 = dMViewTransformPanelBM.mScaleFactor * f;
        dMViewTransformPanelBM.mScaleFactor = f2;
        return f2;
    }

    static /* synthetic */ float access$424(DMViewTransformPanelBM dMViewTransformPanelBM, float f) {
        float f2 = dMViewTransformPanelBM.mRotationDegrees - f;
        dMViewTransformPanelBM.mRotationDegrees = f2;
        return f2;
    }

    static /* synthetic */ float access$516(DMViewTransformPanelBM dMViewTransformPanelBM, float f) {
        float f2 = dMViewTransformPanelBM.mFocusX + f;
        dMViewTransformPanelBM.mFocusX = f2;
        return f2;
    }

    static /* synthetic */ float access$616(DMViewTransformPanelBM dMViewTransformPanelBM, float f) {
        float f2 = dMViewTransformPanelBM.mFocusY + f;
        dMViewTransformPanelBM.mFocusY = f2;
        return f2;
    }

    public DMViewTransformPanelBM(Context context) {
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

    @Override // com.picspool.lib.sticker.util.DM_ImageTransformPanel
    public DMStickerBMRenderable getSprite() {
        return this.mSprite;
    }

    @Override // com.picspool.lib.sticker.util.DM_ImageTransformPanel
    public void setStickerRenderable(DMStickerBMRenderable dMStickerBMRenderable) {
        if (this.mSprite != dMStickerBMRenderable) {
            this.mSprite = dMStickerBMRenderable;
            this.mState = State.SpriteChange;
        }
    }

    @Override // com.picspool.lib.sticker.util.DM_ImageTransformPanel
    public void draw(Canvas canvas) {
        if (this.mSprite == null || !this.isVisible) {
            return;
        }
        updateConnerPts();
        drawPathEffect(canvas);
        float dimension = this.mContext.getResources().getDimension(R.dimen.instasticker_sticker_button_width);
        float dimension2 = this.mContext.getResources().getDimension(R.dimen.instasticker_sticker_button_width);
        Drawable drawable = this.mScaleButton;
        float[] fArr = this.mConnerPtArray;
        drawable.setBounds((int) (fArr[0] - dimension), (int) (fArr[1] - dimension2), (int) (fArr[0] + dimension), (int) (fArr[1] + dimension2));
        this.mScaleButton.draw(canvas);
        Drawable drawable2 = this.mDelButton;
        float[] fArr2 = this.mConnerPtArray;
        drawable2.setBounds((int) (fArr2[2] - dimension), (int) (fArr2[3] - dimension2), (int) (fArr2[2] + dimension), (int) (fArr2[3] + dimension2));
        this.mDelButton.draw(canvas);
    }

    private void drawPathEffect(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(3.0f);
        paint.setColor(this.mLineColor);
        float[] fArr = {0.0f, 0.0f, this.mSprite.width, 0.0f, this.mSprite.width, this.mSprite.height, 0.0f, this.mSprite.height};
        this.mSprite.makeTransform().mapPoints(fArr);
        if (this.mSprite.getSticker().getIsFreePuzzleBitmap()) {
            return;
        }
        canvas.drawLine(fArr[0], fArr[1], fArr[2], fArr[3], paint);
        canvas.drawLine(fArr[2], fArr[3], fArr[4], fArr[5], paint);
        canvas.drawLine(fArr[0], fArr[1], fArr[6], fArr[7], paint);
        canvas.drawLine(fArr[6], fArr[7], fArr[4], fArr[5], paint);
    }

    @Override // com.picspool.lib.sticker.util.DM_ImageTransformPanel
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

    @Override // com.picspool.lib.sticker.util.DM_ImageTransformPanel
    public boolean isTransformButtonClicked(int i, int i2) {
        Rect bounds = this.mScaleButton.getBounds();
        bounds.inset(-4, -4);
        return bounds.contains(i, i2);
    }

    @Override // com.picspool.lib.sticker.util.DM_ImageTransformPanel
    public boolean isEditButtonClicked(int i, int i2) {
        Rect bounds = this.mDelButton.getBounds();
        bounds.inset(-4, -4);
        return bounds.contains(i, i2);
    }

    @Override // com.picspool.lib.sticker.util.DM_ImageTransformPanel
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

    @Override // com.picspool.lib.sticker.util.DM_ImageTransformPanel
    public Context getmContext() {
        return this.mContext;
    }

    @Override // com.picspool.lib.sticker.util.DM_ImageTransformPanel
    public void setmContext(Context context) {
        this.mContext = context;
    }

    /* loaded from: classes3.dex */
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            DMViewTransformPanelBM.access$332(DMViewTransformPanelBM.this, scaleGestureDetector.getScaleFactor());
            DMViewTransformPanelBM dMViewTransformPanelBM = DMViewTransformPanelBM.this;
            dMViewTransformPanelBM.mScaleFactor = Math.max(0.1f, Math.min(dMViewTransformPanelBM.mScaleFactor, 10.0f));
            return true;
        }
    }

    /* loaded from: classes3.dex */
    private class RotateListener extends DMRotateGestureDetectorBMTwoFingerGestureDetector.SimpleOnRotateGestureListener {
        private RotateListener() {
        }

        @Override // com.picspool.lib.sticker.util.DMRotateGestureDetectorBMTwoFingerGestureDetector.SimpleOnRotateGestureListener, com.picspool.lib.sticker.util.DMRotateGestureDetectorBMTwoFingerGestureDetector.OnRotateGestureListener
        public boolean onRotate(DMRotateGestureDetectorBMTwoFingerGestureDetector dMRotateGestureDetectorBMTwoFingerGestureDetector) {
            DMViewTransformPanelBM.access$424(DMViewTransformPanelBM.this, dMRotateGestureDetectorBMTwoFingerGestureDetector.getRotationDegreesDelta());
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
            DMViewTransformPanelBM.access$516(DMViewTransformPanelBM.this, focusDelta.x);
            DMViewTransformPanelBM.access$616(DMViewTransformPanelBM.this, focusDelta.y);
            return true;
        }
    }
}

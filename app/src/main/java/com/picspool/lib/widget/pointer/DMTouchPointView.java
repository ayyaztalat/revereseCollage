package com.picspool.lib.widget.pointer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sky.testproject.Opcodes;

/* loaded from: classes3.dex */
public class DMTouchPointView extends View {
    protected static final int DRAG = 1;
    protected static final int JUMP = 2;
    protected static final int NONE = 0;
    private int innerCircle;
    protected PointF mCurPoint;
    protected PointF mStart;
    protected int mode;
    private Paint paint;
    private OnTouchPointToListener pointToListener;
    Bitmap pointerBitmap;
    float pointerX;
    float pointerY;
    private int rectColor;
    private int ringWidth;
    public Boolean showPointer;
    public Boolean showPointerColor;

    /* loaded from: classes3.dex */
    public interface OnTouchPointToListener {
        void pointTo(float f, float f2);
    }

    public DMTouchPointView(Context context) {
        super(context);
        this.showPointerColor = false;
        this.paint = new Paint();
        this.innerCircle = 80;
        this.ringWidth = 20;
        this.rectColor = -1;
        this.pointerBitmap = null;
        this.pointerY = 0.0f;
        this.showPointer = false;
        this.mode = 0;
        this.mStart = new PointF();
        this.mCurPoint = new PointF();
    }

    public DMTouchPointView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.showPointerColor = false;
        this.paint = new Paint();
        this.innerCircle = 80;
        this.ringWidth = 20;
        this.rectColor = -1;
        this.pointerBitmap = null;
        this.pointerY = 0.0f;
        this.showPointer = false;
        this.mode = 0;
        this.mStart = new PointF();
        this.mCurPoint = new PointF();
    }

    public void setListener(OnTouchPointToListener onTouchPointToListener) {
        this.pointToListener = onTouchPointToListener;
    }

    public void setPointerIcon(Bitmap bitmap) {
        this.pointerBitmap = bitmap;
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.pointerX = i / 2;
        this.pointerY = i2 / 2;
    }

    public void setPoint(float f, float f2) {
        this.pointerX = f;
        this.pointerY = f2;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.showPointer.booleanValue()) {
            Bitmap bitmap = this.pointerBitmap;
            canvas.drawBitmap(bitmap, this.pointerX - (bitmap.getWidth() / 2), this.pointerY - (this.pointerBitmap.getWidth() / 2), this.paint);
        }
        if (this.showPointerColor.booleanValue()) {
            this.paint.setAntiAlias(true);
            this.paint.setStyle(Paint.Style.STROKE);
            this.paint.setARGB(155, Opcodes.GOTO, 190, 206);
            this.paint.setStrokeWidth(2.0f);
            canvas.drawCircle(this.pointerX, this.pointerY, this.innerCircle, this.paint);
            this.paint.setColor(this.rectColor);
            this.paint.setStrokeWidth(this.ringWidth);
            canvas.drawCircle(this.pointerX, this.pointerY, this.innerCircle + 1 + (this.ringWidth / 2), this.paint);
            this.paint.setARGB(155, Opcodes.GOTO, 190, 206);
            this.paint.setStrokeWidth(2.0f);
            canvas.drawCircle(this.pointerX, this.pointerY, this.innerCircle + this.ringWidth, this.paint);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.pointToListener == null) {
            return false;
        }
        this.mCurPoint.set(motionEvent.getX(), motionEvent.getY());
        try {
            int action = motionEvent.getAction() & 255;
            if (action == 0) {
                this.mode = 1;
                this.mStart.set(this.mCurPoint.x, this.mCurPoint.y);
            } else if (action == 1) {
                this.mode = 0;
            } else if (action == 2) {
                if (this.mode == 1) {
                    float f = this.mCurPoint.x - this.mStart.x;
                    float f2 = this.mCurPoint.y - this.mStart.y;
                    this.mStart.set(this.mCurPoint.x, this.mCurPoint.y);
                    float f3 = this.pointerX + f;
                    this.pointerX = f3;
                    this.pointerY += f2;
                    if (f3 < 0.0f) {
                        this.pointerX = 0.0f;
                    }
                    if (this.pointerY < 0.0f) {
                        this.pointerY = 0.0f;
                    }
                    if (this.pointerX > getWidth()) {
                        this.pointerX = getWidth();
                    }
                    if (this.pointerY > getHeight()) {
                        this.pointerY = getHeight();
                    }
                    this.pointToListener.pointTo(this.pointerX, this.pointerY);
                }
                if (this.mode == 2) {
                    this.mode = 1;
                    this.mStart.set(this.mCurPoint.x, this.mCurPoint.y);
                }
            } else if (action == 6) {
                this.mode = 2;
            }
        } catch (Exception e) {
            System.out.println("error:" + e.getMessage());
        }
        return true;
    }

    public void setPointerColor(int i) {
        this.rectColor = i;
    }
}

package com.photo.editor.square.splash.view.cropview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/* loaded from: classes2.dex */
public class CSCropImageView extends View {
    private final int EDGE_B;
    private final int EDGE_L;
    private final int EDGE_LB;
    private final int EDGE_LT;
    private final int EDGE_MOVE_IN;
    private final int EDGE_MOVE_OUT;
    private final int EDGE_NONE;
    private final int EDGE_R;
    private final int EDGE_RB;
    private final int EDGE_RT;
    private final int EDGE_T;
    private final int STATUS_TOUCH_MULTI_START;
    private final int STATUS_TOUCH_MULTI_TOUCHING;
    private final int STATUS_TOUCH_SINGLE;
    private int cropHeight;
    private int cropWidth;
    public int currentEdge;
    private final int defaultCropHeight;
    private final int defaultCropWidth;
    protected float floatRationWH;
    protected boolean isFrist;
    protected Context mContext;
    protected Drawable mDrawable;
    protected Rect mDrawableDst;
    protected Rect mDrawableFloat;
    protected Rect mDrawableSrc;
    protected CSFloatDrawable mFloatDrawable;
    protected final int mFloatPointRadius;
    protected Paint mPaint;
    private int mStatus;
    private final int minFloatHeight;
    private final int minFloatWidth;
    private float oldX;
    private float oldY;
    private float oldx_0;
    private float oldx_1;
    private float oldy_0;
    private float oldy_1;

    public CSCropImageView(Context context) {
        super(context);
        this.oldX = 0.0f;
        this.oldY = 0.0f;
        this.oldx_0 = 0.0f;
        this.oldy_0 = 0.0f;
        this.oldx_1 = 0.0f;
        this.oldy_1 = 0.0f;
        this.STATUS_TOUCH_SINGLE = 1;
        this.STATUS_TOUCH_MULTI_START = 2;
        this.STATUS_TOUCH_MULTI_TOUCHING = 3;
        this.mStatus = 1;
        this.defaultCropWidth = 200;
        this.defaultCropHeight = 200;
        this.minFloatWidth = 60;
        this.minFloatHeight = 60;
        this.cropWidth = 200;
        this.cropHeight = 200;
        this.EDGE_LT = 1;
        this.EDGE_RT = 2;
        this.EDGE_LB = 3;
        this.EDGE_RB = 4;
        this.EDGE_MOVE_IN = 5;
        this.EDGE_MOVE_OUT = 6;
        this.EDGE_NONE = 7;
        this.EDGE_T = 8;
        this.EDGE_B = 9;
        this.EDGE_L = 10;
        this.EDGE_R = 11;
        this.currentEdge = 7;
        this.floatRationWH = 0.0f;
        this.mFloatPointRadius = 10;
        this.mDrawableSrc = new Rect();
        this.mDrawableDst = new Rect();
        this.mDrawableFloat = new Rect();
        this.isFrist = true;
        init(context);
    }

    public CSCropImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.oldX = 0.0f;
        this.oldY = 0.0f;
        this.oldx_0 = 0.0f;
        this.oldy_0 = 0.0f;
        this.oldx_1 = 0.0f;
        this.oldy_1 = 0.0f;
        this.STATUS_TOUCH_SINGLE = 1;
        this.STATUS_TOUCH_MULTI_START = 2;
        this.STATUS_TOUCH_MULTI_TOUCHING = 3;
        this.mStatus = 1;
        this.defaultCropWidth = 200;
        this.defaultCropHeight = 200;
        this.minFloatWidth = 60;
        this.minFloatHeight = 60;
        this.cropWidth = 200;
        this.cropHeight = 200;
        this.EDGE_LT = 1;
        this.EDGE_RT = 2;
        this.EDGE_LB = 3;
        this.EDGE_RB = 4;
        this.EDGE_MOVE_IN = 5;
        this.EDGE_MOVE_OUT = 6;
        this.EDGE_NONE = 7;
        this.EDGE_T = 8;
        this.EDGE_B = 9;
        this.EDGE_L = 10;
        this.EDGE_R = 11;
        this.currentEdge = 7;
        this.floatRationWH = 0.0f;
        this.mFloatPointRadius = 10;
        this.mDrawableSrc = new Rect();
        this.mDrawableDst = new Rect();
        this.mDrawableFloat = new Rect();
        this.isFrist = true;
        init(context);
    }

    public CSCropImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.oldX = 0.0f;
        this.oldY = 0.0f;
        this.oldx_0 = 0.0f;
        this.oldy_0 = 0.0f;
        this.oldx_1 = 0.0f;
        this.oldy_1 = 0.0f;
        this.STATUS_TOUCH_SINGLE = 1;
        this.STATUS_TOUCH_MULTI_START = 2;
        this.STATUS_TOUCH_MULTI_TOUCHING = 3;
        this.mStatus = 1;
        this.defaultCropWidth = 200;
        this.defaultCropHeight = 200;
        this.minFloatWidth = 60;
        this.minFloatHeight = 60;
        this.cropWidth = 200;
        this.cropHeight = 200;
        this.EDGE_LT = 1;
        this.EDGE_RT = 2;
        this.EDGE_LB = 3;
        this.EDGE_RB = 4;
        this.EDGE_MOVE_IN = 5;
        this.EDGE_MOVE_OUT = 6;
        this.EDGE_NONE = 7;
        this.EDGE_T = 8;
        this.EDGE_B = 9;
        this.EDGE_L = 10;
        this.EDGE_R = 11;
        this.currentEdge = 7;
        this.floatRationWH = 0.0f;
        this.mFloatPointRadius = 10;
        this.mDrawableSrc = new Rect();
        this.mDrawableDst = new Rect();
        this.mDrawableFloat = new Rect();
        this.isFrist = true;
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        if (CSUtils.hasHoneycomb()) {
            setLayerType(1, null);
        }
        CSFloatDrawable cSFloatDrawable = new CSFloatDrawable(context);
        this.mFloatDrawable = cSFloatDrawable;
        cSFloatDrawable.setFreeMode(true);
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(Color.parseColor("#a0000000"));
        this.mPaint.setStyle(Paint.Style.FILL);
    }

    public void setDrawable(Drawable drawable, int i, int i2) {
        this.mDrawable = drawable;
        if (i < 60) {
            i = 60;
        }
        this.cropWidth = i;
        if (i2 < 60) {
            i2 = 60;
        }
        this.cropHeight = i2;
        this.isFrist = true;
        invalidate();
    }

    public void setFloatRationWH(float f) {
        int i;
        int i2;
        this.floatRationWH = f;
        this.mFloatDrawable.setFreeMode(f == 0.0f);
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        float intrinsicWidth = this.mDrawable.getIntrinsicWidth() / this.mDrawable.getIntrinsicHeight();
        float f2 = this.mContext.getResources().getDisplayMetrics().density;
        if (intrinsicWidth > getWidth() / getHeight()) {
            i = getWidth();
            i2 = (int) (i / intrinsicWidth);
        } else {
            int height2 = getHeight();
            i = (int) (height2 * intrinsicWidth);
            i2 = height2;
        }
        float f3 = i;
        float f4 = i2;
        float f5 = f3 / f4;
        if (f > 0.0f) {
            if (f > f5) {
                i2 = (int) (f3 / f);
            } else {
                i = (int) (f4 * f);
            }
        }
        int i3 = (i - 0) / 2;
        int i4 = (i2 - 0) / 2;
        this.mDrawableFloat.set(width - i3, height - i4, width + i3, height + i4);
        this.mDrawableFloat.inset(dipTopx(getContext(), 10.0f), dipTopx(getContext(), 10.0f));
        invalidate();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float f;
        int width;
        int height;
        int width2;
        if (motionEvent.getPointerCount() > 1) {
            int i = this.mStatus;
            if (i == 1) {
                this.mStatus = 2;
                this.oldx_0 = motionEvent.getX(0);
                this.oldy_0 = motionEvent.getY(0);
                this.oldx_1 = motionEvent.getX(1);
                this.oldy_1 = motionEvent.getY(1);
            } else if (i == 2) {
                this.mStatus = 3;
            }
        } else {
            int i2 = this.mStatus;
            if (i2 == 2 || i2 == 3) {
                this.oldx_0 = 0.0f;
                this.oldy_0 = 0.0f;
                this.oldx_1 = 0.0f;
                this.oldy_1 = 0.0f;
                this.oldX = motionEvent.getX();
                this.oldY = motionEvent.getY();
            }
            this.mStatus = 1;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this.oldX = motionEvent.getX();
            float y = motionEvent.getY();
            this.oldY = y;
            this.currentEdge = getTouchEdge((int) this.oldX, (int) y);
        } else if (action == 1) {
            checkBounds();
        } else if (action == 2) {
            int i3 = this.mStatus;
            if (i3 == 3) {
                float x = motionEvent.getX(0);
                float y2 = motionEvent.getY(0);
                float x2 = motionEvent.getX(1);
                float y3 = motionEvent.getY(1);
                float abs = Math.abs(this.oldx_1 - this.oldx_0);
                float abs2 = Math.abs(this.oldy_1 - this.oldy_0);
                float abs3 = Math.abs(x2 - x);
                float abs4 = Math.abs(y3 - y2);
                if (abs3 + abs4 > 0.0f) {
                    int centerX = this.mDrawableFloat.centerX();
                    int centerY = this.mDrawableFloat.centerY();
                    if (this.floatRationWH > 0.0f) {
                        width2 = (int) (this.mDrawableFloat.width() * ((Math.abs(abs4 - abs2) > Math.abs(abs3 - abs) ? 1 : (Math.abs(abs4 - abs2) == Math.abs(abs3 - abs) ? 0 : -1)) > 0 ? abs4 / abs2 : abs3 / abs));
                        height = (int) (width2 / this.floatRationWH);
                    } else {
                        double sqrt = Math.sqrt((abs3 * abs3) + (abs4 * abs4)) / Math.sqrt((abs * abs) + (abs2 * abs2));
                        height = (int) (this.mDrawableFloat.height() * sqrt);
                        width2 = (int) (this.mDrawableFloat.width() * sqrt);
                    }
                    int i4 = width2 / 2;
                    int i5 = centerX - i4;
                    int i6 = height / 2;
                    int i7 = centerY - i6;
                    int i8 = centerX + i4;
                    int i9 = centerY + i6;
                    if (i5 <= this.mDrawable.getBounds().left || i8 >= this.mDrawable.getBounds().right || i7 <= this.mDrawable.getBounds().top || i9 >= this.mDrawable.getBounds().bottom || Math.abs(i8 - i5) < 60 || Math.abs(i9 - i7) < 60) {
                        return true;
                    }
                    this.mDrawableFloat.set(i5, i7, i8, i9);
                    invalidate();
                    this.oldx_0 = x;
                    this.oldy_0 = y2;
                    this.oldx_1 = x2;
                    this.oldy_1 = y3;
                }
            } else if (i3 == 1) {
                int x3 = (int) (motionEvent.getX() - this.oldX);
                int y4 = (int) (motionEvent.getY() - this.oldY);
                boolean contains = this.mFloatDrawable.getBounds().contains((int) motionEvent.getX(), (int) motionEvent.getY());
                Rect rect = new Rect(this.mDrawableFloat);
                this.oldX = motionEvent.getX();
                this.oldY = motionEvent.getY();
                if (x3 != 0 || y4 != 0) {
                    if (5 != this.currentEdge && this.floatRationWH > 0.0f) {
                        if (Math.abs(y4) > Math.abs(x3)) {
                            f = y4;
                            width = this.mDrawableFloat.height();
                        } else {
                            f = x3;
                            width = this.mDrawableFloat.width();
                        }
                        x3 = (int) (x3 * ((f / width) + 1.0f));
                        y4 = (int) (x3 / this.floatRationWH);
                    }
                    int i10 = this.mDrawableFloat.left + x3;
                    int i11 = this.mDrawableFloat.top + y4;
                    int i12 = this.mDrawableFloat.right + x3;
                    int i13 = this.mDrawableFloat.bottom + y4;
                    switch (this.currentEdge) {
                        case 1:
                            if (i10 >= this.mDrawable.getBounds().left && i11 >= this.mDrawable.getBounds().top) {
                                Rect rect2 = this.mDrawableFloat;
                                rect2.set(i10, i11, rect2.right, this.mDrawableFloat.bottom);
                                break;
                            }
                            break;
                        case 2:
                            int i14 = this.mDrawableFloat.top;
                            if (this.floatRationWH > 0.0f) {
                                y4 = -y4;
                            }
                            int i15 = i14 + y4;
                            if (i12 <= this.mDrawable.getBounds().right && i15 >= this.mDrawable.getBounds().top) {
                                Rect rect3 = this.mDrawableFloat;
                                rect3.set(rect3.left, i15, i12, this.mDrawableFloat.bottom);
                                break;
                            }
                            break;
                        case 3:
                            int i16 = this.mDrawableFloat.bottom;
                            if (this.floatRationWH > 0.0f) {
                                y4 = -y4;
                            }
                            int i17 = i16 + y4;
                            if (i10 >= this.mDrawable.getBounds().left && i17 <= this.mDrawable.getBounds().bottom) {
                                Rect rect4 = this.mDrawableFloat;
                                rect4.set(i10, rect4.top, this.mDrawableFloat.right, i17);
                                break;
                            }
                            break;
                        case 4:
                            if (i12 <= this.mDrawable.getBounds().right && i13 <= this.mDrawable.getBounds().bottom) {
                                Rect rect5 = this.mDrawableFloat;
                                rect5.set(rect5.left, this.mDrawableFloat.top, i12, i13);
                                break;
                            }
                            break;
                        case 5:
                            if (contains) {
                                this.mDrawableFloat.offset((i10 <= this.mDrawable.getBounds().left || i12 >= this.mDrawable.getBounds().right) ? 0 : 0, (i11 <= this.mDrawable.getBounds().top || i13 >= this.mDrawable.getBounds().bottom) ? 0 : 0);
                                break;
                            }
                            break;
                        case 8:
                            if (i11 >= this.mDrawable.getBounds().top) {
                                Rect rect6 = this.mDrawableFloat;
                                rect6.set(rect6.left, i11, this.mDrawableFloat.right, this.mDrawableFloat.bottom);
                                break;
                            }
                            break;
                        case 9:
                            if (i13 <= this.mDrawable.getBounds().bottom) {
                                Rect rect7 = this.mDrawableFloat;
                                rect7.set(rect7.left, this.mDrawableFloat.top, this.mDrawableFloat.right, i13);
                                break;
                            }
                            break;
                        case 10:
                            if (i10 >= this.mDrawable.getBounds().left) {
                                Rect rect8 = this.mDrawableFloat;
                                rect8.set(i10, rect8.top, this.mDrawableFloat.right, this.mDrawableFloat.bottom);
                                break;
                            }
                            break;
                        case 11:
                            if (i12 <= this.mDrawable.getBounds().right) {
                                Rect rect9 = this.mDrawableFloat;
                                rect9.set(rect9.left, this.mDrawableFloat.top, i12, this.mDrawableFloat.bottom);
                                break;
                            }
                            break;
                    }
                    this.mDrawableFloat.sort();
                    if (this.currentEdge != 5 && (this.mDrawableFloat.width() < 60 || this.mDrawableFloat.height() < 60)) {
                        this.mDrawableFloat = rect;
                        return true;
                    }
                    invalidate();
                }
            }
        } else if (action == 6) {
            this.currentEdge = 7;
        }
        return true;
    }

    public int getTouchEdge(int i, int i2) {
        int cirleWidth = this.mFloatDrawable.getCirleWidth();
        int cirleHeight = this.mFloatDrawable.getCirleHeight();
        int i3 = this.mFloatDrawable.getBounds().left - cirleWidth;
        int i4 = this.mFloatDrawable.getBounds().top - cirleHeight;
        int i5 = this.mFloatDrawable.getBounds().right + cirleWidth;
        int i6 = this.mFloatDrawable.getBounds().bottom + cirleHeight;
        int i7 = cirleWidth * 2;
        int i8 = i3 + i7;
        int i9 = cirleHeight * 2;
        int i10 = i4 + i9;
        int i11 = i5 - i7;
        int i12 = i6 - i9;
        if (i3 > i || i >= i8 || i4 > i2 || i2 >= i10) {
            if (i11 > i || i >= i5 || i4 > i2 || i2 >= i10) {
                if (i3 > i || i >= i8 || i12 > i2 || i2 >= i6) {
                    if (i11 > i || i >= i5 || i12 > i2 || i2 >= i6) {
                        if (this.mFloatDrawable.getFreeMode()) {
                            int i13 = (this.mFloatDrawable.getBounds().left + this.mFloatDrawable.getBounds().right) / 2;
                            int i14 = (this.mFloatDrawable.getBounds().top + this.mFloatDrawable.getBounds().bottom) / 2;
                            int clipRectWidth = this.mFloatDrawable.getClipRectWidth() / 2;
                            int clipRectHeight = this.mFloatDrawable.getClipRectHeight();
                            int i15 = i13 - clipRectWidth;
                            if (i15 <= i && i2 >= i10 - clipRectHeight && i13 + clipRectWidth > i && i2 < i10 + clipRectHeight) {
                                return 8;
                            }
                            if (i15 <= i && i2 >= i12 - clipRectHeight && i13 + clipRectWidth > i && i2 < i12 + clipRectHeight) {
                                return 9;
                            }
                            if (i8 - clipRectHeight <= i && i2 >= i14 - clipRectWidth && i8 + clipRectHeight > i && i2 < i14 + clipRectWidth) {
                                return 10;
                            }
                            if (i11 - clipRectHeight <= i && i2 >= i14 - clipRectWidth && i11 + clipRectHeight > i && i2 < i14 + clipRectWidth) {
                                return 11;
                            }
                        }
                        return this.mFloatDrawable.getBounds().contains(i, i2) ? 5 : 6;
                    }
                    return 4;
                }
                return 3;
            }
            return 2;
        }
        return 1;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap;
        Drawable drawable = this.mDrawable;
        if (drawable == null || drawable.getIntrinsicWidth() == 0 || this.mDrawable.getIntrinsicHeight() == 0) {
            return;
        }
        Drawable drawable2 = this.mDrawable;
        if ((drawable2 instanceof BitmapDrawable) && ((bitmap = ((BitmapDrawable) drawable2).getBitmap()) == null || bitmap.isRecycled())) {
            return;
        }
        configureBounds();
        this.mDrawable.draw(canvas);
        canvas.save();
        canvas.clipRect(this.mDrawableFloat, Region.Op.DIFFERENCE);
        canvas.drawRect(this.mDrawableSrc, this.mPaint);
        canvas.restore();
        this.mFloatDrawable.draw(canvas);
    }

    protected void configureBounds() {
        int i;
        int i2;
        if (this.isFrist) {
            float intrinsicWidth = this.mDrawable.getIntrinsicWidth() / this.mDrawable.getIntrinsicHeight();
            if (intrinsicWidth > getWidth() / getHeight()) {
                i = getWidth();
                i2 = (int) (i / intrinsicWidth);
            } else {
                int height = getHeight();
                i = (int) (height * intrinsicWidth);
                i2 = height;
            }
            int width = (getWidth() - i) / 2;
            int height2 = (getHeight() - i2) / 2;
            int i3 = width + i;
            int i4 = height2 + i2;
            this.mDrawableSrc.set(width, height2, i3, i4);
            int dipTopx = dipTopx(getContext(), 10.0f);
            this.mDrawableSrc.inset(dipTopx, dipTopx);
            this.mDrawableDst.set(this.mDrawableSrc);
            if (i > 120 && i2 > 120) {
                this.cropWidth = i / 2;
                this.cropHeight = i2 / 2;
            }
            if (this.floatRationWH != 0.0f) {
                int width2 = getWidth() / 2;
                int height3 = getHeight() / 2;
                float f = i;
                float f2 = i2;
                float f3 = f / f2;
                float f4 = this.floatRationWH;
                if (f4 > 0.0f) {
                    if (f4 > f3) {
                        i2 = (int) (f / f4);
                    } else {
                        i = (int) (f2 * f4);
                    }
                }
                int i5 = (i - 0) / 2;
                int i6 = (i2 - 0) / 2;
                this.mDrawableFloat.set(width2 - i5, height3 - i6, width2 + i5, height3 + i6);
                this.mDrawableFloat.inset(dipTopx(getContext(), 10.0f), dipTopx(getContext(), 10.0f));
            } else {
                this.mDrawableFloat.set(width + dipTopx, height2 + dipTopx, i3 - dipTopx, i4 - dipTopx);
            }
            this.isFrist = false;
        }
        this.mDrawable.setBounds(this.mDrawableDst);
        this.mFloatDrawable.setBounds(this.mDrawableFloat);
    }

    protected void checkBounds() {
        boolean z;
        int i = this.mDrawableFloat.left;
        int i2 = this.mDrawableFloat.top;
        boolean z2 = true;
        if (this.mDrawableFloat.left < this.mDrawableSrc.left) {
            i = this.mDrawableSrc.left;
            z = true;
        } else {
            z = false;
        }
        if (this.mDrawableFloat.top < this.mDrawableSrc.top) {
            i2 = this.mDrawableSrc.top;
            z = true;
        }
        if (this.mDrawableFloat.right > this.mDrawableSrc.right) {
            i = this.mDrawableSrc.right - this.mDrawableFloat.width();
            z = true;
        }
        if (this.mDrawableFloat.bottom > this.mDrawableSrc.bottom) {
            i2 = this.mDrawableSrc.bottom - this.mDrawableFloat.height();
        } else {
            z2 = z;
        }
        this.mDrawableFloat.offsetTo(i, i2);
        if (z2) {
            invalidate();
        }
    }

    public Bitmap getCropImage() {
        int width = getWidth();
        int height = getHeight();
        if (width > 0 && height > 0) {
            try {
                Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                Drawable drawable = this.mDrawable;
                if (drawable == null) {
                    return createBitmap;
                }
                try {
                    drawable.draw(canvas);
                    int intrinsicWidth = this.mDrawable.getIntrinsicWidth();
                    Matrix matrix = new Matrix();
                    float width2 = intrinsicWidth / this.mDrawableDst.width();
                    matrix.postScale(width2, width2);
                    Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap, this.mDrawableFloat.left, this.mDrawableFloat.top, this.mDrawableFloat.width(), this.mDrawableFloat.height(), matrix, true);
                    if (createBitmap2 != createBitmap) {
                        createBitmap.recycle();
                    }
                    return createBitmap2;
                } catch (Throwable unused) {
                    return createBitmap;
                }
            } catch (Exception unused2) {
            }
        }
        return null;
    }

    public int dipTopx(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}

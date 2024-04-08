package com.picspool.lib.view.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

/* loaded from: classes3.dex */
public class DMBorderImageView extends DMIgnoreRecycleImageView {
    int borderColor;
    float borderWidth;
    RectF boundRect;
    float circleBorderRadius;
    Bitmap imageBorderBitmap;
    int imageColor;
    boolean isCircle;
    boolean isCircleBorder;
    boolean isFillet;
    boolean isImageBorder;
    boolean isShowBorder;
    public Context mContext;
    Paint paint;
    int radius;
    Bitmap srcBitmap;

    public DMBorderImageView(Context context) {
        super(context);
        this.borderColor = 0;
        this.borderWidth = 5.0f;
        this.paint = new Paint();
        this.boundRect = new RectF();
        this.isShowBorder = false;
        this.isCircleBorder = false;
        this.circleBorderRadius = 50.0f;
        this.isImageBorder = false;
        this.imageBorderBitmap = null;
        this.srcBitmap = null;
        this.isCircle = false;
        this.radius = 0;
        this.isFillet = false;
        this.imageColor = 0;
        this.mContext = context;
    }

    public DMBorderImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.borderColor = 0;
        this.borderWidth = 5.0f;
        this.paint = new Paint();
        this.boundRect = new RectF();
        this.isShowBorder = false;
        this.isCircleBorder = false;
        this.circleBorderRadius = 50.0f;
        this.isImageBorder = false;
        this.imageBorderBitmap = null;
        this.srcBitmap = null;
        this.isCircle = false;
        this.radius = 0;
        this.isFillet = false;
        this.imageColor = 0;
        this.mContext = context;
    }

    public DMBorderImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.borderColor = 0;
        this.borderWidth = 5.0f;
        this.paint = new Paint();
        this.boundRect = new RectF();
        this.isShowBorder = false;
        this.isCircleBorder = false;
        this.circleBorderRadius = 50.0f;
        this.isImageBorder = false;
        this.imageBorderBitmap = null;
        this.srcBitmap = null;
        this.isCircle = false;
        this.radius = 0;
        this.isFillet = false;
        this.imageColor = 0;
        this.mContext = context;
    }

    public int getRadius() {
        return this.radius;
    }

    public void setRadius(int i) {
        this.radius = i;
    }

    public void setImageColor(int i) {
        this.imageColor = i;
    }

    public int getImageColor() {
        return this.imageColor;
    }

    public boolean isCircleBorder() {
        return this.isCircleBorder;
    }

    public float getCircleBorderRadius() {
        return this.circleBorderRadius;
    }

    public void setCircleBorder(boolean z, float f) {
        this.isCircleBorder = z;
        this.circleBorderRadius = f;
    }

    @Override // com.picspool.lib.view.image.DMIgnoreRecycleImageView, android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            super.setImageBitmap(null);
        } else {
            this.srcBitmap = bitmap;
            super.setImageBitmap(bitmap);
        }
        invalidate();
    }

    public void setShowBorder(boolean z) {
        this.isShowBorder = z;
    }

    public void setShowImageBorder(boolean z, Bitmap bitmap) {
        this.isImageBorder = z;
        this.imageBorderBitmap = bitmap;
    }

    public boolean isShowBorder() {
        return this.isShowBorder;
    }

    public void setBorderWidth(float f) {
        this.borderWidth = f;
    }

    public void setFilletState(boolean z) {
        this.isFillet = z;
    }

    public boolean getFilletState() {
        return this.isFillet;
    }

    public void setCircleState(boolean z) {
        this.isCircle = z;
    }

    public boolean getCircleState() {
        return this.isCircle;
    }

    public void setBorderColor(int i) {
        this.borderColor = i;
    }

    public void setCircleRadius(boolean z, int i) {
        this.isCircle = z;
        this.radius = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.view.image.DMIgnoreRecycleImageView, android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        Bitmap radiusRectBitmap;
        Rect rect;
        Rect rect2;
        if (!this.isCircle) {
            if (!this.isFillet) {
                Bitmap bitmap = this.srcBitmap;
                if (bitmap != null && !bitmap.isRecycled()) {
                    super.onDraw(canvas);
                }
            } else {
                Bitmap bitmap2 = this.srcBitmap;
                if (bitmap2 != null && !bitmap2.isRecycled()) {
                    Bitmap filletBitmap = getFilletBitmap(this.srcBitmap);
                    Rect rect3 = new Rect(0, 0, filletBitmap.getWidth(), filletBitmap.getHeight());
                    new Rect(0, 0, getWidth(), getWidth());
                    if (getWidth() > getHeight()) {
                        rect2 = new Rect((getWidth() - getHeight()) / 2, 0, getHeight() + ((getWidth() - getHeight()) / 2), getHeight());
                    } else {
                        rect2 = new Rect(0, (getHeight() - getWidth()) / 2, getWidth(), getWidth() + ((getHeight() - getWidth()) / 2));
                    }
                    this.paint.reset();
                    canvas.drawBitmap(filletBitmap, rect3, rect2, this.paint);
                    if (filletBitmap != this.srcBitmap && filletBitmap != null && !filletBitmap.isRecycled()) {
                        filletBitmap.recycle();
                    }
                } else if (this.imageColor != 0) {
                    this.paint.setAntiAlias(true);
                    canvas.drawARGB(0, 0, 0, 0);
                    this.paint.setColor(this.imageColor);
                    canvas.drawRoundRect(new RectF(new Rect(0, 0, getWidth(), getHeight())), 10.0f, 10.0f, this.paint);
                }
            }
            if (this.isShowBorder) {
                if (this.isImageBorder) {
                    this.paint.reset();
                    this.paint.setAntiAlias(true);
                    this.boundRect.left = 0.0f;
                    this.boundRect.top = 0.0f;
                    this.boundRect.right = getWidth();
                    this.boundRect.bottom = getHeight();
                    Bitmap bitmap3 = this.imageBorderBitmap;
                    if (bitmap3 == null || bitmap3.isRecycled()) {
                        return;
                    }
                    canvas.drawBitmap(this.imageBorderBitmap, (Rect) null, this.boundRect, this.paint);
                    return;
                } else if (!this.isCircleBorder) {
                    this.boundRect.left = 0.0f;
                    this.boundRect.top = 0.0f;
                    this.boundRect.right = getWidth();
                    this.boundRect.bottom = getHeight();
                    this.paint.reset();
                    this.paint.setAntiAlias(true);
                    this.paint.setColor(this.borderColor);
                    this.paint.setStyle(Paint.Style.STROKE);
                    this.paint.setStrokeWidth(this.borderWidth);
                    canvas.drawRect(this.boundRect, this.paint);
                    return;
                } else {
                    float width = (getHeight() > getWidth() ? getWidth() : getHeight()) / 2.0f;
                    float f = this.circleBorderRadius;
                    if (width > f) {
                        width = f;
                    }
                    this.paint.reset();
                    this.paint.setAntiAlias(true);
                    this.paint.setColor(this.borderColor);
                    this.paint.setStyle(Paint.Style.STROKE);
                    this.paint.setStrokeWidth(this.borderWidth);
                    canvas.drawCircle(getWidth() / 2, getHeight() / 2, width - 1.0f, this.paint);
                    return;
                }
            }
            return;
        }
        Bitmap bitmap4 = this.srcBitmap;
        if (bitmap4 != null && !bitmap4.isRecycled()) {
            if (this.radius == 0) {
                radiusRectBitmap = getCircleBitmap(this.srcBitmap);
            } else {
                radiusRectBitmap = getRadiusRectBitmap(this.srcBitmap);
            }
            Rect rect4 = new Rect(0, 0, radiusRectBitmap.getWidth(), radiusRectBitmap.getHeight());
            new Rect(0, 0, getWidth(), getWidth());
            if (getWidth() > getHeight()) {
                rect = new Rect((getWidth() - getHeight()) / 2, 0, getHeight() + ((getWidth() - getHeight()) / 2), getHeight());
            } else {
                rect = new Rect(0, (getHeight() - getWidth()) / 2, getWidth(), getWidth() + ((getHeight() - getWidth()) / 2));
            }
            this.paint.reset();
            canvas.drawBitmap(radiusRectBitmap, rect4, rect, this.paint);
            if (radiusRectBitmap == this.srcBitmap || radiusRectBitmap == null || radiusRectBitmap.isRecycled()) {
                return;
            }
            radiusRectBitmap.recycle();
        } else if (this.imageColor != 0) {
            this.paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            this.paint.setColor(this.imageColor);
            int width2 = getWidth();
            if (width2 > getHeight()) {
                width2 = getHeight();
            }
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, width2 / 2, this.paint);
        }
    }

    private Bitmap getFilletBitmap(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        this.paint.reset();
        this.paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        this.paint.setColor(-12434878);
        canvas.drawRoundRect(new RectF(rect), 10.0f, 10.0f, this.paint);
        this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, this.paint);
        return createBitmap;
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        this.paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        this.paint.setColor(-12434878);
        float width = bitmap.getWidth() / 2;
        canvas.drawCircle(width, width, width, this.paint);
        this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, this.paint);
        return createBitmap;
    }

    private Bitmap getRadiusRectBitmap(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        this.paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        this.paint.setColor(-12434878);
        bitmap.getWidth();
        RectF rectF = new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight());
        int i = this.radius;
        canvas.drawRoundRect(rectF, i, i, this.paint);
        this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, this.paint);
        return createBitmap;
    }
}

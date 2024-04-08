package com.picspool.lib.text;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;

/* loaded from: classes3.dex */
public class DMImager {
    private DMTextDrawer BMTextDrawer;
    private Paint paint = new Paint();
    private Rect bitmapRect = new Rect();
    private List<ImagerDrawable> drawables = new ArrayList();

    public DMImager(DMTextDrawer dMTextDrawer) {
        this.BMTextDrawer = dMTextDrawer;
    }

    public void updateData() {
        float f;
        float f2;
        float f3;
        float f4 = 0;
        List<ImagerDrawable> list = this.drawables;
        if (list != null) {
            float f5 = 0.0f;
            try {
                f4 = 0.0f;
                f = 0.0f;
                f2 = 0.0f;
                f3 = 0.0f;
                for (ImagerDrawable imagerDrawable : list) {
                    try {
                        imagerDrawable.updateData();
                        if (f4 == 0.0f) {
                            f4 = imagerDrawable.getDrawRect().left;
                        }
                        if (f == 0.0f) {
                            f = imagerDrawable.getDrawRect().right;
                        }
                        if (f2 == 0.0f) {
                            f2 = imagerDrawable.getDrawRect().top;
                        }
                        if (f3 == 0.0f) {
                            f3 = imagerDrawable.getDrawRect().bottom;
                        }
                        if (f4 > imagerDrawable.getDrawRect().left) {
                            f4 = imagerDrawable.getDrawRect().left;
                        }
                        if (f < imagerDrawable.getDrawRect().right) {
                            f = imagerDrawable.getDrawRect().right;
                        }
                        if (f2 > imagerDrawable.getDrawRect().top) {
                            f2 = imagerDrawable.getDrawRect().top;
                        }
                        if (f3 < imagerDrawable.getDrawRect().bottom) {
                            f3 = imagerDrawable.getDrawRect().bottom;
                        }
                    } catch (Exception e) {
                        e = e;
                        f5 = f4;
                        e.printStackTrace();
                        f4 = f5;
                        this.bitmapRect.set((int) f4, (int) f2, (int) f, (int) f3);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                f = 0.0f;
                f2 = 0.0f;
                f3 = 0.0f;
            }
            this.bitmapRect.set((int) f4, (int) f2, (int) f, (int) f3);
        }
    }

    public void drawInCanvas(Canvas canvas, int i, int i2) {
        List<ImagerDrawable> list = this.drawables;
        if (list != null) {
            try {
                for (ImagerDrawable imagerDrawable : list) {
                    imagerDrawable.draw(canvas, i, i2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Rect getImageContentRect() {
        return this.bitmapRect;
    }

    public void setImagerDrawable(StretchDrawable stretchDrawable, LeftDrawable leftDrawable, TopDrawable topDrawable, RightDrawable rightDrawable, BottomDrawable bottomDrawable) {
        if (stretchDrawable != null) {
            this.drawables.add(stretchDrawable);
        }
        if (leftDrawable != null) {
            this.drawables.add(leftDrawable);
        }
        if (topDrawable != null) {
            this.drawables.add(topDrawable);
        }
        if (rightDrawable != null) {
            this.drawables.add(rightDrawable);
        }
        if (bottomDrawable != null) {
            this.drawables.add(bottomDrawable);
        }
    }

    public void setImagerDrawable(ImageDrawable imageDrawable) {
        if (imageDrawable != null) {
            this.drawables.add(imageDrawable);
        }
    }

    public void cleanImagerDrawable() {
        List<ImagerDrawable> list = this.drawables;
        if (list != null) {
            for (ImagerDrawable imagerDrawable : list) {
                imagerDrawable.clean();
            }
        }
        this.drawables.clear();
        updateData();
    }

    public int getAlpha() {
        return this.paint.getAlpha();
    }

    public void setAlpha(int i) {
        this.paint.setAlpha(i);
        for (ImagerDrawable imagerDrawable : this.drawables) {
            imagerDrawable.setAlpha(i);
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class ImagerDrawable {
        protected DMTextDrawer BMTextDrawer;
        protected int containerHeight;
        protected int containerWidth;
        protected RectF drawRect;
        protected Drawable drawable;

        /* renamed from: h */
        protected int f1992h;
        protected RectF paddingRectPx;

        /* renamed from: w */
        protected int f1993w;

        /* renamed from: x */
        protected int f1994x;

        /* renamed from: y */
        protected int f1995y;

        public abstract void updateData();

        public void draw(Canvas canvas, int i, int i2) {
            if (canvas == null || this.drawable == null || this.drawRect == null || this.BMTextDrawer.getTextString().length() == 0) {
                return;
            }
            this.drawable.setBounds(((int) this.drawRect.left) + i, ((int) this.drawRect.top) + i2, i + ((int) this.drawRect.right), i2 + ((int) this.drawRect.bottom));
            this.drawable.draw(canvas);
        }

        public RectF getDrawRect() {
            return this.drawRect;
        }

        public void clean() {
            this.drawable.setCallback(null);
            Drawable drawable = this.drawable;
            if (drawable instanceof BitmapDrawable) {
                ((BitmapDrawable) drawable).getBitmap().recycle();
            }
            this.drawable = null;
        }

        public void setAlpha(int i) {
            Drawable drawable = this.drawable;
            if (drawable != null) {
                drawable.setAlpha(i);
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class StretchDrawable extends ImagerDrawable {
        public StretchDrawable(DMTextDrawer dMTextDrawer, Drawable drawable, Rect rect) {
            this.BMTextDrawer = dMTextDrawer;
            this.drawable = drawable;
            this.paddingRectPx = new RectF(DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rect.left), DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rect.top), DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rect.right), DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rect.bottom));
            this.drawRect = new RectF();
        }

        @Override // com.picspool.lib.text.DMImager.ImagerDrawable
        public void updateData() {
            if (this.BMTextDrawer.getTextString().length() != 0) {
                this.drawRect.set(this.paddingRectPx.left, this.paddingRectPx.top, this.paddingRectPx.left + this.BMTextDrawer.getTextContentRect().width() + (-this.paddingRectPx.left) + this.paddingRectPx.right, this.paddingRectPx.top + this.BMTextDrawer.getTextContentRect().height() + (-this.paddingRectPx.top) + this.paddingRectPx.bottom);
                return;
            }
            int dip2px = DMScreenInfoUtil.dip2px(this.BMTextDrawer.getContext(), 30.0f);
            this.drawRect.set(0.0f, 0.0f, dip2px / 2, dip2px);
        }
    }

    /* loaded from: classes3.dex */
    public static class ImageDrawable extends ImagerDrawable {
        public ImageDrawable(DMTextDrawer dMTextDrawer, Drawable drawable, int i, int i2, int i3, int i4, int i5, int i6) {
            this.BMTextDrawer = dMTextDrawer;
            this.drawable = drawable;
            this.f1994x = i;
            this.f1995y = i2;
            this.f1993w = i3;
            this.f1992h = i4;
            this.containerWidth = i5;
            this.containerHeight = i6;
            this.drawRect = new RectF();
        }

        @Override // com.picspool.lib.text.DMImager.ImagerDrawable
        public void updateData() {
            if (this.BMTextDrawer.getTextString().length() != 0) {
                int width = this.BMTextDrawer.getTextContentRect().width();
                int height = this.BMTextDrawer.getTextContentRect().height();
                float f = width;
                float f2 = f * 1.0f;
                float f3 = height;
                float f4 = ((this.containerWidth * 1.0f) / this.containerHeight) * 1.0f;
                if (width < height) {
                    f = f3 * 1.0f * f4;
                } else {
                    f3 = (f2 / f4) * 1.0f;
                }
                this.paddingRectPx = new RectF((((this.f1994x * f) * 1.0f) / this.containerWidth) * 1.0f, (((this.f1995y * f3) * 1.0f) / this.containerHeight) * 1.0f, (((((this.containerWidth - this.f1993w) + this.f1994x) * f) * 1.0f) / this.containerWidth) * 1.0f, (((((this.containerHeight - this.f1992h) + this.f1995y) * f3) * 1.0f) / this.containerHeight) * 1.0f);
                this.drawRect.set(this.paddingRectPx.left, this.paddingRectPx.top, this.paddingRectPx.left + f + (-this.paddingRectPx.left) + this.paddingRectPx.right, this.paddingRectPx.top + f3 + (-this.paddingRectPx.top) + this.paddingRectPx.bottom);
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class LeftDrawable extends ImagerDrawable {
        public LeftDrawable(DMTextDrawer dMTextDrawer, Drawable drawable, RectF rectF) {
            this.BMTextDrawer = dMTextDrawer;
            this.drawable = drawable;
            this.paddingRectPx = new RectF(DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.left), rectF.top, rectF.right, rectF.bottom);
            this.drawRect = new RectF();
        }

        @Override // com.picspool.lib.text.DMImager.ImagerDrawable
        public void updateData() {
            Rect rect = new Rect();
            String textString = this.BMTextDrawer.getTextString();
            this.BMTextDrawer.getPaint().getTextBounds(textString, 0, textString.length(), rect);
            int i = -rect.top;
            float textSize = this.paddingRectPx.top * this.BMTextDrawer.getPaint().getTextSize();
            float textSize2 = this.paddingRectPx.right * this.BMTextDrawer.getPaint().getTextSize();
            this.drawRect.set((int) (this.paddingRectPx.left - (this.paddingRectPx.bottom * textSize2)), ((int) textSize) + i, (int) this.paddingRectPx.left, i + ((int) (textSize + textSize2)));
        }
    }

    /* loaded from: classes3.dex */
    public static class TopDrawable extends ImagerDrawable {
        public TopDrawable(DMTextDrawer dMTextDrawer, Drawable drawable, RectF rectF) {
            this.BMTextDrawer = dMTextDrawer;
            this.drawable = drawable;
            this.paddingRectPx = new RectF(DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.left), DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.top), DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.right), DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.bottom));
            this.drawRect = new RectF();
        }

        @Override // com.picspool.lib.text.DMImager.ImagerDrawable
        public void updateData() {
            float f = this.paddingRectPx.left;
            float f2 = -(this.paddingRectPx.top + this.paddingRectPx.bottom);
            this.drawRect.set(f, f2, ((this.BMTextDrawer.getTextContentRect().width() - this.paddingRectPx.left) - this.paddingRectPx.right) + f, this.paddingRectPx.bottom + f2);
        }
    }

    /* loaded from: classes3.dex */
    public static class RightDrawable extends ImagerDrawable {
        public RightDrawable(DMTextDrawer dMTextDrawer, Drawable drawable, RectF rectF) {
            this.BMTextDrawer = dMTextDrawer;
            this.drawable = drawable;
            this.paddingRectPx = new RectF(DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.left), rectF.top, rectF.right, rectF.bottom);
            this.drawRect = new RectF();
        }

        @Override // com.picspool.lib.text.DMImager.ImagerDrawable
        public void updateData() {
            Rect rect = new Rect();
            String textString = this.BMTextDrawer.getTextString();
            this.BMTextDrawer.getPaint().getTextBounds(textString, 0, textString.length(), rect);
            int i = -rect.top;
            float textSize = this.paddingRectPx.right * this.BMTextDrawer.getPaint().getTextSize();
            float textSize2 = this.paddingRectPx.top * this.BMTextDrawer.getPaint().getTextSize();
            float width = this.BMTextDrawer.getTextContentRect().width();
            this.drawRect.set((int) (this.paddingRectPx.left + width), ((int) textSize2) + i, (int) (width + this.paddingRectPx.left + (this.paddingRectPx.bottom * textSize)), i + ((int) (textSize2 + textSize)));
        }
    }

    /* loaded from: classes3.dex */
    public static class BottomDrawable extends ImagerDrawable {
        public BottomDrawable(DMTextDrawer dMTextDrawer, Drawable drawable, RectF rectF) {
            this.BMTextDrawer = dMTextDrawer;
            this.drawable = drawable;
            this.paddingRectPx = new RectF(DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.left), DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.top), DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.right), DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.bottom));
            this.drawRect = new RectF();
        }

        @Override // com.picspool.lib.text.DMImager.ImagerDrawable
        public void updateData() {
            float f = this.paddingRectPx.left;
            float height = this.BMTextDrawer.getTextContentRect().height() + this.paddingRectPx.top;
            this.drawRect.set(f, height, ((this.BMTextDrawer.getTextContentRect().width() - this.paddingRectPx.left) - this.paddingRectPx.right) + f, this.paddingRectPx.bottom + height);
        }
    }
}

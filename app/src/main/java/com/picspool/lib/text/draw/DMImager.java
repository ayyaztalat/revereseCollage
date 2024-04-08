package com.picspool.lib.text.draw;

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
    private Paint paint = new Paint();
    private Rect bitmapRect = new Rect();
    private List<ImagerDrawable> drawables = new ArrayList();

    public DMImager(DMTextDrawer dMTextDrawer) {
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
        protected RectF drawRect;
        protected Drawable drawable;
        protected RectF paddingRectPx;
        protected DMTextDrawer textDrawer;

        public abstract void updateData();

        public void draw(Canvas canvas, int i, int i2) {
            if (canvas == null || this.drawable == null || this.drawRect == null || this.textDrawer.getTextString().length() == 0) {
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
            this.textDrawer = dMTextDrawer;
            this.drawable = drawable;
            this.paddingRectPx = new RectF(DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rect.left), DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rect.top), DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rect.right), DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rect.bottom));
            this.drawRect = new RectF();
        }

        @Override // com.picspool.lib.text.draw.DMImager.ImagerDrawable
        public void updateData() {
            if (this.textDrawer.getTextString().length() != 0) {
                this.drawRect.set(this.paddingRectPx.left, this.paddingRectPx.top, this.paddingRectPx.left + this.textDrawer.getTextContentRect().width() + (-this.paddingRectPx.left) + this.paddingRectPx.right, this.paddingRectPx.top + this.textDrawer.getTextContentRect().height() + (-this.paddingRectPx.top) + this.paddingRectPx.bottom);
                return;
            }
            int dip2px = DMScreenInfoUtil.dip2px(this.textDrawer.getContext(), 30.0f);
            this.drawRect.set(0.0f, 0.0f, dip2px / 2, dip2px);
        }
    }

    /* loaded from: classes3.dex */
    public static class LeftDrawable extends ImagerDrawable {
        public LeftDrawable(DMTextDrawer dMTextDrawer, Drawable drawable, RectF rectF) {
            this.textDrawer = dMTextDrawer;
            this.drawable = drawable;
            this.paddingRectPx = new RectF(DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.left), rectF.top, rectF.right, rectF.bottom);
            this.drawRect = new RectF();
        }

        @Override // com.picspool.lib.text.draw.DMImager.ImagerDrawable
        public void updateData() {
            Rect rect = new Rect();
            String textString = this.textDrawer.getTextString();
            this.textDrawer.getPaint().getTextBounds(textString, 0, textString.length(), rect);
            int i = -rect.top;
            float textSize = this.paddingRectPx.top * this.textDrawer.getPaint().getTextSize();
            float textSize2 = this.paddingRectPx.right * this.textDrawer.getPaint().getTextSize();
            this.drawRect.set((int) (this.paddingRectPx.left - (this.paddingRectPx.bottom * textSize2)), ((int) textSize) + i, (int) this.paddingRectPx.left, i + ((int) (textSize + textSize2)));
        }
    }

    /* loaded from: classes3.dex */
    public static class TopDrawable extends ImagerDrawable {
        public TopDrawable(DMTextDrawer dMTextDrawer, Drawable drawable, RectF rectF) {
            this.textDrawer = dMTextDrawer;
            this.drawable = drawable;
            this.paddingRectPx = new RectF(DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.left), DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.top), DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.right), DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.bottom));
            this.drawRect = new RectF();
        }

        @Override // com.picspool.lib.text.draw.DMImager.ImagerDrawable
        public void updateData() {
            float f = this.paddingRectPx.left;
            float f2 = -(this.paddingRectPx.top + this.paddingRectPx.bottom);
            this.drawRect.set(f, f2, ((this.textDrawer.getTextContentRect().width() - this.paddingRectPx.left) - this.paddingRectPx.right) + f, this.paddingRectPx.bottom + f2);
        }
    }

    /* loaded from: classes3.dex */
    public static class RightDrawable extends ImagerDrawable {
        public RightDrawable(DMTextDrawer dMTextDrawer, Drawable drawable, RectF rectF) {
            this.textDrawer = dMTextDrawer;
            this.drawable = drawable;
            this.paddingRectPx = new RectF(DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.left), rectF.top, rectF.right, rectF.bottom);
            this.drawRect = new RectF();
        }

        @Override // com.picspool.lib.text.draw.DMImager.ImagerDrawable
        public void updateData() {
            Rect rect = new Rect();
            String textString = this.textDrawer.getTextString();
            this.textDrawer.getPaint().getTextBounds(textString, 0, textString.length(), rect);
            int i = -rect.top;
            float textSize = this.paddingRectPx.right * this.textDrawer.getPaint().getTextSize();
            float textSize2 = this.paddingRectPx.top * this.textDrawer.getPaint().getTextSize();
            float width = this.textDrawer.getTextContentRect().width();
            this.drawRect.set((int) (this.paddingRectPx.left + width), ((int) textSize2) + i, (int) (width + this.paddingRectPx.left + (this.paddingRectPx.bottom * textSize)), i + ((int) (textSize2 + textSize)));
        }
    }

    /* loaded from: classes3.dex */
    public static class BottomDrawable extends ImagerDrawable {
        public BottomDrawable(DMTextDrawer dMTextDrawer, Drawable drawable, RectF rectF) {
            this.textDrawer = dMTextDrawer;
            this.drawable = drawable;
            this.paddingRectPx = new RectF(DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.left), DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.top), DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.right), DMScreenInfoUtil.dip2px(dMTextDrawer.getContext(), rectF.bottom));
            this.drawRect = new RectF();
        }

        @Override // com.picspool.lib.text.draw.DMImager.ImagerDrawable
        public void updateData() {
            float f = this.paddingRectPx.left;
            float height = this.textDrawer.getTextContentRect().height() + this.paddingRectPx.top;
            this.drawRect.set(f, height, ((this.textDrawer.getTextContentRect().width() - this.paddingRectPx.left) - this.paddingRectPx.right) + f, this.paddingRectPx.bottom + height);
        }
    }
}

package com.picspool.lib.text.edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import androidx.core.view.ViewCompat;
import com.picspool.lib.text.draw.DMImager;
import com.picspool.lib.text.draw.DMTextDrawer;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMTextFixedView extends EditText {
    private DMTextCaret caret;
    private OnViewTouchDoubleTapListener doubleTapListener;
    private Handler handler;
    private boolean iniEndFlag;
    protected Paint mPaint;
    private RectF mRect;
    private RectF properRectF;
    private boolean showCaret;
    private DMTextDrawer textDrawer;
    private float textOffset;
    private int textProportion;
    private int textcolor;
    private DMTextTouchHandler touchHandler;

    /* loaded from: classes3.dex */
    public interface OnViewTouchDoubleTapListener {
        void onDoubleTap(View view);
    }

    public DMTextFixedView(Context context) {
        super(context);
        this.textcolor = -1;
        this.iniEndFlag = false;
        this.showCaret = false;
        this.textProportion = 7;
        this.textOffset = 1.0f;
        init();
    }

    public DMTextFixedView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.textcolor = -1;
        this.iniEndFlag = false;
        this.showCaret = false;
        this.textProportion = 7;
        this.textOffset = 1.0f;
        init();
    }

    public DMTextFixedView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.textcolor = -1;
        this.iniEndFlag = false;
        this.showCaret = false;
        this.textProportion = 7;
        this.textOffset = 1.0f;
        init();
    }

    private void init() {
        this.textOffset = getContext().getResources().getDimension(R.dimen.text_offset);
        this.touchHandler = new DMTextTouchHandler(this);
        this.handler = new Handler();
        this.caret = new DMTextCaret(this);
        this.textProportion = (int) getResources().getDimension(R.dimen.eidt_text_screen_proportion);
        addTextChangedListener(new TextWatcher() { // from class: com.picspool.lib.text.edit.DMTextFixedView.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                DMTextFixedView.this.setContentText(editable.toString());
            }
        });
        setHorizontallyScrolling(true);
        getPaint().setTextSize(1.0E-6f);
    }

    private void setBackground16(View view, Drawable drawable) {
        view.setBackground(drawable);
    }

    public void drawTextWithCanvas(Canvas canvas) {
        this.textDrawer.drawInCanvas(canvas, (int) this.properRectF.left, (int) this.properRectF.top);
    }

    public void setTextDrawer(DMTextDrawer dMTextDrawer) {
        DMTextCaret dMTextCaret;
        if (dMTextDrawer != null) {
            setText(dMTextDrawer.getTextString());
            this.textDrawer = dMTextDrawer;
            if (dMTextDrawer == null) {
                this.textDrawer = new DMTextDrawer(getContext(), "");
            }
            this.mPaint = this.textDrawer.getPaint();
            if (this.mRect == null) {
                this.mRect = new RectF();
                this.iniEndFlag = true;
            }
            textChanged();
            if (this.showCaret && (dMTextCaret = this.caret) != null) {
                dMTextCaret.iniCaret(getWidth(), getHeight());
            }
            int length = dMTextDrawer.getTextString().length();
            if (length <= getText().toString().length()) {
                setSelection(length);
            } else {
                setSelection(getText().toString().length());
            }
        } else if (this.textDrawer != null) {
            this.textDrawer = null;
        }
    }

    public DMTextDrawer getTextDrawer() {
        return this.textDrawer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RectF getProperFontRect(RectF rectF, String str) {
        Rect textContentRect = this.textDrawer.getTextContentRect();
        float height = (getHeight() - textContentRect.height()) / 2;
        float width = (getWidth() - textContentRect.width()) / 2;
        return new RectF(width, height, textContentRect.width() + width, textContentRect.height() + height);
    }

    public RectF getProperRect() {
        return this.properRectF;
    }

    private void changePaintSize() {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer == null || dMTextDrawer.getTextString().length() == 0) {
            return;
        }
        float f = 1.0f;
        int width = (int) (this.mRect.width() - (this.textDrawer.getContentRect().width() - this.textDrawer.getTextContentRect().width()));
        String[] textLineArray = this.textDrawer.getTextLineArray();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        for (String str : textLineArray) {
            if (str.length() > i) {
                i = str.length();
                i3 = i2;
            }
            i2++;
        }
        Rect rect = new Rect();
        while (this.mPaint != null && width > 0 && this.mRect.height() != 0.0f) {
            this.mPaint.setTextSize(f);
            if (i3 >= textLineArray.length) {
                return;
            }
            this.mPaint.getTextBounds(textLineArray[i3], 0, textLineArray[i3].length(), rect);
            float height = rect.height();
            float fontSpacing = (this.mPaint.getFontSpacing() + this.textDrawer.getLineSpaceOffset()) * textLineArray.length;
            if (rect.width() + (this.textDrawer.getTextSpaceOffset() * textLineArray[i3].length()) > width || height > this.mRect.height() || fontSpacing > getHeight()) {
                this.textDrawer.setTextSize(f - this.textOffset);
                return;
            }
            f += this.textOffset;
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.textDrawer == null || this.properRectF == null || getWidth() <= 0) {
            return;
        }
        this.mPaint.setAntiAlias(true);
        drawTextWithCanvas(canvas);
        DMTextCaret dMTextCaret = this.caret;
        if (dMTextCaret != null) {
            dMTextCaret.onDrawCaret(canvas);
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        if (this.textDrawer == null || !this.iniEndFlag || i2 == 0 || i == 0) {
            return;
        }
        float f = i2;
        float f2 = f / this.textProportion;
        float f3 = (f / 2.0f) - (f2 / 2.0f);
        this.mRect = new RectF(0.0f, f3, i, f2 + f3);
        this.handler.post(new Runnable() { // from class: com.picspool.lib.text.edit.DMTextFixedView.2
            @Override // java.lang.Runnable
            public void run() {
                if (DMTextFixedView.this.textDrawer == null || DMTextFixedView.this.mRect == null) {
                    return;
                }
                if (!DMTextFixedView.this.showCaret) {
                    DMTextFixedView.this.caret.iniCaret(DMTextFixedView.this.getWidth(), DMTextFixedView.this.getHeight());
                    DMTextFixedView.this.showCaret = true;
                }
                DMTextFixedView dMTextFixedView = DMTextFixedView.this;
                dMTextFixedView.properRectF = dMTextFixedView.getProperFontRect(dMTextFixedView.mRect, DMTextFixedView.this.textDrawer.getTextString());
                DMTextFixedView dMTextFixedView2 = DMTextFixedView.this;
                dMTextFixedView2.setSelection(dMTextFixedView2.getSelectionEnd());
            }
        });
        textChanged();
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer != null) {
            if (dMTextDrawer.isShowHelpFlag()) {
                setContentText("");
                this.caret.iniCaret(getWidth(), getHeight());
                return true;
            }
            return this.touchHandler.onTouchEvent(motionEvent);
        }
        return false;
    }

    @Override // android.widget.EditText
    public void setSelection(int i) {
        super.setSelection(i);
        DMTextCaret dMTextCaret = this.caret;
        if (dMTextCaret != null) {
            dMTextCaret.changeSelection(i);
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        DMTextCaret dMTextCaret = this.caret;
        if (dMTextCaret != null) {
            dMTextCaret.startShowCaret();
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        DMTextCaret dMTextCaret = this.caret;
        if (dMTextCaret != null) {
            dMTextCaret.stopShowCaret();
        }
    }

    public void setTextTypeface(Typeface typeface) {
        this.textDrawer.setTypeface(typeface);
        textChanged();
        invalidate();
    }

    public void setBgImage(DMImager.StretchDrawable stretchDrawable, DMImager.LeftDrawable leftDrawable, DMImager.TopDrawable topDrawable, DMImager.RightDrawable rightDrawable, DMImager.BottomDrawable bottomDrawable) {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer != null) {
            dMTextDrawer.setImagerDrawable(stretchDrawable, leftDrawable, topDrawable, rightDrawable, bottomDrawable);
        }
    }

    public void setTextAlpha(int i) {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer != null) {
            dMTextDrawer.setTextAlpha(i);
        }
    }

    public int getTextAlpha() {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer == null) {
            return 0;
        }
        return dMTextDrawer.getTextAlpha();
    }

    public int getTextAddHeight() {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer != null) {
            return dMTextDrawer.getTextAddHeight();
        }
        return 0;
    }

    public void setTextAddHeight(int i) {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer != null) {
            dMTextDrawer.setTextAddHeight(i);
        }
    }

    @Override // android.widget.TextView
    public void setTextColor(int i) {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer != null) {
            dMTextDrawer.setShaderBitmap(null);
            this.textcolor = i;
            this.textDrawer.setColor(i);
            textChanged();
        }
    }

    public int getTextColor() {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer == null) {
            return -1;
        }
        return dMTextDrawer.getTextColor();
    }

    public void setShaderBitmap(Bitmap bitmap) {
        textChanged();
        this.textDrawer.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.textDrawer.setShaderBitmap(bitmap);
        this.textDrawer.setPaintColorIndex(-1);
        invalidate();
    }

    public void setTextBgImage(Bitmap bitmap) {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer != null) {
            dMTextDrawer.setShaderBitmap(bitmap);
            textChanged();
        }
    }

    public DMTextDrawer.TEXTALIGN getTextAlign() {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer != null) {
            return dMTextDrawer.getTextAlign();
        }
        return DMTextDrawer.TEXTALIGN.LEFT;
    }

    public void setTextAlign(DMTextDrawer.TEXTALIGN textalign) {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer != null) {
            dMTextDrawer.setTextAlign(textalign);
            textChanged();
        }
    }

    public Paint getTextPaint() {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer == null) {
            return new Paint();
        }
        return dMTextDrawer.getPaint();
    }

    public void setPaintShadowLayer(DMTextDrawer.SHADOWALIGN shadowalign) {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer != null) {
            dMTextDrawer.setPaintShadowLayer(shadowalign);
            textChanged();
        }
    }

    public DMTextDrawer.SHADOWALIGN getPaintShadowLayer() {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer != null) {
            return dMTextDrawer.getPaintShadowLayer();
        }
        return DMTextDrawer.SHADOWALIGN.NONE;
    }

    public void clearPaintShadowLayer() {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer != null) {
            dMTextDrawer.setPaintShadowLayer(DMTextDrawer.SHADOWALIGN.NONE);
            textChanged();
        }
    }

    public DMTextDrawer.SHADOWALIGN getShadowAlign() {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer != null) {
            return dMTextDrawer.getShadowAlign();
        }
        return DMTextDrawer.SHADOWALIGN.NONE;
    }

    public void textChanged() {
        if (this.textDrawer != null) {
            changePaintSize();
            this.properRectF = getProperFontRect(this.mRect, this.textDrawer.getTextString());
            DMTextCaret dMTextCaret = this.caret;
            if (dMTextCaret != null) {
                dMTextCaret.changeSelection(getSelectionEnd());
            }
        }
    }

    public Rect[] getDrawTextRects() {
        return this.textDrawer.getDrawTextRects();
    }

    public String getTextString() {
        return this.textDrawer.getTextString();
    }

    public String[] getTextLines() {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer == null) {
            return new String[]{""};
        }
        return dMTextDrawer.getTextLineArray();
    }

    public String getContentText() {
        return this.textDrawer.getTextString();
    }

    public void setContentText(String str) {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer != null) {
            if (dMTextDrawer.isShowHelpFlag()) {
                this.textDrawer.setShowHelpFlag(false);
                String str2 = "";
                if (str != "" && this.textDrawer.getTextString().length() <= str.length()) {
                    str2 = str.substring(this.textDrawer.getTextString().length(), str.length());
                }
                this.textDrawer.setText(str2);
                setText(str2);
                setSelection(str2.length());
            } else {
                this.textDrawer.setText(str);
            }
            textChanged();
        }
    }

    public void setDoubleTapListener(OnViewTouchDoubleTapListener onViewTouchDoubleTapListener) {
        this.doubleTapListener = onViewTouchDoubleTapListener;
    }

    /* loaded from: classes3.dex */
    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        public GestureListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (DMTextFixedView.this.doubleTapListener != null) {
                DMTextFixedView.this.doubleTapListener.onDoubleTap(DMTextFixedView.this);
            }
            return super.onDoubleTap(motionEvent);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            super.onLongPress(motionEvent);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return super.onScroll(motionEvent, motionEvent2, f, f2);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return super.onFling(motionEvent, motionEvent2, f, f2);
        }
    }

    public Rect[] getBoundsTextRects() {
        return this.textDrawer.getBoundsTextRects();
    }

    public Rect getContentRects() {
        return this.textDrawer.getTextContentRect();
    }

    public void setShowSideTraces(boolean z) {
        this.textDrawer.setShowSideTraces(z);
    }

    public boolean isShowSideTraces() {
        return this.textDrawer.isShowSideTraces();
    }

    public void setSideTracesColor(int i) {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer != null) {
            dMTextDrawer.setSideTracesColor(i);
        }
    }

    public int getLineSpaceOffset() {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer == null) {
            return 1;
        }
        return dMTextDrawer.getLineSpaceOffset();
    }

    public void setLineSpaceOffset(int i) {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer != null) {
            dMTextDrawer.setLineSpaceOffset(i);
            textChanged();
        }
    }

    public int getTextSpaceOffset() {
        return this.textDrawer.getTextSpaceOffset();
    }

    public void setTextSpaceOffset(int i) {
        this.textDrawer.setTextSpaceOffset(i);
        textChanged();
    }

    public DMTextDrawer.UNDERLINES_STYLE getTextUnderlinesStyle() {
        return this.textDrawer.getUnderlinesStyle();
    }

    public void setTextUnderlinesStyle(DMTextDrawer.UNDERLINES_STYLE underlines_style) {
        this.textDrawer.setUnderlinesStyle(underlines_style);
        invalidate();
    }

    public void cleanBgImage() {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer != null) {
            dMTextDrawer.cleanImagerDrawable();
        }
    }

    public boolean isShowCaretFlag() {
        DMTextCaret dMTextCaret = this.caret;
        if (dMTextCaret == null) {
            return false;
        }
        return dMTextCaret.isShowCaretFlag();
    }

    public void setShowCaretFlag(boolean z) {
        DMTextCaret dMTextCaret = this.caret;
        if (dMTextCaret != null) {
            dMTextCaret.setShowCaretFlag(z);
        }
    }

    public int getBgAlpha() {
        return this.textDrawer.getBgAlpha();
    }

    public void setBgAlpha(int i) {
        this.textDrawer.setBgAlpha(i);
    }

    public DMTextCaret getCaret() {
        return this.caret;
    }

    public void dispose() {
        Bitmap bitmap;
        Drawable background = getBackground();
        if (background != null) {
            background.setCallback(null);
            if ((background instanceof BitmapDrawable) && (bitmap = ((BitmapDrawable) background).getBitmap()) != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        if (Build.VERSION.SDK_INT >= 16) {
            setBackground16(this, null);
        } else {
            setBackgroundDrawable(null);
        }
    }

    public void loadImage() {
        Bitmap bitmap;
        Drawable background = getBackground();
        if (background != null) {
            background.setCallback(null);
            if ((background instanceof BitmapDrawable) && (bitmap = ((BitmapDrawable) background).getBitmap()) != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        if (Build.VERSION.SDK_INT >= 16) {
            setBackground16(this, null);
        } else {
            setBackgroundDrawable(null);
        }
    }
}

package com.picspool.instatextview.edit;

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
import java.util.Map;
import com.picspool.instatextview.resource.manager.DMTextEmojiSelectedManager;
import com.picspool.lib.text.DMImager;
import com.picspool.lib.text.DMTextDrawer;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DM_TextFixedView3 extends androidx.appcompat.widget.AppCompatEditText {
    private DM_TextCaret3 caret;
    private OnViewTouchDoubleTapListener doubleTapListener;
    private Handler handler;
    private boolean iniEndFlag;
    private boolean isFirstIn;
    private boolean isNotUserKeyboardContent;
    protected Paint mPaint;
    private RectF mRect;
    private int previousDefaultLength;
    private RectF properRectF;
    private boolean showCaret;
    private DMTextDrawer textDrawer;
    private float textOffset;
    private int textProportion;
    private int textcolor;
    private DM_TextTouchHandler3 touchHandler;

    /* loaded from: classes3.dex */
    public interface OnViewTouchDoubleTapListener {
        void onDoubleTap(View view);
    }

    public DM_TextFixedView3(Context context) {
        super(context);
        this.textcolor = -1;
        this.iniEndFlag = false;
        this.showCaret = false;
        this.textProportion = 7;
        this.textOffset = 1.0f;
        this.isNotUserKeyboardContent = true;
        this.isFirstIn = true;
        this.previousDefaultLength = 0;
        init();
    }

    public DM_TextFixedView3(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.textcolor = -1;
        this.iniEndFlag = false;
        this.showCaret = false;
        this.textProportion = 7;
        this.textOffset = 1.0f;
        this.isNotUserKeyboardContent = true;
        this.isFirstIn = true;
        this.previousDefaultLength = 0;
        init();
    }

    public DM_TextFixedView3(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.textcolor = -1;
        this.iniEndFlag = false;
        this.showCaret = false;
        this.textProportion = 7;
        this.textOffset = 1.0f;
        this.isNotUserKeyboardContent = true;
        this.isFirstIn = true;
        this.previousDefaultLength = 0;
        init();
    }

    private void init() {
        this.textOffset = getContext().getResources().getDimension(R.dimen.text_offset);
        this.touchHandler = new DM_TextTouchHandler3(this);
        this.handler = new Handler();
        this.caret = new DM_TextCaret3(this);
        this.textProportion = (int) getResources().getDimension(R.dimen.eidt_text_screen_proportion);
        addTextChangedListener(new TextWatcher() { // from class: com.picspool.instatextview.edit.DM_TextFixedView3.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                Map<Integer, Integer> textEmojiSelectedList;
                String obj = editable.toString();
                if (DM_TextFixedView3.this.isNotUserKeyboardContent || obj.length() > 0) {
                    try {
                        if (!DM_TextFixedView3.this.isNotUserKeyboardContent) {
                            if (DM_TextFixedView3.this.isFirstIn) {
                                DM_TextFixedView3.this.isFirstIn = false;
                                DM_TextFixedView3.this.previousDefaultLength = obj.length();
                                return;
                            }
                            DM_TextFixedView3.this.isNotUserKeyboardContent = true;
                            if (obj.length() < DM_TextFixedView3.this.previousDefaultLength) {
                                DM_TextFixedView3.this.previousDefaultLength = 0;
                                editable.clear();
                                return;
                            }
                            String substring = obj.substring(DM_TextFixedView3.this.previousDefaultLength, obj.length());
                            if (substring.length() == 1 && substring.compareTo("人") == 0 && (textEmojiSelectedList = DMTextEmojiSelectedManager.getInstance().getTextEmojiSelectedList()) != null && textEmojiSelectedList.containsKey(Integer.valueOf(obj.length() - 1))) {
                                int intValue = textEmojiSelectedList.get(Integer.valueOf(obj.length() - 1)).intValue();
                                textEmojiSelectedList.clear();
                                DMTextEmojiSelectedManager.getInstance().addSelectedTextEmoji(0, intValue);
                            }
                            DM_TextFixedView3.this.setText(substring);
                            return;
                        }
                        if (obj.length() > 0) {
                            DM_TextFixedView3.this.setSelection(obj.length());
                        }
                        DM_TextFixedView3.this.setContentText(obj);
                    } catch (Exception unused) {
                    }
                }
            }
        });
        setHorizontallyScrolling(true);
        getPaint().setTextSize(1.0E-6f);
    }

    private void setBackground16(View view, Drawable drawable) {
        view.setBackground(drawable);
    }

    public void setTextFixedViewBackgroundDrawable(Drawable drawable) {
        dispose();
        if (Build.VERSION.SDK_INT >= 16) {
            setBackground16(this, drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    public void drawTextWithCanvas(Canvas canvas) {
        this.textDrawer.drawInCanvas(canvas, (int) this.properRectF.left, (int) this.properRectF.top);
    }

    public void setTextDrawer(DMTextDrawer dMTextDrawer) {
        DM_TextCaret3 dM_TextCaret3;
        if (dMTextDrawer != null) {
            setText(dMTextDrawer.getTextString());
            this.textDrawer = dMTextDrawer;
            if (dMTextDrawer == null) {
                this.textDrawer = new DMTextDrawer(getContext(), "");
            }
            if (this.textDrawer.isBackgroundImageDrawable() && this.textDrawer.mBackgroundRes != null) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getContext().getResources(), this.textDrawer.mBackgroundRes.getLocalImageBitmap());
                bitmapDrawable.setBounds(0, 0, this.textDrawer.mBgImageWidth, this.textDrawer.mBgImageHeight);
                this.textDrawer.mBackgroundDrawable = bitmapDrawable;
                setTextFixedViewBackgroundDrawable(bitmapDrawable);
            }
            this.mPaint = this.textDrawer.getPaint();
            if (this.mRect == null) {
                this.mRect = new RectF();
                this.iniEndFlag = true;
            }
            textChanged();
            if (this.showCaret && (dM_TextCaret3 = this.caret) != null) {
                dM_TextCaret3.iniCaret(getWidth(), getHeight());
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
        if (this.textDrawer.mIsImageBackground) {
            Rect textContentRect = this.textDrawer.getTextContentRect();
            float height = ((this.textDrawer.mBgH - textContentRect.height()) / 2.0f) + this.textDrawer.mBgY;
            float width = ((this.textDrawer.mBgW - textContentRect.width()) / 2.0f) + this.textDrawer.mBgX;
            return new RectF(width, height, textContentRect.width() + width, textContentRect.height() + height);
        }
        Rect textContentRect2 = this.textDrawer.getTextContentRect();
        float height2 = (getHeight() - textContentRect2.height()) / 2;
        float width2 = (getWidth() - textContentRect2.width()) / 2;
        return new RectF(width2, height2, textContentRect2.width() + width2, textContentRect2.height() + height2);
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
        this.handler.post(new Runnable() { // from class: com.picspool.instatextview.edit.DM_TextFixedView3.2
            @Override // java.lang.Runnable
            public void run() {
                if (DM_TextFixedView3.this.textDrawer == null || DM_TextFixedView3.this.mRect == null) {
                    return;
                }
                if (!DM_TextFixedView3.this.showCaret) {
                    DM_TextFixedView3.this.caret.iniCaret(DM_TextFixedView3.this.getWidth(), DM_TextFixedView3.this.getHeight());
                    DM_TextFixedView3.this.showCaret = true;
                }
                DM_TextFixedView3 dM_TextFixedView3 = DM_TextFixedView3.this;
                dM_TextFixedView3.properRectF = dM_TextFixedView3.getProperFontRect(dM_TextFixedView3.mRect, DM_TextFixedView3.this.textDrawer.getTextString());
                DM_TextFixedView3 dM_TextFixedView32 = DM_TextFixedView3.this;
                dM_TextFixedView32.setSelection(dM_TextFixedView32.getSelectionEnd());
            }
        });
        textChanged();
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer == null || !dMTextDrawer.isShowHelpFlag()) {
            return false;
        }
        setContentText("");
        this.caret.iniCaret(getWidth(), getHeight());
        return true;
    }

    @Override // android.widget.EditText
    public void setSelection(int i) {
        super.setSelection(i);
        DM_TextCaret3 dM_TextCaret3 = this.caret;
        if (dM_TextCaret3 != null) {
            dM_TextCaret3.changeSelection(i);
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        DM_TextCaret3 dM_TextCaret3 = this.caret;
        if (dM_TextCaret3 != null) {
            dM_TextCaret3.startShowCaret();
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        DM_TextCaret3 dM_TextCaret3 = this.caret;
        if (dM_TextCaret3 != null) {
            dM_TextCaret3.stopShowCaret();
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

    public void setBgImage(DMImager.ImageDrawable imageDrawable) {
        DMTextDrawer dMTextDrawer = this.textDrawer;
        if (dMTextDrawer != null) {
            dMTextDrawer.setImagerDrawable(imageDrawable);
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
            DM_TextCaret3 dM_TextCaret3 = this.caret;
            if (dM_TextCaret3 != null) {
                dM_TextCaret3.changeSelection(getSelectionEnd());
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
            if (DM_TextFixedView3.this.doubleTapListener != null) {
                DM_TextFixedView3.this.doubleTapListener.onDoubleTap(DM_TextFixedView3.this);
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
        DM_TextCaret3 dM_TextCaret3 = this.caret;
        if (dM_TextCaret3 == null) {
            return false;
        }
        return dM_TextCaret3.isShowCaretFlag();
    }

    public void setShowCaretFlag(boolean z) {
        DM_TextCaret3 dM_TextCaret3 = this.caret;
        if (dM_TextCaret3 != null) {
            dM_TextCaret3.setShowCaretFlag(z);
        }
    }

    public int getBgAlpha() {
        return this.textDrawer.getBgAlpha();
    }

    public void setBgAlpha(int i) {
        this.textDrawer.setBgAlpha(i);
    }

    public void setIsUserKeyboardContent(boolean z) {
        this.isNotUserKeyboardContent = z;
    }

    public DM_TextCaret3 getCaret() {
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

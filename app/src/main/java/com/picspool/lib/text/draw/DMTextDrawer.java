package com.picspool.lib.text.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.TextPaint;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import java.util.Arrays;
import com.picspool.lib.text.draw.DMImager;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMTextDrawer {
    private boolean IsShowShadow;
    private Rect[] boundsTextRects;
    private int color;
    private Context context;
    private DMDrawTextHandler drawHandler;
    private Rect[] drawTextRects;
    private int dxShadow;
    private int dyShadow;
    private boolean ignoreLine;
    private DMImager imager;
    private boolean isShowSideTraces;
    private Rect[] lineRects;
    private int lineSpaceOffset;
    @Deprecated
    private String[] lines;
    private int mBorderColor;
    private int mBorderWidth;
    private int mTextAddHeight;
    private int maxHeight;
    private int maxWidth;
    private boolean multiLine;
    private Paint paint;
    private int paintColorIndex;
    private int radiusShadow;
    private Rect rect;
    private Bitmap shaderBMP;
    private int shaderColorIndex;
    private SHADOWALIGN shadowAlign;
    private int shadowColor;
    private boolean showHelpFlag;
    private int sideTracesColorIndex;
    private TextPaint sideTracesPaint;
    private int sideTracesWidthPX;
    private String text;
    private TEXTALIGN textAlign;
    private int textSpaceOffset;
    private String[] textlines;
    private Typeface typeface;
    private int typefaceIndex;
    private UNDERLINES_STYLE underlinesStyle;

    /* loaded from: classes3.dex */
    public enum SHADOWALIGN {
        NONE,
        LEFT_TOP,
        LEFT_BOTTOM,
        BOTTOM,
        RIGHT_TOP,
        RIGHT_BOTTOM,
        TOP
    }

    /* loaded from: classes3.dex */
    public enum TEXTALIGN {
        LEFT,
        RIGHT,
        CENTER
    }

    /* loaded from: classes3.dex */
    public enum UNDERLINES_STYLE {
        NONE,
        SINGLE,
        DOUBLE,
        DASHED,
        POINT_DASHED
    }

    public String getSplitTextString(int i) {
        return "";
    }

    public DMTextDrawer(Context context, String str) {
        this(context, str, 2);
    }

    public DMTextDrawer(Context context, String str, int i) {
        this.textAlign = TEXTALIGN.LEFT;
        this.shadowAlign = SHADOWALIGN.NONE;
        this.underlinesStyle = UNDERLINES_STYLE.NONE;
        this.text = "";
        this.paint = new Paint();
        this.color = -1;
        this.shaderBMP = null;
        this.rect = new Rect();
        this.ignoreLine = true;
        this.multiLine = false;
        this.isShowSideTraces = false;
        this.IsShowShadow = false;
        this.shadowColor = 0;
        this.sideTracesPaint = new TextPaint();
        this.maxWidth = 0;
        this.maxHeight = 0;
        this.lineSpaceOffset = 0;
        this.textSpaceOffset = 0;
        this.mBorderWidth = 0;
        this.mBorderColor = 0;
        this.mTextAddHeight = 0;
        this.showHelpFlag = false;
        this.sideTracesColorIndex = -1;
        this.paintColorIndex = -1;
        this.context = context;
        this.text = str;
        this.paint.setAntiAlias(true);
        this.paint.setDither(true);
        this.typeface = Typeface.DEFAULT;
        this.paint.setColor(-1);
        this.paint.setTypeface(this.typeface);
        if (i >= 0) {
            this.ignoreLine = false;
        }
        this.sideTracesWidthPX = (int) context.getResources().getDimension(R.dimen.side_traces_width);
        this.sideTracesPaint.setFakeBoldText(true);
        this.sideTracesPaint.setAntiAlias(true);
        this.sideTracesPaint.setStyle(Paint.Style.STROKE);
        this.sideTracesPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.sideTracesPaint.setStrokeWidth(this.sideTracesWidthPX);
        this.drawHandler = new DMDrawTextHandler(this);
        this.imager = new DMImager(this);
        this.radiusShadow = (int) context.getResources().getDimension(R.dimen.shadow_radius);
        this.dxShadow = (int) context.getResources().getDimension(R.dimen.shadow_dx);
        this.dyShadow = (int) context.getResources().getDimension(R.dimen.shadow_dy);
        updateData();
    }

    @Deprecated
    private void initRect() {
        if (this.text.contains("\n") && !this.ignoreLine) {
            this.multiLine = true;
            String[] split = this.text.split("\n");
            this.lines = split;
            this.lineRects = new Rect[split.length];
            this.maxHeight = 0;
            this.maxWidth = 0;
            int i = 0;
            while (true) {
                String[] strArr = this.lines;
                if (i < strArr.length) {
                    this.lineRects[i] = getRect(strArr[i]);
                    int i2 = this.lineRects[i].right - this.lineRects[i].left;
                    this.maxHeight += (this.lineRects[i].bottom - this.lineRects[i].top) + this.mTextAddHeight;
                    if (this.maxWidth < i2) {
                        this.maxWidth = i2;
                    }
                    i++;
                } else {
                    this.rect.set(0, 0, this.maxWidth + 15, this.maxHeight + (strArr.length * this.lineSpaceOffset));
                    return;
                }
            }
        } else {
            this.multiLine = false;
            Rect rect = new Rect();
            Paint paint = this.paint;
            String str = this.text;
            paint.getTextBounds(str, 0, str.length(), rect);
            this.rect.set(0, 0, rect.right - rect.left, rect.bottom - rect.top);
        }
    }

    public void drawInCanvas(Canvas canvas, int i, int i2) {
        this.imager.drawInCanvas(canvas, i, i2);
        this.drawHandler.drawInCanvas(canvas, i, i2);
    }

    public Rect[] getDrawTextRects() {
        return this.drawTextRects;
    }

    private Rect[] computeDrawTextRects() {
        Rect[] rectArr;
        ArrayList arrayList = new ArrayList();
        if (this.text.contains("\n") && !this.ignoreLine) {
            String[] split = this.text.split("\n");
            int i = C33281.$SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$TEXTALIGN[this.textAlign.ordinal()];
            if (i == 1) {
                int length = split.length;
                Rect[][] rectArr2 = new Rect[][]{new Rect[length]};
                for (int i2 = 0; i2 < split.length; i2++) {
                    rectArr2[i2] = getDrawRectLine(split[i2]);
                }
                int i3 = 0;
                for (int i4 = 0; i4 < length; i4++) {
                    for (Rect rect : rectArr2[i4]) {
                        rect.top += i3;
                        rect.bottom += i3;
                        arrayList.add(rect);
                    }
                    i3 += ((int) this.paint.getFontSpacing()) + getLineSpaceOffset();
                }
            } else if (i == 2) {
                int length2 = split.length;
                Rect[][] rectArr3 = new Rect[][]{new Rect[length2]};
                int[] iArr = new int[split.length];
                int i5 = 0;
                int i6 = 0;
                for (String str : split) {
                    Rect rect2 = new Rect();
                    this.paint.getTextBounds(str, 0, str.length(), rect2);
                    int textSpaceOffset = (rect2.right - rect2.left) + ((getTextSpaceOffset() * str.length()) - 1);
                    iArr[i5] = textSpaceOffset;
                    if (i6 < textSpaceOffset) {
                        i6 = textSpaceOffset;
                    }
                    i5++;
                }
                for (int i7 = 0; i7 < split.length; i7++) {
                    rectArr3[i7] = getDrawRectLine(split[i7]);
                }
                int i8 = 0;
                int i9 = 0;
                for (int i10 = 0; i10 < length2; i10++) {
                    Rect[] rectArr4 = rectArr3[i10];
                    int i11 = (i6 - iArr[i8]) / 2;
                    for (Rect rect3 : rectArr4) {
                        rect3.left += i11;
                        rect3.right += i11;
                        rect3.top += i9;
                        rect3.bottom += i9;
                        arrayList.add(rect3);
                    }
                    i9 += ((int) this.paint.getFontSpacing()) + getLineSpaceOffset();
                    i8++;
                }
            } else if (i == 3) {
                int length3 = split.length;
                Rect[][] rectArr5 = new Rect[][]{new Rect[length3]};
                int[] iArr2 = new int[split.length];
                int i12 = 0;
                int i13 = 0;
                for (String str2 : split) {
                    Rect rect4 = new Rect();
                    this.paint.getTextBounds(str2, 0, str2.length(), rect4);
                    int textSpaceOffset2 = (rect4.right - rect4.left) + ((getTextSpaceOffset() * str2.length()) - 1);
                    iArr2[i12] = textSpaceOffset2;
                    if (i13 < textSpaceOffset2) {
                        i13 = textSpaceOffset2;
                    }
                    i12++;
                }
                for (int i14 = 0; i14 < split.length; i14++) {
                    rectArr5[i14] = getDrawRectLine(split[i14]);
                }
                int i15 = 0;
                int i16 = 0;
                for (int i17 = 0; i17 < length3; i17++) {
                    Rect[] rectArr6 = rectArr5[i17];
                    int i18 = i13 - iArr2[i15];
                    for (Rect rect5 : rectArr6) {
                        rect5.left += i18;
                        rect5.right += i18;
                        rect5.top += i16;
                        rect5.bottom += i16;
                        arrayList.add(rect5);
                    }
                    i16 += ((int) this.paint.getFontSpacing()) + getLineSpaceOffset();
                    i15++;
                }
            }
        } else {
            for (Rect rect6 : getDrawRectLine(this.text)) {
                arrayList.add(rect6);
            }
        }
        return (Rect[]) arrayList.toArray(new Rect[arrayList.size()]);
    }

    private Rect[] getDrawRectLine(String str) {
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        int i = 0;
        getPaint().getTextBounds(getTextString(), 0, getTextString().length(), rect2);
        float f = -rect2.top;
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        Rect[] rectArr = new Rect[length];
        int r4 = 0;
        int i2 = r4;
        int i3 = 0;
        while (i3 < charArray.length) {
            this.paint.getTextBounds("" + charArray[i3], i, 1, rect);
            int i4 = (int) f;
            rectArr[i3] = new Rect(rect.left + i2, rect.top + i4, rect.left + i2 + rect.width(), i4 + rect.bottom);
            int i5 = i3 + 1;
            if (i5 < charArray.length) {
                int i6 = i3 + 2;
                i2 = (((int) this.paint.measureText(str, 0, i6)) + r4) - ((int) this.paint.measureText(str, i5, i6));
            }
            i3 = i5;
            i = 0;
        }
        int i7 = 0;
        for (int i8 = 0; i8 < length; i8++) {
            Rect rect3 = rectArr[i8];
            rect3.left += i7;
            rect3.right += i7;
            i7 += getTextSpaceOffset();
        }
        return rectArr;
    }

    public Rect getTextContentRect() {
        return this.rect;
    }

    public Rect getBgContentRect() {
        return this.imager.getImageContentRect();
    }

    public Rect getContentRect() {
        int i = this.imager.getImageContentRect().left;
        int i2 = this.imager.getImageContentRect().top;
        int i3 = this.rect.right - this.imager.getImageContentRect().right;
        int i4 = this.rect.bottom - this.imager.getImageContentRect().bottom;
        if (i >= i3) {
            i = i3;
        }
        if (i2 >= i4) {
            i2 = i4;
        }
        int width = this.rect.width();
        int height = this.rect.height();
        if (i < 0) {
            width += i * (-2);
        }
        if (i2 < 0) {
            height += i2 * (-2);
        }
        return new Rect(0, 0, width, height);
    }

    public String[] getTextLineArray() {
        return this.textlines;
    }

    private String[] getTextArrays() {
        String[] split = getTextString().split("\n");
        char[] charArray = getTextString().toCharArray();
        int i = 0;
        for (int length = charArray.length - 1; length >= 0 && charArray[length] == '\n'; length--) {
            i++;
        }
        String[] strArr = (String[]) Arrays.copyOf(split, split.length + i);
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if (strArr[i2] == null) {
                strArr[i2] = "";
            }
        }
        return strArr;
    }

    private Rect computeTextContentRect() {
        String[] textLineArray;
        Rect rect = new Rect();
        if (this.text.contains("\n") && !this.ignoreLine) {
            this.multiLine = true;
            int i = 0;
            int i2 = 0;
            for (String str : getTextLineArray()) {
                Rect rect2 = new Rect();
                this.paint.getTextBounds(str, 0, str.length(), rect2);
                int textSpaceOffset = (rect2.right - rect2.left) + (getTextSpaceOffset() * (str.length() - 1));
                if (i < textSpaceOffset) {
                    i = textSpaceOffset;
                }
                i2 = (int) (i2 + this.paint.getFontSpacing() + getLineSpaceOffset());
            }
            rect.set(0, 0, i, i2);
        } else {
            this.multiLine = false;
            Rect rect3 = new Rect();
            Paint paint = this.paint;
            String str2 = this.text;
            paint.getTextBounds(str2, 0, str2.length(), rect3);
            rect.set(0, 0, (rect3.right - rect3.left) + (getTextSpaceOffset() * (this.text.length() - 1)), rect3.height());
        }
        return rect;
    }

    public Rect[] getBoundsTextRects() {
        return this.boundsTextRects;
    }

    private Rect[] computeBoundsTextRects() {
        ArrayList arrayList = new ArrayList();
        if (this.text.contains("\n") && !this.ignoreLine) {
            for (String str : this.text.split("\n")) {
                for (Rect rect : getBoundsRectLine(str)) {
                    arrayList.add(rect);
                }
            }
        } else {
            for (Rect rect2 : getBoundsRectLine(this.text)) {
                arrayList.add(rect2);
            }
        }
        return (Rect[]) arrayList.toArray(new Rect[arrayList.size()]);
    }

    private Rect[] getBoundsRectLine(String str) {
        char[] charArray;
        ArrayList arrayList = new ArrayList();
        for (char c : str.toCharArray()) {
            Rect rect = new Rect();
            this.paint.getTextBounds("" + c, 0, 1, rect);
            arrayList.add(rect);
        }
        return (Rect[]) arrayList.toArray(new Rect[arrayList.size()]);
    }

    public void updateData() {
        this.textlines = getTextArrays();
        this.drawTextRects = computeDrawTextRects();
        this.rect = computeTextContentRect();
        this.boundsTextRects = computeBoundsTextRects();
        this.imager.updateData();
    }

    public boolean getMultiLine() {
        return this.multiLine;
    }

    public TEXTALIGN getTextAlign() {
        return this.textAlign;
    }

    public void setTextAlign(TEXTALIGN textalign) {
        this.textAlign = textalign;
        updateData();
    }

    public int getTextAddHeight() {
        return this.mTextAddHeight;
    }

    public void setTextAddHeight(int i) {
        this.mTextAddHeight = i;
    }

    @Deprecated
    public String[] getLines() {
        return this.lines;
    }

    @Deprecated
    public Rect[] getLineRects() {
        return this.lineRects;
    }

    public Rect getRect(String str) {
        Rect rect = new Rect();
        Paint.FontMetrics fontMetrics = this.paint.getFontMetrics();
        this.paint.getTextBounds(str, 0, str.length(), rect);
        rect.top = (int) fontMetrics.top;
        rect.bottom = (int) fontMetrics.bottom;
        return rect;
    }

    public void setColor(int i) {
        this.color = i;
        this.paint.setColor(i);
    }

    public void setShaderBitmap(Bitmap bitmap) {
        this.paint.setShader(null);
        Bitmap bitmap2 = this.shaderBMP;
        if (bitmap2 != null) {
            bitmap2.recycle();
            this.shaderBMP = null;
        }
        this.shaderBMP = bitmap;
        if (bitmap == null) {
            return;
        }
        this.paint.setShader(new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR));
        updateData();
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
        this.paint.setTypeface(typeface);
        this.sideTracesPaint.setTypeface(this.typeface);
        updateData();
    }

    public void setText(String str) {
        this.text = str;
        updateData();
    }

    public void setTextSize(float f) {
        this.paint.setTextSize(f);
        this.sideTracesPaint.setTextSize(f);
        updateData();
    }

    public void setMarginLeftTop(int i, int i2) {
        this.rect.left += i;
        this.rect.top += i2;
        Rect rect = this.rect;
        rect.right = i + rect.right;
        Rect rect2 = this.rect;
        rect2.bottom = i2 + rect2.bottom;
    }

    public String getTextString() {
        return this.text;
    }

    public int getTextColor() {
        if (this.paint != null) {
            return this.color;
        }
        return -1;
    }

    public float getTextSize() {
        return this.paint.getTextSize();
    }

    public Paint getPaint() {
        return this.paint;
    }

    @Deprecated
    public Rect getRect() {
        return this.rect;
    }

    public Typeface getTypeface() {
        return this.typeface;
    }

    public void setImagerDrawable(DMImager.StretchDrawable stretchDrawable, DMImager.LeftDrawable leftDrawable, DMImager.TopDrawable topDrawable, DMImager.RightDrawable rightDrawable, DMImager.BottomDrawable bottomDrawable) {
        this.imager.setImagerDrawable(stretchDrawable, leftDrawable, topDrawable, rightDrawable, bottomDrawable);
        updateData();
    }

    public boolean getContains(int i, int i2) {
        return this.rect.contains(i, i2);
    }

    public int getFitSize(float f) {
        return (int) (f * this.context.getResources().getDisplayMetrics().density);
    }

    public void setPaintShadowLayer(float f, float f2, float f3, int i) {
        this.paint.setShadowLayer(f, f2, f3, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.picspool.lib.text.draw.DMTextDrawer$1 */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class C33281 {
        static final /* synthetic */ int[] $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$SHADOWALIGN;
        static final /* synthetic */ int[] $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$TEXTALIGN;

        static {
            int[] iArr = new int[SHADOWALIGN.values().length];
            $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$SHADOWALIGN = iArr;
            try {
                iArr[SHADOWALIGN.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$SHADOWALIGN[SHADOWALIGN.LEFT_TOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$SHADOWALIGN[SHADOWALIGN.LEFT_BOTTOM.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$SHADOWALIGN[SHADOWALIGN.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$SHADOWALIGN[SHADOWALIGN.RIGHT_TOP.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$SHADOWALIGN[SHADOWALIGN.RIGHT_BOTTOM.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$SHADOWALIGN[SHADOWALIGN.TOP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr2 = new int[TEXTALIGN.values().length];
            $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$TEXTALIGN = iArr2;
            try {
                iArr2[TEXTALIGN.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$TEXTALIGN[TEXTALIGN.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$TEXTALIGN[TEXTALIGN.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    public void setPaintShadowLayer(SHADOWALIGN shadowalign) {
        this.shadowAlign = shadowalign;
        switch (C33281.$SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$SHADOWALIGN[shadowalign.ordinal()]) {
            case 1:
                this.paint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
                return;
            case 2:
                this.paint.setShadowLayer(this.radiusShadow, -this.dxShadow, -this.dyShadow, ViewCompat.MEASURED_STATE_MASK);
                return;
            case 3:
                this.paint.setShadowLayer(this.radiusShadow, -this.dxShadow, this.dyShadow, ViewCompat.MEASURED_STATE_MASK);
                return;
            case 4:
                this.paint.setShadowLayer(this.radiusShadow, 0.0f, -this.dyShadow, ViewCompat.MEASURED_STATE_MASK);
                return;
            case 5:
                this.paint.setShadowLayer(this.radiusShadow, this.dxShadow, -this.dyShadow, ViewCompat.MEASURED_STATE_MASK);
                return;
            case 6:
                this.paint.setShadowLayer(this.radiusShadow, this.dxShadow, this.dyShadow, ViewCompat.MEASURED_STATE_MASK);
                return;
            case 7:
                this.paint.setShadowLayer(this.radiusShadow, 0.0f, this.dyShadow, ViewCompat.MEASURED_STATE_MASK);
                return;
            default:
                return;
        }
    }

    public SHADOWALIGN getPaintShadowLayer() {
        return this.shadowAlign;
    }

    public SHADOWALIGN getShadowAlign() {
        return this.shadowAlign;
    }

    public void clearPaintShadowLayer() {
        this.paint.clearShadowLayer();
    }

    public void setBorder(int i, int i2) {
        this.mBorderWidth = i;
        this.mBorderColor = i2;
    }

    public int getBorderWidth() {
        return this.mBorderWidth;
    }

    public int getBorderColor() {
        return this.mBorderColor;
    }

    public boolean isShowSideTraces() {
        return this.isShowSideTraces;
    }

    public void setShowSideTraces(boolean z) {
        this.isShowSideTraces = z;
    }

    public int getSideTracesWidthPX() {
        return this.sideTracesWidthPX;
    }

    public int getSideTracesColor() {
        return this.sideTracesPaint.getColor();
    }

    public void setSideTracesColor(int i) {
        this.sideTracesPaint.setColor(i);
    }

    public Paint getSideTracesPaint() {
        return this.sideTracesPaint;
    }

    public UNDERLINES_STYLE getUnderlinesStyle() {
        return this.underlinesStyle;
    }

    public void setUnderlinesStyle(UNDERLINES_STYLE underlines_style) {
        this.underlinesStyle = underlines_style;
    }

    public void setTextAlpha(int i) {
        this.paint.setAlpha(i);
    }

    public int getTextAlpha() {
        return this.paint.getAlpha();
    }

    public int getLineSpaceOffset() {
        return this.lineSpaceOffset;
    }

    public void setLineSpaceOffset(int i) {
        updateData();
        this.lineSpaceOffset = i;
    }

    public int getTextSpaceOffset() {
        return this.textSpaceOffset;
    }

    public void setTextSpaceOffset(int i) {
        this.textSpaceOffset = i;
        updateData();
    }

    public void cleanImagerDrawable() {
        this.imager.cleanImagerDrawable();
    }

    public int getSideTracesColorIndex() {
        return this.sideTracesColorIndex;
    }

    public void setSideTracesColorIndex(int i) {
        this.sideTracesColorIndex = i;
    }

    public int getPaintColorIndex() {
        return this.paintColorIndex;
    }

    public void setPaintColorIndex(int i) {
        this.paintColorIndex = i;
    }

    public int getShaderColorIndex() {
        return this.shaderColorIndex;
    }

    public void setShaderColorIndex(int i) {
        this.shaderColorIndex = i;
    }

    public int getBgAlpha() {
        return this.imager.getAlpha();
    }

    public void setBgAlpha(int i) {
        this.imager.setAlpha(i);
    }

    public int getTypefaceIndex() {
        return this.typefaceIndex;
    }

    public void setTypefaceIndex(int i) {
        this.typefaceIndex = i;
    }

    public Context getContext() {
        return this.context;
    }

    public boolean isShowHelpFlag() {
        return this.showHelpFlag;
    }

    public void setShowHelpFlag(boolean z) {
        this.showHelpFlag = z;
    }

    public boolean isIgnoreLine() {
        return this.ignoreLine;
    }

    public void setIgnoreLine(boolean z) {
        this.ignoreLine = z;
    }
}

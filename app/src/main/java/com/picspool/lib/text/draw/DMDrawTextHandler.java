package com.picspool.lib.text.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.ArrayList;

import com.picspool.lib.text.draw.DMTextDrawer;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMDrawTextHandler {
    private int dashedWidth;
    private int spaceWidth;
    private DMTextDrawer textDrawer;

    public DMDrawTextHandler(DMTextDrawer dMTextDrawer) {
        this.textDrawer = dMTextDrawer;
        this.dashedWidth = (int) dMTextDrawer.getContext().getResources().getDimension(R.dimen.underlines_dashed_w);
        this.spaceWidth = (int) dMTextDrawer.getContext().getResources().getDimension(R.dimen.underlines_space_w);
    }

    public void drawInCanvas(Canvas canvas, int i, int i2) {
        ArrayList arrayList;
        Rect[] drawTextRects = this.textDrawer.getDrawTextRects();
        Rect[] boundsTextRects = this.textDrawer.getBoundsTextRects();
        String textString = this.textDrawer.getTextString();
        if (textString.contains("\n")) {
            String[] split = textString.split("\n");
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            int length = split.length;
            int i3 = 0;
            int i4 = 0;
            while (i3 < length) {
                String str = split[i3];
                char[] charArray = str.toCharArray();
                int length2 = charArray.length;
                int i5 = 0;
                while (i5 < length2) {
                    arrayList2.add(Character.valueOf(charArray[i5]));
                    i5++;
                    split = split;
                }
                String[] strArr = split;
                if (str.length() != 0) {
                    arrayList3.add(Integer.valueOf(i4));
                    i4 += str.length();
                    arrayList3.add(Integer.valueOf(i4 - 1));
                }
                i3++;
                split = strArr;
            }
            int i6 = 0;
            int i7 = 0;
            while (i7 < arrayList2.size()) {
                int i8 = (drawTextRects[i7].left + i) - boundsTextRects[i7].left;
                int i9 = (i2 + drawTextRects[i7].top) - boundsTextRects[i7].top;
                String str2 = "" + arrayList2.get(i7);
                if (this.textDrawer.getUnderlinesStyle() == DMTextDrawer.UNDERLINES_STYLE.NONE || i6 >= arrayList3.size() - 1 || i7 != ((Integer) arrayList3.get(i6)).intValue()) {
                    arrayList = arrayList2;
                } else {
                    arrayList = arrayList2;
                    drawUnderlines(canvas, boundsTextRects[i7].left + i8, (((int) this.textDrawer.getPaint().getTextSize()) / 10) + i9, drawTextRects[((Integer) arrayList3.get(i6 + 1)).intValue()].right - drawTextRects[i7].left);
                    i6 += 2;
                }
                if (this.textDrawer.isShowSideTraces()) {
                    drawSideTraces(canvas, str2, i8, i9);
                }
                canvas.drawText(str2, i8, i9, this.textDrawer.getPaint());
                i7++;
                arrayList2 = arrayList;
            }
            return;
        }
        char[] charArray2 = textString.toCharArray();
        if (drawTextRects.length != 0 && this.textDrawer.getUnderlinesStyle() != DMTextDrawer.UNDERLINES_STYLE.NONE) {
            drawUnderlines(canvas, i, ((i2 + drawTextRects[0].top) - boundsTextRects[0].top) + (((int) this.textDrawer.getPaint().getTextSize()) / 10), drawTextRects[charArray2.length - 1].right);
        }
        for (int i10 = 0; i10 < charArray2.length; i10++) {
            int i11 = (drawTextRects[i10].left + i) - boundsTextRects[i10].left;
            int i12 = (i2 + drawTextRects[i10].top) - boundsTextRects[i10].top;
            String str3 = "" + charArray2[i10];
            if (this.textDrawer.isShowSideTraces()) {
                drawSideTraces(canvas, str3, i11, i12);
            }
            canvas.drawText(str3, i11, i12, this.textDrawer.getPaint());
        }
    }

    private void drawSideTraces(Canvas canvas, String str, int i, int i2) {
        canvas.drawText(str, i, i2, this.textDrawer.getSideTracesPaint());
    }

    private void drawUnderlines(Canvas canvas, int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(this.textDrawer.getTextSize() / 16.0f);
        paint.setColor(this.textDrawer.getPaint().getColor());
        paint.setShader(this.textDrawer.getPaint().getShader());
        paint.setAlpha(this.textDrawer.getPaint().getAlpha());
        int i4 = C33271.f1996x71e3622f[this.textDrawer.getUnderlinesStyle().ordinal()];
        if (i4 == 1) {
            float f = i2;
            canvas.drawLine(i, f, i + i3, f, paint);
        } else if (i4 == 2) {
            paint.setStrokeWidth(this.textDrawer.getTextSize() / 25.0f);
            float f2 = i;
            float f3 = i2;
            float f4 = i + i3;
            canvas.drawLine(f2, f3, f4, f3, paint);
            canvas.drawLine(f2, f3 + (paint.getStrokeWidth() * 2.0f), f4, f3 + (paint.getStrokeWidth() * 2.0f), paint);
        } else if (i4 != 3) {
        } else {
            int i5 = this.dashedWidth;
            int i6 = i;
            while (true) {
                int i7 = i + i3;
                if (i6 >= i7) {
                    return;
                }
                int i8 = i6 + i5 > i7 ? i7 - i6 : i5;
                float f5 = i2;
                canvas.drawLine(i6, f5, i6 + i8, f5, paint);
                i6 += this.dashedWidth + this.spaceWidth;
                i5 = i8;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.picspool.lib.text.draw.DMDrawTextHandler$1 */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class C33271 {

        /* renamed from: $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$UNDERLINES_STYLE */
        static final /* synthetic */ int[] f1996x71e3622f;

        static {
            int[] iArr = new int[DMTextDrawer.UNDERLINES_STYLE.values().length];
            f1996x71e3622f = iArr;
            try {
                iArr[DMTextDrawer.UNDERLINES_STYLE.SINGLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1996x71e3622f[DMTextDrawer.UNDERLINES_STYLE.DOUBLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1996x71e3622f[DMTextDrawer.UNDERLINES_STYLE.DASHED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f1996x71e3622f[DMTextDrawer.UNDERLINES_STYLE.POINT_DASHED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f1996x71e3622f[DMTextDrawer.UNDERLINES_STYLE.NONE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }
}

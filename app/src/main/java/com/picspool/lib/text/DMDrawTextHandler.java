package com.picspool.lib.text;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.Map;
import com.picspool.instatextview.resource.DMTextEmojiRes;
import com.picspool.instatextview.resource.manager.DMTextEmojiManager;
import com.picspool.instatextview.resource.manager.DMTextEmojiSelectedManager;
import com.picspool.lib.text.DMTextDrawer;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMDrawTextHandler {
    private DMTextDrawer BMTextDrawer;
    private int dashedWidth;
    private int spaceWidth;

    public DMDrawTextHandler(DMTextDrawer dMTextDrawer) {
        this.BMTextDrawer = dMTextDrawer;
        this.dashedWidth = (int) dMTextDrawer.getContext().getResources().getDimension(R.dimen.underlines_dashed_w);
        this.spaceWidth = (int) dMTextDrawer.getContext().getResources().getDimension(R.dimen.underlines_space_w);
    }

    public void drawInCanvas(Canvas canvas, int i, int i2) {
        ArrayList arrayList;
        String str;
        Rect[] drawTextRects = this.BMTextDrawer.getDrawTextRects();
        Rect[] boundsTextRects = this.BMTextDrawer.getBoundsTextRects();
        String textString = this.BMTextDrawer.getTextString();
        String str2 = "";
        if (textString.contains("\n")) {
            String[] split = textString.split("\n");
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            int length = split.length;
            int i3 = 0;
            int i4 = 0;
            while (i3 < length) {
                String str3 = split[i3];
                char[] charArray = str3.toCharArray();
                int length2 = charArray.length;
                int i5 = 0;
                while (i5 < length2) {
                    arrayList2.add(Character.valueOf(charArray[i5]));
                    i5++;
                    split = split;
                }
                String[] strArr = split;
                if (str3.length() != 0) {
                    arrayList3.add(Integer.valueOf(i4));
                    i4 += str3.length();
                    arrayList3.add(Integer.valueOf(i4 - 1));
                }
                i3++;
                split = strArr;
            }
            int i6 = 0;
            int i7 = 0;
            while (i7 < arrayList2.size()) {
                int i8 = (drawTextRects[i7].left + i) - boundsTextRects[i7].left;
                int i9 = (drawTextRects[i7].top + i2) - boundsTextRects[i7].top;
                String str4 = str2 + arrayList2.get(i7);
                ArrayList arrayList4 = arrayList2;
                if (this.BMTextDrawer.getUnderlinesStyle() == DMTextDrawer.UNDERLINES_STYLE.NONE || i6 >= arrayList3.size() - 1 || i7 != ((Integer) arrayList3.get(i6)).intValue()) {
                    arrayList = arrayList3;
                    str = str2;
                } else {
                    arrayList = arrayList3;
                    str = str2;
                    drawUnderlines(canvas, boundsTextRects[i7].left + i8, (((int) this.BMTextDrawer.getPaint().getTextSize()) / 10) + i9, drawTextRects[((Integer) arrayList3.get(i6 + 1)).intValue()].right - drawTextRects[i7].left);
                    i6 += 2;
                }
                if (this.BMTextDrawer.isShowSideTraces() && str4.compareTo("人") != 0) {
                    drawSideTraces(canvas, str4, i8, i9);
                }
                if (str4.compareTo("人") == 0) {
                    Map<Integer, Integer> textEmojiSelectedList = DMTextEmojiSelectedManager.getInstance().getTextEmojiSelectedList();
                    DMTextEmojiRes res = (DMTextEmojiRes) DMTextEmojiManager.getInstance(this.BMTextDrawer.getContext()).getRes(textEmojiSelectedList.containsKey(Integer.valueOf(i7)) ? textEmojiSelectedList.get(Integer.valueOf(i7)).intValue() : 0);
                    if (res == null) {
                        res = (DMTextEmojiRes) DMTextEmojiManager.getInstance(this.BMTextDrawer.getContext()).getRes(0);
                    }
                    int width = drawTextRects[i7].width();
                    Bitmap iconBitmap = res.getIconBitmap(width, width);
                    float f = drawTextRects[i7].left + i;
                    float f2 = drawTextRects[i7].top + i2;
                    if (drawTextRects[i7].height() > width) {
                        f = drawTextRects[i7].left + i;
                        f2 = drawTextRects[i7].top + i2 + ((drawTextRects[i7].height() - width) / 2.0f);
                    }
                    float f3 = width;
                    canvas.drawBitmap(iconBitmap, (Rect) null, new RectF(f, f2, f + f3, f3 + f2), this.BMTextDrawer.getPaint());
                } else {
                    canvas.drawText(str4, i8, i9, this.BMTextDrawer.getPaint());
                }
                i7++;
                arrayList2 = arrayList4;
                arrayList3 = arrayList;
                str2 = str;
            }
            return;
        }
        String str5 = "";
        char[] charArray2 = textString.toCharArray();
        if (drawTextRects.length != 0 && this.BMTextDrawer.getUnderlinesStyle() != DMTextDrawer.UNDERLINES_STYLE.NONE) {
            drawUnderlines(canvas, i, ((drawTextRects[0].top + i2) - boundsTextRects[0].top) + (((int) this.BMTextDrawer.getPaint().getTextSize()) / 10), drawTextRects[charArray2.length - 1].right);
        }
        int i10 = 0;
        while (i10 < charArray2.length) {
            int i11 = (drawTextRects[i10].left + i) - boundsTextRects[i10].left;
            int i12 = (drawTextRects[i10].top + i2) - boundsTextRects[i10].top;
            StringBuilder sb = new StringBuilder();
            String str6 = str5;
            sb.append(str6);
            sb.append(charArray2[i10]);
            String sb2 = sb.toString();
            if (this.BMTextDrawer.isShowSideTraces() && sb2.compareTo("人") != 0) {
                drawSideTraces(canvas, sb2, i11, i12);
            }
            if (sb2.compareTo("人") == 0) {
                Map<Integer, Integer> textEmojiSelectedList2 = DMTextEmojiSelectedManager.getInstance().getTextEmojiSelectedList();
                DMTextEmojiRes res2 = (DMTextEmojiRes) DMTextEmojiManager.getInstance(this.BMTextDrawer.getContext()).getRes(textEmojiSelectedList2.containsKey(Integer.valueOf(i10)) ? textEmojiSelectedList2.get(Integer.valueOf(i10)).intValue() : 0);
                int width2 = drawTextRects[i10].width();
                Bitmap iconBitmap2 = res2.getIconBitmap(width2, width2);
                float f4 = drawTextRects[i10].left + i;
                float f5 = i2;
                if (drawTextRects[i10].height() > width2) {
                    f4 = drawTextRects[i10].left + i;
                    f5 += (drawTextRects[i10].height() - width2) / 2.0f;
                }
                float f6 = width2;
                canvas.drawBitmap(iconBitmap2, (Rect) null, new RectF(f4, f5, f4 + f6, f6 + f5), this.BMTextDrawer.getPaint());
            } else {
                canvas.drawText(sb2, i11, i12, this.BMTextDrawer.getPaint());
            }
            i10++;
            str5 = str6;
        }
    }

    private void drawSideTraces(Canvas canvas, String str, int i, int i2) {
        canvas.drawText(str, i, i2, this.BMTextDrawer.getSideTracesPaint());
    }

    private void drawUnderlines(Canvas canvas, int i, int i2, int i3) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(this.BMTextDrawer.getTextSize() / 16.0f);
        paint.setColor(this.BMTextDrawer.getPaint().getColor());
        paint.setShader(this.BMTextDrawer.getPaint().getShader());
        paint.setAlpha(this.BMTextDrawer.getPaint().getAlpha());
        int i4 = C33171.$SwitchMap$org$picspool$lib$text$DMTextDrawer$UNDERLINES_STYLE[this.BMTextDrawer.getUnderlinesStyle().ordinal()];
        if (i4 == 1) {
            float f = i2;
            canvas.drawLine(i, f, i + i3, f, paint);
        } else if (i4 == 2) {
            paint.setStrokeWidth(this.BMTextDrawer.getTextSize() / 25.0f);
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
    /* renamed from: com.picspool.lib.text.DMDrawTextHandler$1 */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class C33171 {
        static final /* synthetic */ int[] $SwitchMap$org$picspool$lib$text$DMTextDrawer$UNDERLINES_STYLE;

        static {
            int[] iArr = new int[DMTextDrawer.UNDERLINES_STYLE.values().length];
            $SwitchMap$org$picspool$lib$text$DMTextDrawer$UNDERLINES_STYLE = iArr;
            try {
                iArr[DMTextDrawer.UNDERLINES_STYLE.SINGLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$DMTextDrawer$UNDERLINES_STYLE[DMTextDrawer.UNDERLINES_STYLE.DOUBLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$DMTextDrawer$UNDERLINES_STYLE[DMTextDrawer.UNDERLINES_STYLE.DASHED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$DMTextDrawer$UNDERLINES_STYLE[DMTextDrawer.UNDERLINES_STYLE.POINT_DASHED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$DMTextDrawer$UNDERLINES_STYLE[DMTextDrawer.UNDERLINES_STYLE.NONE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }
}

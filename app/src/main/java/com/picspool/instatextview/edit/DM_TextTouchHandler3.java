package com.picspool.instatextview.edit;

import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class DM_TextTouchHandler3 {
    private DM_TextFixedView3 textFixed;

    public DM_TextTouchHandler3(DM_TextFixedView3 dM_TextFixedView3) {
        this.textFixed = dM_TextFixedView3;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return touchHandler(motionEvent);
    }

    private boolean touchHandler(MotionEvent motionEvent) {
        int i;
        if (motionEvent.getAction() == 0) {
            try {
                Rect[] lineRect = getLineRect();
                String[] textLines = this.textFixed.getTextLines();
                ArrayList arrayList = new ArrayList();
                int i2 = 0;
                for (int i3 = 0; i3 < textLines.length; i3++) {
                    if (textLines[i3].length() == 0) {
                        arrayList.add(Integer.valueOf(i3 + i2));
                    }
                    i2 += textLines[i3].length();
                }
                if (lineRect != null) {
                    for (int i4 = 0; i4 < lineRect.length; i4++) {
                        if (lineRect[i4].contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                            return setCaretSelection(((Integer) arrayList.get(i4)).intValue());
                        }
                    }
                }
                RectF properRect = this.textFixed.getProperRect();
                Rect[] boundsTextRects = this.textFixed.getBoundsTextRects();
                Rect[] drawTextRects = this.textFixed.getDrawTextRects();
                if (drawTextRects != null) {
                    double x = motionEvent.getX();
                    double y = motionEvent.getY();
                    int length = drawTextRects.length;
                    double[] dArr = new double[length];
                    int i5 = 0;
                    while (i5 < drawTextRects.length) {
                        double width = x - (((properRect.left + drawTextRects[i5].left) - boundsTextRects[i5].left) + (drawTextRects[i5].width() / 2));
                        double d = y - ((properRect.top + drawTextRects[i5].top) - boundsTextRects[i5].top);
                        dArr[i5] = (width * width) + (d * d);
                        i5++;
                        textLines = textLines;
                    }
                    String[] strArr = textLines;
                    double pow = Math.pow(this.textFixed.getWidth(), 2.0d) + Math.pow(this.textFixed.getHeight(), 2.0d);
                    int i6 = 0;
                    int i7 = 0;
                    for (int i8 = 0; i8 < length; i8++) {
                        double d2 = dArr[i8];
                        if (d2 < pow) {
                            i6 = i7;
                            pow = d2;
                        }
                        i7++;
                    }
                    if (i6 < drawTextRects.length && i6 < boundsTextRects.length) {
                        int i9 = 0;
                        int i10 = 0;
                        while (true) {
                            if (i9 >= strArr.length) {
                                i9 = 0;
                                i = 0;
                                break;
                            }
                            i10 += strArr[i9].length();
                            if (i6 < i10) {
                                i = i6 - (i10 - strArr[i9].length());
                                break;
                            }
                            i9++;
                        }
                        int i11 = 0;
                        for (int i12 = 0; i12 < i9; i12++) {
                            i11 = strArr[i12].length() == 0 ? i11 + 1 : i11 + strArr[i12].length() + 1;
                        }
                        int i13 = i11 + i;
                        if (motionEvent.getX() < ((((int) properRect.left) + drawTextRects[i6].left) - boundsTextRects[i6].left) + (drawTextRects[i6].width() / 2)) {
                            return setCaretSelection(i13);
                        }
                        return setCaretSelection(i13 + 1);
                    }
                    return true;
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return true;
            }
        }
        return true;
    }

    private boolean setCaretSelection(int i) {
        this.textFixed.setSelection(i);
        return true;
    }

    private Rect[] getLineRect() {
        ArrayList<Rect> arrayList = new ArrayList();
        try {
            String[] textLines = this.textFixed.getTextLines();
            for (int i = 0; i < textLines.length; i++) {
                if (textLines[i].length() == 0) {
                    Rect rect = new Rect();
                    int fontSpacing = ((int) (this.textFixed.getTextPaint().getFontSpacing() + this.textFixed.getLineSpaceOffset())) * i;
                    rect.set(0, fontSpacing, this.textFixed.getWidth() + 0, ((int) this.textFixed.getTextPaint().getFontSpacing()) + this.textFixed.getTextDrawer().getLineSpaceOffset() + fontSpacing);
                    arrayList.add(rect);
                }
            }
            int i2 = (int) this.textFixed.getProperRect().top;
            for (Rect rect2 : arrayList) {
                rect2.top += i2;
                rect2.bottom += i2;
            }
        } catch (Exception e) {
            arrayList.add(new Rect());
            e.printStackTrace();
        }
        return (Rect[]) arrayList.toArray(new Rect[arrayList.size()]);
    }
}

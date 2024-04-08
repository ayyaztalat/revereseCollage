package com.picspool.lib.text.edit;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.Timer;
import java.util.TimerTask;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.text.draw.DMTextDrawer;

/* loaded from: classes3.dex */
public class DMTextCaret {
    private int caretHeight;
    private Paint paint;
    private boolean startGlintFlag;
    private DMTextFixedView textFixed;
    private Timer timer;
    private boolean showCaretFlag = true;
    private boolean showFlag = true;
    private boolean notCaret = true;
    private long glintTime = 700;
    private float widthRatio = 0.1f;
    private Rect caretRect = new Rect();
    private int caretColor = -1;

    public DMTextCaret(DMTextFixedView dMTextFixedView) {
        this.caretHeight = 50;
        this.textFixed = dMTextFixedView;
        Paint paint = new Paint();
        this.paint = paint;
        paint.setColor(this.caretColor);
        this.timer = new Timer();
        this.caretHeight = DMScreenInfoUtil.dip2px(dMTextFixedView.getContext(), this.caretHeight);
    }

    public void onDrawCaret(Canvas canvas) {
        if (this.notCaret && this.showFlag && this.showCaretFlag) {
            canvas.drawRect(this.caretRect, this.paint);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00c4, code lost:
        if (r2 != 3) goto L45;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void changeSelection(int r17) {
        /*
            Method dump skipped, instructions count: 309
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.picspool.lib.text.edit.DMTextCaret.changeSelection(int):void");
    }

    /* renamed from: com.picspool.lib.text.edit.DMTextCaret$2 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C33642 {
        static final /* synthetic */ int[] $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$TEXTALIGN;

        static {
            int[] iArr = new int[DMTextDrawer.TEXTALIGN.values().length];
            $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$TEXTALIGN = iArr;
            try {
                iArr[DMTextDrawer.TEXTALIGN.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$TEXTALIGN[DMTextDrawer.TEXTALIGN.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$picspool$lib$text$draw$DMTextDrawer$TEXTALIGN[DMTextDrawer.TEXTALIGN.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public void startShowCaret() {
        if (!this.notCaret || this.startGlintFlag) {
            return;
        }
        Timer timer = this.timer;
        TimerTask timerTask = new TimerTask() { // from class: com.picspool.lib.text.edit.DMTextCaret.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                DMTextCaret dMTextCaret = DMTextCaret.this;
                dMTextCaret.showCaretFlag = !dMTextCaret.showCaretFlag;
                if (DMTextCaret.this.showFlag) {
                    DMTextCaret.this.textFixed.getHandler().post(new Runnable() { // from class: com.picspool.lib.text.edit.DMTextCaret.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            DMTextCaret.this.textFixed.invalidate();
                        }
                    });
                }
            }
        };
        long j = this.glintTime;
        timer.schedule(timerTask, j, j);
        this.startGlintFlag = true;
    }

    public void iniCaret(int i, int i2) {
        int i3 = i / 2;
        int height = (i2 - this.caretRect.height()) / 2;
        Rect rect = this.caretRect;
        rect.set(i3, height, (rect.width() != 0 ? this.caretRect.width() : 2) + i3, (this.caretRect.height() == 0 ? this.caretHeight : this.caretRect.height()) + height);
    }

    public void stopShowCaret() {
        if (this.startGlintFlag) {
            this.timer.cancel();
            this.startGlintFlag = false;
        }
    }

    public boolean isShowCaretFlag() {
        return this.showFlag;
    }

    public void setShowCaretFlag(boolean z) {
        this.showFlag = z;
        this.textFixed.invalidate();
    }

    public boolean isNotCaret() {
        return this.notCaret;
    }

    public void setNotCaret(boolean z) {
        this.notCaret = z;
    }
}

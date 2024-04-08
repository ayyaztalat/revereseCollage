package com.picspool.lib.label.edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;

import com.picspool.lib.sticker.core.DMSticker;
import com.picspool.lib.text.draw.DMTextDrawer;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class BMLabelBMSticker extends DMSticker {
    private Bitmap bitmap;
    private Context context;
    private DMTextDrawer textDrawer;
    private int textPadding;

    public BMLabelBMSticker(Context context, DMTextDrawer dMTextDrawer) {
        super(50);
        this.textPadding = 50;
        this.context = context;
        this.textDrawer = dMTextDrawer;
        this.textPadding = (int) context.getResources().getDimension(R.dimen.show_text_padding);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x006d, code lost:
        if (r5 == null) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x006f, code lost:
        r9.bitmap = android.graphics.Bitmap.createBitmap(50, 50, android.graphics.Bitmap.Config.ARGB_4444);
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00a1, code lost:
        if (r9.bitmap != null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00a4, code lost:
        r0 = new android.graphics.Canvas(r9.bitmap);
        r0.setDrawFilter(new android.graphics.PaintFlagsDrawFilter(0, 3));
        r9.textDrawer.drawInCanvas(r0, r2, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00ba, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateBitmap() {
        /*
            r9 = this;
            com.picspool.lib.text.draw.DMTextDrawer r0 = r9.textDrawer
            android.content.Context r0 = r0.getContext()
            int r0 = com.picspool.lib.sysutillib.DMScreenInfoUtil.screenHeightDp(r0)
            r1 = 350(0x15e, float:4.9E-43)
            if (r0 >= r1) goto L11
            r1 = 1073741824(0x40000000, float:2.0)
            goto L13
        L11:
            r1 = 1069547520(0x3fc00000, float:1.5)
        L13:
            r2 = 300(0x12c, float:4.2E-43)
            if (r0 >= r2) goto L19
            r1 = 1075838976(0x40200000, float:2.5)
        L19:
            r2 = 200(0xc8, float:2.8E-43)
            if (r0 >= r2) goto L1f
            r1 = 1080033280(0x40600000, float:3.5)
        L1f:
            com.picspool.lib.text.draw.DMTextDrawer r0 = r9.textDrawer
            float r2 = r0.getTextSize()
            float r2 = r2 * r1
            r0.setTextSize(r2)
            com.picspool.lib.text.draw.DMTextDrawer r0 = r9.textDrawer
            android.graphics.Rect r0 = r0.getContentRect()
            int r0 = r0.width()
            com.picspool.lib.text.draw.DMTextDrawer r1 = r9.textDrawer
            android.graphics.Rect r1 = r1.getContentRect()
            int r1 = r1.height()
            com.picspool.lib.text.draw.DMTextDrawer r2 = r9.textDrawer
            android.graphics.Rect r2 = r2.getTextContentRect()
            int r2 = r2.right
            com.picspool.lib.text.draw.DMTextDrawer r3 = r9.textDrawer
            android.graphics.Rect r3 = r3.getTextContentRect()
            int r3 = r3.bottom
            int r4 = r9.textPadding
            int r5 = r4 * 2
            int r0 = r0 + r5
            int r4 = r4 * 2
            int r1 = r1 + r4
            int r2 = r0 - r2
            int r2 = r2 / 2
            int r3 = r1 - r3
            int r3 = r3 / 2
            if (r0 <= 0) goto Lc8
            if (r1 > 0) goto L63
            goto Lc8
        L63:
            r4 = 50
            android.graphics.Bitmap$Config r5 = android.graphics.Bitmap.Config.ARGB_4444     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L7a
            android.graphics.Bitmap r5 = android.graphics.Bitmap.createBitmap(r0, r1, r5)     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L7a
            r9.bitmap = r5     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L7a
            if (r5 != 0) goto La4
        L6f:
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.ARGB_4444
            android.graphics.Bitmap r0 = android.graphics.Bitmap.createBitmap(r4, r4, r0)
            r9.bitmap = r0
            goto La4
        L78:
            r0 = move-exception
            goto Lbb
        L7a:
            r5 = move-exception
            int r6 = r0 / 2
            int r7 = r1 / 2
            android.graphics.Bitmap$Config r8 = android.graphics.Bitmap.Config.ARGB_4444     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L8b
            android.graphics.Bitmap r6 = android.graphics.Bitmap.createBitmap(r6, r7, r8)     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L8b
            r9.bitmap = r6     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L8b
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L8b
            goto L9f
        L8b:
            int r0 = r0 / 4
            int r1 = r1 / 4
            android.graphics.Bitmap$Config r6 = android.graphics.Bitmap.Config.ARGB_4444     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L98
            android.graphics.Bitmap r0 = android.graphics.Bitmap.createBitmap(r0, r1, r6)     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L98
            r9.bitmap = r0     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L98
            goto L9c
        L98:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L78
        L9c:
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L78
        L9f:
            android.graphics.Bitmap r0 = r9.bitmap
            if (r0 != 0) goto La4
            goto L6f
        La4:
            android.graphics.Canvas r0 = new android.graphics.Canvas
            android.graphics.Bitmap r1 = r9.bitmap
            r0.<init>(r1)
            android.graphics.PaintFlagsDrawFilter r1 = new android.graphics.PaintFlagsDrawFilter
            r4 = 0
            r5 = 3
            r1.<init>(r4, r5)
            r0.setDrawFilter(r1)
            com.picspool.lib.text.draw.DMTextDrawer r1 = r9.textDrawer
            r1.drawInCanvas(r0, r2, r3)
            return
        Lbb:
            android.graphics.Bitmap r1 = r9.bitmap
            if (r1 != 0) goto Lc7
            android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.ARGB_4444
            android.graphics.Bitmap r1 = android.graphics.Bitmap.createBitmap(r4, r4, r1)
            r9.bitmap = r1
        Lc7:
            throw r0
        Lc8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.picspool.lib.label.edit.BMLabelBMSticker.updateBitmap():void");
    }

    @Override // com.picspool.lib.sticker.core.DMSticker
    public int getWidth() {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            return bitmap.getWidth();
        }
        return 0;
    }

    @Override // com.picspool.lib.sticker.core.DMSticker
    public int getHeight() {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            return bitmap.getHeight();
        }
        return 0;
    }

    @Override // com.picspool.lib.sticker.core.DMSticker
    public void drawInCanvas(Canvas canvas) {
        if (this.bitmap != null) {
            Matrix matrix = this.transform;
            Bitmap bitmap = this.bitmap;
            if (bitmap != null) {
                float width = bitmap.getWidth() / this.bitmap.getWidth();
                float height = this.bitmap.getHeight() / this.bitmap.getHeight();
                if (width >= height) {
                    width = height;
                }
                matrix.postScale(width, width);
            }
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
            Paint paint = new Paint();
            paint.setAlpha(this.alpha);
            paint.setAntiAlias(true);
            Bitmap bitmap2 = this.bitmap;
            if (bitmap2 != null) {
                canvas.drawBitmap(bitmap2, matrix, paint);
            }
        }
    }

    public void releaseImage() {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.bitmap.recycle();
        }
        this.bitmap = null;
    }

    public DMTextDrawer getTextDrawer() {
        return this.textDrawer;
    }

    public void setTextDrawer(DMTextDrawer dMTextDrawer) {
        this.textDrawer = dMTextDrawer;
    }
}

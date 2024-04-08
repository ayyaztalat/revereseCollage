package com.picspool.lib.collagelib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.ThumbnailUtils;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.collagelib.resource.LibDMTemplateWithImageRes;
import com.picspool.lib.collagelib.resource.collage.LibDMCollageInfo;

/* loaded from: classes3.dex */
public class LibDMTemplateIconView extends RelativeLayout {
    private List<LibDMCollageInfo> mCollageinfo;
    private Context mContext;
    private List<Bitmap> mSrcBitmaps;
    private int out_height;
    private int out_width;
    private Bitmap resultBmp;

    public LibDMTemplateIconView(Context context) {
        super(context);
        this.out_width = 130;
        this.out_height = 130;
        this.mSrcBitmaps = new ArrayList();
        this.mCollageinfo = null;
        this.mContext = context;
    }

    public void setRes(LibDMTemplateWithImageRes libDMTemplateWithImageRes) {
        if (libDMTemplateWithImageRes != null) {
            this.mSrcBitmaps = libDMTemplateWithImageRes.getBitmaps();
            this.mCollageinfo = libDMTemplateWithImageRes.getCollageInfo();
        }
    }

    public int getOut_width() {
        return this.out_width;
    }

    public void setOut_width(int i) {
        this.out_width = i;
    }

    public int getOut_height() {
        return this.out_height;
    }

    public void setOut_height(int i) {
        this.out_height = i;
    }

    public void drawResultBmp() {
        int i;
        Canvas canvas;
        Bitmap bitmap;
        Paint paint = new Paint();
        int i2 = this.out_width;
        float f = i2 / 3060.0f;
        int i3 = this.out_height;
        float f2 = i3 / 3060.0f;
        float f3 = i3 / i2;
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(createBitmap);
        canvas2.drawColor(-1);
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        int i4 = 0;
        while (i4 < this.mCollageinfo.size()) {
            Rect GetRect = this.mCollageinfo.get(i4).GetRect(1.0f);
            Rect layoutPosition = getLayoutPosition(this.out_width, GetRect);
            int i5 = layoutPosition.right - layoutPosition.left;
            int i6 = layoutPosition.bottom - layoutPosition.top;
            Path path = this.mCollageinfo.get(i4).getPath(f, f2, GetRect.left, GetRect.top, f3);
            Bitmap maskBitmap = this.mCollageinfo.get(i4).getMaskBitmap(this.mContext);
            if (this.mSrcBitmaps.size() > i4) {
                Bitmap extractThumbnail = ThumbnailUtils.extractThumbnail(this.mSrcBitmaps.get(i4), i5, i6);
                i = i4;
                canvas = canvas2;
                bitmap = createBitmap;
                Bitmap displayImage = getDisplayImage(i5, i6, maskBitmap, extractThumbnail, path);
                canvas.drawBitmap(displayImage, (Rect) null, layoutPosition, paint);
                if (extractThumbnail != null && !extractThumbnail.isRecycled()) {
                    extractThumbnail.recycle();
                }
                if (displayImage != null && !displayImage.isRecycled()) {
                    displayImage.recycle();
                }
            } else {
                i = i4;
                canvas = canvas2;
                bitmap = createBitmap;
            }
            i4 = i + 1;
            canvas2 = canvas;
            createBitmap = bitmap;
        }
        this.resultBmp = createBitmap;
    }

    public Bitmap getDisplayImage(int i, int i2, Bitmap bitmap, Bitmap bitmap2, Path path) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Rect rect = new Rect(0, 0, i, i2);
        if (bitmap == null) {
            canvas.drawPath(path, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap2, (Rect) null, rect, paint);
            paint.setXfermode(null);
        } else {
            canvas.drawBitmap(bitmap2, (Rect) null, rect, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawBitmap(bitmap, (Rect) null, rect, paint);
            paint.setXfermode(null);
        }
        return createBitmap;
    }

    private Rect getLayoutPosition(int i, Rect rect) {
        float f = i / 3060.0f;
        int i2 = (int) ((rect.left * f) + 0.5f);
        int i3 = (int) ((rect.top * f) + 0.5f);
        int i4 = (int) (((rect.bottom - rect.top) * f) + 0.5f);
        Rect rect2 = new Rect();
        rect2.left = i2;
        rect2.top = i3;
        rect2.right = i2 + ((int) (((rect.right - rect.left) * f) + 0.5f));
        rect2.bottom = i3 + i4;
        return rect2;
    }

    public Bitmap getResultBmp() {
        return this.resultBmp;
    }
}

package com.picspool.snappic.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import com.picspool.libsquare.view.CSSquareView;
import com.picspool.snappic.snap.CSBestDragSnapView;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSnapPicView extends CSSquareView {
    CSBestDragSnapView dragSnapView;

    public CSSnapPicView(Context context) {
        super(context);
    }

    public CSSnapPicView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.picspool.libsquare.view.CSSquareView
    public void getSizeBitmap(int i) {
        int i2;
        new Paint().setAntiAlias(true);
        if (getWidth() > getHeight()) {
            i2 = (int) (i * (getHeight() / getWidth()));
        } else {
            i2 = i;
            i = (int) (i * (getWidth() / getHeight()));
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.setDrawFilter(this.mPaintFlagsDrawFilter);
        canvas.drawColor(-1);
        if (this.backgroundDrawable != null) {
            this.backgroundDrawable.setBounds(0, 0, i, i2);
            this.backgroundDrawable.draw(canvas);
            this.backgroundDrawable.setBounds(0, 0, getWidth(), getHeight());
        }
        Bitmap dispalyImage = this.img_pic.getDispalyImage(i, i2);
        if (dispalyImage != null && !dispalyImage.isRecycled()) {
            canvas.drawBitmap(dispalyImage, 0.0f, 0.0f, (Paint) null);
            dispalyImage.recycle();
        }
        CSBestDragSnapView cSBestDragSnapView = (CSBestDragSnapView) findViewById(R.id.drag_snap_view);
        this.dragSnapView = cSBestDragSnapView;
        if (cSBestDragSnapView != null) {
            cSBestDragSnapView.drawSnapInCanvas(canvas);
        }
        Bitmap resultBitmap = this.sfcView_faces.getResultBitmap();
        canvas.drawBitmap(resultBitmap, new Rect(0, 0, resultBitmap.getWidth(), resultBitmap.getHeight()), new Rect(0, 0, i, i2), (Paint) null);
        if (resultBitmap != null && !resultBitmap.isRecycled()) {
            resultBitmap.recycle();
        }
        if (this.mListener != null) {
            this.mListener.getResultBitmapSuccess(createBitmap);
        }
    }

    public Bitmap getSnapMainBitmap(Bitmap bitmap) {
        new Paint().setAntiAlias(true);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.d("xlb", "w:" + width + "h:" + height);
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.setDrawFilter(this.mPaintFlagsDrawFilter);
        canvas.drawColor(-1);
        if (this.backgroundDrawable != null) {
            this.backgroundDrawable.setBounds(0, 0, width, height);
            this.backgroundDrawable.draw(canvas);
            this.backgroundDrawable.setBounds(0, 0, getWidth(), getHeight());
        }
        Bitmap dispalyImage = this.img_pic.getDispalyImage(width, height);
        if (dispalyImage != null && !dispalyImage.isRecycled()) {
            canvas.drawBitmap(dispalyImage, 0.0f, 0.0f, (Paint) null);
        }
        CSBestDragSnapView cSBestDragSnapView = (CSBestDragSnapView) findViewById(R.id.drag_snap_view);
        this.dragSnapView = cSBestDragSnapView;
        if (cSBestDragSnapView != null) {
            cSBestDragSnapView.drawSnapInCanvas(canvas);
        }
        Bitmap resultBitmap = this.sfcView_faces.getResultBitmap();
        canvas.drawBitmap(resultBitmap, new Rect(0, 0, resultBitmap.getWidth(), resultBitmap.getHeight()), new Rect(0, 0, width, height), (Paint) null);
        if (resultBitmap != null && !resultBitmap.isRecycled()) {
            resultBitmap.recycle();
        }
        return createBitmap;
    }
}

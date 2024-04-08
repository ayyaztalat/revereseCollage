package com.photo.editor.square.splash.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import com.picspool.snappic.snap.CSBestDragSnapView;
import com.sky.testproject.R;
import com.winflag.libsquare.view.CSSquareView;


/* loaded from: classes2.dex */
public class CSSnapPicView extends CSSquareView {
    CSBestDragSnapView dragSnapView;

    public CSSnapPicView(Context context) {
        super(context);
    }

    public CSSnapPicView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.winflag.libsquare.view.CSSquareView
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
        if (this.img_light != null && this.img_light.getImageBitmap() != null) {
            canvas.drawBitmap(this.img_light.getImageBitmap(), (Rect) null, new Rect(0, 0, i, i2), this.img_light.getLeakPaint());
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
}

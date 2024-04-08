package com.picspool.lib.text.useless;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import com.google.firebase.messaging.ServiceStarter;

/* loaded from: classes3.dex */
public class DMNotTextView extends View {
    private Paint paint;
    private Bitmap srcBitmap;
    private Bitmap textBitmap;

    public DMNotTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.textBitmap = Bitmap.createBitmap(ServiceStarter.ERROR_UNKNOWN, ServiceStarter.ERROR_UNKNOWN, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(this.textBitmap);
        Paint paint = new Paint();
        this.paint = paint;
        paint.setTextSize(100.0f);
        canvas.drawText("abcde", 100.0f, 100.0f, this.paint);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(-1);
        Bitmap bitmap = this.srcBitmap;
        if (bitmap != null) {
            canvas.saveLayer(0.0f, 0.0f, bitmap.getWidth() + 0, this.srcBitmap.getHeight(), null, 31);
            this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
            canvas.drawBitmap(this.srcBitmap, 0.0f, 0.0f, (Paint) null);
            canvas.drawBitmap(this.textBitmap, 0.0f, 0.0f, this.paint);
            this.paint.setXfermode(null);
        }
    }

    public Bitmap getSrcBitmap() {
        return this.srcBitmap;
    }

    public void setSrcBitmap(Bitmap bitmap) {
        this.srcBitmap = bitmap;
        invalidate();
    }
}

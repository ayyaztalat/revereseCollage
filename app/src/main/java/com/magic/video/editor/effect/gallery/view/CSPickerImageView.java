package com.magic.video.editor.effect.gallery.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.util.AttributeSet;
import com.magic.video.editor.effect.cut.utils.CSScreenInfoUtil;
import com.sky.testproject.R;
/* loaded from: classes2.dex */
public class CSPickerImageView extends CSIgnoreRecycleImageView {
    private int borderSize;
    private boolean isSelected;
    private int number;
    private final Paint paint;
    private Paint paintBorder;
    private Rect rect;
    private Uri uri;

    public CSPickerImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.borderSize = 1;
        this.paint = new Paint();
        init();
    }

    public CSPickerImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.borderSize = 1;
        this.paint = new Paint();
        init();
    }

    private void init() {
        Paint paint = new Paint();
        this.paintBorder = paint;
        paint.setAntiAlias(true);
        this.paintBorder.setColor(getResources().getColor(R.color.gallery_selector_main_color));
        this.borderSize = CSScreenInfoUtil.dip2px(getContext(), 3.0f);
        this.paint.setColor(-1);
        this.paint.setAntiAlias(true);
        this.rect = new Rect();
    }

    public CSPickerImageView(Context context) {
        super(context);
        this.borderSize = 1;
        this.paint = new Paint();
        init();
    }

    @Override // android.widget.ImageView, android.view.View
    public void setSelected(boolean z) {
        if (z != this.isSelected) {
            this.isSelected = z;
            invalidate();
        }
    }

    @Override // android.view.View
    public boolean isSelected() {
        return this.isSelected;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        setMeasuredDimension(measuredWidth, measuredWidth);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.magic.video.editor.effect.gallery.view.CSIgnoreRecycleImageView, android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSelected(canvas);
    }

    protected void drawSelected(Canvas canvas) {
        if (this.isSelected) {
            canvas.drawRect(0.0f, 0.0f, this.borderSize, getHeight(), this.paintBorder);
            canvas.drawRect(getWidth() - this.borderSize, 0.0f, getWidth(), getHeight(), this.paintBorder);
            canvas.drawRect(0.0f, 0.0f, getWidth(), this.borderSize, this.paintBorder);
            canvas.drawRect(0.0f, getHeight() - this.borderSize, getWidth(), getHeight(), this.paintBorder);
            this.paint.setTextSize(getWidth() / 2);
            String str = "" + this.number;
            this.paint.getTextBounds(str, 0, str.length(), this.rect);
            canvas.drawColor(Color.parseColor("#994285F4"));
            canvas.drawText("" + this.number, ((getWidth() - this.rect.width()) / 2) - this.rect.left, ((getHeight() - this.rect.height()) / 2) - this.rect.top, this.paint);
        }
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int i) {
        this.number = i;
    }

    public Uri getUri() {
        return this.uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override // com.magic.video.editor.effect.gallery.view.CSIgnoreRecycleImageView, androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
    }

    public void release() {
        if (this.image != null) {
            this.image.recycle();
        }
        this.image = null;
    }
}

package com.photoart.lib.SysSnap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sky.testproject.R;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;

/* loaded from: classes3.dex */
public class BMDragSnapView extends RelativeLayout {
    private int bgColor;
    private Context context;
    private GestureDetector gestureDetector;
    private OnSnapListener onSnapListener;
    private int snapHeight;
    private int textColor;
    private int textSize;
    private TextView txtView;

    /* loaded from: classes3.dex */
    public interface OnSnapListener {
        void doubleTapSnap(TextView textView);

        void longPressSnap();
    }

    public BMDragSnapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.bgColor = Color.parseColor("#88000000");
        this.textColor = -1;
        this.textSize = 19;
        setOnTouchListener(new MyOnTouchListener());
        this.context = context;
        this.gestureDetector = new GestureDetector(getContext(), new MySimpleOnGestureListener());
        this.snapHeight = DMScreenInfoUtil.dip2px(context, 30.0f);
    }

    public void addSnapView(CharSequence charSequence) {
        TextView textView = new TextView(this.context);
        this.txtView = textView;
        textView.setText(charSequence);
        this.txtView.setTextSize(this.textSize);
        this.txtView.setTextColor(this.textColor);
        this.txtView.setBackgroundColor(this.bgColor);
        this.txtView.setGravity(17);
        this.txtView.setSingleLine(true);
        LayoutParams layoutParams = new LayoutParams(-1, this.snapHeight);
        layoutParams.addRule(10);
        layoutParams.topMargin = 0;
        addView(this.txtView, layoutParams);
    }

    /* loaded from: classes3.dex */
    private class MyOnTouchListener implements OnTouchListener {
        private MyOnTouchListener() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            boolean z = BMDragSnapView.this.hitTest(motionEvent.getX(), motionEvent.getY()) != null;
            BMDragSnapView.this.gestureDetector.onTouchEvent(motionEvent);
            return z;
        }
    }

    /* loaded from: classes3.dex */
    private class MySimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        private TextView textView;

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return true;
        }

        private MySimpleOnGestureListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            TextView textView = this.textView;
            if (textView != null) {
                LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
                layoutParams.topMargin = (int) (layoutParams.topMargin - f2);
                if (layoutParams.topMargin < 0) {
                    layoutParams.topMargin = 0;
                }
                if (layoutParams.topMargin > BMDragSnapView.this.getHeight() - BMDragSnapView.this.snapHeight) {
                    layoutParams.topMargin = BMDragSnapView.this.getHeight() - BMDragSnapView.this.snapHeight;
                }
                this.textView.setLayoutParams(layoutParams);
                BMDragSnapView.this.invalidate();
            }
            return false;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            TextView textView = this.textView;
            if (textView != null) {
                BMDragSnapView.this.dialogCancel(textView);
            }
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (this.textView != null) {
                BMDragSnapView.this.onSnapListener.doubleTapSnap(this.textView);
                return false;
            }
            return false;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            this.textView = BMDragSnapView.this.hitTest(motionEvent.getX(), motionEvent.getY());
            return false;
        }
    }

    protected void dialogCancel(final TextView textView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.dialog_snap_message);
        builder.setTitle(R.string.tips);
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() { // from class: org.photoart.lib.SysSnap.BMDragSnapView.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                BMDragSnapView.this.removeView(textView);
                if (BMDragSnapView.this.onSnapListener != null) {
                    BMDragSnapView.this.onSnapListener.longPressSnap();
                }
                dialogInterface.dismiss();
                BMDragSnapView.this.invalidate();
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() { // from class: org.photoart.lib.SysSnap.BMDragSnapView.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    public TextView hitTest(float f, float f2) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof TextView) {
                TextView textView = (TextView) childAt;
                LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
                int dip2px = DMScreenInfoUtil.dip2px(getContext(), 10.0f);
                if (new RectF(layoutParams.leftMargin, layoutParams.topMargin, (layoutParams.leftMargin + textView.getWidth()) - dip2px, layoutParams.topMargin + textView.getHeight() + dip2px).contains(f, f2)) {
                    return textView;
                }
            }
        }
        return null;
    }

    public void drawSnapInCanvas(Canvas canvas) {
        float f;
        Matrix matrix;
        Paint paint;
        int i;
        float f2;
        String[] strArr;
        Paint paint2;
        int i2 = 0;
        String substring = null;
        float measureText = 0;
        Matrix matrix2;
        BMDragSnapView bMDragSnapView = this;
        float dip2px = DMScreenInfoUtil.dip2px(bMDragSnapView.context, 25.0f);
        Matrix matrix3 = new Matrix();
        matrix3.setScale(canvas.getWidth() / getWidth(), canvas.getHeight() / getHeight());
        Paint paint3 = new Paint();
        paint3.setColor(bMDragSnapView.textColor);
        char c = 1;
        paint3.setAntiAlias(true);
        paint3.setTextSize(matrix3.mapRadius(DMScreenInfoUtil.sp2px(getContext(), bMDragSnapView.textSize)));
        paint3.setTextAlign(Paint.Align.CENTER);
        Paint paint4 = new Paint();
        paint4.setStyle(Paint.Style.FILL);
        paint4.setColor(bMDragSnapView.bgColor);
        paint4.setAntiAlias(true);
        BMStickerImageManager stickerImageManager = BMStickerModeManager.getStickerImageManager(bMDragSnapView.context.getApplicationContext(), BMStickerModeManager.StickerMode.STICKERALL);
        char c2 = 0;
        int i3 = 0;
        while (i3 < getChildCount()) {
            View childAt = bMDragSnapView.getChildAt(i3);
            if (childAt instanceof TextView) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                float[] fArr = new float[4];
                fArr[c2] = layoutParams.leftMargin;
                fArr[c] = layoutParams.topMargin;
                fArr[2] = layoutParams.leftMargin + childAt.getWidth();
                fArr[3] = layoutParams.topMargin + childAt.getHeight();
                matrix3.mapPoints(fArr);
                Rect rect = new Rect((int) fArr[c2], (int) fArr[c], (int) fArr[2], (int) fArr[3]);
                canvas.drawRect(rect, paint4);
                Paint.FontMetricsInt fontMetricsInt = paint3.getFontMetricsInt();
                int height = (rect.top + (((rect.height() + fontMetricsInt.top) - fontMetricsInt.bottom) / 2)) - fontMetricsInt.top;
                String[] split = ("" + ((Object) ((TextView) childAt).getText())).split("_aurona]");
                if (split == null || split.length <= 0) {
                    matrix = matrix3;
                    i = 0;
                } else {
                    int i4 = 0;
                    i = 0;
                    while (i4 < split.length) {
                        String str = split[i4];
                        if (str.contains("[aurona_")) {
                            matrix2 = matrix3;
                            if (str.length() > 11) {
                                i = ((int) (i + paint3.measureText(str.substring(0, str.length() - 11)))) + DMScreenInfoUtil.dip2px(getContext(), (int) ((rect.width() * 25.0f) / childAt.getWidth()));
                            } else {
                                i += DMScreenInfoUtil.dip2px(getContext(), (int) ((rect.width() * 25.0f) / childAt.getWidth()));
                            }
                        } else {
                            matrix2 = matrix3;
                            i = (int) (i + paint3.measureText(str));
                        }
                        i4++;
                        matrix3 = matrix2;
                    }
                    matrix = matrix3;
                }
                float width = (rect.width() - i) / 2.0f;
                if (split != null && split.length > 0) {
                    int i5 = 0;
                    while (i5 < split.length) {
                        String str2 = split[i5];
                        if (str2.contains("[aurona_")) {
                            try {
                                int indexOf = str2.indexOf("[aurona_");
                                if (indexOf > 0) {
                                    strArr = split;
                                    try {
                                        substring = str2.substring(0, indexOf);
                                        measureText = paint3.measureText(substring);
                                        paint2 = paint4;
                                    } catch (Exception unused) {
                                        paint2 = paint4;
                                        f2 = dip2px;
                                        i5++;
                                        split = strArr;
                                        dip2px = f2;
                                        paint4 = paint2;
                                    }
                                    try {
                                        canvas.drawText(substring, width + (measureText / 2.0f), height, paint3);
                                        width += measureText;
                                    } catch (Exception unused2) {
                                        f2 = dip2px;
                                        i5++;
                                        split = strArr;
                                        dip2px = f2;
                                        paint4 = paint2;
                                    }
                                } else {
                                    strArr = split;
                                    paint2 = paint4;
                                }
                                int width2 = (int) ((rect.width() * dip2px) / childAt.getWidth());
//                                Bitmap iconBitmap = stickerImageManager.getRes(Integer.parseInt(str2.substring(str2.length() - 3, str2.length()))).getIconBitmap(width2, width2);
                                Bitmap iconBitmap = stickerImageManager.getRes(Integer.parseInt(str2.substring(str2.length() - 3, str2.length()))).getIconBitmap(width2, width2);
                                int height2 = rect.top + ((int) ((rect.height() - iconBitmap.getHeight()) / 2.0f));
                                if (iconBitmap == null || iconBitmap.isRecycled()) {
                                    f2 = dip2px;
                                } else {
                                    int width3 = iconBitmap.getWidth();
                                    f2 = dip2px;
                                    try {
                                        canvas.drawBitmap(iconBitmap, width, height2, (Paint) null);
                                        width += width3;
                                        if (iconBitmap != null && !iconBitmap.isRecycled()) {
                                            iconBitmap.recycle();
                                        }
                                    } catch (Exception unused3) {
                                    }
                                }
                                i2 = indexOf + 11;
                            } catch (Exception unused4) {
                                f2 = dip2px;
                                strArr = split;
                                paint2 = paint4;
                            }
                            if (i2 < str2.length()) {
                                try {
                                    String substring2 = str2.substring(i2, str2.length() - 1);
                                    float measureText2 = paint3.measureText(substring2);
                                    canvas.drawText(substring2, (measureText2 / 2.0f) + width, height, paint3);
                                    width += measureText2;
                                } catch (Exception unused5) {
                                }
                            }
                        } else {
                            f2 = dip2px;
                            strArr = split;
                            paint2 = paint4;
                            float measureText3 = paint3.measureText(str2);
                            canvas.drawText(str2, (measureText3 / 2.0f) + width, height, paint3);
                            width += measureText3;
                        }
                        i5++;
                        split = strArr;
                        dip2px = f2;
                        paint4 = paint2;
                    }
                }
                f = dip2px;
                paint = paint4;
            } else {
                f = dip2px;
                matrix = matrix3;
                paint = paint4;
            }
            i3++;
            bMDragSnapView = this;
            matrix3 = matrix;
            dip2px = f;
            paint4 = paint;
            c = 1;
            c2 = 0;
        }
    }

    public OnSnapListener getOnSnapListener() {
        return this.onSnapListener;
    }

    public void setOnSnapListener(OnSnapListener onSnapListener) {
        this.onSnapListener = onSnapListener;
    }

    public void removeTextView() {
        TextView textView = this.txtView;
        if (textView != null) {
            removeView(textView);
        }
    }

    public boolean removeTextView(TextView textView) {
        if (textView != null) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if ((childAt instanceof TextView) && textView == childAt) {
                    removeView(textView);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsTextView(TextView textView) {
        if (textView != null) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if ((childAt instanceof TextView) && textView == childAt) {
                    return true;
                }
            }
        }
        return false;
    }

    public void changeTextColor(int i) {
        TextView textView = this.txtView;
        if (textView != null) {
            textView.setTextColor(i);
        }
        this.textColor = i;
    }

    public void changeTextBgColor(int i) {
        TextView textView = this.txtView;
        if (textView != null) {
            textView.setBackgroundColor(i);
        }
        this.bgColor = i;
    }
}

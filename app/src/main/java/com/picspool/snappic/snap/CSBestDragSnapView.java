package com.picspool.snappic.snap;

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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.photoart.libsticker.sticker.DMStickerManager;
import com.photoart.libsticker.sticker2.DMStickerModeManager;
import com.photoart.libsticker.sticker2.DMStickerModeManager2;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSBestDragSnapView extends RelativeLayout {
    private int bgColor;
    private Context context;
    private GestureDetector gestureDetector;
    private OnSnapListener onSnapListener;
    private int textColor;
    private int textSize;

    /* loaded from: classes.dex */
    public interface OnSnapListener {
        void doubleTapSnap(CSBestSnapMainLayout cSBestSnapMainLayout);

        void longPressSnap();
    }

    public CSBestDragSnapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.bgColor = Color.parseColor("#aa000000");
        this.textColor = -1;
        this.textSize = 19;
        setOnTouchListener(new MyOnTouchListener());
        this.context = context;
        this.gestureDetector = new GestureDetector(getContext(), new MySimpleOnGestureListener());
    }

    public void addSnapView(CharSequence charSequence, CSBestFrameRes cSBestFrameRes) {
        int screenWidth = (int) (((DMScreenInfoUtil.screenWidth(this.context) * cSBestFrameRes.getTagHeight()) * 1.0f) / cSBestFrameRes.getTagWidth());
        LayoutParams layoutParams = new LayoutParams(-1, screenWidth);
        layoutParams.addRule(10);
        CSBestSnapMainLayout cSBestSnapMainLayout = new CSBestSnapMainLayout(this.context);
        addView(cSBestSnapMainLayout, layoutParams);
        TextView textView = new TextView(this.context);
        textView.setText(charSequence);
        textView.setTextSize(this.textSize);
        textView.setTextColor(this.textColor);
        textView.setBackgroundColor(this.bgColor);
        textView.setGravity(17);
        textView.setSingleLine(true);
        LayoutParams layoutParams2 = new LayoutParams(-1, DMScreenInfoUtil.dip2px(this.context, 30.0f));
        layoutParams2.addRule(10);
        if (cSBestFrameRes.getName().compareTo("framenone") != 0) {
            Bitmap localImageBitmap = cSBestFrameRes.getLocalImageBitmap();
            if (localImageBitmap != null && !localImageBitmap.isRecycled()) {
                ImageView imageView = new ImageView(this.context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageBitmap(localImageBitmap);
                LayoutParams layoutParams3 = new LayoutParams(-1, screenWidth);
                layoutParams3.addRule(13);
                cSBestSnapMainLayout.addView(imageView, layoutParams3);
                textView.setBackgroundColor(0);
                int screenWidth2 = (int) (((DMScreenInfoUtil.screenWidth(this.context) * cSBestFrameRes.getTagStartY()) * 1.0f) / cSBestFrameRes.getTagWidth());
                layoutParams2.topMargin = screenWidth2;
                cSBestSnapMainLayout.mImageView = imageView;
                cSBestSnapMainLayout.mIsHasFrame = true;
                cSBestSnapMainLayout.mTxtBackgroundColor = 0;
                cSBestSnapMainLayout.mTextViewTopMargin = screenWidth2;
            }
        } else {
            cSBestSnapMainLayout.mImageView = null;
            cSBestSnapMainLayout.mIsHasFrame = false;
            cSBestSnapMainLayout.mTxtBackgroundColor = this.bgColor;
            cSBestSnapMainLayout.mTextViewTopMargin = 0;
            layoutParams2.topMargin = 0;
        }
        cSBestSnapMainLayout.mRes = cSBestFrameRes;
        cSBestSnapMainLayout.snapHeight = screenWidth;
        cSBestSnapMainLayout.mTextView = textView;
        cSBestSnapMainLayout.addView(textView, layoutParams2);
    }

    /* loaded from: classes.dex */
    private class MyOnTouchListener implements OnTouchListener {
        private MyOnTouchListener() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            boolean z = CSBestDragSnapView.this.hitTest(motionEvent.getX(), motionEvent.getY()) != null;
            CSBestDragSnapView.this.gestureDetector.onTouchEvent(motionEvent);
            return z;
        }
    }

    /* loaded from: classes.dex */
    private class MySimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        private CSBestSnapMainLayout relativeLayoutView;

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return true;
        }

        private MySimpleOnGestureListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            CSBestSnapMainLayout cSBestSnapMainLayout = this.relativeLayoutView;
            if (cSBestSnapMainLayout != null) {
                LayoutParams layoutParams = (LayoutParams) cSBestSnapMainLayout.getLayoutParams();
                layoutParams.topMargin = (int) (layoutParams.topMargin - f2);
                if (layoutParams.topMargin < 0) {
                    layoutParams.topMargin = 0;
                }
                if (layoutParams.topMargin > CSBestDragSnapView.this.getHeight() - DMScreenInfoUtil.dip2px(CSBestDragSnapView.this.context, 30.0f)) {
                    layoutParams.topMargin = CSBestDragSnapView.this.getHeight() - DMScreenInfoUtil.dip2px(CSBestDragSnapView.this.context, 30.0f);
                }
                this.relativeLayoutView.setLayoutParams(layoutParams);
                CSBestDragSnapView.this.invalidate();
            }
            return false;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            CSBestSnapMainLayout cSBestSnapMainLayout = this.relativeLayoutView;
            if (cSBestSnapMainLayout != null) {
                CSBestDragSnapView.this.dialogCancel(cSBestSnapMainLayout);
            }
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            CSBestSnapMainLayout cSBestSnapMainLayout = this.relativeLayoutView;
            if (cSBestSnapMainLayout == null || cSBestSnapMainLayout.mTextView == null || CSBestDragSnapView.this.onSnapListener == null) {
                return false;
            }
            CSBestDragSnapView.this.onSnapListener.doubleTapSnap(this.relativeLayoutView);
            return false;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            this.relativeLayoutView = CSBestDragSnapView.this.hitTest(motionEvent.getX(), motionEvent.getY());
            return false;
        }
    }

    protected void dialogCancel(final RelativeLayout relativeLayout) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.dialog_snap_message);
        builder.setTitle(R.string.tips);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // from class: com.picspool.snappic.snap.CSBestDragSnapView.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                CSBestDragSnapView.this.removeView(relativeLayout);
                if (CSBestDragSnapView.this.onSnapListener != null) {
                    CSBestDragSnapView.this.onSnapListener.longPressSnap();
                }
                dialogInterface.dismiss();
                CSBestDragSnapView.this.invalidate();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { // from class: com.picspool.snappic.snap.CSBestDragSnapView.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    public CSBestSnapMainLayout hitTest(float f, float f2) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof CSBestSnapMainLayout) {
                CSBestSnapMainLayout cSBestSnapMainLayout = (CSBestSnapMainLayout) childAt;
                LayoutParams layoutParams = (LayoutParams) cSBestSnapMainLayout.getLayoutParams();
                int dip2px = DMScreenInfoUtil.dip2px(getContext(), 10.0f);
                if (new RectF(layoutParams.leftMargin, layoutParams.topMargin, (layoutParams.leftMargin + cSBestSnapMainLayout.getWidth()) - dip2px, layoutParams.topMargin + cSBestSnapMainLayout.getHeight() + dip2px).contains(f, f2)) {
                    return cSBestSnapMainLayout;
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
        String str;
        int i2 = 0;
        Matrix matrix2;
        Paint paint2;
        int measureText = 0;
        CSBestDragSnapView cSBestDragSnapView = this;
        float dip2px = DMScreenInfoUtil.dip2px(cSBestDragSnapView.context, 25.0f);
        Matrix matrix3 = new Matrix();
        matrix3.setScale(canvas.getWidth() / getWidth(), canvas.getHeight() / getHeight());
        Paint paint3 = new Paint();
        paint3.setColor(cSBestDragSnapView.textColor);
        char c = 1;
        paint3.setAntiAlias(true);
        paint3.setTextSize(matrix3.mapRadius(DMScreenInfoUtil.sp2px(getContext(), cSBestDragSnapView.textSize)));
        paint3.setTextAlign(Paint.Align.CENTER);
        Paint paint4 = new Paint();
        paint4.setStyle(Paint.Style.FILL);
        paint4.setColor(cSBestDragSnapView.bgColor);
        paint4.setAntiAlias(true);
        DMStickerManager stickerImageManager = DMStickerModeManager2.getStickerImageManager(cSBestDragSnapView.context, DMStickerModeManager.StickerMode.STICKERALL);
        char c2 = 0;
        int i3 = 0;
        while (i3 < getChildCount()) {
            View childAt = cSBestDragSnapView.getChildAt(i3);
            if (childAt instanceof CSBestSnapMainLayout) {
                CSBestSnapMainLayout cSBestSnapMainLayout = (CSBestSnapMainLayout) childAt;
                LayoutParams layoutParams = (LayoutParams) cSBestSnapMainLayout.getLayoutParams();
                float[] fArr = new float[4];
                fArr[c2] = layoutParams.leftMargin;
                fArr[c] = layoutParams.topMargin;
                fArr[2] = layoutParams.leftMargin + childAt.getWidth();
                fArr[3] = layoutParams.topMargin + childAt.getHeight();
                matrix3.mapPoints(fArr);
                Rect rect = new Rect((int) fArr[c2], (int) fArr[c], (int) fArr[2], (int) fArr[3]);
                if (cSBestSnapMainLayout.mIsHasFrame) {
                    paint4.setColor(0);
                    canvas.drawBitmap(cSBestSnapMainLayout.mRes.getLocalImageBitmap(), (Rect) null, rect, (Paint) null);
                } else {
                    paint4.setColor(cSBestDragSnapView.bgColor);
                    canvas.drawRect(rect, paint4);
                }
                Paint.FontMetricsInt fontMetricsInt = paint3.getFontMetricsInt();
                int height = (rect.top + (((rect.height() + fontMetricsInt.top) - fontMetricsInt.bottom) / 2)) - fontMetricsInt.top;
                String[] split = ("" + ((Object) cSBestSnapMainLayout.mTextView.getText())).split("_aurona]");
                String str2 = "[aurona_";
                if (split == null || split.length <= 0) {
                    matrix = matrix3;
                    paint = paint4;
                    i = 0;
                } else {
                    int i4 = 0;
                    i = 0;
                    while (i4 < split.length) {
                        String str3 = split[i4];
                        if (str3.contains("[aurona_")) {
                            matrix2 = matrix3;
                            paint2 = paint4;
                            if (str3.length() > 11) {
                                measureText = ((int) (i + paint3.measureText(str3.substring(0, str3.length() - 11)))) + DMScreenInfoUtil.dip2px(getContext(), (int) ((rect.width() * 25.0f) / childAt.getWidth()));
                            } else {
                                i += DMScreenInfoUtil.dip2px(getContext(), (int) ((rect.width() * 25.0f) / childAt.getWidth()));
                                i4++;
                                matrix3 = matrix2;
                                paint4 = paint2;
                            }
                        } else {
                            matrix2 = matrix3;
                            paint2 = paint4;
                            measureText = (int) (i + paint3.measureText(str3));
                        }
                        i = measureText;
                        i4++;
                        matrix3 = matrix2;
                        paint4 = paint2;
                    }
                    matrix = matrix3;
                    paint = paint4;
                }
                float width = (rect.width() - i) / 2.0f;
                if (split != null && split.length > 0) {
                    int i5 = 0;
                    while (i5 < split.length) {
                        String str4 = split[i5];
                        if (str4.contains(str2)) {
                            try {
                                int indexOf = str4.indexOf(str2);
                                if (indexOf > 0) {
                                    strArr = split;
                                    try {
                                        String substring = str4.substring(0, indexOf);
                                        float measureText2 = paint3.measureText(substring);
                                        str = str2;
                                        try {
                                            canvas.drawText(substring, width + (measureText2 / 2.0f), height, paint3);
                                            width += measureText2;
                                        } catch (Exception unused) {
                                            f2 = dip2px;
                                            i5++;
                                            split = strArr;
                                            dip2px = f2;
                                            str2 = str;
                                        }
                                    } catch (Exception unused2) {
                                        str = str2;
                                        f2 = dip2px;
                                        i5++;
                                        split = strArr;
                                        dip2px = f2;
                                        str2 = str;
                                    }
                                } else {
                                    strArr = split;
                                    str = str2;
                                }
                                int width2 = (int) ((rect.width() * dip2px) / childAt.getWidth());
                                Bitmap iconBitmap = stickerImageManager.getRes(Integer.parseInt(str4.substring(str4.length() - 3, str4.length()))).getIconBitmap(width2, width2);
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
                                str = str2;
                            }
                            if (i2 < str4.length()) {
                                try {
                                    String substring2 = str4.substring(i2, str4.length() - 1);
                                    float measureText3 = paint3.measureText(substring2);
                                    canvas.drawText(substring2, (measureText3 / 2.0f) + width, height, paint3);
                                    width += measureText3;
                                } catch (Exception unused5) {
                                }
                            }
                        } else {
                            f2 = dip2px;
                            strArr = split;
                            str = str2;
                            float measureText4 = paint3.measureText(str4);
                            canvas.drawText(str4, width + (measureText4 / 2.0f), ((int) (((canvas.getWidth() * cSBestSnapMainLayout.mTextViewTopMargin) * 1.0f) / getWidth())) + height, paint3);
                            width += measureText4;
                        }
                        i5++;
                        split = strArr;
                        dip2px = f2;
                        str2 = str;
                    }
                }
                f = dip2px;
            } else {
                f = dip2px;
                matrix = matrix3;
                paint = paint4;
            }
            i3++;
            c = 1;
            c2 = 0;
            cSBestDragSnapView = this;
            matrix3 = matrix;
            paint4 = paint;
            dip2px = f;
        }
    }

    public OnSnapListener getOnSnapListener() {
        return this.onSnapListener;
    }

    public void setOnSnapListener(OnSnapListener onSnapListener) {
        this.onSnapListener = onSnapListener;
    }

    public boolean removeRelativeLayoutView(CSBestSnapMainLayout cSBestSnapMainLayout) {
        if (cSBestSnapMainLayout != null) {
            if (cSBestSnapMainLayout.mImageView != null) {
                recycleImageView(cSBestSnapMainLayout.mImageView);
            }
            removeView(cSBestSnapMainLayout);
            return true;
        }
        return false;
    }

    public boolean containsRelativeLayoutView(CSBestSnapMainLayout cSBestSnapMainLayout) {
        if (cSBestSnapMainLayout != null) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if ((childAt instanceof CSBestSnapMainLayout) && cSBestSnapMainLayout == childAt) {
                    return true;
                }
            }
        }
        return false;
    }

    private void recycleImageView(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            imageView.setImageBitmap(null);
            if (drawable != null) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                bitmapDrawable.setCallback(null);
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (bitmap == null || bitmap.isRecycled()) {
                    return;
                }
                bitmap.recycle();
            }
        }
    }

    public void resetMarginTop() {
        List<View> allChildViews = getAllChildViews(this, TextView.class);
        if (allChildViews == null || allChildViews.size() <= 0) {
            return;
        }
        for (int i = 0; i < allChildViews.size(); i++) {
            ((LayoutParams) allChildViews.get(i).getLayoutParams()).topMargin = DMScreenInfoUtil.dip2px(this.context, i * 15) + 0;
        }
    }

    private List<View> getAllChildViews(View view, Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt.getClass().equals(cls)) {
                    arrayList.add(childAt);
                }
                arrayList.addAll(getAllChildViews(childAt, cls));
            }
        }
        return arrayList;
    }
}

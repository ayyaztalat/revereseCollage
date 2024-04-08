package com.picspool.libfuncview.stretchlegs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSStretchView extends FrameLayout {
    private static final String TAG = "CSStretchView";
    float areaBak;
    private boolean compare;
    private Bitmap downImage;
    public Bitmap dstBitmap;
    private float end;
    public int height;
    private ImageView imgView1;
    private ImageView imgView2;
    private boolean isChanged;
    private boolean isShowMask;
    boolean isStartMoving;
    private Context mContext;
    Point posBak;
    OnStretchViewResetListener resetListener;
    private Bitmap srcBitmap;
    public int srcHeight;
    private int srcTop;
    public int srcWidth;
    private float start;
    private float stretch;

    /* renamed from: sx */
    int f1667sx;

    /* renamed from: sy */
    int f1668sy;
    private TextView textView;
    private int tipscolor;
    private Bitmap upImage;
    public int width;

    /* loaded from: classes.dex */
    public interface OnStretchViewResetListener {
        void onStretchViewReset();
    }

    public CSStretchView(Context context) {
        super(context);
        this.compare = false;
        this.tipscolor = 2147436392;
        this.isShowMask = false;
        this.isChanged = false;
        this.isStartMoving = true;
        this.mContext = context;
        initView();
    }

    public CSStretchView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.compare = false;
        this.tipscolor = 2147436392;
        this.isShowMask = false;
        this.isChanged = false;
        this.isStartMoving = true;
        this.mContext = context;
        initView();
    }

    public CSStretchView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.compare = false;
        this.tipscolor = 2147436392;
        this.isShowMask = false;
        this.isChanged = false;
        this.isStartMoving = true;
        this.mContext = context;
        initView();
    }

    public void setOnStretchViewResetListener(OnStretchViewResetListener onStretchViewResetListener) {
        this.resetListener = onStretchViewResetListener;
    }

    private void initView() {
        setWillNotDraw(false);
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_stretch, (ViewGroup) this, true);
        this.imgView1 = (ImageView) findViewById(R.id.imageView1);
        this.imgView2 = (ImageView) findViewById(R.id.imageView2);
        this.textView = (TextView) findViewById(R.id.textView1);
        this.imgView1.setTag(1);
        this.imgView2.setTag(0);
        try {
            this.upImage = BitmapFactory.decodeStream(getResources().getAssets().open("stretch/up.png"));
            this.downImage = BitmapFactory.decodeStream(getResources().getAssets().open("stretch/down.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = this.upImage;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.imgView1.setImageBitmap(this.upImage);
        }
        Bitmap bitmap2 = this.downImage;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.imgView2.setImageBitmap(this.downImage);
        }
        this.imgView1.setOnTouchListener(new OnImgViewTouch());
        this.imgView2.setOnTouchListener(new OnImgViewTouch());
        this.start = 0.4f;
        this.end = 0.6f;
    }

    public void setSrcWidthHeight(int i, int i2) {
        this.srcWidth = i;
        this.srcHeight = i2;
    }

    public void setBitmap(Bitmap bitmap) {
        this.srcBitmap = bitmap;
        Bitmap bitmap2 = this.dstBitmap;
        if (bitmap2 != bitmap) {
            recycleBitmap(bitmap2);
        }
        this.dstBitmap = this.srcBitmap;
    }

    public void resetView() {
        Bitmap bitmap = this.dstBitmap;
        if (bitmap != this.srcBitmap) {
            recycleBitmap(bitmap);
        }
        this.dstBitmap = this.srcBitmap;
        this.start = 0.4f;
        this.end = 0.6f;
        resetImgViewPosition(true);
    }

    public void setStretch(float f, boolean z, int i) {
        this.stretch = f;
        this.isChanged = f > 0.0f;
        invalidate();
        if (f == 0.0f && z) {
            this.srcTop = i;
        }
    }

    public int getDstBitmapHeight() {
        Bitmap bitmap = this.dstBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            Bitmap bitmap2 = this.srcBitmap;
            this.dstBitmap = bitmap2;
            if (bitmap2 == null || bitmap2.isRecycled()) {
                return 0;
            }
        }
        return this.dstBitmap.getHeight();
    }

    public void dispose() {
        dispose(true);
    }

    public void dispose(boolean z) {
        this.srcBitmap = null;
        this.dstBitmap = null;
        if (z) {
            recycleBitmap(this.upImage);
            recycleBitmap(this.downImage);
            releaseImage(this.imgView1);
            releaseImage(this.imgView2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recycleBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        bitmap.recycle();
    }

    private void releaseImage(ImageView imageView) {
        BitmapDrawable bitmapDrawable;
        if (imageView == null || (bitmapDrawable = (BitmapDrawable) imageView.getDrawable()) == null) {
            return;
        }
        imageView.setBackgroundResource(0);
        bitmapDrawable.setCallback(null);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        imageView.setImageBitmap(null);
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        bitmap.recycle();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.height = i2;
        this.width = i;
        resetImgViewPosition(false);
    }

    public void resetImgViewPosition(boolean z) {
        Bitmap bitmap = this.dstBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        float width = (this.width * 1.0f) / (this.dstBitmap.getWidth() * 1.0f);
        int height = (int) (((int) (this.dstBitmap.getHeight() * this.start)) * 1.0f * width);
        int height2 = this.height - ((int) ((this.dstBitmap.getHeight() - ((int) (this.dstBitmap.getHeight() * this.end))) * width));
        LayoutParams layoutParams = (LayoutParams) this.imgView1.getLayoutParams();
        LayoutParams layoutParams2 = (LayoutParams) this.imgView2.getLayoutParams();
        if (z) {
            layoutParams.topMargin = height2 - (layoutParams.height / 2);
            layoutParams2.topMargin = height - (layoutParams2.height / 2);
            this.imgView1.setTag(1);
            this.imgView2.setTag(0);
            this.imgView1.setImageBitmap(this.upImage);
            this.imgView2.setImageBitmap(this.downImage);
            this.imgView1.requestLayout();
            this.imgView2.requestLayout();
        } else if (layoutParams.topMargin > layoutParams2.topMargin) {
            layoutParams.topMargin = height2 - (layoutParams.height / 2);
            layoutParams2.topMargin = height - (layoutParams2.height / 2);
        } else {
            layoutParams.topMargin = height - (layoutParams.height / 2);
            layoutParams2.topMargin = height2 - (layoutParams2.height / 2);
        }
    }

    public void setCompare(boolean z) {
        this.compare = z;
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "dstBitmap.w: " + this.dstBitmap.getWidth() + "///dstBitmap.h:" + this.dstBitmap.getHeight() + "///canvas.w:" + canvas.getWidth() + "///canvas.h)" + canvas.getHeight());
        Bitmap bitmap = this.dstBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            Bitmap bitmap2 = this.srcBitmap;
            this.dstBitmap = bitmap2;
            if (bitmap2 == null || bitmap2.isRecycled()) {
                return;
            }
        }
        this.imgView1.setVisibility(this.compare ? View.GONE : View.VISIBLE);
        this.imgView2.setVisibility(this.compare ? View.GONE : View.VISIBLE);
        if (this.compare) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(getResources().getColor(R.color.libui_main_color_stretchviewbg_grey));
            canvas.drawRect(0.0f, 0.0f, getWidth(), getHeight(), paint);
            canvas.drawBitmap(this.srcBitmap, (Rect) null, new Rect(0, 0, this.srcWidth, this.srcHeight), (Paint) null);
            return;
        }
        int height = (int) (this.dstBitmap.getHeight() * this.start);
        int height2 = (int) (this.dstBitmap.getHeight() * this.end);
        float width = (canvas.getWidth() * 1.0f) / (this.dstBitmap.getWidth() * 1.0f);
        int i = (int) (height * width);
        int height3 = canvas.getHeight() - ((int) ((this.dstBitmap.getHeight() - height2) * width));
        if (this.stretch <= 0.0f) {
            canvas.drawBitmap(this.dstBitmap, new Rect(0, 0, this.dstBitmap.getWidth(), this.dstBitmap.getHeight()), new Rect(0, 0, canvas.getWidth(), canvas.getHeight()), new Paint());
        } else {
            if (this.start > 0.0f) {
                canvas.drawBitmap(this.dstBitmap, new Rect(0, 0, this.dstBitmap.getWidth(), height), new Rect(0, 0, canvas.getWidth(), i), new Paint());
            }
            if (this.end < 1.0f) {
                canvas.drawBitmap(this.dstBitmap, new Rect(0, height2, this.dstBitmap.getWidth(), this.dstBitmap.getHeight()), new Rect(0, height3, canvas.getWidth(), canvas.getHeight()), new Paint());
            }
            canvas.drawBitmap(this.dstBitmap, new Rect(0, height, this.dstBitmap.getWidth(), height2), new Rect(0, i, canvas.getWidth(), height3), new Paint());
        }
        Paint paint2 = new Paint();
        paint2.setColor(-1);
        LayoutParams layoutParams = (LayoutParams) this.imgView1.getLayoutParams();
        float f = i - 1;
        canvas.drawLine(0.0f, f, canvas.getWidth() - layoutParams.width, f, paint2);
        float f2 = height3;
        canvas.drawLine(0.0f, f2, canvas.getWidth() - layoutParams.width, f2, paint2);
        if (this.isShowMask) {
            this.textView.setVisibility(View.VISIBLE);
            Rect rect = new Rect(0, i, canvas.getWidth(), height3);
            paint2.setColor(this.tipscolor);
            paint2.setStyle(Paint.Style.FILL);
            canvas.drawRect(rect, paint2);
            int i2 = height3 - i;
            if (i2 < DMScreenInfoUtil.dip2px(this.mContext, 20.0f)) {
                this.textView.setVisibility(View.INVISIBLE);
                return;
            }
            this.textView.setVisibility(View.VISIBLE);
            LayoutParams layoutParams2 = (LayoutParams) this.textView.getLayoutParams();
            layoutParams2.topMargin = ((i2 / 2) + i) - (layoutParams2.height / 2);
        }
    }

    public Bitmap getResultBitmap() {
        Bitmap bitmap = this.dstBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            Bitmap bitmap2 = this.srcBitmap;
            this.dstBitmap = bitmap2;
            if (bitmap2 == null || bitmap2.isRecycled()) {
                return null;
            }
        }
        float f = this.stretch;
        if (f <= 0.0f) {
            return this.dstBitmap;
        }
        int height = (int) ((f + 1.0f) * this.dstBitmap.getHeight());
        int height2 = (int) (this.dstBitmap.getHeight() * this.start);
        int height3 = (int) (this.dstBitmap.getHeight() * this.end);
        int height4 = height - (this.dstBitmap.getHeight() - height3);
        Bitmap createBitmap = Bitmap.createBitmap(this.dstBitmap.getWidth(), height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        if (this.start > 0.0f) {
            canvas.drawBitmap(this.dstBitmap, new Rect(0, 0, this.dstBitmap.getWidth(), height2), new Rect(0, 0, this.dstBitmap.getWidth(), height2), new Paint());
        }
        if (this.end < 1.0f) {
            canvas.drawBitmap(this.dstBitmap, new Rect(0, height3, this.dstBitmap.getWidth(), this.dstBitmap.getHeight()), new Rect(0, height4, this.dstBitmap.getWidth(), height), new Paint());
        }
        canvas.drawBitmap(this.dstBitmap, new Rect(0, height2, this.dstBitmap.getWidth(), height3), new Rect(0, height2, this.dstBitmap.getWidth(), height4), new Paint());
        return createBitmap;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class OnImgViewTouch implements OnTouchListener {
        OnImgViewTouch() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            boolean z;
            int action = motionEvent.getAction();
            if (action == 0) {
                if (CSStretchView.this.isChanged) {
                    CSStretchView.this.isChanged = false;
                    float width = (CSStretchView.this.width * 1.0f) / (CSStretchView.this.dstBitmap.getWidth() * 1.0f);
                    int height = (int) (((int) (CSStretchView.this.dstBitmap.getHeight() * CSStretchView.this.start)) * width);
                    int height2 = CSStretchView.this.height - ((int) ((CSStretchView.this.dstBitmap.getHeight() - ((int) (CSStretchView.this.dstBitmap.getHeight() * CSStretchView.this.end))) * width));
                    Bitmap resultBitmap = CSStretchView.this.getResultBitmap();
                    if (CSStretchView.this.dstBitmap != CSStretchView.this.srcBitmap) {
                        CSStretchView cSStretchView = CSStretchView.this;
                        cSStretchView.recycleBitmap(cSStretchView.dstBitmap);
                    }
                    CSStretchView.this.dstBitmap = resultBitmap;
                    CSStretchView.this.stretch = 0.0f;
                    if (CSStretchView.this.resetListener != null) {
                        CSStretchView.this.resetListener.onStretchViewReset();
                    }
                    CSStretchView cSStretchView2 = CSStretchView.this;
                    cSStretchView2.start = (height * 1.0f) / (cSStretchView2.height * 1.0f);
                    CSStretchView cSStretchView3 = CSStretchView.this;
                    cSStretchView3.end = (height2 * 1.0f) / (cSStretchView3.height * 1.0f);
                    CSStretchView.this.resetImgViewPosition(false);
                }
                CSStretchView.this.f1667sx = (int) motionEvent.getRawX();
                CSStretchView.this.f1668sy = (int) motionEvent.getRawY();
                CSStretchView.this.posBak = new Point(0, ((LayoutParams) view.getLayoutParams()).topMargin);
                LayoutParams layoutParams = (LayoutParams) CSStretchView.this.imgView1.getLayoutParams();
                LayoutParams layoutParams2 = (LayoutParams) CSStretchView.this.imgView2.getLayoutParams();
                CSStretchView.this.isStartMoving = true;
                if (view == CSStretchView.this.imgView1) {
                    if (layoutParams.topMargin <= layoutParams2.topMargin) {
                        CSStretchView cSStretchView4 = CSStretchView.this;
                        cSStretchView4.areaBak = cSStretchView4.start;
                    } else {
                        CSStretchView.this.isStartMoving = false;
                        CSStretchView cSStretchView5 = CSStretchView.this;
                        cSStretchView5.areaBak = cSStretchView5.end;
                    }
                } else if (layoutParams.topMargin < layoutParams2.topMargin) {
                    CSStretchView cSStretchView6 = CSStretchView.this;
                    cSStretchView6.areaBak = cSStretchView6.end;
                    CSStretchView.this.isStartMoving = false;
                } else {
                    CSStretchView cSStretchView7 = CSStretchView.this;
                    cSStretchView7.areaBak = cSStretchView7.start;
                }
            } else if (action == 1) {
                CSStretchView.this.textView.setVisibility(View.INVISIBLE);
                CSStretchView.this.isShowMask = false;
                CSStretchView.this.invalidate();
            } else if (action == 2) {
                CSStretchView.this.isShowMask = true;
                motionEvent.getRawX();
                int i = CSStretchView.this.f1667sx;
                int rawY = ((int) motionEvent.getRawY()) - CSStretchView.this.f1668sy;
                LayoutParams layoutParams3 = (LayoutParams) view.getLayoutParams();
                layoutParams3.topMargin = CSStretchView.this.posBak.y + rawY;
                float f = (layoutParams3.topMargin + (layoutParams3.height / 2)) / CSStretchView.this.height;
                if (CSStretchView.this.isStartMoving) {
                    if (CSStretchView.this.start <= CSStretchView.this.end) {
                        CSStretchView.this.start = f;
                        if (view.getTag().equals(1)) {
                            view.setTag(0);
                            z = true;
                        }
                        z = false;
                    } else {
                        CSStretchView cSStretchView8 = CSStretchView.this;
                        cSStretchView8.start = cSStretchView8.end;
                        CSStretchView.this.end = f;
                        if (view.getTag().equals(0)) {
                            CSStretchView.this.isStartMoving = false;
                            view.setTag(1);
                            z = true;
                        }
                        z = false;
                    }
                } else if (f > CSStretchView.this.start) {
                    CSStretchView.this.end = f;
                    if (view.getTag().equals(0)) {
                        view.setTag(1);
                        z = true;
                    }
                    z = false;
                } else {
                    CSStretchView cSStretchView9 = CSStretchView.this;
                    cSStretchView9.end = cSStretchView9.start;
                    CSStretchView.this.start = f;
                    if (view.getTag().equals(1)) {
                        CSStretchView.this.isStartMoving = true;
                        view.setTag(0);
                        z = true;
                    }
                    z = false;
                }
                if (z) {
                    if (view == CSStretchView.this.imgView1) {
                        if (view.getTag().equals(1)) {
                            CSStretchView.this.imgView2.setTag(0);
                            CSStretchView.this.imgView1.setImageBitmap(CSStretchView.this.upImage);
                            CSStretchView.this.imgView2.setImageBitmap(CSStretchView.this.downImage);
                        } else {
                            CSStretchView.this.imgView2.setTag(1);
                            CSStretchView.this.imgView1.setImageBitmap(CSStretchView.this.downImage);
                            CSStretchView.this.imgView2.setImageBitmap(CSStretchView.this.upImage);
                        }
                    } else if (view.getTag().equals(1)) {
                        CSStretchView.this.imgView1.setTag(0);
                        CSStretchView.this.imgView1.setImageBitmap(CSStretchView.this.downImage);
                        CSStretchView.this.imgView2.setImageBitmap(CSStretchView.this.upImage);
                    } else {
                        CSStretchView.this.imgView1.setTag(1);
                        CSStretchView.this.imgView1.setImageBitmap(CSStretchView.this.upImage);
                        CSStretchView.this.imgView2.setImageBitmap(CSStretchView.this.downImage);
                    }
                }
                view.requestLayout();
            } else if (action == 3) {
                CSStretchView.this.textView.setVisibility(View.INVISIBLE);
                CSStretchView.this.isShowMask = false;
                CSStretchView.this.invalidate();
            }
            return true;
        }
    }
}

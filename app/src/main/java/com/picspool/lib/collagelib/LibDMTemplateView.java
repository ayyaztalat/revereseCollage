package com.picspool.lib.collagelib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.collagelib.LibDMMaskImageViewTouch;
import com.picspool.lib.collagelib.resource.LibDMTemplateRes;
import com.picspool.lib.collagelib.resource.collage.LibDMCollageInfo;
import com.picspool.lib.resource.DMWBColorRes;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;

import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class LibDMTemplateView extends RelativeLayout {
    int KMaxPix;
    private String TAG;
    private Bitmap backgroundBitmap;
    public int backgroundColor;
    public boolean bgIsBitmap;
    protected List<Bitmap> bitmaps;
    private View customView;
    private onFilterClickListener filterListener;
    private ImageView filter_img;
    Bitmap foregroundBitmap;
    public int imagecount;
    public Boolean imgExchanger;
    protected ImageView img_bg;
    protected ImageView img_fg;
    protected FrameLayout imgvwlayout;
    List<ImageView> lstTouchImageViews;
    protected List<LibDMMaskImageViewTouch> lstmsivt;
    private Drawable mBackgroundDrawable;
    private boolean mBgIsTile;
    LibDMTemplateRes mComposeInfo;
    Context mContext;
    private int mCropSize;
    int mHeight;
    public OnItemLongClickListener mItemLonglistener;
    public OnItemClickListener mItemlistener;
    private float mMaxZoom;
    private OnOneImageClickedListener mOnOneImageClickedListener;
    Bitmap mProcessedBitmap;
    protected Bitmap mResourceBmp;
    int mRotaiton;
    int mShadowValue;
    int mWidth;
    public Bitmap m_vOriginalBitmap;
    public String m_vOriginalfilename;
    protected LibDMMaskImageViewTouch m_vSel;
    protected LibDMMaskImageViewTouch m_vSelOri;
    public String[] m_vfilenames;
    public int m_vwCount;
    protected LibDMMaskImageViewTouch[] m_vws;
    int minnerWidth;
    private ImageView mirror_img;
    int mouterWidth;
    Bitmap picBitmap;
    private PopupWindow popupwindow;
    private float radius;
    private ImageView rotate_img;
    Bitmap tmpBitmap;
    FrameLayout touchimglayout;
    int viewWidth;

    /* loaded from: classes3.dex */
    public interface OnItemClickListener {
        void ItemClick(View view, String str);
    }

    /* loaded from: classes3.dex */
    public interface OnItemLongClickListener {
        void ItemLongClick(View view, int i, String str);
    }

    /* loaded from: classes3.dex */
    public interface OnOneImageClickedListener {
        void onOneImageClicked(LibDMMaskImageViewTouch libDMMaskImageViewTouch);
    }

    /* loaded from: classes3.dex */
    public interface onFilterClickListener {
        void addFilterBar(RelativeLayout relativeLayout);

        void onFilterClick(LibDMMaskImageViewTouch libDMMaskImageViewTouch);

        void removeFilterBar(RelativeLayout relativeLayout);
    }

    /* loaded from: classes3.dex */
    public interface onOutputImageListener {
        void onOutputImageFinish(Bitmap bitmap);
    }

    public LibDMTemplateView(Context context) {
        super(context);
        this.KMaxPix = 612;
        this.TAG = "ImageCollageView";
        this.bitmaps = null;
        this.radius = 0.0f;
        this.imagecount = 1;
        this.imgExchanger = false;
        this.m_vwCount = 9;
        this.minnerWidth = 10;
        this.mouterWidth = 10;
        this.mRotaiton = 0;
        this.backgroundColor = -1;
        this.bgIsBitmap = false;
        this.lstmsivt = new ArrayList();
        this.mMaxZoom = 2.5f;
        this.viewWidth = 720;
        this.mCropSize = 612;
        this.mBgIsTile = true;
        this.mShadowValue = 10;
        this.mContext = context;
        initView();
    }

    public LibDMTemplateView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.KMaxPix = 612;
        this.TAG = "ImageCollageView";
        this.bitmaps = null;
        this.radius = 0.0f;
        this.imagecount = 1;
        this.imgExchanger = false;
        this.m_vwCount = 9;
        this.minnerWidth = 10;
        this.mouterWidth = 10;
        this.mRotaiton = 0;
        this.backgroundColor = -1;
        this.bgIsBitmap = false;
        this.lstmsivt = new ArrayList();
        this.mMaxZoom = 2.5f;
        this.viewWidth = 720;
        this.mCropSize = 612;
        this.mBgIsTile = true;
        this.mShadowValue = 10;
        this.mContext = context;
        initView();
    }

    public LibDMTemplateView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.KMaxPix = 612;
        this.TAG = "ImageCollageView";
        this.bitmaps = null;
        this.radius = 0.0f;
        this.imagecount = 1;
        this.imgExchanger = false;
        this.m_vwCount = 9;
        this.minnerWidth = 10;
        this.mouterWidth = 10;
        this.mRotaiton = 0;
        this.backgroundColor = -1;
        this.bgIsBitmap = false;
        this.lstmsivt = new ArrayList();
        this.mMaxZoom = 2.5f;
        this.viewWidth = 720;
        this.mCropSize = 612;
        this.mBgIsTile = true;
        this.mShadowValue = 10;
        this.mContext = context;
        initView();
    }

    public void setFilterOnClickListener(onFilterClickListener onfilterclicklistener) {
        this.filterListener = onfilterclicklistener;
    }

    public void setOnOneImageClickedListener(OnOneImageClickedListener onOneImageClickedListener) {
        this.mOnOneImageClickedListener = onOneImageClickedListener;
    }

    public void initmPopupWindowView() {
        this.customView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_popview_item, (ViewGroup) null);
        this.popupwindow = new PopupWindow(this.customView, DMScreenInfoUtil.dip2px(getContext(), 150.0f), DMScreenInfoUtil.dip2px(getContext(), 70.0f));
        this.customView.setOnTouchListener(new OnTouchListener() { // from class: com.picspool.lib.collagelib.LibDMTemplateView.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                LibDMTemplateView.this.resetPopupWindow();
                return false;
            }
        });
    }

    public void resetPopupWindow() {
        PopupWindow popupWindow = this.popupwindow;
        if (popupWindow == null || !popupWindow.isShowing()) {
            return;
        }
        this.popupwindow.dismiss();
        this.popupwindow = null;
    }

    protected void initView() {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_view_collage, (ViewGroup) this, true);
        this.m_vfilenames = new String[this.m_vwCount];
        this.imgvwlayout = (FrameLayout) findViewById(R.id.imgvwlayout);
        this.img_fg = (ImageView) findViewById(R.id.img_fg);
        this.lstmsivt.clear();
        this.touchimglayout = (FrameLayout) findViewById(R.id.touchimglayout);
        ImageView imageView = (ImageView) findViewById(R.id.img_bg);
        this.img_bg = imageView;
        imageView.setBackgroundColor(this.backgroundColor);
        this.m_vws = new LibDMMaskImageViewTouch[this.m_vwCount];
        for (int i = 0; i < this.m_vwCount; i++) {
            LibDMMaskImageViewTouch creatMaskView = creatMaskView();
            creatMaskView.setTag(Integer.valueOf(i));
            creatMaskView.setOnClickListener(new OnViewItemListener(i));
            LibDMMaskImageViewTouch[] libDMMaskImageViewTouchArr = this.m_vws;
            libDMMaskImageViewTouchArr[i] = creatMaskView;
            libDMMaskImageViewTouchArr[i].setIndex(i);
            creatMaskView.mClickListener = new LibDMMaskImageViewTouch.OnCustomeClickListener() { // from class: com.picspool.lib.collagelib.LibDMTemplateView.2
                @Override // com.picspool.lib.collagelib.LibDMMaskImageViewTouch.OnCustomeClickListener
                public void CustomeClick(int i2) {
                    LibDMTemplateView libDMTemplateView = LibDMTemplateView.this;
                    libDMTemplateView.m_vSel = libDMTemplateView.m_vws[i2];
                    Boolean drowRectangle = LibDMTemplateView.this.m_vSel.getDrowRectangle();
                    for (int i3 = 0; i3 < LibDMTemplateView.this.m_vwCount; i3++) {
                        LibDMTemplateView.this.m_vws[i3].setDrowRectangle(false);
                    }
                    LibDMTemplateView libDMTemplateView2 = LibDMTemplateView.this;
                    libDMTemplateView2.ExchangeImage(libDMTemplateView2.m_vSel);
                    if (!drowRectangle.booleanValue()) {
                        LibDMTemplateView.this.m_vSel.setDrowRectangle(true);
                    }
                    if (LibDMTemplateView.this.popupwindow != null && LibDMTemplateView.this.popupwindow.isShowing()) {
                        LibDMTemplateView.this.popupwindow.dismiss();
                        LibDMTemplateView.this.popupwindow = null;
                    }
                    LibDMTemplateView libDMTemplateView3 = LibDMTemplateView.this;
                    libDMTemplateView3.mResourceBmp = libDMTemplateView3.getSelBitmap();
                    if (LibDMTemplateView.this.mItemlistener != null) {
                        LibDMTemplateView.this.mItemlistener.ItemClick(LibDMTemplateView.this.m_vSel, LibDMTemplateView.this.m_vfilenames[i2]);
                    }
                }

                @Override // com.picspool.lib.collagelib.LibDMMaskImageViewTouch.OnCustomeClickListener
                public void CustomeTouchUp(int i2) {
                    if (LibDMTemplateView.this.mOnOneImageClickedListener != null) {
                        if (LibDMTemplateView.this.m_vSel.getDrowRectangle().booleanValue() && !LibDMTemplateView.this.imgExchanger.booleanValue()) {
                            LibDMTemplateView.this.m_vSel.setDrowRectangle(true);
                        }
                        LibDMTemplateView.this.mOnOneImageClickedListener.onOneImageClicked(LibDMTemplateView.this.m_vSel);
                        LibDMTemplateView.this.imgExchanger = false;
                        return;
                    }
                    if (!LibDMTemplateView.this.m_vSel.getDrowRectangle().booleanValue() || LibDMTemplateView.this.imgExchanger.booleanValue()) {
                        if (LibDMTemplateView.this.popupwindow != null && LibDMTemplateView.this.popupwindow.isShowing()) {
                            LibDMTemplateView.this.popupwindow.dismiss();
                            LibDMTemplateView.this.popupwindow = null;
                        }
                    } else {
                        LibDMTemplateView.this.filterListener.removeFilterBar(null);
                        LibDMTemplateView.this.m_vSel.setDrowRectangle(true);
                        LibDMTemplateView.this.initmPopupWindowView();
                        LibDMTemplateView.this.popupwindow.showAsDropDown(LibDMTemplateView.this.m_vSel, (LibDMTemplateView.this.m_vSel.getWidth() / 2) - 110, ((-LibDMTemplateView.this.m_vSel.getHeight()) / 2) - 40);
                    }
                    if (LibDMTemplateView.this.customView != null) {
                        LibDMTemplateView libDMTemplateView = LibDMTemplateView.this;
                        libDMTemplateView.mResourceBmp = libDMTemplateView.getSelBitmap();
                        if (LibDMTemplateView.this.mResourceBmp == null || LibDMTemplateView.this.mResourceBmp.isRecycled()) {
                            return;
                        }
                        LibDMTemplateView libDMTemplateView2 = LibDMTemplateView.this;
                        libDMTemplateView2.rotate_img = (ImageView) libDMTemplateView2.customView.findViewById(R.id.image_ad);
                        LibDMTemplateView.this.rotate_img.setOnClickListener(new RotateImgOnClickListener());
                        LibDMTemplateView libDMTemplateView3 = LibDMTemplateView.this;
                        libDMTemplateView3.filter_img = (ImageView) libDMTemplateView3.customView.findViewById(R.id.imageView2);
                        LibDMTemplateView.this.filter_img.setOnClickListener(new FilterImgOnClickListener());
                        LibDMTemplateView libDMTemplateView4 = LibDMTemplateView.this;
                        libDMTemplateView4.mirror_img = (ImageView) libDMTemplateView4.customView.findViewById(R.id.imageView3);
                        LibDMTemplateView.this.mirror_img.setOnClickListener(new MirrorImgOnClickListener());
                    }
                    LibDMTemplateView.this.imgExchanger = false;
                }
            };
            creatMaskView.setCustomeLongClickListener(new LibDMMaskImageViewTouch.OnCustomeLongClickListener() { // from class: com.picspool.lib.collagelib.LibDMTemplateView.3
                @Override // com.picspool.lib.collagelib.LibDMMaskImageViewTouch.OnCustomeLongClickListener
                public void CustomeLongClick(int i2) {
                    if (LibDMTemplateView.this.popupwindow != null && LibDMTemplateView.this.popupwindow.isShowing()) {
                        LibDMTemplateView.this.popupwindow.dismiss();
                    }
                    if (LibDMTemplateView.this.mItemLonglistener != null) {
                        LibDMTemplateView libDMTemplateView = LibDMTemplateView.this;
                        libDMTemplateView.m_vSel = libDMTemplateView.m_vws[i2];
                        LibDMTemplateView.this.m_vSel.setDrowRectangle(true);
                        LibDMTemplateView.this.setOriginalView();
                        LibDMTemplateView.this.mItemLonglistener.ItemLongClick(LibDMTemplateView.this.m_vws[i2], 2, LibDMTemplateView.this.m_vfilenames[i2]);
                    }
                }
            });
            this.imgvwlayout.addView(creatMaskView, i);
        }
    }

    private void setTouchImage() {
        List<LibDMCollageInfo.LibCollageTouchPoint> touchCollagePoints = this.m_vSel.getCollageInfo().getTouchCollagePoints();
        if (touchCollagePoints.size() > 0) {
            float f = this.mHeight;
            float f2 = this.mWidth;
            float f3 = f2 / 3060.0f;
            float f4 = f / ((f / f2) * 3060.0f);
            int dip2px = DMScreenInfoUtil.dip2px(getContext(), 20.0f);
            for (LibDMCollageInfo.LibCollageTouchPoint libCollageTouchPoint : touchCollagePoints) {
                Point touchPoint = libCollageTouchPoint.getTouchPoint();
                ImageView imageView = new ImageView(getContext());
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(dip2px, dip2px);
                float f5 = dip2px / 2.0f;
                layoutParams.setMargins((int) ((touchPoint.x * f3) - f5), (int) ((touchPoint.y * f4) - f5), 0, 0);
                layoutParams.gravity = 3;
                imageView.setImageResource(R.drawable.ic_launcher);
                imageView.setTag(libCollageTouchPoint);
                this.touchimglayout.addView(imageView, layoutParams);
            }
        }
    }

    /* loaded from: classes3.dex */
    protected class RotateImgOnClickListener implements OnClickListener {
        protected RotateImgOnClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            LibDMTemplateView.this.doRotation(90.0f);
        }
    }

    /* loaded from: classes3.dex */
    public class FilterImgOnClickListener implements OnClickListener {
        public FilterImgOnClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (LibDMTemplateView.this.popupwindow != null && LibDMTemplateView.this.popupwindow.isShowing()) {
                LibDMTemplateView.this.popupwindow.dismiss();
                LibDMTemplateView.this.popupwindow = null;
            }
            if (LibDMTemplateView.this.filterListener != null) {
                LibDMTemplateView.this.filterListener.onFilterClick(LibDMTemplateView.this.m_vSel);
            }
        }
    }

    public void setFilterBitmap(LibDMMaskImageViewTouch libDMMaskImageViewTouch, Bitmap bitmap) {
        libDMMaskImageViewTouch.setImageBitmap(bitmap, false);
        setViewBitmap(libDMMaskImageViewTouch, bitmap, "");
        libDMMaskImageViewTouch.invalidate();
    }

    public void setPictureImageBitmapNoReset(Bitmap bitmap) {
        this.m_vSel.setImageBitmapWithStatKeep(null);
        this.m_vSel.setImageBitmap(bitmap, false);
        this.m_vSel.invalidate();
    }

    /* loaded from: classes3.dex */
    protected class MirrorImgOnClickListener implements OnClickListener {
        protected MirrorImgOnClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            LibDMTemplateView.this.doReversal(180.0f);
        }
    }

    public void clearDrowRectangle() {
        for (int i = 0; i < this.m_vwCount; i++) {
            this.m_vws[i].setDrowRectangle(false);
        }
    }

    public void setBackgroundImageBitmap(Bitmap bitmap, boolean z) {
        this.backgroundColor = -1;
        this.mBgIsTile = z;
        if (this.mBackgroundDrawable != null) {
            this.img_bg.setImageDrawable(null);
            this.mBackgroundDrawable = null;
        }
        if (this.backgroundBitmap != null) {
            this.img_bg.setImageBitmap(null);
            DMBitmapUtil.ourBitmapRecycle(this.backgroundBitmap, false);
            this.backgroundBitmap = null;
        }
        if (bitmap != null && !bitmap.isRecycled()) {
            this.bgIsBitmap = true;
            this.backgroundBitmap = bitmap;
            if (z) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(this.backgroundBitmap);
                bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
                bitmapDrawable.setDither(true);
                bitmapDrawable.setBounds(0, 0, 1280, 1280);
                this.img_bg.setImageDrawable(bitmapDrawable);
                return;
            }
            this.img_bg.setImageBitmap(bitmap);
            return;
        }
        this.bgIsBitmap = false;
        this.img_bg.setImageBitmap(null);
    }

    public void setViewGradientBackground(Drawable drawable) {
        this.backgroundColor = -1;
        Drawable drawable2 = this.mBackgroundDrawable;
        if (drawable2 != null) {
            recycleDrawable(drawable2);
            this.mBackgroundDrawable = null;
        }
        if (this.backgroundBitmap != null) {
            this.img_bg.setImageBitmap(null);
            DMBitmapUtil.ourBitmapRecycle(this.backgroundBitmap, false);
            this.backgroundBitmap = null;
        }
        this.backgroundColor = 0;
        this.mBackgroundDrawable = drawable;
        drawable.setBounds(0, 0, getWidth(), getHeight());
        setMyViewBackgroud(drawable);
    }

    private void setMyViewBackgroud(Drawable drawable) {
        if (Build.VERSION.SDK_INT < 16) {
            this.img_bg.setBackgroundDrawable(drawable);
        } else {
            setMyViewBackgroud(this.img_bg, drawable);
        }
    }

    private void setMyViewBackgroud(View view, Drawable drawable) {
        view.setBackground(drawable);
    }

    private void recycleDrawable(Drawable drawable) {
        Bitmap bitmap;
        if (!(drawable instanceof BitmapDrawable) || (bitmap = ((BitmapDrawable) drawable).getBitmap()) == null || bitmap.isRecycled()) {
            return;
        }
        bitmap.recycle();
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        this.backgroundColor = -1;
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable != null) {
            recycleDrawable(drawable);
            this.mBackgroundDrawable = null;
        }
        if (this.backgroundBitmap != null) {
            this.img_bg.setImageBitmap(null);
            DMBitmapUtil.ourBitmapRecycle(this.backgroundBitmap, false);
            this.backgroundBitmap = null;
        }
        this.backgroundColor = i;
        this.img_bg.setBackgroundColor(i);
    }

    public void noBg() {
        setBackgroundColor(-1);
    }

    private Bitmap createBgImage(int i) {
        int i2 = (int) ((i * (this.mHeight / this.mWidth)) + 0.5f);
        if (this.mBackgroundDrawable != null) {
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Rect rect = new Rect();
            rect.left = 0;
            rect.right = i;
            rect.top = 0;
            rect.bottom = i2;
            this.mBackgroundDrawable.setBounds(rect);
            this.mBackgroundDrawable.draw(canvas);
            return createBitmap;
        }
        Bitmap bitmap = this.backgroundBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            return createTileBgImage(i);
        }
        Bitmap createBitmap2 = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(createBitmap2);
        Paint paint = new Paint();
        paint.setColor(this.backgroundColor);
        paint.setDither(false);
        canvas2.drawRect(new Rect(0, 0, i, i2), paint);
        return createBitmap2;
    }

    private Bitmap createTileBgImage(int i) {
        int i2 = (int) ((i * (this.mHeight / this.mWidth)) + 0.5f);
        int width = this.backgroundBitmap.getWidth();
        int height = this.backgroundBitmap.getHeight();
        int i3 = ((i + width) - 1) / width;
        int i4 = ((i2 + height) - 1) / height;
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        if (this.mBgIsTile) {
            for (int i5 = 0; i5 < i3; i5++) {
                float f = i5 * width;
                canvas.drawBitmap(this.backgroundBitmap, f, 0.0f, (Paint) null);
                for (int i6 = 1; i6 < i4; i6++) {
                    canvas.drawBitmap(this.backgroundBitmap, f, i6 * height, (Paint) null);
                }
            }
        } else {
            canvas.drawBitmap(this.backgroundBitmap, new Rect(0, 0, this.backgroundBitmap.getWidth(), this.backgroundBitmap.getHeight()), new Rect(0, 0, i, i2), new Paint());
        }
        return createBitmap;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes3.dex */
    public class OnViewItemListener implements OnClickListener {
        int index;

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
        }

        public OnViewItemListener(int i) {
            this.index = i;
        }
    }

    private void setViewBitmap(View view, Bitmap bitmap, String str) {
        for (int i = 0; i < this.m_vwCount; i++) {
            LibDMMaskImageViewTouch[] libDMMaskImageViewTouchArr = this.m_vws;
            if (libDMMaskImageViewTouchArr[i] == view) {
                libDMMaskImageViewTouchArr[i].setImageBitmap(bitmap);
                this.bitmaps.set(i, bitmap);
                if (bitmap != null) {
                    this.viewWidth = bitmap.getWidth() / 2;
                    return;
                }
                return;
            }
        }
    }

    public void setViewBitmap(Bitmap bitmap, String str) {
        if (this.m_vSel != null) {
            if (bitmap == null) {
                str = "";
            }
            setViewBitmap(this.m_vSel, bitmap, str);
            changeCornerRadius((int) this.radius);
        }
    }

    public void restCollageView() {
        for (int i = 0; i < this.m_vwCount; i++) {
            this.m_vws[i].clear();
        }
        List<Bitmap> list = this.bitmaps;
        if (list != null) {
            for (Bitmap bitmap : list) {
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
            this.bitmaps.clear();
        }
        this.radius = 0.0f;
    }

    public void setCollageImages(List<Bitmap> list, List<Uri> list2, boolean z, int i) {
        this.bitmaps = list;
        this.mCropSize = i;
        if (list == null) {
            return;
        }
        int size = list.size();
        this.imagecount = size;
        if (size == 1) {
            this.m_vws[0].setIsLongclick(false);
        } else {
            this.m_vws[0].setIsLongclick(true);
        }
        int i2 = 0;
        while (i2 < this.m_vwCount) {
            this.m_vws[i2].setVisibility(this.imagecount > i2 ? View.VISIBLE : View.INVISIBLE);
            this.m_vws[i2].setTag(Integer.valueOf(i2));
            this.m_vws[i2].setIndex(i2);
            if (this.m_vws[i2].getVisibility() == View.VISIBLE) {
                this.m_vws[i2].setImageBitmap(list.get(i2), z, null, 4.0f);
                this.m_vws[i2].setUri(list2.get(i2));
                LibDMBitmapInfo libDMBitmapInfo = new LibDMBitmapInfo();
                libDMBitmapInfo.setIsHorizontalMirror(false);
                libDMBitmapInfo.setRotationDegree(0);
                libDMBitmapInfo.setUri(list2.get(i2));
                this.m_vws[i2].setBitmapInfo(libDMBitmapInfo);
            } else {
                this.m_vfilenames[i2] = null;
            }
            i2++;
        }
    }

    public float getRadius() {
        return this.radius;
    }

    public float getInnerWidth() {
        return this.minnerWidth;
    }

    public float getOuterWidth() {
        return this.mouterWidth;
    }

    public void changeCornerRadius(int i) {
        for (int i2 = 0; i2 < this.m_vwCount; i2++) {
            this.m_vws[i2].changeRadius(i);
        }
        this.radius = i;
    }

    public void changeCornerRadius(boolean z) {
        int i = !z ? 0 : 10;
        for (int i2 = 0; i2 < this.m_vwCount; i2++) {
            this.m_vws[i2].changeRadius(i);
        }
        this.radius = i;
    }

    public void setCollageStyle(LibDMTemplateRes libDMTemplateRes, int i, int i2) {
        this.mHeight = i;
        this.mWidth = i2;
        if (libDMTemplateRes != null) {
            this.mComposeInfo = libDMTemplateRes;
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = i;
        layoutParams.width = i2;
        setLayoutParams(layoutParams);
        layoutCompose(i, i2);
        requestLayout();
    }

    public int getCollageWidth() {
        return this.mWidth;
    }

    public int getCollageHeight() {
        return this.mHeight;
    }

    public void setCollageStyle(LibDMTemplateRes libDMTemplateRes) {
        this.mComposeInfo = libDMTemplateRes;
        invalidate();
    }

    public void updateGradientBackground() {
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable == null) {
            return;
        }
        drawable.setBounds(0, 0, getWidth(), getHeight());
        this.backgroundColor = 0;
        if (this.backgroundBitmap != null) {
            this.img_bg.setImageBitmap(null);
            DMBitmapUtil.ourBitmapRecycle(this.backgroundBitmap, false);
            this.backgroundBitmap = null;
        }
        setMyViewBackgroud(this.mBackgroundDrawable);
    }

    public void getOutputImage(int i, onOutputImageListener onoutputimagelistener) {
        Paint paint = new Paint();
        float f = this.mHeight / this.mWidth;
        Bitmap createBgImage = createBgImage(i);
        Bitmap createBitmap = Bitmap.createBitmap(i, (int) ((i * f) + 0.5f), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        if (createBgImage != null) {
            canvas.drawBitmap(createBgImage, 0.0f, 0.0f, paint);
            if (!createBgImage.isRecycled()) {
                createBgImage.recycle();
            }
        }
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        for (int i2 = 0; i2 < this.mComposeInfo.getCollageInfo().size(); i2++) {
            Rect layoutPosition = getLayoutPosition(i, this.mComposeInfo.getCollageInfo().get(i2).GetRect(f));
            Bitmap dispalyImage = this.m_vws[i2].getDispalyImage(layoutPosition.right - layoutPosition.left, layoutPosition.bottom - layoutPosition.top);
            if (dispalyImage != null) {
                canvas.drawBitmap(dispalyImage, (Rect) null, layoutPosition, paint);
                dispalyImage.recycle();
            }
        }
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable != null) {
            drawable.setBounds(0, 0, getWidth(), getHeight());
            setMyViewBackgroud(this.mBackgroundDrawable);
        }
        if (onoutputimagelistener != null) {
            onoutputimagelistener.onOutputImageFinish(createBitmap);
        }
    }

    public Bitmap getOutputImage(int i, float f) {
        return getOutputImage(i, true, f);
    }

    public Bitmap getOutputImage(int i) {
        return getOutputImage(i, true, 0.0f);
    }

    public Bitmap getOutputImage(int i, boolean z, float f) {
        Paint paint = new Paint();
        float f2 = this.mHeight / this.mWidth;
        int i2 = (int) ((i * f2) + 0.5f);
        if (i2 == 0) {
            i2 = i;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        if (z) {
            Bitmap createBgImage = createBgImage(i);
            if (createBgImage != null) {
                paint.setColorFilter(DMColorFilterGenerator.adjustHue(f));
                canvas.drawBitmap(createBgImage, 0.0f, 0.0f, paint);
                paint.reset();
                if (!createBgImage.isRecycled()) {
                    createBgImage.recycle();
                }
            }
        } else {
            canvas.drawColor(-1);
        }
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        for (int i3 = 0; i3 < this.mComposeInfo.getCollageInfo().size(); i3++) {
            Rect layoutPosition = getLayoutPosition(i, this.mComposeInfo.getCollageInfo().get(i3).GetRect(f2));
            Bitmap dispalyImage = this.m_vws[i3].getDispalyImage(layoutPosition.right - layoutPosition.left, layoutPosition.bottom - layoutPosition.top);
            if (dispalyImage != null) {
                canvas.drawBitmap(dispalyImage, (Rect) null, layoutPosition, paint);
                dispalyImage.recycle();
            }
        }
        paint.setColorFilter(DMColorFilterGenerator.adjustHue(f));
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable != null) {
            drawable.setBounds(0, 0, getWidth(), getHeight());
            setMyViewBackgroud(this.mBackgroundDrawable);
        }
        paint.reset();
        return createBitmap;
    }

    public void restCollageViewAndClearBitmap() {
        for (int i = 0; i < this.m_vwCount; i++) {
            LibDMMaskImageViewTouch[] libDMMaskImageViewTouchArr = this.m_vws;
            if (libDMMaskImageViewTouchArr[i] != null) {
                libDMMaskImageViewTouchArr[i].clear();
            }
        }
        List<Bitmap> list = this.bitmaps;
        if (list != null) {
            for (Bitmap bitmap : list) {
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
            this.bitmaps.clear();
        }
        ImageView imageView = this.img_bg;
        if (imageView != null) {
            imageView.setImageBitmap(null);
        }
        Bitmap bitmap2 = this.backgroundBitmap;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.backgroundBitmap.recycle();
        }
        this.backgroundBitmap = null;
        Bitmap bitmap3 = this.mProcessedBitmap;
        if (bitmap3 != null) {
            if (!bitmap3.isRecycled()) {
                this.mProcessedBitmap.recycle();
            }
            this.mProcessedBitmap = null;
        }
        if (this.mBackgroundDrawable instanceof BitmapDrawable) {
            if (Build.VERSION.SDK_INT < 16) {
                this.img_bg.setBackgroundDrawable(null);
            } else {
                setBackground16(this.img_bg, null);
            }
            recycleDrawable(this.mBackgroundDrawable);
        }
        Bitmap bitmap4 = this.picBitmap;
        if (bitmap4 != null) {
            if (!bitmap4.isRecycled()) {
                this.picBitmap.recycle();
            }
            this.picBitmap = null;
        }
        Bitmap bitmap5 = this.m_vOriginalBitmap;
        if (bitmap5 != null) {
            if (!bitmap5.isRecycled()) {
                this.m_vOriginalBitmap.recycle();
            }
            this.m_vOriginalBitmap = null;
        }
        noBg();
    }

    public static Bitmap loadBitmapFromView(View view, int i, int i2) {
        Log.v("lb", "LayoutParams width = " + view.getLayoutParams().width + " height = " + view.getLayoutParams().height);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        view.layout(0, 0, i, i2);
        view.draw(canvas);
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

    private void layoutCompose(int i, int i2) {
        LibDMTemplateRes libDMTemplateRes = this.mComposeInfo;
        if (libDMTemplateRes == null) {
            return;
        }
        float f = i;
        float f2 = i2;
        float f3 = f / f2;
        float f4 = f2 / 3060.0f;
        float f5 = f / (f3 * 3060.0f);
        List<LibDMCollageInfo> collageInfo = libDMTemplateRes.getCollageInfo();
        for (int i3 = 0; i3 < collageInfo.size(); i3++) {
            if (collageInfo.size() >= 1) {
                LibDMCollageInfo libDMCollageInfo = this.mComposeInfo.getCollageInfo().get(i3);
                libDMCollageInfo.setInnerFrameWidth(this.minnerWidth);
                libDMCollageInfo.setOutFrameWidth(this.mouterWidth);
                Rect GetRect = libDMCollageInfo.GetRect(f3);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) (((GetRect.right - GetRect.left) * 1.0f * f4) + 0.5f), (int) (((GetRect.bottom - GetRect.top) * 1.0f * f5) + 0.5f));
                layoutParams.setMargins((int) ((GetRect.left * 1.0f * f4) + 0.5f), (int) ((GetRect.top * 1.0f * f5) + 0.5f), 0, 0);
                layoutParams.gravity = 3;
                Path path = libDMCollageInfo.getPath(f4, f5, GetRect.left, GetRect.top, f3);
                if (libDMCollageInfo.getMaskUri() != null) {
                    this.m_vws[i3].setMask(libDMCollageInfo.getMaskBitmap(getContext()));
                } else {
                    this.m_vws[i3].setMask(null);
                }
                this.m_vws[i3].setIsCanCorner(libDMCollageInfo.getIsCanCorner());
                this.m_vws[i3].setIsShowFrame(libDMCollageInfo.getIsShowFrame());
                this.m_vws[i3].setLayoutParams(layoutParams);
                this.m_vws[i3].setPath(path);
                this.m_vws[i3].resetDisplayMatrix();
                this.m_vws[i3].setRadius((int) this.radius);
                this.m_vws[i3].setFitToScreen(true);
                this.m_vws[i3].setVisibility(View.VISIBLE);
                this.m_vws[i3].setCollageInfo(libDMCollageInfo);
                this.m_vws[i3].invalidate();
            } else {
                this.m_vws[i3].setVisibility(View.INVISIBLE);
            }
        }
    }

    public void Changelayout(int i, int i2, float f, int i3, int i4) {
        float f2 = i;
        float f3 = i2;
        float f4 = f2 / f3;
        float f5 = f3 / 3060.0f;
        float f6 = f2 / (f4 * 3060.0f);
        for (int i5 = 0; i5 < this.mComposeInfo.getCollageInfo().size(); i5++) {
            if (this.mComposeInfo.getCollageInfo().size() >= 1) {
                LibDMCollageInfo libDMCollageInfo = this.mComposeInfo.getCollageInfo().get(i5);
                if (i3 != -1) {
                    libDMCollageInfo.setInnerFrameWidth(i3);
                    this.minnerWidth = i3;
                }
                if (i4 != -1) {
                    libDMCollageInfo.setOutFrameWidth(i4);
                    this.mouterWidth = i4;
                }
                Rect GetRect = this.mComposeInfo.getCollageInfo().get(i5).GetRect(f4);
                Path path = this.mComposeInfo.getCollageInfo().get(i5).getPath(f5, f6, GetRect.left, GetRect.top, f4);
                int i6 = (int) ((GetRect.left * f5 * 1.0f) + 0.5f);
                int i7 = (int) ((GetRect.top * f6 * 1.0f) + 0.5f);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(((int) (((GetRect.right * f5) * 1.0f) + 0.5f)) - i6, ((int) ((1.0f * (GetRect.bottom * f6)) + 0.5f)) - i7);
                layoutParams.setMargins(i6, i7, 0, 0);
                layoutParams.gravity = 3;
                this.m_vws[i5].setLayoutParams(layoutParams);
                this.m_vws[i5].setPath(path);
                this.m_vws[i5].setRadius((int) getRadius());
                this.m_vws[i5].invalidate();
                this.m_vws[i5].setVisibility(View.VISIBLE);
            } else {
                this.m_vws[i5].setVisibility(View.INVISIBLE);
            }
        }
    }

    public void Changelayout(int i, int i2, int i3) {
        Changelayout(this.mHeight, this.mWidth, i, i2, i3);
    }

    public void setBackgroundImageBitmap(Bitmap bitmap) {
        if (this.backgroundBitmap != null) {
            this.img_bg.setImageBitmap(null);
            DMBitmapUtil.ourBitmapRecycle(this.backgroundBitmap, false);
            this.backgroundBitmap = null;
        }
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable instanceof BitmapDrawable) {
            recycleDrawable(drawable);
        }
        if (bitmap != null) {
            this.bgIsBitmap = true;
            this.backgroundBitmap = bitmap;
            if (bitmap != null) {
                this.img_bg.setImageBitmap(createXORImage(bitmap, createTileBgImage(1024)));
                return;
            }
            return;
        }
        this.bgIsBitmap = false;
        setBackgroundColor(-1);
    }

    public Bitmap createXORImage(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setColor(Color.argb(240, 255, 255, 255));
        paint.setDither(false);
        Canvas canvas = new Canvas(createBitmap);
        paint.setAntiAlias(true);
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return createBitmap;
    }

    public void setOriginalView() {
        LibDMMaskImageViewTouch libDMMaskImageViewTouch = this.m_vSel;
        this.m_vSelOri = libDMMaskImageViewTouch;
        setOriginalBitmap(libDMMaskImageViewTouch);
        this.imgExchanger = true;
    }

    public Bitmap getSelBitmap() {
        if (this.m_vSel == null) {
            this.m_vSel = this.m_vws[0];
        }
        return getViewBitmap(this.m_vSel);
    }

    protected Bitmap getViewBitmap(View view) {
        if (this.m_vws == null || this.bitmaps == null) {
            return null;
        }
        for (int i = 0; i < this.m_vwCount; i++) {
            if (view == this.m_vws[i] && this.bitmaps.size() > i) {
                return this.bitmaps.get(i);
            }
        }
        return null;
    }

    public void setSelViewBitmap(Bitmap bitmap, String str) {
        if (this.m_vSel == null) {
            this.m_vSel = this.m_vws[0];
        }
        if (this.m_vSel != null) {
            if (bitmap == null) {
                str = "";
            }
            setViewBitmap(this.m_vSel, bitmap, str);
            changeCornerRadius((int) this.radius);
        }
    }

    private void setOriginalBitmap(View view) {
        if (this.m_vws == null || this.bitmaps == null) {
            return;
        }
        for (int i = 0; i < this.m_vwCount; i++) {
            if (view == this.m_vws[i]) {
                this.m_vOriginalBitmap = this.bitmaps.get(i);
                return;
            }
        }
    }

    public void ExchangeImage(View view) {
        LibDMMaskImageViewTouch libDMMaskImageViewTouch;
        if (this.imgExchanger.booleanValue() && view != (libDMMaskImageViewTouch = this.m_vSelOri)) {
            LibDMMaskImageViewTouch libDMMaskImageViewTouch2 = (LibDMMaskImageViewTouch) view;
            Uri uri = libDMMaskImageViewTouch2.getUri();
            libDMMaskImageViewTouch2.setUri(libDMMaskImageViewTouch.getUri());
            libDMMaskImageViewTouch.setUri(uri);
            LibDMBitmapInfo bitmapInfo = libDMMaskImageViewTouch2.getBitmapInfo();
            libDMMaskImageViewTouch2.setBitmapInfo(libDMMaskImageViewTouch.getBitmapInfo());
            libDMMaskImageViewTouch.setBitmapInfo(bitmapInfo);
            this.m_vOriginalBitmap = getViewBitmap(this.m_vSelOri);
            Bitmap viewBitmap = getViewBitmap(view);
            Bitmap bitmap = this.m_vOriginalBitmap;
            if (bitmap != null) {
                libDMMaskImageViewTouch2.setImageBitmap(bitmap, true, null, this.mMaxZoom);
            }
            setExchangeViewBitmap(view);
            libDMMaskImageViewTouch2.setlongclickEnable(false);
            if (viewBitmap != null) {
                libDMMaskImageViewTouch.setImageBitmap(viewBitmap, true, null, this.mMaxZoom);
            }
            this.m_vOriginalBitmap = viewBitmap;
            setExchangeViewBitmap(this.m_vSelOri);
            libDMMaskImageViewTouch2.setDrowRectangle(true);
        }
    }

    private void setExchangeViewBitmap(View view) {
        for (int i = 0; i < this.m_vwCount; i++) {
            if (view == this.m_vws[i]) {
                this.bitmaps.set(i, this.m_vOriginalBitmap);
                return;
            }
        }
    }

    public int getFrameWidth() {
        return this.mComposeInfo.getFrameWidth();
    }

    public void setBackground(int i, DMWBRes dMWBRes) {
        if (dMWBRes != null) {
            if (i == 2) {
                setBackgroundColor(((DMWBColorRes) dMWBRes).getColorValue());
                return;
            }
            DMWBImageRes dMWBImageRes = (DMWBImageRes) dMWBRes;
            if (dMWBImageRes.getFitType() == DMWBImageRes.FitType.TITLE) {
                setBackgroundImageBitmap(dMWBImageRes.getLocalImageBitmap(), true);
            } else {
                setBackgroundImageBitmap(dMWBImageRes.getLocalImageBitmap(), false);
            }
        }
    }

    private LibDMMaskImageViewTouch creatMaskView() {
        LibDMMaskImageViewTouch libDMMaskImageViewTouch = new LibDMMaskImageViewTouch(this.mContext);
        libDMMaskImageViewTouch.setFitToScreen(true);
        libDMMaskImageViewTouch.setVisibility(View.INVISIBLE);
        return libDMMaskImageViewTouch;
    }

    public void doRotation(float f) {
        if (this.m_vSel == null) {
            this.m_vSel = this.m_vws[0];
        }
        Bitmap viewBitmap = getViewBitmap(this.m_vSel);
        if (viewBitmap == null) {
            return;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(f, getWidth() / 2, getHeight() / 2);
        setViewBitmap(Bitmap.createBitmap(viewBitmap, 0, 0, viewBitmap.getWidth(), viewBitmap.getHeight(), matrix, true), "");
        if (viewBitmap != null && !viewBitmap.isRecycled()) {
            viewBitmap.recycle();
        }
        this.m_vSel.getBitmapInfo().addRotationDegree((int) f);
        this.mResourceBmp = getSelBitmap();
    }

    public void doReversal(float f) {
        if (this.m_vSel == null) {
            this.m_vSel = this.m_vws[0];
        }
        Bitmap viewBitmap = getViewBitmap(this.m_vSel);
        if (viewBitmap == null) {
            return;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(1.0f, -1.0f, getWidth() / 2, getHeight() / 2);
        matrix.postRotate(f, getWidth() / 2, getHeight() / 2);
        Bitmap createBitmap = Bitmap.createBitmap(viewBitmap, 0, 0, viewBitmap.getWidth(), viewBitmap.getHeight(), matrix, true);
        if (viewBitmap != null && !viewBitmap.isRecycled()) {
            viewBitmap.recycle();
        }
        setViewBitmap(createBitmap, "");
        this.m_vSel.getBitmapInfo().changeIsHorizontalMirror();
        this.mResourceBmp = getSelBitmap();
    }

    public void setBackgroundBitmapDrawable(Drawable drawable, Bitmap bitmap) {
        Drawable drawable2 = this.mBackgroundDrawable;
        if ((drawable2 instanceof BitmapDrawable) && drawable2 != drawable) {
            recycleDrawable(drawable2);
        }
        this.img_bg.setImageBitmap(null);
        if (this.backgroundBitmap != null) {
            this.img_bg.setImageBitmap(null);
            DMBitmapUtil.ourBitmapRecycle(this.backgroundBitmap, false);
            this.backgroundBitmap = null;
        }
        this.mBackgroundDrawable = drawable;
        if (Build.VERSION.SDK_INT < 16) {
            this.img_bg.setBackgroundDrawable(drawable);
        } else {
            setBackground16(this.img_bg, drawable);
        }
    }

    private void setBackground16(ImageView imageView, Drawable drawable) {
        imageView.setBackground(drawable);
    }

    public void setShadow(boolean z, int i) {
        LibDMTemplateRes libDMTemplateRes = this.mComposeInfo;
        if (libDMTemplateRes == null || libDMTemplateRes.getCollageInfo() == null) {
            return;
        }
        for (int i2 = 0; i2 < this.mComposeInfo.getCollageInfo().size(); i2++) {
            if (this.mComposeInfo.getCollageInfo().size() >= 1) {
                if (!this.mComposeInfo.getCollageInfo().get(i2).getIsCanShadow()) {
                    this.m_vws[i2].setIsUsingShadow(false);
                } else {
                    this.m_vws[i2].setIsUsingShadow(z);
                    if (z) {
                        this.m_vws[i2].setShadowColor(i);
                    }
                }
                this.m_vws[i2].invalidate();
                this.m_vws[i2].setVisibility(View.VISIBLE);
            } else {
                this.m_vws[i2].setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setShadow(boolean z) {
        LibDMTemplateRes libDMTemplateRes = this.mComposeInfo;
        if (libDMTemplateRes == null || libDMTemplateRes.getCollageInfo() == null) {
            return;
        }
        for (int i = 0; i < this.mComposeInfo.getCollageInfo().size(); i++) {
            if (this.mComposeInfo.getCollageInfo().size() >= 1) {
                if (!this.mComposeInfo.getCollageInfo().get(i).getIsCanShadow()) {
                    this.m_vws[i].setIsUsingShadow(false);
                } else {
                    this.m_vws[i].setIsUsingShadow(z);
                }
                this.m_vws[i].invalidate();
                this.m_vws[i].setVisibility(View.VISIBLE);
            } else {
                this.m_vws[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setRotationDegree(int i) {
        LibDMTemplateRes libDMTemplateRes = this.mComposeInfo;
        if (libDMTemplateRes == null || libDMTemplateRes.getCollageInfo() == null) {
            return;
        }
        for (int i2 = 0; i2 < this.mComposeInfo.getCollageInfo().size(); i2++) {
            if (this.mComposeInfo.getCollageInfo().size() >= 1) {
                this.mRotaiton = i;
                this.m_vws[i2].setRotationDegree(i);
                this.m_vws[i2].invalidate();
                this.m_vws[i2].setVisibility(View.VISIBLE);
            } else {
                this.m_vws[i2].setVisibility(View.INVISIBLE);
            }
        }
    }

    public int getRotaitonDegree() {
        return this.mRotaiton;
    }

    public void setShadowValue(int i) {
        for (int i2 = 0; i2 < this.mComposeInfo.getCollageInfo().size(); i2++) {
            if (this.mComposeInfo.getCollageInfo().size() >= 1) {
                this.mShadowValue = i;
                this.m_vws[i2].setChangePadding(i);
                this.m_vws[i2].invalidate();
                this.m_vws[i2].setVisibility(View.VISIBLE);
            } else {
                this.m_vws[i2].setVisibility(View.INVISIBLE);
            }
        }
    }

    public int getShadowValue() {
        return this.mShadowValue;
    }

    public LibDMMaskImageViewTouch[] getViewTouchs() {
        return this.m_vws;
    }

    public void handleBackgroundHue(float f) {
        ImageView imageView = this.img_bg;
        if (imageView != null && this.mBackgroundDrawable != null) {
            imageView.getBackground().setColorFilter(DMColorFilterGenerator.adjustHue(f));
            return;
        }
        ImageView imageView2 = this.img_bg;
        if (imageView2 == null || this.backgroundBitmap == null) {
            return;
        }
        imageView2.setColorFilter(DMColorFilterGenerator.adjustHue(f));
    }
}

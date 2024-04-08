package com.winflag.libcollage.view;

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
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import com.blankj.utilcode.util.ImageUtils;
import com.sky.testproject.Opcodes;
import com.sky.testproject.R;
import com.winflag.Utils.Data_Change;
import com.winflag.libcollage.filter.LibCollageFilterBarView;
import com.winflag.libcollage.frame.FramesViewProcess;
import com.winflag.libcollage.frame.res.FrameBorderRes;
import com.winflag.libcollage.resource.TemplateRes;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.photoart.lib.SysSnap.BMDragSnapView;
import com.picspool.instafilter.GPUFilter;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.bitmap.DMBitmapCrop;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.collagelib.DMColorFilterGenerator;
import com.picspool.lib.collagelib.LibDMMaskImageViewTouch;
import com.picspool.lib.collagelib.resource.collage.LibDMCollageInfo;
import com.picspool.lib.filter.cpu.normal.FastBlurFilter;
import com.picspool.lib.filter.listener.OnFilterFinishedListener;
import com.picspool.lib.filter.listener.OnPostFilteredListener;
import com.picspool.lib.resource.DMWBColorRes;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sticker.core.DMSticker;
import com.picspool.lib.sticker.drawonview.DMStickerCanvasView;
import com.picspool.lib.sticker.util.DMStickerStateCallback;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.view.image.DMIgnoreRecycleImageView;

/* loaded from: classes.dex */
public class TemplateView extends RelativeLayout implements AdapterView.OnItemClickListener, DMStickerStateCallback {
    private static boolean longPressFlag = false;
    int KMaxPix;
    private String TAG;
    private Bitmap backgroundBitmap;
    public int backgroundColor;
    private Drawable backgroundDrawable;
    public boolean bgIsBitmap;
    private List<Bitmap> bitmaps;
    public List<Bitmap> bmps;
    ImageView borderView;
    private int curDownIndex;
    private DMWBRes curFilterRes;
    ImageView curMoveImageView;
    private GPUFilterRes currentFilterRes;
    private View customView;
    BMDragSnapView dragSnapView;
    private View dragView;
    public onFilterClickListener filterListener;
    private LibCollageFilterBarView filter_bottom_bar;
    private ImageView filter_img;
    int finalRadius;
    Bitmap foregroundBitmap;
    int imageWidth;
    public int imagecount;
    public Boolean imgExchanger;
    public DMIgnoreRecycleImageView img_bg;
    ImageView img_fg;
    FrameLayout imgvwlayout;
    private boolean isLongMode;
    private boolean isOnceTouch;
    public OnViewItemClickListener listener;
    List<ImageView> lstTouchImageViews;
    List<LibDMMaskImageViewTouch> lstmsivt;
    private Drawable mBackgroundDrawable;
    public TemplateRes mComposeInfo;
    Context mContext;
    DMSticker mCurrentFace;
    private boolean mDrogOnTouchDown;
    private FramesViewProcess mFrameView;
    int mHeight;
    private float mHueValue;
    DMWBImageRes mImageRes;
    private boolean mIsBlurBackground;
    public OnItemLongClickListener mItemLonglistener;
    public OnItemTapupClickListener mItemTapuplistener;
    public OnItemClickListener mItemlistener;
    private float mMaxZoom;
    private float mMinZoom;
    public onPhotoEditorClickListener mPhotoEditorClickListener;
    Bitmap mProcessedBitmap;
    private Bitmap mResourceBmp;
    int mRotaiton;
    int mShadowValue;
    private HashMap<Bitmap, Bitmap> mSrc2FilterMap;
    int mWidth;
    public Bitmap m_vOriginalBitmap;
    public String m_vOriginalfilename;
    LibDMMaskImageViewTouch m_vSel;
    LibDMMaskImageViewTouch m_vSelOri;
    public String[] m_vfilenames;
    public int m_vwCount;
    private LibDMMaskImageViewTouch[] m_vws;
    int maxAreaValue;
    private int mhight;
    int minAreaValue;
    private int mindex;
    int minnerWidth;
    private ImageView mirror_img;
    int mouterWidth;
    private int mwidth;
    private Handler myHandler;
    Bitmap picBitmap;
    private PopupWindow popupwindow;
    private float radius;
    private ImageView rotate_img;
    DMStickerCanvasView sfcView_faces;
    private List<Bitmap> stickerBitmapList;
    private List<DMStickerStateCallback> stickerStateCallSpreaders;
    private FrameBorderRes tBorderRes;
    FrameLayout touchimglayout;
    int viewWidth;

    /* loaded from: classes.dex */
    public interface OnItemClickListener {
        void ItemChange(View view, View view2);

        void ItemClick(View view, String str);

        void ItemSizeChange();
    }

    /* loaded from: classes.dex */
    public interface OnItemLongClickListener {
        void ItemLongClick(View view, int i, String str);

        void ItemLongMove();
    }

    /* loaded from: classes.dex */
    public interface OnItemTapupClickListener {
        void ItemTapupClick(int i);
    }

    /* loaded from: classes.dex */
    public interface OnViewItemClickListener {
        void onCollageViewItemClick(View view, int i, int i2, int i3, int i4, int i5);
    }

    /* loaded from: classes.dex */
    public interface onFilterClickListener {
        void addFilterBar(RelativeLayout relativeLayout);

        void removeFilterBar(RelativeLayout relativeLayout);
    }

    /* loaded from: classes.dex */
    public interface onOutputImageListener {
        void onOutputImageFinish(Bitmap bitmap);
    }

    /* loaded from: classes.dex */
    public interface onPhotoEditorClickListener {
        void addEditorBar(RelativeLayout relativeLayout);

        void removeEditorBar(RelativeLayout relativeLayout);
    }

    @Override // org.picspool.lib.sticker.util.DMStickerStateCallback
    public void onStickerChanged() {
    }

    public TemplateView(Context context) {
        super(context);
        this.KMaxPix = 612;
        this.TAG = "ImageCollageView";
        this.bitmaps = null;
        this.radius = 0.0f;
        this.mHeight = 720;
        this.mWidth = 720;
        this.imagecount = 1;
        this.imgExchanger = false;
        this.m_vwCount = 22;
        this.minnerWidth = 0;
        this.mouterWidth = 0;
        this.mRotaiton = 0;
        this.backgroundColor = -1;
        this.bgIsBitmap = false;
        this.lstmsivt = new ArrayList();
        this.mMaxZoom = 2.5f;
        this.mMinZoom = 0.3f;
        this.viewWidth = 720;
        this.mSrc2FilterMap = new HashMap<>();
        this.stickerBitmapList = new ArrayList();
        this.mHueValue = 0.0f;
        this.lstTouchImageViews = new ArrayList();
        this.mIsBlurBackground = false;
        this.myHandler = new Handler() { // from class: com.winflag.libcollage.view.TemplateView.4
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                TemplateView templateView = TemplateView.this;
                templateView.layoutCompose(templateView.mhight, TemplateView.this.mwidth);
                super.handleMessage(message);
            }
        };
        this.mShadowValue = 10;
        this.imageWidth = DMScreenInfoUtil.dip2px(getContext(), 20.0f);
        this.mDrogOnTouchDown = false;
        this.minAreaValue = -3060;
        this.maxAreaValue = 3060;
        this.isLongMode = false;
        this.curDownIndex = -1;
        this.mContext = context;
        initView();
    }

    public TemplateView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.KMaxPix = 612;
        this.TAG = "ImageCollageView";
        this.bitmaps = null;
        this.radius = 0.0f;
        this.mHeight = 720;
        this.mWidth = 720;
        this.imagecount = 1;
        this.imgExchanger = false;
        this.m_vwCount = 22;
        this.minnerWidth = 0;
        this.mouterWidth = 0;
        this.mRotaiton = 0;
        this.backgroundColor = -1;
        this.bgIsBitmap = false;
        this.lstmsivt = new ArrayList();
        this.mMaxZoom = 2.5f;
        this.mMinZoom = 0.3f;
        this.viewWidth = 720;
        this.mSrc2FilterMap = new HashMap<>();
        this.stickerBitmapList = new ArrayList();
        this.mHueValue = 0.0f;
        this.lstTouchImageViews = new ArrayList();
        this.mIsBlurBackground = false;
        this.myHandler = new Handler() { // from class: com.winflag.libcollage.view.TemplateView.4
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                TemplateView templateView = TemplateView.this;
                templateView.layoutCompose(templateView.mhight, TemplateView.this.mwidth);
                super.handleMessage(message);
            }
        };
        this.mShadowValue = 10;
        this.imageWidth = DMScreenInfoUtil.dip2px(getContext(), 20.0f);
        this.mDrogOnTouchDown = false;
        this.minAreaValue = -3060;
        this.maxAreaValue = 3060;
        this.isLongMode = false;
        this.curDownIndex = -1;
        this.mContext = context;
        initView();
    }

    public TemplateView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.KMaxPix = 612;
        this.TAG = "ImageCollageView";
        this.bitmaps = null;
        this.radius = 0.0f;
        this.mHeight = 720;
        this.mWidth = 720;
        this.imagecount = 1;
        this.imgExchanger = false;
        this.m_vwCount = 22;
        this.minnerWidth = 0;
        this.mouterWidth = 0;
        this.mRotaiton = 0;
        this.backgroundColor = -1;
        this.bgIsBitmap = false;
        this.lstmsivt = new ArrayList();
        this.mMaxZoom = 2.5f;
        this.mMinZoom = 0.3f;
        this.viewWidth = 720;
        this.mSrc2FilterMap = new HashMap<>();
        this.stickerBitmapList = new ArrayList();
        this.mHueValue = 0.0f;
        this.lstTouchImageViews = new ArrayList();
        this.mIsBlurBackground = false;
        this.myHandler = new Handler() { // from class: com.winflag.libcollage.view.TemplateView.4
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                TemplateView templateView = TemplateView.this;
                templateView.layoutCompose(templateView.mhight, TemplateView.this.mwidth);
                super.handleMessage(message);
            }
        };
        this.mShadowValue = 10;
        this.imageWidth = DMScreenInfoUtil.dip2px(getContext(), 20.0f);
        this.mDrogOnTouchDown = false;
        this.minAreaValue = -3060;
        this.maxAreaValue = 3060;
        this.isLongMode = false;
        this.curDownIndex = -1;
        this.mContext = context;
        initView();
    }

    public void setItemOnClickListener(OnViewItemClickListener onViewItemClickListener) {
        this.listener = onViewItemClickListener;
    }

    public void setFilterOnClickListener(onFilterClickListener onfilterclicklistener) {
        this.filterListener = onfilterclicklistener;
    }

    public void resetPopupWindow() {
        PopupWindow popupWindow = this.popupwindow;
        if (popupWindow == null || !popupWindow.isShowing()) {
            return;
        }
        this.popupwindow.dismiss();
        this.popupwindow = null;
    }

    private void initView() {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.collage_view_template, (ViewGroup) this, true);
        DMIgnoreRecycleImageView dMIgnoreRecycleImageView = (DMIgnoreRecycleImageView) findViewById(R.id.img_bg);
        this.img_bg = dMIgnoreRecycleImageView;
        dMIgnoreRecycleImageView.setBackgroundColor(this.backgroundColor);
        ColorDrawable colorDrawable = new ColorDrawable(-1);
        this.backgroundDrawable = colorDrawable;
        setSquareBackground(colorDrawable, false);
        this.m_vfilenames = new String[this.m_vwCount];
        this.imgvwlayout = (FrameLayout) findViewById(R.id.imgvwlayout);
        this.img_fg = (ImageView) findViewById(R.id.img_fg);
        this.lstmsivt.clear();
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.touchimglayout);
        this.touchimglayout = frameLayout;
        frameLayout.setOnTouchListener(new LayoutDragOnTouchListener());
        this.curMoveImageView = (ImageView) findViewById(R.id.move_img_view);
        this.m_vws = new LibDMMaskImageViewTouch[this.m_vwCount];
        for (int i = 0; i < this.m_vwCount; i++) {
            LibDMMaskImageViewTouch creatMaskView = creatMaskView();
            creatMaskView.setTag(Integer.valueOf(i));
            creatMaskView.setOnClickListener(new OnViewItemListener(i));
            LibDMMaskImageViewTouch[] libDMMaskImageViewTouchArr = this.m_vws;
            libDMMaskImageViewTouchArr[i] = creatMaskView;
            libDMMaskImageViewTouchArr[i].setIndex(i);
            creatMaskView.mClickListener = new LibDMMaskImageViewTouch.OnCustomeClickListener() { // from class: com.winflag.libcollage.view.TemplateView.1
                @Override // org.picspool.lib.collagelib.LibDMMaskImageViewTouch.OnCustomeClickListener
                public void CustomeClick(int i2) {
                    TemplateView templateView = TemplateView.this;
                    templateView.m_vSel = templateView.m_vws[i2];
                    Boolean drowRectangle = TemplateView.this.m_vSel.getDrowRectangle();
                    for (int i3 = 0; i3 < TemplateView.this.m_vwCount; i3++) {
                        TemplateView.this.m_vws[i3].setDrowRectangle(false);
                        TemplateView.this.m_vws[i3].setDrawLineMode(-1);
                    }
                    if (TemplateView.this.imgExchanger.booleanValue() && !TemplateView.this.isLongMode) {
                        TemplateView templateView2 = TemplateView.this;
                        templateView2.ExchangeImage(templateView2.m_vSel);
                    }
                    if (TemplateView.this.mPhotoEditorClickListener != null) {
                        TemplateView.this.mPhotoEditorClickListener.removeEditorBar(null);
                    }
                    if (!drowRectangle.booleanValue()) {
                        TemplateView.this.m_vSel.setDrowRectangle(true);
                        TemplateView.this.setTouchImage();
                    } else {
                        TemplateView.this.mItemTapuplistener.ItemTapupClick(0);
                        TemplateView.this.clearTouchImage();
                    }
                    TemplateView templateView3 = TemplateView.this;
                    templateView3.mResourceBmp = templateView3.getSelBitmap();
                    if (TemplateView.this.mItemlistener != null) {
                        TemplateView.this.mItemlistener.ItemClick(TemplateView.this.m_vSel, TemplateView.this.m_vfilenames[i2]);
                    }
                }

                @Override // org.picspool.lib.collagelib.LibDMMaskImageViewTouch.OnCustomeClickListener
                public void CustomeTouchUp(int i2) {
                    if (TemplateView.this.m_vSel == null) {
                        return;
                    }
                    Boolean drowRectangle = TemplateView.this.m_vSel.getDrowRectangle();
                    TemplateView.this.isLongMode = false;
                    if (drowRectangle.booleanValue() && !TemplateView.this.imgExchanger.booleanValue()) {
                        if (TemplateView.this.filter_bottom_bar != null) {
                            TemplateView.this.filter_bottom_bar.dispose();
                            TemplateView.this.filterListener.removeFilterBar(TemplateView.this.filter_bottom_bar);
                            TemplateView.this.filter_bottom_bar = null;
                        }
                        TemplateView.this.m_vSel.setDrowRectangle(true);
                        TemplateView.this.m_vSel.setDrawLineMode(-16711936);
                        if (TemplateView.this.mPhotoEditorClickListener != null) {
                            TemplateView.this.mPhotoEditorClickListener.addEditorBar(null);
                        }
                    } else if (TemplateView.this.mPhotoEditorClickListener != null) {
                        TemplateView.this.mPhotoEditorClickListener.removeEditorBar(null);
                    }
                    if (TemplateView.this.mItemTapuplistener != null) {
                        if (!TemplateView.longPressFlag) {
                            Log.e("if----->longPressFlag", TemplateView.longPressFlag ? "1" : "0");
                            TemplateView.this.mItemTapuplistener.ItemTapupClick(1);
                            return;
                        }
                        TemplateView.this.m_vSel = null;
                        boolean unused = TemplateView.longPressFlag = false;
                        for (int i3 = 0; i3 < TemplateView.this.m_vwCount; i3++) {
                            TemplateView.this.m_vws[i3].setDrowRectangle(false);
                        }
                    }
                }
            };
            creatMaskView.setCustomeLongClickListener(new LibDMMaskImageViewTouch.OnCustomeLongClickListener() { // from class: com.winflag.libcollage.view.TemplateView.2
                @Override // org.picspool.lib.collagelib.LibDMMaskImageViewTouch.OnCustomeLongClickListener
                public void CustomeLongClick(int i2) {
                    boolean unused = TemplateView.longPressFlag = true;
                    if (TemplateView.this.popupwindow != null && TemplateView.this.popupwindow.isShowing()) {
                        TemplateView.this.popupwindow.dismiss();
                    }
                    TemplateView.this.isLongMode = true;
                    TemplateView templateView = TemplateView.this;
                    templateView.m_vSel = templateView.m_vws[i2];
                    TemplateView.this.m_vSel.setDrowRectangle(true);
                    TemplateView.this.m_vSel.setDrawLineMode(-65536);
                    TemplateView.this.setOriginalView();
                    TemplateView.this.clearTouchImage();
                    if (TemplateView.this.mItemLonglistener != null) {
                        TemplateView.this.mItemLonglistener.ItemLongClick(TemplateView.this.m_vws[i2], 2, TemplateView.this.m_vfilenames[i2]);
                    }
                }
            });
            this.imgvwlayout.addView(creatMaskView, i);
        }
        this.mFrameView = (FramesViewProcess) findViewById(R.id.frame_fv);
        DMStickerCanvasView dMStickerCanvasView = (DMStickerCanvasView) findViewById(R.id.img_facial);
        this.sfcView_faces = dMStickerCanvasView;
        dMStickerCanvasView.startRender();
        this.sfcView_faces.onShow();
        this.sfcView_faces.setStickerCallBack(this);
        this.stickerStateCallSpreaders = new ArrayList();
        this.dragSnapView = (BMDragSnapView) findViewById(R.id.drag_snap_view);
    }

    public BMDragSnapView getDragSnapView() {
        return this.dragSnapView;
    }

    /* loaded from: classes.dex */
    protected class RotateImgOnClickListener implements OnClickListener {
        protected RotateImgOnClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            TemplateView.this.doRotation(90.0f);
        }
    }

    public void clearViewDrawRectangle() {
        LibDMMaskImageViewTouch libDMMaskImageViewTouch = this.m_vSel;
        if (libDMMaskImageViewTouch != null) {
            libDMMaskImageViewTouch.setDrowRectangle(false);
            this.m_vSel.setDrawLineMode(-1);
            clearTouchImage();
        }
    }

    public void setSquareBackground(Drawable drawable, boolean z) {
        recycleBackgroud();
        this.mIsBlurBackground = z;
        this.backgroundDrawable = drawable;
        setBackgroud(drawable);
    }

    public void recycleBackgroud() {
        setBackgroud(null);
        recycleDrawable(this.backgroundDrawable);
        this.backgroundDrawable = null;
    }

    public void setBlurBackground(int i, int i2) {
        Bitmap blur;
        List<Bitmap> list = this.bmps;
        if (list == null || list.size() <= 0 || i2 < 0 || i2 >= this.bmps.size() || (blur = FastBlurFilter.blur(DMBitmapCrop.cropCenterScaleBitmap(this.bmps.get(i2), 400, 400), i, false)) == null || blur.isRecycled()) {
            return;
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(blur);
        this.backgroundDrawable = bitmapDrawable;
        setBackgroud(bitmapDrawable);
    }

    public void resetBackgroud() {
        ColorDrawable colorDrawable = new ColorDrawable(-1);
        if (Build.VERSION.SDK_INT < 16) {
            this.img_bg.setBackgroundDrawable(colorDrawable);
        } else {
            setBackground16(this.img_bg, colorDrawable);
        }
    }

    private void setBackgroud(Drawable drawable) {
        if (Build.VERSION.SDK_INT < 16) {
            this.img_bg.setBackgroundDrawable(drawable);
        } else {
            setBackground16(this.img_bg, drawable);
        }
    }

    /* loaded from: classes.dex */
    public class FilterImgOnClickListener implements OnClickListener {
        public FilterImgOnClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (TemplateView.this.popupwindow != null && TemplateView.this.popupwindow.isShowing()) {
                TemplateView.this.popupwindow.dismiss();
                TemplateView.this.popupwindow = null;
            }
            if (TemplateView.this.filterListener != null) {
                if (TemplateView.this.filter_bottom_bar != null) {
                    TemplateView.this.filter_bottom_bar.dispose();
                    TemplateView.this.filterListener.removeFilterBar(TemplateView.this.filter_bottom_bar);
                    TemplateView.this.filter_bottom_bar = null;
                    return;
                }
                TemplateView.this.filter_bottom_bar = new LibCollageFilterBarView(TemplateView.this.mContext, TemplateView.this.mResourceBmp);
                TemplateView.this.filterListener.addFilterBar(TemplateView.this.filter_bottom_bar);
                TemplateView.this.filter_bottom_bar.setOnFilterBarViewListener(new LibCollageFilterBarView.OnFilterBarViewListener() { // from class: com.winflag.libcollage.view.TemplateView.FilterImgOnClickListener.1
                    @Override // com.winflag.libcollage.filter.LibCollageFilterBarView.OnFilterBarViewListener
                    public void resourceFilterChanged(DMWBRes dMWBRes, String str, int i, int i2) {
                        if (dMWBRes != null) {
                            TemplateView.this.setFilter((GPUFilterRes) dMWBRes);
                        }
                    }

                    @Override // com.winflag.libcollage.filter.LibCollageFilterBarView.OnFilterBarViewListener
                    public void onFilterBarDisappear() {
                        TemplateView.this.m_vSel.setDrowRectangle(false);
                        if (TemplateView.this.filter_bottom_bar != null) {
                            TemplateView.this.filter_bottom_bar.dispose();
                            TemplateView.this.filterListener.removeFilterBar(TemplateView.this.filter_bottom_bar);
                            TemplateView.this.filter_bottom_bar = null;
                        }
                        if (TemplateView.this.popupwindow == null || !TemplateView.this.popupwindow.isShowing()) {
                            return;
                        }
                        TemplateView.this.popupwindow.dismiss();
                        TemplateView.this.popupwindow = null;
                    }
                });
            }
        }
    }

    public void setSingleFilter(DMWBRes dMWBRes, OnFilterFinishedListener onFilterFinishedListener) {
        if (dMWBRes == null || !(dMWBRes instanceof GPUFilterRes)) {
            return;
        }
        GPUFilterRes gPUFilterRes = (GPUFilterRes) dMWBRes;
        if (this.m_vSel == null) {
            return;
        }
        setPictureImageBitmapWithStatKeep(GPUFilter.filterForType(this.mContext, getSelBitmap(), gPUFilterRes.getFilterType()));
    }

    public void setPictureImageBitmapWithStatKeep(Bitmap bitmap) {
        if (this.m_vSel == null) {
            this.m_vSel = this.m_vws[0];
        }
        this.m_vSel.setImageBitmapWithStatKeep(null);
        this.m_vSel.setImageBitmap(bitmap, false);
    }

    public void setFilter(DMWBRes dMWBRes, OnFilterFinishedListener onFilterFinishedListener) {
        if (dMWBRes != null) {
            this.curFilterRes = dMWBRes;
            updateImagePic(onFilterFinishedListener);
        }
    }

    private void updateImagePic(final OnFilterFinishedListener onFilterFinishedListener) {
        List<Bitmap> list;
        LibDMMaskImageViewTouch[] libDMMaskImageViewTouchArr = this.m_vws;
        if (libDMMaskImageViewTouchArr == null || libDMMaskImageViewTouchArr.length <= 0 || this.curFilterRes == null || (list = this.bmps) == null || list.size() <= 0) {
            return;
        }
        AsyncTemplateProcess.executeAsyncFilter(this.mContext, this.m_vws, this.bmps, this.curFilterRes, new OnPostFilteredListener() { // from class: com.winflag.libcollage.view.TemplateView.3
            @Override // org.picspool.lib.filter.listener.OnPostFilteredListener
            public void postFiltered(Bitmap bitmap) {
                OnFilterFinishedListener onFilterFinishedListener2 = onFilterFinishedListener;
                if (onFilterFinishedListener2 != null) {
                    onFilterFinishedListener2.postFinished();
                }
            }
        });
    }

    public void setFilter(GPUFilterRes gPUFilterRes) {
        LibDMMaskImageViewTouch libDMMaskImageViewTouch;
        this.currentFilterRes = gPUFilterRes;
        if (gPUFilterRes == null || this.mResourceBmp == null || (libDMMaskImageViewTouch = this.m_vSel) == null) {
            return;
        }
        libDMMaskImageViewTouch.setImageBitmapWithStatKeep(null);
        if (this.mSrc2FilterMap.get(this.mResourceBmp) != null && !this.mSrc2FilterMap.get(this.mResourceBmp).isRecycled()) {
            this.mSrc2FilterMap.get(this.mResourceBmp).recycle();
        }
        this.mSrc2FilterMap.remove(this.mResourceBmp);
        Bitmap filterForType = GPUFilter.filterForType(this.mContext, this.mResourceBmp, this.currentFilterRes.getFilterType());
        this.mSrc2FilterMap.put(this.mResourceBmp, filterForType);
        setPictureImageBitmapNoReset(filterForType);
    }

    public void setViewBitmapWithKeepStat(int i, Bitmap bitmap) {
        LibDMMaskImageViewTouch libDMMaskImageViewTouch = this.m_vws[i];
        getViewBitmap(libDMMaskImageViewTouch);
        libDMMaskImageViewTouch.setImageBitmapWithStatKeep(null);
        libDMMaskImageViewTouch.setImageBitmapWithStatKeep(bitmap);
    }

    public void setPictureImageBitmapNoReset(Bitmap bitmap) {
        this.m_vSel.setImageBitmapWithStatKeep(null);
        this.m_vSel.setImageBitmap(bitmap, false);
        this.m_vSel.invalidate();
    }

    /* loaded from: classes.dex */
    protected class MirrorImgOnClickListener implements OnClickListener {
        protected MirrorImgOnClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            TemplateView.this.doReversal(180.0f);
        }
    }

    public void clearDrowRectangle() {
        for (int i = 0; i < this.m_vwCount; i++) {
            this.m_vws[i].setDrowRectangle(false);
        }
    }

    public void setBackgroundBlueImageBitmap(Bitmap bitmap, boolean z) {
        this.backgroundColor = -1;
        if (this.mBackgroundDrawable != null) {
            this.img_bg.setImageDrawable(null);
            this.mBackgroundDrawable = null;
        }
        this.backgroundBitmap = null;
        if (0 != 0) {
            this.img_bg.setImageBitmap(null);
            DMBitmapUtil.ourBitmapRecycle(this.backgroundBitmap, false);
            this.backgroundBitmap = null;
        }
        if (bitmap != null && !bitmap.isRecycled()) {
            this.bgIsBitmap = true;
            Bitmap blur = FastBlurFilter.blur(bitmap, 15, false);
            this.backgroundBitmap = blur;
            if (z) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(this.backgroundBitmap);
                bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
                bitmapDrawable.setDither(true);
                bitmapDrawable.setBounds(0, 0, 1280, 1280);
                this.img_bg.setImageDrawable(bitmapDrawable);
                return;
            }
            this.img_bg.setImageBitmap(blur);
            return;
        }
        this.bgIsBitmap = false;
        this.img_bg.setImageBitmap(null);
    }

    public void setBackgroundImageBitmap(Bitmap bitmap, boolean z) {
        this.backgroundColor = -1;
        if (this.mBackgroundDrawable != null) {
            this.img_bg.setImageDrawable(null);
            this.mBackgroundDrawable = null;
        }
        this.backgroundBitmap = null;
        if (0 != 0) {
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

    public void addFrame(FrameBorderRes frameBorderRes) {
        if (frameBorderRes == null || frameBorderRes.getName().compareTo("ori") == 0) {
            this.mFrameView.width = 0;
            this.mFrameView.changeRes(null);
        } else {
            this.imgvwlayout.invalidate();
            this.mFrameView.width = getWidth();
            this.mFrameView.height = getHeight();
            this.mFrameView.changeRes(frameBorderRes);
        }
        this.tBorderRes = this.mFrameView.getCurrentRes();
        this.mFrameView.invalidate();
    }

    public void addFrame(FrameBorderRes frameBorderRes, int i, int i2) {
        if (frameBorderRes == null || frameBorderRes.getName().compareTo("ori") == 0) {
            this.mFrameView.width = 0;
            this.mFrameView.changeRes(null);
        } else {
            this.imgvwlayout.invalidate();
            this.mFrameView.width = getWidth();
            this.mFrameView.height = getHeight();
            this.mFrameView.changeRes(frameBorderRes);
        }
        this.tBorderRes = this.mFrameView.getCurrentRes();
        this.mFrameView.invalidate();
    }

    private Bitmap createBgImage(int i) {
        int i2 = (int) ((i * (this.mHeight / this.mWidth)) + 0.5f);
        if (i2 == 0) {
            i2 = i;
        }
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
        DMWBImageRes dMWBImageRes = this.mImageRes;
        if (dMWBImageRes == null || dMWBImageRes.getFitType() != DMWBImageRes.FitType.SCALE) {
            for (int i5 = 0; i5 < i3; i5++) {
                float f = i5 * width;
                canvas.drawBitmap(this.backgroundBitmap, f, 0.0f, (Paint) null);
                for (int i6 = 1; i6 < i4; i6++) {
                    canvas.drawBitmap(this.backgroundBitmap, f, i6 * height, (Paint) null);
                }
            }
        } else {
            canvas.drawBitmap(this.backgroundBitmap, (Rect) null, new Rect(0, 0, i, i2), (Paint) null);
        }
        return createBitmap;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class OnViewItemListener implements OnClickListener {
        int index;

        public OnViewItemListener(int i) {
            TemplateView.this.mindex = i;
            this.index = i;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
            if (layoutParams == null || TemplateView.this.listener == null) {
                return;
            }
            int i = layoutParams.height;
            int i2 = layoutParams.width;
            TemplateView.this.listener.onCollageViewItemClick(view, this.index, layoutParams.leftMargin, layoutParams.topMargin, i2, i);
        }
    }

    private void setViewBitmap(View view, Bitmap bitmap, String str) {
        if (bitmap != null) {
            for (int i = 0; i < this.m_vwCount; i++) {
                if (this.m_vws[i] == view && i < this.bmps.size()) {
                    this.m_vws[i].setImageBitmap(bitmap);
                    this.bmps.set(i, bitmap);
                    this.viewWidth = bitmap.getWidth() / 2;
                    return;
                }
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

    public void setCollageImages(List<Bitmap> list, boolean z) {
        this.bitmaps = list;
        if (this.imagecount == 1) {
            this.m_vws[0].setIsLongclick(false);
        } else {
            this.m_vws[0].setIsLongclick(true);
        }
        int i = 0;
        while (i < this.m_vwCount) {
            this.m_vws[i].setVisibility(this.imagecount > i ? View.VISIBLE : View.INVISIBLE);
            this.m_vws[i].setTag(Integer.valueOf(i));
            this.m_vws[i].setIndex(i);
            if (this.m_vws[i].getVisibility() == View.VISIBLE) {
                this.m_vws[i].setImageBitmap(list.get(i), z, null, 4.0f);
            } else {
                this.m_vfilenames[i] = null;
            }
            i++;
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

    public void setCollageStyle(TemplateRes templateRes, final int i, final int i2) {
        this.mHeight = i;
        this.mWidth = i2;
        if (templateRes != null) {
            this.mComposeInfo = templateRes;
            this.radius = templateRes.getRoundRadius();
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = i;
        layoutParams.width = i2;
        setLayoutParams(layoutParams);
        new Thread(new Runnable() { // from class: com.winflag.libcollage.view.TemplateView.5
            @Override // java.lang.Runnable
            public void run() {
                TemplateView.this.mhight = i;
                TemplateView.this.mwidth = i2;
                Message message = new Message();
                message.what = 1;
                TemplateView.this.myHandler.sendMessage(message);
            }
        }).start();
        ViewGroup.LayoutParams layoutParams2 = this.mFrameView.getLayoutParams();
        layoutParams2.height = i;
        layoutParams2.width = i2;
        this.mFrameView.setLayoutParams(layoutParams2);
        this.mFrameView.invalidate();
        requestLayout();
        if (this.lstTouchImageViews.size() > 0) {
            setTouchImage();
        }
    }

    public int getCollageWidth() {
        return this.mWidth;
    }

    public int getCollageHeight() {
        return this.mHeight;
    }

    public void setCollageStyle(TemplateRes templateRes) {
        this.mComposeInfo = templateRes;
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
        int i2;
        Bitmap bitmap;
        Paint paint = new Paint();
        if (this.mHeight < 1) {
            this.mHeight = getHeight();
        }
        if (this.mWidth < 1) {
            this.mWidth = getWidth();
        }
        float f = this.mHeight / this.mWidth;
        int i3 = (int) ((i * f) + 0.5f);
        if (this.mComposeInfo == null) {
            return;
        }
        Bitmap createBgImage = createBgImage(i);
        if (i3 < 1 || i < 1) {
            i3 = this.mHeight;
            i2 = this.mWidth;
        } else {
            i2 = i;
        }
        if (i3 < 1 || i2 < 1) {
            return;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        if (createBgImage != null) {
            canvas.drawBitmap(createBgImage, 0.0f, 0.0f, paint);
            if (!createBgImage.isRecycled()) {
                createBgImage.recycle();
            }
        }
        Drawable drawable = this.backgroundDrawable;
        if (drawable != null) {
            drawable.setBounds(0, 0, i2, i3);
            this.backgroundDrawable.draw(canvas);
            this.backgroundDrawable.setBounds(0, 0, getWidth(), getHeight());
        }
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        for (int i4 = 0; i4 < this.mComposeInfo.getCollageInfo().size(); i4++) {
            Rect layoutPosition = getLayoutPosition(i2, this.mComposeInfo.getCollageInfo().get(i4).GetRect(f));
            Bitmap dispalyImage = this.m_vws[i4].getDispalyImage(layoutPosition.right - layoutPosition.left, layoutPosition.bottom - layoutPosition.top);
            if (dispalyImage != null) {
                canvas.drawBitmap(dispalyImage, (Rect) null, layoutPosition, paint);
                dispalyImage.recycle();
            }
        }
        FramesViewProcess framesViewProcess = this.mFrameView;
        if (framesViewProcess != null && (bitmap = framesViewProcess.getBitmap()) != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(bitmap, (Rect) null, new Rect(0, 0, i, i3), new Paint());
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        float f2 = i2;
        float f3 = i3;
        float f4 = f2 / 800.0f;
        if (f2 > f3) {
            f4 = f3 / 800.0f;
        }
        try {
            if (Data_Change.getInstance().getBl() != null && Data_Change.getInstance().getBl().getWidth() > 5 && Data_Change.getInstance().getBl().getHeight() > 5) {
                int width = (int) (Data_Change.getInstance().getBl().getWidth() * f4);
                int height = (int) (Data_Change.getInstance().getBl().getHeight() * f4);
                Bitmap bl = Data_Change.getInstance().getBl();
                if (bl != null && !bl.isRecycled()) {
                    Bitmap scale = ImageUtils.scale(Data_Change.getInstance().getBl(), width, height);
                    canvas.drawBitmap(scale, 0.0f, canvas.getHeight() - scale.getHeight(), paint);
                    scale.recycle();
                }
            }
            if (Data_Change.getInstance().getBr() != null && Data_Change.getInstance().getBr().getWidth() > 5 && Data_Change.getInstance().getBr().getHeight() > 5) {
                int width2 = (int) (Data_Change.getInstance().getBr().getWidth() * f4);
                int height2 = (int) (Data_Change.getInstance().getBr().getHeight() * f4);
                Bitmap br = Data_Change.getInstance().getBr();
                if (br != null && !br.isRecycled()) {
                    Bitmap scale2 = ImageUtils.scale(Data_Change.getInstance().getBr(), width2, height2);
                    canvas.drawBitmap(scale2, canvas.getWidth() - scale2.getWidth(), canvas.getHeight() - scale2.getHeight(), paint);
                    scale2.recycle();
                }
            }
            if (Data_Change.getInstance().getTl() != null && Data_Change.getInstance().getTl().getWidth() > 5 && Data_Change.getInstance().getTl().getHeight() > 5) {
                int width3 = (int) (Data_Change.getInstance().getTl().getWidth() * f4);
                int height3 = (int) (Data_Change.getInstance().getTl().getHeight() * f4);
                Bitmap tl = Data_Change.getInstance().getTl();
                if (tl != null && !tl.isRecycled()) {
                    Bitmap scale3 = ImageUtils.scale(Data_Change.getInstance().getTl(), width3, height3);
                    canvas.drawBitmap(scale3, 0.0f, 0.0f, paint);
                    scale3.recycle();
                }
            }
            if (Data_Change.getInstance().getTr() != null && Data_Change.getInstance().getTr().getWidth() > 5 && Data_Change.getInstance().getTr().getHeight() > 5) {
                int width4 = (int) (Data_Change.getInstance().getTr().getWidth() * f4);
                int height4 = (int) (Data_Change.getInstance().getTr().getHeight() * f4);
                Bitmap tr = Data_Change.getInstance().getTr();
                if (tr != null && !tr.isRecycled()) {
                    Bitmap scale4 = ImageUtils.scale(Data_Change.getInstance().getTr(), width4, height4);
                    canvas.drawBitmap(scale4, canvas.getWidth() - scale4.getWidth(), 0.0f, paint);
                    scale4.recycle();
                }
            }
        } catch (Exception unused) {
        }
        Bitmap resultBitmap = this.sfcView_faces.getResultBitmap();
        canvas.drawBitmap(resultBitmap, new Rect(0, 0, resultBitmap.getWidth(), resultBitmap.getHeight()), new Rect(0, 0, i, i3), (Paint) null);
        if (resultBitmap != null && !resultBitmap.isRecycled()) {
            resultBitmap.recycle();
        }
        Drawable drawable2 = this.mBackgroundDrawable;
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, getWidth(), getHeight());
            setMyViewBackgroud(this.mBackgroundDrawable);
        }
        if (onoutputimagelistener != null) {
            onoutputimagelistener.onOutputImageFinish(createBitmap);
        }
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
        DMIgnoreRecycleImageView dMIgnoreRecycleImageView = this.img_bg;
        if (dMIgnoreRecycleImageView != null) {
            dMIgnoreRecycleImageView.setImageBitmap(null);
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
        if (this.borderView != null) {
            this.borderView = null;
        }
        DMStickerCanvasView dMStickerCanvasView = this.sfcView_faces;
        if (dMStickerCanvasView != null) {
            dMStickerCanvasView.clearStickers();
        }
        for (Map.Entry<Bitmap, Bitmap> entry : this.mSrc2FilterMap.entrySet()) {
            Bitmap value = entry.getValue();
            if (value != null && !value.isRecycled()) {
                value.recycle();
            }
        }
        this.mSrc2FilterMap.clear();
        this.mFrameView.dispose();
        noBg();
        ColorDrawable colorDrawable = new ColorDrawable(-1);
        this.backgroundDrawable = colorDrawable;
        setSquareBackground(colorDrawable, false);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void layoutCompose(int i, int i2) {
        TemplateRes templateRes = this.mComposeInfo;
        if (templateRes == null) {
            return;
        }
        float f = i;
        float f2 = i2;
        float f3 = f / f2;
        float f4 = f2 / 3060.0f;
        float f5 = f / (f3 * 3060.0f);
        if (templateRes.getCollageInfo() != null) {
            this.minnerWidth = this.mComposeInfo.getCollageInfo().get(0).getInnerFrameWidth();
            this.mouterWidth = this.mComposeInfo.getCollageInfo().get(0).getOutFrameWidth();
        }
        List<LibDMCollageInfo> collageInfo = this.mComposeInfo.getCollageInfo();
        for (int i3 = 0; i3 < collageInfo.size(); i3++) {
            if (collageInfo.size() >= 1) {
                LibDMCollageInfo libDMCollageInfo = this.mComposeInfo.getCollageInfo().get(i3);
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
        TemplateRes templateRes = this.mComposeInfo;
        if (templateRes == null || templateRes.getCollageInfo() == null) {
            return;
        }
        if (!this.mComposeInfo.getInnerChange()) {
            i3 = -1;
        }
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
        if (this.lstTouchImageViews.size() > 0) {
            changeTouchImagePosition();
        }
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

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        FrameLayout.LayoutParams layoutParams;
        if (this.listener == null || (layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams()) == null || this.listener == null) {
            return;
        }
        int i2 = layoutParams.height;
        this.listener.onCollageViewItemClick(view, this.mindex, layoutParams.leftMargin, layoutParams.topMargin, layoutParams.width, i2);
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

    private Bitmap getViewBitmap(View view) {
        if (this.m_vws == null || this.bmps == null) {
            return null;
        }
        for (int i = 0; i < this.bmps.size(); i++) {
            if (view == this.m_vws[i]) {
                return this.bmps.get(i);
            }
        }
        return null;
    }

    public void setSelViewBitmap(Bitmap bitmap) {
        setSelViewBitmap(bitmap, "");
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
        if (this.m_vws == null || this.bmps == null) {
            return;
        }
        for (int i = 0; i < this.m_vwCount; i++) {
            if (view == this.m_vws[i]) {
                if (i < this.bmps.size()) {
                    this.m_vOriginalBitmap = this.bmps.get(i);
                    return;
                } else {
                    this.m_vOriginalBitmap = null;
                    return;
                }
            }
        }
    }

    public void setBitmapList(List<Bitmap> list) {
        this.bmps = list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ExchangeImage(View view) {
        if (this.imgExchanger.booleanValue()) {
            this.imgExchanger = false;
            LibDMMaskImageViewTouch libDMMaskImageViewTouch = this.m_vSelOri;
            if (view != libDMMaskImageViewTouch) {
                OnItemClickListener onItemClickListener = this.mItemlistener;
                if (onItemClickListener != null) {
                    onItemClickListener.ItemChange(view, libDMMaskImageViewTouch);
                }
                LibDMMaskImageViewTouch libDMMaskImageViewTouch2 = (LibDMMaskImageViewTouch) view;
                LibDMMaskImageViewTouch libDMMaskImageViewTouch3 = this.m_vSelOri;
                getResources();
                this.m_vOriginalBitmap = getViewBitmap(this.m_vSelOri);
                Bitmap viewBitmap = getViewBitmap(view);
                Bitmap bitmap = this.m_vOriginalBitmap;
                if (bitmap != null) {
                    libDMMaskImageViewTouch2.setImageBitmap(bitmap, true, null, this.mMaxZoom);
                }
                setExchangeViewBitmap(view);
                libDMMaskImageViewTouch2.setlongclickEnable(false);
                if (viewBitmap != null) {
                    libDMMaskImageViewTouch3.setImageBitmap(viewBitmap, true, null, this.mMaxZoom);
                }
                this.m_vOriginalBitmap = viewBitmap;
                setExchangeViewBitmap(this.m_vSelOri);
                this.imgExchanger = false;
                libDMMaskImageViewTouch2.setDrowRectangle(true);
            }
        }
    }

    private void setExchangeViewBitmap(View view) {
        for (int i = 0; i < this.m_vwCount; i++) {
            if (view == this.m_vws[i] && i < this.bmps.size()) {
                this.bmps.set(i, this.m_vOriginalBitmap);
                return;
            }
        }
    }

    public void resetNoSelected() {
        this.m_vSel = null;
        for (int i = 0; i < this.m_vwCount; i++) {
            this.m_vws[i].setDrowRectangle(false);
        }
    }

    public int getFrameWidth() {
        return this.mComposeInfo.getFrameWidth();
    }

    public void setBackground(int i, DMWBRes dMWBRes) {
        this.mImageRes = null;
        if (dMWBRes != null) {
            if (i == 2) {
                setBackgroundColor(((DMWBColorRes) dMWBRes).getColorValue());
                return;
            }
            DMWBImageRes dMWBImageRes = (DMWBImageRes) dMWBRes;
            this.mImageRes = dMWBImageRes;
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
        Bitmap bitmap = null;
        if (viewBitmap != null && this.mSrc2FilterMap.get(viewBitmap) != null) {
            bitmap = this.mSrc2FilterMap.get(viewBitmap);
        }
        Bitmap bitmap2 = bitmap;
        if (viewBitmap == null || viewBitmap.isRecycled()) {
            return;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(f, getWidth() / 2, getHeight() / 2);
        Bitmap createBitmap = Bitmap.createBitmap(viewBitmap, 0, 0, viewBitmap.getWidth(), viewBitmap.getHeight(), matrix, true);
        setViewBitmap(createBitmap, "");
        if (bitmap2 != null) {
            this.mSrc2FilterMap.remove(viewBitmap);
        }
        if (bitmap2 != null) {
            Matrix matrix2 = new Matrix();
            matrix2.postRotate(f, getWidth() / 2, getHeight() / 2);
            Bitmap createBitmap2 = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix2, true);
            setPictureImageBitmapNoReset(createBitmap2);
            this.mSrc2FilterMap.put(createBitmap, createBitmap2);
            if (bitmap2 != null && !bitmap2.isRecycled()) {
                bitmap2.recycle();
            }
        }
        this.mResourceBmp = getSelBitmap();
    }

    public void doReversal(float f) {
        if (this.m_vSel == null) {
            this.m_vSel = this.m_vws[0];
        }
        Bitmap viewBitmap = getViewBitmap(this.m_vSel);
        Bitmap bitmap = null;
        if (viewBitmap != null && this.mSrc2FilterMap.get(viewBitmap) != null) {
            bitmap = this.mSrc2FilterMap.get(viewBitmap);
        }
        Bitmap bitmap2 = bitmap;
        if (viewBitmap == null || viewBitmap.isRecycled()) {
            return;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(1.0f, -1.0f, getWidth() / 2, getHeight() / 2);
        matrix.postRotate(f, getWidth() / 2, getHeight() / 2);
        Bitmap createBitmap = Bitmap.createBitmap(viewBitmap, 0, 0, viewBitmap.getWidth(), viewBitmap.getHeight(), matrix, true);
        if (bitmap2 != null) {
            this.mSrc2FilterMap.remove(viewBitmap);
        }
        setViewBitmap(createBitmap, "");
        if (bitmap2 != null) {
            Matrix matrix2 = new Matrix();
            matrix2.postScale(1.0f, -1.0f, getWidth() / 2, getHeight() / 2);
            matrix2.postRotate(f, getWidth() / 2, getHeight() / 2);
            Bitmap createBitmap2 = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix2, true);
            setPictureImageBitmapNoReset(createBitmap2);
            this.mSrc2FilterMap.put(createBitmap, createBitmap2);
            if (bitmap2 != null && !bitmap2.isRecycled()) {
                bitmap2.recycle();
            }
        }
        this.mResourceBmp = getSelBitmap();
    }

    public void addSticker(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        DMSticker dMSticker = new DMSticker(this.viewWidth);
        dMSticker.setBitmap(bitmap);
        float width = (getWidth() / 3.0f) / dMSticker.getWidth();
        Matrix matrix = new Matrix();
        Matrix matrix2 = new Matrix();
        Matrix matrix3 = new Matrix();
        matrix3.postScale(width, width);
        matrix2.postTranslate(getWidth() / 3.0f, getHeight() / 3.0f);
        this.stickerBitmapList.add(bitmap);
        this.sfcView_faces.addSticker(dMSticker, matrix, matrix2, matrix3);
        this.sfcView_faces.cancelSelected();
        this.sfcView_faces.invalidate();
    }

    public void changeToTemplateCollage() {
        this.sfcView_faces.clearStickersOnlyFreePuzzzle();
        for (int i = 0; i < this.bitmaps.size(); i++) {
            this.m_vws[i].setVisibility(View.VISIBLE);
        }
    }

    public void changeToFreeCollage() {
        for (int i = 0; i < this.bitmaps.size(); i++) {
            this.m_vws[i].setVisibility(View.INVISIBLE);
        }
    }

    @Override // org.picspool.lib.sticker.util.DMStickerStateCallback
    public void stickerSelected(DMSticker dMSticker) {
        this.mCurrentFace = dMSticker;
        List<DMStickerStateCallback> list = this.stickerStateCallSpreaders;
        if (list != null) {
            for (DMStickerStateCallback dMStickerStateCallback : list) {
                if (dMStickerStateCallback != null) {
                    dMStickerStateCallback.stickerSelected(dMSticker);
                }
            }
        }
    }

    @Override // org.picspool.lib.sticker.util.DMStickerStateCallback
    public void editButtonClicked() {
        if (this.mCurrentFace != null) {
            this.sfcView_faces.removeCurSelectedSticker();
            Bitmap bitmap = this.mCurrentFace.getBitmap();
            for (int i = 0; i < this.stickerBitmapList.size(); i++) {
                if (bitmap == this.stickerBitmapList.get(i)) {
                    this.stickerBitmapList.remove(bitmap);
                    bitmap.recycle();
                    bitmap = null;
                }
            }
            this.mCurrentFace = null;
        }
        List<DMStickerStateCallback> list = this.stickerStateCallSpreaders;
        if (list != null) {
            for (DMStickerStateCallback dMStickerStateCallback : list) {
                dMStickerStateCallback.editButtonClicked();
            }
        }
    }

    @Override // org.picspool.lib.sticker.util.DMStickerStateCallback
    public void noStickerSelected() {
        this.mCurrentFace = null;
        List<DMStickerStateCallback> list = this.stickerStateCallSpreaders;
        if (list != null) {
            for (DMStickerStateCallback dMStickerStateCallback : list) {
                dMStickerStateCallback.noStickerSelected();
            }
        }
    }

    @Override // org.picspool.lib.sticker.util.DMStickerStateCallback
    public void onDoubleClicked() {
        List<DMStickerStateCallback> list = this.stickerStateCallSpreaders;
        if (list != null) {
            for (DMStickerStateCallback dMStickerStateCallback : list) {
                dMStickerStateCallback.onDoubleClicked();
            }
        }
    }

    public int getStickerCount() {
        DMStickerCanvasView dMStickerCanvasView = this.sfcView_faces;
        if (dMStickerCanvasView != null) {
            return dMStickerCanvasView.getStickersCount();
        }
        return 0;
    }

    @Override // org.picspool.lib.sticker.util.DMStickerStateCallback
    public void onImageDown(DMSticker dMSticker) {
        this.mCurrentFace = dMSticker;
    }

    public DMStickerCanvasView getSfcView_faces() {
        return this.sfcView_faces;
    }

    public void setSfcView_faces(DMStickerCanvasView dMStickerCanvasView) {
        this.sfcView_faces = dMStickerCanvasView;
    }

    public void addStickerStateCallSpreader(DMStickerStateCallback dMStickerStateCallback) {
        List<DMStickerStateCallback> list = this.stickerStateCallSpreaders;
        if (list != null) {
            list.add(dMStickerStateCallback);
        }
    }

    public void clearStickerStateCallSpreader() {
        List<DMStickerStateCallback> list = this.stickerStateCallSpreaders;
        if (list != null) {
            list.clear();
        }
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
        if (this.mComposeInfo == null) {
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
        if (this.mComposeInfo == null) {
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
        if (this.mComposeInfo != null) {
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
    }

    public int getRotaitonDegree() {
        return this.mRotaiton;
    }

    public void setShadowValue(int i) {
        TemplateRes templateRes = this.mComposeInfo;
        if (templateRes == null || templateRes.getCollageInfo() == null) {
            return;
        }
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

    public void setHueValue(float f) {
        this.mHueValue = f;
    }

    public void handleImage() {
        DMIgnoreRecycleImageView dMIgnoreRecycleImageView = this.img_bg;
        if (dMIgnoreRecycleImageView != null && dMIgnoreRecycleImageView.getBackground() != null) {
            this.img_bg.getBackground().setColorFilter(DMColorFilterGenerator.adjustHue(this.mHueValue));
            this.img_bg.invalidate();
            return;
        }
        this.img_bg.setColorFilter(DMColorFilterGenerator.adjustHue(this.mHueValue));
        this.img_bg.invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTouchImage() {
        LibDMMaskImageViewTouch libDMMaskImageViewTouch = this.m_vSel;
        if (libDMMaskImageViewTouch == null) {
            return;
        }
        List<LibDMCollageInfo.LibCollageTouchPoint> touchCollagePoints = libDMMaskImageViewTouch.getCollageInfo().getTouchCollagePoints();
        this.touchimglayout.removeAllViews();
        this.lstTouchImageViews.clear();
        if (touchCollagePoints.size() > 0) {
            float f = this.mHeight;
            float f2 = this.mWidth;
            float f3 = f2 / 3060.0f;
            float f4 = f / ((f / f2) * 3060.0f);
            int i = 0;
            for (LibDMCollageInfo.LibCollageTouchPoint libCollageTouchPoint : touchCollagePoints) {
                Point touchPoint = libCollageTouchPoint.getTouchPoint();
                int i2 = i + 1;
                libCollageTouchPoint.setIndex(i);
                ImageView imageView = new ImageView(getContext());
                int i3 = (int) ((touchPoint.x * f3) - (this.imageWidth / 2.0f));
                int i4 = (int) ((touchPoint.y * f4) - (this.imageWidth / 2.0f));
                if (libCollageTouchPoint.getTouchState() == 0) {
                    imageView.setImageResource(R.drawable.collage_img_drop_leftright);
                } else {
                    imageView.setImageResource(R.drawable.collage_img_drop_updown);
                }
                imageView.setTag(libCollageTouchPoint);
                imageView.setOnTouchListener(new ImgDragOnTouchListener());
                this.lstTouchImageViews.add(imageView);
                this.touchimglayout.addView(imageView);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
                layoutParams.width = this.imageWidth;
                layoutParams.height = this.imageWidth;
                layoutParams.setMargins(i3, i4, 0, 0);
                imageView.requestLayout();
                i = i2;
            }
        }
    }

    public void clearTouchImage() {
        this.touchimglayout.removeAllViews();
        this.lstTouchImageViews.clear();
    }

    private void changeTouchImagePosition() {
        LibDMMaskImageViewTouch libDMMaskImageViewTouch = this.m_vSel;
        if (libDMMaskImageViewTouch == null) {
            return;
        }
        List<LibDMCollageInfo.LibCollageTouchPoint> touchCollagePoints = libDMMaskImageViewTouch.getCollageInfo().getTouchCollagePoints();
        if (touchCollagePoints.size() > 0) {
            float f = this.mHeight;
            float f2 = this.mWidth;
            float f3 = f / f2;
            float f4 = f2 / 3060.0f;
            float f5 = f / (f3 * 3060.0f);
            int i = 0;
            for (LibDMCollageInfo.LibCollageTouchPoint libCollageTouchPoint : touchCollagePoints) {
                Point touchPoint = libCollageTouchPoint.getTouchPoint();
                for (int i2 = 0; i2 < this.lstTouchImageViews.size(); i2++) {
                    ImageView imageView = this.lstTouchImageViews.get(i2);
                    LibDMCollageInfo.LibCollageTouchPoint libCollageTouchPoint2 = (LibDMCollageInfo.LibCollageTouchPoint) imageView.getTag();
                    if (libCollageTouchPoint2.getIndex() == i) {
                        int i3 = this.imageWidth;
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i3, i3);
                        layoutParams.setMargins((int) ((touchPoint.x * f4) - (this.imageWidth / 2.0f)), (int) (((touchPoint.y * f5) * f3) - (this.imageWidth / 2.0f)), 0, 0);
                        layoutParams.gravity = 3;
                        libCollageTouchPoint.setIndex(i);
                        libCollageTouchPoint.setPointMaxValue(libCollageTouchPoint2.getPointMaxValue());
                        libCollageTouchPoint.setPointMinValue(libCollageTouchPoint2.getPointMinValue());
                        imageView.setTag(libCollageTouchPoint);
                        imageView.setLayoutParams(layoutParams);
                    }
                }
                i++;
            }
        }
    }

    /* loaded from: classes.dex */
    public class LayoutDragOnTouchListener implements OnTouchListener {
        int contextheight;
        int contextwidth;
        int lastX = 0;
        int lastY = 0;

        public LayoutDragOnTouchListener() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            try {
                if (TemplateView.this.mDrogOnTouchDown && TemplateView.this.dragView != null) {
                    int action = motionEvent.getAction() & 255;
                    if (action == 0) {
                        this.lastX = (int) motionEvent.getX();
                        this.lastY = (int) motionEvent.getY();
                    } else if (action == 1) {
                        TemplateView.this.mDrogOnTouchDown = false;
                    } else if (action != 2 || view == null) {
                        return true;
                    } else {
                        int height = view.getHeight();
                        int width = view.getWidth();
                        if (TemplateView.this.dragView.getTag() != null) {
                            LibDMCollageInfo.LibCollageTouchPoint libCollageTouchPoint = (LibDMCollageInfo.LibCollageTouchPoint) TemplateView.this.dragView.getTag();
                            List<LibDMCollageInfo> collageInfo = TemplateView.this.mComposeInfo.getCollageInfo();
                            float innerFrameWidth = width / ((3060.0f - (collageInfo.get(0).getInnerFrameWidth() * 2)) + (collageInfo.get(0).getOutFrameWidth() * 2));
                            float innerFrameWidth2 = height / ((3060.0f - (collageInfo.get(0).getInnerFrameWidth() * 2)) + (collageInfo.get(0).getOutFrameWidth() * 2));
                            if (libCollageTouchPoint.getTouchState() == 0) {
                                int x = (int) ((((int) motionEvent.getX()) - this.lastX) / innerFrameWidth);
                                Log.v("getTouchPoint", "getTouchPoint:" + String.valueOf(libCollageTouchPoint.getTouchPoint().x));
                                if (x > 0) {
                                    if (libCollageTouchPoint.getTouchPoint().x + x < libCollageTouchPoint.getPointMaxValue()) {
                                        for (int i = 0; i < collageInfo.size(); i++) {
                                            collageInfo.get(i).createNewPoint(libCollageTouchPoint.gethLineMode(), 0, x);
                                        }
                                    }
                                } else if (libCollageTouchPoint.getTouchPoint().x + x > libCollageTouchPoint.getPointMinValue()) {
                                    for (int i2 = 0; i2 < collageInfo.size(); i2++) {
                                        collageInfo.get(i2).createNewPoint(libCollageTouchPoint.gethLineMode(), 0, x);
                                    }
                                }
                            } else {
                                int y = (int) ((((int) motionEvent.getY()) - this.lastY) / innerFrameWidth2);
                                if (y > 0) {
                                    if (libCollageTouchPoint.getTouchPoint().y + y < libCollageTouchPoint.getPointMaxValue()) {
                                        for (int i3 = 0; i3 < collageInfo.size(); i3++) {
                                            collageInfo.get(i3).createNewPoint(libCollageTouchPoint.getvLineMode(), 1, y);
                                        }
                                    }
                                } else if (libCollageTouchPoint.getTouchPoint().y + y > libCollageTouchPoint.getPointMinValue()) {
                                    for (int i4 = 0; i4 < collageInfo.size(); i4++) {
                                        collageInfo.get(i4).createNewPoint(libCollageTouchPoint.getvLineMode(), 1, y);
                                    }
                                }
                            }
                            TemplateView.this.Changelayout(1, TemplateView.this.minnerWidth, TemplateView.this.mouterWidth);
                        }
                        this.lastX = (int) motionEvent.getX();
                        this.lastY = (int) motionEvent.getY();
                    }
                    return true;
                }
            } catch (Exception e) {
                PrintStream printStream = System.out;
                printStream.println("error:" + e.getMessage());
            }
            return false;
        }
    }

    /* loaded from: classes.dex */
    public class ImgDragOnTouchListener implements OnTouchListener {
        public ImgDragOnTouchListener() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            try {
                int action = motionEvent.getAction() & 255;
                if (action == 0) {
                    TemplateView.this.mDrogOnTouchDown = true;
                    if (TemplateView.this.mItemlistener != null) {
                        TemplateView.this.mItemlistener.ItemSizeChange();
                    }
                    TemplateView.this.dragView = view;
                    LibDMCollageInfo.LibCollageTouchPoint libCollageTouchPoint = (LibDMCollageInfo.LibCollageTouchPoint) TemplateView.this.dragView.getTag();
                    TemplateView.this.minAreaValue = -3060;
                    TemplateView.this.maxAreaValue = 3060;
                    for (int i = 0; i < TemplateView.this.mComposeInfo.getCollageInfo().size(); i++) {
                        if (TemplateView.this.mComposeInfo.getCollageInfo().size() >= 1) {
                            LibDMCollageInfo libDMCollageInfo = TemplateView.this.mComposeInfo.getCollageInfo().get(i);
                            if (libCollageTouchPoint.getTouchState() == 0) {
                                libCollageTouchPoint = libDMCollageInfo.getPointArea(libCollageTouchPoint.gethLineMode(), 0, libCollageTouchPoint);
                                if (libCollageTouchPoint.getAreaMinValue() > TemplateView.this.minAreaValue) {
                                    TemplateView.this.minAreaValue = libCollageTouchPoint.getAreaMinValue();
                                }
                                if (libCollageTouchPoint.getAreaMaxValue() < TemplateView.this.maxAreaValue) {
                                    TemplateView.this.maxAreaValue = libCollageTouchPoint.getAreaMaxValue();
                                }
                            } else {
                                libCollageTouchPoint = libDMCollageInfo.getPointArea(libCollageTouchPoint.getvLineMode(), 1, libCollageTouchPoint);
                                if (libCollageTouchPoint.getAreaMinValue() > TemplateView.this.minAreaValue) {
                                    TemplateView.this.minAreaValue = libCollageTouchPoint.getAreaMinValue();
                                }
                                if (libCollageTouchPoint.getAreaMaxValue() < TemplateView.this.maxAreaValue) {
                                    TemplateView.this.maxAreaValue = libCollageTouchPoint.getAreaMaxValue();
                                }
                            }
                        }
                    }
                    int innerFrameWidth = TemplateView.this.mComposeInfo.getCollageInfo().get(0).getInnerFrameWidth() + TemplateView.this.mComposeInfo.getCollageInfo().get(0).getOutFrameWidth();
                    if (libCollageTouchPoint.getTouchState() == 0) {
                        libCollageTouchPoint.setPointMaxValue((libCollageTouchPoint.getTouchPoint().x + TemplateView.this.maxAreaValue) - innerFrameWidth);
                        libCollageTouchPoint.setPointMinValue(libCollageTouchPoint.getTouchPoint().x + TemplateView.this.minAreaValue + innerFrameWidth);
                    } else {
                        libCollageTouchPoint.setPointMaxValue((libCollageTouchPoint.getTouchPoint().y + TemplateView.this.maxAreaValue) - innerFrameWidth);
                        libCollageTouchPoint.setPointMinValue(libCollageTouchPoint.getTouchPoint().y + TemplateView.this.minAreaValue + innerFrameWidth);
                    }
                } else if (action == 1) {
                    TemplateView.this.mDrogOnTouchDown = false;
                    TemplateView.this.dragView = null;
                }
            } catch (Exception e) {
                PrintStream printStream = System.out;
                printStream.println("error:" + e.getMessage());
            }
            return false;
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        OnItemLongClickListener onItemLongClickListener;
        int actionMasked = motionEvent.getActionMasked();
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (this.isLongMode) {
            if (actionMasked == 0) {
                this.isOnceTouch = true;
            } else if (actionMasked == 1) {
                int longUpPointFromViewIndex = getLongUpPointFromViewIndex(x, y);
                this.curMoveImageView.setVisibility(View.INVISIBLE);
                this.curMoveImageView.setImageBitmap(null);
                if (longUpPointFromViewIndex != -1) {
                    ExchangeImage(this.m_vws[longUpPointFromViewIndex]);
                    setSelectIndexRectColor(longUpPointFromViewIndex, -16711936);
                    this.m_vSel = this.m_vws[longUpPointFromViewIndex];
                    setTouchImage();
                }
                this.isLongMode = false;
            } else if (actionMasked == 2) {
                if (this.isOnceTouch && (onItemLongClickListener = this.mItemLonglistener) != null) {
                    onItemLongClickListener.ItemLongMove();
                    this.isOnceTouch = false;
                }
                setCurMoveView(x, y);
                setSelectIndexRectColor(getLongUpPointFromViewIndex(x, y), -65536);
            }
        }
        return false;
    }

    public void setCurMoveView(int i, int i2) {
        Bitmap srcBitmap;
        LibDMMaskImageViewTouch libDMMaskImageViewTouch = this.m_vSel;
        if (libDMMaskImageViewTouch == null || (srcBitmap = libDMMaskImageViewTouch.getSrcBitmap()) == null) {
            return;
        }
        float dip2px = DMScreenInfoUtil.dip2px(this.mContext, 160.0f);
        float height = (srcBitmap.getHeight() / srcBitmap.getWidth()) * dip2px;
        int i3 = (int) dip2px;
        this.curMoveImageView.getLayoutParams().width = i3;
        int i4 = (int) height;
        this.curMoveImageView.getLayoutParams().height = i4;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.curMoveImageView.getLayoutParams();
        layoutParams.leftMargin = i - (i3 / 2);
        layoutParams.topMargin = i2 - (i4 / 2);
        this.curMoveImageView.setAlpha(Opcodes.IF_ICMPNE);
        this.curMoveImageView.setVisibility(View.VISIBLE);
        this.curMoveImageView.setImageBitmap(srcBitmap);
        this.curMoveImageView.requestLayout();
        this.curMoveImageView.invalidate();
    }

    public void setSelectIndexRectColor(int i, int i2) {
        for (int i3 = 0; i3 < this.bitmaps.size(); i3++) {
            if (i3 == i) {
                this.m_vws[i3].setDrawLineMode(i2);
                this.m_vws[i3].setDrowRectangle(true);
            } else {
                this.m_vws[i3].setDrawLineMode(-1);
                this.m_vws[i3].setDrowRectangle(false);
            }
            this.m_vws[i3].invalidate();
        }
    }

    public int getLongUpPointFromViewIndex(int i, int i2) {
        for (int i3 = 0; i3 < this.bitmaps.size(); i3++) {
            if (this.m_vws[i3].isInViewArea(i, i2) && i3 != this.curDownIndex && this.m_vws[i3] != this.m_vSelOri) {
                return i3;
            }
        }
        return -1;
    }
}

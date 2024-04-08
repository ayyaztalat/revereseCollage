package com.picspool.libsquare.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.picspool.libfuncview.background.CSGradientRes;
import com.picspool.libsquare.manager.CSSquareUiFrameManager;
import com.picspool.libsquare.res.CSShapeRes;
import com.picspool.libsquare.widget.blend.BlendView;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import com.picspool.instafilter.GPUFilter;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.bitmap.DMBitmapCrop;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.filter.cpu.normal.FastBlurFilter;
import com.picspool.lib.filter.listener.OnFilterFinishedListener;
import com.picspool.lib.filter.listener.OnPostFilteredListener;
import com.picspool.lib.resource.DMWBColorRes;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sticker.core.DMSticker;
import com.picspool.lib.sticker.drawonview.DMStickerCanvasView;
import com.picspool.lib.sticker.util.DMStickerStateCallback;
import com.picspool.lib.sysphotoselector.DMPagerSlidingTabStrip;
import com.picspool.lib.widget.colorstraw.DMColorStraw;
import com.picspool.lib.widget.listener.OnDMViewColorChangedListener;
import com.picspool.lib.widget.pointer.DMTouchPointView;
import com.sky.testproject.R;
import com.touch.android.library.imagezoom.ImageViewTouch;
import com.touch.android.library.imagezoom.ImageViewTouchBase;

/* loaded from: classes.dex */
public class CSSquareView extends RelativeLayout implements DMColorStraw.OnStrawingListener, OnDMViewColorChangedListener, DMStickerStateCallback {
    public static final int BLURBG = 4;
    public static final int COLORBG = 1;
    public static final int GRADIENTBG = 3;
    public static final int IMAGEBG = 2;
    private List<Bitmap> StickerBitmapList;
    private List<DMStickerStateCallback> StickerStateCallSpreaders;
    public int backgroundColor;
    public Drawable backgroundDrawable;
    public Bitmap bgsrc;
    private int colorfiltervalue;
    private DMWBRes curBorderRes;
    private DMWBRes curFilterRes;
    private DMWBRes curVigRes;
    private DMWBRes currentForeRes;
    private CSShapeRes currentShapeRes;
    public Bitmap dst;
    private int groundMode;
    IStickercallback iStickercallback;
    public ImageView img_bg;
    public ImageView img_bgfilter;
    private ImageView img_fg;
    public ImageViewTouch img_pic;
    public DMTouchPointView img_pointer;
    boolean isAutoBorderRes;
    boolean isOverlapping;
    private Boolean isStrawable;
    public int layerColor;
    private BlendView mBlendView;
    private DMColorStraw mColorStraw;
    public Context mContext;
    GradientDrawable mCurrentDrawable;
    private DMSticker mCurrentFace;
    private Bitmap mFilteredPic;
    private boolean mIsBlurBackground;
    public OnGetResultBitmapListener mListener;
    private Bitmap mOriPic;
    public PaintFlagsDrawFilter mPaintFlagsDrawFilter;
    private float mRotationDegree;
    private float mRotationScale;
    private Bitmap mShapeBgBitmap;
    Canvas mShapeCanvas;
    private Bitmap mShapeFinal;
    private Bitmap mShapePattern;
    Bitmap mShapedBitmap;
    Bitmap mStrawTmpXorShape;
    private int mTranslateX;
    private int mTranslateY;
    public DMStickerCanvasView sfcView_faces;
    private int shadowRadius;
    private boolean shadowed;
    private int shapeSize;
    public Bitmap src;
    int viewWidth;

    /* loaded from: classes.dex */
    public interface IStickercallback {
        void onIStickerSelected();

        void onIStickernoSelected();
    }

    /* loaded from: classes.dex */
    public interface OnGetResultBitmapListener {
        void getResultBitmapSuccess(Bitmap bitmap);
    }

    /* loaded from: classes.dex */
    public interface OnShapeFinishedListener {
        void onXorImage(Bitmap bitmap);
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void onImageDown(DMSticker dMSticker) {
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void onStickerChanged() {
    }

    public void setPicToEnd() {
    }

    public void setOnGetResultBitmapListener(OnGetResultBitmapListener onGetResultBitmapListener) {
        this.mListener = onGetResultBitmapListener;
    }

    public CSSquareView(Context context) {
        super(context);
        this.backgroundColor = -1;
        this.shadowRadius = 0;
        this.shadowed = false;
        this.viewWidth = 720;
        this.StickerBitmapList = new ArrayList();
        this.shapeSize = 960;
        this.groundMode = 1;
        this.layerColor = -1;
        this.mCurrentDrawable = null;
        this.mIsBlurBackground = false;
        this.mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, 3);
        this.isStrawable = false;
        this.mColorStraw = null;
        this.isOverlapping = false;
        this.isAutoBorderRes = false;
        this.mRotationDegree = 3.0f;
        this.mRotationScale = 1.0f;
        this.mContext = context;
        initView();
    }

    public CSSquareView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.backgroundColor = -1;
        this.shadowRadius = 0;
        this.shadowed = false;
        this.viewWidth = 720;
        this.StickerBitmapList = new ArrayList();
        this.shapeSize = 960;
        this.groundMode = 1;
        this.layerColor = -1;
        this.mCurrentDrawable = null;
        this.mIsBlurBackground = false;
        this.mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, 3);
        this.isStrawable = false;
        this.mColorStraw = null;
        this.isOverlapping = false;
        this.isAutoBorderRes = false;
        this.mRotationDegree = 3.0f;
        this.mRotationScale = 1.0f;
        this.mContext = context;
        initView();
    }

    public CSSquareView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.backgroundColor = -1;
        this.shadowRadius = 0;
        this.shadowed = false;
        this.viewWidth = 720;
        this.StickerBitmapList = new ArrayList();
        this.shapeSize = 960;
        this.groundMode = 1;
        this.layerColor = -1;
        this.mCurrentDrawable = null;
        this.mIsBlurBackground = false;
        this.mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, 3);
        this.isStrawable = false;
        this.mColorStraw = null;
        this.isOverlapping = false;
        this.isAutoBorderRes = false;
        this.mRotationDegree = 3.0f;
        this.mRotationScale = 1.0f;
        this.mContext = context;
        initView();
    }

    private void initView() {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.libs_view_square, (ViewGroup) this, true);
        this.img_bg = (ImageView) findViewById(R.id.img_bg);
        this.img_fg = (ImageView) findViewById(R.id.img_fg);
        ImageViewTouch imageViewTouch = (ImageViewTouch) findViewById(R.id.img_pic);
        this.img_pic = imageViewTouch;
        imageViewTouch.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        this.img_pointer = (DMTouchPointView) findViewById(R.id.img_pointer);
        this.img_bgfilter = (ImageView) findViewById(R.id.img_bg_filter);
        DMStickerCanvasView dMStickerCanvasView = (DMStickerCanvasView) findViewById(R.id.img_facial);
        this.sfcView_faces = dMStickerCanvasView;
        dMStickerCanvasView.startRender();
        this.sfcView_faces.onShow();
        this.sfcView_faces.setStickerCallBack(this);
        this.StickerStateCallSpreaders = new ArrayList();
    }

    public DMStickerCanvasView getBMStickerCanvasView() {
        return this.sfcView_faces;
    }

    public void setShape(DMWBRes dMWBRes) {
        if (dMWBRes != null) {
            if (this.img_pic.getDisplayType() != ImageViewTouchBase.DisplayType.FILL_TO_SCREEN) {
                this.img_pic.setDisplayType(ImageViewTouchBase.DisplayType.FILL_TO_SCREEN);
                this.img_pic.resetDisplay();
            }
            this.currentShapeRes = (CSShapeRes) dMWBRes;
            this.img_fg.setImageBitmap(null);
            this.mShapeCanvas = null;
            Bitmap bitmap = this.mShapedBitmap;
            if (bitmap != null && !bitmap.isRecycled()) {
                this.mShapedBitmap.recycle();
            }
            this.mShapedBitmap = null;
            Bitmap bitmap2 = this.mShapeFinal;
            if (bitmap2 != null && !bitmap2.isRecycled()) {
                this.mShapeFinal.recycle();
                this.mShapeFinal = null;
            }
            createShapeFinal(this.shapeSize, new OnShapeFinishedListener() { // from class: com.picspool.libsquare.view.CSSquareView.1
                @Override // com.picspool.libsquare.view.CSSquareView.OnShapeFinishedListener
                public void onXorImage(Bitmap bitmap3) {
                    CSSquareView.this.mShapeFinal = bitmap3;
                    CSSquareView.this.img_fg.setImageBitmap(CSSquareView.this.mShapeFinal);
                }
            });
        }
    }

    public void setColorbg(int i) {
        this.colorfiltervalue = i;
        this.img_bgfilter.setBackgroundColor(i);
        this.img_bgfilter.setVisibility(View.VISIBLE);
    }

    public void hideColorBgfilter() {
        this.img_bgfilter.setVisibility(View.GONE);
    }

    public void setForeground(DMWBRes dMWBRes, int i) {
        if (dMWBRes != null) {
            this.mCurrentDrawable = null;
            this.currentForeRes = dMWBRes;
            if (i == 1) {
                this.groundMode = 1;
                this.layerColor = ((DMWBColorRes) dMWBRes).getColorValue();
            } else if (i == 2) {
                this.groundMode = 2;
            }
            Bitmap bitmap = this.mShapePattern;
            if (bitmap != null && !bitmap.isRecycled()) {
                this.mShapePattern.recycle();
                this.mShapePattern = null;
            }
            if (this.currentShapeRes != null) {
                this.img_fg.setImageBitmap(null);
                createShapeFinal(this.shapeSize, new OnShapeFinishedListener() { // from class: com.picspool.libsquare.view.CSSquareView.2
                    @Override // com.picspool.libsquare.view.CSSquareView.OnShapeFinishedListener
                    public void onXorImage(Bitmap bitmap2) {
                        CSSquareView.this.mShapeFinal = bitmap2;
                        CSSquareView.this.img_fg.setImageBitmap(CSSquareView.this.mShapeFinal);
                    }
                });
            }
        }
    }

    public void setShapeBgBitmap(Bitmap bitmap) {
        this.groundMode = 4;
        this.mShapeBgBitmap = bitmap;
        if (this.currentShapeRes != null) {
            this.img_fg.setImageBitmap(null);
            createShapeFinal(this.shapeSize, new OnShapeFinishedListener() { // from class: com.picspool.libsquare.view.CSSquareView.3
                @Override // com.picspool.libsquare.view.CSSquareView.OnShapeFinishedListener
                public void onXorImage(Bitmap bitmap2) {
                    CSSquareView.this.mShapeFinal = bitmap2;
                    CSSquareView.this.img_fg.setImageBitmap(CSSquareView.this.mShapeFinal);
                }
            });
        }
    }

    public void setForegroundGradient(GradientDrawable gradientDrawable) {
        if (gradientDrawable != null) {
            this.mCurrentDrawable = gradientDrawable;
            this.groundMode = 3;
            Bitmap bitmap = this.mShapePattern;
            if (bitmap != null && !bitmap.isRecycled()) {
                this.mShapePattern.recycle();
                this.mShapePattern = null;
            }
            if (this.currentShapeRes != null) {
                this.img_fg.setImageBitmap(null);
                createShapeFinal(this.shapeSize, new OnShapeFinishedListener() { // from class: com.picspool.libsquare.view.CSSquareView.4
                    @Override // com.picspool.libsquare.view.CSSquareView.OnShapeFinishedListener
                    public void onXorImage(Bitmap bitmap2) {
                        CSSquareView.this.mShapeFinal = bitmap2;
                        CSSquareView.this.img_fg.setImageBitmap(CSSquareView.this.mShapeFinal);
                    }
                });
            }
        }
    }

    public void createShapeFinal(final int i, final OnShapeFinishedListener onShapeFinishedListener) {
        this.currentShapeRes.getImageBitmap(getContext(), new DMWBImageRes.OnResImageLoadListener() { // from class: com.picspool.libsquare.view.CSSquareView.5
            @Override // com.picspool.lib.resource.DMWBImageRes.OnResImageLoadListener
            public void onImageLoadFinish(Bitmap bitmap) {
                int i2;
                int i3;
                int i4;
                int i5;
                OnShapeFinishedListener onShapeFinishedListener2;
                if (CSSquareView.this.mShapedBitmap == null) {
                    CSSquareView cSSquareView = CSSquareView.this;
                    int i6 = i;
                    cSSquareView.mShapedBitmap = Bitmap.createBitmap(i6, i6, Bitmap.Config.ARGB_8888);
                }
                if (bitmap == null && (onShapeFinishedListener2 = onShapeFinishedListener) != null) {
                    onShapeFinishedListener2.onXorImage(CSSquareView.this.mShapedBitmap);
                }
                Paint paint = new Paint();
                paint.setDither(false);
                paint.setAntiAlias(true);
                if (CSSquareView.this.mShapeCanvas == null) {
                    CSSquareView.this.mShapeCanvas = new Canvas(CSSquareView.this.mShapedBitmap);
                }
                CSSquareView.this.mShapeCanvas.drawColor(CSSquareView.this.layerColor);
                if (CSSquareView.this.groundMode != 1) {
                    if (CSSquareView.this.groundMode == 2) {
                        if (CSSquareView.this.currentForeRes != null) {
                            Bitmap localImageBitmap = ((DMWBImageRes) CSSquareView.this.currentForeRes).getLocalImageBitmap();
                            Bitmap createBgImage = CSSquareView.this.createBgImage(localImageBitmap, i);
                            localImageBitmap.recycle();
                            CSSquareView.this.mShapeCanvas.drawBitmap(createBgImage, 0.0f, 0.0f, paint);
                            createBgImage.recycle();
                        }
                    } else if (CSSquareView.this.groundMode != 3) {
                        if (CSSquareView.this.groundMode == 4) {
                            if (CSSquareView.this.mShapeBgBitmap != null) {
                                Bitmap bitmap2 = CSSquareView.this.mShapeBgBitmap;
                                int i7 = i;
                                Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap2, i7, i7, false);
                                CSSquareView.this.mShapeCanvas.drawBitmap(createScaledBitmap, 0.0f, 0.0f, paint);
                                createScaledBitmap.recycle();
                            }
                        } else {
                            int width = CSSquareView.this.mShapePattern.getWidth();
                            int height = CSSquareView.this.mShapePattern.getHeight();
                            if (width >= height) {
                                i5 = (width - height) / 2;
                                i4 = i5 + height;
                                i3 = height;
                                i2 = 0;
                            } else {
                                i2 = (height - width) / 2;
                                i3 = i2 + width;
                                i4 = width;
                                i5 = 0;
                            }
                            CSSquareView.this.mShapeCanvas.drawBitmap(CSSquareView.this.mShapePattern, new Rect(i5, i2, i4, i3), new Rect(0, 0, CSSquareView.this.shapeSize, CSSquareView.this.shapeSize), paint);
                        }
                    } else if (CSSquareView.this.mCurrentDrawable != null) {
                        GradientDrawable gradientDrawable = CSSquareView.this.mCurrentDrawable;
                        int i8 = i;
                        gradientDrawable.setBounds(0, 0, i8, i8);
                        CSSquareView.this.mCurrentDrawable.draw(CSSquareView.this.mShapeCanvas);
                    }
                } else {
                    CSSquareView.this.mShapeCanvas.drawColor(CSSquareView.this.layerColor);
                }
                if (CSSquareView.this.currentShapeRes != null && bitmap != null) {
                    int i9 = i;
                    Bitmap createScaledBitmap2 = Bitmap.createScaledBitmap(bitmap, i9, i9, false);
                    if (createScaledBitmap2 != bitmap) {
                        bitmap.recycle();
                    }
                    if (CSSquareView.this.currentShapeRes.getShapeMode() == CSShapeRes.ShapeMode.OPAQUE) {
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
                    } else {
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                    }
                    CSSquareView.this.mShapeCanvas.drawBitmap(createScaledBitmap2, 0.0f, 0.0f, paint);
                    createScaledBitmap2.recycle();
                }
                OnShapeFinishedListener onShapeFinishedListener3 = onShapeFinishedListener;
                if (onShapeFinishedListener3 != null) {
                    onShapeFinishedListener3.onXorImage(CSSquareView.this.mShapedBitmap);
                }
            }

            @Override // com.picspool.lib.resource.DMWBImageRes.OnResImageLoadListener
            public void onImageLoadFaile() {
                OnShapeFinishedListener onShapeFinishedListener2 = onShapeFinishedListener;
                if (onShapeFinishedListener2 != null) {
                    onShapeFinishedListener2.onXorImage(null);
                }
            }
        });
    }

    public Bitmap createBgImage(Bitmap bitmap, int i) {
        if (((DMWBImageRes) this.currentForeRes).getScaleType() == DMWBImageRes.FitType.TITLE) {
            return createTileFgImage(bitmap, i);
        }
        return Bitmap.createScaledBitmap(bitmap, i, i, false);
    }

    private Bitmap createTileFgImage(Bitmap bitmap, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i2 = ((i + width) - 1) / width;
        int i3 = ((i + height) - 1) / height;
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        for (int i4 = 0; i4 < i2; i4++) {
            float f = i4 * width;
            canvas.drawBitmap(bitmap, f, 0.0f, (Paint) null);
            for (int i5 = 1; i5 < i3; i5++) {
                canvas.drawBitmap(bitmap, f, i5 * height, (Paint) null);
            }
        }
        return createBitmap;
    }

    public void resetClear() {
        recyclePic();
        recycleBackgroud();
        ImageViewTouch imageViewTouch = this.img_pic;
        if (imageViewTouch != null) {
            imageViewTouch.dispose();
        }
    }

    public void recyclePic() {
        this.curFilterRes = null;
        this.curBorderRes = null;
        this.curVigRes = null;
        this.img_pic.setImageBitmap(null);
        Bitmap bitmap = this.src;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.src.recycle();
            this.src = null;
        }
        Bitmap bitmap2 = this.dst;
        if (bitmap2 == null || bitmap2.isRecycled()) {
            return;
        }
        this.dst.recycle();
        this.dst = null;
    }

    public void recycleBackgroud() {
        setBackgroud(null);
        recycleDrawable(this.backgroundDrawable);
        this.backgroundDrawable = null;
    }

    public void recycleDrawable(Drawable drawable) {
        Bitmap bitmap;
        if (!(drawable instanceof BitmapDrawable) || (bitmap = ((BitmapDrawable) drawable).getBitmap()) == null || bitmap.isRecycled()) {
            return;
        }
        bitmap.recycle();
    }

    public boolean getIsBlurBackground() {
        return this.mIsBlurBackground;
    }

    public void setBgsrc(Bitmap bitmap) {
        this.bgsrc = bitmap;
    }

    public void setBlurBackground(float f, float f2, boolean z) {
        Bitmap cropCenterScaleBitmap;
        if (Math.abs(f - f2) > 0.001d || z) {
            DMWBRes dMWBRes = this.curFilterRes;
            if (dMWBRes != null) {
                Bitmap filterForType = GPUFilter.filterForType(this.mContext, this.bgsrc, ((GPUFilterRes) dMWBRes).getFilterType());
                cropCenterScaleBitmap = DMBitmapCrop.cropCenterScaleBitmap(filterForType, 300, 300);
                if (cropCenterScaleBitmap != filterForType && filterForType != null && !filterForType.isRecycled()) {
                    filterForType.recycle();
                }
            } else {
                cropCenterScaleBitmap = DMBitmapCrop.cropCenterScaleBitmap(this.bgsrc, 300, 300);
            }
            if (cropCenterScaleBitmap == null || cropCenterScaleBitmap.isRecycled()) {
                return;
            }
            if (f2 != 0.0f) {
                cropCenterScaleBitmap = FastBlurFilter.blur(cropCenterScaleBitmap, (int) (f2 * 25.0f), false);
            }
            if (cropCenterScaleBitmap == null || cropCenterScaleBitmap.isRecycled()) {
                return;
            }
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), cropCenterScaleBitmap);
            bitmapDrawable.setBounds(0, 0, getWidth(), getHeight());
            setSquareBackground(bitmapDrawable, true);
            setShapeBgBitmap(cropCenterScaleBitmap);
        }
    }

    public void setTileBackgroud(float f) {
        Bitmap sampeZoomFromBitmap;
        Bitmap tileBitmap;
        try {
            if (this.curFilterRes != null) {
                Bitmap filterForType = GPUFilter.filterForType(this.mContext, this.bgsrc, ((GPUFilterRes) this.curFilterRes).getFilterType());
                sampeZoomFromBitmap = DMBitmapUtil.sampeZoomFromBitmap(filterForType, 800);
                if (sampeZoomFromBitmap != filterForType && filterForType != null && !filterForType.isRecycled()) {
                    filterForType.recycle();
                }
            } else {
                sampeZoomFromBitmap = DMBitmapUtil.sampeZoomFromBitmap(this.bgsrc, 800);
            }
            if (sampeZoomFromBitmap == null || sampeZoomFromBitmap.isRecycled() || (tileBitmap = getTileBitmap(sampeZoomFromBitmap, 0.6f - (f * 0.5f), true)) == null || tileBitmap.isRecycled()) {
                return;
            }
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), tileBitmap);
            bitmapDrawable.setBounds(0, 0, getWidth(), getHeight());
            setSquareBackground(bitmapDrawable, true);
            setShapeBgBitmap(tileBitmap);
        } catch (Exception unused) {
        }
    }

    private Bitmap getTileBitmap(Bitmap bitmap, float f, boolean z) {
        Bitmap createBitmap;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (z) {
            int i = width < height ? width : height;
            createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        } else {
            createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(createBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        int ceil = (int) Math.ceil(1.0f / f);
        for (int i2 = 0; i2 < ceil; i2++) {
            for (int i3 = 0; i3 < ceil; i3++) {
                Matrix matrix = new Matrix();
                matrix.preScale(f, f);
                matrix.postTranslate(width * f * i2, height * f * i3);
                canvas.drawBitmap(bitmap, matrix, null);
            }
        }
        return createBitmap;
    }

    public void setMosaicBackgroud(float f) {
        Bitmap cropCenterScaleBitmap;
        DMWBRes dMWBRes = this.curFilterRes;
        if (dMWBRes != null) {
            Bitmap filterForType = GPUFilter.filterForType(this.mContext, this.bgsrc, ((GPUFilterRes) dMWBRes).getFilterType());
            cropCenterScaleBitmap = DMBitmapCrop.cropCenterScaleBitmap(filterForType, 300, 300);
            if (cropCenterScaleBitmap != filterForType && filterForType != null && !filterForType.isRecycled()) {
                filterForType.recycle();
            }
        } else {
            cropCenterScaleBitmap = DMBitmapCrop.cropCenterScaleBitmap(this.bgsrc, 300, 300);
        }
        if (cropCenterScaleBitmap == null || cropCenterScaleBitmap.isRecycled()) {
            return;
        }
        if (f != 0.0f) {
            cropCenterScaleBitmap = getGridMosaic(cropCenterScaleBitmap, ((int) (f * 20.0f)) + 5);
        }
        if (cropCenterScaleBitmap == null || cropCenterScaleBitmap.isRecycled()) {
            return;
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), cropCenterScaleBitmap);
        bitmapDrawable.setBounds(0, 0, getWidth(), getHeight());
        setSquareBackground(bitmapDrawable, true);
        setShapeBgBitmap(cropCenterScaleBitmap);
    }

    private Bitmap getGridMosaic(Bitmap bitmap, int i) {
        int i2 = i;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width <= 0 || height <= 0) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float f = i2;
        int ceil = (int) Math.ceil(width / f);
        int ceil2 = (int) Math.ceil(height / f);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        int i3 = 0;
        while (i3 < ceil) {
            int i4 = 0;
            while (i4 < ceil2) {
                int i5 = i2 * i3;
                int i6 = i2 * i4;
                int i7 = i5 + i2;
                if (i7 > width) {
                    i7 = width;
                }
                int i8 = i6 + i2;
                if (i8 > height) {
                    i8 = height;
                }
                int pixel = bitmap.getPixel(i5, i6);
                Rect rect = new Rect(i5, i6, i7, i8);
                paint.setColor(pixel);
                canvas.drawRect(rect, paint);
                i4++;
                i2 = i;
            }
            i3++;
            i2 = i;
        }
        return createBitmap;
    }

    public void initSetPictureImageBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        resetClear();
        this.src = bitmap;
        this.img_pic.setImageBitmap(bitmap);
        Bitmap bitmap2 = this.src;
        this.dst = bitmap2;
        this.bgsrc = bitmap2;
    }

    public void setSquareBackgroundRes(DMWBRes dMWBRes) {
        Bitmap localImageBitmap;
        if (dMWBRes instanceof DMWBColorRes) {
            DMWBColorRes dMWBColorRes = (DMWBColorRes) dMWBRes;
            Drawable colorDrawable = new ColorDrawable(dMWBColorRes.getColorValue());
            this.backgroundColor = dMWBColorRes.getColorValue();
            setSquareBackground(colorDrawable, false);
            setForeground(dMWBRes, 1);
        } else if (dMWBRes instanceof CSGradientRes) {
            GradientDrawable gradientDrawable = ((CSGradientRes) dMWBRes).getGradientDrawable();
            gradientDrawable.setBounds(0, 0, getWidth(), getHeight());
            setSquareBackground(gradientDrawable, false);
            setForegroundGradient(gradientDrawable);
        } else if (dMWBRes instanceof DMWBImageRes) {
            DMWBImageRes dMWBImageRes = (DMWBImageRes) dMWBRes;
            if (dMWBImageRes.getImageType() == DMWBRes.LocationType.ONLINE) {
                localImageBitmap = BitmapFactory.decodeFile(dMWBImageRes.getImageFileName());
            } else {
                localImageBitmap = dMWBImageRes.getLocalImageBitmap();
            }
            if (dMWBImageRes.getScaleType() == DMWBImageRes.FitType.TITLE) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), localImageBitmap);
                bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
                bitmapDrawable.setDither(true);
                setSquareBackground(bitmapDrawable, false);
            } else if (dMWBImageRes.getScaleType() == DMWBImageRes.FitType.SCALE) {
                dMWBImageRes.setScaleType(DMWBImageRes.FitType.SCALE);
                BitmapDrawable bitmapDrawable2 = new BitmapDrawable(getResources(), localImageBitmap);
                bitmapDrawable2.setBounds(0, 0, getWidth(), getHeight());
                setSquareBackground(bitmapDrawable2, false);
            }
            setForeground(dMWBRes, 2);
        }
    }

    public void setSquareBackground(Drawable drawable, boolean z) {
        recycleBackgroud();
        this.mIsBlurBackground = z;
        this.backgroundDrawable = drawable;
        setBackgroud(drawable);
    }

    private void setBackgroud(Drawable drawable) {
        if (Build.VERSION.SDK_INT < 16) {
            this.img_bg.setBackgroundDrawable(drawable);
        } else {
            setBackground16(this.img_bg, drawable);
        }
    }

    private void setBackground16(ImageView imageView, Drawable drawable) {
        imageView.setBackground(drawable);
    }

    public void getSizeBitmap(int i) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(createBitmap);
        canvas.setDrawFilter(this.mPaintFlagsDrawFilter);
        canvas.drawColor(-1);
        Drawable drawable = this.backgroundDrawable;
        if (drawable != null) {
            drawable.setBounds(0, 0, i, i);
            this.backgroundDrawable.draw(canvas);
            this.backgroundDrawable.setBounds(0, 0, getWidth(), getHeight());
        }
        if (this.img_bgfilter.getVisibility() == View.VISIBLE) {
            canvas.drawColor(this.colorfiltervalue);
        }
        Bitmap dispalyImage = this.img_pic.getDispalyImage(i);
        if (dispalyImage != null && !dispalyImage.isRecycled()) {
            canvas.drawBitmap(dispalyImage, 0.0f, 0.0f, (Paint) null);
            dispalyImage.recycle();
        }
        if (this.currentShapeRes != null) {
            Bitmap bitmap = this.mShapeFinal;
            if (bitmap != null && !bitmap.isRecycled()) {
                canvas.drawBitmap(this.mShapeFinal, (Rect) null, new Rect(0, 0, i, i), paint);
            } else {
                createShapeFinal(i, new OnShapeFinishedListener() { // from class: com.picspool.libsquare.view.CSSquareView.6
                    @Override // com.picspool.libsquare.view.CSSquareView.OnShapeFinishedListener
                    public void onXorImage(Bitmap bitmap2) {
                        if (bitmap2 == null) {
                            return;
                        }
                        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
                        if (bitmap2.isRecycled()) {
                            return;
                        }
                        bitmap2.recycle();
                    }
                });
            }
        }
        Bitmap resultBitmap = this.sfcView_faces.getResultBitmap();
        canvas.drawBitmap(resultBitmap, new Rect(0, 0, resultBitmap.getWidth(), resultBitmap.getHeight()), new Rect(0, 0, i, i), (Paint) null);
        if (resultBitmap != null && !resultBitmap.isRecycled()) {
            resultBitmap.recycle();
        }
        OnGetResultBitmapListener onGetResultBitmapListener = this.mListener;
        if (onGetResultBitmapListener != null) {
            onGetResultBitmapListener.getResultBitmapSuccess(createBitmap);
        }
    }

    public void setScale(float f) {
        ImageViewTouch imageViewTouch = this.img_pic;
        if (imageViewTouch != null) {
            imageViewTouch.changeScale(f);
        }
    }

    public void setSmallerType(ImageViewTouchBase.DisplayType displayType) {
        ImageViewTouch imageViewTouch = this.img_pic;
        if (imageViewTouch != null) {
            imageViewTouch.setDisplayType(displayType);
        }
    }

    public void setRotateDegree(float f) {
        ImageViewTouch imageViewTouch = this.img_pic;
        if (imageViewTouch != null) {
            imageViewTouch.changeRotation(f);
        }
    }

    public float getSizeScale() {
        return this.img_pic.getScale();
    }

    public void setSizeRotationEnable(boolean z) {
        this.img_pic.SetRotationEnable(z);
    }

    public void setSizeScaleEnable(boolean z) {
        this.img_pic.setScaleEnabled(z);
    }

    public void setSizeRotation(float f) {
        this.img_pic.postRotation(f);
    }

    public void setSizeReversal(float f) {
        this.img_pic.Reversal(f);
    }

    public void Zoom(float f) {
        this.img_pic.Zoom(f);
    }

    public void setOrignial() {
        this.img_pic.setImageBitmap(this.img_pic.getImageBitmap());
    }

    public void setStrawable(Boolean bool) {
        if (this.isStrawable == bool) {
            return;
        }
        this.isStrawable = bool;
        if (this.mColorStraw == null) {
            DMColorStraw dMColorStraw = new DMColorStraw(this.mContext, this.img_pointer);
            this.mColorStraw = dMColorStraw;
            dMColorStraw.setListener(this, this);
        }
        if (bool.booleanValue()) {
            Bitmap createViewCanvas = createViewCanvas();
            if (createViewCanvas != null) {
                this.mColorStraw.setStrawBitmap(createViewCanvas);
            } else {
                bool = false;
            }
        }
        this.mColorStraw.setStrawable(bool);
    }

    public Bitmap createViewCanvas() {
        Bitmap createBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        Matrix matrix = new Matrix();
        matrix.set(this.img_pic.getImageViewMatrix());
        Bitmap bitmap = this.dst;
        if (bitmap != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(this.dst, matrix, null);
        } else {
            Bitmap bitmap2 = this.src;
            if (bitmap2 != null && !bitmap2.isRecycled()) {
                canvas.drawBitmap(this.src, matrix, null);
            }
        }
        return createBitmap;
    }

    @Override // com.picspool.lib.widget.listener.OnDMViewColorChangedListener
    public void onColorChanged(int i, boolean z) {
        setSquareBackground(new ColorDrawable(i), false);
    }

    @Override // com.picspool.lib.widget.colorstraw.DMColorStraw.OnStrawingListener
    public void Stawing(Boolean bool) {
        this.img_pic.setLockTouch(bool.booleanValue());
    }

    public void setVignette(DMWBRes dMWBRes, OnFilterFinishedListener onFilterFinishedListener) {
        if (dMWBRes != null) {
            this.curVigRes = dMWBRes;
            this.shadowed = false;
            updateImagePic(onFilterFinishedListener);
        }
    }

    public void setFilter(DMWBRes dMWBRes, OnFilterFinishedListener onFilterFinishedListener) {
        if (dMWBRes != null) {
            this.curFilterRes = dMWBRes;
            this.shadowed = false;
            updateImagePic(onFilterFinishedListener);
        }
    }

    public void setBorder(DMWBRes dMWBRes, OnFilterFinishedListener onFilterFinishedListener) {
        if (dMWBRes != null) {
            this.isAutoBorderRes = false;
            this.curBorderRes = dMWBRes;
            this.shadowed = false;
            this.img_pic.setBorderRes(dMWBRes);
        }
    }

    public void clearBorder() {
        this.curBorderRes = null;
        this.img_pic.setBorderRes(null);
    }

    public void setRoundCorner(float f, float f2) {
        if (this.curBorderRes != null) {
            this.curBorderRes = null;
            updateImagePic(null);
            return;
        }
        this.img_pic.postRoundRadius(f, f2);
    }

    public void setOverlapping(boolean z) {
        if (z) {
            DMWBRes res = new CSSquareUiFrameManager(getContext()).getRes("b_white");
            this.curBorderRes = res;
            this.img_pic.setBorderRes(res);
            this.img_pic.setIsOverlapping(true);
            return;
        }
        this.img_pic.setIsOverlapping(false);
    }

    public void setBlurBgGradientShader(Shader shader) {
        this.img_pic.setBlurBgGradientShader(shader);
    }

    public void setBlurBgHueColorFilter(float f) {
        this.img_pic.setBlurBgHueColorFilter(f);
    }

    public Bitmap overlappingBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return bitmap;
        }
        if (bitmap.isRecycled()) {
            return null;
        }
        float rotationScale = getRotationScale(this.mRotationDegree, bitmap.getWidth(), bitmap.getHeight());
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, (Rect) null, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), paint);
        int saveLayer = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.rotate(this.mRotationDegree + 1.0f, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.scale(rotationScale, rotationScale, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.translate(this.mTranslateX, this.mTranslateY);
        canvas.drawBitmap(bitmap, (Rect) null, new Rect(0, 0, (int) ((bitmap.getWidth() * rotationScale) + 0.5f), (int) ((bitmap.getHeight() * rotationScale) + 0.5f)), paint);
        canvas.restoreToCount(saveLayer);
        int saveLayer2 = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.rotate((-this.mRotationDegree) - 1.0f, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.scale(rotationScale, rotationScale, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.translate(this.mTranslateX, this.mTranslateY);
        canvas.drawBitmap(bitmap, (Rect) null, new Rect(0, 0, (int) ((bitmap.getWidth() * rotationScale) + 0.5f), (int) ((bitmap.getHeight() * rotationScale) + 0.5f)), paint);
        canvas.restoreToCount(saveLayer2);
        int saveLayer3 = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.scale(rotationScale, rotationScale, canvas.getWidth() / 2, canvas.getHeight() / 2);
        float f = 1.0f - rotationScale;
        canvas.translate((bitmap.getWidth() * f) / 2.0f, (f * bitmap.getHeight()) / 2.0f);
        canvas.drawBitmap(bitmap, (Rect) null, new Rect(0, 0, (int) ((bitmap.getWidth() * rotationScale) + 0.5f), (int) ((rotationScale * bitmap.getHeight()) + 0.5f)), paint);
        canvas.restoreToCount(saveLayer3);
        return createBitmap;
    }

    private Point getRotationPoint(PointF pointF, PointF pointF2, double d) {
        double radians = Math.toRadians(d);
        float f = pointF.x;
        float f2 = pointF.y;
        float f3 = pointF2.x;
        float f4 = pointF2.y;
        float f5 = f - f3;
        float f6 = f2 - f4;
        return new Point((int) (((((float) Math.cos(radians)) * f5) - (((float) Math.sin(radians)) * f6)) + f3), (int) ((f5 * ((float) Math.sin(radians))) + (f6 * ((float) Math.cos(radians))) + f4));
    }

    private float getRotationScale(float f, float f2, float f3) {
        float f4 = f2 / 2.0f;
        float f5 = f3 / 2.0f;
        double d = f;
        Point rotationPoint = getRotationPoint(new PointF(0.0f, 0.0f), new PointF(f4, f5), d);
        Point rotationPoint2 = getRotationPoint(new PointF(f2, 0.0f), new PointF(f4, f5), d);
        Point rotationPoint3 = getRotationPoint(new PointF(f2, f3), new PointF(f4, f5), d);
        Point rotationPoint4 = getRotationPoint(new PointF(0.0f, f3), new PointF(f4, f5), d);
        if (f2 > f3) {
            float max = Math.max(Math.max(rotationPoint.y, rotationPoint2.y), Math.max(rotationPoint3.y, rotationPoint4.y)) - Math.min(Math.min(rotationPoint.y, rotationPoint2.y), Math.min(rotationPoint3.y, rotationPoint4.y));
            float f6 = f3 / max;
            this.mTranslateX = (int) (((((f2 * max) / f3) - f2) / 2.0f) + 0.5f);
            this.mTranslateY = (int) (((max - f3) / 2.0f) + 0.5f);
            return f6;
        }
        float max2 = Math.max(Math.max(rotationPoint.x, rotationPoint2.x), Math.max(rotationPoint3.x, rotationPoint4.x)) - Math.min(Math.min(rotationPoint.x, rotationPoint2.x), Math.min(rotationPoint3.x, rotationPoint4.x));
        float f7 = f2 / max2;
        this.mTranslateX = (int) (((max2 - f2) / 2.0f) + 0.5f);
        this.mTranslateY = (int) (((((f3 * max2) / f2) - f3) / 2.0f) + 0.5f);
        return f7;
    }

    public static Bitmap Shadowfilter(Bitmap bitmap, int i, int i2) {
        if (bitmap == null || bitmap.isRecycled() || i2 <= 0) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (height > width) {
            int i3 = i2 * 2;
            int i4 = (int) ((height / ((height + i3) / (width + i3))) + 0.5f);
            Bitmap createBitmap = Bitmap.createBitmap(i4, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Paint paint = new Paint();
            paint.setColor(i);
            paint.setShadowLayer(i2, 0.0f, 0.0f, i);
            Rect rect = new Rect(i2, i2, i4 - i2, height - i2);
            canvas.drawRect(new Rect(rect), paint);
            paint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
            canvas.drawBitmap(bitmap, (Rect) null, rect, paint);
            return createBitmap;
        }
        int i5 = i2 * 2;
        int i6 = (int) ((width * ((height + i5) / (i5 + width))) + 0.5f);
        Bitmap createBitmap2 = Bitmap.createBitmap(width, i6, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(createBitmap2);
        Paint paint2 = new Paint();
        paint2.setColor(i);
        paint2.setShadowLayer(i2, 0.0f, 0.0f, i);
        Rect rect2 = new Rect(i2, i2, width - i2, i6 - i2);
        canvas2.drawRect(new Rect(rect2), paint2);
        paint2.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
        canvas2.drawBitmap(bitmap, (Rect) null, rect2, paint2);
        return createBitmap2;
    }

    private void updateImagePic(final OnFilterFinishedListener onFilterFinishedListener) {
        Bitmap bitmap = this.src;
        if (bitmap != null && !bitmap.isRecycled()) {
            CSAsyncSizeProcess.executeAsyncFilter(this.mContext, this.src, this.curFilterRes, this.curVigRes, null, new OnPostFilteredListener() { // from class: com.picspool.libsquare.view.CSSquareView.7
                @Override // com.picspool.lib.filter.listener.OnPostFilteredListener
                public void postFiltered(Bitmap bitmap2) {
                    CSSquareView.this.img_pic.setImageBitmapWithStatKeep(null);
                    if (CSSquareView.this.dst != null && !CSSquareView.this.dst.isRecycled() && CSSquareView.this.dst != CSSquareView.this.src) {
                        CSSquareView.this.dst.recycle();
                    }
                    CSSquareView.this.dst = bitmap2;
                    CSSquareView.this.img_pic.setImageBitmapWithStatKeep(CSSquareView.this.dst);
                    CSSquareView.this.img_pic.resetBitmap();
                    OnFilterFinishedListener onFilterFinishedListener2 = onFilterFinishedListener;
                    if (onFilterFinishedListener2 != null) {
                        onFilterFinishedListener2.postFinished();
                    }
                }
            });
        } else if (onFilterFinishedListener != null) {
            onFilterFinishedListener.postFinished();
        }
    }

    public void setFeatherBitmap(boolean z) {
        if (z) {
            this.img_pic.setMosaicIntensity(DMPagerSlidingTabStrip.DEF_VALUE_TAB_TEXT_ALPHA);
        } else {
            this.img_pic.setMosaicIntensity(0);
        }
    }

    public void toPageEffect(int i, boolean z) {
        if (this.img_pic != null) {
            if (z) {
                if (this.isAutoBorderRes) {
                    this.curBorderRes = null;
                }
                updateImagePic(null);
            }
            this.img_pic.setPageEffectIntensity(i);
        }
    }

    public void toMosaic(int i) {
        ImageViewTouch imageViewTouch = this.img_pic;
        if (imageViewTouch != null) {
            imageViewTouch.setMosaicIntensity(i);
        }
    }

    public void setShadow(boolean z) {
        if (z) {
            this.img_pic.setIsShowShadow(true);
        } else {
            this.img_pic.setIsShowShadow(false);
        }
    }

    public void setOverlay(boolean z) {
        this.img_pic.setIsOverlay(z);
    }

    public void reversal(float f) {
        this.img_pic.Reversal(f);
    }

    public void rotation(float f) {
        this.img_pic.postRotation(f);
    }

    public void setPicToCenter() {
        this.img_pic.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        this.img_pic.resetDisplay();
    }

    public void setPicFill() {
        this.img_pic.setDisplayType(ImageViewTouchBase.DisplayType.FILL_TO_SCREEN);
        this.img_pic.resetDisplay();
    }

    public void zoom(float f) {
        this.img_pic.Zoom(f);
    }

    public void scrollPicBy(float f, float f2) {
        this.img_pic.setTop();
        this.img_pic.resetDisplay();
    }

    private Bitmap blur(Bitmap bitmap, int i, boolean z) {
        int[] iArr;
        Bitmap bitmap2 = bitmap;
        int i2 = i;
        if (bitmap2 == null) {
            return null;
        }
        if (!z) {
            bitmap2 = bitmap2.copy(bitmap.getConfig(), true);
        }
        if (i2 < 1) {
            return null;
        }
        int width = bitmap2.getWidth();
        int height = bitmap2.getHeight();
        int i3 = width * height;
        int[] iArr2 = new int[i3];
        bitmap2.getPixels(iArr2, 0, width, 0, 0, width, height);
        int i4 = width - 1;
        int i5 = height - 1;
        int i6 = i2 + i2 + 1;
        int[] iArr3 = new int[i3];
        int[] iArr4 = new int[i3];
        int[] iArr5 = new int[i3];
        int[] iArr6 = new int[Math.max(width, height)];
        int i7 = (i6 + 1) >> 1;
        int i8 = i7 * i7;
        int i9 = i8 * 256;
        int[] iArr7 = new int[i9];
        for (int i10 = 0; i10 < i9; i10++) {
            iArr7[i10] = i10 / i8;
        }
        int[][] iArr8 = (int[][]) Array.newInstance(int.class, i6, 3);
        int i11 = i2 + 1;
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        while (i12 < height) {
            Bitmap bitmap3 = bitmap2;
            int i15 = height;
            int i16 = 0;
            int i17 = 0;
            int i18 = 0;
            int i19 = 0;
            int i20 = 0;
            int i21 = 0;
            int i22 = 0;
            int i23 = 0;
            int i24 = -i2;
            int i25 = 0;
            while (i24 <= i2) {
                int i26 = i5;
                int[] iArr9 = iArr6;
                int i27 = iArr2[i13 + Math.min(i4, Math.max(i24, 0))];
                int[] iArr10 = iArr8[i24 + i2];
                iArr10[0] = (i27 & 16711680) >> 16;
                iArr10[1] = (i27 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                iArr10[2] = i27 & 255;
                int abs = i11 - Math.abs(i24);
                i25 += iArr10[0] * abs;
                i16 += iArr10[1] * abs;
                i17 += iArr10[2] * abs;
                if (i24 > 0) {
                    i21 += iArr10[0];
                    i22 += iArr10[1];
                    i23 += iArr10[2];
                } else {
                    i18 += iArr10[0];
                    i19 += iArr10[1];
                    i20 += iArr10[2];
                }
                i24++;
                i5 = i26;
                iArr6 = iArr9;
            }
            int i28 = i5;
            int[] iArr11 = iArr6;
            int i29 = i25;
            int i30 = i2;
            int i31 = 0;
            while (i31 < width) {
                iArr3[i13] = iArr7[i29];
                iArr4[i13] = iArr7[i16];
                iArr5[i13] = iArr7[i17];
                int i32 = i29 - i18;
                int i33 = i16 - i19;
                int i34 = i17 - i20;
                int[] iArr12 = iArr8[((i30 - i2) + i6) % i6];
                int i35 = i18 - iArr12[0];
                int i36 = i19 - iArr12[1];
                int i37 = i20 - iArr12[2];
                if (i12 == 0) {
                    iArr = iArr7;
                    iArr11[i31] = Math.min(i31 + i2 + 1, i4);
                } else {
                    iArr = iArr7;
                }
                int i38 = iArr2[i14 + iArr11[i31]];
                iArr12[0] = (i38 & 16711680) >> 16;
                iArr12[1] = (i38 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                iArr12[2] = i38 & 255;
                int i39 = i21 + iArr12[0];
                int i40 = i22 + iArr12[1];
                int i41 = i23 + iArr12[2];
                i29 = i32 + i39;
                i16 = i33 + i40;
                i17 = i34 + i41;
                i30 = (i30 + 1) % i6;
                int[] iArr13 = iArr8[i30 % i6];
                i18 = i35 + iArr13[0];
                i19 = i36 + iArr13[1];
                i20 = i37 + iArr13[2];
                i21 = i39 - iArr13[0];
                i22 = i40 - iArr13[1];
                i23 = i41 - iArr13[2];
                i13++;
                i31++;
                iArr7 = iArr;
            }
            i14 += width;
            i12++;
            bitmap2 = bitmap3;
            height = i15;
            i5 = i28;
            iArr6 = iArr11;
        }
        Bitmap bitmap4 = bitmap2;
        int i42 = i5;
        int[] iArr14 = iArr6;
        int i43 = height;
        int[] iArr15 = iArr7;
        int i44 = 0;
        while (i44 < width) {
            int i45 = -i2;
            int i46 = i6;
            int[] iArr16 = iArr2;
            int i47 = 0;
            int i48 = 0;
            int i49 = 0;
            int i50 = 0;
            int i51 = 0;
            int i52 = 0;
            int i53 = 0;
            int i54 = i45;
            int i55 = i45 * width;
            int i56 = 0;
            int i57 = 0;
            while (i54 <= i2) {
                int i58 = width;
                int max = Math.max(0, i55) + i44;
                int[] iArr17 = iArr8[i54 + i2];
                iArr17[0] = iArr3[max];
                iArr17[1] = iArr4[max];
                iArr17[2] = iArr5[max];
                int abs2 = i11 - Math.abs(i54);
                i56 += iArr3[max] * abs2;
                i57 += iArr4[max] * abs2;
                i47 += iArr5[max] * abs2;
                if (i54 > 0) {
                    i51 += iArr17[0];
                    i52 += iArr17[1];
                    i53 += iArr17[2];
                } else {
                    i48 += iArr17[0];
                    i49 += iArr17[1];
                    i50 += iArr17[2];
                }
                int i59 = i42;
                if (i54 < i59) {
                    i55 += i58;
                }
                i54++;
                i42 = i59;
                width = i58;
            }
            int i60 = width;
            int i61 = i42;
            int i62 = i44;
            int i63 = i2;
            int i64 = i57;
            int i65 = i43;
            int i66 = i56;
            int i67 = 0;
            while (i67 < i65) {
                iArr16[i62] = (iArr16[i62] & ViewCompat.MEASURED_STATE_MASK) | (iArr15[i66] << 16) | (iArr15[i64] << 8) | iArr15[i47];
                int i68 = i66 - i48;
                int i69 = i64 - i49;
                int i70 = i47 - i50;
                int[] iArr18 = iArr8[((i63 - i2) + i46) % i46];
                int i71 = i48 - iArr18[0];
                int i72 = i49 - iArr18[1];
                int i73 = i50 - iArr18[2];
                if (i44 == 0) {
                    iArr14[i67] = Math.min(i67 + i11, i61) * i60;
                }
                int i74 = iArr14[i67] + i44;
                iArr18[0] = iArr3[i74];
                iArr18[1] = iArr4[i74];
                iArr18[2] = iArr5[i74];
                int i75 = i51 + iArr18[0];
                int i76 = i52 + iArr18[1];
                int i77 = i53 + iArr18[2];
                i66 = i68 + i75;
                i64 = i69 + i76;
                i47 = i70 + i77;
                i63 = (i63 + 1) % i46;
                int[] iArr19 = iArr8[i63];
                i48 = i71 + iArr19[0];
                i49 = i72 + iArr19[1];
                i50 = i73 + iArr19[2];
                i51 = i75 - iArr19[0];
                i52 = i76 - iArr19[1];
                i53 = i77 - iArr19[2];
                i62 += i60;
                i67++;
                i2 = i;
            }
            i44++;
            i2 = i;
            i42 = i61;
            i43 = i65;
            i6 = i46;
            iArr2 = iArr16;
            width = i60;
        }
        int i78 = width;
        bitmap4.setPixels(iArr2, 0, i78, 0, 0, i78, i43);
        return bitmap4;
    }

    public void addStickerStateCallSpreader(DMStickerStateCallback dMStickerStateCallback) {
        List<DMStickerStateCallback> list = this.StickerStateCallSpreaders;
        if (list != null) {
            list.add(dMStickerStateCallback);
        }
    }

    public void clearStickerStateCallSpreader() {
        List<DMStickerStateCallback> list = this.StickerStateCallSpreaders;
        if (list != null) {
            list.clear();
        }
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void stickerSelected(DMSticker dMSticker) {
        this.mCurrentFace = dMSticker;
        List<DMStickerStateCallback> list = this.StickerStateCallSpreaders;
        if (list != null) {
            for (DMStickerStateCallback dMStickerStateCallback : list) {
                dMStickerStateCallback.stickerSelected(dMSticker);
            }
        }
        IStickercallback iStickercallback = this.iStickercallback;
        if (iStickercallback != null) {
            iStickercallback.onIStickerSelected();
        }
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void editButtonClicked() {
        if (this.mCurrentFace != null) {
            this.sfcView_faces.removeCurSelectedSticker();
            Bitmap bitmap = this.mCurrentFace.getBitmap();
            for (int i = 0; i < this.StickerBitmapList.size(); i++) {
                if (bitmap == this.StickerBitmapList.get(i)) {
                    this.StickerBitmapList.remove(bitmap);
                    bitmap.recycle();
                    bitmap = null;
                }
            }
            this.mCurrentFace = null;
        }
        List<DMStickerStateCallback> list = this.StickerStateCallSpreaders;
        if (list != null) {
            for (DMStickerStateCallback dMStickerStateCallback : list) {
                dMStickerStateCallback.editButtonClicked();
            }
        }
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void noStickerSelected() {
        this.mCurrentFace = null;
        List<DMStickerStateCallback> list = this.StickerStateCallSpreaders;
        if (list != null) {
            for (DMStickerStateCallback dMStickerStateCallback : list) {
                dMStickerStateCallback.noStickerSelected();
            }
        }
        IStickercallback iStickercallback = this.iStickercallback;
        if (iStickercallback != null) {
            iStickercallback.onIStickernoSelected();
        }
    }

    @Override // com.picspool.lib.sticker.util.DMStickerStateCallback
    public void onDoubleClicked() {
        List<DMStickerStateCallback> list = this.StickerStateCallSpreaders;
        if (list != null) {
            for (DMStickerStateCallback dMStickerStateCallback : list) {
                dMStickerStateCallback.onDoubleClicked();
            }
        }
    }

    public void setiStickercallback(IStickercallback iStickercallback) {
        this.iStickercallback = iStickercallback;
    }
}
